package com.woooqi.bpm.repository.organization;

import java.util.List;

import com.woooqi.bpm.entity.organization.Dept;
import com.woooqi.bpm.entity.organization.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.organization.Dept;
import com.titan.entity.organization.Post;

public interface PostRepository extends JpaRepository<Post,String>,JpaSpecificationExecutor<Post>{

	public List<Post> findByDept(Dept dept);
	
	public List<Post> findByDeptAndStatus(Dept dept ,Integer status);
	
	public Post findByIdAndDeptId(String id,String deptId);
	
	public List<Post> findByNameAndDept(String name, Dept dept);
	
	@Query(value="select t.* from bpm_post t where t.dept_id =?1  and t.status = ?2 order by create_time desc",nativeQuery=true)
	public List<Post> findEnablePost(String deptId,Integer status);
	
	@Query(value="select t.* from bpm_post t where t.dept_id =?1 order by create_time desc",nativeQuery=true)
	public List<Post> findPostByDeptId(String deptId);
}
