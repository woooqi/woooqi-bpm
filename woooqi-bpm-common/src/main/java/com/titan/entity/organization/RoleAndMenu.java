package com.titan.entity.organization;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_ROLE_MENU")
@Entity
public class RoleAndMenu implements Serializable{
	
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String is_parent;
	
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private Role role;
	@ManyToOne
	@JoinColumn(name="MENU_ID")
	private Menu menu;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIs_parent() {
		return is_parent;
	}
	public void setIs_parent(String is_parent) {
		this.is_parent = is_parent;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
}
