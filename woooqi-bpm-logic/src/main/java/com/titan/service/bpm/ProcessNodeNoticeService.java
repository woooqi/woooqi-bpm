package com.titan.service.bpm;

import java.util.List;
import java.util.Map;

import com.titan.entity.bpm.manage.ProcessNodeNotice;

public interface ProcessNodeNoticeService {
	public List<ProcessNodeNotice> getProcessNodeNoticeByActId(String activitiId);

	public Map<String, Object> saveProcessNodeNotice(List<ProcessNodeNotice> lists);
	
	public void delProcessNodeNotice(String id);
	 
	public List<Map<String,Object>> getProcessNodeNoticeUserByActId(String activitiId);
	 
}
