package com.titan.entity.bpm.manage;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_PROCESS_NODE_ASSIGN")
@Entity
public class ProcessNodeAssign implements Serializable{
	
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String activitiId;
	private String definitionId;
	private String policy;
	private String initiator;//0否  1是
	
	@JsonIgnore
	@OneToMany(mappedBy="assign",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<ProcessNodeAssignType> assignTypes = new HashSet<ProcessNodeAssignType>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
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
	public Set<ProcessNodeAssignType> getAssignTypes() {
		return assignTypes;
	}
	public void setAssignTypes(Set<ProcessNodeAssignType> assignTypes) {
		this.assignTypes = assignTypes;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	
	
}
