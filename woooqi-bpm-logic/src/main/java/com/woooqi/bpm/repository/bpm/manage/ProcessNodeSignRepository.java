package com.woooqi.bpm.repository.bpm.manage;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.bpm.manage.ProcessNodeSign;


public interface ProcessNodeSignRepository extends JpaRepository<ProcessNodeSign,String>{
	
	public ProcessNodeSign findByActivitiId(String activitiId);
	
	@Modifying
	@Query(value="insert into bpm_user_sign(sign_id,user_id) values(?1,?2) ",nativeQuery=true)
	public void saveUsersign(String signId,String userId);
	

}
