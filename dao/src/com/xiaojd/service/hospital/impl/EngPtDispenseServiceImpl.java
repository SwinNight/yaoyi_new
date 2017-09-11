package com.xiaojd.service.hospital.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.xiaojd.entity.hospital.EngPtCf;
import com.xiaojd.entity.hospital.EngPtDispense;
import com.xiaojd.entity.hospital.EngPtMessage;
import com.xiaojd.service.hospital.EngPtDispenseService;
import com.xiaojd.service.hospital.EngPtMessageService;

@Repository
public class EngPtDispenseServiceImpl extends HospitalBaseDAOImpl<EngPtDispense> implements EngPtDispenseService {

	
	
	@Override
	public EngPtDispense loadByPresNo(String presNo){
		if(presNo != null && !"".equals(presNo)){
			String hql = " from EngPtDispense where presNo = '" + presNo+"'";
			List<EngPtDispense> list = executeQuery(hql);
			return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
		}
		return null;
	}
	
	@Override
	public List<EngPtDispense> loadByPresNos(String presNos){
		if(presNos != null && !"".equals(presNos)){
			String hql = " from EngPtDispense where presNo in (" +presNos+")";
			List<EngPtDispense> list = executeQuery(hql);
			return list;
		}
		return null;
	}
}
