package com.xiaojd.service.hospital.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.xiaojd.entity.hospital.EngPtDeliveryAddress;
import com.xiaojd.service.hospital.EngPtDeliveryAddressService;

@Repository
public class EngPtDeliveryAddressServiceImpl extends HospitalBaseDAOImpl<EngPtDeliveryAddress> implements EngPtDeliveryAddressService {

	@Override
	public List<EngPtDeliveryAddress> loadByPatientNo(String patientNo){
		if(patientNo != null && !"".equals(patientNo)){
			String hql = " from EngPtDeliveryAddress where patientNo = '" + patientNo + "'";
			List<EngPtDeliveryAddress> list = executeQuery(hql);
			return list;
		}
		return null;
	}
	


	@Override
	public List<EngPtDeliveryAddress> loadByPhoneNo(String phoneNo) {
		if(phoneNo != null && !"".equals(phoneNo)){
			String hql = " from EngPtDeliveryAddress where phoneNo = '" + phoneNo + "'";
			List<EngPtDeliveryAddress> list = executeQuery(hql);
			return list;
		}
		return null;
	}
	
	@Override
	public void deleteById(Long id){
		if(id != null && !"".equals(id)){
			String hql = " delete from EngPtDeliveryAddress where id = '" + id + "'";
			executeUpdate(hql);
		}
	}


	@Override
	public EngPtDeliveryAddress loadById(Long id) {
		if(id != null && !"".equals(id)){
			String hql = " from EngPtDeliveryAddress where id = '" + id + "'";
			List<EngPtDeliveryAddress> list = executeQuery(hql);
			return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
		}
		return null;
	}
	
	
	/* 返回自增主键
	 * @see com.xiaojd.service.hospital.impl.HospitalBaseDAOImpl#save(java.lang.Object)
	 */
	public Long saveAddress(EngPtDeliveryAddress patient) {
		 return save(patient);
	}
	
}
