package com.woooqi.bpm.core.handler.button;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SaveDraftButton implements BaseButton{

	@Override
	public boolean support(String buttonCode) {
		if(buttonCode.toLowerCase().equals("savedraft")){
			return true;
		}
		return false;
	}
	
	@Override
	public void handler(String taskId,Map<String, Object> before) {
		
	}

}
