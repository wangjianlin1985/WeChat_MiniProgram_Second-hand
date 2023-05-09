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
import com.chengxusheji.service.NewOldLevelService;
import com.chengxusheji.po.NewOldLevel;

//NewOldLevel管理控制层
@Controller
@RequestMapping("/NewOldLevel")
public class NewOldLevelController extends BaseController {

    /*业务层对象*/
    @Resource NewOldLevelService newOldLevelService;

	@InitBinder("newOldLevel")
	public void initBinderNewOldLevel(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("newOldLevel.");
	}
	/*跳转到添加NewOldLevel视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new NewOldLevel());
		return "NewOldLevel_add";
	}

	/*客户端ajax方式提交添加新旧程度信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated NewOldLevel newOldLevel, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        newOldLevelService.addNewOldLevel(newOldLevel);
        message = "新旧程度添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询新旧程度信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)newOldLevelService.setRows(rows);
		List<NewOldLevel> newOldLevelList = newOldLevelService.queryNewOldLevel(page);
	    /*计算总的页数和总的记录数*/
	    newOldLevelService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = newOldLevelService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = newOldLevelService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(NewOldLevel newOldLevel:newOldLevelList) {
			JSONObject jsonNewOldLevel = newOldLevel.getJsonObject();
			jsonArray.put(jsonNewOldLevel);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询新旧程度信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<NewOldLevel> newOldLevelList = newOldLevelService.queryAllNewOldLevel();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(NewOldLevel newOldLevel:newOldLevelList) {
			JSONObject jsonNewOldLevel = new JSONObject();
			jsonNewOldLevel.accumulate("levelId", newOldLevel.getLevelId());
			jsonNewOldLevel.accumulate("levelName", newOldLevel.getLevelName());
			jsonArray.put(jsonNewOldLevel);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询新旧程度信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<NewOldLevel> newOldLevelList = newOldLevelService.queryNewOldLevel(currentPage);
	    /*计算总的页数和总的记录数*/
	    newOldLevelService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = newOldLevelService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = newOldLevelService.getRecordNumber();
	    request.setAttribute("newOldLevelList",  newOldLevelList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "NewOldLevel/newOldLevel_frontquery_result"; 
	}

     /*前台查询NewOldLevel信息*/
	@RequestMapping(value="/{levelId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer levelId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键levelId获取NewOldLevel对象*/
        NewOldLevel newOldLevel = newOldLevelService.getNewOldLevel(levelId);

        request.setAttribute("newOldLevel",  newOldLevel);
        return "NewOldLevel/newOldLevel_frontshow";
	}

	/*ajax方式显示新旧程度修改jsp视图页*/
	@RequestMapping(value="/{levelId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer levelId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键levelId获取NewOldLevel对象*/
        NewOldLevel newOldLevel = newOldLevelService.getNewOldLevel(levelId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonNewOldLevel = newOldLevel.getJsonObject();
		out.println(jsonNewOldLevel.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新新旧程度信息*/
	@RequestMapping(value = "/{levelId}/update", method = RequestMethod.POST)
	public void update(@Validated NewOldLevel newOldLevel, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			newOldLevelService.updateNewOldLevel(newOldLevel);
			message = "新旧程度更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "新旧程度更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除新旧程度信息*/
	@RequestMapping(value="/{levelId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer levelId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  newOldLevelService.deleteNewOldLevel(levelId);
	            request.setAttribute("message", "新旧程度删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "新旧程度删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条新旧程度记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String levelIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = newOldLevelService.deleteNewOldLevels(levelIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出新旧程度信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<NewOldLevel> newOldLevelList = newOldLevelService.queryNewOldLevel();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "NewOldLevel信息记录"; 
        String[] headers = { "记录编号","新旧程度"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<newOldLevelList.size();i++) {
        	NewOldLevel newOldLevel = newOldLevelList.get(i); 
        	dataset.add(new String[]{newOldLevel.getLevelId() + "",newOldLevel.getLevelName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"NewOldLevel.xls");//filename是下载的xls的名，建议最好用英文 
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
