package com.woooqi.bpm.service.bpm.form;

import java.util.List;
import java.util.Map;

import com.titan.entity.bpm.form.CustomTable;
import com.titan.entity.bpm.form.CustomTableColumn;



public interface CustomTableService {

	public Map<String, Object> saveCustomTable(CustomTable customTable , List<Map<String,Object>> lists);

	public CustomTable getCustomTableByTableId(String id);	
	
	public Map<String,Object> getTableByTableId(String id);	
	
	public List<CustomTableColumn> getCustomTableColumnByTable(String tableId);
	
	public List<CustomTable> getAllCustomTable(int pageNumber, int pageSize);
	
	public Map<String,Object> delCustomTable(String id);

	public List<CustomTable> getAllSelectCustomTable();

}
