package com.titan.entity.bpm.manage;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.titan.entity.organization.User;



//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_PROCESS_TASK_SUGGEST")
@Entity
public class ProcessNodeTaskSuggest implements Serializable{
	
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String activitiId;
	private String definitionId;
	private String instanceId;
	private String suggest;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="id")
	private User user;
	
	
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
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
