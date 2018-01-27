package com.woooqi.bpm.repository.bpm.manage;

import java.util.List;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.manage.ProcessNodeEvent;


public interface ProcessNodeEventRepository extends JpaRepository<ProcessNodeEvent,String>{
	
	public List<ProcessNodeEvent> findByActivitiId(String activitiId);

}
