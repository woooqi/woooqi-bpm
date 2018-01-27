package com.titan.service.work;

import java.util.List;

import com.titan.entity.work.Task;


public interface WorkStartedService {
	
	public List<Task> getAllTasks(String name);

	public List<Task> getAssignTasks(String userId);

	//public List<Task> getCandidateTasks(String userId);

	//public List<Task> getCandidateGroupTasks(String groupId);	
	
}
