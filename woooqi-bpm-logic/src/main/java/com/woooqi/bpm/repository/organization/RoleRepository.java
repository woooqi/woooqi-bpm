package com.woooqi.bpm.repository.organization;

import java.util.List;
import java.util.Map;

import com.woooqi.bpm.entity.organization.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.organization.Role;

public interface RoleRepository extends JpaRepository<Role,String>{

	public Role findRoleByName(String name);
	
	public List<Role> findByNameContaining(String name);
	
	@Modifying
	@Query(value="delete from bpm_role_menu t where t.role_id = ?1",nativeQuery=true)
	public void delRoleMenu(String roleId);
	
	@Modifying
	@Query(value="insert into bpm_role_menu(id,role_id,menu_id,is_parent) values(sys_guid(),?1,?2,?3) ",nativeQuery=true)
	public void saveRoleMenu(String roleId,String menuId,String isParent);
	
	@Query("select new map(t.id as id,t.name as name) from Role t where t.status = '1'")
	public List<Map<String,String>> getTreeRole();
	
	
	@Query(value="select a.* from bpm_role a,bpm_user_role b where a.id = b.role_id and b.user_id = ?1",nativeQuery=true)
	public List<Role> getRoleByUserId(String userId);
	
	@Modifying
	@Query(value="select * from bpm_role t where t.status = '1'",nativeQuery=true)
	public List<Role> findRoleEnable();
	
	@Query(value="select a.* from bpm_role a,bpm_role_assign_type b where a.id = b.role_id and b.type_id = ?1 and a.status = ?2",nativeQuery=true)
	public List<Role> getRoleByAssignTypeId(String typeId,int status);
	
}
