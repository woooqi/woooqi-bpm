package com.woooqi.bpm.manage.freemaker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.woooqi.bpm.utils.PageUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.titan.utils.PageUtils;

public class FreeMarker extends FreeMarkerView {
	@Override
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
		model.put("ctx", request.getContextPath());
		model.put("user", PageUtils.getCurrentUser());
	}
}