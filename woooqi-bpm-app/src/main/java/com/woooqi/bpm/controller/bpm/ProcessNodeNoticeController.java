package com.woooqi.bpm.controller.bpm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeNotice;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.bpm.ProcessNodeNoticeService;
import com.woooqi.bpm.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.manage.ProcessNodeNotice;
import com.titan.entity.web.Page;
import com.titan.service.bpm.ProcessNodeNoticeService;
import com.titan.utils.JsonUtils;


@RestController
@RequestMapping("processNodeNotice")
public class ProcessNodeNoticeController {

	@Autowired
	private ProcessNodeNoticeService processNodeNoticeService;
	
	@RequestMapping("saveProcessNodeNotice")
	@ResponseBody
	public Map<String,Object> saveNodeManageAssign(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> map =new HashMap<String, Object>();
		
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		String definitionId = request.getParameter("definitionId")==null?"":request.getParameter("definitionId");
		String notices = request.getParameter("notices")==null?"":request.getParameter("notices");
		List<Map<String,Object>> lists = null;
 		List<ProcessNodeNotice> processNodeNotices  = new ArrayList<ProcessNodeNotice>();
		if(StringUtils.isNotEmpty(notices)){
			lists = JsonUtils.jsonToList(notices);
		    for(int i=0;i<lists.size();i++){
		    	ProcessNodeNotice processNodeNotice = new ProcessNodeNotice();
		    	Map<String,Object> notice = new HashMap<String, Object>();
		    	notice=	lists.get(i);
		    	processNodeNotice.setActivitiId(activitiId);
		    	processNodeNotice.setDefinitionId(definitionId);
		    	processNodeNotice.setId((String)notice.get("id"));
		    	processNodeNotice.setMould(notice.get("mould")==null?"":notice.get("mould").toString());
		    	processNodeNotice.setNotice_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(notice.get("notice_time")==null?"":notice.get("notice_time").toString()));
		    	processNodeNotice.setType((String)notice.get("type"));
		    	processNodeNotice.setUser_id(notice.get("user_id")==null?"":notice.get("user_id").toString());		    	
		    	processNodeNotices.add(processNodeNotice);
		    }
			
		}
		map  = processNodeNoticeService.saveProcessNodeNotice(processNodeNotices);
		return map;
	}
	
	
	@RequestMapping("delProcessNodeNoticeById")
	@ResponseBody
	public Map<String,Object> delProcessNodeNoticeByActId(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		processNodeNoticeService.delProcessNodeNotice(id);
		map.put("code", 1);
		return map;
	}
	
	@RequestMapping("getNodeManageNoticeByActId")
	@ResponseBody
	public Page<Map<String,Object>> getNodeManageNoticeByActId(HttpServletRequest request, HttpServletResponse response){
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		List<Map<String,Object>> processNodeNotices  = processNodeNoticeService.getProcessNodeNoticeUserByActId(activitiId);
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		if(processNodeNotices==null||processNodeNotices.size()==0){
			page.setCode(-1);
			return page;
		}
		page.setCode(1);
		page.setRows(processNodeNotices);
		
		return page;
	}

	
	




}
