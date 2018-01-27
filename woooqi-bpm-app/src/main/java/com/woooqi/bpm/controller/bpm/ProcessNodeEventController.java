package com.woooqi.bpm.controller.bpm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeEvent;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.bpm.ProcessNodeEventService;
import com.woooqi.bpm.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.manage.ProcessNodeEvent;
import com.titan.entity.web.Page;
import com.titan.repository.bpm.manage.ProcessNodeEventRepository;
import com.titan.service.bpm.ProcessNodeEventService;
import com.titan.utils.PageUtils;

@RestController
@RequestMapping("processNodeEvent")
public class ProcessNodeEventController {
	@Autowired
	private ProcessNodeEventRepository processNodeEventRepository;
	@Autowired
	private ProcessNodeEventService processNodeEventService;
	
	@RequestMapping("getProcessNodeEventByActId")
	@ResponseBody
	public Page<ProcessNodeEvent> getProcessNodeEventByActId(HttpServletRequest request, HttpServletResponse response){
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		Page<ProcessNodeEvent> page = new Page<ProcessNodeEvent>();
		List<ProcessNodeEvent> lists  = processNodeEventService.getProcessNodeEventByActId(activitiId);
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}

	@RequestMapping("saveProcessNodeEvent")
	@ResponseBody
	public Map<String,Object> saveProcessNodeEvent(HttpServletRequest request,HttpServletResponse response) {
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		String ids = request.getParameter("ids")==null?"":request.getParameter("ids");
		String definitionId = request.getParameter("definitionId")==null?"":request.getParameter("definitionId");
		String types = request.getParameter("types")==null?"":request.getParameter("types");
		String class_names = request.getParameter("class_names")==null?"":request.getParameter("class_names");
		Map<String,Object> map = new HashMap<String, Object>();
		List<String> type = new ArrayList<String>();
		List<String> class_name = new ArrayList<String>();
		List<String> id = new ArrayList<String>();
		try {
			if(StringUtils.isNotEmpty(types)&&StringUtils.isNotEmpty(class_names)&&StringUtils.isNotEmpty(ids)){
				type= Arrays.asList(types.split(","));
				class_name= Arrays.asList(class_names.split(","));
				id= Arrays.asList(ids.split(","));
				
			}
			ProcessNodeEvent processNodeEvent = new ProcessNodeEvent();
			
			for(int i=0;i<type.size();i++){
				if(id.get(i).equals("")||id.get(i) == null||id.get(i)=="0"){
					processNodeEvent.setId(PageUtils.getUUID());
				}else{
					processNodeEvent.setId(id.get(i));
				}
				processNodeEvent.setType(type.get(i));
				processNodeEvent.setActivitiId(activitiId);
				processNodeEvent.setClass_name(class_name.get(i));
				processNodeEvent.setDefinitionId(definitionId);
				processNodeEventRepository.saveAndFlush(processNodeEvent);
			}
			map.put("code",1);
		} catch (Exception e) {
			map.put("code",0);
		}
		return map;
	}
	
	@RequestMapping("delProcessNodeEventById")
	@ResponseBody
	public void delProcessNodeEventById(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		processNodeEventService.delProcessNodeEventById(id);
	
	}
	
}
