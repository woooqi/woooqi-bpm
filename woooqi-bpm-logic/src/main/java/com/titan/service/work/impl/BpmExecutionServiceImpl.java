package com.titan.service.work.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.titan.service.work.BpmExecutionService;
import com.titan.core.cmd.SplitTaskCommand;
import com.titan.core.cmd.JumpTaskCommand;
import com.titan.core.creater.SignActivitiesCreator;
import com.titan.core.creater.MultiInstanceActivityCreator;
import com.titan.core.creater.RuntimeActivityDefinitionEntityIntepreter;
import com.titan.core.creater.MultiInstanceRuntimeActivityDefinitionEntity;
import com.titan.core.utils.ProcessDefUtils;
import com.titan.utils.PageUtils;

@Service
public class BpmExecutionServiceImpl implements BpmExecutionService{
	
}
