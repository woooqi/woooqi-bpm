package com.woooqi.bpm.service.bpm.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.bpm.Category;
import com.titan.entity.bpm.bpm.Instance;
import com.titan.service.bpm.InstanceService;
import com.titan.utils.DataBaseUtils;

@Service
public class InstanceServiceImpl implements InstanceService{
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private ProcessEngine processEngine;
	
	
	public List<Instance> getAllInstance(String name){
		String sql = "";
		if(name == null || name.equals("")){
			sql = "select t.* from  act_ru_execution t";
		}else{
			sql = "select t2.* from (select t.*, substr(t.proc_def_id_,1,instr(t.proc_def_id_,':')-1) as new_name from act_ru_execution t) t2";
			sql += " where t2.new_name like '%"+name+"%'";
		}
		List<Instance> Instance = DataBaseUtils.queryForListBean(sql, Instance.class);
		return Instance;
	}
	
	
	public Execution getInstanceByInstanceId(String instanceId){
		Execution execution = runtimeService.createExecutionQuery().processInstanceId(instanceId).singleResult();
		return execution;
	}
	
	
	public void pendInstance(String instanceId) {
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
		if(!instance.isEnded() && !instance.isSuspended()){
			runtimeService.suspendProcessInstanceById(instanceId);
		}
	}
	
	public void activeInstance(String instanceId){
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
		if(!instance.isEnded() && instance.isSuspended()){
			runtimeService.activateProcessInstanceById(instanceId);
		}
	}

	public byte[] getActivitiProccessImage(String processInstanceId){
	    try {
	        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	        String prodefid = historicProcessInstance.getProcessDefinitionId();
	        if (historicProcessInstance != null) {
				 ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(prodefid);
	            // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
	            List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();
	            
	            // 已执行的节点ID集合
	            List<String> finishedActiveActivityIds = new ArrayList<String>();
	            
	            for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
	            	finishedActiveActivityIds .add(activityInstance.getActivityId());                
	            }
	            
	            // 已完成的节点+当前节点  
	            List<String> activeActivityIds = new ArrayList<>();
	            activeActivityIds.addAll(finishedActiveActivityIds);  
	            activeActivityIds.addAll(runtimeService.getActiveActivityIds(processInstanceId));  
	            
	            // 已执行的线集合
	            List<String> highLightedFlows = new ArrayList<>(); 
	            getHighLightedFlows(processDefinitionEntity.getActivities(), highLightedFlows, activeActivityIds);
	            
	            BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
	            
	            ProcessDiagramGenerator processDiagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();  
	            InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "PNG", finishedActiveActivityIds, highLightedFlows,"宋体","宋体","宋体",null, 1.0); 
	            return IOUtils.toByteArray(inputStream);
	        }        
	    } catch (Exception e) { 
	    	e.printStackTrace();
	    }
	    return null;
	}

	private void getHighLightedFlows(List<ActivityImpl> activityList, List<String> highLightedFlows, List<String> historicActivityInstanceList) {  
		for (ActivityImpl activity : activityList) {  
			if (activity.getProperty("type").equals("subProcess")) {  
				getHighLightedFlows(activity.getActivities(), highLightedFlows, historicActivityInstanceList);  
			}  
  
			if(historicActivityInstanceList.contains(activity.getId())) {  
				List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();  
				for (PvmTransition pvmTransition : pvmTransitionList) {  
					String destinationFlowId = pvmTransition.getDestination().getId();  
					if (historicActivityInstanceList.contains(destinationFlowId)) {  
						highLightedFlows.add(pvmTransition.getId());  
					}  
				}  
			}  
		}  
	}


	@Override
	public Instance getInstanceByDeployId(String deployId) {
		String sql = "select t.* from act_ru_execution t where t.proc_def_id_ = ?";
		return DataBaseUtils.queryForBean(sql, Instance.class,deployId);
		
	}  

}
