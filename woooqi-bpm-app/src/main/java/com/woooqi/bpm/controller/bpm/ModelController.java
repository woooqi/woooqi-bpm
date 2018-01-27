package com.woooqi.bpm.controller.bpm;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.bpm.DeployService;
import com.woooqi.bpm.service.bpm.ModelService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.titan.entity.web.Page;
import com.titan.service.bpm.DeployService;
import com.titan.service.bpm.ModelService;


@RestController 
@RequestMapping("model")
public class ModelController  { 
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private DeployService deployService;
	
	@RequestMapping("/getAllModel")
	@ResponseBody
    public Page<Model> getAllModel(HttpServletRequest request, HttpServletResponse response) {
        Page<Model> page = new Page<Model>();
        String name = request.getParameter("name")==null?"":request.getParameter("name");
		String key = request.getParameter("key")==null?"":request.getParameter("key");
        List<Model> list = modelService.getAllModel(name,key);
        page.setRows(list);
        page.setTotal(list.size());
        return page;
    }
   
    @RequestMapping(value="createModel")
    public ModelAndView createModel(HttpServletRequest request, HttpServletResponse response,ModelAndView model) {
        try {
        	String name = request.getParameter("name");
        	String key = request.getParameter("key");
        	String description = request.getParameter("description");
        	String id = modelService.createModel(key, name, description);
            model.setViewName("redirect:/flowable/modeler.html?modelId="+id +"&key="+key);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return model;
    }

   
    @RequestMapping("/deployModel")
	@ResponseBody
    public Map<String, Object> deployModel(HttpServletRequest request,HttpServletResponse response) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	 try{
    		 String modelId = request.getParameter("modelId")==null?"":request.getParameter("modelId");
    		
         	modelService.deploymentOnWeb(modelId);
         	map.put("code",1);
         } catch (Exception e) {
        	 map.put("code", -1);
 			map.put("msg",e.getMessage());
         }
    	 return map;
    }
   
    @RequestMapping("/delModel")
    public Map<String,Object> delModel(HttpServletRequest request,HttpServletResponse response) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
         	String modelId = request.getParameter("modelId");
         	String deploymentId = request.getParameter("deploymentId")==null?"":request.getParameter("deploymentId");
          	if(!"".equals(deploymentId)){
          		map.put("code",-2);
          		
          	}else{
          		modelService.delModel(modelId);
          		map.put("code",1);
          		}
         	
         } catch (Exception e) {
        	 map.put("code", -1);
 			map.put("msg",e.getMessage());
         }
    	 return map;
     
    }
    
    
    
}  
