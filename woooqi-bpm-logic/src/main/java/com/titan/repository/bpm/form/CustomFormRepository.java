package com.titan.repository.bpm.form;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.bpm.form.CustomForm;



public interface CustomFormRepository extends JpaRepository<CustomForm,String>{
	
	@Modifying
	@Query(value="insert into bpm_form(id,columns,description,name,table_id) values(?1,?2,?3,?4,?5) ",nativeQuery=true)
	public void saveCustomForm(String id,Integer columns,String description,String name,String tableId);
	
	@Modifying
	@Query(value="update bpm_form set columns=?1,description=?2,name=?3,table_id=?4 where id =?5 ",nativeQuery=true)
	public void updateCustomForm(Integer columns,String description,String name,String tableId,String id);
	
	@Query(value="select a.id,a.columns,a.description,a.name from bpm_form a where a.id = ?1",nativeQuery=true)
	public CustomForm findCustomFormById(String id);

}
