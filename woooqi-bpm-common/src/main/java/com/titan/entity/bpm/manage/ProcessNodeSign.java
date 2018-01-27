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
@Table(name="BPM_PROCESS_NODE_SIGN")
@Entity
public class ProcessNodeSign implements Serializable {
	
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String decision_type;
	private String vote_type;
	private String ballot;
	private String activitiId;
	private String definitionId;
	
	@JsonIgnore
	@OneToMany(mappedBy="sign",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<ProcessNodeSignSpecial> signSpecials = new HashSet<ProcessNodeSignSpecial>();
	
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
	public String getDecision_type() {
		return decision_type;
	}
	public void setDecision_type(String decision_type) {
		this.decision_type = decision_type;
	}
	public String getVote_type() {
		return vote_type;
	}
	public void setVote_type(String vote_type) {
		this.vote_type = vote_type;
	}
	public String getBallot() {
		return ballot;
	}
	public void setBallot(String ballot) {
		this.ballot = ballot;
	}
	public Set<ProcessNodeSignSpecial> getSignSpecials() {
		return signSpecials;
	}
	public void setSignSpecials(Set<ProcessNodeSignSpecial> signSpecials) {
		this.signSpecials = signSpecials;
	}
	
	
}
