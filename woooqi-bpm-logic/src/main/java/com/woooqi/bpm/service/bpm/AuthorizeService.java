package com.woooqi.bpm.service.bpm;

import java.util.List;
import java.util.Map;


public interface AuthorizeService {
	
	public void saveAuthorization(String definitionId,List<Map<String, Object>> groups);
	
	public Map<String,Object> getGroupByDefinitionId(String definitionId);
	
}

