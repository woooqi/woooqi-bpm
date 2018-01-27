package com.woooqi.bpm.service.organization;

import java.util.List;
import java.util.Map;

import com.titan.entity.organization.Post;


public interface PostService {	
	public Post getById(String id);
	
	public Post getByIdAndDeptId(String id,String deptId);
	
	public Map<String,Object> delById(String id);
	
	public Map<String,Object> saveAndFlush(Post post,String deptId);

	public List<Post> getEnableByDeptId(String deptId, Integer status);

	List<Post> getPostByDeptId(String deptId, int pageNumber, int pageSize);

	List<Post> getByDeptId(String deptId,String status, int pageNumber, int pageSize);

	
}
