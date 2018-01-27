package com.titan.entity.bpm.manage;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_PROCESS_MANAGE")
@Entity
public class ProcessManage implements Serializable{
	
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String definitionId;
	private String name;
	private String category;
	private String description;
	private String startEventClass;
	private String endEventClass;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartEventClass() {
		return startEventClass;
	}
	public void setStartEventClass(String startEventClass) {
		this.startEventClass = startEventClass;
	}
	public String getEndEventClass() {
		return endEventClass;
	}
	public void setEndEventClass(String endEventClass) {
		this.endEventClass = endEventClass;
	}
	
}
