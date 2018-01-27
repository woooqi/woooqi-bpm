package com.woooqi.bpm.service.bpm;


import java.util.Map;

import com.titan.entity.web.Page;


public interface HistoryInstanceService {

	public byte[] getActivitiProccessImage(String processInstanceId);

	public Page<Map<String,Object>> getHistoricTaskinst(int pageNumber, int pageSize, String processInstanceId);

}
