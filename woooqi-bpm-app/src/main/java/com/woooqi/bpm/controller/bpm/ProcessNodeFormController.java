package com.woooqi.bpm.controller.bpm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeForm;
import com.woooqi.bpm.service.bpm.ProcessNodeFormService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.manage.ProcessNodeForm;
import com.titan.service.bpm.ProcessNodeFormService;

@RestController
@RequestMapping("processNodeForm")
public class ProcessNodeFormController {

	@Autowired
	private ProcessNodeFormService processNodeFormService;
	
	@RequestMapping("updateProcessNodeForm")
	@ResponseBody
	public Map<String,Object> updateProcessNodeForm(HttpServletRequest request,HttpServletResponse response){
		   Map<String,Object> map = new HashMap<String, Object>();
		   	String id = request.getParameter("id")==null?"":request.getParameter("id");
		   	String definitionId = request.getParameter("definitionId")==null?"":request.getParameter("definitionId");
			String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
			String type = request.getParameter("type")==null?"":request.getParameter("type");
			String url = request.getParameter("url")==null?"":request.getParameter("url");
			String urlInfo = request.getParameter("urlInfo")==null?"":request.getParameter("urlInfo");
			String beforeFunction = request.getParameter("beforeFunction")==null?"":request.getParameter("beforeFunction");
			String afterFunction = request.getParameter("afterFunction")==null?"":request.getParameter("afterFunction");
			String buttonIds = request.getParameter("buttonIds")==null?"":request.getParameter("buttonIds");
			List<String> buttons = new ArrayList<String>();
			if(StringUtils.isNotEmpty(buttonIds)){
				buttons= Arrays.asList(buttonIds.split(","));
			}
			ProcessNodeForm processNodeForm = new ProcessNodeForm();
			processNodeForm.setActivitiId(activitiId);
			processNodeForm.setAfterFunction(afterFunction);
			processNodeForm.setBeforeFunction(beforeFunction);
			processNodeForm.setDefinitionId(definitionId);
			processNodeForm.setId(id);
			processNodeForm.setType(type);
			processNodeForm.setUrl(url);
			processNodeForm.setUrlInfo(urlInfo);
			try {
				map = processNodeFormService.saveProcessNodeForm(processNodeForm,buttons);
				map.put("code", 1);
			} catch (Exception e) {
				map.put("code", 0);
				map.put("msg","操作失败");
			}
		   
		   return map;
	}
	
	@RequestMapping("getProcessNodeFormByActId")
	@ResponseBody
	public ProcessNodeForm getProcessNodeFormByActId(HttpServletRequest request,HttpServletResponse response){
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		ProcessNodeForm processNodeForm  = processNodeFormService.getProcessNodeFormByActivitiId(activitiId);
		return processNodeForm;
	}
	
	@RequestMapping("getFormByActId")
	@ResponseBody
	public Map<String,Object> getFormByActId(HttpServletRequest request,HttpServletResponse response){
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		Map<String,Object> map = processNodeFormService.getFormByActivitiId(activitiId);
		return map;
	}




}
