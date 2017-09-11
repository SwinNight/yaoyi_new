package com.xiaojd.service.hospital;

import java.util.List;

import com.xiaojd.entity.hospital.EngPtDrug;

public interface EngPtDrugService {

	public abstract List<EngPtDrug> loadByCfid(String cfid);

	public void saveOrUpdate(EngPtDrug item);

	public void flush();

	public void clear();
	
	public void executeSQLUpdate(String sql);
}