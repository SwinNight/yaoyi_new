package com.xiaojd.service.hospital.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.xiaojd.entity.hospital.EngPtDelivery;
import com.xiaojd.entity.util.PTSTATUS;
import com.xiaojd.service.hospital.EngPtDeliveryService;


@Repository
public class EngPtDeliveryServiceImpl extends HospitalBaseDAOImpl<EngPtDelivery> implements EngPtDeliveryService {

	@Override
	public EngPtDelivery loadByCfId(String id){
	
		String hql = " from EngPtDelivery where cfId = '" + id + "' order by id desc";
		List<EngPtDelivery> list = executeQuery(hql);
		return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
	}
	
	@Override
	public EngPtDelivery loadById(long id){
		String hql = " from EngPtDelivery where Id = " + id + " order by id desc";
		List<EngPtDelivery> list = executeQuery(hql);
		return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
	}
	
	
	@Override
	public void saveOrUpdateDelivery(EngPtDelivery deli){
		saveOrUpdate(deli);
	}
	
	@Override
	public long saveDelivery(EngPtDelivery delivery) {
		return save(delivery);
	}
	
	@Override
	public List<EngPtDelivery> loadByCourier(long courier_id){
			String hql = " from EngPtDelivery where courierId = " + courier_id+"";
			List<EngPtDelivery> list = executeQuery(hql);
		return list;
	}
	
	@Override
	public List<EngPtDelivery> loadByPharmacyId(long pharmacyId){
			String hql = " from EngPtDelivery where pharmacyId = " + pharmacyId+"";
			List<EngPtDelivery> list = executeQuery(hql);
		return list;
	}
	
	
}
