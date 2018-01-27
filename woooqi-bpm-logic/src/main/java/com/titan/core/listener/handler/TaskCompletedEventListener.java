package com.titan.core.listener.handler;

import java.util.List;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.titan.core.listener.EventHandler;
import com.titan.core.listener.base.TaskCompleteListener;
import com.titan.entity.bpm.manage.ProcessNodeEvent;
import com.titan.service.bpm.ProcessNodeEventService;

public class TaskCompletedEventListener implements EventHandler{
	@Autowired
	private ProcessNodeEventService processNodeEventService;
	

	public void handle(ActivitiEvent event) {		
		ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl)event; 
		if(eventImpl.getEntity() instanceof TaskEntity){
			TaskEntity taskEntity = (TaskEntity)eventImpl.getEntity();
			doTaskListener(taskEntity);			
		}
	}
	
	private void doTaskListener(TaskEntity taskEntity) {
		try {
			List<ProcessNodeEvent> lists  = processNodeEventService.getProcessNodeEventByActId(taskEntity.getTaskDefinitionKey());
			for(ProcessNodeEvent processNodeEvent:lists){
				   Class<?> onwClass = Class.forName(processNodeEvent.getClass_name());
				   if("3".equals(processNodeEvent.getType())){
					   TaskCompleteListener h = (TaskCompleteListener)onwClass.newInstance();
							 h.handle(taskEntity);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
					  
				   }
				   
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
