package org.activiti.engine.impl.history.handler;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

public class SignTaskIdHandler implements TaskListener {

  public void notify(DelegateTask task) {
    Context.getCommandContext().getHistoryManager()
      .recordTaskId((TaskEntity) task);
  }
  
}
