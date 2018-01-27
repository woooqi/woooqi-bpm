package com.titan.service.bpm;

import java.util.List;

import org.activiti.engine.runtime.Execution;

import com.titan.entity.bpm.bpm.Instance;

public interface InstanceService {

	public List<Instance> getAllInstance(String name);
	
	public Instance getInstanceByDeployId(String deployId);
	
	public Execution getInstanceByInstanceId(String instanceId);
	
	public void pendInstance(String instanceId);
	
	public void activeInstance(String instanceId);
	
	public byte[] getActivitiProccessImage(String processInstanceId);
	
}
