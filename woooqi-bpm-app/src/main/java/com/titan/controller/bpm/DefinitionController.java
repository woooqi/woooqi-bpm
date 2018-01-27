package com.titan.controller.bpm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.titan.entity.bpm.bpm.Instance;
import com.titan.entity.bpm.bpm.ProcessDefinition;
import com.titan.entity.web.Page;
import com.titan.service.bpm.DefinitionService;
import com.titan.service.bpm.InstanceService;

@RestController
@RequestMapping("definition")
public class DefinitionController {
	@Autowired
	private DefinitionService definitionService;
	
	@Autowired
	InstanceService instanceService;
	
	@ResponseBody
	@RequestMapping("getAllProcessDefinition")
	public Page<ProcessDefinition> getAllProcessDefinition(HttpServletRequest request,
			HttpServletResponse response) {
		
		String name = request.getParameter("name_")==null?"":request.getParameter("name_");
		List<ProcessDefinition> list = definitionService.getDefinitionByName(name);
		Page<ProcessDefinition> page = new Page<ProcessDefinition>();
		page.setCode(1);
		page.setRows(list);
		page.setMsg("");
		page.setTotal(list.size());

		return page;

	}
	
	//升级(即二次部署)
	@RequestMapping("/deploymentOnWeb")
	public Map<String,Object> deploymentOnWeb(HttpServletRequest request,HttpServletResponse response){
	 	Map<String,Object> map = new HashMap<String,Object>();
	 	try {
			String modelId = request.getParameter("deployId");
			definitionService.deploymentOnWeb(modelId);
			map.put("code", 1);
		} catch (Exception e) {
			map.put("code",-1);
			map.put("msg",e.getMessage());
		}
	 	return map;
		 
	 }
		 
	 //降级(删除)
	 @RequestMapping("/delDeploy")
	 	public Map<String,Object> delDeploy(HttpServletRequest request,HttpServletResponse response){
		 	Map<String,Object> map = new HashMap<String, Object>();
		 	try {
		 		String deployId = request.getParameter("deployId");
		 		Instance instance =instanceService.getInstanceByDeployId(deployId);
		 		if(instance == null){
		 			definitionService.delDeploy(deployId);
			 		map.put("code", 1);
		 		}else{
		 			map.put("code", -1);
		 			map.put("msg", "此版本流程已发起,目前无法进行降级操作");
		 		}
		 		
			} catch (Exception e) {
				map.put("code",-1);
				map.put("msg", "操作失败");
			}
		 	return map;
	 }
	//离线部署
	@RequestMapping("/deploymentOnLine")
	public Map<String,Object> deploymentOnLine(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MultipartFile file = null;
			CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getServletContext());
			String fileNames = "";
			if (resolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					fileNames = iter.next();
					file = multiRequest.getFile(fileNames);
				}
			}
			String suffixList = "zip";
			String name = file.getOriginalFilename();
			String suffix =  name.substring(name.lastIndexOf(".") + 1,name.length());
			if ( file == null||file.isEmpty() || !suffixList.contains(suffix.trim().toLowerCase())) {
				map.put("code", "-1");
				return map;
			}
			definitionService.deploymentOnLine(name, file.getBytes());
	     	map.put("code",1);
	     } catch (Exception e) {
	    	e.printStackTrace();
	    	map.put("code", -1);
			map.put("msg",e.getMessage());
	     }
		 return map;
	 
	}
	
	
	
}
