package com.woooqi.bpm.repository.bpm.manage;


import java.util.List;

import com.woooqi.bpm.entity.bpm.bpm.ProcessDefinitionPost;
import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.bpm.ProcessDefinitionDept;
import com.titan.entity.bpm.bpm.ProcessDefinitionPost;


public interface DefinitionPostRepository extends JpaRepository<ProcessDefinitionPost,String>{
	
	public ProcessDefinitionPost findById(String id);
	
	public List<ProcessDefinitionPost> findByDefinitionId(String definitionId);

}
