package com.woooqi.bpm.service.bpm.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.InclusiveGateway;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.manage.ProcessFlow;
import com.titan.repository.bpm.manage.ProcessManageFlowRepository;
import com.titan.service.bpm.FlowManageService;

@Service
public class FlowManageServiceImpl implements FlowManageService{

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private ProcessManageFlowRepository processManageFlowRepository;

	
	@Override
	public List<FlowElement> getFlowsByDefinitionId(String definitionId){
		
		List<FlowElement> flows = new ArrayList<FlowElement>();
		
		BpmnModel bpmnModel = repositoryService.getBpmnModel(definitionId);
		
		Process process = bpmnModel.getProcesses().get(0);
		
		Collection<FlowElement> flowElements = process.getFlowElements();
		for (FlowElement flowElement : flowElements){
			if(flowElement instanceof SequenceFlow){
				SequenceFlow sequenceFlow = (SequenceFlow)flowElement;
				if(getFlowElementById(sequenceFlow.getSourceRef(),flowElements) instanceof ExclusiveGateway){
					sequenceFlow.setName(sequenceFlow.getName() + ":" + "互斥流线");
				}else if(getFlowElementById(sequenceFlow.getSourceRef(),flowElements) instanceof InclusiveGateway){
					sequenceFlow.setName(sequenceFlow.getName() + ":" + "包容流线");
				}else if(getFlowElementById(sequenceFlow.getSourceRef(),flowElements) instanceof ParallelGateway){
					sequenceFlow.setName(sequenceFlow.getName() + ":" + "并行流线");
				}else{
					sequenceFlow.setName(sequenceFlow.getName() + ":" + "普通流线");
				}
				flows.add(sequenceFlow);
			}
		}
		return flows;
	}

	private FlowElement getFlowElementById(String id,Collection<FlowElement> flowElements) {
		for (FlowElement flowElement : flowElements){
			if(flowElement.getId().equals(id)){
				return flowElement;
			}
		}
		return null;
	}

	@Override
	public ProcessFlow getProcessFlowByActId(String actId) {
		return processManageFlowRepository.findByActivitiId(actId);
	}

	@Override
	public void updateProcessFlow(String id,String actId, String defId,String description,String name, String defalutFlow, String conditionFlow) {
		ProcessFlow processFlow = new ProcessFlow();
		processFlow.setId(id);
		processFlow.setActivitiId(actId);
		processFlow.setDefinitionId(defId);
		processFlow.setName(name);
		processFlow.setDescription(description);
		processFlow.setConditionFlow(conditionFlow);
		processFlow.setDefalutFlow(defalutFlow);
		processManageFlowRepository.saveAndFlush(processFlow);
	}
	
}
