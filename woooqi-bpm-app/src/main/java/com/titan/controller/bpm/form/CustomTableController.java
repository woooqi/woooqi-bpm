package com.titan.controller.bpm.form;

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

import com.titan.entity.bpm.form.CustomTable;
import com.titan.entity.bpm.form.CustomTableColumn;
import com.titan.entity.web.Page;
import com.titan.service.bpm.form.CustomTableService;
import com.titan.utils.JsonUtils;

@RestController
@RequestMapping("customTable")
public class CustomTableController {
	
	@Autowired
	private CustomTableService customTableService;

	
	@RequestMapping("saveCustomTable")
	@ResponseBody
	public Map<String,Object> saveCustomTable(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map =new HashMap<String, Object>();
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String description = request.getParameter("description")==null?"":request.getParameter("description");
		String cloumns = request.getParameter("cloumns")==null?"":request.getParameter("cloumns");
		List<Map<String,Object>> lists = null;
		if(StringUtils.isNotEmpty(cloumns)){
			lists = JsonUtils.jsonToList(cloumns);
		}
		CustomTable table = new CustomTable();
		table.setId(id);
		table.setName(name);
		table.setDescription(description);
		map  = customTableService.saveCustomTable(table, lists);
		return map;
	}
	
	
	@RequestMapping("getCustomTableByTableId")
	@ResponseBody
	public CustomTable getCustomTableByTableId(HttpServletRequest request,HttpServletResponse response){
		String tableId = request.getParameter("tableId")==null?"":request.getParameter("tableId");
		CustomTable table = customTableService.getCustomTableByTableId(tableId);
		return table;
	}
	
	@RequestMapping("getTableByTableId")
	@ResponseBody
	public Map<String,Object> getTableByTableId(HttpServletRequest request,HttpServletResponse response){
		String tableId = request.getParameter("tableId")==null?"":request.getParameter("tableId");
		Map<String,Object> map = customTableService.getTableByTableId(tableId);
		return map;
	}
	
	
	@RequestMapping("getCustomTableColumnByTable")
	@ResponseBody
	public List<CustomTableColumn> getCustomTableColumnByTable(HttpServletRequest request,HttpServletResponse response){
		String tableId = request.getParameter("tableId")==null?"":request.getParameter("tableId");
		List<CustomTableColumn> lists = customTableService.getCustomTableColumnByTable(tableId);
		return lists;
				
	} 
	
	
	@RequestMapping("getAllCustomTable")
	@ResponseBody
	public Page<CustomTable> getAllCustomTable(HttpServletRequest request,HttpServletResponse response){
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "0" : request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "20" : request.getParameter("pageSize"));
		List<CustomTable> customTables = customTableService.getAllCustomTable(pageNumber, pageSize);
		Page<CustomTable> page = new Page<>();
		page.setCode(1);
		page.setRows(customTables);
		page.setTotal(customTables.size());
		return page;
				
	} 
	
	@RequestMapping("getAllSelectCustomTable")
	@ResponseBody
	public List<CustomTable> getAllSelectCustomTable(HttpServletRequest request,HttpServletResponse response){
		List<CustomTable> customTables = customTableService.getAllSelectCustomTable();
		return customTables;
				
	} 
	
	@RequestMapping("delCustomTable")
	@ResponseBody
	public Map<String,Object> delCustomTable(HttpServletRequest request, HttpServletResponse response){
		String tableId = request.getParameter("tableId")==null?"":request.getParameter("tableId");
		Map<String, Object> map = customTableService.delCustomTable(tableId);
		return map;
	}
 


}
