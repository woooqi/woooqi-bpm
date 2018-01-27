package com.woooqi.bpm.service.work.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.core.cmd.BaseTaskCommand;
import com.titan.entity.bpm.bpm.ProcessDefinition;
import com.titan.entity.organization.User;
import com.titan.service.work.BpmStartService;
import com.titan.utils.DataBaseUtils;
import com.titan.utils.PageUtils;

@Service
public class BpmStartServiceImpl implements BpmStartService{
	
	@Autowired 
	private BaseTaskCommand baseTaskCommand;
	
	@Override
	public void startProcess(String processKey, String bussinesskey, Map<String, Object> param) {
		baseTaskCommand.startProcess(processKey, bussinesskey, param);
	}

	@Override
	public List<ProcessDefinition> getAuthorizeProcessDefinition() {
		User user = PageUtils.getCurrentUser();
		String sql = "select distinct * from (select c.* from bpm_definition_role a,bpm_user_role b,act_re_procdef c where a.role_id = b.role_id and a.definition_id = c.id_ and b.user_id = ? union all select c.* from bpm_definition_dept a,bpm_user b,act_re_procdef c where a.dept_id = b.dept_id and a.definition_id = c.id_ and b.id = ? union all select c.* from bpm_definition_post a,bpm_user b,act_re_procdef c where a.post_id = b.post_id and a.definition_id = c.id_ and b.id = ?)";
		return DataBaseUtils.queryForListBean(sql, ProcessDefinition.class, user.getId(),user.getId(),user.getId());
	}
	
}
