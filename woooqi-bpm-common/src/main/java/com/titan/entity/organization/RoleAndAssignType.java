package com.titan.entity.organization;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.titan.entity.bpm.manage.ProcessNodeAssignType;

//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_ROLE_ASSIGN_TYPE")
@Entity
public class RoleAndAssignType implements Serializable{

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String logic; // 1与 2或 3非
	private Integer sort;
	
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private Role role;
	@ManyToOne
	@JoinColumn(name="TYPE_ID")
	private ProcessNodeAssignType assignType;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogic() {
		return logic;
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public ProcessNodeAssignType getAssignType() {
		return assignType;
	}
	public void setAssignType(ProcessNodeAssignType assignType) {
		this.assignType = assignType;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}
