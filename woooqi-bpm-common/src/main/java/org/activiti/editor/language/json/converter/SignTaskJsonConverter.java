package org.activiti.editor.language.json.converter;

import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SignTask;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SignTaskJsonConverter extends BaseBpmnJsonConverter {

	private final static String STENCIL_TASK_SIGN = "SignTask";
	
    public static void fillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap,
            Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
    
        fillJsonTypes(convertersToBpmnMap);
        fillBpmnTypes(convertersToJsonMap);
    }
  
    public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
        convertersToBpmnMap.put(STENCIL_TASK_SIGN, SignTaskJsonConverter.class);
    }
  
    public static void fillBpmnTypes(Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        convertersToJsonMap.put(SignTask.class, SignTaskJsonConverter.class);
    }
  
    @Override
    protected String getStencilId(BaseElement baseElement) {
        return STENCIL_TASK_SIGN;
    }
  
    @Override
    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        SignTask signTask = (SignTask) baseElement;
        String assignee = signTask.getAssignee();
        String owner = signTask.getOwner();
    
        if (StringUtils.isNotEmpty(assignee) || StringUtils.isNotEmpty(owner) || CollectionUtils.isNotEmpty(signTask.getCandidateUsers()) || 
                CollectionUtils.isNotEmpty(signTask.getCandidateGroups())) {
          
            ObjectNode assignmentNode = objectMapper.createObjectNode();
            ObjectNode assignmentValuesNode = objectMapper.createObjectNode();
          
            if (StringUtils.isNotEmpty(assignee)) {
              assignmentValuesNode.put(PROPERTY_USERTASK_ASSIGNEE, assignee);
            }
            
            if (StringUtils.isNotEmpty(owner)) {
              assignmentValuesNode.put(PROPERTY_USERTASK_OWNER, owner);
            }
          
            if (CollectionUtils.isNotEmpty(signTask.getCandidateUsers())) {
                ArrayNode candidateArrayNode = objectMapper.createArrayNode();
                for (String candidateUser : signTask.getCandidateUsers()) {
                    ObjectNode candidateNode = objectMapper.createObjectNode();
                    candidateNode.put("value", candidateUser);
                    candidateArrayNode.add(candidateNode);
                }
                assignmentValuesNode.put(PROPERTY_USERTASK_CANDIDATE_USERS, candidateArrayNode);
            }
          
            if (CollectionUtils.isNotEmpty(signTask.getCandidateGroups())) {
                ArrayNode candidateArrayNode = objectMapper.createArrayNode();
                for (String candidateGroup : signTask.getCandidateGroups()) {
                    ObjectNode candidateNode = objectMapper.createObjectNode();
                    candidateNode.put("value", candidateGroup);
                    candidateArrayNode.add(candidateNode);
                }
                assignmentValuesNode.put(PROPERTY_USERTASK_CANDIDATE_GROUPS, candidateArrayNode);
            }
          
            assignmentNode.put("assignment", assignmentValuesNode);
            propertiesNode.put(PROPERTY_USERTASK_ASSIGNMENT, assignmentNode);
        }
        
        if (signTask.getPriority() != null) {
            setPropertyValue(PROPERTY_USERTASK_PRIORITY, signTask.getPriority().toString(), propertiesNode);
        }
        
        if (StringUtils.isNotEmpty(signTask.getFormKey())) {
            setPropertyValue(PROPERTY_FORMKEY, signTask.getFormKey(), propertiesNode);
        }
        
        setPropertyValue(PROPERTY_USERTASK_DUEDATE, signTask.getDueDate(), propertiesNode);
        setPropertyValue(PROPERTY_USERTASK_CATEGORY, signTask.getCategory(), propertiesNode);
        
        addFormProperties(signTask.getFormProperties(), propertiesNode);
    }
  
    @Override
    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
        SignTask task = new SignTask();
        task.setPriority(getPropertyValueAsString(PROPERTY_USERTASK_PRIORITY, elementNode));
        String formKey = getPropertyValueAsString(PROPERTY_FORMKEY, elementNode);
        if (StringUtils.isNotEmpty(formKey)) {
            task.setFormKey(formKey);
        }
        task.setDueDate(getPropertyValueAsString(PROPERTY_USERTASK_DUEDATE, elementNode));
        task.setCategory(getPropertyValueAsString(PROPERTY_USERTASK_CATEGORY, elementNode));
        
        JsonNode assignmentNode = getProperty(PROPERTY_USERTASK_ASSIGNMENT, elementNode);
        if (assignmentNode != null) {
            JsonNode assignmentDefNode = assignmentNode.get("assignment");
            if (assignmentDefNode != null) {
              
                JsonNode assigneeNode = assignmentDefNode.get(PROPERTY_USERTASK_ASSIGNEE);
                if (assigneeNode != null && assigneeNode.isNull() == false) {
                    task.setAssignee(assigneeNode.asText());
                }
                
                JsonNode ownerNode = assignmentDefNode.get(PROPERTY_USERTASK_OWNER);
                if (ownerNode != null && ownerNode.isNull() == false) {
                    task.setOwner(ownerNode.asText());
                }
                
                task.setCandidateUsers(getValueAsList(PROPERTY_USERTASK_CANDIDATE_USERS, assignmentDefNode));
                task.setCandidateGroups(getValueAsList(PROPERTY_USERTASK_CANDIDATE_GROUPS, assignmentDefNode));
            }
        }
        convertJsonToFormProperties(elementNode, task);
        return task;
    }
}
