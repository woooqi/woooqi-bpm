package com.woooqi.bpm.controller.organization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.organization.Role;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.manage.shiro.ShiroDbRealm;
import com.woooqi.bpm.service.organization.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.organization.Role;
import com.titan.entity.web.Page;
import com.titan.manage.shiro.ShiroDbRealm;
import com.titan.service.organization.RoleService;

@RestController
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private ShiroDbRealm shiroDbRealm;
	
	
	@RequestMapping("getTreeRole")
	@ResponseBody
	public List<Map<String,String>> getTreeMenu(HttpServletRequest request,HttpServletResponse response){
		List<Map<String,String>> lists = roleService.getTreeRole();
		for(int i = 0;i<lists.size();i++){
			Map<String, String> map = lists.get(i);
			map.put("pId", "parent");
		}
		
		Map<String, String> pmap = new HashMap<String, String>();
		pmap.put("id", "parent");
		pmap.put("name", "全部角色");
		pmap.put("pId", "top");
		lists.add(pmap);
		
		return lists;
	}
	
	@RequestMapping("findRoleEnable")
	@ResponseBody
	public Page<Role> findUserEnable(HttpServletRequest request, HttpServletResponse response){
		Page<Role> page = new Page<Role>();
		List<Role> lists = roleService.findRoleEnable();
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}
	
	@RequestMapping("getAllRole")
	@ResponseBody
	public Page<Role> getAllRole(HttpServletRequest request,HttpServletResponse response){
		Page<Role> page = new Page<Role>();
		
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber")==null?"0":request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize")==null?"20":request.getParameter("pageSize"));
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		List<Role> lists = null;
		if(name.equals("") || name == null){
			lists = roleService.getAllRole(pageNumber, pageSize);
			page.setRows(lists);
			page.setTotal(lists.size());
		}else{
			lists = roleService.findByNameContaining(name);
			page.setRows(lists);
			page.setTotal(lists.size());
		}
		
		
		page.setRows(lists);
		page.setTotal(lists.size());
		
		return page;
	}
	
	@RequestMapping("createRole")
	@ResponseBody
	public Map<String,Object> createRole(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String id = request.getParameter("id")==null?"":request.getParameter("id");
			String name = request.getParameter("name")==null?"":request.getParameter("name");
			String roleCode = request.getParameter("roleCode")==null?"":request.getParameter("roleCode");
			//String password = request.getParameter("password")==null?"":request.getParameter("password");
			int status = Integer.parseInt(request.getParameter("status")==null?"1":request.getParameter("status"));
			Role role =  roleService.getRoleByName(name);
			if(StringUtils.isBlank(id)){
				if(role==null){
					roleService.createRole(id,name, roleCode,status);
					map.put("code", 1);
				}else{
					map.put("code", -1);
					map.put("msg", "角色名已经存在");
				}
			}else {
				Role roleById =  roleService.getRoleById(id);
				if( name.equals(roleById.getName())||role==null){
					roleService.createRole(id, name,roleCode,status);
					map.put("code", 1);
				}else{
					map.put("code", -1);
					map.put("msg", "角色名已经存在");
				}
			}
		} catch (Exception e) {
			map.put("code", -2);
		}
		return map;
	}
	
	@RequestMapping("delRoleById")
	@ResponseBody
	public Map<String,Object> delRoleById(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String id = request.getParameter("id")==null?"":request.getParameter("id");
			roleService.delRoleById(id);
			map.put("code", 1);
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	
	@RequestMapping("getRoleById")
	@ResponseBody
	public Role getRoleById(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		Role role =  roleService.getRoleById(id);
		
		return role;
	}
	
	@RequestMapping("saveRoleMenu")
	@ResponseBody
	public Map<String,Object> saveRoleMenu(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map =new HashMap<String, Object>();
		String id = request.getParameter("roleId")==null?"":request.getParameter("roleId");
		String menuids = request.getParameter("ids")==null?"":request.getParameter("ids");
		List<String> menus = new ArrayList<String>();
		if(StringUtils.isNotEmpty(menuids)){
			menus= Arrays.asList(menuids.split(","));
		}
		map  = roleService.saveRoleMenu(id, menus);
		shiroDbRealm.clearAuthenticationInfoCached();
		shiroDbRealm.clearAuthorizationInfoCached();
		return map;
	}
	
	
	@RequestMapping("getRoleByUserId")
	@ResponseBody
	public List<Role> getRoleByUserId(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getParameter("id") == null ? "" : request.getParameter("id");
		List<Role> roles =  roleService.getRoleByUserId(userId);
		return roles;
	}
	
	@RequestMapping("getRoleByAssignId")
	@ResponseBody
	public Page<Role> getRoleByAssignId(HttpServletRequest request,HttpServletResponse response){
		String assignId = request.getParameter("assignId");
		int status =1;
		List<Role> lists = roleService.getRoleByAssignId(assignId,status);
		Page<Role> page = new Page<Role>();
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}
	
	
	
}
