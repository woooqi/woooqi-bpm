package com.woooqi.bpm.service.organization;

import java.util.List;
import java.util.Map;





import com.titan.entity.organization.Role;

public interface RoleService {
	
	public Role createRole(String id,String name,String roleCode,int status);

	public Role getRoleByName(String name);
	
	public void delRoleById(String id);
	
	public List<Role> getAllRole(int pageNumber,int pageSize);
	
	public Role getRoleById(String id);
	
	public Map<String,Object> saveRoleMenu(String id,List<String> menuids);
	 
	public List<Map<String,String>> getTreeRole();
	
	public List<Role> getRoleByUserId(String userId);

	public List<Role> getRoleByAssignId(String assignId,int status);
	
	public List<Role> findRoleEnable();
	
	public List<Role> findByNameContaining(String name);

	
	
}
