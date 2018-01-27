package com.titan.repository.bpm.manage;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.manage.ProcessNodeSign;
import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;



public interface ProcessNodeSignSpecialRepository extends JpaRepository<ProcessNodeSignSpecial,String>{
	
	public List<ProcessNodeSignSpecial> findBySign(ProcessNodeSign sign);
  

}
