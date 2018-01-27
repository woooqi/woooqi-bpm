package com.titan.entity.bpm.manage;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_PROCESS_NODE_FORM")
@Entity
public class ProcessNodeForm implements Serializable{
	
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String activitiId;
	private String definitionId;
	private String type;//1自定义表单 2外部表单
	private String url;//自定义表  url是customForm的id
	private String urlInfo;
	
	private String beforeFunction;//2
	private String afterFunction;//2
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="BPM_PROCESS_NODE_FORM_BUTTON",
		joinColumns={@JoinColumn(name="FORM_ID",referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="BUTTON_ID",referencedColumnName="id")})
	private Set<ProcessNodeFormButton> buttons = new HashSet<ProcessNodeFormButton>();
	

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlInfo() {
		return urlInfo;
	}

	public void setUrlInfo(String urlInfo) {
		this.urlInfo = urlInfo;
	}

	public String getBeforeFunction() {
		return beforeFunction;
	}

	public void setBeforeFunction(String beforeFunction) {
		this.beforeFunction = beforeFunction;
	}

	public String getAfterFunction() {
		return afterFunction;
	}

	public void setAfterFunction(String afterFunction) {
		this.afterFunction = afterFunction;
	}

	public Set<ProcessNodeFormButton> getButtons() {
		return buttons;
	}

	public void setButtons(Set<ProcessNodeFormButton> buttons) {
		this.buttons = buttons;
	}
	
	
}
