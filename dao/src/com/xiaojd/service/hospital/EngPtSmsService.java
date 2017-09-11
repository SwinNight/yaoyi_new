package com.xiaojd.service.hospital;
import java.util.List;
import java.util.Map;

import com.xiaojd.entity.hospital.EngPtDeliveryAddress;
import com.xiaojd.entity.hospital.EngPtSms;
public interface EngPtSmsService {

	
	public Long saveSms(EngPtSms sms);
	
	public void saveOrUpdate(EngPtSms sms);

	public void flush();

	public void clear();
	
	public List executeSQLQuery(String queryString, int firstResult,
			int maxResults, Map<String, String> paramValues);
	public Long countBySql(String sql, Map<String, String> paramValues);


}