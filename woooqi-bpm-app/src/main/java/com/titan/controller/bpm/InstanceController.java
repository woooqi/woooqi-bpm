package com.titan.controller.bpm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.bpm.Instance;
import com.titan.entity.web.Page;
import com.titan.service.bpm.InstanceService;

@RestController
@RequestMapping("instance")
public class InstanceController {
	@Autowired
	InstanceService instanceService;
	
	@RequestMapping("/getAllInstance")
	@ResponseBody
	 public Page<Instance> getAllInstance(HttpServletRequest request,HttpServletResponse response) {
	        Page<Instance> page = new Page<Instance>();
	        String name = request.getParameter("name")==null?"":request.getParameter("name");
	        List<Instance> list = instanceService.getAllInstance(name);;
	        page.setRows(list);
   	        page.setTotal(list.size());
	        return page;
	    }
	
	@RequestMapping("/spendInstance")
	@ResponseBody
	 public Map<String,Object> spendInstance(HttpServletRequest request,HttpServletResponse response) {
			Map<String,Object> map = new HashMap<String, Object>();
	        String instanceId = request.getParameter("instanceId")==null?"":request.getParameter("instanceId");
	        try {
		        instanceService.pendInstance(instanceId);
		        map.put("code",1);
		        
			} catch (Exception e) {
				map.put("code",-1);
				map.put("msg","操作失败");
			}
	        return map;    
	    }
	
	@RequestMapping("/activeInstance")
	@ResponseBody
	public Map<String,Object> active(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String instanceId = request.getParameter("instanceId")==null?"":request.getParameter("instanceId");
		try {
		        instanceService.activeInstance(instanceId);
		        map.put("code",1);
		        
			} catch (Exception e) {
				map.put("code",-1);
				map.put("msg","操作失败");
			}
	        return map;    
	    }
	
	@RequestMapping("/getInstanceImage")
	public void getInstanceImage(HttpServletRequest request,HttpServletResponse response){
		OutputStream os = null;
		String processInstanceId = request.getParameter("id")==null?"":request.getParameter("id");
		try {
			os = response.getOutputStream();
			byte[] bytes = instanceService.getActivitiProccessImage(processInstanceId);
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
}
