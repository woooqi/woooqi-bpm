package com.titan.service.bpm;

import java.util.List;

import org.activiti.engine.repository.Model;

import com.titan.entity.bpm.bpm.Category;

public interface ModelService {

	public String createModel(String key,String name,String description);

	public List<Model> getAllModel(String name,String key);
	
	public Model getModelByDeployId(String deploymentId);

	public void delModel(String modelId);
	
	public String deploymentOnWeb(String modelId);


}
