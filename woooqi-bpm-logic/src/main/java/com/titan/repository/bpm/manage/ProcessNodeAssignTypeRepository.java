package com.titan.repository.bpm.manage;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.bpm.manage.ProcessNodeAssign;
import com.titan.entity.bpm.manage.ProcessNodeAssignType;



public interface ProcessNodeAssignTypeRepository extends JpaRepository<ProcessNodeAssignType,String>{
	
	public List<ProcessNodeAssignType> findByAssign(ProcessNodeAssign assign);

	@Modifying
	@Query(value="insert into bpm_user_assign_type(type_id,user_id) values(?1,?2) ",nativeQuery=true)
	public void saveUserAssignType(String typeId,String userId);

	@Modifying
	@Query(value="insert into bpm_role_assign_type(id,type_id,role_id,logic) values(sys_guid(),?1,?2,?3) ",nativeQuery=true)
	public void saveRoleAssignType(String typeId, String roleId,String logic);
	
	@Modifying
	@Query(value="insert into bpm_dept_assign_type(id,type_id,dept_id,logic) values(sys_guid(),?1,?2,?3) ",nativeQuery=true)
	public void saveDeptAssignType(String typeId, String deptId,String logic);
	
	@Modifying
	@Query(value="insert into bpm_post_assign_type(id,type_id,post_id,logic) values(sys_guid(),?1,?2,?3) ",nativeQuery=true)
	public void savePostAssignType(String typeId, String postId,String logic);

	@Modifying
	@Query(value="delete bpm_role_assign_type where type_id = ?1",nativeQuery=true)
	public void delRoleAssignType(String type_id);

  

}
