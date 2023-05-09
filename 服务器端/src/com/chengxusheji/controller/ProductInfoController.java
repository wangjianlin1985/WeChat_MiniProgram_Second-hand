package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.ProductInfoService;
import com.chengxusheji.po.ProductInfo;
import com.chengxusheji.service.AreaInfoService;
import com.chengxusheji.po.AreaInfo;
import com.chengxusheji.service.NewOldLevelService;
import com.chengxusheji.po.NewOldLevel;
import com.chengxusheji.service.PriceRegionService;
import com.chengxusheji.po.PriceRegion;
import com.chengxusheji.service.ProductClassService;
import com.chengxusheji.po.ProductClass;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//ProductInfo管理控制层
@Controller
@RequestMapping("/ProductInfo")
public class ProductInfoController extends BaseController {

    /*业务层对象*/
    @Resource ProductInfoService productInfoService;

    @Resource AreaInfoService areaInfoService;
    @Resource NewOldLevelService newOldLevelService;
    @Resource PriceRegionService priceRegionService;
    @Resource ProductClassService productClassService;
    @Resource UserInfoService userInfoService;
	@InitBinder("productClassObj")
	public void initBinderproductClassObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("productClassObj.");
	}
	@InitBinder("priceRegionObj")
	public void initBinderpriceRegionObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("priceRegionObj.");
	}
	@InitBinder("newOldLevelObj")
	public void initBindernewOldLevelObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("newOldLevelObj.");
	}
	@InitBinder("areaObj")
	public void initBinderareaObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("areaObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("productInfo")
	public void initBinderProductInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("productInfo.");
	}
	/*跳转到添加ProductInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new ProductInfo());
		/*查询所有的AreaInfo信息*/
		List<AreaInfo> areaInfoList = areaInfoService.queryAllAreaInfo();
		request.setAttribute("areaInfoList", areaInfoList);
		/*查询所有的NewOldLevel信息*/
		List<NewOldLevel> newOldLevelList = newOldLevelService.queryAllNewOldLevel();
		request.setAttribute("newOldLevelList", newOldLevelList);
		/*查询所有的PriceRegion信息*/
		List<PriceRegion> priceRegionList = priceRegionService.queryAllPriceRegion();
		request.setAttribute("priceRegionList", priceRegionList);
		/*查询所有的ProductClass信息*/
		List<ProductClass> productClassList = productClassService.queryAllProductClass();
		request.setAttribute("productClassList", productClassList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "ProductInfo_add";
	}

	/*客户端ajax方式提交添加商品信息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated ProductInfo productInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			productInfo.setProductPhoto(this.handlePhotoUpload(request, "productPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        productInfoService.addProductInfo(productInfo);
        message = "商品信息添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询商品信息信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("productClassObj") ProductClass productClassObj,String productName,@ModelAttribute("priceRegionObj") PriceRegion priceRegionObj,@ModelAttribute("newOldLevelObj") NewOldLevel newOldLevelObj,@ModelAttribute("areaObj") AreaInfo areaObj,@ModelAttribute("userObj") UserInfo userObj,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
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
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(ProductInfo productInfo:productInfoList) {
			JSONObject jsonProductInfo = productInfo.getJsonObject();
			jsonArray.put(jsonProductInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询商品信息信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<ProductInfo> productInfoList = productInfoService.queryAllProductInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(ProductInfo productInfo:productInfoList) {
			JSONObject jsonProductInfo = new JSONObject();
			jsonProductInfo.accumulate("productId", productInfo.getProductId());
			jsonProductInfo.accumulate("productName", productInfo.getProductName());
			jsonArray.put(jsonProductInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询商品信息信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("productClassObj") ProductClass productClassObj,String productName,@ModelAttribute("priceRegionObj") PriceRegion priceRegionObj,@ModelAttribute("newOldLevelObj") NewOldLevel newOldLevelObj,@ModelAttribute("areaObj") AreaInfo areaObj,@ModelAttribute("userObj") UserInfo userObj,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (productName == null) productName = "";
		if (addTime == null) addTime = "";
		List<ProductInfo> productInfoList = productInfoService.queryProductInfo(productClassObj, productName, priceRegionObj, newOldLevelObj, areaObj, userObj, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    productInfoService.queryTotalPageAndRecordNumber(productClassObj, productName, priceRegionObj, newOldLevelObj, areaObj, userObj, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = productInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = productInfoService.getRecordNumber();
	    request.setAttribute("productInfoList",  productInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("productClassObj", productClassObj);
	    request.setAttribute("productName", productName);
	    request.setAttribute("priceRegionObj", priceRegionObj);
	    request.setAttribute("newOldLevelObj", newOldLevelObj);
	    request.setAttribute("areaObj", areaObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("addTime", addTime);
	    List<AreaInfo> areaInfoList = areaInfoService.queryAllAreaInfo();
	    request.setAttribute("areaInfoList", areaInfoList);
	    List<NewOldLevel> newOldLevelList = newOldLevelService.queryAllNewOldLevel();
	    request.setAttribute("newOldLevelList", newOldLevelList);
	    List<PriceRegion> priceRegionList = priceRegionService.queryAllPriceRegion();
	    request.setAttribute("priceRegionList", priceRegionList);
	    List<ProductClass> productClassList = productClassService.queryAllProductClass();
	    request.setAttribute("productClassList", productClassList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "ProductInfo/productInfo_frontquery_result"; 
	}

     /*前台查询ProductInfo信息*/
	@RequestMapping(value="/{productId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer productId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键productId获取ProductInfo对象*/
        ProductInfo productInfo = productInfoService.getProductInfo(productId);

        List<AreaInfo> areaInfoList = areaInfoService.queryAllAreaInfo();
        request.setAttribute("areaInfoList", areaInfoList);
        List<NewOldLevel> newOldLevelList = newOldLevelService.queryAllNewOldLevel();
        request.setAttribute("newOldLevelList", newOldLevelList);
        List<PriceRegion> priceRegionList = priceRegionService.queryAllPriceRegion();
        request.setAttribute("priceRegionList", priceRegionList);
        List<ProductClass> productClassList = productClassService.queryAllProductClass();
        request.setAttribute("productClassList", productClassList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("productInfo",  productInfo);
        return "ProductInfo/productInfo_frontshow";
	}

	/*ajax方式显示商品信息修改jsp视图页*/
	@RequestMapping(value="/{productId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer productId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键productId获取ProductInfo对象*/
        ProductInfo productInfo = productInfoService.getProductInfo(productId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonProductInfo = productInfo.getJsonObject();
		out.println(jsonProductInfo.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新商品信息信息*/
	@RequestMapping(value = "/{productId}/update", method = RequestMethod.POST)
	public void update(@Validated ProductInfo productInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String productPhotoFileName = this.handlePhotoUpload(request, "productPhotoFile");
		if(!productPhotoFileName.equals("upload/NoImage.jpg"))productInfo.setProductPhoto(productPhotoFileName); 


		try {
			productInfoService.updateProductInfo(productInfo);
			message = "商品信息更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品信息更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除商品信息信息*/
	@RequestMapping(value="/{productId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer productId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  productInfoService.deleteProductInfo(productId);
	            request.setAttribute("message", "商品信息删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "商品信息删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条商品信息记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String productIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = productInfoService.deleteProductInfos(productIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出商品信息信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("productClassObj") ProductClass productClassObj,String productName,@ModelAttribute("priceRegionObj") PriceRegion priceRegionObj,@ModelAttribute("newOldLevelObj") NewOldLevel newOldLevelObj,@ModelAttribute("areaObj") AreaInfo areaObj,@ModelAttribute("userObj") UserInfo userObj,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(productName == null) productName = "";
        if(addTime == null) addTime = "";
        List<ProductInfo> productInfoList = productInfoService.queryProductInfo(productClassObj,productName,priceRegionObj,newOldLevelObj,areaObj,userObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "ProductInfo信息记录"; 
        String[] headers = { "商品编号","商品类别","商品名称","商品价格","成色","区域","商品图片","联系人","联系电话","发布人","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<productInfoList.size();i++) {
        	ProductInfo productInfo = productInfoList.get(i); 
        	dataset.add(new String[]{productInfo.getProductId() + "",productInfo.getProductClassObj().getProductClassName(),productInfo.getProductName(),productInfo.getPrice() + "",productInfo.getNewOldLevelObj().getLevelName(),productInfo.getAreaObj().getAreaName(),productInfo.getProductPhoto(),productInfo.getConnectPerson(),productInfo.getConnectPhone(),productInfo.getUserObj().getRealname(),productInfo.getAddTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"ProductInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
