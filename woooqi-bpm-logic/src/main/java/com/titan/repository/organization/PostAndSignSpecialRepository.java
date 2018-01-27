package com.titan.repository.organization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;
import com.titan.entity.organization.Post;
import com.titan.entity.organization.PostAndSignSpecial;

public interface PostAndSignSpecialRepository extends JpaRepository<PostAndSignSpecial,String>,JpaSpecificationExecutor<PostAndSignSpecial>{
 
	  public List<PostAndSignSpecial> findBySignSpecial(ProcessNodeSignSpecial signSpecial);
	
	  public List<PostAndSignSpecial> findBySignSpecialAndPost(ProcessNodeSignSpecial signSpecial ,Post post);
}
