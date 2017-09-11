package com.xiaojd.service.hospital.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiaojd.entity.hospital.EngPtDeliveryAddress;
import com.xiaojd.entity.hospital.EngPtSms;
import com.xiaojd.service.hospital.EngPtSmsService;

	
@Repository
public class EngPtSmsServiceImpl extends HospitalBaseDAOImpl<EngPtSms> implements EngPtSmsService {

	/* 返回自增主键
	 * 
	 */
	public Long saveSms(EngPtSms sms) {
		 return save(sms);
	}
	
	
}