package com.titan.entity.bpm.form;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_FORM")
@Entity
public class CustomForm implements Serializable{

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String name;
	private String description;
	private Integer columns;
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(columnDefinition="BLOB") 
	private String html;
	
	@OneToOne
	@JoinColumn(name="TABLE_ID")
	private CustomTable table;

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

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public CustomTable getTable() {
		return table;
	}

	public void setTable(CustomTable table) {
		this.table = table;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}



	

	
}
