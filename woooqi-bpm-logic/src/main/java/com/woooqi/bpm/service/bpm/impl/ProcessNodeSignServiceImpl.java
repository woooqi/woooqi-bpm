package com.woooqi.bpm.service.bpm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.bpm.manage.ProcessNodeSign;
import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;
import com.titan.entity.organization.Dept;
import com.titan.entity.organization.DeptAndAssignType;
import com.titan.entity.organization.DeptAndSignSpecial;
import com.titan.entity.organization.Post;
import com.titan.entity.organization.PostAndAssignType;
import com.titan.entity.organization.PostAndSignSpecial;
import com.titan.entity.organization.Role;
import com.titan.entity.organization.RoleAndAssignType;
import com.titan.entity.organization.RoleAndSignSpecial;
import com.titan.entity.organization.User;
import com.titan.entity.organization.UserAndSignSpecial;
import com.titan.entity.web.Page;
import com.titan.repository.bpm.manage.ProcessNodeAssignRepository;
import com.titan.repository.bpm.manage.ProcessNodeAssignTypeRepository;
import com.titan.repository.bpm.manage.ProcessNodeSignRepository;
import com.titan.repository.bpm.manage.ProcessNodeSignSpecialRepository;
import com.titan.repository.organization.DeptAndAssignTypeRepository;
import com.titan.repository.organization.DeptAndSignSpecialRepository;
import com.titan.repository.organization.DeptRepository;
import com.titan.repository.organization.PostAndAssignTypeRepository;
import com.titan.repository.organization.PostAndSignSpecialRepository;
import com.titan.repository.organization.PostRepository;
import com.titan.repository.organization.RoleAndAssignTypeRepository;
import com.titan.repository.organization.RoleAndSignSpecialRepository;
import com.titan.repository.organization.RoleRepository;
import com.titan.repository.organization.UserAndSignSpecialRepository;
import com.titan.repository.organization.UserRepository;
import com.titan.service.bpm.ProcessNodeSignService;

@Service
public class ProcessNodeSignServiceImpl implements ProcessNodeSignService {
	
	@Autowired
	private ProcessNodeSignRepository processNodeSignRepository;
	
	
	@Autowired
	private ProcessNodeAssignRepository processNodeAssignRepository;
	
	@Autowired 
	private UserRepository userRepository;
	@Autowired 
	private DeptRepository deptRepository;
	@Autowired 
	private RoleRepository roleRepository;
	@Autowired 
	private PostRepository postRepository;
	
	@Autowired
	private ProcessNodeAssignTypeRepository processNodeAssignTypeRepository;
	
	@Autowired
	private RoleAndAssignTypeRepository roleAndAssignTypeRepository;
	
	@Autowired
	private DeptAndAssignTypeRepository deptAndAssignTypeRepository;

	@Autowired
	private PostAndAssignTypeRepository postAndAssignTypeRepository;
	
	@Autowired
	private UserAndSignSpecialRepository userAndSignSpecialRepository;
	
	@Autowired
	private RoleAndSignSpecialRepository roleAndSignSpecialRepository;
	
	@Autowired
	private DeptAndSignSpecialRepository deptAndSignSpecialRepository;

	@Autowired
	private PostAndSignSpecialRepository postAndSignSpecialRepository;
	
	@Autowired
	private ProcessNodeSignSpecialRepository processNodeSignSpecialRepository;
	
	

	@Override
	public Map<String,Object> getProcessNodeSignByActId(String activitiId) {
		ProcessNodeSign processNodeSign = processNodeSignRepository.findByActivitiId(activitiId);
		Map<String,Object> map  = new HashMap<String, Object>();
		if(processNodeSign==null){
			processNodeSign = new ProcessNodeSign();
		}
		map.put("sign", processNodeSign);
		
		return map;
	}

	@Override
	public Map<String, Object> savaAndFlushProcessNodeSignUser(ProcessNodeSign processNodeSign, List<Map<String, Object>> list) {
		Map<String,Object> map = new HashMap<String, Object>();
		Set<User> sets = new HashSet<User>();
		ProcessNodeSign saveProcessNodeSign = processNodeSignRepository.saveAndFlush(processNodeSign);
		if(saveProcessNodeSign==null){
			map.put("code", -1);
			return map;
		}
		
		if(list!=null && list.size()!=0){
			for(int i=0;i<list.size();i++){
				User user = new User();
				user =  userRepository.findOne(list.get(i).get("id")==null?"":list.get(i).get("id").toString());
				//user.setWeight(list.get(i).get("weight")==null?"":list.get(i).get("weight").toString());
				userRepository.saveAndFlush(user);
				sets.add(user);
				processNodeSignRepository.saveUsersign(saveProcessNodeSign.getId(), user.getId());
			}
		}
		processNodeSign.setId(saveProcessNodeSign.getId());
		//processNodeSign.setUsers(sets);
		
		processNodeSignRepository.saveAndFlush(processNodeSign);
		
		map.put("code",1);
		map.put("msg", "操作成功");
		
		
		return map;
	}

	@Override
	public Page<User> getUserSignByActId(String activitiId) {
		Page<User> page = new Page<User>();
			ProcessNodeAssign processNodeAssign  = processNodeAssignRepository.findByActivitiId(activitiId);
			List<ProcessNodeAssignType> processNodeAssignTypes = processNodeAssignTypeRepository.findByAssign(processNodeAssign);
			List<User> lists = new ArrayList<User>();
			Set<User> sets = new HashSet<User>();
			for(ProcessNodeAssignType p : processNodeAssignTypes){
				if("1".equals(p.getType())){
					List<User> users = userRepository.getUserByAssignTypeId(p.getId());
					sets.addAll(users);
				}else if("2".equals(p.getType())){
					List<User> users = userRepository.getUserByAssignTypeId(p.getId());
					sets.addAll(users);
				}
			}
			lists.addAll(sets);
			page.setCode(1);
			page.setRows(lists);
			page.setTotal(lists.size());
		
		
		return page;
	}

	@Override
	public Page<Role> getRoleSignByActId(String activitiId) {
		Page<Role> page = new Page<Role>();
		ProcessNodeAssign processNodeAssign  = processNodeAssignRepository.findByActivitiId(activitiId);
		List<ProcessNodeAssignType> processNodeAssignTypes = processNodeAssignTypeRepository.findByAssign(processNodeAssign);
		List<Role> lists = new ArrayList<Role>();
		for(ProcessNodeAssignType p : processNodeAssignTypes){
			 if("3".equals(p.getType())){
					List<RoleAndAssignType> roles = roleAndAssignTypeRepository.findByAssignType(p);
					for(RoleAndAssignType roleAndAssignType:roles){
						if(roleAndAssignType.getRole()!=null){
							lists.add(roleAndAssignType.getRole());
						}
						
					}
			 }
			
		}

		page.setCode(1);
		page.setRows(lists);
		page.setTotal(lists.size());
	
	
	return page;
	}

	@Override
	public Page<Dept> getDeptSignByActId(String activitiId) {
		Page<Dept> page = new Page<Dept>();
		ProcessNodeAssign processNodeAssign  = processNodeAssignRepository.findByActivitiId(activitiId);
		List<ProcessNodeAssignType> processNodeAssignTypes = processNodeAssignTypeRepository.findByAssign(processNodeAssign);
		List<Dept> lists = new ArrayList<Dept>();

		for(ProcessNodeAssignType p : processNodeAssignTypes){
			 if("3".equals(p.getType())){
					List<DeptAndAssignType> depts = deptAndAssignTypeRepository.findByAssignType(p);
					for(DeptAndAssignType deptAndAssignType:depts){
						if(deptAndAssignType.getDept()!=null){
							lists.add(deptAndAssignType.getDept());
						}
						
					}
			 }
			
		}

		
		page.setCode(1);
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}

	@Override
	public Page<Post> getPostSignByActId(String activitiId) {
		Page<Post> page = new Page<Post>();
		ProcessNodeAssign processNodeAssign  = processNodeAssignRepository.findByActivitiId(activitiId);
		List<ProcessNodeAssignType> processNodeAssignTypes = processNodeAssignTypeRepository.findByAssign(processNodeAssign);
		List<Post> lists = new ArrayList<Post>();

		for(ProcessNodeAssignType p : processNodeAssignTypes){
			 if("3".equals(p.getType())){
					List<PostAndAssignType> posts = postAndAssignTypeRepository.findByAssignType(p);
					for(PostAndAssignType postAndAssignType:posts){
						if(postAndAssignType.getPost()!=null){
							lists.add(postAndAssignType.getPost());
						}
						
					}
			 }
			
		}

		page.setCode(1);
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}

	@Override
	public List<ProcessNodeSignSpecial> getProcessNodeSignSpecialBySign(String signId) {
		ProcessNodeSign processNodeSign = processNodeSignRepository.findOne(signId);
		List<ProcessNodeSignSpecial> findByAssign = processNodeSignSpecialRepository.findBySign(processNodeSign);
		return findByAssign;
	}

	

	@Override
	public Map<String, Object> savaAndFlushProcessNodeSign(ProcessNodeSign processNodeSign) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ProcessNodeSign saveProcessNodeSign = processNodeSignRepository.saveAndFlush(processNodeSign);
			if(saveProcessNodeSign==null){
				map.put("code", -1);
				return map;
			}
			map.put("code",1);
			map.put("msg", "操作成功");
			return map;
		} catch (Exception e) {
			map.put("code", -1);
			return map;
		}
		
	}

	@Override
	public Map<String, Object> savaAndFlushProcessNodeSignSpecial(String signId,String type,List<Map<String,Object>> signSpecial) {
		Map<String,Object> map = new HashMap<String, Object>(); 
		ProcessNodeSign processNodeSign = processNodeSignRepository.findOne(signId);
		List<ProcessNodeSignSpecial> before = processNodeSignSpecialRepository.findBySign(processNodeSign);
		Set<ProcessNodeSignSpecial> signSpecials =new HashSet<ProcessNodeSignSpecial>();
		if(processNodeSign==null){
			map.put("code", -1);
			map.put("msg", "请先设置常规规则");
		}
		if(before !=null){
			for(ProcessNodeSignSpecial processNodeSignSpecial:before){
				if(type.equals(processNodeSignSpecial.getType())){
					before.remove(processNodeSignSpecial);
					processNodeSignSpecial.getUsers().clear();
					processNodeSignSpecial.getRoles().clear();
					processNodeSignSpecial.getDepts().clear();
					processNodeSignSpecial.getPosts().clear();
					processNodeSignSpecialRepository.delete(processNodeSignSpecial);
					break;
				}
			}
			
		}
		ProcessNodeSignSpecial processNodeSignSpecial = new ProcessNodeSignSpecial();
		processNodeSignSpecial.setType(type);
		ProcessNodeSignSpecial newProcessNodeSignSpecial = processNodeSignSpecialRepository.saveAndFlush(processNodeSignSpecial);

		for(int i=0;i<signSpecial.size();i++){
			Map<String,Object> group = signSpecial.get(i);
			if("user".equals(group.get("type"))){
				User user = userRepository.findOne(group.get("id").toString());
				UserAndSignSpecial userAndSignSpecial = new UserAndSignSpecial();
				userAndSignSpecial.setSignSpecial(newProcessNodeSignSpecial);
				userAndSignSpecial.setUser(user);
				userAndSignSpecial.setDecision(group.get("oneType").toString());
				userAndSignSpecialRepository.saveAndFlush(userAndSignSpecial);
				
			}else if("role".equals(group.get("type"))){
				Role role = roleRepository.findOne(group.get("id").toString());
				RoleAndSignSpecial roleAndSignSpecial = new RoleAndSignSpecial();
				roleAndSignSpecial.setSignSpecial(newProcessNodeSignSpecial);
				roleAndSignSpecial.setRole(role);
				roleAndSignSpecial.setDecision(group.get("oneType").toString());
				roleAndSignSpecialRepository.saveAndFlush(roleAndSignSpecial);
				
			}else if("dept".equals(group.get("type"))){
				Dept dept = deptRepository.findOne(group.get("id").toString());
				DeptAndSignSpecial deptAndSignSpecial = new DeptAndSignSpecial();
				deptAndSignSpecial.setSignSpecial(newProcessNodeSignSpecial);
				deptAndSignSpecial.setDept(dept);
				deptAndSignSpecial.setDecision(group.get("oneType").toString());
				deptAndSignSpecialRepository.saveAndFlush(deptAndSignSpecial);

			}else if("post".equals(group.get("type"))){
				Post post = postRepository.findOne(group.get("id").toString());
				PostAndSignSpecial postAndSignSpecial = new PostAndSignSpecial();
				postAndSignSpecial.setSignSpecial(newProcessNodeSignSpecial);
				postAndSignSpecial.setPost(post);
				postAndSignSpecial.setDecision(group.get("oneType").toString());
				postAndSignSpecialRepository.saveAndFlush(postAndSignSpecial);
				
			}
			
		}
		
		if(signSpecial.size()>0){
			newProcessNodeSignSpecial.setSign(processNodeSign);
			before.add(newProcessNodeSignSpecial);
			signSpecials.addAll(before);
		}else{
			processNodeSignSpecialRepository.delete(newProcessNodeSignSpecial);
		}
		
		processNodeSign.setSignSpecials(signSpecials);
		processNodeSignRepository.saveAndFlush(processNodeSign);
		
		
		map.put("code", 1);
    	map.put("msg", "操作成功");
		return map;
		
	}

	@Override
	public Map<String, Object> getGroupBySignSpecialId(String specialId) {
		ProcessNodeSignSpecial processNodeSignSpecial = processNodeSignSpecialRepository.findOne(specialId);
		Map<String,Object> map = new HashMap<String, Object>();
		List<UserAndSignSpecial> users = userAndSignSpecialRepository.findBySignSpecial(processNodeSignSpecial);
		List<RoleAndSignSpecial> roles = roleAndSignSpecialRepository.findBySignSpecial(processNodeSignSpecial);
		List<DeptAndSignSpecial> depts = deptAndSignSpecialRepository.findBySignSpecial(processNodeSignSpecial);
		List<PostAndSignSpecial> posts = postAndSignSpecialRepository.findBySignSpecial(processNodeSignSpecial);
		
		map.put("users", users);
		map.put("roles", roles);
		map.put("depts", depts);
		map.put("posts", posts);
		
		return map;
	}
	

	

}
