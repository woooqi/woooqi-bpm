package com.woooqi.bpm.service.organization.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.woooqi.bpm.service.organization.PostService;
import org.apache.commons.lang3.StringUtils;
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
import com.titan.service.organization.PostService;



@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private DeptRepository deptRepository;

	@Override
	public Post getByIdAndDeptId(String id, String deptId) {
		Post post = postRepository.findByIdAndDeptId(id, deptId);
		return post;
	}
	
	@Override
	public Post getById(String id) {
		Post post = postRepository.findOne(id);
		return post;
	}

	@Override
	public Map<String, Object> delById(String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		Post post = postRepository.findOne(id);
		if(post!=null){
			post.setDept(null);
			postRepository.saveAndFlush(post);
		}
		postRepository.delete(id);	
		map.put("code", 1);
		map.put("msg", "删除成功");
		 
		return map;
	}

	@Override
	public Map<String, Object> saveAndFlush(Post post,String deptId) {
		Map<String,Object> map = new HashMap<String, Object>();
		Dept dept = deptRepository.findOne(deptId);
		post.setDept(dept);
		//List<Post> posts = postRepository.findByNameAndDept(post.getName(), dept);
//		if(posts!=null&&posts.size()!=1){
//			map.put("code", -1);
//	    	map.put("msg", "同一个部门下岗位名不能相同");
//	    	return map;
//		}
		if(!StringUtils.isNotEmpty(post.getId())){
			post.setId(null);
		}
		Post savePost = postRepository.saveAndFlush(post);
		if(savePost!=null){
	    	map.put("code", 1);
	    	map.put("msg", "操作成功");
	    }else{
	    	map.put("code", -1);
	    	map.put("msg", "操作失败");
	    }
		return map;
	}
	
	@Override
	public List<Post> getByDeptId(final String deptId,final String status, int pageNumber, int pageSize) {
		Page<Post> posts = null;
		
		Sort sort = new Sort(Sort.Direction.ASC, "createTime");
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize, sort);
		final Dept dept = deptRepository.findOne(deptId);
		Specification<Post> specification = new Specification<Post>() {

			@Override
			public Predicate toPredicate(Root<Post> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Path<Dept> _dept = root.get("dept");
				
				List<Predicate> list = new ArrayList<Predicate>();  
				
				list.add(cb.equal(_dept,dept));
				if(StringUtils.isNotEmpty(status)){
					Integer parseInt = Integer.parseInt(status);
					Path<Integer> _status = root.get("status");
					list.add(cb.equal(_status,parseInt));
				}
				Predicate[] p = new Predicate[list.size()]; 
				return cb.and(list.toArray(p));
			}
			
		};
		
		posts = postRepository.findAll(specification,pageRequest);
				
		return posts.getContent();
	}


	
	@Override
	public List<Post> getPostByDeptId(String deptId, int pageNumber, int pageSize) {
		List<Post> posts = postRepository.findPostByDeptId(deptId);
		return posts;
	}
	
	

	@Override
	public List<Post> getEnableByDeptId(String deptId, Integer status) {
		List<Post> list = postRepository.findEnablePost(deptId, status);
		return list;
	}


}
