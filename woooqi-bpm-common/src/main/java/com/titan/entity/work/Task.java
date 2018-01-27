package com.titan.entity.work;

import java.io.Serializable;
import java.util.Date;

//@Cacheable
@SuppressWarnings("serial")
public class Task implements Serializable{

	private String id_ ;
	private Integer rev_;
	private String execution_id_ ;
	private String proc_inst_id_;
	private String proc_def_id_ ;
	private String name_ ;
	private String parent_task_id_ ;
	private String description_ ;
	private String task_def_key_ ;
	private String owner_;
	private String assignee_;
	private String delegation_;
	private Integer priority_ ;
	private Date create_time_;
	private Date due_date_ ;
	private String category_;
	private Integer suspension_state_ ;
	private String tenant_id_;
	private String form_key_ ;
	private String task_op;
	private String proc_def_name_;
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
	public String getExecution_id_() {
		return execution_id_;
	}
	public void setExecution_id_(String execution_id_) {
		this.execution_id_ = execution_id_;
	}
	public String getProc_inst_id_() {
		return proc_inst_id_;
	}
	public void setProc_inst_id_(String proc_inst_id_) {
		this.proc_inst_id_ = proc_inst_id_;
	}
	public String getProc_def_id_() {
		return proc_def_id_;
	}
	public void setProc_def_id_(String proc_def_id_) {
		this.proc_def_id_ = proc_def_id_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getParent_task_id_() {
		return parent_task_id_;
	}
	public void setParent_task_id_(String parent_task_id_) {
		this.parent_task_id_ = parent_task_id_;
	}
	public String getDescription_() {
		return description_;
	}
	public void setDescription_(String description_) {
		this.description_ = description_;
	}
	public String getTask_def_key_() {
		return task_def_key_;
	}
	public void setTask_def_key_(String task_def_key_) {
		this.task_def_key_ = task_def_key_;
	}
	public String getOwner_() {
		return owner_;
	}
	public void setOwner_(String owner_) {
		this.owner_ = owner_;
	}
	public String getAssignee_() {
		return assignee_;
	}
	public void setAssignee_(String assignee_) {
		this.assignee_ = assignee_;
	}
	public String getDelegation_() {
		return delegation_;
	}
	public void setDelegation_(String delegation_) {
		this.delegation_ = delegation_;
	}
	public Integer getPriority_() {
		return priority_;
	}
	public void setPriority_(Integer priority_) {
		this.priority_ = priority_;
	}
	public Date getCreate_time_() {
		return create_time_;
	}
	public void setCreate_time_(Date create_time_) {
		this.create_time_ = create_time_;
	}
	public Date getDue_date_() {
		return due_date_;
	}
	public void setDue_date_(Date due_date_) {
		this.due_date_ = due_date_;
	}
	public String getCategory_() {
		return category_;
	}
	public void setCategory_(String category_) {
		this.category_ = category_;
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
	public String getForm_key_() {
		return form_key_;
	}
	public void setForm_key_(String form_key_) {
		this.form_key_ = form_key_;
	}

	public String getTask_op() {
		return task_op;
	}
	public void setTask_op(String task_op) {
		this.task_op = task_op;
	}
	
	
	public String getProc_def_name_() {
		return proc_def_name_;
	}
	public void setProc_def_name_(String proc_def_name_) {
		this.proc_def_name_ = proc_def_name_;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assignee_ == null) ? 0 : assignee_.hashCode());
		result = prime * result
				+ ((category_ == null) ? 0 : category_.hashCode());
		result = prime * result
				+ ((create_time_ == null) ? 0 : create_time_.hashCode());
		result = prime * result
				+ ((delegation_ == null) ? 0 : delegation_.hashCode());
		result = prime * result
				+ ((description_ == null) ? 0 : description_.hashCode());
		result = prime * result
				+ ((due_date_ == null) ? 0 : due_date_.hashCode());
		result = prime * result
				+ ((execution_id_ == null) ? 0 : execution_id_.hashCode());
		result = prime * result
				+ ((form_key_ == null) ? 0 : form_key_.hashCode());
		result = prime * result + ((id_ == null) ? 0 : id_.hashCode());
		result = prime * result + ((name_ == null) ? 0 : name_.hashCode());
		result = prime * result + ((owner_ == null) ? 0 : owner_.hashCode());
		result = prime * result
				+ ((parent_task_id_ == null) ? 0 : parent_task_id_.hashCode());
		result = prime * result
				+ ((priority_ == null) ? 0 : priority_.hashCode());
		result = prime * result
				+ ((proc_def_id_ == null) ? 0 : proc_def_id_.hashCode());
		result = prime * result
				+ ((proc_inst_id_ == null) ? 0 : proc_inst_id_.hashCode());
		result = prime * result + ((rev_ == null) ? 0 : rev_.hashCode());
		result = prime
				* result
				+ ((suspension_state_ == null) ? 0 : suspension_state_
						.hashCode());
		result = prime * result
				+ ((task_def_key_ == null) ? 0 : task_def_key_.hashCode());
		result = prime * result
				+ ((tenant_id_ == null) ? 0 : tenant_id_.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (assignee_ == null) {
			if (other.assignee_ != null)
				return false;
		} else if (!assignee_.equals(other.assignee_))
			return false;
		if (category_ == null) {
			if (other.category_ != null)
				return false;
		} else if (!category_.equals(other.category_))
			return false;
		if (create_time_ == null) {
			if (other.create_time_ != null)
				return false;
		} else if (!create_time_.equals(other.create_time_))
			return false;
		if (delegation_ == null) {
			if (other.delegation_ != null)
				return false;
		} else if (!delegation_.equals(other.delegation_))
			return false;
		if (description_ == null) {
			if (other.description_ != null)
				return false;
		} else if (!description_.equals(other.description_))
			return false;
		if (due_date_ == null) {
			if (other.due_date_ != null)
				return false;
		} else if (!due_date_.equals(other.due_date_))
			return false;
		if (execution_id_ == null) {
			if (other.execution_id_ != null)
				return false;
		} else if (!execution_id_.equals(other.execution_id_))
			return false;
		if (form_key_ == null) {
			if (other.form_key_ != null)
				return false;
		} else if (!form_key_.equals(other.form_key_))
			return false;
		if (id_ == null) {
			if (other.id_ != null)
				return false;
		} else if (!id_.equals(other.id_))
			return false;
		if (name_ == null) {
			if (other.name_ != null)
				return false;
		} else if (!name_.equals(other.name_))
			return false;
		if (owner_ == null) {
			if (other.owner_ != null)
				return false;
		} else if (!owner_.equals(other.owner_))
			return false;
		if (parent_task_id_ == null) {
			if (other.parent_task_id_ != null)
				return false;
		} else if (!parent_task_id_.equals(other.parent_task_id_))
			return false;
		if (priority_ == null) {
			if (other.priority_ != null)
				return false;
		} else if (!priority_.equals(other.priority_))
			return false;
		if (proc_def_id_ == null) {
			if (other.proc_def_id_ != null)
				return false;
		} else if (!proc_def_id_.equals(other.proc_def_id_))
			return false;
		if (proc_inst_id_ == null) {
			if (other.proc_inst_id_ != null)
				return false;
		} else if (!proc_inst_id_.equals(other.proc_inst_id_))
			return false;
		if (rev_ == null) {
			if (other.rev_ != null)
				return false;
		} else if (!rev_.equals(other.rev_))
			return false;
		if (suspension_state_ == null) {
			if (other.suspension_state_ != null)
				return false;
		} else if (!suspension_state_.equals(other.suspension_state_))
			return false;
		if (task_def_key_ == null) {
			if (other.task_def_key_ != null)
				return false;
		} else if (!task_def_key_.equals(other.task_def_key_))
			return false;
		if (tenant_id_ == null) {
			if (other.tenant_id_ != null)
				return false;
		} else if (!tenant_id_.equals(other.tenant_id_))
			return false;
		return true;
	}
	
	
	
}
