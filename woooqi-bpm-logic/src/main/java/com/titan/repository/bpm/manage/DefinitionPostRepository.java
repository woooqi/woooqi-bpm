package com.titan.repository.bpm.manage;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.bpm.ProcessDefinitionDept;
import com.titan.entity.bpm.bpm.ProcessDefinitionPost;


public interface DefinitionPostRepository extends JpaRepository<ProcessDefinitionPost,String>{
	
	public ProcessDefinitionPost findById(String id);
	
	public List<ProcessDefinitionPost> findByDefinitionId(String definitionId);

}
