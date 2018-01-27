package com.titan.controller.organization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.organization.Menu;
import com.titan.entity.web.Page;
import com.titan.service.organization.MenuService;


@RestController
@RequestMapping("menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	
	@RequestMapping("getTreeMenu")
	@ResponseBody
	public List<Map<String,Object>> getTreeMenu(HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> lists = menuService.getTreeMenu();
		return lists;
	}
	
	@RequestMapping("getEnableMenu")
	@ResponseBody
	public List<Map<String,Object>> getEnableMenu(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String,Object>> lists = menuService.getEnableMenu();
		return lists;
	}
	
	@RequestMapping("getAllMenu")
	@ResponseBody
	public Page<Menu> getAllDept(HttpServletRequest request,HttpServletResponse response){
		Page<Menu> page = new Page<Menu>();
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber")==null?"0":request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize")==null?"20":request.getParameter("pageSize"));
	    String parentId = request.getParameter("parentId")==null?"0":request.getParameter("parentId");
		List<Menu> lists = menuService.getAllMenuByName( pageNumber, pageSize,parentId);
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}
	
	@RequestMapping("getMenuById")
	@ResponseBody
	public Menu getUserById(HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		
		Menu menu = menuService.getMenuById(id);
		
		return menu;
	}
	
	@RequestMapping("saveMenu")
	@ResponseBody
	public Map<String,Object> saveAndFlushMenuById(HttpServletRequest request,HttpServletResponse response){
		
		String parentId = request.getParameter("parentId")==null?"0":request.getParameter("parentId");
		Integer status = Integer.parseInt(request.getParameter("status")==null?"1":request.getParameter("status"));
		String name = request.getParameter("name");
		String stamp = request.getParameter("stamp");
		String image = request.getParameter("image");
		String url_name= request.getParameter("url_name");
		String id= request.getParameter("id");
		Integer sort = Integer.parseInt(request.getParameter("sort")==null?"1":request.getParameter("sort"));

		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isNotEmpty(id)){
			map =  menuService.save(name,url_name, parentId,status,sort,stamp,image);
		}else{
			 map =  menuService.update(id,name,url_name, parentId,status,sort,stamp,image);	
		}
			

		return map;
	}
	
	
	@RequestMapping("deleteMenu")
	@ResponseBody
	public Map<String, Object> deleteDept(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		List<Menu> list = menuService.getAllMenuByName(0, 20, id);
		if(list==null||list.size()==0){
			try {
				menuService.delById(id);
				map.put("code", 1);
				map.put("msg", "删除成功");
			} catch (Exception e) {
				map.put("code", -1);
				map.put("msg", e.getMessage());
			}
		}else{
			map.put("code", -1);
			map.put("msg", "不能直接删除父级菜单");
		}

		return map;
	}
	
	@RequestMapping(value="getMenuByRoleId")
	@ResponseBody
	public List<Menu> getMenuByRoleId(HttpServletRequest request, HttpServletResponse response){
		String roleId = request.getParameter("id") == null ? "" : request.getParameter("id");
		List<Menu> menus =  menuService.getMenuByRoleId(roleId);
		return menus;
	}

	@RequestMapping(value="getAllRoleMenu")
	@ResponseBody
	public Map<String, Object> getAllRoleMenu(HttpServletRequest request, HttpServletResponse response){
		String roleId = request.getParameter("roleIds") == null ? "" : request.getParameter("roleIds");
		List<String> roleIds = Arrays.asList(roleId.split(","));
		Map<String, Object> menus =  menuService.getAllRoleMenu(roleIds);
		return menus;
	}
	
	@RequestMapping(value="getSubRoleMenu")
	@ResponseBody
	public Map<String, Object> getSubRoleMenu(HttpServletRequest request, HttpServletResponse response){
		String roleId = request.getParameter("roleIds") == null ? "" : request.getParameter("roleIds");
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		List<String> roleIds = Arrays.asList(roleId.split(","));
		Map<String, Object> menus =  menuService.getSubRoleMenu(roleIds,id);
		return menus;
	}
}
