package com.woooqi.bpm.repository.bpm.manage;


import java.util.List;

import com.woooqi.bpm.entity.bpm.bpm.ProcessDefinitionRole;
import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.bpm.ProcessDefinitionDept;
import com.titan.entity.bpm.bpm.ProcessDefinitionRole;


public interface DefinitionRoleRepository extends JpaRepository<ProcessDefinitionRole,String>{
	
	public ProcessDefinitionRole findById(String id);
	
	public List<ProcessDefinitionRole> findByDefinitionId(String definitionId);

}
