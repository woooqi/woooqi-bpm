package com.woooqi.bpm.controller.bpm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.bpm.bpm.ProcessDefinition;
import com.woooqi.bpm.entity.bpm.manage.ProcessManage;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.bpm.DefinitionService;
import com.woooqi.bpm.service.bpm.DeployService;
import com.woooqi.bpm.service.bpm.ProcessManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.titan.entity.bpm.bpm.ProcessDefinition;
import com.titan.entity.bpm.manage.ProcessManage;
import com.titan.entity.web.Page;
import com.titan.service.bpm.DefinitionService;
import com.titan.service.bpm.DeployService;
import com.titan.service.bpm.ProcessManageService;

@RestController
@RequestMapping("process")
public class ProcessManageController {

	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private ProcessManageService processManageService;
	
	@Autowired
	private DeployService deployService;
	
	
	@ResponseBody
	@RequestMapping("getAllProcessDefinition")
	public Page<ProcessDefinition> getAllProcessDefinition(HttpServletRequest request, HttpServletResponse response){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		
		List<ProcessDefinition> list = definitionService.getDefinitionByName(name);
		Page<ProcessDefinition> page = new Page<ProcessDefinition>();
		page.setCode(1);
		page.setRows(list);
		page.setMsg("");
		page.setTotal(list.size());
		
		return page;
		
		
	}
	
	 @ResponseBody
	 @RequestMapping("getProcessDefinition")
	 public  Page<ProcessDefinition> edit(HttpServletRequest request, HttpServletResponse response,ModelAndView mav) {
		    String definitionId = request.getParameter("definitionId");
		    ProcessDefinition processDefinition = definitionService.getDefinitionByDefinitionId(definitionId);
			
			List<ProcessDefinition> list = new ArrayList<ProcessDefinition>();
			list.add(processDefinition);

			Page<ProcessDefinition> page = new Page<ProcessDefinition>();
			page.setCode(1);
			page.setRows(list);
			page.setMsg("");
			page.setTotal(list.size());
			
			return page;
	 }
	 
	 @ResponseBody
	 @RequestMapping("getByDefinitionId")
	 public Map<String,Object> getByDefinitionId(HttpServletRequest request, HttpServletResponse response){
		 Map<String,Object> map = new HashMap<String, Object>();
		 String definitionId = request.getParameter("definitionId");
		 ProcessManage processMange = processManageService.findByDefinitionId(definitionId);
		 ProcessDefinition processDefinition = definitionService.getDefinitionByDefinitionId(definitionId);
		 
		 if(processMange==null || processMange.getName()==null){
			 processMange = new ProcessManage();
			 processMange.setName(processDefinition.getName_());
			
		 }
		 processMange.setCategory(processDefinition.getCategory_());
		 map.put("code", 1);
		 map.put("rows", processMange);
		 
		 return map; 
	 }
	 
	 @ResponseBody
	 @RequestMapping("saveAndFlushProcessManage")
	 public Map<String,Object> saveAndFlushProcessManage(HttpServletRequest request, HttpServletResponse response){
		 Map<String,Object> map = new HashMap<String, Object>();
		 String id = request.getParameter("id");
		 String definitionId = request.getParameter("definitionId");
		 String name = request.getParameter("name");
		 String category = request.getParameter("category");
		 String description = request.getParameter("description");
		 String startEventClass = request.getParameter("startEventClass");
		 String endEventClass = request.getParameter("endEventClass");
		 ProcessManage processManage = new ProcessManage();
		 ProcessDefinition processDefinition = definitionService.getDefinitionByDefinitionId(definitionId);
		 deployService.setDeployGategory(processDefinition.getDeployment_id_(), category);
		 processManage.setCategory(category);
		 processManage.setDefinitionId(definitionId);
		 processManage.setDescription(description);
		 processManage.setEndEventClass(endEventClass);
		 processManage.setStartEventClass(startEventClass);
		 processManage.setName(name);
		 processManage.setId(id);
		 map= processManageService.saveAndFlushProcessManage(processManage);
		 return map; 
	 }


}
