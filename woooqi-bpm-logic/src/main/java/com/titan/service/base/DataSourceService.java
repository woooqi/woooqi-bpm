package com.titan.service.base;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface DataSourceService {
	
	public List<Map<String, String>> getDataSource(String id);
	
	public void updateDataSource(String oldId,String id,String classname, String url, String username,String password,String jndi);
	
	public void dataSourceBean(String classname, String url, String username,String password)  throws Exception;
	
	public void delDataSourceById(String id);
	
	public Connection getConnection(String classname, String url, String username,String password);
}
