package org.activiti.bpmn.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.converter.child.BaseChildElementParser;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.converter.util.CommaSplitter;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.SignTask;
import org.activiti.bpmn.model.alfresco.AlfrescoSignTask;
import org.apache.commons.lang3.StringUtils;

public class SignTaskXMLConverter extends BaseBpmnXMLConverter {
	
	private static final String ELEMENT_TASK_SIGN = "signTask";

  protected Map<String, BaseChildElementParser> childParserMap = new HashMap<String, BaseChildElementParser>();

  /** default attributes taken from bpmn spec and from activiti extension */
  protected static final List<ExtensionAttribute> defaultUserTaskAttributes = Arrays.asList(
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_FORM_FORMKEY), 
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_DUEDATE), 
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_BUSINESS_CALENDAR_NAME),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_ASSIGNEE), 
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_OWNER), 
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_PRIORITY), 
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEUSERS), 
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEGROUPS),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CATEGORY),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_SERVICE_EXTENSIONID),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_SKIP_EXPRESSION)
  );

  public SignTaskXMLConverter() {
    HumanPerformerParser humanPerformerParser = new HumanPerformerParser();
    childParserMap.put(humanPerformerParser.getElementName(), humanPerformerParser);
    PotentialOwnerParser potentialOwnerParser = new PotentialOwnerParser();
    childParserMap.put(potentialOwnerParser.getElementName(), potentialOwnerParser);
    CustomIdentityLinkParser customIdentityLinkParser = new CustomIdentityLinkParser();
    childParserMap.put(customIdentityLinkParser.getElementName(), customIdentityLinkParser);
  }
  
  public Class<? extends BaseElement> getBpmnElementType() {
    return SignTask.class;
  }
  
  @Override
  protected String getXMLElementName() {
    return ELEMENT_TASK_SIGN;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  protected BaseElement convertXMLToElement(XMLStreamReader xtr, BpmnModel model) throws Exception {
    String formKey = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_FORM_FORMKEY);
    SignTask signTask = null;
    if (StringUtils.isNotEmpty(formKey)) {
      if (model.getUserTaskFormTypes() != null && model.getUserTaskFormTypes().contains(formKey)) {
    	  signTask = new AlfrescoSignTask();
      }
    }
    if (signTask == null) {
    	signTask = new SignTask();
    }
    BpmnXMLUtil.addXMLLocation(signTask, xtr);
    signTask.setDueDate(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_DUEDATE));
    signTask.setBusinessCalendarName(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_BUSINESS_CALENDAR_NAME));
    signTask.setCategory(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CATEGORY));
    signTask.setFormKey(formKey);
    signTask.setAssignee(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_ASSIGNEE)); 
    signTask.setOwner(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_OWNER));
    signTask.setPriority(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_PRIORITY));
    
    if (StringUtils.isNotEmpty(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEUSERS))) {
      String expression = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEUSERS);
      signTask.getCandidateUsers().addAll(parseDelimitedList(expression));
    } 
    
    if (StringUtils.isNotEmpty(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEGROUPS))) {
      String expression = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEGROUPS);
      signTask.getCandidateGroups().addAll(parseDelimitedList(expression));
    }
    
    signTask.setExtensionId(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_SERVICE_EXTENSIONID));

    if (StringUtils.isNotEmpty(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_SKIP_EXPRESSION))) {
      String expression = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_SKIP_EXPRESSION);
      signTask.setSkipExpression(expression);
    }

    BpmnXMLUtil.addCustomAttributes(xtr, signTask, defaultElementAttributes, 
        defaultActivityAttributes, defaultUserTaskAttributes);

    parseChildElements(getXMLElementName(), signTask, childParserMap, model, xtr);
    
    return signTask;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  protected void writeAdditionalAttributes(BaseElement element, BpmnModel model, XMLStreamWriter xtw) throws Exception {
    SignTask signTask = (SignTask) element;
    writeQualifiedAttribute(ATTRIBUTE_TASK_USER_ASSIGNEE, signTask.getAssignee(), xtw);
    writeQualifiedAttribute(ATTRIBUTE_TASK_USER_OWNER, signTask.getOwner(), xtw);
    writeQualifiedAttribute(ATTRIBUTE_TASK_USER_CANDIDATEUSERS, convertToDelimitedString(signTask.getCandidateUsers()), xtw);
    writeQualifiedAttribute(ATTRIBUTE_TASK_USER_CANDIDATEGROUPS, convertToDelimitedString(signTask.getCandidateGroups()), xtw);
    writeQualifiedAttribute(ATTRIBUTE_TASK_USER_DUEDATE, signTask.getDueDate(), xtw);
    writeQualifiedAttribute(ATTRIBUTE_TASK_USER_BUSINESS_CALENDAR_NAME, signTask.getBusinessCalendarName(), xtw);
    writeQualifiedAttribute(ATTRIBUTE_TASK_USER_CATEGORY, signTask.getCategory(), xtw);
    writeQualifiedAttribute(ATTRIBUTE_FORM_FORMKEY, signTask.getFormKey(), xtw);
    if (signTask.getPriority() != null) {
      writeQualifiedAttribute(ATTRIBUTE_TASK_USER_PRIORITY, signTask.getPriority().toString(), xtw);
    }
    if (StringUtils.isNotEmpty(signTask.getExtensionId())) {
      writeQualifiedAttribute(ATTRIBUTE_TASK_SERVICE_EXTENSIONID, signTask.getExtensionId(), xtw);
    }
    if (signTask.getSkipExpression() != null) {
      writeQualifiedAttribute(ATTRIBUTE_TASK_USER_SKIP_EXPRESSION, signTask.getSkipExpression(), xtw);
    }
    // write custom attributes
    BpmnXMLUtil.writeCustomAttributes(signTask.getAttributes().values(), xtw, defaultElementAttributes, 
        defaultActivityAttributes, defaultUserTaskAttributes);
  }
  
  @Override
  protected boolean writeExtensionChildElements(BaseElement element, boolean didWriteExtensionStartElement, XMLStreamWriter xtw) throws Exception {
    SignTask signTask = (SignTask) element;
    didWriteExtensionStartElement = writeFormProperties(signTask, didWriteExtensionStartElement, xtw);
    didWriteExtensionStartElement = writeCustomIdentities(element, didWriteExtensionStartElement, xtw);
    if (!signTask.getCustomProperties().isEmpty()) {
      for (CustomProperty customProperty : signTask.getCustomProperties()) {
        
        if (StringUtils.isEmpty(customProperty.getSimpleValue())) {
          continue;
        }
        
        if (didWriteExtensionStartElement == false) {
          xtw.writeStartElement(ELEMENT_EXTENSIONS);
          didWriteExtensionStartElement = true;
        }
        xtw.writeStartElement(ACTIVITI_EXTENSIONS_PREFIX, customProperty.getName(), ACTIVITI_EXTENSIONS_NAMESPACE);
        xtw.writeCData(customProperty.getSimpleValue());
        xtw.writeEndElement();
      }
    }
    return didWriteExtensionStartElement;
  }
  
  protected boolean writeCustomIdentities(BaseElement element, boolean didWriteExtensionStartElement, XMLStreamWriter xtw) throws Exception {
	  SignTask signTask = (SignTask) element;
	  if (signTask.getCustomUserIdentityLinks().isEmpty() && signTask.getCustomGroupIdentityLinks().isEmpty()) {
		  return didWriteExtensionStartElement;
	  }
	    
  	if (didWriteExtensionStartElement == false) { 
      xtw.writeStartElement(ELEMENT_EXTENSIONS);
      didWriteExtensionStartElement = true;
    }
  	Set<String> identityLinkTypes = new HashSet<String>();
  	identityLinkTypes.addAll(signTask.getCustomUserIdentityLinks().keySet());
  	identityLinkTypes.addAll(signTask.getCustomGroupIdentityLinks().keySet());
  	for (String identityType : identityLinkTypes) {
  		writeCustomIdentities(signTask, identityType, signTask.getCustomUserIdentityLinks().get(identityType), signTask.getCustomGroupIdentityLinks().get(identityType), xtw);
  	}
    
    return didWriteExtensionStartElement;
  }

  protected void writeCustomIdentities(SignTask signTask,String identityType, Set<String> users, Set<String> groups, XMLStreamWriter xtw) throws Exception {
	  xtw.writeStartElement(ACTIVITI_EXTENSIONS_PREFIX, ELEMENT_CUSTOM_RESOURCE, ACTIVITI_EXTENSIONS_NAMESPACE);
	  writeDefaultAttribute(ATTRIBUTE_NAME, identityType, xtw);
      
    List<String> identityList = new ArrayList<String>();
    
    if (users!=null) {
      for (String userId: users) {
        identityList.add("user("+userId+")");
      }
    }
    
    if (groups!=null) {
      for (String groupId: groups){
    	  identityList.add("group("+groupId+")");
      }
    }
    
    String delimitedString = convertToDelimitedString(identityList);
    
    xtw.writeStartElement(ELEMENT_RESOURCE_ASSIGNMENT);
    xtw.writeStartElement(ELEMENT_FORMAL_EXPRESSION);
    xtw.writeCharacters(delimitedString);
    xtw.writeEndElement(); // End ELEMENT_FORMAL_EXPRESSION
    xtw.writeEndElement(); // End ELEMENT_RESOURCE_ASSIGNMENT
    
    xtw.writeEndElement(); // End ELEMENT_CUSTOM_RESOURCE
  }
  
  @Override
  protected void writeAdditionalChildElements(BaseElement element, BpmnModel model, XMLStreamWriter xtw) throws Exception {
  }
  
  public class HumanPerformerParser extends BaseChildElementParser {

    public String getElementName() {
      return "humanPerformer";
    }

    public void parseChildElement(XMLStreamReader xtr, BaseElement parentElement, BpmnModel model) throws Exception {
      String resourceElement = XMLStreamReaderUtil.moveDown(xtr);
      if (StringUtils.isNotEmpty(resourceElement) && ELEMENT_RESOURCE_ASSIGNMENT.equals(resourceElement)) {
        String expression = XMLStreamReaderUtil.moveDown(xtr);
        if (StringUtils.isNotEmpty(expression) && ELEMENT_FORMAL_EXPRESSION.equals(expression)) {
          ((SignTask) parentElement).setAssignee(xtr.getElementText());
        }
      }
    }
  }

  public class PotentialOwnerParser extends BaseChildElementParser {

    public String getElementName() {
      return "potentialOwner";
    }
    
    public void parseChildElement(XMLStreamReader xtr, BaseElement parentElement, BpmnModel model) throws Exception {
      String resourceElement = XMLStreamReaderUtil.moveDown(xtr);
      if (StringUtils.isNotEmpty(resourceElement) && ELEMENT_RESOURCE_ASSIGNMENT.equals(resourceElement)) {
        String expression = XMLStreamReaderUtil.moveDown(xtr);
        if (StringUtils.isNotEmpty(expression) && ELEMENT_FORMAL_EXPRESSION.equals(expression)) {
          
          List<String> assignmentList = CommaSplitter.splitCommas(xtr.getElementText());
          
          for (String assignmentValue : assignmentList) {
            if (assignmentValue == null) {
              continue;
            }
            
            assignmentValue = assignmentValue.trim();
            
            if (assignmentValue.length() == 0) {
              continue;
            }

            String userPrefix = "user(";
            String groupPrefix = "group(";
            if (assignmentValue.startsWith(userPrefix)) {
              assignmentValue = assignmentValue.substring(userPrefix.length(), assignmentValue.length() - 1).trim();
              ((SignTask) parentElement).getCandidateUsers().add(assignmentValue);
            } else if (assignmentValue.startsWith(groupPrefix)) {
              assignmentValue = assignmentValue.substring(groupPrefix.length(), assignmentValue.length() - 1).trim();
              ((SignTask) parentElement).getCandidateGroups().add(assignmentValue);
            } else {
              ((SignTask) parentElement).getCandidateGroups().add(assignmentValue);
            }
          }
        }
      } else if (StringUtils.isNotEmpty(resourceElement) && ELEMENT_RESOURCE_REF.equals(resourceElement)) {
        String resourceId = xtr.getElementText();
        if (model.containsResourceId(resourceId)) { 
          Resource resource = model.getResource(resourceId);
          ((SignTask) parentElement).getCandidateGroups().add(resource.getName());
        } else { 
          Resource resource = new Resource(resourceId, resourceId);
          model.addResource(resource);
          ((SignTask) parentElement).getCandidateGroups().add(resource.getName());
        }
      }
    }
  }
  
  public class CustomIdentityLinkParser extends BaseChildElementParser {

    public String getElementName() {
      return ELEMENT_CUSTOM_RESOURCE;
    }
	    
    public void parseChildElement(XMLStreamReader xtr, BaseElement parentElement, BpmnModel model) throws Exception {
	    String identityLinkType = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_NAME);
	    
	    // the attribute value may be unqualified
	    if (identityLinkType == null) {
	      identityLinkType = xtr.getAttributeValue(null, ATTRIBUTE_NAME);
	    }
	    
	    if (identityLinkType == null) return;
	    
	    String resourceElement = XMLStreamReaderUtil.moveDown(xtr);
	      if (StringUtils.isNotEmpty(resourceElement) && ELEMENT_RESOURCE_ASSIGNMENT.equals(resourceElement)) {
	        String expression = XMLStreamReaderUtil.moveDown(xtr);
	        if (StringUtils.isNotEmpty(expression) && ELEMENT_FORMAL_EXPRESSION.equals(expression)) {
	          
	          List<String> assignmentList = CommaSplitter.splitCommas(xtr.getElementText());
	          
	          for (String assignmentValue : assignmentList) {
	            if (assignmentValue == null) {
	              continue;
	            }
	            
	            assignmentValue = assignmentValue.trim();
	            
	            if (assignmentValue.length() == 0) {
	              continue;
	            }

	            String userPrefix = "user(";
	            String groupPrefix = "group(";
	            if (assignmentValue.startsWith(userPrefix)) {
	              assignmentValue = assignmentValue.substring(userPrefix.length(), assignmentValue.length() - 1).trim();
	              ((SignTask) parentElement).addCustomUserIdentityLink(assignmentValue, identityLinkType);
	            } else if (assignmentValue.startsWith(groupPrefix)) {
	              assignmentValue = assignmentValue.substring(groupPrefix.length(), assignmentValue.length() - 1).trim();
	              ((SignTask) parentElement).addCustomGroupIdentityLink(assignmentValue, identityLinkType);
	            } else {
	              ((SignTask) parentElement).addCustomGroupIdentityLink(assignmentValue, identityLinkType);
	            }
	          }
	        }
	      }
	    }
	  }
}
