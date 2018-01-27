package com.woooqi.bpm.core.jdbc;

import org.apache.commons.lang3.StringUtils;

public class DataSourceRoute {
	
	public final static String DATASOURCE_DEFAULT = "default";
	
	private static ThreadLocal<String> local = new ThreadLocal<String>();

	public static void putSourceId(String sourceId) {
		local.set(sourceId);
	}

	public static String getSourceId() {
		if(StringUtils.isBlank(local.get())){
			return DATASOURCE_DEFAULT;
		}else{
			return local.get();
		}
	}
}
