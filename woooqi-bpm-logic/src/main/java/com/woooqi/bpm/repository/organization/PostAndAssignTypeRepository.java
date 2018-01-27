package com.woooqi.bpm.repository.organization;

import java.util.List;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeAssignType;
import com.woooqi.bpm.entity.organization.PostAndAssignType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.organization.PostAndAssignType;

public interface PostAndAssignTypeRepository extends JpaRepository<PostAndAssignType,String>,JpaSpecificationExecutor<PostAndAssignType>{
 
	  public List<PostAndAssignType> findByAssignType(ProcessNodeAssignType assignType);
	
}
