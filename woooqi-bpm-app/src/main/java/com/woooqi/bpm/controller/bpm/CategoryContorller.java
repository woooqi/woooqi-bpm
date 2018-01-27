package com.woooqi.bpm.controller.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.bpm.bpm.Category;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.bpm.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.bpm.Category;
import com.titan.entity.web.Page;
import com.titan.service.bpm.CategoryService;

@RestController
@RequestMapping("category")
public class CategoryContorller {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("getAllCategory")
	@ResponseBody
	public Page<Category> getAllCategory(HttpServletRequest request, HttpServletResponse reponse){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		Page<Category> page = new Page<Category>();
		List<Category> list = categoryService.getAllCategory(name);
		page.setRows(list);
	    page.setTotal(list.size());
		return page;
	}
	
	@RequestMapping("updateCategory")
	@ResponseBody
	public Map<String,Object> updateCategory(HttpServletRequest request,HttpServletResponse reponse){
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		int status = Integer.parseInt(request.getParameter("status")==null?"1":request.getParameter("status"));
		int sort = Integer.parseInt(request.getParameter("sort")==null?"1":request.getParameter("sort"));
		Map<String,Object> map = new HashMap<String, Object>();
		Category category =categoryService.getCategoryByName(name);
		try {
			if(StringUtils.isBlank(id)){
				if(category == null){
					categoryService.updateCategory(id,name,status,sort);
					map.put("code", 1);
					map.put("msg", "操作成功");
				}else{
					map.put("code", -1);
					map.put("msg", "分类名称已经存在");
				}
			}else{
				Category categoryById =categoryService.getCategoryById(id);
				if(name.equals(categoryById.getName()) || category == null){
					categoryService.updateCategory(id,name,status,sort);
					map.put("code", 1);
					map.put("msg", "操作成功");
				}else{
					map.put("code", -1);
					map.put("msg", "分类名称已经存在");
				}	
			}
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	
	@RequestMapping("delCategoryById")
	public Map<String,Object> delCategoryById(HttpServletRequest request,HttpServletResponse reponse){
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		Map<String,Object> map = new HashMap<String, Object>();
		categoryService.delCategoryById(id);
		map.put("code",1);
		return map;
	}
	
	@RequestMapping("getCategoryById")
	public Category getCategoryById(HttpServletRequest request,HttpServletResponse reponse){
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		Category category = categoryService.getCategoryById(id);
		return category;
	}
	
	@RequestMapping("getTreeCategory")
	public List<Map<String,String>> getTreeCategory(HttpServletRequest request,HttpServletResponse reponse){
		
		List<Map<String,String>> lists = categoryService.getTreeCategory();
		for(int i = 0;i<lists.size();i++){
			Map<String, String> map = lists.get(i);
			map.put("pId", "parent");
		}
		
		Map<String, String> pmap = new HashMap<String, String>();
		pmap.put("id", "parent");
		pmap.put("name", "全部分类");
		pmap.put("pId", "top");
		lists.add(pmap);
		
		return lists;
	}
}
