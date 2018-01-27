package com.woooqi.bpm.service.bpm;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.titan.entity.bpm.bpm.Deployment;


public interface DeployService {
	
	public List<Deployment> getAllDeploy(String name);
	
	public InputStream getImageByte(String deployId) throws IOException;
	
	public InputStream getXmlByte(String deployId) throws IOException;
	
	public int setDeployGategory(String deployId,String categoryId);
	
	
}
