package com.titan.entity.bpm.bpm;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Instance implements Serializable{
	private String id_;
	private Integer rev_;
	private String proc_inst_id_;
	private String business_key_;
	private String parent_id_;
	private String proc_def_id_;
	private String super_exec_;
	private String act_id_;
	private Long is_active_;
	private Long is_concurrent_;
	private Long is_scope_;
	private Long is_event_scope_;
	private Long suspension_state_;
	private Long cached_ent_state_;
	private String tenant_id_;
	private String name_;
	private Date lock_time_;
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
	public String getProc_inst_id_() {
		return proc_inst_id_;
	}
	public void setProc_inst_id_(String proc_inst_id_) {
		this.proc_inst_id_ = proc_inst_id_;
	}
	public String getBusiness_key_() {
		return business_key_;
	}
	public void setBusiness_key_(String business_key_) {
		this.business_key_ = business_key_;
	}
	public String getParent_id_() {
		return parent_id_;
	}
	public void setParent_id_(String parent_id_) {
		this.parent_id_ = parent_id_;
	}
	public String getProc_def_id_() {
		return proc_def_id_;
	}
	public void setProc_def_id_(String proc_def_id_) {
		this.proc_def_id_ = proc_def_id_;
	}
	public String getSuper_exec_() {
		return super_exec_;
	}
	public void setSuper_exec_(String super_exec_) {
		this.super_exec_ = super_exec_;
	}
	public String getAct_id_() {
		return act_id_;
	}
	public void setAct_id_(String act_id_) {
		this.act_id_ = act_id_;
	}
	public Long getIs_active_() {
		return is_active_;
	}
	public void setIs_active_(Long is_active_) {
		this.is_active_ = is_active_;
	}
	public Long getIs_concurrent_() {
		return is_concurrent_;
	}
	public void setIs_concurrent_(Long is_concurrent_) {
		this.is_concurrent_ = is_concurrent_;
	}
	public Long getIs_scope_() {
		return is_scope_;
	}
	public void setIs_scope_(Long is_scope_) {
		this.is_scope_ = is_scope_;
	}
	public Long getIs_event_scope_() {
		return is_event_scope_;
	}
	public void setIs_event_scope_(Long is_event_scope_) {
		this.is_event_scope_ = is_event_scope_;
	}
	public Long getSuspension_state_() {
		return suspension_state_;
	}
	public void setSuspension_state_(Long suspension_state_) {
		this.suspension_state_ = suspension_state_;
	}
	public Long getCached_ent_state_() {
		return cached_ent_state_;
	}
	public void setCached_ent_state_(Long cached_ent_state_) {
		this.cached_ent_state_ = cached_ent_state_;
	}
	public String getTenant_id_() {
		return tenant_id_;
	}
	public void setTenant_id_(String tenant_id_) {
		this.tenant_id_ = tenant_id_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public Date getLock_time_() {
		return lock_time_;
	}
	public void setLock_time_(Date lock_time_) {
		this.lock_time_ = lock_time_;
	}
	
	
	
	
	
}
