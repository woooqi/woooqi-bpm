package com.titan.core.listener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.titan.utils.BeanUtils;


public class GlobalEventListener implements ActivitiEventListener {

	private Map<String, String> handlers = new HashMap<String, String>();

	public void onEvent(ActivitiEvent event) {
		String eventType = event.getType().name();
		String eventHandlerBeanId = handlers.get(eventType);
		
		System.out.println("####"+ToStringBuilder.reflectionToString(event));
		
		if(eventHandlerBeanId!=null){  
			EventHandler handler = (EventHandler)BeanUtils.getInstance().getBean(eventHandlerBeanId); 
			handler.handle(event);  
		}
	}

	public boolean isFailOnException() {
		return false;
	}

	public Map<String, String> getHandlers() {
		return handlers;
	}

	public void setHandlers(Map<String, String> handlers) {
		this.handlers = handlers;
	}

}
