package com.titan.entity.bpm.bpm;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProcessDefinition implements Serializable{
	private String id_;
	private Integer rev_;
	private String category_;
	private String name_;
	private String key_;
	private Integer version_;
	private String deployment_id_;
	private String resource_name_;
	private String dgrm_resource_name_;
	private String description_;
	private Integer has_start_form_key_;
	private Integer has_graphical_notation_;
	private Integer suspension_state_;
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
	public String getCategory_() {
		return category_;
	}
	public void setCategory_(String category_) {
		this.category_ = category_;
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
	public Integer getVersion_() {
		return version_;
	}
	public void setVersion_(Integer version_) {
		this.version_ = version_;
	}
	public String getDeployment_id_() {
		return deployment_id_;
	}
	public void setDeployment_id_(String deployment_id_) {
		this.deployment_id_ = deployment_id_;
	}
	public String getResource_name_() {
		return resource_name_;
	}
	public void setResource_name_(String resource_name_) {
		this.resource_name_ = resource_name_;
	}
	public String getDgrm_resource_name_() {
		return dgrm_resource_name_;
	}
	public void setDgrm_resource_name_(String dgrm_resource_name_) {
		this.dgrm_resource_name_ = dgrm_resource_name_;
	}
	public String getDescription_() {
		return description_;
	}
	public void setDescription_(String description_) {
		this.description_ = description_;
	}
	public Integer getHas_start_form_key_() {
		return has_start_form_key_;
	}
	public void setHas_start_form_key_(Integer has_start_form_key_) {
		this.has_start_form_key_ = has_start_form_key_;
	}
	public Integer getHas_graphical_notation_() {
		return has_graphical_notation_;
	}
	public void setHas_graphical_notation_(Integer has_graphical_notation_) {
		this.has_graphical_notation_ = has_graphical_notation_;
	}
	public Integer getSuspension_state_() {
		return suspension_state_;
	}
	public void setSuspension_state_(Integer suspension_state_) {
		this.suspension_state_ = suspension_state_;
	}
	public String getTenant_id_() {
		return tenant_id_;
	}
	public void setTenant_id_(String tenant_id_) {
		this.tenant_id_ = tenant_id_;
	}
	
}
