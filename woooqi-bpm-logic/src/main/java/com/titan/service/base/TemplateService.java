package com.titan.service.base;

import java.util.List;

import com.titan.entity.base.base.Template;

public interface TemplateService {
	
	public List<Template> getAllTemplates(int pageNumber,int pageSize,String name);
	
	public void updateTemplate(String id,String name,String comments);
	
	public void delTemplateById(String id);
	
	public Template getTemplateByName(String name);
	
	public Template getTemplateById(String id);
}
