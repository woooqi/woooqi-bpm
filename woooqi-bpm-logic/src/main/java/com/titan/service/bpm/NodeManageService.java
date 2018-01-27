package com.titan.service.bpm;

import java.util.List;

import org.activiti.bpmn.model.FlowElement;

public interface NodeManageService {

	public List<FlowElement> getNodesByDefinitionId(String definitionId);
	
	public FlowElement getNodesByDefinitionIdAndActId(String definitionId, String actId);
	
}
