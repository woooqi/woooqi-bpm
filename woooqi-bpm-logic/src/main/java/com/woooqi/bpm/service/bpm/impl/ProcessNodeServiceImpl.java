package com.woooqi.bpm.service.bpm.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.manage.ProcessNode;
import com.titan.repository.bpm.manage.ProcessManageNodeRepository;
import com.titan.service.bpm.ProcessNodeService;


@Service
public class ProcessNodeServiceImpl implements ProcessNodeService {
	
	@Autowired
	private ProcessManageNodeRepository processManageNodeRepository;
	
	@Override
	public void updateNode(String id,String limitType,String jumpType,String day,String hour,String minute,String processVariable,String definitionId,String activitiId){
		ProcessNode processNode = new ProcessNode();
		processNode.setId(id);
		processNode.setLimitType(limitType);
		processNode.setJumpType(jumpType);
		processNode.setDay(day);
		processNode.setHour(hour);
		processNode.setMinute(minute);
		processNode.setProcessVariable(processVariable);
		processNode.setDefinitionId(definitionId);
		processNode.setActivitiId(activitiId);
		processManageNodeRepository.saveAndFlush(processNode);
		
	}

	@Override
	public ProcessNode  getNodeByActivitiId(String activitiId) {
		 return processManageNodeRepository.findByActivitiId(activitiId);
	}

	

}
