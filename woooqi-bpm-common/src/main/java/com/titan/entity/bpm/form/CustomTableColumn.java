package com.titan.entity.bpm.form;

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
@Table(name="BPM_TABLE_COLUMN")
@Entity
public class CustomTableColumn implements Serializable{

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String comments;
	private String field;
	private String type;
	private Integer lengths;
	private String tableName;
	private String dataSourceId;
	private Integer sort;
	private Boolean isProcessVariable;
	
	@ManyToOne
	@JoinColumn(name="TABLE_ID")
	private CustomTable table;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDataSourceId() {
		return dataSourceId;
	}
	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	public CustomTable getTable() {
		return table;
	}
	public void setTable(CustomTable table) {
		this.table = table;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getLengths() {
		return lengths;
	}
	public void setLengths(Integer lengths) {
		this.lengths = lengths;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Boolean getIsProcessVariable() {
		return isProcessVariable;
	}
	public void setIsProcessVariable(Boolean isProcessVariable) {
		this.isProcessVariable = isProcessVariable;
	}
	
}

