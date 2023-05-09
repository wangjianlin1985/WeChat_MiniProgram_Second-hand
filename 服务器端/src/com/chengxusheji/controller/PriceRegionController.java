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
import com.chengxusheji.service.PriceRegionService;
import com.chengxusheji.po.PriceRegion;

//PriceRegion管理控制层
@Controller
@RequestMapping("/PriceRegion")
public class PriceRegionController extends BaseController {

    /*业务层对象*/
    @Resource PriceRegionService priceRegionService;

	@InitBinder("priceRegion")
	public void initBinderPriceRegion(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("priceRegion.");
	}
	/*跳转到添加PriceRegion视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new PriceRegion());
		return "PriceRegion_add";
	}

	/*客户端ajax方式提交添加价格区间信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated PriceRegion priceRegion, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        priceRegionService.addPriceRegion(priceRegion);
        message = "价格区间添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询价格区间信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)priceRegionService.setRows(rows);
		List<PriceRegion> priceRegionList = priceRegionService.queryPriceRegion(page);
	    /*计算总的页数和总的记录数*/
	    priceRegionService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = priceRegionService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = priceRegionService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(PriceRegion priceRegion:priceRegionList) {
			JSONObject jsonPriceRegion = priceRegion.getJsonObject();
			jsonArray.put(jsonPriceRegion);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询价格区间信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<PriceRegion> priceRegionList = priceRegionService.queryAllPriceRegion();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(PriceRegion priceRegion:priceRegionList) {
			JSONObject jsonPriceRegion = new JSONObject();
			jsonPriceRegion.accumulate("regionId", priceRegion.getRegionId());
			jsonPriceRegion.accumulate("regionName", priceRegion.getRegionName());
			jsonArray.put(jsonPriceRegion);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询价格区间信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<PriceRegion> priceRegionList = priceRegionService.queryPriceRegion(currentPage);
	    /*计算总的页数和总的记录数*/
	    priceRegionService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = priceRegionService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = priceRegionService.getRecordNumber();
	    request.setAttribute("priceRegionList",  priceRegionList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "PriceRegion/priceRegion_frontquery_result"; 
	}

     /*前台查询PriceRegion信息*/
	@RequestMapping(value="/{regionId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer regionId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键regionId获取PriceRegion对象*/
        PriceRegion priceRegion = priceRegionService.getPriceRegion(regionId);

        request.setAttribute("priceRegion",  priceRegion);
        return "PriceRegion/priceRegion_frontshow";
	}

	/*ajax方式显示价格区间修改jsp视图页*/
	@RequestMapping(value="/{regionId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer regionId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键regionId获取PriceRegion对象*/
        PriceRegion priceRegion = priceRegionService.getPriceRegion(regionId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonPriceRegion = priceRegion.getJsonObject();
		out.println(jsonPriceRegion.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新价格区间信息*/
	@RequestMapping(value = "/{regionId}/update", method = RequestMethod.POST)
	public void update(@Validated PriceRegion priceRegion, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			priceRegionService.updatePriceRegion(priceRegion);
			message = "价格区间更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "价格区间更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除价格区间信息*/
	@RequestMapping(value="/{regionId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer regionId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  priceRegionService.deletePriceRegion(regionId);
	            request.setAttribute("message", "价格区间删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "价格区间删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条价格区间记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String regionIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = priceRegionService.deletePriceRegions(regionIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出价格区间信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<PriceRegion> priceRegionList = priceRegionService.queryPriceRegion();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "PriceRegion信息记录"; 
        String[] headers = { "记录编号","价格区间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<priceRegionList.size();i++) {
        	PriceRegion priceRegion = priceRegionList.get(i); 
        	dataset.add(new String[]{priceRegion.getRegionId() + "",priceRegion.getRegionName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"PriceRegion.xls");//filename是下载的xls的名，建议最好用英文 
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
