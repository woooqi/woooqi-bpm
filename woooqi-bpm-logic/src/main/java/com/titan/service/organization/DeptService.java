package com.titan.service.organization;

import java.util.List;
import java.util.Map;

import com.titan.entity.organization.Dept;

public interface DeptService {

	public Dept getByName(String name);
	
	public List<Dept> getByNameAndParentId(String name,String parentId);
	
	public List<Map<String,String>> getTreeDept();
	
	public List<Dept> getAllDeptByName(String parentId, int pageNumber, int pageSize);
	
	public Dept getDeptById(String id);
	
	public void delById(String id);
	
	public  Map<String,Object> save(String name,String parentId,int status,int sort,String deptCode);
	
	public Map<String,Object> update(String id,String name,String parentId,int status,int sort,String deptCode);

	public List<Map<String, Object>> getEnableDept();
	
}
