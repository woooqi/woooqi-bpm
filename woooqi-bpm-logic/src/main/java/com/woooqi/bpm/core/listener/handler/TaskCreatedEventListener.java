package com.woooqi.bpm.core.listener.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.woooqi.bpm.service.bpm.NodeManageService;
import com.woooqi.bpm.service.bpm.ProcessNodeAssignService;
import com.woooqi.bpm.service.bpm.ProcessNodeEventService;
import com.woooqi.bpm.service.bpm.ProcessNodeSignService;
import com.woooqi.bpm.service.organization.RoleService;
import com.woooqi.bpm.service.organization.UserService;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.titan.core.listener.EventHandler;
import com.titan.core.listener.base.TaskCreateListener;
import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.bpm.manage.ProcessNodeEvent;
import com.titan.entity.organization.DeptAndAssignType;
import com.titan.entity.organization.PostAndAssignType;
import com.titan.entity.organization.RoleAndAssignType;
import com.titan.entity.organization.User;
import com.titan.repository.bpm.manage.ProcessNodeAssignTypeRepository;
import com.titan.repository.organization.DeptAndAssignTypeRepository;
import com.titan.repository.organization.PostAndAssignTypeRepository;
import com.titan.repository.organization.RoleAndAssignTypeRepository;
import com.titan.service.bpm.NodeManageService;
import com.titan.service.bpm.ProcessNodeAssignService;
import com.titan.service.bpm.ProcessNodeEventService;
import com.titan.service.bpm.ProcessNodeSignService;
import com.titan.service.organization.RoleService;
import com.titan.service.organization.UserService;
import com.titan.utils.PageUtils;

public class TaskCreatedEventListener implements EventHandler{
	
	@Autowired
	private ProcessNodeAssignService processNodeAssignService;
	
	@Autowired
	private ProcessNodeEventService processNodeEventService;
	
	@Autowired
	private ProcessNodeSignService processNodeSignService;
	
	@Autowired
	private ProcessNodeAssignTypeRepository processNodeAssignTypeRepository;
	
	@Autowired
	private RoleAndAssignTypeRepository roleAndAssignTypeRepository;
	
	@Autowired
	private DeptAndAssignTypeRepository deptAndAssignTypeRepository;

	@Autowired
	private PostAndAssignTypeRepository postAndAssignTypeRepository;
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	private NodeManageService nodeMangeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	public void handle(ActivitiEvent event) {		
		ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl)event; 
		if(eventImpl.getEntity() instanceof TaskEntity){
			TaskEntity taskEntity = (TaskEntity)eventImpl.getEntity();
			doTaskListener(taskEntity);
			FlowElement flowElement = nodeMangeService.getNodesByDefinitionIdAndActId(taskEntity.getProcessDefinitionId(), taskEntity.getTaskDefinitionKey());
			doTaskUser(taskEntity,flowElement.getClass().getSimpleName());			
		}
	}

	private void doTaskUser(TaskEntity taskEntity,String taskType) {
		if(taskType.equals("UserTask")){
			ProcessNodeAssign processNodeAssign  = processNodeAssignService.getNodeManageAssignByActId(taskEntity.getTaskDefinitionKey());
			if(processNodeAssign!=null && "1".equals(processNodeAssign.getInitiator())){
				User currentUser = PageUtils.getCurrentUser();
				//解决第一个节点问题
				HistoricProcessInstance  hpi= historyService.createHistoricProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult();
				if(hpi==null){
					taskEntity.setAssignee(currentUser.getId()); 
					return;
					
				}
				HistoricProcessInstance singleResultUser = historyService.createHistoricProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult();
				//解决第一个节点问题
				if(singleResultUser!=null){
					taskEntity.setAssignee(singleResultUser.getStartUserId());
					return;
				}
				
			}
			List<ProcessNodeAssignType> processNodeAssignTypes = processNodeAssignTypeRepository.findByAssign(processNodeAssign);
			for(ProcessNodeAssignType p : processNodeAssignTypes){
				if("1".equals(p.getType())){
					List<User> users = userService.getUserByAssignTypeId(p.getId());
					if(users!=null && users.size()!=0){
						taskEntity.setAssignee(users.get(0).getId());
					}
				}else if("2".equals(p.getType())){
					List<User> users = userService.getUserByAssignTypeId(p.getId());
					Collection<String> coll = new ArrayList<String>();
					for(User u:users){
						coll.add(u.getId());
					}
					if(coll!=null&&coll.size()!=0){
						taskEntity.addCandidateUsers(coll);
						//taskEntity.addCandidateUsers();
					}
					
				}else if("3".equals(p.getType())){
					List<RoleAndAssignType> roles = roleAndAssignTypeRepository.findByAssignType(p);
					List<DeptAndAssignType> depts = deptAndAssignTypeRepository.findByAssignType(p);
					List<PostAndAssignType> posts = postAndAssignTypeRepository.findByAssignType(p);
					Collection<String> coll = new ArrayList<String>();
					for(RoleAndAssignType roleAndAssignType:roles){
						if(roleAndAssignType.getRole()!=null){
							coll.add(roleAndAssignType.getRole().getId());
						}
						
					}
					for(DeptAndAssignType deptAndAssignType:depts){
						if(deptAndAssignType.getDept()!=null){
							coll.add(deptAndAssignType.getDept().getId());
						}
						
					}
					for(PostAndAssignType postAndAssignType:posts){
						if(postAndAssignType.getPost()!=null){
							coll.add(postAndAssignType.getPost().getId());
						}
					}
					if(coll!=null&&coll.size()!=0){
						taskEntity.addCandidateGroups(coll);
					}
				}
			}
		}else if(taskType.equals("SignTask")){
			//taskEntity.setAssignee("${assignee}");
		}
		
	}
	
	private void doTaskListener(TaskEntity taskEntity) {
		try {
			List<ProcessNodeEvent> lists  = processNodeEventService.getProcessNodeEventByActId(taskEntity.getTaskDefinitionKey());
			for(ProcessNodeEvent processNodeEvent:lists){
				   Class<?> onwClass = Class.forName(processNodeEvent.getClass_name());
				   if("1".equals(processNodeEvent.getType())){
					   TaskCreateListener h = (TaskCreateListener)onwClass.newInstance();
							 h.handle(taskEntity);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
					  
				   }
			}
			
		} catch (Exception e) {
		}
		
	}
	
}
