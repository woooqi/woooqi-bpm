package com.titan.service.bpm.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.organization.Dept;
import com.titan.entity.organization.DeptAndAssignType;
import com.titan.entity.organization.Post;
import com.titan.entity.organization.PostAndAssignType;
import com.titan.entity.organization.Role;
import com.titan.entity.organization.RoleAndAssignType;
import com.titan.entity.organization.User;
import com.titan.repository.bpm.manage.ProcessNodeAssignRepository;
import com.titan.repository.bpm.manage.ProcessNodeAssignTypeRepository;
import com.titan.repository.organization.DeptAndAssignTypeRepository;
import com.titan.repository.organization.DeptRepository;
import com.titan.repository.organization.PostAndAssignTypeRepository;
import com.titan.repository.organization.PostRepository;
import com.titan.repository.organization.RoleAndAssignTypeRepository;
import com.titan.repository.organization.RoleRepository;
import com.titan.repository.organization.UserRepository;
import com.titan.service.bpm.ProcessNodeAssignService;

@Service
public class ProcessNodeAssignServiceImpl implements ProcessNodeAssignService {
	
	@Autowired
	private ProcessNodeAssignRepository processNodeAssignRepository;
	
	@Autowired
	private ProcessNodeAssignTypeRepository processNodeAssignTypeRepository;
	
	@Autowired
	private RoleAndAssignTypeRepository roleAndAssignTypeRepository;
	
	@Autowired
	private DeptAndAssignTypeRepository deptAndAssignTypeRepository;

	@Autowired
	private PostAndAssignTypeRepository postAndAssignTypeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private DeptRepository deptRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	

	@Override
	public Map<String, Object> saveNodeManageAssign(ProcessNodeAssign processNodeAssign, String type, Set<String> users) {
		Map<String,Object> map = new HashMap<>();
		
		ProcessNodeAssign before = processNodeAssignRepository.findOne(processNodeAssign.getId());
		Set<ProcessNodeAssignType> assignTypes = new HashSet<>();
		if(before != null){
			assignTypes = before.getAssignTypes();
			for(ProcessNodeAssignType assignType:assignTypes){
				if(type.equals(assignType.getType())){
					assignTypes.remove(assignType);
					assignType.getUsers().clear();
					processNodeAssignTypeRepository.delete(assignType);
					break;
				}
			}
		}
		
		
		ProcessNodeAssignType processNodeAssignType = new ProcessNodeAssignType();
		processNodeAssignType.setType(type);
		Set<User> sets = new HashSet<>();
		for(String userId:users){
			User u = userRepository.findOne(userId);
			sets.add(u);
		}
		if(sets.size()>0){
			processNodeAssignType.setUsers(sets);
			processNodeAssignType.setAssign(processNodeAssign);
			assignTypes.add(processNodeAssignType);
		}else{
			processNodeAssignType = null;
		}
				
		processNodeAssign.setAssignTypes(assignTypes);
		processNodeAssignRepository.saveAndFlush(processNodeAssign);
		
		map.put("code", 1);
    	map.put("msg", "操作成功");
		return map;
		
		
	}

	@Override
	public ProcessNodeAssign getNodeManageAssignByActId(String activitiId) {
		ProcessNodeAssign processNodeAssign = processNodeAssignRepository.findByActivitiId(activitiId);
		
		return processNodeAssign;
	}

	@Override
	public Map<String, Object> saveNodeManageGroupAssign(ProcessNodeAssign processNodeAssign,String type,List<Map<String, Object>> groups) {
		Map<String,Object> map = new HashMap<>();
		
		ProcessNodeAssign before = processNodeAssignRepository.findOne(processNodeAssign.getId());
		Set<ProcessNodeAssignType> assignTypes = new HashSet<>();
		if(before != null){
			assignTypes = before.getAssignTypes();
			for(ProcessNodeAssignType assignType:assignTypes){
				if(type.equals(assignType.getType())){
					assignTypes.remove(assignType);
					assignType.getRoles().clear();
					assignType.getDepts().clear();
					assignType.getPosts().clear();
					processNodeAssignTypeRepository.delete(assignType);
					break;
				}
			}
		}	
		
		ProcessNodeAssignType processNodeAssignType = new ProcessNodeAssignType();
		processNodeAssignType.setType(type);
		ProcessNodeAssignType newProcessNodeAssignType = processNodeAssignTypeRepository.saveAndFlush(processNodeAssignType);

		for(int i=0;i<groups.size();i++){
			Map<String,Object> group = groups.get(i);
			if("角色".equals(group.get("type"))){
				Role role = roleRepository.findOne(group.get("id").toString());
				RoleAndAssignType roleAndAssignType = new RoleAndAssignType();
				roleAndAssignType.setAssignType(newProcessNodeAssignType);
				roleAndAssignType.setRole(role);
				roleAndAssignType.setLogic(group.get("logic").toString());
				roleAndAssignType.setSort(i+1);
				roleAndAssignTypeRepository.saveAndFlush(roleAndAssignType);
				
			}else if("部门".equals(group.get("type"))){
				Dept dept = deptRepository.findOne(group.get("id").toString());
				DeptAndAssignType deptAndAssignType = new DeptAndAssignType();
				deptAndAssignType.setAssignType(newProcessNodeAssignType);
				deptAndAssignType.setDept(dept);
				deptAndAssignType.setLogic(group.get("logic").toString());
				deptAndAssignType.setSort(i+1);
				deptAndAssignTypeRepository.saveAndFlush(deptAndAssignType);

			}else if("岗位".equals(group.get("type"))){
				Post post = postRepository.findOne(group.get("id").toString());
				PostAndAssignType postAndAssignType = new PostAndAssignType();
				postAndAssignType.setAssignType(newProcessNodeAssignType);
				postAndAssignType.setPost(post);
				postAndAssignType.setLogic(group.get("logic").toString());
				postAndAssignType.setSort(i+1);
				postAndAssignTypeRepository.saveAndFlush(postAndAssignType);
				
			}
			
		}
		if(groups!=null && groups.size()>0){
			newProcessNodeAssignType.setAssign(processNodeAssign);
			assignTypes.add(newProcessNodeAssignType);
		}else{
			processNodeAssignTypeRepository.delete(newProcessNodeAssignType);
		}
		
		processNodeAssign.setAssignTypes(assignTypes);
		processNodeAssignRepository.saveAndFlush(processNodeAssign);
		
		
		map.put("code", 1);
    	map.put("msg", "操作成功");
		return map;
	}

	@Override
	public Map<String, Object> getGroupByAssignTypeId(String typeId) {
		ProcessNodeAssignType processNodeAssignType = processNodeAssignTypeRepository.findOne(typeId);
		Map<String,Object> map = new HashMap<String, Object>();
		List<RoleAndAssignType> roles = roleAndAssignTypeRepository.findByAssignType(processNodeAssignType);
		List<DeptAndAssignType> depts = deptAndAssignTypeRepository.findByAssignType(processNodeAssignType);
		List<PostAndAssignType> posts = postAndAssignTypeRepository.findByAssignType(processNodeAssignType);
		map.put("roles", roles);
		map.put("depts", depts);
		map.put("posts", posts);
		
		return map;
	}

	@Override
	public List<ProcessNodeAssignType> getProcessNodeAssignTypeByAssign(String assignId) {
		ProcessNodeAssign processNodeAssign = processNodeAssignRepository.findOne(assignId);
		List<ProcessNodeAssignType> list = processNodeAssignTypeRepository.findByAssign(processNodeAssign);
		return list;
	}
	
	

	

}
