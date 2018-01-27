package com.woooqi.bpm.repository.bpm.manage;

import java.util.List;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.manage.ProcessNodeNotice;


public interface ProcessNodeNoticeRepository extends JpaRepository<ProcessNodeNotice,String>{
	
	//@Query(value="select a.* from bpm_menu a,bpm_role_menu b where a.id = b.menu_id and b.role_id = ?1",nativeQuery=true)
	public List<ProcessNodeNotice> findByActivitiId(String activitiId);


}
