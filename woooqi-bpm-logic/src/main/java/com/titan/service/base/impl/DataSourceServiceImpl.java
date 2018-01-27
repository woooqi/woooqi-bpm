package com.titan.service.base.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.core.jdbc.DynamicLoadBean;
import com.titan.core.jdbc.LoadDataSource;
import com.titan.service.base.DataSourceService;
import com.titan.utils.FileUtils;

@Service
public class DataSourceServiceImpl implements DataSourceService{
	
	public String beans_contants = "<bean id='dataSource' class='com.alibaba.druid.pool.DruidDataSource' init-method='init' destroy-method='close' lazy-init='true'>"
								+"<property name='driverClassName' value='${jdbc.driver}' />"
								+"<property name='url' value='${jdbc.url}' />"
								+"<property name='username' value='${jdbc.username}' />"
								+"<property name='password' value='${jdbc.password}' />"
								+"<property name='maxActive' value='100' />"
								+"<property name='initialSize' value='10' />"
								+"<property name='maxWait' value='10' />"
								+"<property name='minIdle' value='10' />"
								+"<property name='timeBetweenEvictionRunsMillis' value='60000' />"
								+"<property name='minEvictableIdleTimeMillis' value='300000' />"
								+"<property name='validationQuery' value='select 1 from dual' />"
								+"<property name='testWhileIdle' value='true' />"
								+"<property name='testOnBorrow' value='false' />"
								+"<property name='testOnReturn' value='false' />"
								+"<property name='poolPreparedStatements' value='true' />"
								+"<property name='maxPoolPreparedStatementPerConnectionSize' value='20' />"
								+"<property name='filters' value='stat' /></bean>";
	
	@Autowired
	private DynamicLoadBean dynamicLoadBean;

	@Override
	public List<Map<String, String>> getDataSource(String id) {
		String path = LoadDataSource.class.getClassLoader().getResource("datasource.xml").getFile().toString();
		File file = new File(path.replace("%20", " "));
		Document doc = FileUtils.readFileAsDocument(file);
		Element element = doc.getRootElement();
		List<Element> elements = element.elements("datasource");
		List<Map<String, String>> ds = new ArrayList<Map<String, String>>();
		if(id == null|| id.equals("")){
			for(Element el : elements) {
				String dataSourceId = el.attributeValue("name");
				String classname = el.elementText("classname");
				String url = el.elementText("url");
			 	String username = el.elementText("username");
				String password = el.elementText("password");
				String jndi = el.elementText("jndi");
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", dataSourceId);
				map.put("url", url);
				map.put("classname", classname);
			 	map.put("username", username);
				map.put("password", password);
				map.put("jndi", jndi);
				ds.add(map);
			}
		}else{
			for(Element el : elements) {
				if(id.equals(el.attributeValue("name"))){
					String dataSourceId = el.attributeValue("name");
					String classname = el.elementText("classname");
					String url = el.elementText("url");
				 	String username = el.elementText("username");
					String password = el.elementText("password");
					String jndi = el.elementText("jndi");
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", dataSourceId);
					map.put("classname", classname);
					map.put("url", url);
				 	map.put("username", username);
					map.put("password", password);
					map.put("jndi", jndi);
					ds.add(map);
				}
			}
		}	
		return ds;
	}
	
	@Override
	public void updateDataSource(String oldId,String id,String classname, String url, String username,String password,String jndi) {
		String path = LoadDataSource.class.getClassLoader().getResource("datasource.xml").getFile().toString();
		File file = new File(path.replace("%20", " "));
		Document doc = FileUtils.readFileAsDocument(file);
		Element element = doc.getRootElement();
		List<Element> elements = element.elements("datasource");
		try {
			if(oldId == null || oldId.equals("")){
				Element resource = element.addElement("datasource");
				Element resourceClassname = resource.addElement("classname");
				Element resourceUrl = resource.addElement("url");
				Element resourceUsername = resource.addElement("username");
				Element resourcePassword = resource.addElement("password");
				Element resourcJndi = resource.addElement("jndi");
				resource.addAttribute("name",id);
				resourceClassname.setText(classname);
				resourceUrl.setText(url);
				resourceUsername.setText(username);
				resourcePassword.setText(password);
				resourcJndi.setText(jndi);
				
		}else{
			for(Element el : elements) {
				if (oldId.equals(el.attributeValue("name"))) {
					Element resourceClassname = el.element("classname");
					Element resourceUrl = el.element("url");
					Element resourceUsername = el.element("username");
					Element resourcePassword = el.element("password");
					Element resourcJndi = el.element("jndi");
					el.addAttribute("name", id);
					resourceClassname.setText(classname);
					resourceUrl.setText(url);
					resourceUsername.setText(username);
					resourcePassword.setText(password);
					resourcJndi.setText(jndi);
				}
			}
			
		}
			OutputFormat opf = new OutputFormat("\t",true,"UTF-8");
			opf.setTrimText(true);
			XMLWriter writer=new XMLWriter(new FileOutputStream(path),opf);
			writer.write(doc);
			writer.close();
			
		} catch (IOException e) {
			   e.printStackTrace();
			  }
		
		
	}

	@Override
	public void delDataSourceById(String id) {
		String path = LoadDataSource.class.getClassLoader().getResource("datasource.xml").getFile().toString();
		File file = new File(path.replace("%20", " "));
		Document doc = FileUtils.readFileAsDocument(file);
		Element element = doc.getRootElement();
		List<Element> elements = element.elements("datasource");
		if(id !=null ){
			for(Element el : elements) {
				if (id.equals(el.attributeValue("name"))) {
					el.detach();
				}
			}
		}
		try {
			OutputFormat opf = new OutputFormat("\t",true,"UTF-8");
			opf.setTrimText(true);
			XMLWriter writer=new XMLWriter(new FileOutputStream(path),opf);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
		}
	
	}

	@Override
	public Connection getConnection(String classname, String url, String username, String password) {
		 
		Connection connection = null;
        try {
            Class.forName(classname);
            connection = DriverManager.getConnection(url, username, password);
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
	}

	@Override
	public void dataSourceBean(String classname, String url, String username, String password) throws Exception{
		beans_contants = beans_contants.replace("${jdbc.driver}", classname);
		beans_contants = beans_contants.replace("${jdbc.url}", url);
		beans_contants = beans_contants.replace("${jdbc.username}", username);
		beans_contants = beans_contants.replace("${jdbc.password}", password);
		
		dynamicLoadBean.loadBean(beans_contants);
	}

		
	

}
