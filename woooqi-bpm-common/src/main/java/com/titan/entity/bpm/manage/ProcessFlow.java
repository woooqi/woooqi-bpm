package com.titan.entity.bpm.manage;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_PROCESS_FLOW")
@Entity
public class ProcessFlow implements Serializable{
	
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String activitiId;
	private String definitionId;
	private String name;
	private String description;
	private String defalutFlow;
	private String conditionFlow;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivitiId() {
		return activitiId;
	}
	public void setActivitiId(String activitiId) {
		this.activitiId = activitiId;
	}
	public String getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getConditionFlow() {
		return conditionFlow;
	}
	public void setConditionFlow(String conditionFlow) {
		this.conditionFlow = conditionFlow;
	}
	public String getDefalutFlow() {
		return defalutFlow;
	}
	public void setDefalutFlow(String defalutFlow) {
		this.defalutFlow = defalutFlow;
	}
	
}
