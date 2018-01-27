package com.woooqi.bpm.core.listener.base;

import org.activiti.engine.impl.persistence.entity.TaskEntity;

public abstract class TaskAssignListener {
	
	public abstract void handle(TaskEntity taskEntity);

}
