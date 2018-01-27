package com.titan.service.work.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.work.Task;
import com.titan.service.work.WorkStartedService;
import com.titan.utils.DataBaseUtils;
@Service
public class WorkStaredtServiceImpl implements WorkStartedService {
	@Autowired
	private	TaskService taskService;
	
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
	public List<Task> getAssignTasks(String userId) {
		String sql = "select t.*,t2.name_ as proc_def_name_ from act_ru_task t,act_re_procdef t2 where t.assignee_ = ? and t.proc_def_id_=t2.id_";
		List<Task> tasks = DataBaseUtils.queryForListBean(sql, Task.class, userId);
		return tasks;
	}

	/*@Override
	public List<Task> getCandidateTasks(String userId) {
		String sql = "select a.* from act_ru_task a,act_ru_identitylink b where a.id_ = b.task_id_ and b.type_ = 'candidate' and b.user_id_ is not null and b.user_id_ = ?";
		List<Task> tasks = DataBaseUtils.queryForListBean(sql, Task.class, userId);
		return tasks;
	}

	@Override
	public List<Task> getCandidateGroupTasks(String groupId) {
		List<Task> tasks = new ArrayList<Task>();
		
		String[] groupIds = groupId.split(",");
		for(int i = 0;i<groupIds.length;i++){
			String sql = "select a.* from act_ru_task a,act_ru_identitylink b where a.id_ = b.task_id_ and b.type_ = 'candidate' and b.group_id_ is not null and ? in b.group_id_";
			List<Task> task = DataBaseUtils.queryForListBean(sql, Task.class, groupIds[i]);
			if(task.size() > 0){
				tasks.addAll(task);
			}
		}
		return tasks;
	}*/
	
}
