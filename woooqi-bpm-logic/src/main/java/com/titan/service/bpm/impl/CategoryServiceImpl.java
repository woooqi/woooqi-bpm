package com.titan.service.bpm.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.bpm.Category;
import com.titan.repository.organization.CategoryRepository;
import com.titan.service.bpm.CategoryService;
import com.titan.utils.DataBaseUtils;
import com.titan.utils.PageUtils;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> getAllCategory(String name) {
		List<Category> Category = null;
		String sql = "select t.* from bpm_category t";
		if(name != null && !name.equals("")){
			sql = sql+"where t.name = ?";
			 Category = DataBaseUtils.queryForListBean(sql, Category.class,name);
		}
		 Category = DataBaseUtils.queryForListBean(sql, Category.class);
		return Category;
	}
	
	@Override
	public int updateCategory(String id,String name,int status,int sort) {
		String sql = "";
		int doSql = 0;
		if(id == null || id.equals("")){
			sql = "insert into bpm_category(id,create_time,name,sort,status) values(?,?,?,?,?)";
			doSql = DataBaseUtils.update(sql,PageUtils.getUUID(),new Date(),name,sort,status);
		}else{
			sql = "update bpm_category set name=?,sort=?,status=?,create_time=? where id=?";
			doSql = DataBaseUtils.update(sql,name,sort,status,new Date(),id);
		}
		return doSql;
	}

	@Override
	public int delCategoryById(String id) {
		String sql = "delete from bpm_category where id = ?";
		return DataBaseUtils.update(sql,id);
		
	}
	
	@Override
	public Category getCategoryById(String id) {
		String sql = "select t.* from bpm_category t where t.id = ?";
		return DataBaseUtils.queryForBean(sql, Category.class,id);
	}

	@Override
	public Category getCategoryByName(String name) {
		String sql = "select t.* from bpm_category t where t.name = ?";
		return  DataBaseUtils.queryForBean(sql, Category.class,name);
	}
	
	@Override
	public List<Map<String, String>> getTreeCategory() {
		List<Map<String,String>> lists = categoryRepository.getTreeCategory();
		
		return lists;
	}
}
