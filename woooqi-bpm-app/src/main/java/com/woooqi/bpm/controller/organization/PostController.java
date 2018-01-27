package com.woooqi.bpm.controller.organization;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woooqi.bpm.entity.organization.Post;
import com.woooqi.bpm.entity.web.Page;
import com.woooqi.bpm.service.organization.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.entity.organization.Post;
import com.titan.entity.web.Page;
import com.titan.service.organization.PostService;

@RestController
@RequestMapping("post")
public class PostController {

	@Autowired
	private PostService postService;
	
	@RequestMapping("getEnableByDeptId")
	@ResponseBody
	public List<Post> getEnableByDeptId(HttpServletRequest request, HttpServletResponse response) {
		Integer status = 1;
		String deptId = request.getParameter("deptId")==null?"0":request.getParameter("deptId");
		List<Post> lists = postService.getEnableByDeptId(deptId,status);
		return lists;
	}

	@RequestMapping("getPostByDeptId")
	@ResponseBody
	public Page<Post> getPostByDeptId(HttpServletRequest request, HttpServletResponse response) {
		Page<Post> page = new Page<Post>();
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "0" : request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "20" : request.getParameter("pageSize"));
		String deptId = request.getParameter("deptId")==null?"0":request.getParameter("deptId");
		String status = request.getParameter("status");
		List<Post> lists = postService.getByDeptId(deptId,status,pageNumber,pageSize);
		page.setRows(lists);
		page.setTotal(lists.size());
		return page;
	}

	@RequestMapping("getPostById")
	@ResponseBody
	public Post getPostById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		Post post = postService.getById(id);

		return post;
	}

	@RequestMapping("savePost")
	@ResponseBody
	public Map<String,Object> savePost(HttpServletRequest request, HttpServletResponse response) {
		String deptId = request.getParameter("deptId")==null?"0":request.getParameter("deptId");
		String name = request.getParameter("name") == null ? "" : request.getParameter("name");
		String postCode = request.getParameter("postCode") == null ? "" : request.getParameter("postCode");
		String id= request.getParameter("id") == null ? "" : request.getParameter("id");
		Integer status = Integer.parseInt(request.getParameter("status")==null?"1":request.getParameter("status"));

		Map<String,Object> map = new HashMap<String, Object>();
		Post post = new Post();
		post.setCreateTime(new Date());
		post.setId(id);
		post.setName(name);
		post.setStatus(status);
		post.setPostCode(postCode);
		map = postService.saveAndFlush(post,deptId);

		return map;
	}

	@RequestMapping("deletePost")
	@ResponseBody
	public Map<String, Object> deletePost(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
			try { 
				postService.delById(id);
				map.put("code", 1);
				map.put("msg", "删除成功");
			} catch (Exception e) {
				map.put("code", -1);
				map.put("msg", e.getMessage());
			}
		
		return map;
	}

}
