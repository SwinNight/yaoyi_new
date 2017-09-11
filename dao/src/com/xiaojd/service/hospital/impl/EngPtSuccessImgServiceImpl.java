package com.xiaojd.service.hospital.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiaojd.entity.hospital.EngPtDeliveryAddress;
import com.xiaojd.entity.hospital.EngPtSms;
import com.xiaojd.entity.hospital.EngPtSuccessImg;
import com.xiaojd.entity.hospital.EngPtUser;
import com.xiaojd.service.hospital.EngPtSmsService;
import com.xiaojd.service.hospital.EngPtSuccessImgService;

	
@Repository
public class EngPtSuccessImgServiceImpl extends HospitalBaseDAOImpl<EngPtSuccessImg> implements EngPtSuccessImgService {

	@Override
	public EngPtSuccessImg findByImgNo(String imgNo) {
		if(imgNo != null && !"".equals(imgNo)){
			String hql = " from EngPtSuccessImg where imgNo = '" + imgNo + "'";
			List<EngPtSuccessImg> list = executeQuery(hql);
			return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
		}
		return null;
	}
}