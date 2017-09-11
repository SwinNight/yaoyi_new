package com.xiaojd.service.hospital.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.xiaojd.entity.hospital.EngPtDelivery;
import com.xiaojd.entity.hospital.EngPtCf;
import com.xiaojd.service.hospital.EngPtCfService;


@Repository
public class EngPtCfServiceImpl extends HospitalBaseDAOImpl<EngPtCf> implements EngPtCfService {

	/* 药店处方
	 * @see com.xiaojd.service.hospital.EngPtCfService#loadById(java.lang.String)
	 */
	@Override
	public EngPtCf loadById(String id){
		if(id != null && !"".equals(id)){
			String hql = " from EngPtCf where id = '" + id+"'  and  hospitalOrPharmacy !='社区'";
			List<EngPtCf> list = executeQuery(hql);
			return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
		}
		return null;
	}
	
	/* 社区处方
	 * @see com.xiaojd.service.hospital.EngPtCfService#loadByIdToHospital(java.lang.String)
	 */
	@Override
	public EngPtCf loadByIdToHospital(String id){
		if(id != null && !"".equals(id)){
			String hql = " from EngPtCf where id = '" + id+"'  and  hospitalOrPharmacy ='社区'";
			List<EngPtCf> list = executeQuery(hql);
			return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
		}
		return null;
	}
	
	/* 社区处方
	 * @see com.xiaojd.service.hospital.EngPtCfService#loadByIdToHospital(java.lang.String)
	 */
	@Override
	public EngPtCf loadByIdToAll(String id){
		if(id != null && !"".equals(id)){
			String hql = " from EngPtCf where id = '" + id+"'";
			List<EngPtCf> list = executeQuery(hql);
			return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
		}
		return null;
	}
	@Override
	public List<EngPtCf> loadByPresNos(String presNos){
		if(presNos != null && !"".equals(presNos)){
			String hql = " from EngPtCf where presNo in (" +presNos+")";
			List<EngPtCf> list = executeQuery(hql);
			return list;
		}
		return null;
	}
	@Override
	public List<EngPtCf> loadByCardAndName(String cardNo,String name,String idNo,String phoneNo) {
		String  sql ="";
		if(!"".equals(idNo)) {
			sql = " and idCard='" +idNo +"' ";
		}  else if(!"".equals(cardNo)) {
			sql = " and cardNo='" +cardNo +"' ";
		}  else if(!"".equals(phoneNo)) {
			sql = " and phoneNo='" +phoneNo +"' ";
		}  else if (!"".equals(name)) {
			sql = " and name='" +name +"' ";
		}
		sql += " and  hospitalOrPharmacy ='社区'";
		if(!"".equals(sql)) {
			String hql = " from EngPtCf where  1=1" + sql;
			List<EngPtCf> list = executeQuery(hql);
			return list;
		} else {
			String hql = " from EngPtCf where  1=1" +sql;
			List<EngPtCf> list = executeQuery(hql);
			return list;
		}
	}
	
	@Override
	public List<EngPtCf> loadByStatus(String cardNo,String name,String idCard,String phoneNo,String status) {
		String  sql ="";
		
		if(!"".equals(idCard)) {
			sql = " and idCard='" +idCard +"' and status='"+status+"' ";
		}  else if(!"".equals(cardNo)) {
			sql = " and cardNo='" +cardNo +"' and status='"+status+"' ";
		}  else if(!"".equals(phoneNo)) {
			sql = " and phoneNo='" +phoneNo +"' and status='"+status+"' ";
		}  else if (!"".equals(name)) {
			sql = " and name='" +name +"' and status='"+status+"' ";
		}
		if(!"".equals(sql)) {
			String hql = " from EngPtCf where  1=1" + sql;
			List<EngPtCf> list = executeQuery(hql);
			return list;
		}
		return null;
	}

}