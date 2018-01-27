package com.titan.repository.organization;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.bpm.bpm.Category;

public interface CategoryRepository extends  JpaRepository<Category,String>{
	
	public Category findById(String id);
	
	public Category findByName(String name);
	
	@Query("select new map(t.id as id,t.name as name) from Category t where t.status = '1'")
	public List<Map<String,String>> getTreeCategory();

}
