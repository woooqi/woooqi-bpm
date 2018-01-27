package com.titan.service.bpm;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;


public interface ProcessNodeAssignService {

	public Map<String, Object> saveNodeManageAssign(ProcessNodeAssign processNodeAssign, String type, Set<String> users);

	public ProcessNodeAssign getNodeManageAssignByActId(String activitiId);

	public Map<String, Object> saveNodeManageGroupAssign(ProcessNodeAssign processNodeAssign,String type,List<Map<String, Object>> groups);

	public Map<String, Object> getGroupByAssignTypeId(String assignId);
	
	public List<ProcessNodeAssignType> getProcessNodeAssignTypeByAssign(String assignId);


	
}
