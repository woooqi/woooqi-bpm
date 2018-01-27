package com.titan.repository.base;


import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.base.base.Template;


public interface TemplateRepository extends JpaRepository<Template,String>{
	
	public Template findById(String id);
	
	public Template findByName(String name);
	
	
	
}
