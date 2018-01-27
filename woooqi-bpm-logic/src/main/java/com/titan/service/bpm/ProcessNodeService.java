package com.titan.service.bpm;


import com.titan.entity.bpm.manage.ProcessNode;

public interface ProcessNodeService {
	
	
	public ProcessNode getNodeByActivitiId(String activitiId);

	public void updateNode(String id, String limitType, String jumpType,String day, String hour, String minute, String processVariable,String definitionId, String activitiId);
	
}
