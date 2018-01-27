package com.woooqi.bpm.controller.bpm;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.bpm.manage.ProcessNode;
import com.woooqi.bpm.service.bpm.ProcessNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.manage.ProcessNode;
import com.titan.service.bpm.ProcessNodeService;

@RestController
@RequestMapping("processNode")

public class ProcessNodeController {
	
	@Autowired
	private ProcessNodeService processNodeService;
	
	@ResponseBody
	@RequestMapping("updateNode")
	public Map<String,Object> updateNode(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String limitType = request.getParameter("limitType")==null?"":request.getParameter("limitType");
		String jumpType = request.getParameter("jumpType")==null?"":request.getParameter("jumpType");
		String day = request.getParameter("day")==null?"":request.getParameter("day");
		String hour = request.getParameter("hour")==null?"":request.getParameter("hour");
		String minute = request.getParameter("min")==null?"":request.getParameter("min");
		String definitionId = request.getParameter("definitionId")==null?"":request.getParameter("definitionId");
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		String processVariable = request.getParameter("processVariable")==null?"":request.getParameter("processVariable");
		
		try {
			processNodeService.updateNode(id,limitType,jumpType,day,hour,minute,processVariable,definitionId,activitiId);
			map.put("code", 1);
			
		} catch (Exception e) {
			map.put("code",0);
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("getNodeByActivitiId")
	public ProcessNode getNodeByActivitiId(HttpServletRequest request, HttpServletResponse response) {
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		ProcessNode processNode = processNodeService.getNodeByActivitiId(activitiId);
		return processNode;
	}
	
}
