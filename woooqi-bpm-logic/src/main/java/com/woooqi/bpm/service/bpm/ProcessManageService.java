package com.woooqi.bpm.service.bpm;

import java.util.Map;

import com.titan.entity.bpm.manage.ProcessManage;

public interface ProcessManageService {
	public ProcessManage findByDefinitionId(String definitionId);
	
	public Map<String, Object> saveAndFlushProcessManage(ProcessManage processManage);

}
