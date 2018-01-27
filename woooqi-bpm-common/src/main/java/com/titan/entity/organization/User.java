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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;
import com.titan.entity.bpm.manage.ProcessNodeTaskSuggest;

//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_USER")
@Entity
public class User implements Serializable{

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String loginName;
	private String name;
	private String password;
	private String salt;
	private Integer status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@JsonIgnore
	@ManyToMany(mappedBy="users",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<Role> roles = new HashSet<Role>();
	
	
	@JsonIgnore
	@ManyToMany(mappedBy="users",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<ProcessNodeAssignType> assignTypes = new HashSet<ProcessNodeAssignType>();
	
	@JsonIgnore
	@ManyToMany(mappedBy="users",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<ProcessNodeSignSpecial> signSpecials = new HashSet<ProcessNodeSignSpecial>();
	
	@ManyToOne
	@JoinColumn(name="DEPT_ID", referencedColumnName="id")
	private Dept dept;
	
	@JsonIgnore
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<ProcessNodeTaskSuggest> suggests = new HashSet<ProcessNodeTaskSuggest>();
	
	
	@ManyToOne
	@JoinColumn(name="POST_ID", referencedColumnName="id")
	private Post post;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<ProcessNodeAssignType> getAssignTypes() {
		return assignTypes;
	}

	public void setAssignTypes(Set<ProcessNodeAssignType> assignTypes) {
		this.assignTypes = assignTypes;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	public String getDeptId(){
		return dept==null?"":dept.getId();
	}
	
	public String getPostId(){
		return post==null?"":post.getId();
	}

	public Set<ProcessNodeSignSpecial> getSignSpecials() {
		return signSpecials;
	}

	public void setSignSpecials(Set<ProcessNodeSignSpecial> signSpecials) {
		this.signSpecials = signSpecials;
	}
	
	public String getRoleNames(){
		String roleNames = "";
		for(Role role :roles){
			roleNames = "," + role.getRoleCode();
		}
		return roleNames.length()>0?roleNames.substring(1):roleNames;
	}
	
	public String getRoleIds(){
		String roleIds = "";
		for(Role role :roles){
			roleIds = "," + role.getId();
		}
		return roleIds.length()>0?roleIds.substring(1):roleIds;
	}
	
}
