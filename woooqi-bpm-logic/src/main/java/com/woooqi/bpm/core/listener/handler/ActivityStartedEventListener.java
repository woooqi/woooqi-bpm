package com.woooqi.bpm.core.listener.handler;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;

import com.titan.core.listener.EventHandler;


public class ActivityStartedEventListener implements EventHandler{

	public void handle(ActivitiEvent event) {
		ActivitiActivityEventImpl eventImpl = (ActivitiActivityEventImpl)event; 
		
		doRunPath(eventImpl);
	}

	private void doRunPath(ActivitiEvent event) {
		
	}

}
