package com.titan.entity.bpm.bpm;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Deployment implements Serializable{
	private String id_;
	private String name_;
	private String category_;
	private String tenant_id_;
	private Date  deploy_time_;
	private Integer version_;
	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getCategory_() {
		return category_;
	}
	public void setCategory_(String category_) {
		this.category_ = category_;
	}
	public String getTenant_id_() {
		return tenant_id_;
	}
	public void setTenant_id_(String tenant_id_) {
		this.tenant_id_ = tenant_id_;
	}
	public Date getDeploy_time_() {
		return deploy_time_;
	}
	public void setDeploy_time_(Date deploy_time_) {
		this.deploy_time_ = deploy_time_;
	}
	public Integer getVersion_() {
		return version_;
	}
	public void setVersion_(Integer version_) {
		this.version_ = version_;
	}
}
