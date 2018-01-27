package com.titan.core.listener.handler;


import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;

import com.titan.core.listener.EventHandler;

public class ActivityCompletedEventListener implements EventHandler{
	
	
	public void handle(ActivitiEvent event) {
		ActivitiActivityEventImpl eventImpl = (ActivitiActivityEventImpl)event; 
		doRunPath(eventImpl);
	}

	private void doRunPath(ActivitiEvent event) {
		
		
	}

}
