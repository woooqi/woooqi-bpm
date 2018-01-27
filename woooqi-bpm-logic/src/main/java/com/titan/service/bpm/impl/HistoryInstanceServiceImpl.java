package com.titan.service.bpm.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.web.Page;
import com.titan.service.bpm.HistoryInstanceService;
import com.titan.utils.DataBaseUtils;

@Service
public class HistoryInstanceServiceImpl implements HistoryInstanceService {
	
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


	@Override
	public byte[] getActivitiProccessImage(String processInstanceId) {
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
		            List<String> activityIds = new ArrayList<>();
		            activityIds.addAll(finishedActiveActivityIds);  
		           
		            if( historicProcessInstance.getEndTime()==null){
		            	activityIds.addAll(runtimeService.getActiveActivityIds(processInstanceId));  
		            }   
		            
		            // 已执行的线集合
		            List<String> highLightedFlows = new ArrayList<>(); 
		            getHighLightedFlows(processDefinitionEntity.getActivities(), highLightedFlows, activityIds);
		            
		            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		            List<String> activeActivityIds = new ArrayList<String>();
		            for(Task Task:tasks){
		            	activeActivityIds.add(Task.getTaskDefinitionKey());
		            }
		            
		            List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
		            List<Map<String, Object>> turnTransitionActivity = null;
		            turnTransitionActivity = new ArrayList<Map<String,Object>>();
		            for(HistoricVariableInstance historicVariableInstance:historicVariableInstances){
		            	if(historicVariableInstance.getVariableName().startsWith("backSequence")){
		            		Map<String, Object> map = new HashMap<String, Object>();
		            		String value = historicVariableInstance.getValue().toString();
		            		if(StringUtils.isNotEmpty(value) && value.indexOf(":")!=-1){
		            			String[] split = value.split(":");
		            			map.put("type", "back");
		            			map.put("sourceId", split[0]);
		            			map.put("targetId", split[1]);
		            		}
		            		turnTransitionActivity.add(map);
		            	}
		            	
		            	if(historicVariableInstance.getVariableName().startsWith("jumpSequence")){
		            		Map<String, Object> map = new HashMap<String, Object>();
		            		String value = historicVariableInstance.getValue().toString();
		            		if(StringUtils.isNotEmpty(value) && value.indexOf(":")!=-1){
		            			String[] split = value.split(":");
		            			map.put("type", "jump");
		            			map.put("sourceId", split[0]);
		            			map.put("targetId", split[1]);
		            		}
		            		turnTransitionActivity.add(map);
		            	}
		            	
		            	
		            }
		            BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
		            ProcessDiagramGenerator processDiagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator(); 
		            InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, finishedActiveActivityIds, highLightedFlows,activeActivityIds,turnTransitionActivity); 
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
	public Page<Map<String,Object>> getHistoricTaskinst(int pageNumber,int pageSize, String processInstanceId) {
		String sql =  "select t.*,u.name assignee_name from  act_hi_taskinst t,bpm_user u where proc_inst_id_='"+processInstanceId+"' and u.id(+)=t.assignee_ order by start_time_ asc";
		Page<Map<String,Object>> page = DataBaseUtils.queryForPage(sql, pageSize, pageNumber);
	    return page;
	}
	
}
	
	