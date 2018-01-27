package com.woooqi.bpm.core.behavior;

import java.util.Iterator;  
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;  
import org.activiti.engine.impl.pvm.PvmTransition;  
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;  
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.titan.entity.bpm.manage.ProcessFlow;
import com.titan.repository.bpm.manage.ProcessManageFlowRepository;
import com.titan.utils.GroovyEngineUtils;

@SuppressWarnings("serial")
public class ExclusiveGatewayActivityBehaviorExt extends ExclusiveGatewayActivityBehavior{  
      
	@Autowired
	private ProcessManageFlowRepository processManageFlowRepository;
	
    @Override  
    protected void leave(ActivityExecution execution) {  
    	
    	String activitiId = execution.getActivity().getId();
        String definitionId = execution.getActivity().getProcessDefinition().getId();
          
        PvmTransition outgoingSeqFlow = null;
        	
        List<ProcessFlow> processFlows = processManageFlowRepository.findByDefinitionId(definitionId);
            
        String defaultSequence = null;
        Object boolVal = null;
        for(ProcessFlow processFlow:processFlows){  
            	
        	String conditionSequence = processFlow.getConditionFlow();
        	if(conditionSequence!=null && !conditionSequence.equals("")){
	        	if(conditionSequence.startsWith("${") && conditionSequence.endsWith("}")){
	        		conditionSequence = conditionSequence.substring(2, conditionSequence.length()-1);
	        	}
	            boolVal = GroovyEngineUtils.executeScript(conditionSequence, execution.getVariables());
        	}else{
        		if(StringUtils.isNotEmpty(processFlow.getDefalutFlow())){
        			defaultSequence = processFlow.getActivitiId();
        		}
            }
            if(boolVal instanceof Boolean){  
                Boolean returnVal = (Boolean)boolVal;
                if(returnVal == true){  
                    Iterator<PvmTransition> transitionIterator = execution.getActivity().getOutgoingTransitions().iterator();  
                    while (transitionIterator.hasNext()) {  
                          PvmTransition seqFlow = transitionIterator.next();  
                          if(processFlow.getActivitiId().equals(seqFlow.getId())){  
                        	  outgoingSeqFlow = seqFlow;
                          }  
                    }  
                }  
            }else{  
                throw new RuntimeException("Sequence flow Condition<"+conditionSequence+"> is Error(true or false)");  
            }  
        }
            
        if(outgoingSeqFlow != null) {
        	execution.take(outgoingSeqFlow);
        }else{
        	if (defaultSequence != null) {
        		PvmTransition defaultTransition = execution.getActivity().findOutgoingTransition(defaultSequence);
        		if (defaultTransition != null) {
        			execution.take(defaultTransition);
        		} else {
        			throw new ActivitiException("Default sequence flow '" + defaultSequence + "' not found");
        		}
        	}else{
        		throw new ActivitiException("No outgoing sequence flow of the exclusive gateway '" + execution.getActivity().getId() + "' could be selected for continuing the process");
        	}
        }
          
    }  
} 
