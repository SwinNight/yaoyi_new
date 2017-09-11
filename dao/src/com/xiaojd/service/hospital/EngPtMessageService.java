package com.xiaojd.service.hospital;

import java.util.List;

import com.xiaojd.entity.hospital.EngPtMessage;

public interface EngPtMessageService {

	public abstract List<EngPtMessage> loadByCfid(String cfid);

	public void deleteByCfid(String cfid);
	
	public void saveOrUpdate(EngPtMessage message);
	
	public void flush();
	
	public void clear();
	
	public void executeSQLUpdate(String sql);
}