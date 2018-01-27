package com.woooqi.bpm.repository.bpm.manage;

import com.woooqi.bpm.entity.bpm.manage.ProcessNode;
import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.manage.ProcessNode;


public interface ProcessManageNodeRepository extends JpaRepository<ProcessNode,String>{
	
	public ProcessNode findByActivitiId(String activitiId);
	
}
