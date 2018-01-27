package com.titan.controller.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.titan.utils.PageUtils;

@RestController
public class ViewController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="/main_index",method=RequestMethod.GET)
	public ModelAndView getMain(HttpServletRequest request,HttpServletResponse response,ModelAndView model){
		try {
			model = view(request, response, model);
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/main_business",method=RequestMethod.GET)
	public ModelAndView getMainOrganization(HttpServletRequest request,HttpServletResponse response,ModelAndView model){
		try {
			model = view(request, response, model);
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
		
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public ModelAndView getEdit(HttpServletRequest request,HttpServletResponse response,ModelAndView model){
		try {
			model = view(request, response, model);
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	public ModelAndView view(HttpServletRequest request,HttpServletResponse response,ModelAndView model){
		Map<String, Object> map = PageUtils.getParameters(request);
		model.addAllObjects(map);
		model.addObject("_ids", PageUtils.getCurrentUser()==null?"":PageUtils.getCurrentUser().getRoleIds());
		String path = null;
		if(map.get("path")!=null){
			path = map.get("path").toString();
		}else{
			logger.error("There is no page!");
		}
		
		model.setViewName(path);
		return model;
	}

}
