package com.titan.core.handler.button;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.core.cmd.BaseTaskCommand;
import com.titan.core.sign.TaskSignService;

@Component
public class SignOutTaskButton implements BaseButton{

	@Autowired
	private BaseTaskCommand baseTaskCommand;
	
	@Autowired 
	private TaskService taskService;
	
	@Autowired 
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskSignService taskSignService;
	
	@Override
	public boolean support(String buttonCode) {
		if(buttonCode.toLowerCase().equals("signouttask")){
			return true;
		}
		return false;
	}
	
	@Override
	public void handler(String taskId,Map<String, Object> before) {
		try {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			int sign = runtimeService.getVariable(task.getExecutionId(), "nrOfSignouts")==null?0:Integer.parseInt(runtimeService.getVariable(task.getExecutionId(), "nrOfSignouts").toString());
			sign = sign + 1;
			runtimeService.setVariable(task.getExecutionId(), "nrOfSignouts", sign);
			List<Task> list = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
			 Execution excution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
			 if(list!=null && list.size()==1){
				int completeCounter = runtimeService.getVariable(task.getExecutionId(), "nrOfCompletedInstances")==null?0:Integer.parseInt(runtimeService.getVariable(task.getExecutionId(), "nrOfCompletedInstances").toString());
				runtimeService.setVariable(task.getExecutionId(), "nrOfCompletedInstances", completeCounter+1);
				taskSignService.getComplete(task.getExecutionId(), excution.getActivityId());
			 }
			baseTaskCommand.completeTask(taskId, before);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
