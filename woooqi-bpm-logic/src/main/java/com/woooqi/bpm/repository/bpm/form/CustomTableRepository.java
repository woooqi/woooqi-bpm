package com.woooqi.bpm.repository.bpm.form;



import com.woooqi.bpm.entity.bpm.form.CustomTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.titan.entity.bpm.form.CustomTable;



public interface CustomTableRepository extends JpaRepository<CustomTable,String>{
	
	@Query(value="select b.* from bpm_form a,bpm_table b where a.id = ?1 and b.id = a.table_id",nativeQuery=true)
	public CustomTable findCustomTableById(String id);

}
