package com.titan.core.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.titan.core.creater.MultiInstanceActivityCreator;
import com.titan.core.creater.MultiInstanceRuntimeActivityDefinitionEntity;
import com.titan.core.creater.RuntimeActivityDefinitionEntityIntepreter;
import com.titan.core.creater.SignActivitiesCreator;
import com.titan.core.utils.ProcessDefUtils;
import com.titan.utils.PageUtils;

public class BaseTaskCommand {
	
	@Autowired 
	private RuntimeService runtimeService;
	@Autowired
	private	TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private IdentityService identityService;
	
	/**
	 * 启动流程
	 * @param processKey
	 * @param bussinesskey
	 * @param param
	 */
	public void startProcess(String processKey, String bussinesskey, Map<String, Object> param) {
		identityService.setAuthenticatedUserId(PageUtils.getCurrentUser().getId());
		runtimeService.startProcessInstanceByKey(processKey,bussinesskey,param);
	}
	/**
	 * 启动并执行一个节点
	 * @param processKey
	 * @param bussinesskey
	 * @param param
	 */
	public void startFirstProcess(String processKey, String bussinesskey,Map<String, Object> param) {
		identityService.setAuthenticatedUserId(PageUtils.getCurrentUser().getId());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processKey,bussinesskey,param);
		Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		completeTask(task.getId(), param);
	}
	/**
	 * 结束流程
	 * @param processKey
	 * @param bussinesskey
	 * @param deleteReason
	 */
	public void endProcess(String processKey,String bussinesskey,String deleteReason) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(bussinesskey,processKey).singleResult();
		runtimeService.deleteProcessInstance(pi.getProcessInstanceId(),deleteReason);
	}
	/**
	 * 结束流程
	 * @param taskId
	 */
	public void endProcess(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		runtimeService.deleteProcessInstance(task.getProcessInstanceId(),"");		
	}

	public Task getCurrentTask(String processKey, String bussinesskey) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(bussinesskey,processKey).singleResult();
		return taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
	}
	
	public Task getTask(String processKey, String bussinesskey, String taskName) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(bussinesskey,processKey).singleResult();
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).list();
		Task task = null;
		if(tasks.size()>1){
			for(Task t:tasks){
				if(t.getName().equals(taskName)){
					task = t;
					break;
				}
			}
		}else{
			task = tasks.get(0);
		}
		return task;
	}
	
	public ActivityImpl getBackTask(String processKey, String bussinesskey) {
		
		Task task = getCurrentTask(processKey, bussinesskey);

		 List<ActivityImpl> alist =  this.findBackAvtivity(task.getId());
		 if(alist.size() == 0){
			 throw new RuntimeException("BackTask is null");
		 }
		 ActivityImpl avtivity = alist.get(alist.size() - 1);
	
		return avtivity;
	}
	/**
	 * 获取回去到上一个节点的节点信息
	 * @param taskId
	 * @return
	 */
	public ActivityImpl getBackTask(String taskId) {

		 List<ActivityImpl> alist =  this.findBackAvtivity(taskId);
		 if(alist.size() == 0){
			 throw new RuntimeException("BackTask is null");
		 }
		 ActivityImpl avtivity = alist.get(alist.size() - 1);
	
		return avtivity;
	}
	/**
	 * 完成一个节点
	 * @param taskId 任务taskId
	 * @param param 流程变量
	 */
	public void completeTask(String taskId, Map<String,Object> param) {
		taskService.complete(taskId, param);
	}
	/**
	 * 完成一个节点
	 * @param processKey
	 * @param bussinesskey
	 * @param param
	 */
	public void completeTask(String processKey, String bussinesskey, Map<String,Object> param) {
		taskService.complete(getCurrentTask(processKey, bussinesskey).getId(), param);
	}
	/**
	 * 回退上一个节点
	 * @param taskId 任务taskId
	 * @param param 流程变量
	 * @throws Exception 
	 */
	public void fallbackTask(String taskId, Map<String,Object> param) throws Exception{
		ActivityImpl targetActivity = this.getBackTask(taskId);
		ActivityImpl currentActivity = this.findCurrentActivity(taskId);
		if(targetActivity!=null && currentActivity!=null){
			param = new HashMap<String, Object>();
			param.put("backSequence:"+PageUtils.getUUID(), currentActivity.getId()+":"+targetActivity.getId());
		}
		this.taskBack(taskId, targetActivity, param);
	}
	/**
	 * 回退开始节点
	 * @param taskId 任务taskId
	 * @param param 流程变量
	 */
	public void fallInitalTask(String taskId,Map<String, Object> param) throws Exception{
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntity(taskId);
		ActivityImpl targetActivity = processDefinition.getInitial();
		
		List<PvmTransition> outgoingTransitions = targetActivity.getOutgoingTransitions();
		for(PvmTransition pvmTransition :outgoingTransitions){
			targetActivity = (ActivityImpl) pvmTransition.getDestination();
		}
		
		ActivityImpl currentActivity = this.findCurrentActivity(taskId);
		if(targetActivity!=null && currentActivity!=null){
			param = new HashMap<String, Object>();
			param.put("backSequence:"+PageUtils.getUUID(), currentActivity.getId()+":"+targetActivity.getId());
		}

		this.taskBack(taskId, targetActivity, param);
	}
	
	/**
	 * 任务领取
	 * @param processKey 流程key
	 * @param bussinesskey 业务key
	 * @param userId 会签人员ID
	 */
	public void taskAssign(String processKey, String bussinesskey, String userId) {
		taskService.claim(getCurrentTask(processKey,bussinesskey).getId(), userId);
	}
	
	/**
	 * 任务领取
	 * @param taskId 任务taskID
	 * @param userId 会签人员ID
	 */
	public void taskAssign(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}
	/**
	 * 代理任务
	 * @param taskId 任务taskId
	 * @param ownerId 任务拥有者
	 * @param agentId 任务代理者
	 */
	public void taskAgent(String taskId, String ownerId,String agentId){
		taskService.setOwner(taskId, ownerId);
		taskService.setAssignee(taskId, agentId);
	}
	/**
	 * 流程是否结束
	 * @param processKey
	 * @param bussinesskey
	 * @return
	 */
	public boolean processIsEnd(String processKey, String bussinesskey) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(bussinesskey,processKey).singleResult();
		if(pi == null){
			return true;
		}else{
			return pi.isEnded();
		}
	}
	/**
	 * 流程是否启动
	 * @param processKey
	 * @param bussinesskey
	 * @return
	 */
	public boolean processIsStart(String processKey, String bussinesskey) {
		Execution singleResult = runtimeService.createExecutionQuery().processInstanceBusinessKey(bussinesskey).singleResult();
		if(singleResult != null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 自由扭转（顺序）
	 * @param taskId 任务taskId
	 * @param targetActivity 目标节点
	 * @param param 流程变量
	 */
	private void taskForward(String taskId,ActivityImpl targetActivity,Map<String,Object> param) {
		
		try {
			turnTransition(taskId,targetActivity,param);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 自由扭转（倒叙序）
	 * @param taskId 任务taskId
	 * @param targetActivity 目标节点
	 * @param param 流程变量
	 */
	private void taskBack(String taskId,ActivityImpl targetActivity, Map<String,Object> param){
		try{
			turnTransition(taskId,targetActivity,param);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
    protected void turnTransition(String taskId, ActivityImpl targetActivity,Map<String, Object> variables) throws Exception {  
    	
    	TaskEntity currentTaskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
    	
    	CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new JumpTaskCommand(currentTaskEntity, targetActivity, variables));
    }  
    
	public ActivityImpl findCurrentActivity(String taskId) throws Exception {
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntity(taskId);
		String	activityId = ((TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult()).getTaskDefinitionKey();
		ActivityImpl activityImpl = processDefinition.findActivity(activityId);
		return activityImpl;
	}
	
    protected ProcessDefinitionEntity findProcessDefinitionEntity(String taskId) throws Exception {  
    	TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();  
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());  
        if (processDefinition == null) {  
            throw new Exception("ProcessDefinition is null");  
        }  
        return processDefinition;  
    } 

	public List<ActivityImpl> findBackAvtivity(String taskId) {
		List<ActivityImpl> backActivityImpls = new ArrayList<ActivityImpl>();
		try {
			ActivityImpl activity = findCurrentActivity(taskId);
			List<ActivityImpl> allActivity = null;
			if(activity.getParentActivity() == null){
				allActivity = findAllActivity(taskId,true);
			}else{
				allActivity = findAllActivity(taskId,false);
			}
			backActivityImpls = iteratorBackActivity(activity, new ArrayList<ActivityImpl>(),allActivity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return reverList(backActivityImpls);
	}
	
	public List<ActivityImpl> findNextActivity(String taskId){
		List<ActivityImpl> nextActivityImpls = new ArrayList<ActivityImpl>();
		try {
			ActivityImpl activity = findCurrentActivity(taskId);
			List<ActivityImpl> allActivity = null;
			if(activity.getParentActivity() != null){
				allActivity = findAllActivity(taskId,true);
			}else{
				allActivity = findAllActivity(taskId,false);
			}
			iteratorNextActivity(activity, nextActivityImpls,allActivity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return reverList(nextActivityImpls);
	}
	
	public List<ActivityImpl> findAllActivity(String taskId,Boolean isMainProcess){
		try {
			ProcessDefinitionEntity processDefinition = findProcessDefinitionEntity(taskId);
			List<ActivityImpl> activityImpls = processDefinition.getActivities();
			
			List<ActivityImpl> subActivityImpls = new ArrayList<ActivityImpl>();
			if(!isMainProcess){
				for(ActivityImpl activityImpl:activityImpls){
					if(activityImpl.getProperty("type").equals("subProcess")){
						subActivityImpls.addAll(activityImpl.getActivities());
					}
				}
			}
			activityImpls.addAll(subActivityImpls);
			return activityImpls;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private List<ActivityImpl> iteratorNextActivity(ActivityImpl currActivity, List<ActivityImpl> retList,List<ActivityImpl> allActivity) throws Exception{
		String activitiId = currActivity.getId();
		for(ActivityImpl activityImpl:allActivity){
			String id = activityImpl.getId();
			if(activitiId.equals(id)){
				List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
				for(PvmTransition tr:outTransitions){
					ActivityImpl ac = (ActivityImpl) tr.getDestination();
					String type = ac.getProperty("type").toString();
					if(type.endsWith("Gateway") || type.equals("subProcess")){
						iteratorNextActivity(ac,retList,allActivity);
					}else if(type.equals("endEvent")){
						return retList;
					}else if(type.endsWith("Task")){
						retList.add(ac);
					}
				}
				break;
			}
		}
		return retList;
	}
	
	private List<ActivityImpl> iteratorBackActivity(ActivityImpl currActivity, List<ActivityImpl> retList,List<ActivityImpl> allActivity) throws Exception {
		
		String activitiId = currActivity.getId();
		for(ActivityImpl activityImpl:allActivity){
			String id = activityImpl.getId();
			if(activitiId.equals(id)){
				List<PvmTransition> inTransitions = activityImpl.getIncomingTransitions();
				for(PvmTransition tr:inTransitions){
					ActivityImpl ac = (ActivityImpl) tr.getSource();
					String type = ac.getProperty("type").toString();
					if(type.endsWith("Gateway") || type.equals("subProcess")){
						iteratorBackActivity(ac,retList,allActivity);
					}else if(type.equals("startEvent")){
						return retList;
					}else if(type.endsWith("Task")){
						retList.add(ac);
					}
				}
				//break;
			}
		}
		return retList;
	}
	
	private List<ActivityImpl> reverList(List<ActivityImpl> list) {
		List<ActivityImpl> returnList = new ArrayList<ActivityImpl>();
		for (int i = list.size(); i > 0; i--) {
			if (!returnList.contains(list.get(i - 1)))
				returnList.add(list.get(i - 1));
		}
		return returnList;
	}
	
	//后加签
	public ActivityImpl[] signTasksAfter(String processDefinitionId, String processInstanceId, String targetActivityId, Map<String, Object> variables, String... assignees) {
		
		List<String> assigneeList = new ArrayList<String>();
		assigneeList.add(PageUtils.getCurrentUser().getId());
		assigneeList.addAll(CollectionUtils.arrayToList(assignees));
		String[] newAssignees = assigneeList.toArray(new String[0]);
		
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		ActivityImpl prototypeActivity = ProcessDefUtils.getActivity(processEngine, processDefinition.getId(), targetActivityId);
		return cloneAndMakeChain(processDefinition, processInstanceId, targetActivityId, prototypeActivity.getOutgoingTransitions().get(0).getDestination().getId(), variables, newAssignees);
	}
	
	//前加签
	public ActivityImpl[] signTasksBefore(String processDefinitionId, String processInstanceId, String targetActivityId, Map<String, Object> variables, String... assignees) {
		ProcessDefinitionEntity procDef = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		return cloneAndMakeChain(procDef, processInstanceId, targetActivityId, targetActivityId, variables, assignees);
	}
	
	private ActivityImpl[] cloneAndMakeChain(ProcessDefinitionEntity processDefinition, String processInstanceId, String prototypeActivityId, String nextActivityId, Map<String, Object> variables, String... assignees) {
		MultiInstanceRuntimeActivityDefinitionEntity info = new MultiInstanceRuntimeActivityDefinitionEntity();
		info.setProcessDefinitionId(processDefinition.getId());
		info.setProcessInstanceId(processInstanceId);
		RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);
		radei.setPrototypeActivityId(prototypeActivityId);
		radei.setAssignees(CollectionUtils.arrayToList(assignees));
		radei.setNextActivityId(nextActivityId);
		ActivityImpl[] activities = new SignActivitiesCreator().createActivities(processEngine, processDefinition, info);
		
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		try {
			turnTransition(task.getId(), activities[0], variables);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activities;
	}
	//分裂为多实例
	public ActivityImpl splitTask(String processDefinitionId, String processInstanceId, String targetActivityId, Map<String, Object> variables, boolean isSequential, String... assignees) {
		MultiInstanceRuntimeActivityDefinitionEntity info = new MultiInstanceRuntimeActivityDefinitionEntity();
		info.setProcessDefinitionId(processDefinitionId);
		info.setProcessInstanceId(processInstanceId);

		RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);

		radei.setPrototypeActivityId(targetActivityId);
		radei.setAssignees(CollectionUtils.arrayToList(assignees));
		radei.setSequential(isSequential);
		
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processInstanceId);
		ActivityImpl clone = new MultiInstanceActivityCreator().createActivities(processEngine, processDefinition, info)[0];

		TaskEntity currentTaskEntity = (TaskEntity) taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new SplitTaskCommand(currentTaskEntity, clone, variables));

		return clone;
	}
}