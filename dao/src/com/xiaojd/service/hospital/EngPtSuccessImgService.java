package com.xiaojd.service.hospital;

import java.util.List;
import java.util.Map;

import com.xiaojd.entity.hospital.EngPtSms;
import com.xiaojd.entity.hospital.EngPtSuccessImg;

public interface EngPtSuccessImgService {

	public EngPtSuccessImg findByImgNo(String imgNo) ;
	
	public void saveOrUpdate(EngPtSuccessImg img);
}
