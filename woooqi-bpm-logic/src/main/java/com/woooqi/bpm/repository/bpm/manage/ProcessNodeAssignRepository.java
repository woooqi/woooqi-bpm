package com.woooqi.bpm.repository.bpm.manage;


import com.woooqi.bpm.entity.bpm.manage.ProcessNodeAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.bpm.manage.ProcessNodeAssign;



public interface ProcessNodeAssignRepository extends JpaRepository<ProcessNodeAssign,String>{
	
	public ProcessNodeAssign findByActivitiId(String activitiId);

	@Modifying
	@Query(value="delete bpm_role_assign where assign_id = ?1",nativeQuery=true)
	public void delRoleAssign(String id);

  

}
