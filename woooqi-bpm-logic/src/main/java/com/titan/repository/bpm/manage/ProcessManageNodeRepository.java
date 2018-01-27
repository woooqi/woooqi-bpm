package com.titan.repository.bpm.manage;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.manage.ProcessNode;


public interface ProcessManageNodeRepository extends JpaRepository<ProcessNode,String>{
	
	public ProcessNode findByActivitiId(String activitiId);
	
}
