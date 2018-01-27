package com.titan.repository.bpm.manage;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.manage.ProcessNodeForm;

public interface ProcessNodeFormRepository extends JpaRepository<ProcessNodeForm,String>{
	public ProcessNodeForm findByActivitiId(String activitiId);

}
