package com.titan.controller.base;


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

import com.titan.entity.base.base.Template;
import com.titan.entity.web.Page;
import com.titan.service.base.TemplateService;

@RestController
@RequestMapping("template")
public class TemplateController {

	@Autowired
	private TemplateService templateService;
	
	@ResponseBody
	@RequestMapping("getAllTemplates")
	public Page<Template> getAllTemplates(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("name");
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber")==null?"0":request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize")==null?"20":request.getParameter("pageSize"));
		List<Template> list = templateService.getAllTemplates(pageNumber, pageSize, name);
		Page<Template> page = new Page<Template>();
		page.setCode(1);
		page.setRows(list);
		page.setMsg("");
		page.setTotal(list.size());

		return page;

	}
	
	@ResponseBody
	@RequestMapping("updateTemplate")
	public Map<String,Object> updateProcessFlow(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String comments = request.getParameter("comments")==null?"":request.getParameter("comments");
		Template template = templateService.getTemplateByName(name);
		try {
			if(StringUtils.isBlank(id)){
				if(template == null){
					templateService.updateTemplate(id,name,comments);
					map.put("code", 1);
					map.put("msg", "保存成功");
				}else{
					map.put("code", -1);
					map.put("msg", "模板名已存在");
				}
			}else{
				Template templateById = templateService.getTemplateById(id);
				if(templateById == null || name.equals(templateById.getName())){
					templateService.updateTemplate(id,name,comments);
					map.put("code", 1);
					map.put("msg", "保存成功");
				}else{
					map.put("code", -1);
					map.put("msg", "模板名已存在");
				}
				
			}	
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping("delTemplateById")
	public Map<String, Object> delTemplateById(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			templateService.delTemplateById(id);
			map.put("code", 1);
		} catch (Exception e) {
			map.put("code", -1);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("getTemplateById")
	public Template getTemplateById(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		Template template = templateService.getTemplateById(id);
		return template;
	}
	
}
