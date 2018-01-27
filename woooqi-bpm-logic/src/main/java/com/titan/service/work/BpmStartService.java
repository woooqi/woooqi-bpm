package com.titan.service.work;

import java.util.List;
import java.util.Map;

import com.titan.entity.bpm.bpm.ProcessDefinition;

public interface BpmStartService {
	
	public void startProcess(String processKey, String bussinesskey, Map<String, Object> param);

	public List<ProcessDefinition> getAuthorizeProcessDefinition();		
}
