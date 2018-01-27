package org.activiti.engine.impl.history.parse;

import java.util.HashSet;
import java.util.Set;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.EventGateway;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.InclusiveGateway;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.ManualTask;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.bpmn.model.ReceiveTask;
import org.activiti.bpmn.model.ScriptTask;
import org.activiti.bpmn.model.SendTask;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.SignTask;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.Task;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.history.handler.ActivityInstanceEndHandler;
import org.activiti.engine.impl.history.handler.ActivityInstanceStartHandler;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.parse.BpmnParseHandler;

public class FlowNodeHistoryParseHandler implements BpmnParseHandler {
  
  protected static final ActivityInstanceEndHandler ACTIVITI_INSTANCE_END_LISTENER = new ActivityInstanceEndHandler();

  protected static final ActivityInstanceStartHandler ACTIVITY_INSTANCE_START_LISTENER = new ActivityInstanceStartHandler();
  
  protected static Set<Class<? extends BaseElement>> supportedElementClasses = new HashSet<Class<? extends BaseElement>>();
  
  static {
    supportedElementClasses.add(EndEvent.class);
    supportedElementClasses.add(ThrowEvent.class);
    supportedElementClasses.add(BoundaryEvent.class);
    supportedElementClasses.add(IntermediateCatchEvent.class);

    supportedElementClasses.add(ExclusiveGateway.class);
    supportedElementClasses.add(InclusiveGateway.class);
    supportedElementClasses.add(ParallelGateway.class);
    supportedElementClasses.add(EventGateway.class);
    
    supportedElementClasses.add(Task.class);
    supportedElementClasses.add(ManualTask.class);
    supportedElementClasses.add(ReceiveTask.class);
    supportedElementClasses.add(ScriptTask.class);
    supportedElementClasses.add(ServiceTask.class);
    supportedElementClasses.add(BusinessRuleTask.class);
    supportedElementClasses.add(SendTask.class);
    supportedElementClasses.add(UserTask.class);
    supportedElementClasses.add(SignTask.class);
    
    supportedElementClasses.add(CallActivity.class);
    supportedElementClasses.add(SubProcess.class);
  }
  
  public Set<Class< ? extends BaseElement>> getHandledTypes() {
    return supportedElementClasses;
  }

  public void parse(BpmnParse bpmnParse, BaseElement element) {
    ActivityImpl activity = bpmnParse.getCurrentScope().findActivity(element.getId());
    if(element instanceof BoundaryEvent) {
    	// A boundary-event never receives an activity start-event
    	activity.addExecutionListener(org.activiti.engine.impl.pvm.PvmEvent.EVENTNAME_END, ACTIVITY_INSTANCE_START_LISTENER, 0);
    	activity.addExecutionListener(org.activiti.engine.impl.pvm.PvmEvent.EVENTNAME_END, ACTIVITI_INSTANCE_END_LISTENER, 1);
    } else {
    	activity.addExecutionListener(org.activiti.engine.impl.pvm.PvmEvent.EVENTNAME_START, ACTIVITY_INSTANCE_START_LISTENER, 0);
    	activity.addExecutionListener(org.activiti.engine.impl.pvm.PvmEvent.EVENTNAME_END, ACTIVITI_INSTANCE_END_LISTENER);
    }
  }

}
