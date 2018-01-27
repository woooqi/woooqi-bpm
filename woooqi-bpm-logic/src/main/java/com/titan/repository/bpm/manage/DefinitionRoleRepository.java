package com.titan.repository.bpm.manage;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.bpm.ProcessDefinitionDept;
import com.titan.entity.bpm.bpm.ProcessDefinitionRole;


public interface DefinitionRoleRepository extends JpaRepository<ProcessDefinitionRole,String>{
	
	public ProcessDefinitionRole findById(String id);
	
	public List<ProcessDefinitionRole> findByDefinitionId(String definitionId);

}
