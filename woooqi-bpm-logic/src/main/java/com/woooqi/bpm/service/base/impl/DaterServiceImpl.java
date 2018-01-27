package com.woooqi.bpm.service.base.impl;

import java.util.Date;
import java.util.List;

import com.woooqi.bpm.service.base.DaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.titan.entity.base.date.Dater;
import com.titan.repository.base.DaterRepository;
import com.titan.service.base.DaterService;

@Service
public class DaterServiceImpl implements DaterService {

	@Autowired
	private DaterRepository daterRepository;

	@Override
	public List<Dater> getAllDater(int pageNumber,int pageSize,String name) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		Page<Dater> dater = daterRepository.findAll(pageRequest);
		return dater.getContent();
	}

	@Override
	public void updateDater(String id, String name,Date date,String type) {
		Dater dater = new Dater();
		dater.setId(id);
		dater.setName(name);
		dater.setDater(date);
		dater.setType(type);
		daterRepository.saveAndFlush(dater);
	}

	@Override
	public void delDaterById(String id) {
		daterRepository.delete(id);
	}

	@Override
	public Dater getDaterById(String id) {
		return daterRepository.findById(id);
	}


	
}
