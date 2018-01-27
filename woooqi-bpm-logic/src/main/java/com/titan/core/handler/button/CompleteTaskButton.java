package com.titan.core.handler.button;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.core.cmd.BaseTaskCommand;

@Component
public class CompleteTaskButton implements BaseButton{

	@Autowired
	private BaseTaskCommand baseTaskCommand;
	
	@Override
	public boolean support(String buttonCode) {
		if(buttonCode.toLowerCase().equals("completetask")){
			return true;
		}
		return false;
	}
	
	@Override
	public void handler(String taskId,Map<String, Object> before) {
		baseTaskCommand.completeTask(taskId, before);
	}

}
