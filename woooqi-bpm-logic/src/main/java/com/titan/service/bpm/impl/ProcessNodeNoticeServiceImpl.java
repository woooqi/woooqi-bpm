package com.titan.service.bpm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.manage.ProcessNodeNotice;
import com.titan.repository.bpm.manage.ProcessNodeNoticeRepository;
import com.titan.service.bpm.ProcessNodeNoticeService;
import com.titan.utils.DataBaseUtils;

@Service
public class ProcessNodeNoticeServiceImpl implements ProcessNodeNoticeService{
	
	@Autowired
	private ProcessNodeNoticeRepository processNodeNoticeRepository;
	
	public List<ProcessNodeNotice> getProcessNodeNoticeByActId(String activitiId){
		List<ProcessNodeNotice> processNodeNotices = processNodeNoticeRepository.findByActivitiId(activitiId);
		return processNodeNotices;
		
	}

	@Override
	public Map<String, Object> saveProcessNodeNotice(List<ProcessNodeNotice> lists) {
		Map<String,Object> map = new HashMap<String, Object>();
		for(ProcessNodeNotice processNodeNotice:lists){
			processNodeNoticeRepository.saveAndFlush(processNodeNotice);
		}
		map.put("code", 1);
    	map.put("msg", "操作成功");
		return map;
	}

	@Override
	public void delProcessNodeNotice(String id) {
		processNodeNoticeRepository.delete(id);
	}

	@Override
	public List<Map<String, Object>> getProcessNodeNoticeUserByActId(String activitiId) {
		
		String sql ="select a.*,b.name from bpm_process_node_notice a,bpm_user b where a.user_id = b.id and a.activiti_id = ?";
		List<Map<String, Object>> lists = DataBaseUtils.queryForList(sql, activitiId);
		return lists;
	}

}
