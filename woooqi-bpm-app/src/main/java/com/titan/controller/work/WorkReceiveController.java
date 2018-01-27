package com.titan.controller.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.organization.User;
import com.titan.entity.web.Page;
import com.titan.entity.work.Task;
import com.titan.service.bpm.ProcessNodeAssignService;
import com.titan.service.work.WorkReceivedService;
import com.titan.service.work.WorkStartedService;
import com.titan.utils.PageUtils;

@RestController
@RequestMapping("workReceived")
public class WorkReceiveController {
	
	@Autowired
	private WorkReceivedService workReceivedService;
	
	@Autowired
	private ProcessNodeAssignService processNodeAssignService;
		
	@RequestMapping("getWorkReceivedTasks")
	@ResponseBody
	public Page<Task> getWorkReceivedTasks(HttpServletRequest request,HttpServletResponse response) {
		
		Page<Task> page = new Page<Task>();
		Set<Task> sets = new HashSet<Task>();
		User user = PageUtils.getCurrentUser();
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		List<Task> tasks =  workReceivedService.getAllTasks(name);
		for(Task task:tasks){
			ProcessNodeAssign processNodeAssign = processNodeAssignService.getNodeManageAssignByActId(task.getTask_def_key_());
			Set<ProcessNodeAssignType> assignTypes = processNodeAssign.getAssignTypes();
			if(!assignTypes.contains("1")){
				List<Task> candidates = workReceivedService.getCandidateTasks(user.getId());
				sets.addAll(candidates);
				String groupIds = (user.getDeptId().equals("")?"":(user.getDeptId() + ",")) + (user.getPostId().equals("")?"":(user.getPostId()+",") ) + (user.getRoleIds().equals("")?"":(user.getRoleIds()));
				List<Task> candidateGroups = workReceivedService.getCandidateGroupTasks(groupIds);
				sets.addAll(candidateGroups);
			}
		}
		List<Task> list = new ArrayList<Task>();
		list.addAll(sets);
		page.setRows(list);
	    page.setTotal(list.size());
		return page;
	}
	
	@RequestMapping("receiveTask")
	@ResponseBody
	public Map<String,Object> receiveTask(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		User user = PageUtils.getCurrentUser();
		String userId = user.getId();
		String taskId = request.getParameter("taskId")==null?"":request.getParameter("taskId");
		try {
			workReceivedService.recevieTask(taskId, userId);
			map.put("code", 1);
			
		} catch (Exception e) {
			map.put("code", -1);
		}
	
		return map;
	}
	
	
}
