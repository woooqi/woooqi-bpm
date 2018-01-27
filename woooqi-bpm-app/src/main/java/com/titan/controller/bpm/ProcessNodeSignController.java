package com.titan.controller.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.manage.ProcessNodeSign;
import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;
import com.titan.entity.organization.Dept;
import com.titan.entity.organization.Post;
import com.titan.entity.organization.Role;
import com.titan.entity.organization.User;
import com.titan.entity.web.Page;
import com.titan.service.bpm.ProcessNodeSignService;
import com.titan.utils.JsonUtils;


@RestController
@RequestMapping("processNodeSign")
public class ProcessNodeSignController {

	@Autowired
	private ProcessNodeSignService processNodeSignService;
	
	@RequestMapping("saveProcessNodeSign")
	@ResponseBody
	public Map<String,Object> saveNodeManageAssign(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> map =new HashMap<String, Object>();
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		String definitionId = request.getParameter("definitionId")==null?"":request.getParameter("definitionId");
		String decisionType = request.getParameter("decisionType")==null?"":request.getParameter("decisionType");
		String voteType = request.getParameter("voteType")==null?"":request.getParameter("voteType");
		String ballot = request.getParameter("ballot")==null?"":request.getParameter("ballot");
		ProcessNodeSign processNodeSign = new ProcessNodeSign();
		processNodeSign.setActivitiId(activitiId);
		processNodeSign.setBallot(ballot);
		processNodeSign.setDecision_type(decisionType);
		processNodeSign.setDefinitionId(definitionId);
		processNodeSign.setId(id);
		processNodeSign.setVote_type(voteType);
		map  = processNodeSignService.savaAndFlushProcessNodeSign(processNodeSign);
		return map;
	}
	
	
	@RequestMapping("saveProcessNodeSignSpecial")
	@ResponseBody
	public Map<String,Object> saveProcessNodeSignSpecial(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> map =new HashMap<String, Object>();
		String signId = request.getParameter("signId")==null?"":request.getParameter("signId");
		String type = request.getParameter("type")==null?"":request.getParameter("type");
		String signGroups = request.getParameter("signGroups")==null?"":request.getParameter("signGroups");
		
		List<Map<String,Object>> signSpecial = null;
		if(StringUtils.isNotEmpty(signGroups)){
			signSpecial = JsonUtils.jsonToList(signGroups);
		}
		map  = processNodeSignService.savaAndFlushProcessNodeSignSpecial(signId,type, signSpecial);
		return map;
	}
	

	
	@RequestMapping("getProcessNodeSignByActId")
	@ResponseBody
	public Map<String,Object> getNodeManageNoticeByActId(HttpServletRequest request,HttpServletResponse response){
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		Map<String,Object> map = processNodeSignService.getProcessNodeSignByActId(activitiId);
		
		
		return map;
	}
	
	
	@RequestMapping("getUserSignByActId")
	@ResponseBody
	public Page<User> getUserSignByActId(HttpServletRequest request,HttpServletResponse response){
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		Page<User> page = processNodeSignService.getUserSignByActId(activitiId);
		
		
		return page;
	}

	@RequestMapping("getRoleSignByActId")
	@ResponseBody
	public Page<Role> getRoleSignByActId(HttpServletRequest request,HttpServletResponse response){
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		Page<Role> page = processNodeSignService.getRoleSignByActId(activitiId);
		
		
		return page;
	}

	@RequestMapping("getDeptSignByActId")
	@ResponseBody
	public Page<Dept> getDeptSignByActId(HttpServletRequest request,HttpServletResponse response){
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		Page<Dept> page = processNodeSignService.getDeptSignByActId(activitiId);

		return page;
	}

	@RequestMapping("getPostSignByActId")
	@ResponseBody
	public Page<Post> getPostSignByActId(HttpServletRequest request,HttpServletResponse response){
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		Page<Post> page = processNodeSignService.getPostSignByActId(activitiId);
		return page;
	}
	
	@RequestMapping("getProcessNodeSignSpecialBySign")
	@ResponseBody
	public List<ProcessNodeSignSpecial> getProcessNodeSignSpecialBySign(HttpServletRequest request, HttpServletResponse response){
		String signId = request.getParameter("signId")==null?"":request.getParameter("signId");
		List<ProcessNodeSignSpecial> lists = processNodeSignService.getProcessNodeSignSpecialBySign(signId);
		return lists;
	}
 
	@RequestMapping("getGroupBySpecialId")
	@ResponseBody
	public Map<String,Object> getGroupBySpecialId(HttpServletRequest request,HttpServletResponse response){
		String specialId = request.getParameter("specialId")==null?"":request.getParameter("specialId");
		Map<String,Object> map = processNodeSignService.getGroupBySignSpecialId(specialId);
		return map;
				
	} 



}
