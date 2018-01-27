package com.titan.core.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;

public interface EventHandler {

	public void handle(ActivitiEvent event);
}
