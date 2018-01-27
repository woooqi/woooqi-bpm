package com.titan.entity.organization;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;

//@Cacheable
@SuppressWarnings("serial")
@Table(name="BPM_POST_SIGN_SPECIAL")
@Entity
public class PostAndSignSpecial implements Serializable{

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;
	private String decision;
	
	@ManyToOne
	@JoinColumn(name="POST_ID")
	private Post post;
	@ManyToOne
	@JoinColumn(name="SPECIAL_ID")
	private ProcessNodeSignSpecial signSpecial;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public ProcessNodeSignSpecial getSignSpecial() {
		return signSpecial;
	}
	public void setSignSpecial(ProcessNodeSignSpecial signSpecial) {
		this.signSpecial = signSpecial;
	}
	
}
