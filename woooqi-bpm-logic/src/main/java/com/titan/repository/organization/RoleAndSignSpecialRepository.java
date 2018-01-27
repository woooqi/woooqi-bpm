package com.titan.repository.organization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;
import com.titan.entity.organization.Role;
import com.titan.entity.organization.RoleAndSignSpecial;

public interface RoleAndSignSpecialRepository extends JpaRepository<RoleAndSignSpecial,String>,JpaSpecificationExecutor<RoleAndSignSpecial>{
 
	  public List<RoleAndSignSpecial> findBySignSpecial(ProcessNodeSignSpecial signSpecial);
	  
	  public List<RoleAndSignSpecial> findBySignSpecialAndRole(ProcessNodeSignSpecial signSpecial ,Role role);

	
}
