package com.woooqi.bpm.service.bpm.form.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.form.CustomTable;
import com.titan.entity.bpm.form.CustomTableColumn;
import com.titan.repository.bpm.form.CustomTableColumnRepository;
import com.titan.repository.bpm.form.CustomTableRepository;
import com.titan.service.bpm.form.CustomTableService;

@Service
public class CustomTableServiceImpl implements CustomTableService {
	
	@Autowired
	private CustomTableRepository customTableRepository;
	
	@Autowired
	private CustomTableColumnRepository customTableColumnRepository;

	@Override
	public Map<String, Object> saveCustomTable(CustomTable customTable , List<Map<String, Object>> lists) {
		Map<String,Object> map = new HashMap<>();
		CustomTable before = customTableRepository.findOne(customTable.getId());
		Set<CustomTableColumn> customTableColumns = new HashSet<>();
		try {
			if(before!=null){
				Set<CustomTableColumn> sets = before.getCloumns();
				for(CustomTableColumn column : sets){
					customTableColumnRepository.delete(column);
				}
				
			}
			
			for(int i=0;i<lists.size();i++){
				CustomTableColumn customTableColumn = new CustomTableColumn();
				customTableColumn.setComments(lists.get(i).get("comments").toString());
				customTableColumn.setDataSourceId(lists.get(i).get("dataSourceId").toString());
				customTableColumn.setField(lists.get(i).get("field").toString());
				String lengths = lists.get(i).get("lengths").toString();
				customTableColumn.setLengths(lengths==null?1:Integer.parseInt(lengths));
				customTableColumn.setTable(customTable);
				String sort = lists.get(i).get("sort").toString();
				customTableColumn.setSort(sort==null?1:Integer.parseInt(sort));
				customTableColumn.setTableName(lists.get(i).get("tableName").toString());
				customTableColumn.setType(lists.get(i).get("type").toString());
				customTableColumns.add(customTableColumn);
				
			}
			customTable.setCloumns(customTableColumns);
			customTableRepository.saveAndFlush(customTable);
			map.put("code", 1);
	    	map.put("msg", "操作成功");
			
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		return map;
	}

	@Override
	public CustomTable getCustomTableByTableId(String id) {
		CustomTable table = customTableRepository.findOne(id);
		return table;
	}

	@Override
	public List<CustomTableColumn> getCustomTableColumnByTable(String tableId) {
		CustomTable table = customTableRepository.findOne(tableId);
		List<CustomTableColumn> customTableColumns = customTableColumnRepository.findByTableOrderBySortAsc(table);
		return customTableColumns;
	}
	
	@Override
	public List<CustomTable> getAllCustomTable(int pageNumber, int pageSize) {
		//Sort sort = new Sort(Sort.Direction.ASC, "createTime");
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		Page<CustomTable> customTables = customTableRepository.findAll(pageRequest);
		List<CustomTable> lists =customTables.getContent();	
		
		for(CustomTable customTable:lists){
			Set<CustomTableColumn> sets = new HashSet<CustomTableColumn>();
			List<CustomTableColumn> customTableColumns = customTableColumnRepository.findByTableOrderBySortAsc(customTable);
			sets.addAll(customTableColumns);
			customTable.setCloumns(sets);
		}
		return lists;
	}

	@Override
	public Map<String, Object> delCustomTable(String id) {
		Map<String,Object> map = new HashMap<>();
		try {
			CustomTable table = customTableRepository.findOne(id);
			List<CustomTableColumn> customTableColumns = customTableColumnRepository.findByTableOrderBySortAsc(table);
			for(CustomTableColumn column : customTableColumns){
				customTableColumnRepository.delete(column);
			}
			customTableRepository.delete(id);
			map.put("code", 1);
	    	map.put("msg", "操作成功");
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> getTableByTableId(String id) {
		Map<String,Object> map = new HashMap<>();
		CustomTable table = customTableRepository.findOne(id);
		map.put("table", table);
		List<CustomTableColumn> customTableColumns = customTableColumnRepository.findByTableOrderBySortAsc(table);
		map.put("column", customTableColumns);
		return map;
	}

	@Override
	public List<CustomTable> getAllSelectCustomTable() {
		List<CustomTable> findAll = customTableRepository.findAll();
		return findAll;
	}

}
