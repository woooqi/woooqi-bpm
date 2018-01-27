package com.woooqi.bpm.core.jdbc;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woooqi.bpm.core.jdbc.entity.DataSourceEntity;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.titan.core.jdbc.entity.DataSourceEntity;
import com.titan.utils.FileUtils;

public class LoadDataSource {
	
	private final static Map<String, DataSourceEntity> dataSourcebMap = new HashMap<String, DataSourceEntity>();
	
	public static DataSourceEntity getDataConfig(String appId){
		if(dataSourcebMap.get(appId) == null){
			load();
		}
		return dataSourcebMap.get(appId);
	}
	
	
	public static void removeDbmap(String id){
		dataSourcebMap.remove(id);
	}

	@SuppressWarnings("unchecked")
	public static void load(){
		File file = null;
		try {
			String path = LoadDataSource.class.getClassLoader().getResource("datasource.xml").getFile().toString();
			file = new File(path.replace("%20", " "));
			Document doc = FileUtils.readFileAsDocument(file);
			Element rootElement = doc.getRootElement();
			if(rootElement != null){
				List<Element> list = rootElement.elements("datasource");
				for(int i=0;i<list.size();i++){
					DataSourceEntity dataBaseEntity = new DataSourceEntity();
					Element datasources = list.get(i);
					if(datasources != null){
						String datasname = datasources.attributeValue("name");
						dataBaseEntity.setSourceId(datasname);
						dataBaseEntity.setClassName(StringUtils.isBlank(datasources.elementText("classname"))?"":datasources.elementText("classname"));
						dataBaseEntity.setUrl(StringUtils.isBlank(datasources.elementText("url"))?"":datasources.elementText("url"));
						dataBaseEntity.setUsername(StringUtils.isBlank(datasources.elementText("username"))?"":datasources.elementText("username"));
						dataBaseEntity.setPassword(StringUtils.isBlank(datasources.elementText("password"))?"":datasources.elementText("password"));
						dataBaseEntity.setJndiName(StringUtils.isBlank(datasources.elementText("jndi"))?"":datasources.elementText("jndi"));
						dataSourcebMap.put(datasname,dataBaseEntity);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
