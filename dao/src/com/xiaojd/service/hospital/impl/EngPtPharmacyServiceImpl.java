package com.xiaojd.service.hospital.impl;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.xiaojd.entity.hospital.EngPtDelivery;
import com.xiaojd.entity.hospital.EngPtPharmacy;
import com.xiaojd.entity.hospital.EngPtUser;
import com.xiaojd.service.hospital.EngPtPharmacyService;


@Repository
public class EngPtPharmacyServiceImpl extends HospitalBaseDAOImpl<EngPtPharmacy> implements EngPtPharmacyService {
   
	
	@Override
	public EngPtPharmacy loadById(long id){
		String hql = " from EngPtPharmacy where Id = " + id;
		List<EngPtPharmacy> list = executeQuery(hql);
		return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
	}
	@Override
	public void saveOrUpdataUser(EngPtPharmacy engPtPharmacy) {
		saveOrUpdate(engPtPharmacy);
	}
	
	@Override
	public List<EngPtPharmacy> getPharmacysByStatus(String status) {
		String sql =" 1=1";
		if(status !=null && !"".equals(status) ){
			sql  = " status ='" +status+"'";
		}
		return executeQuery(" from EngPtPharmacy where " + sql);
	}

	/**
	 * 药房所在城区
	 * @return
	 */
	@Override
	public List<String> getPharmacysAreas() {
		List<String> list = executeNativeSql("select  distinct urban_area from Eng_Pt_Pharmacy where status ='1' ");
		return list;
	}
  
	/* 更具城区查找对应的药房
	 * @see com.xiaojd.service.hospital.EngPtPharmacyService#getPharmacysByArea(java.lang.String)
	 */
	@Override
	public List<EngPtPharmacy> getPharmacysByArea(String area) {
		String sql =" 1=1";
		if(area !=null && !"".equals(area) ){
			sql  = " urbanArea ='" +area+"'";
		}
		return executeQuery(" from EngPtPharmacy where " + sql);
	}


}
