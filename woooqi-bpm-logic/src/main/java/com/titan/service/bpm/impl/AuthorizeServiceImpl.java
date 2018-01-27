package com.titan.service.bpm.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.bpm.Category;
import com.titan.entity.bpm.bpm.ProcessDefinitionDept;
import com.titan.entity.bpm.bpm.ProcessDefinitionPost;
import com.titan.entity.bpm.bpm.ProcessDefinitionRole;
import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.organization.Dept;
import com.titan.entity.organization.DeptAndAssignType;
import com.titan.entity.organization.Post;
import com.titan.entity.organization.PostAndAssignType;
import com.titan.entity.organization.Role;
import com.titan.entity.organization.RoleAndAssignType;
import com.titan.repository.bpm.manage.DefinitionDeptRepository;
import com.titan.repository.bpm.manage.DefinitionPostRepository;
import com.titan.repository.bpm.manage.DefinitionRoleRepository;
import com.titan.repository.organization.CategoryRepository;
import com.titan.repository.organization.DeptRepository;
import com.titan.repository.organization.PostRepository;
import com.titan.repository.organization.RoleRepository;
import com.titan.service.bpm.AuthorizeService;
import com.titan.utils.DataBaseUtils;
@Service
public class AuthorizeServiceImpl implements AuthorizeService{
	
	@Autowired
	private DefinitionDeptRepository definitionDeptRepository;
	
	@Autowired
	private DefinitionRoleRepository definitionRoleRepository;
	
	@Autowired
	private DefinitionPostRepository definitionPostRepository;
	
	@Override
	public void saveAuthorization(String definitionId,List<Map<String, Object>> groups) {
		
		List<ProcessDefinitionRole> definitionRoles = definitionRoleRepository.findByDefinitionId(definitionId);
		List<ProcessDefinitionDept> definitionDepts = definitionDeptRepository.findByDefinitionId(definitionId);
		List<ProcessDefinitionPost> definitionPosts = definitionPostRepository.findByDefinitionId(definitionId);
		
		if(definitionRoles != null){
			for(ProcessDefinitionRole definitionRole:definitionRoles){
				definitionRoleRepository.delete(definitionRole);
			}
		}
		if(definitionDepts != null){
			for(ProcessDefinitionDept definitionDept:definitionDepts){
				definitionDeptRepository.delete(definitionDept);
			}
		}	
		if(definitionPosts != null){
			for(ProcessDefinitionPost definitionPost:definitionPosts){
				definitionPostRepository.delete(definitionPost);
			}
		}	
		
		for(int i=0;i<groups.size();i++){
			Map<String,Object> group = groups.get(i);
			if("角色".equals(group.get("type"))){
				ProcessDefinitionRole processDefinitionRole = new ProcessDefinitionRole();
				processDefinitionRole.setRoleId(group.get("id").toString());
				processDefinitionRole.setDefinitionId(definitionId);
				definitionRoleRepository.saveAndFlush(processDefinitionRole);
				
			}else if("部门".equals(group.get("type"))){
				ProcessDefinitionDept processDefinitionDept = new ProcessDefinitionDept();
				processDefinitionDept.setDeptId(group.get("id").toString());
				processDefinitionDept.setDefinitionId(definitionId);
				definitionDeptRepository.saveAndFlush(processDefinitionDept);
				
			}else if("岗位".equals(group.get("type"))){
				ProcessDefinitionPost processDefinitionPost = new ProcessDefinitionPost();
				processDefinitionPost.setPostId(group.get("id").toString());
				processDefinitionPost.setDefinitionId(definitionId);
				definitionPostRepository.saveAndFlush(processDefinitionPost);
				
			}
		}
	}

	@Override
	public Map<String, Object> getGroupByDefinitionId(String definitionId) {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Role> roles = this.findRoleByDefinitionId(definitionId);
		List<Dept> depts = this.findDeptByDefinitionId(definitionId);
		List<Post> posts = this.findPostByDefinitionId(definitionId);
		
		map.put("roles", roles);
		map.put("depts", depts);
		map.put("posts", posts);
		
		return map;
	}

	private List<Post> findPostByDefinitionId(String definitionId) {
		String sql = "select b.* from bpm_definition_post a,bpm_post b where a.post_id = b.id and a.definition_id = ?";
		return DataBaseUtils.queryForListBean(sql, Post.class, definitionId);
	}

	private List<Dept> findDeptByDefinitionId(String definitionId) {
		String sql = "select b.* from bpm_definition_dept a,bpm_dept b where a.dept_id = b.id and a.definition_id = ?";
		return DataBaseUtils.queryForListBean(sql, Dept.class, definitionId);
	}

	private List<Role> findRoleByDefinitionId(String definitionId) {
		String sql = "select b.* from bpm_definition_role a,bpm_role b where a.role_id = b.id and a.definition_id = ?";
		return DataBaseUtils.queryForListBean(sql, Role.class, definitionId);
	}
	
	
	

}
