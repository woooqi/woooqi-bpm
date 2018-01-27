package com.woooqi.bpm.service.bpm.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.titan.entity.bpm.bpm.ProcessDefinition;
import com.titan.service.bpm.DefinitionService;
import com.titan.utils.DataBaseUtils;
import com.titan.utils.FileUtils;
import com.titan.utils.WorkflowUtils;

@Service
public class DefinitionServiceImpl implements DefinitionService{

	@Autowired
	private RepositoryService repositoryService;	

	@Override
	public List<ProcessDefinition> getAllDefinition() {
		String sql = "select a.id_, a.rev_, a.category_, b.name_, a.key_, a.version_, a.deployment_id_, a.resource_name_, a.dgrm_resource_name_, a.description_, a.has_start_form_key_, a.has_graphical_notation_, a.suspension_state_, a.tenant_id_ from act_re_procdef a,act_re_model b where a.deployment_id_ = b.deployment_id_ order by a.name_";
		List<ProcessDefinition> processDefinitions = DataBaseUtils.queryForListBean(sql, ProcessDefinition.class);
		return processDefinitions;
	}
	
	@Override
	public List<ProcessDefinition> getDefinitionByName(String name) {
		String sql = "select t.*,t2.name as category_ from (select a.id_, a.rev_, c.category_ as true_category_, b.name_, a.key_, a.version_, a.deployment_id_, a.resource_name_, a.dgrm_resource_name_, a.description_, a.has_start_form_key_, a.has_graphical_notation_, a.suspension_state_, a.tenant_id_ from act_re_procdef a,act_re_model b, act_re_deployment c where a.deployment_id_ = b.deployment_id_ and a.deployment_id_ = c.id_)t,bpm_category t2 where t.true_category_ = t2.id(+)";
		if(!"".equals(name)){
			sql += " and t.name_ like '%"+name+"%'";
		}
		sql+=" order by t.name_" ;
		List<ProcessDefinition> processDefinitions = DataBaseUtils.queryForListBean(sql, ProcessDefinition.class);
		
		return processDefinitions;
	}


	@Override
	public ProcessDefinition getDefinitionByDefinitionId(String definitionId) {
		String sql = "select t.*,t2.name as category_ from (select a.id_, a.rev_, c.category_ as true_category_, b.name_, a.key_, a.version_, a.deployment_id_, a.resource_name_, a.dgrm_resource_name_, a.description_, a.has_start_form_key_, a.has_graphical_notation_, a.suspension_state_, a.tenant_id_ from act_re_procdef a,act_re_model b, act_re_deployment c where a.deployment_id_ = b.deployment_id_ and a.deployment_id_ = c.id_)t,bpm_category t2 where t.id_=? and t.true_category_ = t2.id(+)";
		//String sql = "select * from act_re_procdef t where t.id_ = ? order by t.name_";
		ProcessDefinition processDefinition = DataBaseUtils.queryForBean(sql, ProcessDefinition.class,definitionId);
		return processDefinition;
	}
	
	@Override
	public void delDeploy(String deployId) throws Exception {
		Model model = repositoryService.createModelQuery().deploymentId(deployId).singleResult();
		org.activiti.engine.repository.ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
		String key = processDefinition.getKey();
		repositoryService.deleteDeployment(deployId);
		
		List<org.activiti.engine.repository.ProcessDefinition> newProcessDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).orderByProcessDefinitionVersion().desc().list();
		if(newProcessDefinitions.size()>0){
			String newDeployId = newProcessDefinitions.get(0).getDeploymentId();
			
			String resourceName = "";
			List<String> resourceNames = repositoryService.getDeploymentResourceNames(newDeployId);
			for(String name:resourceNames){
				if(name.endsWith(".xml")){
					resourceName = name;
				}
			}
			if(!StringUtils.isBlank(resourceName)){
				InputStream resourceAsStream = repositoryService.getResourceAsStream(newDeployId, resourceName);
				repositoryService.deleteModel(model.getId());
				String modelId = saveXmlAdModel(resourceName, resourceAsStream, newDeployId);
				DataBaseUtils.update("update act_re_model set deployment_id_ = ? where id_ = ?",newDeployId ,modelId);
			}
		}else{
			//
		}
	}
	
	public String saveXmlAdModel(String fileName , InputStream inxml,String deploymentId) throws Exception{

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		int len = 0;
		while((len = inxml.read()) != -1){
			outputStream.write(len);
		}
		if (fileName.endsWith(".xml")) {
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			XMLInputFactory xif = XMLInputFactory.newInstance();
			InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray()), "UTF-8");
			XMLStreamReader xtr = xif.createXMLStreamReader(in);
	        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
	        xmlConverter.convertToBpmnModel(xtr);

	        if ((bpmnModel.getMainProcess() == null) || (bpmnModel.getMainProcess().getId() == null) || (bpmnModel.getLocationMap().size() == 0)) {
	        	throw new RuntimeException("Null Model InputStream Exception");
	        }else{
	        	 String processName = bpmnModel.getMainProcess().getName()==null?"":bpmnModel.getMainProcess().getName();
	        	 String processId = bpmnModel.getMainProcess().getId()==null?"":bpmnModel.getMainProcess().getId();

        		 if(StringUtils.isBlank(processName)){
        			 processName = processId;
        		 }
        		 if(StringUtils.isBlank(processId)){
        			 processId = processName;
        		 }
	        	 
	        	 Model modelData = this.repositoryService.newModel();
	        	 ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
	        	 modelObjectNode.put("name", processName);
	        	 modelObjectNode.put("revision", 1);
	        	 modelData.setMetaInfo(modelObjectNode.toString());
	        	 modelData.setName(processName);
	        	 modelData.setKey(processId);
	        	 
	        	 modelData.setDeploymentId(deploymentId);
	        	 
	        	 List<Model> Models = repositoryService.createModelQuery().modelKey(processId).list();
	        	 for(Model model:Models){
	        		 repositoryService.deleteModel(model.getId());
	        	 }
	        	 
	        	 repositoryService.saveModel(modelData);

	        	 BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
	        	 ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);

	        	 this.repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
	        	 return modelData.getId();
	         } 
		}
		outputStream.close();
		return null;
	}
	
	@Override
    public String deploymentOnLine(String fileName,byte[] bytes) throws Exception{
		DeploymentBuilder deploymentBuilder = this.repositoryService.createDeployment().name(fileName);
		ZipInputStream zipIn = new ZipInputStream(FileUtils.byte2InputStream(bytes));
		
		if (fileName.endsWith(".xml")) {
			deploymentBuilder.addInputStream(fileName, zipIn);
		} else if (fileName.endsWith(".zip")) {
			deploymentBuilder.addZipInputStream(zipIn);
		} 
		String deployId = deploymentBuilder.deploy().getId();
		
		//转成在线版的资源文件
	    ZipEntry entry;
	    String modelId = "";
	    zipIn = new ZipInputStream(FileUtils.byte2InputStream(bytes));
	    while ((entry = zipIn.getNextEntry())!=null && !entry.isDirectory()) {
	    	String entryName = entry.getName();
	    	if(entryName.endsWith(".xml")){
	    		modelId = saveXmlAdModel(entryName,zipIn,deployId);
	    	}
		}
	    zipIn.close();
	    DataBaseUtils.update("update act_re_model set deployment_id_ = ? where id_ = ?",deployId ,modelId);
		return deployId; 
	}
	
	@Override
	public String deploymentOnWeb(String deployId){
		
		try {
			Model model = repositoryService.createModelQuery().deploymentId(deployId).singleResult();
  
			ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(model.getId()));

			BpmnModel bpmModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			byte[] bytes = new BpmnXMLConverter().convertToXML(bpmModel);

			String processName = model.getName() + ".bpmn20.xml";
			String xml = WorkflowUtils.changeXML(new String(bytes,"utf-8"));
 
			Deployment deployment = repositoryService.createDeployment().name(model.getName()).addString(processName, xml).deploy();		      
			deployId = deployment.getId();
			
			DataBaseUtils.update("update act_re_model set deployment_id_ = ? where id_ = ?", deployment.getId() ,model.getId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return deployId;
	}

	@Override
	public List<ProcessDefinition> getDefinitionByCategoryId(String parentId) {
		String sql = "select t.*,t2.name as category_ from (select a.id_, a.rev_, c.category_ as true_category_, b.name_, a.key_, a.version_, a.deployment_id_, a.resource_name_, a.dgrm_resource_name_, a.description_, a.has_start_form_key_, a.has_graphical_notation_, a.suspension_state_, a.tenant_id_ from act_re_procdef a,act_re_model b, act_re_deployment c where a.deployment_id_ = b.deployment_id_ and a.deployment_id_ = c.id_)t,bpm_category t2 where t.true_category_ = t2.id(+)";
		if(!"".equals(parentId)){
			sql += " and t.true_category_ ='"+parentId+"'";
		}
		sql+=" order by t.name_" ;
		List<ProcessDefinition> processDefinitions = DataBaseUtils.queryForListBean(sql, ProcessDefinition.class);
		
		return processDefinitions;
	}

}
