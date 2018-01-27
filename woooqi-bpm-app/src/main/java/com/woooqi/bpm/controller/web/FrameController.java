package com.woooqi.bpm.controller.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.organization.User;
import com.woooqi.bpm.manage.shiro.ShiroCredentialsMatcher;
import com.woooqi.bpm.utils.PageUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.titan.entity.organization.User;
import com.titan.manage.shiro.ShiroCredentialsMatcher;
import com.titan.utils.PageUtils;

@RestController
public class FrameController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="/login",method=RequestMethod.GET)  
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response,ModelAndView model){ 
		
		User user = PageUtils.getCurrentUser();
		model.addObject("user", user);
		model.addObject("ctx", request.getContextPath());
		model.setViewName("login");
		return model;
    }  
      
	@RequestMapping(value="/login",method=RequestMethod.POST)  
    public ModelAndView login_fail(HttpServletRequest request, HttpServletResponse response, ModelAndView model){  
		String attribute = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
    	if(attribute != null){
	    	if(attribute.indexOf("UnknownAccountException")>0){
	    		model.addObject("errorMessage", "用户名或者密码不正确");
	    	}else if(attribute.indexOf("IncorrectCredentialsException")>0){
	    		model.addObject("errorMessage", "用户名或者密码不正确");
	    	}else if(attribute.indexOf("LockedAccountException")>0){
	    		model.addObject("errorMessage", "账户已锁定");
	    	}else if (attribute.indexOf("ExcessiveAttemptsException")> 0) {  
	    		model.addObject("errorMessage", "密码错误次数过多，账户锁定10分钟");
	        }else if(attribute.indexOf("CaptchaIncorrectException")>0){
	        	model.addObject("errorMessage", "验证码不正确");
	        }else{
	    		model.addObject("errorMessage", "用户名或者密码不正确");
	    	}
    	}

    	model.addObject("loginCount", ShiroCredentialsMatcher.LOGINCOUNT);
    	
		User user = PageUtils.getCurrentUser();
		model.addObject("user", user);
		model.addObject("ctx", request.getContextPath());
		model.setViewName("login");
		return model;
    }

    @RequestMapping(value="/logout",method=RequestMethod.GET)    
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response,ModelAndView model){   
    	SecurityUtils.getSubject().logout();    
    	model.setViewName("redirect:/login"); 
    	return model;
    }
    
	@RequestMapping(value="/unauthorize",method=RequestMethod.GET)  
    public ModelAndView unauth(HttpServletRequest request, HttpServletResponse response,ModelAndView model){ 
		
		User user = PageUtils.getCurrentUser();
		model.addObject("user", user);
		model.addObject("ctx", request.getContextPath());
		
		model.setViewName("base/permission");
		
		return model;  
    }
	
	@RequestMapping(value="/error",method=RequestMethod.GET)  
    public String error(HttpServletRequest request, HttpServletResponse response,Model model){ 
		return "base/error";  
    }
	
}