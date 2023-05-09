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
import com.chengxusheji.po.NewOldLevel;
import com.chengxusheji.service.NewOldLevelService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/newOldLevel") 
public class ApiNewOldLevelController {
	@Resource NewOldLevelService newOldLevelService;
	@Resource AuthService authService;

	@InitBinder("newOldLevel")
	public void initBinderNewOldLevel(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("newOldLevel.");
	}

	/*客户端ajax方式添加新旧程度信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated NewOldLevel newOldLevel, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        newOldLevelService.addNewOldLevel(newOldLevel); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新新旧程度信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated NewOldLevel newOldLevel, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        newOldLevelService.updateNewOldLevel(newOldLevel);  //更新记录到数据库
        return JsonResultBuilder.ok(newOldLevelService.getNewOldLevel(newOldLevel.getLevelId()));
	}

	/*ajax方式显示获取新旧程度详细信息*/
	@RequestMapping(value="/get/{levelId}",method=RequestMethod.POST)
	public JsonResult getNewOldLevel(@PathVariable int levelId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键levelId获取NewOldLevel对象*/
        NewOldLevel newOldLevel = newOldLevelService.getNewOldLevel(levelId); 
        return JsonResultBuilder.ok(newOldLevel);
	}

	/*ajax方式删除新旧程度记录*/
	@RequestMapping(value="/delete/{levelId}",method=RequestMethod.POST)
	public JsonResult deleteNewOldLevel(@PathVariable int levelId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			newOldLevelService.deleteNewOldLevel(levelId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询新旧程度信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)newOldLevelService.setRows(rows);
		List<NewOldLevel> newOldLevelList = newOldLevelService.queryNewOldLevel(page);
	    /*计算总的页数和总的记录数*/
	    newOldLevelService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = newOldLevelService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = newOldLevelService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", newOldLevelList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有新旧程度
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<NewOldLevel> newOldLevelList = newOldLevelService.queryAllNewOldLevel(); 
		return JsonResultBuilder.ok(newOldLevelList);
	}
}

