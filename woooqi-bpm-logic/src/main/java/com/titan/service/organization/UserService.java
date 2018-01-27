package com.titan.service.organization;

import java.util.List;
import java.util.Map;

import com.titan.entity.organization.User;

public interface UserService {

	public User getByLoginName(String loginName);
	
	public User getLoginNameIfy(String loginName);
	
	public List<User> getAllUser(int pageNumber,int pageSize,String name);
	
	public void delUserById(String id);
	
	public User createUser(String id,String loginName,String name,String password,int status);
	
	public User getUserById(String id);

	public User setDept(String id,String deptId);

	public Map<String, Object> saveUserRole(String userId, List<String> roles);
	
	public List<User> getUserByDeptId(String deptId,int status);
	 
	public List<User> getUserByAssignTypeId(String assignId);
	
	public List<String> getUserByRoleIds(List<String> roleIds);
	
	public List<String> getUserByDeptIds(List<String> deptIds);
	
	public List<String> getUserByPostIds(List<String> postIds);

	public List<User> findByNameContaining(String name);
	
	public List<User> findUserEnable();
} 
