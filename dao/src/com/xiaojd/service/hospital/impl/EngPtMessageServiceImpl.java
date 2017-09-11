package com.xiaojd.service.hospital.impl;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.xiaojd.entity.hospital.EngPtMessage;
import com.xiaojd.service.hospital.EngPtMessageService;

@Repository
public class EngPtMessageServiceImpl extends HospitalBaseDAOImpl<EngPtMessage> implements EngPtMessageService {

	@Override
	public List<EngPtMessage> loadByCfid(String cfid){
		if(cfid != null && !"".equals(cfid)){
			String hql = " from EngPtMessage where cfId = '" + cfid + "' and severity >= '3' and type!='提醒'  order by id desc";
			List<EngPtMessage> list = executeQuery(hql);
			return list;
		}
		return null;
	}
	
	@Override
	public void deleteByCfid(String cfid){
		if(cfid != null && !"".equals(cfid)){
			String hql = " delete from EngPtMessage where cfId = '" + cfid + "'";
			executeUpdate(hql);
		}
	}
	
}
