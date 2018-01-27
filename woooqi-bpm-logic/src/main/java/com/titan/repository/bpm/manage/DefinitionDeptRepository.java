package com.titan.repository.bpm.manage;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.bpm.ProcessDefinitionDept;
import com.titan.entity.bpm.bpm.ProcessDefinitionRole;


public interface DefinitionDeptRepository extends JpaRepository<ProcessDefinitionDept,String>{
	
	public ProcessDefinitionDept findById(String id);
	
	public List<ProcessDefinitionDept> findByDefinitionId(String definitionId);

}
