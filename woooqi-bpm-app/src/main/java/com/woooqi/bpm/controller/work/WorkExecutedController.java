package com.woooqi.bpm.controller.work;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.bpm.bpm.DoneTask;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.work.WorkExecutedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.bpm.DoneTask;
import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.bpm.manage.ProcessNodeForm;
import com.titan.entity.organization.User;
import com.titan.entity.web.Page;
import com.titan.entity.work.Task;
import com.titan.service.bpm.ProcessNodeAssignService;
import com.titan.service.bpm.ProcessNodeFormService;
import com.titan.service.work.WorkStartedService;
import com.titan.service.work.WorkExecutedService;
import com.titan.utils.PageUtils;

@RestController
@RequestMapping("executed")
public class WorkExecutedController {
	
	@Autowired
	private WorkExecutedService workTaskDoneService;
	
	@RequestMapping("getAllDoneTasks")
	@ResponseBody
	public Page<DoneTask> getAllDoneTasks(HttpServletRequest request, HttpServletResponse response) {
		Page<DoneTask> page = new Page<DoneTask>();
		List<DoneTask> list =  workTaskDoneService.getAllDoneTasks();
		page.setRows(list);
	    page.setTotal(list.size());
		return page;
	}
	
	
}
