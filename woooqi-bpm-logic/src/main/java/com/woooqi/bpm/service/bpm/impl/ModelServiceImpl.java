package com.woooqi.bpm.service.bpm.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.titan.entity.bpm.bpm.Category;
import com.titan.entity.bpm.bpm.ProcessDefinition;
import com.titan.service.bpm.ModelService;
import com.titan.utils.DataBaseUtils;
import com.titan.utils.PageUtils;
import com.titan.utils.WorkflowUtils;

@Service
public class ModelServiceImpl implements ModelService{
	
	@Autowired
	private RepositoryService repositoryService;

	@SuppressWarnings("deprecation")
	public String createModel(String key,String name,String description) {
		Model modelData = repositoryService.newModel();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            
            ObjectNode propertiesNode = objectMapper.createObjectNode();
            propertiesNode.put("process_id", key);
            propertiesNode.put("name", name);
            propertiesNode.put("version", 1);
            if (StringUtils.isNotEmpty(description)) {
                propertiesNode.put("documentation", description);
            }
            editorNode.set("properties", propertiesNode);
            
            ArrayNode childShapeArray = objectMapper.createArrayNode();
            editorNode.set("childShapes", childShapeArray);
            ObjectNode childNode = objectMapper.createObjectNode();
            childShapeArray.add(childNode);
            ObjectNode boundsNode = objectMapper.createObjectNode();
            childNode.set("bounds", boundsNode);
            ObjectNode lowerRightNode = objectMapper.createObjectNode();
            boundsNode.set("lowerRight", lowerRightNode);
            lowerRightNode.put("x", 130);
            lowerRightNode.put("y", 130);
            ObjectNode upperLeftNode = objectMapper.createObjectNode();
            boundsNode.set("upperLeft", upperLeftNode);
            upperLeftNode.put("x", 100);
            upperLeftNode.put("y", 100);
            childNode.set("childShapes", objectMapper.createArrayNode());
            childNode.set("dockers", objectMapper.createArrayNode());
            childNode.set("outgoing", objectMapper.createArrayNode());
            childNode.put("resourceId", "sid-"+PageUtils.getUUID());
            ObjectNode stencilNode = objectMapper.createObjectNode();
            childNode.set("stencil", stencilNode);
            stencilNode.put("id", "StartNoneEvent");
            
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(key);
            modelData.setVersion(1);
           
            repositoryService.saveModel(modelData);
            
			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return modelData.getId();
	}

	@Override
	public List<Model> getAllModel(String name,String key) {
		List<Model> models = repositoryService.createModelQuery().orderByCreateTime().asc().list();
		if(!"".equals(name)&&!"".equals(key)){
			models = repositoryService.createModelQuery().modelName(name).modelKey(key).orderByCreateTime().asc().list();
		}
		if("".equals(name)&&!"".equals(key)){
			models = repositoryService.createModelQuery().modelKey(key).orderByCreateTime().asc().list();
		}
		if(!"".equals(name)&&"".equals(key)){
			models = repositoryService.createModelQuery().modelName(name).orderByCreateTime().asc().list();
		}
		
		return models;
	}

	@Override
	public Model getModelByDeployId(String deploymentId) {
		Model model = repositoryService.createModelQuery().deploymentId(deploymentId).singleResult();
		return model;
	}

	@Override
	public void delModel(String modelId) {
		repositoryService.deleteModel(modelId);
	}
	
	@Override
	public String deploymentOnWeb(String modelId){
		String deployId = "";
		try {
			Model modelData = repositoryService.getModel(modelId);
  
			ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));

			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			byte[] bytes = new BpmnXMLConverter().convertToXML(model);

			String processName = modelData.getName() + ".bpmn20.xml";
			String xml = WorkflowUtils.changeXML(new String(bytes,"utf-8"));
 
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, xml).deploy();		      
			deployId = deployment.getId();
			
			DataBaseUtils.update("update act_re_model set deployment_id_ = ? where id_ = ?", deployment.getId() ,modelId);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return deployId;
	}

}
