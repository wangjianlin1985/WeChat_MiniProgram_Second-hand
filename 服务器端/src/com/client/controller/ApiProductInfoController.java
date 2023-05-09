package com.client.controller;

import java.text.SimpleDateFormat;
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
import com.chengxusheji.po.ProductInfo;
import com.chengxusheji.po.AreaInfo;
import com.chengxusheji.po.NewOldLevel;
import com.chengxusheji.po.PriceRegion;
import com.chengxusheji.po.ProductClass;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.service.ProductInfoService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/productInfo") 
public class ApiProductInfoController {
	@Resource ProductInfoService productInfoService;
	@Resource AuthService authService;

	@InitBinder("areaObj")
	public void initBinderareaObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("areaObj.");
	}
	@InitBinder("newOldLevelObj")
	public void initBindernewOldLevelObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("newOldLevelObj.");
	}
	@InitBinder("priceRegionObj")
	public void initBinderpriceRegionObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("priceRegionObj.");
	}
	@InitBinder("productClassObj")
	public void initBinderproductClassObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("productClassObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("productInfo")
	public void initBinderProductInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("productInfo.");
	}

	/*客户端ajax方式添加商品信息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(ProductInfo productInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
		
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(userName);
		
		productInfo.setUserObj(userObj);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		productInfo.setAddTime(sdf.format(new java.util.Date()));
		
        productInfoService.addProductInfo(productInfo); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新商品信息信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated ProductInfo productInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        productInfoService.updateProductInfo(productInfo);  //更新记录到数据库
        return JsonResultBuilder.ok(productInfoService.getProductInfo(productInfo.getProductId()));
	}

	/*ajax方式显示获取商品信息详细信息*/
	@RequestMapping(value="/get/{productId}",method=RequestMethod.POST)
	public JsonResult getProductInfo(@PathVariable int productId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键productId获取ProductInfo对象*/
        ProductInfo productInfo = productInfoService.getProductInfo(productId); 
        return JsonResultBuilder.ok(productInfo);
	}

	/*ajax方式删除商品信息记录*/
	@RequestMapping(value="/delete/{productId}",method=RequestMethod.POST)
	public JsonResult deleteProductInfo(@PathVariable int productId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			productInfoService.deleteProductInfo(productId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询商品信息信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(@ModelAttribute("productClassObj") ProductClass productClassObj,String productName,@ModelAttribute("priceRegionObj") PriceRegion priceRegionObj,@ModelAttribute("newOldLevelObj") NewOldLevel newOldLevelObj,@ModelAttribute("areaObj") AreaInfo areaObj,@ModelAttribute("userObj") UserInfo userObj,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (productName == null) productName = "";
		if (addTime == null) addTime = "";
		if(rows != 0)productInfoService.setRows(rows);
		List<ProductInfo> productInfoList = productInfoService.queryProductInfo(productClassObj, productName, priceRegionObj, newOldLevelObj, areaObj, userObj, addTime, page);
	    /*计算总的页数和总的记录数*/
	    productInfoService.queryTotalPageAndRecordNumber(productClassObj, productName, priceRegionObj, newOldLevelObj, areaObj, userObj, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = productInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = productInfoService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", productInfoList);
	    return JsonResultBuilder.ok(resultMap);
	}
	
	
	//客户端查询最新5条商品信息信息
	@RequestMapping(value="/newlist",method=RequestMethod.POST)
	public JsonResult newlist(Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		 
		List<ProductInfo> productInfoList = productInfoService.queryNewProductInfo();
	     
	    HashMap<String, Object> resultMap = new HashMap<String, Object>(); 
	    resultMap.put("list", productInfoList);
	    return JsonResultBuilder.ok(resultMap);
	}
	
	
	//客户端查询商品信息信息
	@RequestMapping(value="/userlist",method=RequestMethod.POST)
	public JsonResult userList(@ModelAttribute("productClassObj") ProductClass productClassObj,String productName,@ModelAttribute("priceRegionObj") PriceRegion priceRegionObj,@ModelAttribute("newOldLevelObj") NewOldLevel newOldLevelObj,@ModelAttribute("areaObj") AreaInfo areaObj,@ModelAttribute("userObj") UserInfo userObj,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (productName == null) productName = "";
		if (addTime == null) addTime = "";
		if(rows != 0)productInfoService.setRows(rows);
		
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		
		userObj = new UserInfo();
		userObj.setUser_name(userName); 
		
		List<ProductInfo> productInfoList = productInfoService.queryProductInfo(productClassObj, productName, priceRegionObj, newOldLevelObj, areaObj, userObj, addTime, page);
	    /*计算总的页数和总的记录数*/
	    productInfoService.queryTotalPageAndRecordNumber(productClassObj, productName, priceRegionObj, newOldLevelObj, areaObj, userObj, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = productInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = productInfoService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", productInfoList);
	    return JsonResultBuilder.ok(resultMap);
	}
	
	

	//客户端ajax获取所有商品信息
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<ProductInfo> productInfoList = productInfoService.queryAllProductInfo(); 
		return JsonResultBuilder.ok(productInfoList);
	}
}

