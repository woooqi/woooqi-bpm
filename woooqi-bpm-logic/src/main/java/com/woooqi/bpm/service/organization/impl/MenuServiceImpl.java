package com.woooqi.bpm.service.organization.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.entity.organization.Menu;
import com.titan.repository.organization.MenuRepository;
import com.titan.service.organization.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public Menu getByName(String name) {
		return menuRepository.findByName(name);
	}

	@Override
	public List<Menu> getAllMenuByName(int pageNumber, int pageSize,final String parentId) {
		Page<Menu> menus= null;
		Sort sort = new Sort(Sort.Direction.ASC, "sort");
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize, sort);
		
			Specification<Menu> specification = new Specification<Menu>() {
	
				@Override
				public Predicate toPredicate(Root<Menu> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				//	Path<String> _name = root.get("name");
					Path<String> _parentId = root.get("parentId");
					List<Predicate> list = new ArrayList<Predicate>();
					
					list.add(cb.equal(_parentId,parentId));
					Predicate[] p = new Predicate[list.size()]; 
					return cb.and(list.toArray(p));
				}
				
			};
			
			menus = menuRepository.findAll(specification,pageRequest);
		
		return menus.getContent();
	}

	@Override
	public Menu getMenuById(String id) {
		Menu menu = menuRepository.findOne(id);
		return menu;
	}

	@Override
	public List<Map<String, Object>> getTreeMenu() {
		List<Map<String,Object>> lists = menuRepository.getTreeMenu();
		return lists;
	}

	@Override
	public void delById(String id) {
		menuRepository.delete(id);
		
	}

	@Override
	public Map<String, Object> save(String name, String url_name,String parentId,int status,int sort,String stamp,String image) {
		 Map<String,Object>  map = new HashMap<String, Object>();
			Menu menu = new Menu();
			menu.setCreateTime(new Date());
			menu.setName(name);
			menu.setParentId(parentId);
			menu.setStatus(status);
			menu.setUrl(url_name);
			menu.setSort(sort);
			menu.setStamp(stamp);
			menu.setImage(image);
		    Menu saveMenu = menuRepository.saveAndFlush(menu);
		    if(saveMenu!=null){
		    	map.put("code", 1);
		    	map.put("msg", "操作成功");
		    }else{
		    	map.put("code", -1);
		    	map.put("msg", "操作失败");
		    }
		return map;
	}

	@Override
	public Map<String, Object> update(String id, String name, String url_name,String parentId,int status,int sort,String stamp,String image) {
		Map<String,Object>  map = new HashMap<String, Object>();
			Menu menu = new Menu();
			menu.setId(id);
			menu.setCreateTime(new Date());
			menu.setName(name);
			menu.setParentId(parentId);
			menu.setStatus(status);
			menu.setSort(sort);
			menu.setUrl(url_name);
			menu.setStamp(stamp);
			menu.setImage(image);
		    Menu saveMenu = menuRepository.saveAndFlush(menu);
		    if(saveMenu!=null){
		    	map.put("code", 1);
		    	map.put("msg", "操作成功");
		    }else{
		    	map.put("code", -1);
		    	map.put("msg", "操作失败");
		    }
		return map;
	}

	@Override
	public List<Menu> getMenuByRoleId(String roleId) {
		return menuRepository.getMenuByRoleId(roleId);
	}
	
	@Override
	public List<Map<String, Object>> getEnableMenu() {
		List<Map<String,Object>> treeMenus = new ArrayList<Map<String,Object>>();
		List<Menu> lists = menuRepository.getEnableMenu();
		if(lists != null && lists.size() != 0){
			for(Menu menu:lists){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", menu.getId());
			map.put("pId", menu.getParentId());
			map.put("name", menu.getName());
			treeMenus.add(map);
			}
			return treeMenus;
			
			
		}
		return null;
	}

	@Override
	public Map<String, Object> getAllRoleMenu(List<String> roleIds) {
		List<Menu> menus = menuRepository.getAllRoleMenu(roleIds);
		Map<String, Object> orders = new HashMap<String, Object>();
		if(menus.size() > 0){
			Menu menu = menus.get(0);
			orders = getTreeRoleMenu(menu,menus);
		}
		return orders;
	}

	private Map<String, Object> getTreeRoleMenu(Menu parent,List<Menu> menus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", parent.getId());
		map.put("stamp", parent.getStamp());
		map.put("image", parent.getImage());
		map.put("name", parent.getName());
		map.put("url", parent.getUrl()==null?"":parent.getUrl());
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		for(int i = 0;i<menus.size();i++){
			Menu menu = menus.get(i);
			if(menu.getParentId() != null){
				if(menu.getParentId().equals(parent.getId())){
					list.add(getTreeRoleMenu(menu,menus));
				}
			}
		}
		
		map.put("child", list);
		
		return map;
		
	}

	@Override
	public Map<String, Object> getSubRoleMenu(List<String> roleIds, String id) {
		Map<String, Object> tree = new HashMap<String, Object>();
		List<Menu> menus = menuRepository.getAllRoleMenu(roleIds);
		if(menus.size()>0){
			Menu menu = menus.get(0);
			
			Map<String, Object> trees = getTreeRoleMenu(menu,menus);
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) trees.get("child");
			
			for(int i = 0;i<list.size();i++){
				Map<String, Object> map = list.get(i);
				if(map.get("id").toString().equals(id)){
					tree = map;
					break;
				}
			}
		}
		
		return tree;
	}


	

}
