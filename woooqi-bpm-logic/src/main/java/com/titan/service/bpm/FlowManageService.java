package com.titan.service.bpm;

import java.util.List;

import org.activiti.bpmn.model.FlowElement;

import com.titan.entity.bpm.manage.ProcessFlow;

public interface FlowManageService {

	public List<FlowElement> getFlowsByDefinitionId(String definitionId);
	
	public ProcessFlow getProcessFlowByActId(String actId);
		
	public void updateProcessFlow(String id,String actId,String defId,String description,String name,String defalutFlow,String conditionFlow);
	
}
