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

import com.titan.entity.bpm.bpm.ProcessDefinition;
import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.web.Page;
import com.titan.service.bpm.AuthorizeService;
import com.titan.service.bpm.DefinitionService;
import com.titan.utils.JsonUtils;

@RestController
@RequestMapping("authorize")
public class AuthorizeController {
	
	@Autowired
	private AuthorizeService authorizeService;
	@Autowired
	private DefinitionService definitionService;

	
	@ResponseBody
	@RequestMapping("getAllDefinition")
	public Page<ProcessDefinition> getAllDefinition(HttpServletRequest request,HttpServletResponse response){
		String parentId = request.getParameter("parentId")==null?"":request.getParameter("parentId");
		List<ProcessDefinition> list = definitionService.getDefinitionByCategoryId(parentId);
		Page<ProcessDefinition> page = new Page<ProcessDefinition>();
		page.setCode(1);
		page.setRows(list);
		page.setMsg("");
		page.setTotal(list.size());
		
		return page;
		
	}
	
	@RequestMapping("saveAuthorization")
	@ResponseBody
	public Map<String,Object> saveAuthorization(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map =new HashMap<String, Object>();
		String definitionId = request.getParameter("definitionId")==null?"":request.getParameter("definitionId");
		String assignGroups = request.getParameter("assignGroups")==null?"":request.getParameter("assignGroups");
		List<Map<String,Object>> groups = null;
		if(StringUtils.isNotEmpty(assignGroups)){
			groups = JsonUtils.jsonToList(assignGroups);
		}
		try {
			authorizeService.saveAuthorization(definitionId,groups);
			map.put("code", 1);
		} catch (Exception e) {
			map.put("code", -1);
		}
		
		return map;
	}
	
	@RequestMapping("getGroupByDefinitionId")
	@ResponseBody
	public Map<String,Object> getGroupBydefinitionId(HttpServletRequest request,HttpServletResponse response){
		String definitionId = request.getParameter("definitionId")==null?"":request.getParameter("definitionId");
		Map<String,Object> map = authorizeService.getGroupByDefinitionId(definitionId);
		return map;
				
	} 
}
