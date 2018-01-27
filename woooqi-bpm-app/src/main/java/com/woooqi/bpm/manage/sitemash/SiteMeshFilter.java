package com.woooqi.bpm.manage.sitemash;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;


public class SiteMeshFilter extends ConfigurableSiteMeshFilter{

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		
		builder.addDecoratorPath("/main_index", "/WEB-INF/sitemesh/sitemesh_main_index.jsp")
			   .addDecoratorPath("/main_business", "/WEB-INF/sitemesh/sitemesh_main_business.jsp")
			   
			   .addDecoratorPath("/edit", "/WEB-INF/sitemesh/sitemesh_edit.jsp");
	}
	
}
