package com.woooqi.bpm.service.organization.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.woooqi.bpm.service.organization.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.titan.entity.organization.Dept;
import com.titan.entity.organization.Role;
import com.titan.entity.organization.User;
import com.titan.repository.organization.DeptRepository;
import com.titan.repository.organization.PostRepository;
import com.titan.repository.organization.RoleRepository;
import com.titan.repository.organization.UserRepository;
import com.titan.service.organization.UserService;
import com.titan.utils.Md5Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public User createUser(String id,String loginName,String password,String name,int status) {
		User user = new User();
		if(id==null || id.equals("")){
			user.setLoginName(loginName);
	        user.setName(name);
	        user.setPassword(Md5Utils.getMD5(password));
	        user.setStatus(status);
	        user.setCreateTime(new Date());
		}else{
			user.setId(id);
			user.setLoginName(loginName);
	        user.setName(name);
	        user.setCreateTime(new Date());
	        user.setStatus(status);
		}
		return userRepository.saveAndFlush(user);
	}
	
	@Override
	public User getByLoginName(String loginName) {	
		
		User user = userRepository.findByLoginName(loginName);
		
		Set<Role> roles = new HashSet<Role>(roleRepository.getRoleByUserId(user.getId()));  
		user.setRoles(roles);
		
		return user;
	}
	
	@Override
	public User getLoginNameIfy(String loginName) {	
		
		User user = userRepository.findByLoginName(loginName);
		
		return user;
	}
	
	@Override
	public List<User> getAllUser(int pageNumber,int pageSize,String name){
		Sort sort = new Sort(Sort.Direction.ASC, "createTime");
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize,sort);
		Page<User> users = userRepository.findAll(pageRequest);
		return users.getContent();
	}
	
	@Override
	public void delUserById(String id){
		userRepository.delUser_Role(id);
		userRepository.delUserDept(id);
		userRepository.delete(id);
	}
	
	@Override
	public User getUserById(String id){
		User user = userRepository.findOne(id);
		return user;
	}

	@Override
	public User setDept(String id,String deptId){
		Dept dept = deptRepository.getOne(deptId);
		User user = getUserById(id);
		user.setDept(dept);
		return userRepository.saveAndFlush(user);
	}


	@Override
	public Map<String, Object> saveUserRole(String userId, List<String> roles) {
		Map<String,Object> map = new HashMap<String, Object>();
		User user = userRepository.findOne(userId);
		if(user==null){
			map.put("code", -1);
	    	map.put("msg", "请选择一个用户");
	    	return map;
		}
		userRepository.delUserRole(userId);
		for(String roleId:roles){
			userRepository.saveUserRole(userId, roleId);
		}
		map.put("code", 1);
    	map.put("msg", "操作成功");
		return map;
	
	}


	@Override
	public List<User> getUserByDeptId(String deptId, int status) {
		List<User> lists = userRepository.getUserByDeptId(deptId, status);
		return lists;
	}

	@Override
	public List<User> getUserByAssignTypeId(String assignId) {
		List<User> lists = userRepository.getUserByAssignTypeId(assignId);
		return lists;
	}

	@Override
	public List<String> getUserByRoleIds(List<String> roleIds) {
		List<String> lists = userRepository.getUserByRoleIds(roleIds);
		return lists;
	}

	@Override
	public List<String> getUserByDeptIds(List<String> deptIds) {
		List<String> lists = userRepository.getUserByDeptIds(deptIds);
		return lists;
	}

	@Override
	public List<String> getUserByPostIds(List<String> postIds) {
		List<String> list = userRepository.getUserByPostIds(postIds);
		return list;
	}

	@Override
	public List<User> findByNameContaining(String name) {
		
		return userRepository.findByNameContaining(name);
	}

	@Override
	public List<User> findUserEnable() {
		return userRepository.findUserEnable();
	}

}
