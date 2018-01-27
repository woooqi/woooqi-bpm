package com.woooqi.bpm.controller.bpm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.organization.User;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.bpm.HistoryInstanceService;
import com.woooqi.bpm.utils.PageUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.organization.User;
import com.titan.entity.web.Page;
import com.titan.service.bpm.HistoryInstanceService;
import com.titan.utils.PageUtils;

@RestController
@RequestMapping("historyInstance")
public class HisInstanceController {
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private HistoryInstanceService historyInstanceService;
	
	@RequestMapping("/getHistoricProcessInstance")
	@ResponseBody
	 public Page<HistoricProcessInstance> getAllInstance(HttpServletRequest request, HttpServletResponse response) {
	        Page<HistoricProcessInstance> page = new Page<HistoricProcessInstance>();
	        int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "0" : request.getParameter("pageNumber"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "20" : request.getParameter("pageSize"));
			List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime().asc().listPage(pageNumber, pageSize);
	        page.setRows(list);
   	        page.setTotal(list.size());
	        return page;
	    }
	
	
	@RequestMapping("/getUnfinishedInstance")
	@ResponseBody
	 public Page<HistoricProcessInstance> getUnfinishedInstance(HttpServletRequest request,HttpServletResponse response) {
		User currentUser = PageUtils.getCurrentUser();
        Page<HistoricProcessInstance> page = new Page<HistoricProcessInstance>();
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "0" : request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "20" : request.getParameter("pageSize"));
		List<HistoricProcessInstance> instances = historyService.createHistoricProcessInstanceQuery().startedBy(currentUser.getId()).orderByProcessInstanceStartTime().asc().listPage(pageNumber, pageSize);
        List<HistoricProcessInstance> list = new ArrayList<>();
		for(HistoricProcessInstance hisInstance:instances){
			if(hisInstance.getEndTime()==null){
				list.add(hisInstance);
				
			}
        	
        }
		page.setRows(list);
        page.setTotal(list.size());
        return page;
    }
	
	
	
	@RequestMapping("/getFinishedInstance")
	@ResponseBody
	 public Page<HistoricProcessInstance> getFinishedInstance(HttpServletRequest request,HttpServletResponse response) {
		User currentUser = PageUtils.getCurrentUser();
        Page<HistoricProcessInstance> page = new Page<HistoricProcessInstance>();
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "0" : request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "20" : request.getParameter("pageSize"));
		List<HistoricProcessInstance> instances = historyService.createHistoricProcessInstanceQuery().startedBy(currentUser.getId()).orderByProcessInstanceStartTime().asc().listPage(pageNumber, pageSize);
        List<HistoricProcessInstance> list = new ArrayList<>();
		for(HistoricProcessInstance hisInstance:instances){
			if(hisInstance.getEndTime()!=null){
				list.add(hisInstance);
				
			}
        	
        }
		page.setRows(list);
        page.setTotal(list.size());
        return page;
    }

	@RequestMapping("/getInstanceImage")
	public void getInstanceImage(HttpServletRequest request,HttpServletResponse response){
		OutputStream os = null;
		String processInstanceId = request.getParameter("id")==null?"":request.getParameter("id");
		try {
			os = response.getOutputStream();
			byte[] bytes = historyInstanceService.getActivitiProccessImage(processInstanceId);
			response.setContentType("application/octet-stream; charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(("image.png").getBytes("UTF-8"), "iso-8859-1"));
			os.write(bytes);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@RequestMapping("/getHistoricTaskinst")
	@ResponseBody
	 public Page<Map<String,Object>> getHistoricTaskinst(HttpServletRequest request,HttpServletResponse response) {
	        int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "0" : request.getParameter("pageNumber"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "20" : request.getParameter("pageSize"));
			String processInstanceId = request.getParameter("processInstanceId")==null?"":request.getParameter("processInstanceId");
			Page<Map<String,Object>> page = historyInstanceService.getHistoricTaskinst(pageNumber,pageSize,processInstanceId);
			/*Page<HistoricTaskInstance> page = new Page<>();
			List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskCreateTime().asc().listPage(pageNumber , pageSize);
			page.setCode(1);
			page.setRows(list);
			page.setTotal(list.size());*/
	        return page;
	    }
	
}
