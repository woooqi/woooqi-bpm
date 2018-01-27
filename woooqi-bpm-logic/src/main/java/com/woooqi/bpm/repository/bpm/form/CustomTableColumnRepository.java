package com.woooqi.bpm.repository.bpm.form;


import java.util.List;

import com.woooqi.bpm.entity.bpm.form.CustomTable;
import com.woooqi.bpm.entity.bpm.form.CustomTableColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.entity.bpm.form.CustomTable;
import com.titan.entity.bpm.form.CustomTableColumn;



public interface CustomTableColumnRepository extends JpaRepository<CustomTableColumn,String>{
	
	public List<CustomTableColumn> findByTableOrderBySortAsc(CustomTable table);

}
