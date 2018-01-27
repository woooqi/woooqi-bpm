package com.titan.service.base;

import java.util.Date;
import java.util.List;

import com.titan.entity.base.date.Dater;

public interface DaterService {
	
	public List<Dater> getAllDater(int pageNumber,int pageSize,String name);
	
	public void updateDater(String id,String name,Date date,String type);
	
	public void delDaterById(String id);
	
	public Dater getDaterById(String id);
	
}
