package com.titan.controller.bpm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.service.bpm.ProcessNodeAssignService;
import com.titan.utils.JsonUtils;

@RestController
@RequestMapping("processNodeAssign")
public class ProcessNodeAssignController {

	@Autowired
	private ProcessNodeAssignService processNodeAssignService;
	
	@RequestMapping("saveProcessNodeAssign")
	@ResponseBody
	public Map<String,Object> saveNodeManageAssign(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map =new HashMap<String, Object>();
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		String type = request.getParameter("type")==null?"":request.getParameter("type");
		String policy = request.getParameter("policy")==null?"":request.getParameter("policy");
		String definitionId = request.getParameter("definitionId")==null?"":request.getParameter("definitionId");
		String initiator = request.getParameter("initiator")==null?"0":request.getParameter("initiator");
		
		String userIds = request.getParameter("userIds")==null?"":request.getParameter("userIds");
		List<String> users = new ArrayList<String>();
		Set<String> sets = new HashSet<String>();
		if(StringUtils.isNotEmpty(userIds)){
			users= Arrays.asList(userIds.split(","));
			sets.addAll(users);
		}
		
		ProcessNodeAssign processNodeAssign = new ProcessNodeAssign();
		processNodeAssign.setId(id);
		processNodeAssign.setActivitiId(activitiId);
		processNodeAssign.setDefinitionId(definitionId);
		processNodeAssign.setInitiator(initiator);
		//processNodeAssign.setType(type);
		processNodeAssign.setPolicy(policy);
		map  = processNodeAssignService.saveNodeManageAssign(processNodeAssign ,type , sets);
		return map;
	}
	
	
	
	@RequestMapping("saveGroupProcessNodeAssign")
	@ResponseBody
	public Map<String,Object> saveGroupProcessNodeAssign(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map =new HashMap<String, Object>();
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		String type = request.getParameter("type")==null?"":request.getParameter("type");
		String policy = request.getParameter("policy")==null?"":request.getParameter("policy");
		String initiator = request.getParameter("initiator")==null?"0":request.getParameter("initiator");
		String definitionId = request.getParameter("definitionId")==null?"":request.getParameter("definitionId");
		String assignGroups = request.getParameter("assignGroups")==null?"":request.getParameter("assignGroups");
		List<Map<String,Object>> groups = null;
		if(StringUtils.isNotEmpty(assignGroups)){
			groups = JsonUtils.jsonToList(assignGroups);
		}
		
		ProcessNodeAssign processNodeAssign = new ProcessNodeAssign();
		processNodeAssign.setId(id);
		processNodeAssign.setActivitiId(activitiId);
		processNodeAssign.setDefinitionId(definitionId);
		processNodeAssign.setInitiator(initiator);
		//processNodeAssign.setType(type);
		processNodeAssign.setPolicy(policy);
		map  = processNodeAssignService.saveNodeManageGroupAssign(processNodeAssign,type,groups);
		return map;
	}
	
	
	@RequestMapping("getNodeManageAssignByActId")
	@ResponseBody
	public ProcessNodeAssign getNodeManageAssignByActId(HttpServletRequest request,HttpServletResponse response){
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		
		ProcessNodeAssign processNodeAssign  = processNodeAssignService.getNodeManageAssignByActId(activitiId);
		return processNodeAssign;
	}
	
	
	@RequestMapping("getGroupByAssignId")
	@ResponseBody
	public Map<String,Object> getGroupByAssignId(HttpServletRequest request,HttpServletResponse response){
		String typeId = request.getParameter("typeId")==null?"":request.getParameter("typeId");
		Map<String,Object> map = processNodeAssignService.getGroupByAssignTypeId(typeId);
		return map;
				
	} 
	
	@RequestMapping("getProcessNodeAssignTypeByAssign")
	@ResponseBody
	public List<ProcessNodeAssignType> getProcessNodeAssignTypeByAssign(HttpServletRequest request, HttpServletResponse response){
		String assignId = request.getParameter("assignId")==null?"":request.getParameter("assignId");
		List<ProcessNodeAssignType> lists = processNodeAssignService.getProcessNodeAssignTypeByAssign(assignId);
		return lists;
	}
 


}
