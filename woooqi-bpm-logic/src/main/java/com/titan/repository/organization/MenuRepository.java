package com.titan.repository.organization;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.organization.Menu;

public interface MenuRepository extends JpaRepository<Menu, String> ,JpaSpecificationExecutor<Menu>{
	
	public Menu findByName(String name);
	
	public List<Menu> findByNameAndParentIdAndUrl(String name,String parnetId,String url);
	
	@Query("select new map(t.id as id,t.parentId as pId,t.name as name) from Menu t order by sort asc")
	public List<Map<String,Object>> getTreeMenu();
	
	@Query(value="select a.* from bpm_menu a,bpm_role_menu b where a.id = b.menu_id and b.role_id = ?1 and b.is_parent = '1'",nativeQuery=true)
	public List<Menu> getMenuByRoleId(String roleId);
	
	@Query(value="select  t.* from bpm_menu t start with id='0' connect by prior id =  t.parent_id and t.status = 1 order siblings by sort asc",nativeQuery=true)
	public List<Menu> getEnableMenu();
	
	@Query(value="select * from bpm_menu b where b.id in(select a.menu_id from bpm_role_menu a where a.role_id in ?1) order by b.sort,b.id",nativeQuery=true)
	public List<Menu> getAllRoleMenu(List<String> roleIds);
	
}