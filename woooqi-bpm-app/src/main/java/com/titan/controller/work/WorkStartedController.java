package com.titan.controller.work;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SignTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.bpm.manage.ProcessNodeForm;
import com.titan.entity.organization.User;
import com.titan.entity.web.Page;
import com.titan.entity.work.Task;
import com.titan.service.bpm.NodeManageService;
import com.titan.service.bpm.ProcessNodeAssignService;
import com.titan.service.bpm.ProcessNodeFormService;
import com.titan.service.work.WorkStartedService;
import com.titan.utils.PageUtils;

@RestController
@RequestMapping("work")
public class WorkStartedController {
	
	@Autowired
	private WorkStartedService workStartedService;
	
	@Autowired
	private ProcessNodeAssignService processNodeAssignService;
	
	@Autowired
	private ProcessNodeFormService processNodeFormService;
	
	@Autowired
	private NodeManageService nodeManageService;
	
		
	@RequestMapping("getWorkStartedTasks")
	@ResponseBody
	public Page<Task> getWorkStartedTasks(HttpServletRequest request,HttpServletResponse response) {
		Page<Task> page = new Page<Task>();
		Set<Task> sets = new HashSet<Task>();
		User user = PageUtils.getCurrentUser();
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		List<Task> tasks =  workStartedService.getAllTasks(name);
		for(Task task:tasks){
				List<Task> assigns = workStartedService.getAssignTasks(user.getId());
				sets.addAll(assigns);
		}
		List<Task> list = new ArrayList<Task>();
		list.addAll(sets);
		for(Task task:list){
			ProcessNodeForm processNodeForm = processNodeFormService.getProcessNodeFormByActivitiId(task.getTask_def_key_());
			FlowElement flowElement = nodeManageService.getNodesByDefinitionIdAndActId(task.getProc_def_id_(), task.getTask_def_key_());
			if(flowElement!=null && flowElement instanceof SignTask){
				task.setTask_op("2");
			}else{
				if(processNodeForm==null){
					task.setTask_op("0");
					
				}else{
					task.setTask_op("1");
				}
			}
		}
		page.setRows(list);
	    page.setTotal(list.size());
		return page;
	}
	
	
}
