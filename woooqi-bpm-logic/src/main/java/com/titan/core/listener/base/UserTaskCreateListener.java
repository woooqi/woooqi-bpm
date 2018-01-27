package com.titan.core.listener.base;

import org.activiti.engine.impl.persistence.entity.TaskEntity;

public class UserTaskCreateListener extends TaskCreateListener {

	@Override
	public void handle(TaskEntity taskEntity) {
		System.out.println("-----------------------------"+taskEntity.toString());

	}

}
