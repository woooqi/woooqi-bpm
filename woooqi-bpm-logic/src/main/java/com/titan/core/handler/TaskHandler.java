package com.titan.core.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.core.handler.button.BaseButton;
import com.titan.entity.bpm.form.CustomForm;
import com.titan.entity.bpm.manage.ProcessNode;
import com.titan.entity.bpm.manage.ProcessNodeForm;
import com.titan.entity.bpm.manage.ProcessNodeFormButton;
import com.titan.service.bpm.ProcessNodeFormService;
import com.titan.service.bpm.ProcessNodeService;
import com.titan.service.bpm.form.CustomFormService;
import com.titan.utils.BeanUtils;
import com.titan.utils.JsonUtils;

@RestController
@RequestMapping(value="handler")
public class TaskHandler {
	
	@Autowired
	private ProcessNodeFormService processNodeFormService;
	
	@Autowired
	private ProcessNodeService processNodeService;
	
	@Autowired
	private CustomFormService customFormService;
	
	
	@RequestMapping(value="dohandler")
	@ResponseBody
	public Map<String, Object> doHandler(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<>();
		//1 判断节点是否存在表单
		
		//2 跳转到表单公共页面（节点信息和表单类型）
		String activitiId = request.getParameter("activitiId")==null?"":request.getParameter("activitiId");
		ProcessNodeForm processNodeForm  = processNodeFormService.getProcessNodeFormByActivitiId(activitiId);
		if(processNodeForm!=null){
			if("1".equals(processNodeForm.getType())){//自定义表单
				String urlName = processNodeForm.getUrl();
				CustomForm customForm= customFormService.getCustomFormHtmlName(urlName);
				if(customForm!=null){
					map.put("custom", customForm);
				}
			}else{
				ProcessNode processNode = processNodeService.getNodeByActivitiId(activitiId);
				if(processNode!=null){
					map.put("node", processNode.getProcessVariable());
					
				}
				map.put("web", processNodeForm);
			}
			Set<ProcessNodeFormButton> buttons = processNodeForm.getButtons();
			map.put("buttons", buttons);
			
			
			
		}
		
		return map;
	}
	
	@RequestMapping(value="buttonhandler")
	@ResponseBody
	public Map<String, Object> buttonhandler(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String activitiId = request.getParameter("actId")==null?"":request.getParameter("actId");
		String instanceId = request.getParameter("instanceId")==null?"":request.getParameter("instanceId");
		String taskId = request.getParameter("taskId")==null?"":request.getParameter("taskId");
		String button = request.getParameter("button")==null?"":request.getParameter("button");
		String befores = request.getParameter("before")==null?"":request.getParameter("before");
		Map<String, Object> before = null;
		if(StringUtils.isNotEmpty(befores)){
			before = JsonUtils.jsonToMap(befores);
		}
		ProcessNodeForm processNodeForm  = processNodeFormService.getProcessNodeFormByActivitiId(activitiId);
		
		//1 获取流程变量
		//2 执行相应的操作
		BaseButton baseButton = this.createButton(button);
		baseButton.handler(taskId,before);
		//3 记录操作
		ret.put("code", 1);
		return ret;
	}
	
	public BaseButton createButton(String buttonCode){
		BaseButton button = null;
		Map<String,BaseButton> baseButtons = BeanUtils.getInstance().getBeanOfType(BaseButton.class);
		for(String key:baseButtons.keySet()){
			if(baseButtons.get(key).support(buttonCode)){
				button = baseButtons.get(key);
				break;
			}
		}
		return button;
	}
	
}
