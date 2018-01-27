package com.woooqi.bpm.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.titan.entity.base.base.Template;
import com.titan.repository.base.TemplateRepository;
import com.titan.service.base.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService{

	@Autowired
	private TemplateRepository templateRepository;

	@Override
	public List<Template> getAllTemplates(int pageNumber,int pageSize,String name) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		Page<Template> templates = templateRepository.findAll(pageRequest);
		return templates.getContent();
	}

	@Override
	public void updateTemplate(String id, String name, String comments) {
		Template template = new Template();
		template.setId(id);
		template.setName(name);
		template.setComments(comments);
		templateRepository.saveAndFlush(template);
	}

	@Override
	public void delTemplateById(String id) {
		templateRepository.delete(id);
	}

	@Override
	public Template getTemplateByName(String name) {
		return templateRepository.findByName(name);
	}

	@Override
	public Template getTemplateById(String id) {
		
		return templateRepository.findById(id);
	}
	
}
