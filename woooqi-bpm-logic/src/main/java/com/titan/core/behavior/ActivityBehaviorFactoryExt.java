package com.titan.core.behavior;

import org.activiti.bpmn.model.ExclusiveGateway;  
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;  
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;  
 
public class ActivityBehaviorFactoryExt extends DefaultActivityBehaviorFactory {  
      
    private ExclusiveGatewayActivityBehaviorExt exclusiveGatewayActivityBehaviorExt;  
      
    public void setExclusiveGatewayActivityBehaviorExt(ExclusiveGatewayActivityBehaviorExt exclusiveGatewayActivityBehaviorExt) {  
        this.exclusiveGatewayActivityBehaviorExt = exclusiveGatewayActivityBehaviorExt;  
    }  

    @Override  
    public ExclusiveGatewayActivityBehavior createExclusiveGatewayActivityBehavior(ExclusiveGateway exclusiveGateway) {  
        return exclusiveGatewayActivityBehaviorExt;  
    }  
}  
