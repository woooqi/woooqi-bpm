package com.titan.core.listener.base;

import org.activiti.engine.impl.persistence.entity.TaskEntity;

public abstract class TaskCreateListener {
	
	public abstract void handle(TaskEntity taskEntity);

}
