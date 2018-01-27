package com.woooqi.bpm.repository.organization;

import java.util.List;

import com.woooqi.bpm.entity.bpm.manage.ProcessNodeSignSpecial;
import com.woooqi.bpm.entity.organization.Post;
import com.woooqi.bpm.entity.organization.PostAndSignSpecial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;
import com.titan.entity.organization.Post;
import com.titan.entity.organization.PostAndSignSpecial;

public interface PostAndSignSpecialRepository extends JpaRepository<PostAndSignSpecial,String>,JpaSpecificationExecutor<PostAndSignSpecial>{
 
	  public List<PostAndSignSpecial> findBySignSpecial(ProcessNodeSignSpecial signSpecial);
	
	  public List<PostAndSignSpecial> findBySignSpecialAndPost(ProcessNodeSignSpecial signSpecial ,Post post);
}
