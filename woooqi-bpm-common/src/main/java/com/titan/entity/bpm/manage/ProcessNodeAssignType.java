package com.titan.entity.bpm.manage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.titan.entity.organization.Dept;
import com.titan.entity.organization.Post;
import com.titan.entity.organization.Role;
import com.titan.entity.organization.User;

//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_PROCESS_NODE_ASSIGN_TYPE")
@Entity
public class ProcessNodeAssignType implements Serializable{
	
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String type;// 1 委派人 2 获选人 3 候选组
	
	@ManyToOne
	@JoinColumn(name="ASSIGN_ID")
	private ProcessNodeAssign assign;
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="BPM_USER_ASSIGN_TYPE",
			   joinColumns={@JoinColumn(name="TYPE_ID",referencedColumnName="id")},
			   inverseJoinColumns={@JoinColumn(name="USER_ID",referencedColumnName="id")})
	private Set<User> users = new HashSet<User>();
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="BPM_ROLE_ASSIGN_TYPE",
			   joinColumns={@JoinColumn(name="TYPE_ID",referencedColumnName="id")},
			   inverseJoinColumns={@JoinColumn(name="ROLE_ID",referencedColumnName="id")})
	private List<Role> roles = new ArrayList<Role>();
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="BPM_DEPT_ASSIGN_TYPE",
			   joinColumns={@JoinColumn(name="TYPE_ID",referencedColumnName="id")},
			   inverseJoinColumns={@JoinColumn(name="DEPT_ID",referencedColumnName="id")})
	private List<Dept> depts = new ArrayList<Dept>();
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="BPM_POST_ASSIGN_TYPE",
			   joinColumns={@JoinColumn(name="TYPE_ID",referencedColumnName="id")},
			   inverseJoinColumns={@JoinColumn(name="POST_ID",referencedColumnName="id")})
	private List<Post> posts = new ArrayList<Post>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public ProcessNodeAssign getAssign() {
		return assign;
	}
	public void setAssign(ProcessNodeAssign assign) {
		this.assign = assign;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<Dept> getDepts() {
		return depts;
	}
	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
}
