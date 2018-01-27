package com.woooqi.bpm.service.bpm.form;

import java.util.List;
import java.util.Map;

import com.titan.entity.bpm.form.CustomForm;



public interface CustomFormService {

	public Map<String, Object> saveCustomForm(CustomForm customForm);

	public CustomForm getCustomFormById(String id);	
			
	public List<CustomForm> getAllCustomForm(int pageNumber, int pageSize);
	
	public Map<String,Object> delCustomForm(String id);


	public String createForm(String formId, String name, String description,Integer columns, String tableId);

	public Map<String, Object> saveForm(String formId, String name, String description, Integer columns, String tableId);

	public Map<String,Object> getCustomFormHtmlById(String formId);
	
	Map<String, Object> saveFormHtml(String formId, String html);

	CustomForm getCustomFormHtmlName(String name);

}
