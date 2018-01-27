package com.woooqi.bpm.service.organization;

import java.util.List;
import java.util.Map;

import com.titan.entity.organization.Menu;


public interface MenuService {

	public Menu getByName(String name);
	
	public List<Menu> getAllMenuByName(int pageNumber,int pageSize,String parentId);
	
	public Menu getMenuById(String id);
	
	public List<Map<String,Object>> getTreeMenu();

	public void delById(String id);
	
	public Map<String,Object> save(String name,String url_name,String parentId,int status,int sort,String stamp,String image);
	
	public Map<String,Object> update(String id,String name,String url_name,String parentId,int status,int sort, String stamp,String image);

	public List<Menu> getMenuByRoleId(String roleId);
	
	public List<Map<String, Object>> getEnableMenu();

	public Map<String, Object> getAllRoleMenu(List<String> roleIds);
	
	public Map<String, Object> getSubRoleMenu(List<String> roleIds,String id);
	
}
