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
import com.chengxusheji.po.AreaInfo;
import com.chengxusheji.service.AreaInfoService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/areaInfo") 
public class ApiAreaInfoController {
	@Resource AreaInfoService areaInfoService;
	@Resource AuthService authService;

	@InitBinder("areaInfo")
	public void initBinderAreaInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("areaInfo.");
	}

	/*客户端ajax方式添加区域信息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated AreaInfo areaInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        areaInfoService.addAreaInfo(areaInfo); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新区域信息信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated AreaInfo areaInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        areaInfoService.updateAreaInfo(areaInfo);  //更新记录到数据库
        return JsonResultBuilder.ok(areaInfoService.getAreaInfo(areaInfo.getAreaId()));
	}

	/*ajax方式显示获取区域信息详细信息*/
	@RequestMapping(value="/get/{areaId}",method=RequestMethod.POST)
	public JsonResult getAreaInfo(@PathVariable int areaId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键areaId获取AreaInfo对象*/
        AreaInfo areaInfo = areaInfoService.getAreaInfo(areaId); 
        return JsonResultBuilder.ok(areaInfo);
	}

	/*ajax方式删除区域信息记录*/
	@RequestMapping(value="/delete/{areaId}",method=RequestMethod.POST)
	public JsonResult deleteAreaInfo(@PathVariable int areaId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			areaInfoService.deleteAreaInfo(areaId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询区域信息信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)areaInfoService.setRows(rows);
		List<AreaInfo> areaInfoList = areaInfoService.queryAreaInfo(page);
	    /*计算总的页数和总的记录数*/
	    areaInfoService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = areaInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = areaInfoService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", areaInfoList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有区域信息
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<AreaInfo> areaInfoList = areaInfoService.queryAllAreaInfo(); 
		return JsonResultBuilder.ok(areaInfoList);
	}
}

