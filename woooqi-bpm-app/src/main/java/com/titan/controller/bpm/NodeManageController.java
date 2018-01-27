package com.titan.controller.bpm;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.FlowElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.bpm.ProcessDefinition;
import com.titan.entity.web.Page;
import com.titan.service.bpm.DefinitionService;
import com.titan.service.bpm.NodeManageService;

@RestController
@RequestMapping("node")
public class NodeManageController {
	
	@Autowired
	private DefinitionService definitionService;

	@Autowired
	private NodeManageService nodeMangeService;

	@ResponseBody
	@RequestMapping("getAllProcessDefinition")
	public Page<ProcessDefinition> getAllProcessDefinition(HttpServletRequest request,
			HttpServletResponse response) {
		
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
	@RequestMapping("nodeManage")
	public Page<FlowElement> nodeManage(HttpServletRequest request,
			HttpServletResponse response) {
		String definitionId = request.getParameter("definitionId");
		List<FlowElement> list = nodeMangeService.getNodesByDefinitionId(definitionId);
		Page<FlowElement> page = new Page<FlowElement>();
		page.setCode(1);
		page.setRows(list);
		page.setMsg("");
		page.setTotal(list.size());

		return page;

	}
	
	@ResponseBody
	@RequestMapping("getNodeManageByActId")
	public FlowElement getNodeManageByActId(HttpServletRequest request,
			HttpServletResponse response) {
		String definitionId = request.getParameter("definitionId");
		String actId = request.getParameter("actId");
		FlowElement flowElement = nodeMangeService.getNodesByDefinitionIdAndActId(definitionId,actId);

		return flowElement;

	}
	
	
	
	
}
