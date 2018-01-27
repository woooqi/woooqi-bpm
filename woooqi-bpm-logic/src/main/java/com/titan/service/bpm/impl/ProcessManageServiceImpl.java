package com.titan.service.bpm.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.manage.ProcessManage;
import com.titan.repository.bpm.manage.ProcessManageRepository;
import com.titan.service.bpm.ProcessManageService;

@Service
public class ProcessManageServiceImpl implements ProcessManageService{
	
	@Autowired
	private ProcessManageRepository processManageRepository;

	@Override
	public ProcessManage findByDefinitionId(String definitionId) {
		
		return processManageRepository.findByDefinitionId(definitionId);
	}

	@Override
	public Map<String, Object> saveAndFlushProcessManage(ProcessManage processManage) {
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isNotEmpty(processManage.getId())){
			processManage.setId(null);
		}
		ProcessManage saveProcessManage = processManageRepository.saveAndFlush(processManage);
		if(saveProcessManage==null){
			map.put("code", -1);
			map.put("msg", "操作失败");
			
		}else{
			map.put("code", 1);
			map.put("msg", "操作成功");
		}
		return map;
	}

	
}
