package com.titan.entity.organization;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;

//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_DEPT")
@Entity
public class Dept implements Serializable{

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String name;
	private String deptCode;
	private String parentId;
	private Integer sort;
	private Integer status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@JsonIgnore
	@OneToMany(mappedBy="dept",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<User> users = new HashSet<User>();
	
	@JsonIgnore
	@OneToMany(mappedBy="dept",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<Post> posts = new HashSet<Post>();
	
	@JsonIgnore
	@ManyToMany(mappedBy="depts",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<ProcessNodeAssignType> assignTypes = new HashSet<ProcessNodeAssignType>();
	
	@JsonIgnore
	@ManyToMany(mappedBy="depts",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<ProcessNodeSignSpecial> signSpecials = new HashSet<ProcessNodeSignSpecial>();
	
	
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<Post> getPosts() {
		return posts;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
	public Set<ProcessNodeAssignType> getAssignTypes() {
		return assignTypes;
	}
	public void setAssignTypes(Set<ProcessNodeAssignType> assignTypes) {
		this.assignTypes = assignTypes;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Set<ProcessNodeSignSpecial> getSignSpecials() {
		return signSpecials;
	}
	public void setSignSpecials(Set<ProcessNodeSignSpecial> signSpecials) {
		this.signSpecials = signSpecials;
	}
	
}
