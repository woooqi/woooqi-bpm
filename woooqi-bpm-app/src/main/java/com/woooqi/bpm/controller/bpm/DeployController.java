package com.woooqi.bpm.controller.bpm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.bpm.bpm.Deployment;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.bpm.DeployService;
import com.woooqi.bpm.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.bpm.bpm.Deployment;
import com.titan.entity.web.Page;
import com.titan.service.bpm.DeployService;
import com.titan.utils.FileUtils;

@RestController
@RequestMapping("deploy")
public class DeployController {
	@Autowired
	private DeployService deployService;
	
	
	@RequestMapping("/getAllDeploy")
	@ResponseBody
    public Page<Deployment> getAllDeploy(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name")==null?"":request.getParameter("name");
        Page<Deployment> page = new Page<Deployment>();
        List<Deployment> list = deployService.getAllDeploy(name);
        page.setRows(list);
        page.setTotal(list.size());
        return page;
    }
	
	@RequestMapping("/getXmlByte")
	public void getXmlByte(HttpServletRequest request,HttpServletResponse response){
		OutputStream os = null;
		String deployId= request.getParameter("id");
		String name= request.getParameter("name");

		try {
			os = response.getOutputStream();

			InputStream xmlByte = deployService.getXmlByte(deployId);
			byte[] bytes = FileUtils.inputStream2Byte(xmlByte);

			response.setContentType("application/octet-stream; charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String((name+".bpmn20.xml").getBytes("UTF-8"), "iso-8859-1"));
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
	
	@RequestMapping("/getXmlString")
	@ResponseBody
	public Map<String, Object> getXmlString(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		String deployId= request.getParameter("id");
		try {
			InputStream xmlByte = deployService.getXmlByte(deployId);
			String str = FileUtils.inputStream2String(xmlByte);
			map.put("data", str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
		
	@RequestMapping("/getImageByte")
	public void getImageByte(HttpServletRequest request,HttpServletResponse response){
		OutputStream os = null;
		String deployId= request.getParameter("id");

		try {
			os = response.getOutputStream();

			InputStream imgByte = deployService.getImageByte(deployId);
			byte[] bytes = FileUtils.inputStream2Byte(imgByte);

			response.setContentType("application/octet-stream; charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(("image.bpmn20.jpg").getBytes("UTF-8"), "iso-8859-1"));
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
	
	@RequestMapping("/setDeployGategory")
	@ResponseBody
    public Map<String,Object> setDeployGategory(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String deployId = request.getParameter("deployId")==null?"":request.getParameter("deployId");
		String categoryId = request.getParameter("categoryId")==null?"":request.getParameter("categoryId");
       try {
    	   deployService.setDeployGategory(deployId, categoryId);
    	   map.put("code", 1);
    	   map.put("msg", "设置成功");
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
        return map;
    }
	 
	
}
