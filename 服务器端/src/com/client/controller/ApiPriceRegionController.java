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
import com.chengxusheji.po.PriceRegion;
import com.chengxusheji.service.PriceRegionService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/priceRegion") 
public class ApiPriceRegionController {
	@Resource PriceRegionService priceRegionService;
	@Resource AuthService authService;

	@InitBinder("priceRegion")
	public void initBinderPriceRegion(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("priceRegion.");
	}

	/*客户端ajax方式添加价格区间信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated PriceRegion priceRegion, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        priceRegionService.addPriceRegion(priceRegion); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新价格区间信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated PriceRegion priceRegion, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        priceRegionService.updatePriceRegion(priceRegion);  //更新记录到数据库
        return JsonResultBuilder.ok(priceRegionService.getPriceRegion(priceRegion.getRegionId()));
	}

	/*ajax方式显示获取价格区间详细信息*/
	@RequestMapping(value="/get/{regionId}",method=RequestMethod.POST)
	public JsonResult getPriceRegion(@PathVariable int regionId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键regionId获取PriceRegion对象*/
        PriceRegion priceRegion = priceRegionService.getPriceRegion(regionId); 
        return JsonResultBuilder.ok(priceRegion);
	}

	/*ajax方式删除价格区间记录*/
	@RequestMapping(value="/delete/{regionId}",method=RequestMethod.POST)
	public JsonResult deletePriceRegion(@PathVariable int regionId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			priceRegionService.deletePriceRegion(regionId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询价格区间信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)priceRegionService.setRows(rows);
		List<PriceRegion> priceRegionList = priceRegionService.queryPriceRegion(page);
	    /*计算总的页数和总的记录数*/
	    priceRegionService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = priceRegionService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = priceRegionService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", priceRegionList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有价格区间
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<PriceRegion> priceRegionList = priceRegionService.queryAllPriceRegion(); 
		return JsonResultBuilder.ok(priceRegionList);
	}
}

