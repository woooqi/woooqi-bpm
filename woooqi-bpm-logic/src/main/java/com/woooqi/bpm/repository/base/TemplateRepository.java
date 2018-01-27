package com.woooqi.bpm.repository.base;


import com.woooqi.bpm.entity.base.base.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.base.base.Template;


public interface TemplateRepository extends JpaRepository<Template,String>{
	
	public Template findById(String id);
	
	public Template findByName(String name);
	
	
	
}
