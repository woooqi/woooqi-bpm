package com.woooqi.bpm.controller.organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.organization.Dept;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.organization.DeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.organization.Dept;
import com.titan.entity.web.Page;
import com.titan.service.organization.DeptService;

@RestController
@RequestMapping("dept")
public class DeptController {

	@Autowired
	private DeptService deptService;

	@RequestMapping("getAllDept")
	@ResponseBody
	public List<Map<String,String>> getAllDept(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String,String>> lists = deptService.getTreeDept();
		return lists;
	}
	
	@RequestMapping("getEnableDept")
	@ResponseBody
	public List<Map<String,Object>> getEnableDept(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String,Object>> lists = deptService.getEnableDept();
		return lists;
	}

	@RequestMapping("getAllDeptByName")
	@ResponseBody
	public Page<Dept> getAllDeptByName(HttpServletRequest request, HttpServletResponse response) {
		Page<Dept> page = new Page<Dept>();
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "0" : request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "20" : request.getParameter("pageSize"));
		String parentId = request.getParameter("parentId")==null?"0":request.getParameter("parentId");
		//String mane = request.getParameter("mane");
		List<Dept> lists = deptService.getAllDeptByName(parentId, pageNumber, pageSize);
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}

	@RequestMapping("getDeptById")
	@ResponseBody
	public Dept getUserById(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id") == null ? "" : request.getParameter("id");

		Dept dept = deptService.getDeptById(id);

		return dept;
	}

	@RequestMapping("saveDept")
	@ResponseBody
	public Map<String,Object> saveDept(HttpServletRequest request, HttpServletResponse response) {
		String parentId = request.getParameter("parentId")==null?"0":request.getParameter("parentId");
		String name = request.getParameter("name");
		String deptCode = request.getParameter("deptCode");
		String id= request.getParameter("id");
		Integer status = Integer.parseInt(request.getParameter("status")==null?"1":request.getParameter("status"));
		Integer sort = Integer.parseInt(request.getParameter("sort")==null?"1":request.getParameter("sort"));

		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isNotEmpty(id)){
			map =  deptService.save(name, parentId,status,sort,deptCode);
		}else{
			 map =  deptService.update(id,name, parentId,status,sort,deptCode);	
		}
			

		return map;
	}

	@RequestMapping("deleteDept")
	@ResponseBody
	public Map<String, Object> deleteDept(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		List<Dept> list = deptService.getAllDeptByName(id, 0, 20);
		if(list==null||list.size()==0){
			try { 
				deptService.delById(id);
				map.put("code", 1);
				map.put("msg", "删除成功");
			} catch (Exception e) {
				map.put("code", -1);
				map.put("msg", e.getMessage());
			}
		}else{
			map.put("code", -1);
			map.put("msg", "不能直接删除父级部门");
		}

		return map;
	}

}
