package com.titan.service.work.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.core.cmd.BaseTaskCommand;
import com.titan.entity.work.Task;
import com.titan.service.work.WorkReceivedService;
import com.titan.utils.DataBaseUtils;
@Service
public class WorkReceivedServiceImpl implements WorkReceivedService {
	@Autowired
	private	TaskService taskService;
	
	@Autowired 
	private BaseTaskCommand baseTaskCommand;
	
	
	@Override
	public List<Task> getAllTasks(String name) {
		List<String> args = new ArrayList<String>();
		String sql = "select distinct t.task_def_key_ from act_ru_task t";
		if( name != null && !name.equals("")){
			sql = sql + " where t.name_ = ?";
			args.add(name);
		}
		List<Task> tasks = DataBaseUtils.queryForListBean(sql,Task.class,args.toArray());
		return tasks;
	}

	@Override
	public List<Task> getCandidateTasks(String userId) {
		String sql = "select a.* ,c.name_ as proc_def_name_ from act_ru_task a,act_ru_identitylink b,act_re_procdef c where a.id_ = b.task_id_ and b.type_ = 'candidate' and b.user_id_ is not null and a.assignee_ is null and a.proc_def_id_ = c.id_ and b.user_id_ = ?";
		List<Task> tasks = DataBaseUtils.queryForListBean(sql, Task.class, userId);
		return tasks;
	}

	@Override
	public List<Task> getCandidateGroupTasks(String groupId) {
		List<Task> tasks = new ArrayList<Task>();
		
		String[] groupIds = groupId.split(",");
		for(int i = 0;i<groupIds.length;i++){
			String sql = "select a.*,c.name_ as proc_def_name_ from act_ru_task a,act_ru_identitylink b,act_re_procdef c where a.id_ = b.task_id_ and b.type_ = 'candidate' and a.assignee_ is null and b.group_id_ is not null and a.proc_def_id_ = c.id_ and ? in b.group_id_";
			List<Task> task = DataBaseUtils.queryForListBean(sql, Task.class, groupIds[i]);
			if(task.size() > 0){
				tasks.addAll(task);
			}
		}
		return tasks;
	}

	@Override
	public void recevieTask(String taskId, String userId) {
		try {
			taskService.claim(taskId, userId);
		} catch (ActivitiTaskAlreadyClaimedException e) {
			String mes = "该任务已经被领取";
		} catch (Exception e) {
			String mes = "领取异常";
		}
		
	}
	
}
