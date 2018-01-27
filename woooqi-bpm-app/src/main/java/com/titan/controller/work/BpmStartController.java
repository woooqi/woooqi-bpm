package com.titan.controller.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.bpm.ProcessDefinition;
import com.titan.service.bpm.DefinitionService;
import com.titan.service.work.BpmStartService;
import com.titan.utils.PageUtils;

@RestController
@RequestMapping("bpm")
public class BpmStartController {

	@Autowired
	private DefinitionService definitionService;
	
	@Autowired
	private BpmStartService bpmStartService;
	
	private Logger logger = Logger.getLogger(this.getClass());
		
		
	@RequestMapping("getAuthorizeProcessDefinition")
	@ResponseBody
	public List<ProcessDefinition> getAuthorizeProcessDefinition(HttpServletRequest request,HttpServletResponse response) {
		List<ProcessDefinition> list = bpmStartService.getAuthorizeProcessDefinition();
		return list;
	}
	
	@RequestMapping("startDefinition")
	@ResponseBody
	public Map<String,Object> startDefinition(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		try{
    		String processKey = request.getParameter("processKey")==null?"":request.getParameter("processKey");
    		String Bussinesskey = PageUtils.getUUID();
    		Map<String,Object> param = new HashMap<String, Object>();
    		param = null;
    		bpmStartService.startProcess(processKey, Bussinesskey, param);
         	map.put("code",1);
         } catch (Exception e) {
        	map.put("code", -1);
 			map.put("msg",e.getMessage());
         }
		return map;
	}
	
}
