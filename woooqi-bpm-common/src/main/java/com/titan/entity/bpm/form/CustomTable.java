package com.titan.entity.bpm.form;

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
@Table(name="BPM_TABLE")
@Entity
public class CustomTable implements Serializable{

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String name;
	private String description;
	
	@JsonIgnore
	@OneToMany(mappedBy="table",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<CustomTableColumn> cloumns = new HashSet<CustomTableColumn>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<CustomTableColumn> getCloumns() {
		return cloumns;
	}

	public void setCloumns(Set<CustomTableColumn> cloumns) {
		this.cloumns = cloumns;
	}
	
}
