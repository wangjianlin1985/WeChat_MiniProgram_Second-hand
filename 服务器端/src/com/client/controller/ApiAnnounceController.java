package com.client.controller;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.chengxusheji.po.Announce;
import com.chengxusheji.service.AnnounceService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/announce") 
public class ApiAnnounceController {
	@Resource AnnounceService announceService;
	@Resource AuthService authService;

	@InitBinder("announce")
	public void initBinderAnnounce(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("announce.");
	}

	/*客户端ajax方式添加公告信息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated Announce announce, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        announceService.addAnnounce(announce); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新公告信息信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated Announce announce, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        announceService.updateAnnounce(announce);  //更新记录到数据库
        return JsonResultBuilder.ok(announceService.getAnnounce(announce.getAnnounceId()));
	}

	/*ajax方式显示获取公告信息详细信息*/
	@RequestMapping(value="/get/{announceId}",method=RequestMethod.POST)
	public JsonResult getAnnounce(@PathVariable int announceId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键announceId获取Announce对象*/
        Announce announce = announceService.getAnnounce(announceId); 
        return JsonResultBuilder.ok(announce);
	}

	/*ajax方式删除公告信息记录*/
	@RequestMapping(value="/delete/{announceId}",method=RequestMethod.POST)
	public JsonResult deleteAnnounce(@PathVariable int announceId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			announceService.deleteAnnounce(announceId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询公告信息信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(String announceTitle,String announceDate,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (announceTitle == null) announceTitle = "";
		if (announceDate == null) announceDate = "";
		if(rows != 0)announceService.setRows(rows);
		List<Announce> announceList = announceService.queryAnnounce(announceTitle, announceDate, page);
	    /*计算总的页数和总的记录数*/
	    announceService.queryTotalPageAndRecordNumber(announceTitle, announceDate);
	    /*获取到总的页码数目*/
	    int totalPage = announceService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = announceService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", announceList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有公告信息
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<Announce> announceList = announceService.queryAllAnnounce(); 
		return JsonResultBuilder.ok(announceList);
	}
}

