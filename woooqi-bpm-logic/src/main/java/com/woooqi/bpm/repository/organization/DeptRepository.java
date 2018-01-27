package com.woooqi.bpm.repository.organization;

import java.util.List;
import java.util.Map;

import com.woooqi.bpm.entity.organization.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.organization.Dept;

public interface DeptRepository extends JpaRepository<Dept,String>,JpaSpecificationExecutor<Dept>{

	public Dept findByName(String name);
	
	public List<Dept> findByNameAndParentId(String mane,String parentId);
	
	@Query("select new map(t.id as id,t.parentId as pId,t.name as name) from Dept t order by sort asc")
	public List<Map<String,String>> getTreeDept();

	@Query(value="select t.* from bpm_dept t start with id='0' connect by prior id =  t.parent_id and t.status = 1 order by sort asc",nativeQuery=true)
	public List<Dept> getEnableDept();
	
}
