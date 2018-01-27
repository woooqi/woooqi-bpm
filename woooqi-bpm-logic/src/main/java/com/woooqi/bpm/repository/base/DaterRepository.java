package com.woooqi.bpm.repository.base;


import com.woooqi.bpm.entity.base.date.Dater;
import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.base.date.Dater;


public interface DaterRepository extends JpaRepository<Dater,String>{
	
	public Dater findById(String id);
	
	public Dater findByName(String name);
	
}
