package com.woooqi.bpm.repository.organization;

import java.util.List;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeAssignType;
import com.woooqi.bpm.entity.organization.RoleAndAssignType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.organization.RoleAndAssignType;

public interface RoleAndAssignTypeRepository extends JpaRepository<RoleAndAssignType,String>,JpaSpecificationExecutor<RoleAndAssignType>{
 
	  public List<RoleAndAssignType> findByAssignType(ProcessNodeAssignType assignType);
	
}
