package com.xiaojd.service.hospital;

import java.util.List;

import com.xiaojd.entity.hospital.EngPtCf;
import com.xiaojd.entity.hospital.EngPtDispense;
import com.xiaojd.entity.hospital.EngPtMessage;

public interface EngPtDispenseService {
	public  EngPtDispense loadByPresNo(String id);
	
	public  List<EngPtDispense> loadByPresNos(String presNos);

	public void saveOrUpdate(EngPtDispense message);
	
}