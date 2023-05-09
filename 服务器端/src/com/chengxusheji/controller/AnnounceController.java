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
import com.chengxusheji.service.AnnounceService;
import com.chengxusheji.po.Announce;

//Announce管理控制层
@Controller
@RequestMapping("/Announce")
public class AnnounceController extends BaseController {

    /*业务层对象*/
    @Resource AnnounceService announceService;

	@InitBinder("announce")
	public void initBinderAnnounce(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("announce.");
	}
	/*跳转到添加Announce视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Announce());
		return "Announce_add";
	}

	/*客户端ajax方式提交添加公告信息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Announce announce, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        announceService.addAnnounce(announce);
        message = "公告信息添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询公告信息信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String announceTitle,String announceDate,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
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
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Announce announce:announceList) {
			JSONObject jsonAnnounce = announce.getJsonObject();
			jsonArray.put(jsonAnnounce);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询公告信息信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Announce> announceList = announceService.queryAllAnnounce();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Announce announce:announceList) {
			JSONObject jsonAnnounce = new JSONObject();
			jsonAnnounce.accumulate("announceId", announce.getAnnounceId());
			jsonAnnounce.accumulate("announceTitle", announce.getAnnounceTitle());
			jsonArray.put(jsonAnnounce);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询公告信息信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String announceTitle,String announceDate,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (announceTitle == null) announceTitle = "";
		if (announceDate == null) announceDate = "";
		List<Announce> announceList = announceService.queryAnnounce(announceTitle, announceDate, currentPage);
	    /*计算总的页数和总的记录数*/
	    announceService.queryTotalPageAndRecordNumber(announceTitle, announceDate);
	    /*获取到总的页码数目*/
	    int totalPage = announceService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = announceService.getRecordNumber();
	    request.setAttribute("announceList",  announceList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("announceTitle", announceTitle);
	    request.setAttribute("announceDate", announceDate);
		return "Announce/announce_frontquery_result"; 
	}

     /*前台查询Announce信息*/
	@RequestMapping(value="/{announceId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer announceId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键announceId获取Announce对象*/
        Announce announce = announceService.getAnnounce(announceId);

        request.setAttribute("announce",  announce);
        return "Announce/announce_frontshow";
	}

	/*ajax方式显示公告信息修改jsp视图页*/
	@RequestMapping(value="/{announceId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer announceId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键announceId获取Announce对象*/
        Announce announce = announceService.getAnnounce(announceId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonAnnounce = announce.getJsonObject();
		out.println(jsonAnnounce.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新公告信息信息*/
	@RequestMapping(value = "/{announceId}/update", method = RequestMethod.POST)
	public void update(@Validated Announce announce, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			announceService.updateAnnounce(announce);
			message = "公告信息更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "公告信息更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除公告信息信息*/
	@RequestMapping(value="/{announceId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer announceId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  announceService.deleteAnnounce(announceId);
	            request.setAttribute("message", "公告信息删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "公告信息删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条公告信息记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String announceIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = announceService.deleteAnnounces(announceIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出公告信息信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String announceTitle,String announceDate, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(announceTitle == null) announceTitle = "";
        if(announceDate == null) announceDate = "";
        List<Announce> announceList = announceService.queryAnnounce(announceTitle,announceDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Announce信息记录"; 
        String[] headers = { "公告编号","公告标题","发布日期"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<announceList.size();i++) {
        	Announce announce = announceList.get(i); 
        	dataset.add(new String[]{announce.getAnnounceId() + "",announce.getAnnounceTitle(),announce.getAnnounceDate()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Announce.xls");//filename是下载的xls的名，建议最好用英文 
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
