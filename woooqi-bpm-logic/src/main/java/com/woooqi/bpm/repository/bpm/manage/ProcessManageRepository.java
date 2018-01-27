package com.woooqi.bpm.repository.bpm.manage;

import com.woooqi.bpm.entity.bpm.manage.ProcessManage;
import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.manage.ProcessManage;


public interface ProcessManageRepository extends JpaRepository<ProcessManage,String>{
	
	public ProcessManage findByDefinitionId(String definitionId);

}
