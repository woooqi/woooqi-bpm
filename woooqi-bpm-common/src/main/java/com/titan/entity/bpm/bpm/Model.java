package com.titan.entity.bpm.bpm;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Model implements Serializable{
	private String id_;
	private Integer rev_;
	private String name_;
	private String key_;
	private String category_;
	private Date create_time_;
	private Date last_update_time_;
	private Integer version_ ;
	private String meta_info_;
	private String deployment_id_;
	private String editor_source_value_id_;
	private String editor_source_extra_value_id_;
	private String tenant_id_;
	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}
	public Integer getRev_() {
		return rev_;
	}
	public void setRev_(Integer rev_) {
		this.rev_ = rev_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getKey_() {
		return key_;
	}
	public void setKey_(String key_) {
		this.key_ = key_;
	}
	public String getCategory_() {
		return category_;
	}
	public void setCategory_(String category_) {
		this.category_ = category_;
	}
	public Date getCreate_time_() {
		return create_time_;
	}
	public void setCreate_time_(Date create_time_) {
		this.create_time_ = create_time_;
	}
	public Date getLast_update_time_() {
		return last_update_time_;
	}
	public void setLast_update_time_(Date last_update_time_) {
		this.last_update_time_ = last_update_time_;
	}
	public Integer getVersion_() {
		return version_;
	}
	public void setVersion_(Integer version_) {
		this.version_ = version_;
	}
	public String getMeta_info_() {
		return meta_info_;
	}
	public void setMeta_info_(String meta_info_) {
		this.meta_info_ = meta_info_;
	}
	public String getDeployment_id_() {
		return deployment_id_;
	}
	public void setDeployment_id_(String deployment_id_) {
		this.deployment_id_ = deployment_id_;
	}
	public String getEditor_source_value_id_() {
		return editor_source_value_id_;
	}
	public void setEditor_source_value_id_(String editor_source_value_id_) {
		this.editor_source_value_id_ = editor_source_value_id_;
	}
	public String getEditor_source_extra_value_id_() {
		return editor_source_extra_value_id_;
	}
	public void setEditor_source_extra_value_id_(
			String editor_source_extra_value_id_) {
		this.editor_source_extra_value_id_ = editor_source_extra_value_id_;
	}
	public String getTenant_id_() {
		return tenant_id_;
	}
	public void setTenant_id_(String tenant_id_) {
		this.tenant_id_ = tenant_id_;
	}
	
}
