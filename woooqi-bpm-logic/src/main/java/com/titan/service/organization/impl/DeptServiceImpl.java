package com.titan.service.organization.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.entity.organization.Dept;
import com.titan.entity.organization.Post;
import com.titan.repository.organization.DeptRepository;
import com.titan.repository.organization.PostRepository;
import com.titan.service.organization.DeptService;

@Service
public class DeptServiceImpl implements DeptService {
	
	@Autowired
	private DeptRepository deptRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public Dept getByName(String name) {
		
		return deptRepository.findByName(name);
	}
	
	@Override
	public List<Map<String, String>> getTreeDept() {
		List<Map<String, String>> lists = deptRepository.getTreeDept();
		return lists;
	}
	

	@Override
	public List<Dept> getByNameAndParentId(String name, String parentId) {
		return deptRepository.findByNameAndParentId(name, parentId);
	}
	
	
	@Override
	public List<Dept> getAllDeptByName(final String parentId, int pageNumber, int pageSize) {
		Page<Dept> depts = null;
		
		Sort sort = new Sort(Sort.Direction.ASC, "sort");
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize, sort);
		
	/*	if(!StringUtils.isNotEmpty(mane)){
			depts = deptRepository.findAll(pageRequest);
		}else{}*/
			Specification<Dept> specification = new Specification<Dept>() {
	
				@Override
				public Predicate toPredicate(Root<Dept> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
					//Path<String> _mane = root.get("mane");
					Path<String> _parentId = root.get("parentId");
					List<Predicate> list = new ArrayList<Predicate>();  
					//Predicate _key = cb.like(_mane, "%" + mane + "%");
					
				/*	if(StringUtils.isNotEmpty(mane)){
						list.add(cb.like(_mane, "%" + mane + "%"));
					}*/
					
					list.add(cb.equal(_parentId,parentId));
					Predicate[] p = new Predicate[list.size()]; 
					return cb.and(list.toArray(p));
				}
				
			};
			
			depts = deptRepository.findAll(specification,pageRequest);
				
		return depts.getContent();
	}

	@Override
	public Dept getDeptById(String id) {
		Dept dept = deptRepository.findOne(id);
		return dept;
	}

	@Override
	public void delById(String id) {
		Dept dept = deptRepository.findOne(id);
		List<Post> list = postRepository.findByDept(dept);
		if(list!=null && list.size()!=0){
			for(Post post :list){
				post.setDept(null);
				postRepository.saveAndFlush(post);
				postRepository.delete(post.getId());
			}
		}
		deptRepository.delete(id);
		
	}

	@Override
	public Map<String,Object> save(String mane,String parentId,int status,int sort,String deptCode) {
	//	List<Dept> lists = deptRepository.findByManeAndParentId(mane, parentId);
		 Map<String,Object>  map = new HashMap<String, Object>();
		//if(lists==null||lists.size()==0){
			Dept dept = new Dept();
			dept.setCreateTime(new Date());
			dept.setName(mane);
			dept.setParentId(parentId);
			dept.setStatus(status);
			dept.setSort(sort);
			dept.setUsers(null);
			dept.setDeptCode(deptCode);
		    Dept savedept = deptRepository.saveAndFlush(dept);
		    if(savedept!=null){
		    	map.put("code", 1);
		    	map.put("msg", "操作成功");
		    }else{
		    	map.put("code", -1);
		    	map.put("msg", "操作失败");
		    }
//		}else{
//			map.put("code", -1);
//	    	map.put("msg", "同一级别下的部门名称不能相同!");
//			
//		}
		return map;
		
	}

	@Override
	public Map<String, Object> update(String id, String name, String parentId,int status,int sort,String deptCode) {
		//List<Dept> lists = deptRepository.findByManeAndParentId(mane, parentId);
		Dept beforeDept = deptRepository.findOne(id);
		Map<String,Object>  map = new HashMap<String, Object>();
//		 if(mane.equals(beforeDept.getMane())){
//			 map.put("code", 1);
//		     map.put("msg", "操作成功");
//		 }
//		 else{
			// if(lists==null||lists.size()==0){
				Dept dept = new Dept();
				dept.setId(id);
				dept.setCreateTime(new Date());
				dept.setName(name);
				dept.setParentId(beforeDept.getParentId());
				dept.setStatus(status);
				dept.setSort(sort);
				dept.setUsers(null);
				dept.setDeptCode(deptCode);
			    Dept savedept = deptRepository.saveAndFlush(dept);
			    if(savedept!=null){
			    	map.put("code", 1);
			    	map.put("msg", "操作成功");
			    }else{
			    	map.put("code", -1);
			    	map.put("msg", "操作失败");
			    }
//			}else{
//				map.put("code", -1);
//		    	map.put("msg", "同一级别下的部门名称不能相同!");
//			}
			// }
		return map;
	}

	@Override
	public List<Map<String, Object>> getEnableDept() {
		List<Map<String,Object>> treeDepts = new ArrayList<Map<String,Object>>();
		List<Dept> lists = deptRepository.getEnableDept();
		if(lists != null && lists.size() != 0){
			for(Dept dept:lists){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", dept.getId());
			map.put("pId", dept.getParentId());
			map.put("name", dept.getName());
			treeDepts.add(map);
			}
			return treeDepts;
		}
		return null;
	}

	







}
