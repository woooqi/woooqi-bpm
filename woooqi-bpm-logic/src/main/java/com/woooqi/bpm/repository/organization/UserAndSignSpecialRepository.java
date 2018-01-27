package com.woooqi.bpm.repository.organization;

import java.util.List;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeSignSpecial;
import com.woooqi.bpm.entity.organization.User;
import com.woooqi.bpm.entity.organization.UserAndSignSpecial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;
import com.titan.entity.organization.User;
import com.titan.entity.organization.UserAndSignSpecial;

public interface UserAndSignSpecialRepository extends JpaRepository<UserAndSignSpecial,String>,JpaSpecificationExecutor<UserAndSignSpecial>{
 
	  public List<UserAndSignSpecial> findBySignSpecial(ProcessNodeSignSpecial signSpecial);
	  
	  
	  public List<UserAndSignSpecial> findBySignSpecialAndUser(ProcessNodeSignSpecial signSpecial , User user);
	
}
