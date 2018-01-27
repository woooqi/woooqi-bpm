package com.titan.service.work.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.titan.entity.bpm.bpm.DoneTask;
import com.titan.entity.organization.User;
import com.titan.service.work.WorkExecutedService;
import com.titan.utils.DataBaseUtils;
import com.titan.utils.PageUtils;

@Service
public class WorkExecutedServiceImpl implements WorkExecutedService {
	

	@Override
	public List<DoneTask> getAllDoneTasks() {
		User user = PageUtils.getCurrentUser();
		String sql = "select  t.* ,a.name_ as proc_def_name_ from act_hi_actinst t,act_re_procdef a where t.proc_def_id_ = a.id_ and t.assignee_ = ?";
		return DataBaseUtils.queryForListBean(sql, DoneTask.class, user.getId());
	}

}
