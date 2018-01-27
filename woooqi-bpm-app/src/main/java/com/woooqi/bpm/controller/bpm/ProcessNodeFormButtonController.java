package com.woooqi.bpm.controller.bpm;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeFormButton;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.bpm.ProcessNodeFormButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.titan.entity.bpm.form.CustomForm;
import com.titan.entity.bpm.manage.ProcessNodeFormButton;
import com.titan.entity.web.Page;
import com.titan.service.bpm.ProcessNodeFormButtonService;
import com.titan.service.bpm.form.CustomFormService;

@RestController
@RequestMapping("ProcessNodeFormButton")
public class ProcessNodeFormButtonController {
	
	@Autowired
	private ProcessNodeFormButtonService processNodeFormButtonService;
	

	@RequestMapping("getAllProcessNodeFormButton")
	@ResponseBody
	public Page<ProcessNodeFormButton> getAllProcessNodeFormButton(HttpServletRequest request, HttpServletResponse response){
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "0" : request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "20" : request.getParameter("pageSize"));
		List<ProcessNodeFormButton> processNodeFormButtons = processNodeFormButtonService.getAllProcessNodeFormButton(pageNumber, pageSize);
		Page<ProcessNodeFormButton> page = new Page<>();
		page.setCode(1);
		page.setRows(processNodeFormButtons);
		page.setTotal(processNodeFormButtons.size());
		return page;
				
	} 

}
