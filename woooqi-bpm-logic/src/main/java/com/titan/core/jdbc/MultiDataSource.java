package com.titan.core.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jndi.JndiObjectFactoryBean;

import com.alibaba.druid.pool.DruidDataSource;
import com.titan.core.jdbc.entity.DataSourceEntity;

public class MultiDataSource implements DataSource {

	private static final Map<String, Object> dataSources = Collections.synchronizedMap(new HashMap<String, Object>());
	
	@Override
	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getDataSource().getConnection(username, password);
	}

	public DataSourceEntity getDataBaseEntity(String appId) {
		return LoadDataSource.getDataConfig(appId);
	}
	
	public DataSource getDataSource() {
		String sourceId = DataSourceRoute.getSourceId();
		return getDataSource(sourceId);
	}

	public DataSource getDataSource(String sourceId) {
		
		if (dataSources.get(sourceId) != null) {
			return (DataSource) dataSources.get(sourceId);
		} else {
			return createDataSource(sourceId);
		}
	}
	
	public void removeDataSource(String id){
		synchronized(dataSources){
			if(dataSources.containsKey(id)){
				LoadDataSource.removeDbmap(id);
				DruidDataSource ds = (com.alibaba.druid.pool.DruidDataSource) dataSources.remove(id);
				ds.close();
			}
		}
	}

	public DataSource createDataSource(String sourceId) {
		DataSourceEntity dbentiy = LoadDataSource.getDataConfig(sourceId);
		if (dbentiy == null) {
			DataSourceRoute.putSourceId(null);
			sourceId = DataSourceRoute.DATASOURCE_DEFAULT;
			dbentiy = LoadDataSource.getDataConfig(sourceId);
		}

		if (dbentiy != null) {
			synchronized (this) {
				if (StringUtils.isBlank(dbentiy.getJndiName())) {
					DruidDataSource source = new DruidDataSource();

					source.setUsername(dbentiy.getUsername());
					source.setPassword(dbentiy.getPassword());
					source.setDriverClassName(dbentiy.getClassName());
					source.setUrl(dbentiy.getUrl());
					source.setMaxActive(100);
					source.setInitialSize(10);
					source.setMinIdle(10);
					source.setTimeBetweenEvictionRunsMillis(60000);
					source.setMinEvictableIdleTimeMillis(600000);
	
				    if(dbentiy.getClassName().indexOf("oracle.jdbc.driver.OracleDriver")> -1){
				    	source.setTestWhileIdle(false);  
						source.setTestOnBorrow(false);
						source.setValidationQuery("select 1 from dual ");
						source.setPoolPreparedStatements(true);
						source.setMaxPoolPreparedStatementPerConnectionSize(20);
				    }else if(dbentiy.getClassName().indexOf("com.mysql.jdbc.Driver") > -1){
				    	source.setTestWhileIdle(true);  
						source.setTestOnBorrow(true);
						source.setValidationQuery("select 1 ");
						source.setPoolPreparedStatements(false);
						source.setMaxPoolPreparedStatementPerConnectionSize(20);
						
				    }else{
				    	source.setTestWhileIdle(false);  
						source.setTestOnBorrow(false);
						source.setValidationQuery("select 1 ");
						source.setPoolPreparedStatements(true);
						source.setMaxPoolPreparedStatementPerConnectionSize(20);
				    }
					try {
						source.setFilters("stat");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					dataSources.put(sourceId, source);
					return source;
				}else{
					JndiObjectFactoryBean jb = new JndiObjectFactoryBean();
					jb.setJndiName(dbentiy.getJndiName());
					try {
						jb.afterPropertiesSet();
					} catch (Exception e) {
						e.printStackTrace();
					} 					
					dataSources.put(sourceId, jb.getObject());
					return (DataSource) jb.getObject();
				}
			}
		}
		return null;
	}
	@Override
	public int getLoginTimeout() throws SQLException {
		return getDataSource().getLoginTimeout();
	}
	@Override
	public void setLogWriter(PrintWriter printWriter) throws SQLException {
		getDataSource().setLogWriter(printWriter);
	}
	@Override
	public void setLoginTimeout(int timeOut) throws SQLException {
		getDataSource().setLoginTimeout(timeOut);
	}
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return getDataSource().getLogWriter();
	}
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}
	
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

}

