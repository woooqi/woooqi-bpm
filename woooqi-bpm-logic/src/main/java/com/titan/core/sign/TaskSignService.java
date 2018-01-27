package com.titan.core.sign;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.titan.core.cmd.BaseTaskCommand;
import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;
import com.titan.entity.bpm.manage.ProcessNodeSign;
import com.titan.entity.bpm.manage.ProcessNodeSignSpecial;
import com.titan.entity.organization.DeptAndAssignType;
import com.titan.entity.organization.DeptAndSignSpecial;
import com.titan.entity.organization.PostAndAssignType;
import com.titan.entity.organization.PostAndSignSpecial;
import com.titan.entity.organization.RoleAndAssignType;
import com.titan.entity.organization.RoleAndSignSpecial;
import com.titan.entity.organization.User;
import com.titan.entity.organization.UserAndSignSpecial;
import com.titan.repository.bpm.manage.ProcessNodeAssignTypeRepository;
import com.titan.repository.bpm.manage.ProcessNodeSignRepository;
import com.titan.repository.bpm.manage.ProcessNodeSignSpecialRepository;
import com.titan.repository.organization.DeptAndAssignTypeRepository;
import com.titan.repository.organization.DeptAndSignSpecialRepository;
import com.titan.repository.organization.PostAndAssignTypeRepository;
import com.titan.repository.organization.PostAndSignSpecialRepository;
import com.titan.repository.organization.RoleAndAssignTypeRepository;
import com.titan.repository.organization.RoleAndSignSpecialRepository;
import com.titan.repository.organization.UserAndSignSpecialRepository;
import com.titan.repository.organization.UserRepository;
import com.titan.service.bpm.NodeManageService;
import com.titan.service.bpm.ProcessNodeAssignService;
import com.titan.service.bpm.ProcessNodeSignService;
import com.titan.service.organization.RoleService;
import com.titan.service.organization.UserService;
import com.titan.utils.PageUtils;



public class TaskSignService {
	
	@Autowired
	private ProcessNodeAssignService processNodeAssignService;
	
	@Autowired
	private ProcessNodeSignService processNodeSignService;
	
	@Autowired
	private ProcessNodeAssignTypeRepository processNodeAssignTypeRepository;
	
	@Autowired
	private RoleAndAssignTypeRepository roleAndAssignTypeRepository;
	
	@Autowired
	private DeptAndAssignTypeRepository deptAndAssignTypeRepository;

	@Autowired
	private PostAndAssignTypeRepository postAndAssignTypeRepository;
	
	@Autowired
	private ProcessNodeSignRepository processNodeSignRepository;
	
	@Autowired
	private UserAndSignSpecialRepository userAndSignSpecialRepository;
	
	@Autowired
	private RoleAndSignSpecialRepository roleAndSignSpecialRepository;
	
	@Autowired
	private DeptAndSignSpecialRepository deptAndSignSpecialRepository;

	@Autowired
	private PostAndSignSpecialRepository postAndSignSpecialRepository;
	
	@Autowired
	private NodeManageService nodeMangeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ProcessNodeSignSpecialRepository processNodeSignSpecialRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private BaseTaskCommand baseTaskCommand;
	
	@Autowired 
	private RuntimeService runtimeService;
	
	@Autowired 
	private TaskService taskService;

	public List<String> getSignUser(ActivityExecution execution){
		
		String activityId = execution.getCurrentActivityId();
		
		ProcessNodeAssign processNodeAssign  = processNodeAssignService.getNodeManageAssignByActId(activityId);
		List<ProcessNodeAssignType> processNodeAssignTypes = processNodeAssignTypeRepository.findByAssign(processNodeAssign);
		List<String> signUsers = new ArrayList<>();
		Set<String> sets = new HashSet<>();
		for(ProcessNodeAssignType p : processNodeAssignTypes){
			if("1".equals(p.getType())){
				List<User> users = userService.getUserByAssignTypeId(p.getId());
				if(users!=null && users.size()!=0){
					sets.add(users.get(0).getId());
				}
			}else if("2".equals(p.getType())){
				List<User> users = userService.getUserByAssignTypeId(p.getId());
				List<String> lists = new ArrayList<String>();
				for(User u:users){
					lists.add(u.getId());
				}
				if(lists!=null&&lists.size()!=0){
					sets.addAll(lists);
				}
				
			}else if("3".equals(p.getType())){
				List<RoleAndAssignType> roleAndAssignTypes = roleAndAssignTypeRepository.findByAssignType(p);
				List<DeptAndAssignType> deptAndAssignTypes = deptAndAssignTypeRepository.findByAssignType(p);
				List<PostAndAssignType> postAndAssignTypes = postAndAssignTypeRepository.findByAssignType(p);
				List<String> roleIds = new ArrayList<String>();
				List<String> postIds = new ArrayList<String>();
				List<String> deptIds = new ArrayList<String>();
				for(RoleAndAssignType roleAndAssignType:roleAndAssignTypes){
					if(roleAndAssignType.getRole()!=null){
						roleIds.add(roleAndAssignType.getRole().getId());
					}
					
				}
				if(roleIds.size()!=0){
					List<String> userByRoleIds = userService.getUserByRoleIds(roleIds);
					if(userByRoleIds!=null){
						sets.addAll(userByRoleIds);
					}
				}
				
				for(DeptAndAssignType deptAndAssignType:deptAndAssignTypes){
					if(deptAndAssignType.getDept()!=null){
						deptIds.add(deptAndAssignType.getDept().getId());
					}
					
				}
				if(deptIds.size()!=0){
					List<String> lists = userService.getUserByDeptIds(deptIds);
					if(lists!=null){
						sets.addAll(lists);
					}
				}
				for(PostAndAssignType postAndAssignType:postAndAssignTypes){
					if(postAndAssignType.getPost()!=null){
						postIds.add(postAndAssignType.getPost().getId());
					}
				}
				if(postIds.size()!=0){
					List<String> lists = userService.getUserByPostIds(postIds);
					if(lists!=null){
						sets.addAll(lists);
					}
				}
			}
		}
		if(sets.size()!=0){
			signUsers.addAll(sets);
			return signUsers;
		}
		
		return null;
	}
	
	public Boolean isComplete(ActivityExecution execution){
		
		return getComplete(execution.getId(),execution.getCurrentActivityId());
		
	}
	
	public Boolean getComplete(String executeId,String activityId){
		Boolean isComplete = false;
		String nrOfResult = "";
		
		ProcessNodeSign processNodeSign = processNodeSignRepository.findByActivitiId(activityId);
		List<ProcessNodeSignSpecial> findByAssign = processNodeSignSpecialRepository.findBySign(processNodeSign);
		
		User user = PageUtils.getCurrentUser();
		//会签次数
		Integer completeCounter = (Integer)runtimeService.getVariable(executeId, "nrOfCompletedInstances");
		//实例总数
		Integer instanceOfNumbers = (Integer)runtimeService.getVariable(executeId, "nrOfInstances"); 
		//通过票 
		Integer signins = runtimeService.getVariable(executeId, "nrOfSignins")==null?0:Integer.parseInt(runtimeService.getVariable(executeId, "nrOfSignins").toString());
		//反对票 
		Integer signouts = runtimeService.getVariable(executeId, "nrOfSignouts")==null?0:Integer.parseInt(runtimeService.getVariable(executeId, "nrOfSignouts").toString());
		
		if(processNodeSign==null){
			if(signins+signouts==instanceOfNumbers){
				if(signins>=signouts){
					isComplete = true;
					nrOfResult = "pass";
				}else{
					isComplete = true;
					nrOfResult="oppose";
				}
				
			}
			
			
		}else{
		//有没有配置特权
			if(findByAssign!=null && findByAssign.size()!=0){
				for(ProcessNodeSignSpecial processNodeSignSpecial:findByAssign){
					//1 一票制
					if("1".equals(processNodeSignSpecial.getType())){
						List<UserAndSignSpecial> userAndSignSpecials = userAndSignSpecialRepository.findBySignSpecial(processNodeSignSpecial);
						List<RoleAndSignSpecial> roleAndSignSpecials = roleAndSignSpecialRepository.findBySignSpecial(processNodeSignSpecial);
						List<DeptAndSignSpecial> deptAndSignSpecials = deptAndSignSpecialRepository.findBySignSpecial(processNodeSignSpecial);
						List<PostAndSignSpecial> postAndSignSpecials = postAndSignSpecialRepository.findBySignSpecial(processNodeSignSpecial);
						for(UserAndSignSpecial userAndSignSpecial:userAndSignSpecials){
							if(userAndSignSpecial.getUser()!=null && user.getId().equals(userAndSignSpecial.getUser().getId())){
								isComplete = true;
							}
							if("1".equals(userAndSignSpecial.getDecision())){
								nrOfResult = "pass";
							}else{
								nrOfResult="oppose";
							}
						}
						for(RoleAndSignSpecial roleAndSignSpecial:roleAndSignSpecials){
							if(roleAndSignSpecial.getRole()!=null && user.getId().equals(roleAndSignSpecial.getRole().getId())){
								List<String> userByRoleIds = userRepository.getUserByRoleId(roleAndSignSpecial.getRole().getId());
								if(userByRoleIds.contains(user.getId())){
									isComplete = true;
								}
								if("1".equals(roleAndSignSpecial.getDecision())){
									nrOfResult = "pass";
								}else{
									nrOfResult="oppose";
								}
							}
						}
						for(DeptAndSignSpecial deptAndSignSpecial:deptAndSignSpecials){
							if(deptAndSignSpecial.getDept()!=null && user.getId().equals(deptAndSignSpecial.getDept().getId())){
								List<String> userByRoleIds = userRepository.getUserByDeptId(deptAndSignSpecial.getDept().getId());
								
								if(userByRoleIds.contains(user.getId())){
									isComplete = true;
								}	
								if("1".equals(deptAndSignSpecial.getDecision())){
									nrOfResult = "pass";
								}else{
									nrOfResult="oppose";
								}
							}
						}
						for(PostAndSignSpecial postAndSignSpecial:postAndSignSpecials){
							if(postAndSignSpecial.getPost()!=null && user.getId().equals(postAndSignSpecial.getPost().getId())){
								List<String> userByRoleIds = userRepository.getUserByPostId(postAndSignSpecial.getPost().getId());
								if(userByRoleIds.contains(user.getId())){
									isComplete = true;
								}
								if("1".equals(postAndSignSpecial.getDecision())){
									nrOfResult = "pass";
								}else{
									nrOfResult="oppose";
								}
								
							}
						}
					}
				}
			}
			if("".equals(nrOfResult)){
			
				Integer ballot = processNodeSign.getBallot()==null?-1:Integer.parseInt(processNodeSign.getBallot());
				//没有填值全部投票
				if(ballot == -1){
					isComplete = instanceOfNumbers.equals(completeCounter) ;
					if("1".equals(processNodeSign.getDecision_type())){
							if(signins==instanceOfNumbers){
								nrOfResult = "pass";
							}else{
								nrOfResult="oppose";
							}
					}else{
						if(signouts==instanceOfNumbers){
							nrOfResult = "pass";
						}else{
							nrOfResult="oppose";
						}
						
					}
					
				}else{
					// 票数
					if("1".equals(processNodeSign.getVote_type())){
						if("1".equals(processNodeSign.getDecision_type())){
							if(signins>=ballot){
								isComplete = true;
								nrOfResult = "pass";
							}
							
						}else{
							if(signouts>=ballot){
								isComplete = true;
								nrOfResult="oppose";
							}
						}
						
					}else{// 百分比
						if(completeCounter!=null && instanceOfNumbers!=null){
							if("1".equals(processNodeSign.getDecision_type())){
								if((float)signins/instanceOfNumbers*100 < ballot){
									nrOfResult = "oppose";
								}
								
							}else{
								if((float)signouts/instanceOfNumbers*100 < ballot){
									nrOfResult="pass";
								}
							}
						}
						
					}
				}
				if(completeCounter.equals(instanceOfNumbers)){
					isComplete = true;
					if("1".equals(processNodeSign.getVote_type())){
						if("1".equals(processNodeSign.getDecision_type())){
							if(signins < ballot){
								nrOfResult = "oppose";
							}
							
						}else{
							if(signouts < ballot){
								nrOfResult="pass";
							}
						}
						
					}else{// 百分比
						if(completeCounter!=null && instanceOfNumbers!=null){
							if("1".equals(processNodeSign.getDecision_type())){
								if((float)signins/instanceOfNumbers*100 >= ballot){
									isComplete = true;
									nrOfResult = "pass";
								}
								
							}else{
								if((float)signouts/instanceOfNumbers*100 >= ballot){
									isComplete = true;
									nrOfResult="oppose";
								}
							}
						}
						
					}
					
				}
			}
		}
		if(isComplete){
			//pass 通过 , oppose 反对
			runtimeService.setVariable(executeId, "nrOfResult", nrOfResult);
		}
		return isComplete;
	}
	
}
