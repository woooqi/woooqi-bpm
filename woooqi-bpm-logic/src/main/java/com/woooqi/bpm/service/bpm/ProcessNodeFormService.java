package com.woooqi.bpm.service.bpm;



import java.util.List;
import java.util.Map;

import com.titan.entity.bpm.manage.ProcessNodeForm;


public interface ProcessNodeFormService {
	
	
	public ProcessNodeForm getProcessNodeFormByActivitiId(String activitiId);

	public Map<String, Object> saveProcessForm(ProcessNodeForm processNodeForm, List<Map<String, Object>> operas);

	public 	Map<String, Object> getFormByActivitiId(String activitiId);

	public Map<String, Object> saveProcessNodeForm(ProcessNodeForm processNodeForm, List<String> sets);
	
}
