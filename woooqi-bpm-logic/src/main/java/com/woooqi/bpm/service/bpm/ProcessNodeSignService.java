package com.woooqi.bpm.service.bpm;


import java.util.List;
import java.util.Map;

import com.titan.entity.bpm.manage.ProcessNodeSign;
import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;
import com.titan.entity.organization.Dept;
import com.titan.entity.organization.Post;
import com.titan.entity.organization.Role;
import com.titan.entity.organization.User;
import com.titan.entity.web.Page;


public interface ProcessNodeSignService {
	
	public Map<String,Object> getProcessNodeSignByActId(String activitiId);
	
	
	public Map<String,Object> savaAndFlushProcessNodeSignUser(ProcessNodeSign processNodeSign,List<Map<String,Object>> list);
	
	public Map<String,Object> savaAndFlushProcessNodeSign(ProcessNodeSign processNodeSign);
	
	public Page<User> getUserSignByActId(String activitiId);
	
	public Page<Role> getRoleSignByActId(String activitiId);
	
	public Page<Dept> getDeptSignByActId(String activitiId);
	
	public Page<Post> getPostSignByActId(String activitiId);
	
	public List<ProcessNodeSignSpecial> getProcessNodeSignSpecialBySign(String signId);
	
	public Map<String,Object> savaAndFlushProcessNodeSignSpecial(String signId,String type,List<Map<String,Object>> denySignSpecial);


	public Map<String, Object> getGroupBySignSpecialId(String specialId);

 
} 
