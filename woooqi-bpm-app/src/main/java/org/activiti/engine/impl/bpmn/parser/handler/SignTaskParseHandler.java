package org.activiti.engine.impl.bpmn.parser.handler;

import java.util.HashSet;
import java.util.Set;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.SignTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.calendar.DueDateBusinessCalendar;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.form.DefaultTaskFormHandler;
import org.activiti.engine.impl.form.TaskFormHandler;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.apache.commons.lang3.StringUtils;

public class SignTaskParseHandler extends AbstractActivityBpmnParseHandler<SignTask> {
  
  public static final String PROPERTY_TASK_DEFINITION = "taskDefinition";
  public static final String ELEMENT_TASK_SIGN = "signTask";
  
  public Class< ? extends BaseElement> getHandledType() {
    return SignTask.class;
  }
  
  protected void executeParse(BpmnParse bpmnParse, SignTask signTask) {
    ActivityImpl activity = createActivityOnCurrentScope(bpmnParse, signTask, ELEMENT_TASK_SIGN);
    
    activity.setAsync(signTask.isAsynchronous());
    activity.setExclusive(!signTask.isNotExclusive()); 
    
    TaskDefinition taskDefinition = parseTaskDefinition(bpmnParse, signTask, signTask.getId(), (ProcessDefinitionEntity) bpmnParse.getCurrentScope().getProcessDefinition());
    activity.setProperty(PROPERTY_TASK_DEFINITION, taskDefinition);
    activity.setActivityBehavior(bpmnParse.getActivityBehaviorFactory().createSignTaskActivityBehavior(signTask, taskDefinition));
  }
  
  public TaskDefinition parseTaskDefinition(BpmnParse bpmnParse, SignTask signTask, String taskDefinitionKey, ProcessDefinitionEntity processDefinition) {
    TaskFormHandler taskFormHandler = new DefaultTaskFormHandler();
    taskFormHandler.parseConfiguration(signTask.getFormProperties(), signTask.getFormKey(), bpmnParse.getDeployment(), processDefinition);

    TaskDefinition taskDefinition = new TaskDefinition(taskFormHandler);

    taskDefinition.setKey(taskDefinitionKey);
    processDefinition.getTaskDefinitions().put(taskDefinitionKey, taskDefinition);
    ExpressionManager expressionManager = bpmnParse.getExpressionManager();

    if (StringUtils.isNotEmpty(signTask.getName())) {
      taskDefinition.setNameExpression(expressionManager.createExpression(signTask.getName()));
    }

    if (StringUtils.isNotEmpty(signTask.getDocumentation())) {
      taskDefinition.setDescriptionExpression(expressionManager.createExpression(signTask.getDocumentation()));
    }

    if (StringUtils.isNotEmpty(signTask.getAssignee())) {
      taskDefinition.setAssigneeExpression(expressionManager.createExpression(signTask.getAssignee()));
    }
    if (StringUtils.isNotEmpty(signTask.getOwner())) {
      taskDefinition.setOwnerExpression(expressionManager.createExpression(signTask.getOwner()));
    }
    for (String candidateUser : signTask.getCandidateUsers()) {
      taskDefinition.addCandidateUserIdExpression(expressionManager.createExpression(candidateUser));
    }
    for (String candidateGroup : signTask.getCandidateGroups()) {
      taskDefinition.addCandidateGroupIdExpression(expressionManager.createExpression(candidateGroup));
    }
    
    // Activiti custom extension
    
    // Task listeners
    for (ActivitiListener taskListener : signTask.getTaskListeners()) {
      taskDefinition.addTaskListener(taskListener.getEvent(), createTaskListener(bpmnParse, taskListener, signTask.getId()));
    }

    // Due date
    if (StringUtils.isNotEmpty(signTask.getDueDate())) {
      taskDefinition.setDueDateExpression(expressionManager.createExpression(signTask.getDueDate()));
    }

    // Business calendar name
    if (StringUtils.isNotEmpty(signTask.getBusinessCalendarName())) {
      taskDefinition.setBusinessCalendarNameExpression(expressionManager.createExpression(signTask.getBusinessCalendarName()));
    } else {
      taskDefinition.setBusinessCalendarNameExpression(expressionManager.createExpression(DueDateBusinessCalendar.NAME));
    }

    // Category
    if (StringUtils.isNotEmpty(signTask.getCategory())) {
    	taskDefinition.setCategoryExpression(expressionManager.createExpression(signTask.getCategory()));
    }
    
    // Priority
    if (StringUtils.isNotEmpty(signTask.getPriority())) {
      taskDefinition.setPriorityExpression(expressionManager.createExpression(signTask.getPriority()));
    }
    
    if (StringUtils.isNotEmpty(signTask.getFormKey())) {
    	taskDefinition.setFormKeyExpression(expressionManager.createExpression(signTask.getFormKey()));
    }

    // CustomUserIdentityLinks
    for (String customUserIdentityLinkType : signTask.getCustomUserIdentityLinks().keySet()) {
    	Set<Expression> userIdentityLinkExpression = new HashSet<Expression>();
    	for (String userIdentityLink : signTask.getCustomUserIdentityLinks().get(customUserIdentityLinkType)) {
    		userIdentityLinkExpression.add(expressionManager.createExpression(userIdentityLink));
    	}
    	taskDefinition.addCustomUserIdentityLinkExpression(customUserIdentityLinkType, userIdentityLinkExpression);
      }
    
    // CustomGroupIdentityLinks
    for (String customGroupIdentityLinkType : signTask.getCustomGroupIdentityLinks().keySet()) {
    	Set<Expression> groupIdentityLinkExpression = new HashSet<Expression>();
    	for (String groupIdentityLink : signTask.getCustomGroupIdentityLinks().get(customGroupIdentityLinkType)) {
    		groupIdentityLinkExpression.add(expressionManager.createExpression(groupIdentityLink));
    	}
    	taskDefinition.addCustomGroupIdentityLinkExpression(customGroupIdentityLinkType, groupIdentityLinkExpression);
      }

    if (StringUtils.isNotEmpty(signTask.getSkipExpression())) {
      taskDefinition.setSkipExpression(expressionManager.createExpression(signTask.getSkipExpression()));
    }
    
    return taskDefinition;
  }
  
  protected TaskListener createTaskListener(BpmnParse bpmnParse, ActivitiListener activitiListener, String taskId) {
    TaskListener taskListener = null;

    if (ImplementationType.IMPLEMENTATION_TYPE_CLASS.equalsIgnoreCase(activitiListener.getImplementationType())) {
      taskListener = bpmnParse.getListenerFactory().createClassDelegateTaskListener(activitiListener); 
    } else if (ImplementationType.IMPLEMENTATION_TYPE_EXPRESSION.equalsIgnoreCase(activitiListener.getImplementationType())) {
      taskListener = bpmnParse.getListenerFactory().createExpressionTaskListener(activitiListener);
    } else if (ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION.equalsIgnoreCase(activitiListener.getImplementationType())) {
      taskListener = bpmnParse.getListenerFactory().createDelegateExpressionTaskListener(activitiListener);
    }
    return taskListener;
  }

}
