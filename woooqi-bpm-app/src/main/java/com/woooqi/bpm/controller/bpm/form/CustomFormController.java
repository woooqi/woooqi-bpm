package com.woooqi.bpm.controller.bpm.form;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.bpm.form.CustomForm;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.bpm.form.CustomFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.titan.entity.bpm.form.CustomForm;
import com.titan.entity.web.Page;
import com.titan.service.bpm.form.CustomFormService;

@RestController
@RequestMapping("customForm")
public class CustomFormController {
	
	@Autowired
	private CustomFormService customFormService;
	
	@RequestMapping("/saveForm")
	@ResponseBody
	public Map<String,Object> saveForm(HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr,ModelAndView model) {
	    	String formId = request.getParameter("formId")==null?"":request.getParameter("formId");
	    	String name =request.getParameter("name")==null?"":request.getParameter("name");
	    	String description =request.getParameter("description")==null?"":request.getParameter("description");
	    	Integer columns =Integer.parseInt(request.getParameter("columns")==null?"1":request.getParameter("columns"));
	    	String tableId =request.getParameter("tableId")==null?"":request.getParameter("tableId");
	    	//String html = request.getParameter("html")==null?"":request.getParameter("html");
	    	Map<String,Object> map = customFormService.saveForm(formId,name, description,columns,tableId);
	    	return map;
		  
	}
	
	
	
	@RequestMapping("/saveFormHtml")
	@ResponseBody
	public Map<String,Object> saveFormHtml(HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr,ModelAndView model) {
	    	String html = request.getParameter("html")==null?"":request.getParameter("html");
	    	String formId = request.getParameter("formId")==null?"":request.getParameter("formId");
	    	Map<String,Object> map = customFormService.saveFormHtml(formId, html);
	    	return map;
		  
	}
	 

	
	@RequestMapping("getCustomFormById")
	@ResponseBody
	public CustomForm getCustomFormById(HttpServletRequest request, HttpServletResponse response){
		String formId = request.getParameter("formId")==null?"":request.getParameter("formId");
		CustomForm form = customFormService.getCustomFormById(formId);
		return form;
	}
	
	
	
	@RequestMapping("getCustomFormHtmlById")
	@ResponseBody
	public Map<String,Object> getCustomFormHtmlById(HttpServletRequest request,HttpServletResponse response){
		String formId = request.getParameter("formId")==null?"":request.getParameter("formId");
		Map<String,Object> map = customFormService.getCustomFormHtmlById(formId);
		return map;
	}
		
	
	@RequestMapping("getAllCustomForm")
	@ResponseBody
	public Page<CustomForm> getAllCustomForm(HttpServletRequest request, HttpServletResponse response){
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "0" : request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "20" : request.getParameter("pageSize"));
		List<CustomForm> customForms = customFormService.getAllCustomForm(pageNumber, pageSize);
		Page<CustomForm> page = new Page<>();
		page.setCode(1);
		page.setRows(customForms);
		page.setTotal(customForms.size());
		return page;
				
	} 
	
	@RequestMapping("delCustomForm")
	@ResponseBody
	public Map<String,Object> delCustomForm(HttpServletRequest request, HttpServletResponse response){
		String formId = request.getParameter("formId")==null?"":request.getParameter("formId");
		Map<String, Object> map = customFormService.delCustomForm(formId);
		return map;
	}
	
	@RequestMapping("/createForm")
	public ModelAndView createModel(HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr,ModelAndView model) {
	    try {
	    	String formId = request.getParameter("formId")==null?"":request.getParameter("formId");
	    	String name =request.getParameter("name")==null?"":request.getParameter("name");
	    	String description =request.getParameter("description")==null?"":request.getParameter("description");
	    	Integer columns =Integer.parseInt(request.getParameter("columns")==null?"1":request.getParameter("columns"));
	    	String tableId =request.getParameter("tableId")==null?"":request.getParameter("tableId");
	    	String id = customFormService.createForm(formId,name, description,columns,tableId);
	        model.setViewName("redirect:/flowable/modeler.html?modelId="+id +"&key="+id);
		    } catch (Exception e) {
		        e.printStackTrace();
		     }
				return model;
	}
	 


}
