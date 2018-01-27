package com.titan.core.cmd;

import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.runtime.AtomicOperation;

public class JumpTaskCommand implements Command<java.lang.Void> {
	
	private TaskEntity taskEntity;
	private ActivityImpl targetActivity;
	protected Map<String, Object> variables;

	public JumpTaskCommand(TaskEntity taskEntity, ActivityImpl targetActivity, Map<String, Object> variables) {
		this.taskEntity = taskEntity;
		this.targetActivity = targetActivity;
		this.variables = variables;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		
		if (taskEntity != null) {
			
			if (variables != null) {
				if (taskEntity.getExecutionId() != null) {
					taskEntity.setExecutionVariables(variables);
				} else {
					taskEntity.setVariables(variables);
				}
			}

			Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, TaskEntity.DELETE_REASON_COMPLETED, false);	
			
			ExecutionEntity execution = taskEntity.getExecution();
			execution.setActivity(targetActivity);
			execution.performOperation(AtomicOperation.ACTIVITY_START);
			
		}

		return null;
	}
}