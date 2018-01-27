package com.woooqi.bpm.controller.organization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.organization.User;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.manage.shiro.ShiroDbRealm;
import com.woooqi.bpm.service.organization.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.organization.User;
import com.titan.entity.web.Page;
import com.titan.manage.shiro.ShiroDbRealm;
import com.titan.service.organization.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ShiroDbRealm shiroDbRealm;
	
	@RequestMapping("getAllUser")
	@ResponseBody
	public Page<User> getAllUser(HttpServletRequest request, HttpServletResponse response){
		Page<User> page = new Page<User>();
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber")==null?"0":request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize")==null?"20":request.getParameter("pageSize"));
		List<User> lists = null;
		if(name.equals("") || name == null){
			lists = userService.getAllUser(pageNumber, pageSize,name);
			page.setRows(lists);
			page.setTotal(lists.size());
		}else{
			lists = userService.findByNameContaining(name);
			page.setRows(lists);
			page.setTotal(lists.size());
		}
	
		return page;
	}
	
	
	@RequestMapping("findUserEnable")
	@ResponseBody
	public Page<User> findUserEnable(HttpServletRequest request,HttpServletResponse response){
		Page<User> page = new Page<User>();
		List<User> lists = userService.findUserEnable();
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}
	
	@RequestMapping("createUser")
	@ResponseBody
	public Map<String,Object> createUser(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String id = request.getParameter("id")==null?"":request.getParameter("id");
			String loginName = request.getParameter("loginName")==null?"":request.getParameter("loginName");
			String name = request.getParameter("name")==null?"":request.getParameter("name");
			String password = request.getParameter("password")==null?"123":request.getParameter("password");
			int status = Integer.parseInt(request.getParameter("status")==null?"1":request.getParameter("status"));
			User user =  userService.getLoginNameIfy(loginName);
			if(StringUtils.isBlank(id)){
				if(user==null){
					userService.createUser(id,loginName,password,name,status);
					map.put("code", 1);
				}else{
					map.put("code", -1);
					map.put("msg", "登录名已经存在");
				}
			}else {
				User userById =  userService.getUserById(id);
				if( loginName.equals(userById.getLoginName())||user==null){
					userService.createUser(id,loginName,password,name,status);
					map.put("code", 1);
				}else{
					map.put("code", -1);
					map.put("msg", "登录名已经存在");
				}
			}
			
		} catch (Exception e) {
			map.put("code", -2);
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	@RequestMapping("setDept")
	@ResponseBody
	public Map<String,Object> setDept(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String id = request.getParameter("id")==null?"":request.getParameter("id");
			String deptId = request.getParameter("deptId")==null?"":request.getParameter("deptId");
			userService.setDept(id,deptId);
			map.put("code", 1);
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	@RequestMapping("delUserById")
	@ResponseBody
	public Map<String,Object> delUserById(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String id = request.getParameter("id")==null?"":request.getParameter("id");
			userService.delUserById(id);
			map.put("code", 1);
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	@RequestMapping("getUserById")
	@ResponseBody
	public User getUserById(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		User user =  userService.getUserById(id);
		return user;
		
	}
	
	@RequestMapping("saveUserRole")
	@ResponseBody
	public Map<String,Object> saveUserRole(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map =new HashMap<String, Object>();
		String userId = request.getParameter("userId")==null?"":request.getParameter("userId");
		String roleIds = request.getParameter("roleIds")==null?"":request.getParameter("roleIds");
		List<String> roles = new ArrayList<String>();
		if(StringUtils.isNotEmpty(roleIds)){
			roles= Arrays.asList(roleIds.split(","));
		}
		map  = userService.saveUserRole(userId, roles);
		shiroDbRealm.clearAuthenticationInfoCached();
		shiroDbRealm.clearAuthorizationInfoCached();
		return map;
	}
	
	@RequestMapping("getUserByDeptId")
	@ResponseBody
	public Page<User> getUserByDeptId(HttpServletRequest request,HttpServletResponse response){
		String deptId = request.getParameter("deptId");
		int status =1;
		List<User> lists = userService.getUserByDeptId(deptId, status);
		Page<User> page = new Page<User>();
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}
	
	
	@RequestMapping("getUserByAssignId")
	@ResponseBody
	public Page<User> getUserByAssignId(HttpServletRequest request,HttpServletResponse response){
		String typeId = request.getParameter("typeId");
		//int status =1;
		List<User> lists = userService.getUserByAssignTypeId(typeId);
		Page<User> page = new Page<User>();
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}
	
}
