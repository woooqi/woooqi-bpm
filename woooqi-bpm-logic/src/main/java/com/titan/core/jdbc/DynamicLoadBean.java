package com.titan.core.jdbc;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class DynamicLoadBean implements ApplicationContextAware{

	private XmlWebApplicationContext applicationContext = null;
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = (XmlWebApplicationContext)applicationContext;
	}
	public XmlWebApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void loadBean(String configLocationString) throws Exception{
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)getApplicationContext().getBeanFactory());
		beanDefinitionReader.setResourceLoader(getApplicationContext());
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(getApplicationContext()));
		String[] configLocations = new String[]{configLocationString};
		for(int i=0;i<configLocations.length;i++){
			beanDefinitionReader.loadBeanDefinitions(getApplicationContext().getResources(configLocations[i]));
		}

	}
}
