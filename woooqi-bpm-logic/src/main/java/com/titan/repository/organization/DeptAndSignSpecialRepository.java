package com.titan.repository.organization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;
import com.titan.entity.organization.Dept;
import com.titan.entity.organization.DeptAndSignSpecial;

public interface DeptAndSignSpecialRepository extends JpaRepository<DeptAndSignSpecial,String>,JpaSpecificationExecutor<DeptAndSignSpecial>{
 
	  public List<DeptAndSignSpecial> findBySignSpecial(ProcessNodeSignSpecial signSpecial);
	  
	  public List<DeptAndSignSpecial> findBySignSpecialAndDept(ProcessNodeSignSpecial signSpecial ,Dept dept);

	
}
