package com.titan.repository.organization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.organization.User;

public interface UserRepository extends JpaRepository<User,String>{

	public User findByLoginName(String loginName);
	
	public List<User> findByNameContaining(String name);
	
	
	
	@Query(value="select t.* from  BPM_USER t where t.dept_id=?1 and t.status=?2 order by create_time asc",nativeQuery=true)
	public List<User> getUserByDeptId(String deptId,int status);
	 
	@Modifying
	@Query(value="insert into bpm_user_role(user_id,role_id) values(?1,?2) ",nativeQuery=true)
	public void saveUserRole(String userId,String roleId);
	
	@Modifying
	@Query(value="delete from bpm_user_role t where t.user_id = ?1",nativeQuery=true)
	public void delUserRole(String userId);
	
	@Modifying
	@Query(value="update bpm_user t set t.dept_id = '' where t.id = ?1",nativeQuery=true) 
	public void delUserDept(String id);
	
	@Modifying
	@Query(value="delete from bpm_user_role t where t.user_id = ?1",nativeQuery=true)
	public void delUser_Role(String id);
	
	@Modifying
	@Query(value="select * from bpm_user t where t.status = '1'",nativeQuery=true)
	public List<User> findUserEnable();

	@Query(value="select a.* from bpm_user a,bpm_user_assign_type b where a.id = b.user_id and b.type_id = ?1",nativeQuery=true)
	public List<User> getUserByAssignTypeId(String typeId);
	
	@Query(value="sselect a.* from  bpm_user a ,bpm_user_assign_type b, bpm_process_node_assign_type c,bpm_process_node_assign d where c.assign_id = d.id and b.type_id = c.id and  b.user_id = a.id and d.id=?1",nativeQuery=true)
	public List<User> getUserByAssignId(String assignId);
	
	@Query(value="select a.* from bpm_user a,bpm_user_sign b where a.id = b.user_id and b.sign_id = ?1",nativeQuery=true)
	public List<User> getUserBySignId(String signId);

	@Query(value="select a.id from bpm_user a,bpm_user_role b where a.id = b.user_id and b.role_id in(?1)",nativeQuery=true)
	public List<String> getUserByRoleIds(List<String> roleIds);
	
	@Query(value="select a.id from bpm_user a where a.dept_id in(?1)",nativeQuery=true)
	public List<String> getUserByDeptIds(List<String> deptIds);
	
	@Query(value="select a.id from bpm_user a where a.post_id in(?1)",nativeQuery=true)
	public List<String> getUserByPostIds(List<String> postIds);
	
	
	@Query(value="select a.id from bpm_user a,bpm_user_sign_special b where a.id = b.user_id  and b.special_id = ?1",nativeQuery=true)
	public List<String> getUserIdBySpecialId(String specialId);
	
	@Query(value="select a.id from bpm_user a,bpm_user_role b,bpm_role_sign_special c where a.id = b.user_id and b.role_id = c.role_id and c.special_id = ?1",nativeQuery=true)
	public List<String> getUserIdByRoleSpecialId(String specialId);
	
	@Query(value="select a.id from bpm_user a,bpm_dept_sign_special b where  a.dept_id = b.dept_id and b.special_id = ?1",nativeQuery=true)
	public List<String> getUserIdByDeptSpecialId(String specialId);
	
	@Query(value="select a.id from bpm_user a,bpm_post_sign_special b where a.post_id = b.post_id and c.special_id = ?1",nativeQuery=true)
	public List<String> getUserIdByPostSpecialId(String specialId);
	

	@Query(value="select a.id from bpm_user a,bpm_user_role b where a.id = b.user_id and b.role_id =?1",nativeQuery=true)
	public List<String> getUserByRoleId(String roleId);
	
	@Query(value="select a.id from bpm_user a where a.dept_id =?1",nativeQuery=true)
	public List<String> getUserByDeptId(String deptId);
	
	@Query(value="select a.id from bpm_user a where a.post_id =?1",nativeQuery=true)
	public List<String> getUserByPostId(String postId);
	
	
	
	
	

}
