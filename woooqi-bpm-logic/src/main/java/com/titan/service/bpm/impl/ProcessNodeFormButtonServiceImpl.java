package com.titan.service.bpm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.manage.ProcessNodeFormButton;
import com.titan.repository.bpm.manage.ProcessNodeFormButtonRepository;
import com.titan.service.bpm.ProcessNodeFormButtonService;

@Service
public class ProcessNodeFormButtonServiceImpl implements ProcessNodeFormButtonService {
	
	@Autowired
	private ProcessNodeFormButtonRepository processNodeFormButtonRepository;

	@Override
	public List<ProcessNodeFormButton> getAllProcessNodeFormButton(int pageNumber, int pageSize) {
		
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		Page<ProcessNodeFormButton> processNodeFormButtons  = processNodeFormButtonRepository.findAll(pageRequest);
		List<ProcessNodeFormButton> lists =processNodeFormButtons.getContent();	
		return lists;
	}

}
