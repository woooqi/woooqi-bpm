package com.titan.core.handler.button;

import java.util.Map;

public interface BaseButton {
	
	public abstract boolean support(String buttonCode);
	
	public void handler(String taskId,Map<String, Object> before);
}
