package com.titan.entity.bpm.manage;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_PROCESS_NODE_BUTTON")
@Entity
public class ProcessNodeFormButton implements Serializable{
	
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String code;
	private String name;
	private String description;
	private String mapping;
	
	@JsonIgnore
	@ManyToMany(mappedBy="buttons", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<ProcessNodeForm> forms = new HashSet<ProcessNodeForm>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<ProcessNodeForm> getForms() {
		return forms;
	}

	public void setForms(Set<ProcessNodeForm> forms) {
		this.forms = forms;
	}
	
	
}
