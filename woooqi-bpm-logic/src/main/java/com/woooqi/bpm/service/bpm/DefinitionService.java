package com.woooqi.bpm.service.bpm;

import java.util.List;

import com.titan.entity.bpm.bpm.ProcessDefinition;

public interface DefinitionService {
	
	public List<ProcessDefinition> getAllDefinition();
	
	public List<ProcessDefinition> getDefinitionByName(String name);
	
	public ProcessDefinition getDefinitionByDefinitionId(String definitionId);
	
	public void delDeploy(String deployId) throws Exception;
	
	public String deploymentOnLine(String fileName,byte[] bytes) throws Exception;
		
	public String deploymentOnWeb(String deployId);
	
	public List<ProcessDefinition> getDefinitionByCategoryId(String parentId);
}
