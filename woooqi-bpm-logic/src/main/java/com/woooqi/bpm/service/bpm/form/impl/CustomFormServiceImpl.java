package com.woooqi.bpm.service.bpm.form.impl;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.woooqi.bpm.service.bpm.form.CustomFormService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;

import com.titan.entity.bpm.form.CustomForm;
import com.titan.entity.bpm.form.CustomTable;
import com.titan.entity.bpm.form.CustomTableColumn;
import com.titan.repository.bpm.form.CustomFormRepository;
import com.titan.repository.bpm.form.CustomTableColumnRepository;
import com.titan.repository.bpm.form.CustomTableRepository;
import com.titan.service.bpm.form.CustomFormService;
import com.titan.utils.DataBaseUtils;
import com.titan.utils.FileUtils;
import com.titan.utils.PageUtils;

@Service
public class CustomFormServiceImpl implements CustomFormService {
	
	
	@Autowired
	private CustomFormRepository customFormRepository;
	
	@Autowired
	private CustomTableRepository customTableRepository;
	
	@Autowired
	private CustomTableColumnRepository customTableColumnRepository;
	
	public String oneColumn="<table border='1' cellpadding='0' cellspacing='1' style='width:600px'><tbody><tr><td colspan='2' style='text-align:center;background-color: #3366ff;'>{table.name}</td></tr>{tr}</tbody></table>";
	
	public String twoColumn="<table border='1' cellpadding='0' cellspacing='1' style='width:600px'><tbody><tr><td colspan='4' style='text-align:center;background-color: #3366ff;'>{table.name}</td></tr>{tr}</tbody></table>";
	
	public String threeColumn="<table border='1' cellpadding='0' cellspacing='1' style='width:600px'><tbody><tr><td colspan='6' style='text-align:center;background-color: #3366ff;'>{table.name}</td></tr>{tr}</tbody></table>";
	
	public String fourColumn="<table border='1' cellpadding='0' cellspacing='1' style='width:600px'><tbody><tr><td colspan='8' style='text-align:center;background-color: #3366ff;'>{table.name}</td></tr>{tr}</tbody></table>";
	
	//public  static final String td="<td>{td.name}</td><td>{td.type}</td>";
	
	

	@Override
	public Map<String, Object> saveCustomForm(CustomForm customForm) {
		Map<String,Object> map= new HashMap<>();
		try {
			customFormRepository.saveAndFlush(customForm);
			map.put("code", 1);
			map.put("msg", "操作成功");
			
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CustomForm getCustomFormById(String id) {
		List<CustomForm> lists =new ArrayList<>();
		try {
			JdbcTemplate jdbcTemplate = DataBaseUtils.getJdbcTemplate();
			final LobHandler lobHandler = DataBaseUtils.getLobHandler();
			String sql = "select id,columns,description,name from bpm_form where id=?";
			lists = jdbcTemplate.query(sql,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CustomForm customForm = new CustomForm();
					customForm.setId( rs.getString(1));
					customForm.setColumns(rs.getInt(2));
					customForm.setDescription(rs.getString(3));
					customForm.setName( rs.getString(4));
					return customForm;
				}
	        },id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(lists.size()!=0){
			CustomTable table = customTableRepository.findCustomTableById(id);
			lists.get(0).setTable(table);
		}
		return lists.get(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CustomForm> getAllCustomForm(int pageNumber, int pageSize) {
		List<CustomForm> lists =new ArrayList<>();
		
		try {
			JdbcTemplate jdbcTemplate = DataBaseUtils.getJdbcTemplate();
			final LobHandler lobHandler = DataBaseUtils.getLobHandler();
			String sql = "select id,columns,description,html,name,table_id from bpm_form";
			lists = jdbcTemplate.query(sql,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CustomForm customForm = new CustomForm();
					customForm.setId( rs.getString(1));
					customForm.setColumns(rs.getInt(2));
					customForm.setDescription(rs.getString(3));
					customForm.setName( rs.getString(5));
					customForm.setHtml(lobHandler.getBlobAsBytes(rs, 4).toString());
					return customForm;
				}
	        });
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lists;
	}

	@Override
	public Map<String, Object> delCustomForm(final String id) {
		Map<String,Object> map = new HashMap<>();
		
		try {			
			String sql = "delete from bpm_form where id =?";
			DataBaseUtils.update(sql, id);	
			map.put("code", 1);
	    	map.put("msg", "操作成功");
		}catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		return map;
	}


	@Override
	public String createForm(String formId, String name, String description,Integer columns, String tableId) {
		CustomTable table = customTableRepository.findOne(tableId);
		CustomForm form = new CustomForm();
		form.setColumns(columns);
		form.setDescription(description);
		form.setId(formId);
		form.setName(name);
		form.setTable(table);
		CustomForm saveAndFlushform = customFormRepository.saveAndFlush(form);
		return saveAndFlushform.getId();
	}

	@Override
	public Map<String, Object> saveForm(String formId, final String name, final String description, final Integer columns,final String tableId) {
		Map<String,Object> map = new HashMap<>();
		try {
			CustomTable table = customTableRepository.findOne(tableId);
			List<CustomTableColumn> cloumns = customTableColumnRepository.findByTableOrderBySortAsc(table);
			String html="";
			oneColumn = oneColumn.replace("{table.name}", name);
			twoColumn = twoColumn.replace("{table.name}", name);
			threeColumn = threeColumn.replace("{table.name}", name);
			fourColumn = fourColumn.replace("{table.name}", name);
			
			if(columns==1){
				String tr = "";
				if(cloumns==null || cloumns.size()==0){
					tr="<tr><td></td><td></td></tr>";
				}
				
				for(CustomTableColumn column:cloumns){
					tr+="<tr>";
					String td = getTd(column);
					tr += td;
					tr+="</tr>";
				}
				oneColumn = oneColumn.replace("{tr}",tr );
				html = oneColumn;
				
				
			}else if(columns==2){
				String tr = "";
				if(cloumns==null || cloumns.size()==0){
					tr="<tr><td></td><td></td><td></td><td></td></tr>";
				}
				for(int i=0;i<Math.ceil(cloumns.size()/2.0);i++){
					tr += "<tr>";
					if(2*i < cloumns.size()){
						tr += getTd(cloumns.get(2*i));
					}else{
						tr += "<td></td><td></td>";
					}
					if((2*i+1) < cloumns.size()){
						tr += getTd(cloumns.get(2*i+1));
					}else{
						tr += "<td></td><td></td>";
					}
					tr += "</tr>";
					
				}
				twoColumn = twoColumn.replace("{tr}",tr );
				html = twoColumn;
			}else if(columns==3){
				String tr = "";
				if(cloumns==null || cloumns.size()==0){
					tr="<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>";
				}
				
				for(int i=0;i<Math.ceil(cloumns.size()/3.0);i++){
					tr+="<tr>";
					if(3*i < cloumns.size()){
						tr += getTd(cloumns.get(3*i));
					}else{
						tr +="<td></td><td></td>";
					}
					if((3*i+1) < cloumns.size()){
						tr += getTd(cloumns.get(3*i+1));
					}else{
						tr +="<td></td><td></td>";
					}
					if((3*i+2) < cloumns.size()){
						tr += getTd(cloumns.get(3*i+2));
					}else{
						tr +="<td></td><td></td>";
					}
					tr+="</tr>";
					
				}
				
				threeColumn = threeColumn.replace("{tr}",tr );
				html = threeColumn;
				
			}else if(columns==4){
				
				String tr = "";
				if(cloumns==null || cloumns.size()==0){
					tr="<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>";
				}
				for(int i=0;i<Math.ceil(cloumns.size()/4.0);i++){
					tr+="<tr>";
					if(4*i < cloumns.size()){
						tr += getTd(cloumns.get(4*i));
					}else{
						tr +="<td></td><td></td>";
					}
					if((4*i+1) < cloumns.size()){
						tr += getTd(cloumns.get(4*i+1));
					}else{
						tr +="<td></td><td></td>";
					}
					if((4*i+2) < cloumns.size()){
						tr += getTd(cloumns.get(4*i+2));
					}else{
						tr +="<td></td><td></td>";
					}
					if((4*i+3) < cloumns.size()){
						tr += getTd(cloumns.get(4*i+3));
					}else{
						tr +="<td></td><td></td>";
					}
					tr+="</tr>";
					
				}
				fourColumn = fourColumn.replace("{tr}",tr );
				html = fourColumn;
			}
			
			final InputStream is = FileUtils.String2InputStream(html);
			
			
			CustomForm form = new CustomForm();
			if(!StringUtils.isNotEmpty(formId)){
				form.setId(PageUtils.getUUID());
				final String uuid = form.getId();
				final int size = html.length();
				
				JdbcTemplate jdbcTemplate = DataBaseUtils.getJdbcTemplate();
				LobHandler lobHandler = DataBaseUtils.getLobHandler(); 
				try {			
					String sql = "insert into bpm_form(id,columns,description,html,name,table_id) values(?,?,?,?,?,?)";
					if(is != null){
						jdbcTemplate.execute(sql,new AbstractLobCreatingPreparedStatementCallback(lobHandler){
							@Override
							protected void setValues(PreparedStatement pstmt, LobCreator lobCreator) throws SQLException, DataAccessException {
								try {
									pstmt.setString(1, uuid);
									pstmt.setInt(2, columns);
									pstmt.setString(3, description);
					                lobCreator.setBlobAsBinaryStream(pstmt,4,is,size); 
					                pstmt.setString(5,name);
					                pstmt.setString(6,tableId);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}
						});
					}
				}catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				
				//customFormRepository.saveCustomForm(form.getId(),columns, description, name, tableId);
			}else{
				
				form.setId(formId);
				final String uuid = form.getId();
				final int size = html.length();
				CustomForm customFormById = getCustomFormById(uuid);
				if(columns==customFormById.getColumns()&&name.equals(customFormById.getName())&&tableId.equals(customFormById.getTable().getId())){
				}else{
				JdbcTemplate jdbcTemplate = DataBaseUtils.getJdbcTemplate();
				LobHandler lobHandler = DataBaseUtils.getLobHandler(); 
					try {			
						String sql = "update bpm_form set columns=?,description=?,name=?,table_id=?,html=? where id =?";
						if(is != null){
							jdbcTemplate.execute(sql,new AbstractLobCreatingPreparedStatementCallback(lobHandler){
								@Override
								protected void setValues(PreparedStatement pstmt, LobCreator lobCreator) throws SQLException, DataAccessException {
									try {
										pstmt.setInt(1, columns);
										pstmt.setString(2, description);
										pstmt.setString(3, name);
										pstmt.setString(4,tableId);
						                lobCreator.setBlobAsBinaryStream(pstmt,5,is,size); 
						                pstmt.setString(6,uuid);
									} catch (Exception e) {
										e.printStackTrace();
									}
									
								}
							});
						}
					}catch (Exception e) {
						throw new RuntimeException(e.getMessage(), e);
					}
				}
				
				//customFormRepository.updateCustomForm(columns, description, name, tableId,form.getId());
			}
			form.setColumns(columns);
			form.setDescription(description);
			
			
			form.setName(name);
			form.setTable(table);
		   
			map.put("code", 1);
			map.put("data", form);
	    	map.put("msg", "操作成功");
			
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String,Object> getCustomFormHtmlById(String formId) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			JdbcTemplate jdbcTemplate = DataBaseUtils.getJdbcTemplate();
			final LobHandler lobHandler = DataBaseUtils.getLobHandler();
			String sql = "select id,columns,description,html,name,table_id from bpm_form t where t.id = ?";
			list = jdbcTemplate.query(sql,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					Map<String, Object> map = new HashMap<String, Object>();
					String name = rs.getString(5);
					map.put("name", name);
					byte[] html = lobHandler.getBlobAsBytes(rs, 4);
					InputStream byte2InputStream = FileUtils.byte2InputStream(html);
					String inputStream2String = FileUtils.inputStream2String(byte2InputStream);
					map.put("html", inputStream2String);
					return map;
				}
	        },formId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list.get(0);
		
		


	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CustomForm getCustomFormHtmlName(String name) {
		
		List<CustomForm> list = new ArrayList<>();
		try {
			JdbcTemplate jdbcTemplate = DataBaseUtils.getJdbcTemplate();
			final LobHandler lobHandler = DataBaseUtils.getLobHandler();
			String sql = "select id,columns,description,html,name from bpm_form t where t.name = ?";
			list = jdbcTemplate.query(sql,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CustomForm customForm = new CustomForm();
					customForm.setId(rs.getString(1));
					customForm.setColumns(rs.getInt(2));
					customForm.setDescription(rs.getString(3));
					byte[] html = lobHandler.getBlobAsBytes(rs, 4);
					InputStream byte2InputStream = FileUtils.byte2InputStream(html);
					String inputStream2String = FileUtils.inputStream2String(byte2InputStream);
					customForm.setHtml(inputStream2String);
					customForm.setName(rs.getString(5));
					return customForm;
				}
	        },name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list.get(0);
		
		


	}
	
	@Override
	public Map<String, Object> saveFormHtml(final String formId,String html) {
		Map<String,Object> map = new HashMap<>();
		try {
			final InputStream is = FileUtils.String2InputStream(html);
			final int size = html.length();
				JdbcTemplate jdbcTemplate = DataBaseUtils.getJdbcTemplate();
				LobHandler lobHandler = DataBaseUtils.getLobHandler(); 
				try {			
					String sql = "update bpm_form set html=? where id =?";
					if(is != null){
						jdbcTemplate.execute(sql,new AbstractLobCreatingPreparedStatementCallback(lobHandler){
							@Override
							protected void setValues(PreparedStatement pstmt, LobCreator lobCreator) throws SQLException, DataAccessException {
								try {
					                lobCreator.setBlobAsBinaryStream(pstmt,1,is,size); 
					                pstmt.setString(2,formId);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}
						});
					}
				}catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				
				//customFormRepository.updateCustomForm(columns, description, name, tableId,form.getId());		   
			map.put("code", 1);
	    	map.put("msg", "操作成功");
			
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	public static String getTd(CustomTableColumn column){
		String tr = "";
		if("text".equals(column.getType())){
			tr ="<td style='width:40%;'>"+column.getComments()+":</td><td><input name="+column.getField()+" id="+column.getField()+"  type='text' style='border: 0px;width: 100%;height: 100%;'/></td>";
		}else if("textarea".equals(column.getType())){
			tr = "<td>"+column.getComments()+"</td><td><textarea cols='30' name="+column.getField()+" id="+column.getField()+" rows='1'></textarea></td>";
		}else if("date".equals(column.getType())){
			tr = "<td>"+column.getComments()+"</td><td><input size='30' id="+column.getField()+" name="+column.getField()+" class='form-control timepicker'  /></td>";
		}else if("number".equals(column.getType())){
			tr = "<td>"+column.getComments()+"</td><td>input name="+column.getField()+" id="+column.getField()+"  type='text' /></td>";
		}
		return tr;
		
	}

}
