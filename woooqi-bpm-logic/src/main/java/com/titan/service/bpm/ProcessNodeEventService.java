package com.titan.service.bpm;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.titan.entity.bpm.manage.ProcessNodeEvent;


public interface ProcessNodeEventService {
	
	public List<ProcessNodeEvent> getProcessNodeEventByActId(String activitiId);
	
	public Map<String, Object> saveProcessNodeEvent(ProcessNodeEvent processNodeEvent, Set<String> sets);
	public void delProcessNodeEventById(String id);
	
}
