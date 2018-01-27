package com.woooqi.bpm.service.bpm.impl;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.manage.ProcessNodeForm;
import com.titan.entity.bpm.manage.ProcessNodeFormButton;
import com.titan.repository.bpm.manage.ProcessNodeFormButtonRepository;
import com.titan.repository.bpm.manage.ProcessNodeFormRepository;
import com.titan.service.bpm.ProcessNodeFormService;

@Service
public class ProcessNodeFormServiceImpl implements ProcessNodeFormService {
	
	@Autowired
	private ProcessNodeFormRepository processNodeFormRepository;
	
	@Autowired
	private ProcessNodeFormButtonRepository processNodeFormButtonRepository;
	
	

	@Override
	public ProcessNodeForm getProcessNodeFormByActivitiId(String activitiId) {
		return  processNodeFormRepository.findByActivitiId(activitiId);
	
	}

	@Override
	public Map<String,Object> getFormByActivitiId(String activitiId) {
		Map<String,Object> map = new HashMap<>();
		ProcessNodeForm processNodeForm = processNodeFormRepository.findByActivitiId(activitiId);
		map.put("form", processNodeForm);
		if(processNodeForm != null){
			Set<ProcessNodeFormButton> sets = processNodeForm.getButtons();
			map.put("ops", sets);
		}
		return map;
	
	}

	
	@Override
	public Map<String, Object> saveProcessForm(ProcessNodeForm processNodeForm, List<Map<String, Object>> lists) {
		Map<String,Object> map = new HashMap<>();
		ProcessNodeForm before = processNodeFormRepository.findOne(processNodeForm.getId());
		Set<ProcessNodeFormButton> processNodeFormOperates = new HashSet<>();
		try {
			if(before!=null){
				
				/*Set<ProcessNodeFormButton> sets = before.getOperates();
				for(ProcessNodeFormButton operate : sets){
					processNodeFormOperateRepository.delete(operate);
				}*/
				
			}
			
			for(int i=0;i<lists.size();i++){
				ProcessNodeFormButton processNodeFormOperate = new ProcessNodeFormButton();
				processNodeFormOperate.setId(lists.get(i).get("id").toString());
				//processNodeFormOperate.setForm(processNodeForm);
				processNodeFormOperate.setDescription(lists.get(i).get("description").toString());
				processNodeFormOperate.setName(lists.get(i).get("name").toString());
				processNodeFormOperates.add(processNodeFormOperate);
				
			}
			//processNodeForm.setOperates(processNodeFormOperates);
			processNodeFormRepository.saveAndFlush(processNodeForm);
			map.put("code", 1);
	    	map.put("msg", "操作成功");
			
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		
		
		return map;
	}

	@Override
	public Map<String, Object> saveProcessNodeForm(ProcessNodeForm processNodeForm, List<String> buttons) {
		Map<String,Object> map = new HashMap<>();
		ProcessNodeForm before = processNodeFormRepository.findOne(processNodeForm.getId());
		Set<ProcessNodeFormButton> processNodeFormButtons = new HashSet<>();
		try {
			if(before!=null){
				before.getButtons().clear();
				//processNodeFormButtonRepository.delete(button);
				
			}
			for(int i=0;i<buttons.size();i++){
				ProcessNodeFormButton processNodeFormButton = processNodeFormButtonRepository.findOne(buttons.get(i));
				if(processNodeFormButton!=null){
					processNodeFormButtons.add(processNodeFormButton);
				}
				
			}
			processNodeForm.setButtons(processNodeFormButtons);
			processNodeFormRepository.saveAndFlush(processNodeForm);
			map.put("code", 1);
	    	map.put("msg", "操作成功");
			
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		
		
		return map;
	}

	

}
