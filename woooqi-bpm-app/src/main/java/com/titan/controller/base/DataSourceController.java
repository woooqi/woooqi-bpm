package com.titan.controller.base;


import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.service.base.DataSourceService;

@RestController
@RequestMapping("datasource")
public class DataSourceController {

	@Autowired
	private DataSourceService dataSourceService;
	
	@ResponseBody
	@RequestMapping("getDataSource")
	public Map<Object,Object> getDataSource(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("dataSourceId");
		Map<Object,Object> map = new HashMap<Object, Object>();
		List<Map<String, String>> list = dataSourceService.getDataSource(id);
		map.put("code", 1);
		map.put("msg", "");
		map.put("rows",list);
		map.put("total", list.size());
		return map;
	}
	
	@ResponseBody
	@RequestMapping("updateDataSource")
	public Map<String,Object> updateDataSource(HttpServletRequest request,HttpServletResponse response) {
		String oldId = request.getParameter("oldId");
		String id = request.getParameter("dataSourceId");
		String classname = request.getParameter("classname");
		String url = request.getParameter("url");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String jndi = request.getParameter("jndi");
		 Map<String,Object> map = new HashMap<String, Object>();
		 List<Map<String, String>> datasource = dataSourceService.getDataSource(id);
		 if(oldId == null || "".equals(oldId)){ 
			 if(datasource.size()<1){
				 dataSourceService.updateDataSource(oldId,id,classname,url,username,password,jndi);
				 map.put("code", 1);
				 map.put("msg", "操作成功");
			 }else{
				 map.put("code", -1);
				 map.put("msg", "数据源名已经存在");
			 }
		 }else{
			 if( datasource.size()<1 || oldId.equals(datasource.get(0).get("id")) ){
				 dataSourceService.updateDataSource(oldId,id,classname,url,username,password,jndi);
				 map.put("code", 1);
				 map.put("msg", "操作成功");
			 }else{
				 map.put("code", -1);
				 map.put("msg", "数据源名已经存在");
			 }
		 }
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("delDataSourceById")
	public Map<String, Object> delDataSourceById(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("dataSourceId");
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			dataSourceService.delDataSourceById(id);
			map.put("code", 1);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			 map.put("code", -1);
			 map.put("msg", "操作失败");
		}
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping("getConnection")
	public Map<String, Object> getConnection(HttpServletRequest request,HttpServletResponse response) {
		String classname = request.getParameter("classname");
		String url = request.getParameter("url");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Map<String,Object> map = new HashMap<String, Object>();
		Connection connection = dataSourceService.getConnection(classname,url,username,password);
	    if (connection != null) {
	    	map.put("code", 1);
	    	map.put("msg", "测试成功");
	    } else {
	    	map.put("code", -1);
	    	map.put("msg", "测试失败");
	     }
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping("dataSourceBean")
	public Map<String, Object> dataSourceBean(HttpServletRequest request,HttpServletResponse response) {
		String classname = request.getParameter("classname");
		String url = request.getParameter("url");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			dataSourceService.dataSourceBean(classname, url, username, password);;
			map.put("code", 1);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			 map.put("code", -1);
			 map.put("msg", "操作失败");
		}
		
		return map;
	}
	
}
