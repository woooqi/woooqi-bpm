package com.woooqi.bpm.service.bpm.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.manage.ProcessNodeEvent;
import com.titan.repository.bpm.manage.ProcessNodeEventRepository;
import com.titan.service.bpm.ProcessNodeEventService;


@Service
public class ProcessNodeEventServiceImpl implements ProcessNodeEventService {
	
	@Autowired
	private ProcessNodeEventRepository processNodeEventRepository;

	@Override
	public List<ProcessNodeEvent> getProcessNodeEventByActId(String activitiId) {
		 List<ProcessNodeEvent> lists = processNodeEventRepository.findByActivitiId(activitiId);
		return lists;
	}
	
	@Override
	public void delProcessNodeEventById(String id) {
		processNodeEventRepository.delete(id);
	}
	
	@Override
	public Map<String, Object> saveProcessNodeEvent(ProcessNodeEvent processNodeEvent,Set<String> sets) {
		
		return null;
	}

}
