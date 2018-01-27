package com.titan.utils;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.titan.entity.organization.User;


public class PageUtils{
	
	public static User getCurrentUser(){
		
		Subject subject = SecurityUtils.getSubject();
		
		if(subject.getPrincipal() instanceof User){
			User user = (User) subject.getPrincipal();
			return user;
		}
		return null;
	}

	public static Map<String,Object> getParameters(HttpServletRequest request){
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		Enumeration<String> names = request.getParameterNames();
		
		while(names.hasMoreElements()){
			String key = names.nextElement();
			String value = request.getParameter(key);
			map.put(key, value);
		}
		return map;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	
}
