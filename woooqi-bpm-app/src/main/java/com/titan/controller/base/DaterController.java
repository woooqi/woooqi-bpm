package com.titan.controller.base;


import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.titan.entity.base.date.Dater;
import com.titan.entity.web.Page;
import com.titan.service.base.DaterService;

@RestController
@RequestMapping("dater")
public class DaterController {

	@Autowired
	private DaterService daterService;
	
	@ResponseBody
	@RequestMapping("getAllDater")
	public Page<Dater> getAllDater(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("name");
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber")==null?"0":request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize")==null?"20":request.getParameter("pageSize"));
		List<Dater> list = daterService.getAllDater(pageNumber, pageSize, name);
		Page<Dater> page = new Page<Dater>();
		page.setCode(1);
		page.setRows(list);
		page.setMsg("");
		page.setTotal(list.size());

		return page;

	}
	
	@ResponseBody
	@RequestMapping("updateDater")
	public Map<String,Object> updateDater(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String id = request.getParameter("id");
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String type = request.getParameter("type")==null?"":request.getParameter("type");
		String html_date = request.getParameter("date")==null?"":request.getParameter("date");
		Dater dater = daterService.getDaterById(id);
		try {
			Date date  = sdf.parse(html_date);
		
			if(StringUtils.isBlank(id)){
				if(dater == null){
					daterService.updateDater(id, name, date,type);
					map.put("code", 1);
					map.put("msg", "保存成功");
				}else{
					map.put("code", -1);
					map.put("msg", "模板名已存在");
				}
			}else{
				Dater daterById = daterService.getDaterById(id);
				if(daterById == null || name.equals(daterById.getName())){
					daterService.updateDater(id, name, date,type);
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
	@RequestMapping("delDaterById")
	public Map<String, Object> delDaterById(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			daterService.delDaterById(id);
			map.put("code", 1);
		} catch (Exception e) {
			map.put("code", -1);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("getDaterById")
	public Dater getDaterById(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		Dater dater = daterService.getDaterById(id);
		return dater;
	}
	
}
