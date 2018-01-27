package com.titan.service.bpm;

import java.util.List;


import java.util.Map;

import com.titan.entity.bpm.bpm.Category;

public interface CategoryService {
	
	public List<Category> getAllCategory(String name);
	
	public int updateCategory(String id,String name,int status,int sort);
	
	public int delCategoryById(String id);
	
	public Category getCategoryByName(String name);
	
	public Category getCategoryById(String id);
	
	public List<Map<String,String>> getTreeCategory();
}
