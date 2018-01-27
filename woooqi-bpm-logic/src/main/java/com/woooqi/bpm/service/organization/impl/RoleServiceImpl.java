package com.woooqi.bpm.service.organization.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woooqi.bpm.service.organization.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.titan.entity.organization.Role;
import com.titan.repository.organization.MenuRepository;
import com.titan.repository.organization.RoleRepository;
import com.titan.service.organization.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	public Role createRole(String id,String name,String roleCode,int status) {
		Role role = new Role();
		if(StringUtils.isBlank(id)){
			role.setName(name);
	        role.setRoleCode(roleCode);
	        role.setStatus(status);
	        role.setCreateTime(new Date());
		}else{
			role.setId(id);
			role.setRoleCode(roleCode);
			role.setName(name);
			role.setCreateTime(new Date());
			role.setStatus(status);
		}
		return roleRepository.saveAndFlush(role);
	}

	@Override
	public Role getRoleByName(String name) {
		
		return roleRepository.findRoleByName(name);
	}

	@Override
	public List<Role> getAllRole(int pageNumber, int pageSize) {
		Sort sort = new Sort(Sort.Direction.ASC, "createTime");
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize, sort);
		Page<Role> role = roleRepository.findAll(pageRequest);
		return role.getContent();
	}

	@Override
	public Role getRoleById(String id) {
		return roleRepository.findOne(id);
		
	}

	@Override
	public void delRoleById(String id) {
		roleRepository.delRoleMenu(id);
		roleRepository.delete(id);
		
	}

	@Override
	public Map<String, Object> saveRoleMenu(String id, List<String> menuids) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		Role role = roleRepository.findOne(id);
		if(role==null){
			map.put("code", -1);
	    	map.put("msg", "请选择一条角色");
	    	return map;
		}
		
		roleRepository.delRoleMenu(id);
		for(String menuId:menuids){
			String[] menus = menuId.split(":");
			roleRepository.saveRoleMenu(id, menus[0],menus[1]);
		}
		map.put("code", 1);
    	map.put("msg", "操作成功");
		return map;
	}

		@Override
		public List<Map<String, String>> getTreeRole() {
			List<Map<String,String>> lists = roleRepository.getTreeRole();
			return lists;
		}

		@Override
		public List<Role> getRoleByUserId(String userId) {
			List<Role> lists = roleRepository.getRoleByUserId(userId);
			return lists;
		}

		@Override
		public List<Role> getRoleByAssignId(String assignId ,int status) {
			List<Role> lists= roleRepository.getRoleByAssignTypeId(assignId,status);
			return lists;
		}

		@Override
		public List<Role> findRoleEnable() {
			return roleRepository.findRoleEnable();
		}

		@Override
		public List<Role> findByNameContaining(String name) {
			
			return roleRepository.findByNameContaining(name);
		}

}
