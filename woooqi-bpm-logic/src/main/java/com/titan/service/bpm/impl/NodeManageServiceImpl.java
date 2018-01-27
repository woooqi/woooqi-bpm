package com.titan.service.bpm.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.InclusiveGateway;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SignTask;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.service.bpm.NodeManageService;

@Service
public class NodeManageServiceImpl implements NodeManageService{

	@Autowired
	private RepositoryService repositoryService;
	
	@Override
	public List<FlowElement> getNodesByDefinitionId(String definitionId){
		
		List<FlowElement> flows = new ArrayList<FlowElement>();
		
		BpmnModel bpmnModel = repositoryService.getBpmnModel(definitionId);
		
		Collection<FlowElement> flowElements =  bpmnModel.getMainProcess().getFlowElements();
		for (FlowElement flowElement : flowElements){
			if(flowElement instanceof FlowNode){
				if(flowElement instanceof StartEvent){
					flowElement.setName(flowElement.getName() + ":" + "开始事件");
				}else if(flowElement instanceof EndEvent){
					flowElement.setName(flowElement.getName() + ":" + "结束事件");
				}else if(flowElement instanceof UserTask){
					flowElement.setName(flowElement.getName() + ":" + "用户任务");
				}else if(flowElement instanceof SignTask){
					flowElement.setName(flowElement.getName() + ":" + "会签任务");
				}else if(flowElement instanceof ExclusiveGateway){
					flowElement.setName(flowElement.getName() + ":" + "互斥网关");
				}else if(flowElement instanceof InclusiveGateway){
					flowElement.setName(flowElement.getName() + ":" + "包容性网关");
				}else if(flowElement instanceof ParallelGateway){
					flowElement.setName(flowElement.getName() + ":" + "并行网关");
				}else if(flowElement instanceof SubProcess){
					Collection<FlowElement> subFlowElements = ((SubProcess) flowElement).getFlowElements();
					for (FlowElement subFlowElement : subFlowElements){
						if(!(subFlowElement instanceof SequenceFlow)){
							if(subFlowElement instanceof StartEvent){
								subFlowElement.setName(subFlowElement.getName() + ":" + "子流程-开始事件");
							}else if(subFlowElement instanceof EndEvent){
								subFlowElement.setName(subFlowElement.getName() + ":" + "子流程-结束事件");
							}else if(subFlowElement instanceof UserTask){
								subFlowElement.setName(subFlowElement.getName() + ":" + "子流程-用户任务");
							}else if(subFlowElement instanceof SignTask){
								subFlowElement.setName(subFlowElement.getName() + ":" + "子流程-会签任务");
							}else if(subFlowElement instanceof ExclusiveGateway){
								subFlowElement.setName(subFlowElement.getName() + ":" + "子流程-互斥网关");
							}else if(subFlowElement instanceof InclusiveGateway){
								subFlowElement.setName(subFlowElement.getName() + ":" + "子流程-包容性网关");
							}else if(subFlowElement instanceof ParallelGateway){
								subFlowElement.setName(subFlowElement.getName() + ":" + "子流程-并行网关");
							}
							flows.add(subFlowElement);
						}
					}
				}
				if(!(flowElement instanceof SubProcess)){
					flows.add(flowElement);
				}
				
				
			}
		}
		return flows;
	}

	@Override
	public FlowElement getNodesByDefinitionIdAndActId(String definitionId, String actId) {
		
		BpmnModel bpmnModel = repositoryService.getBpmnModel(definitionId);
		Process process = bpmnModel.getProcesses().get(0);
		FlowElement fe =null;
		Collection<FlowElement> flowElements = process.getFlowElements();
		for (FlowElement flowElement : flowElements){
			if(flowElement instanceof SubProcess){
				Collection<FlowElement> subFlowElements = ((SubProcess) flowElement).getFlowElements();
				for (FlowElement subFlowElement : subFlowElements){
					if(actId.equals(subFlowElement.getId())){
						fe =  subFlowElement;
						break;
					}
					
					}
			}
			
			if(actId.equals(flowElement.getId())){
				fe =  flowElement;
				break;
			}
				
		}
			
	
		return fe;
	}

	
	
}
