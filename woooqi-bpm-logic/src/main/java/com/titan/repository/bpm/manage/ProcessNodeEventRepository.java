package com.titan.repository.bpm.manage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.manage.ProcessNodeEvent;


public interface ProcessNodeEventRepository extends JpaRepository<ProcessNodeEvent,String>{
	
	public List<ProcessNodeEvent> findByActivitiId(String activitiId);

}
