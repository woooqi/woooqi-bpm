package com.woooqi.bpm.repository.organization;

import java.util.List;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeAssignType;
import com.woooqi.bpm.entity.organization.DeptAndAssignType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.organization.DeptAndAssignType;

public interface DeptAndAssignTypeRepository extends JpaRepository<DeptAndAssignType,String>,JpaSpecificationExecutor<DeptAndAssignType>{
 
	  public List<DeptAndAssignType> findByAssignType(ProcessNodeAssignType assignType);
	
}
