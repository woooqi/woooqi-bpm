package com.titan.repository.bpm.manage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.manage.ProcessFlow;


public interface ProcessManageFlowRepository extends JpaRepository<ProcessFlow,String>{
	
	public ProcessFlow findByActivitiId(String activitiId);

	public List<ProcessFlow> findByDefinitionId(String definitionId);
}
