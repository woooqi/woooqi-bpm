package com.titan.controller.bpm;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.FlowElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.manage.ProcessFlow;
import com.titan.entity.web.Page;
import com.titan.service.bpm.FlowManageService;

@RestController
@RequestMapping("flow")
public class FlowManageController {

	@Autowired
	private FlowManageService nodeMangeService;
	
	@ResponseBody
	@RequestMapping("flowManage")
	public Page<FlowElement> flowManage(HttpServletRequest request,HttpServletResponse response) {
		String definitionId = request.getParameter("definitionId");
		List<FlowElement> list = nodeMangeService.getFlowsByDefinitionId(definitionId);
		Page<FlowElement> page = new Page<FlowElement>();
		page.setCode(1);
		page.setRows(list);
		page.setMsg("");
		page.setTotal(list.size());

		return page;

	}
	
	@ResponseBody
	@RequestMapping("getProcessFlowByActId")
	public ProcessFlow getProcessFlowByActId(HttpServletRequest request,HttpServletResponse response) {
		String actId = request.getParameter("activitiId");
		ProcessFlow processFlow = nodeMangeService.getProcessFlowByActId(actId);
		return processFlow;
	}
	
	@ResponseBody
	@RequestMapping("updateProcessFlow")
	public Map<String,Object> updateProcessFlow(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String actId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		String defId = request.getParameter("definitionId")==null?"":request.getParameter("definitionId");
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String conditionFlow = request.getParameter("conditionFlow")==null?"":request.getParameter("conditionFlow");
		String  description= request.getParameter("description")==null?"":request.getParameter("description");
		String defalutFlow = request.getParameter("defalutFlow")==null?"":request.getParameter("defalutFlow");
		try {
			nodeMangeService.updateProcessFlow(id,actId, defId,description,name,defalutFlow,conditionFlow);
			map.put("code", 1);
		} catch (Exception e) {
			map.put("code", -1);
		}
		
		return map;
		
	}
	
}
