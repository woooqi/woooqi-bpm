package org.activiti.engine.impl.history.parse;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.SignTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.AbstractBpmnParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.history.handler.SignTaskAssignmentHandler;
import org.activiti.engine.impl.history.handler.SignTaskIdHandler;
import org.activiti.engine.impl.task.TaskDefinition;

public class SignTaskHistoryParseHandler extends AbstractBpmnParseHandler<SignTask> {
  
  protected static final SignTaskAssignmentHandler USER_TASK_ASSIGNMENT_HANDLER = new SignTaskAssignmentHandler();
  
  protected static final SignTaskIdHandler USER_TASK_ID_HANDLER = new SignTaskIdHandler();
  
  protected Class< ? extends BaseElement> getHandledType() {
    return SignTask.class;
  }
  
  protected void executeParse(BpmnParse bpmnParse,SignTask element) {
    TaskDefinition taskDefinition = (TaskDefinition) bpmnParse.getCurrentActivity().getProperty(UserTaskParseHandler.PROPERTY_TASK_DEFINITION);
    taskDefinition.addTaskListener(TaskListener.EVENTNAME_ASSIGNMENT, USER_TASK_ASSIGNMENT_HANDLER);
    taskDefinition.addTaskListener(TaskListener.EVENTNAME_CREATE, USER_TASK_ID_HANDLER);
  }

}
