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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;

//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_ROLE")
@Entity
public class Role implements Serializable{
	
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String name;
	private String roleCode;
	private Integer status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@JsonIgnore
	@ManyToMany(mappedBy="roles",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<ProcessNodeAssignType> assignTypes = new HashSet<ProcessNodeAssignType>();
	
	@JsonIgnore
	@ManyToMany(mappedBy="roles",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<ProcessNodeSignSpecial> signSpecials = new HashSet<ProcessNodeSignSpecial>();
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="BPM_USER_ROLE",
			   joinColumns={@JoinColumn(name="ROLE_ID",referencedColumnName="id")},
			   inverseJoinColumns={@JoinColumn(name="USER_ID",referencedColumnName="id")})
	private Set<User> users = new HashSet<User>();
	
	@JsonIgnore
	@ManyToMany(mappedBy="roles",fetch=FetchType.LAZY)
	private Set<Menu> menus = new HashSet<Menu>();
	
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
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
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
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	public Set<ProcessNodeAssignType> getAssignTypes() {
		return assignTypes;
	}
	public void setAssignTypes(Set<ProcessNodeAssignType> assignTypes) {
		this.assignTypes = assignTypes;
	}
	public Set<ProcessNodeSignSpecial> getSignSpecials() {
		return signSpecials;
	}
	public void setSignSpecials(Set<ProcessNodeSignSpecial> signSpecials) {
		this.signSpecials = signSpecials;
	}
	
}
