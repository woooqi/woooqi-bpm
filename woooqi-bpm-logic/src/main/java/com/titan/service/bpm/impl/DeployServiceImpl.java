package com.titan.service.bpm.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.bpm.Deployment;
import com.titan.service.bpm.DeployService;
import com.titan.utils.DataBaseUtils;

@Service
public class DeployServiceImpl implements DeployService{

	@Autowired
	private RepositoryService repositoryService;
	
	@Override
	public List<Deployment> getAllDeploy(String name) {
		
		String sql = "select c.id_,c.name_,c.tenant_id_,c.deploy_time_,c.version_,d.name as category_ from (select b.*,a.version_ from act_re_procdef a,act_re_deployment b where a.deployment_id_(+) = b.id_)c,bpm_category d where c.category_ =d.id(+)";
		if(!"".equals(name)){
			sql += " and c.name_ like '%"+name+"%'";
		}
		sql+=" order by c.deploy_time_" ;
		List<Deployment> deployments = DataBaseUtils.queryForListBean(sql, Deployment.class);
		return deployments;
	}
	
	@Override
	public InputStream getImageByte(String deployId) throws IOException {
        
    	String imageName = "";
    	List<String> names = repositoryService.getDeploymentResourceNames(deployId);
    	for(String name:names){
    		if(name.endsWith(".png")){
    			imageName = name;
    		}
    	}
    	if(!StringUtils.isBlank(imageName)){
    		InputStream imageStream = repositoryService.getResourceAsStream(deployId, imageName);			    
    		return imageStream;
    	}
    	return null;
    }
	
	@Override
	public InputStream getXmlByte(String deployId) throws IOException {
        
    	String xmlName = "";
    	List<String> names = repositoryService.getDeploymentResourceNames(deployId);
    	for(String name:names){
    		if(name.endsWith(".xml")){
    			xmlName = name;
    		}
    	}
    	if(!StringUtils.isBlank(xmlName)){
    		InputStream xmlStream = repositoryService.getResourceAsStream(deployId, xmlName);			    
    		return xmlStream;
    	}
    	return null;
    }

	@Override
	public int setDeployGategory(String deployId, String categoryId) {
		String sql = "update act_re_deployment set category_=? where id_=?";
		return  DataBaseUtils.update(sql,categoryId,deployId);
	}

}
