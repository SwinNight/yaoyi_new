package com.xiaojd.service.hospital;

import java.util.List;
import java.util.Map;

import com.xiaojd.entity.hospital.SysConfig;

public interface SysConfigService {
	public List<SysConfig> getAllSysConfigs(Map<String, String> paremeters, String sort, int firstRusult, int maxResult);
	public String getSysCfgValue(String flag, String name);
	public Long addSysConfig(SysConfig entity);
	public void saveOrUpdateSysConfig(SysConfig entity);
	public void deleteSysConfig(Long id);
	public int getTotalPages(Map<String, String> paremeters, String sort);
	public List<String> getFlags();
	public SysConfig getSysCfg(String flag, String name);
	public List<SysConfig> getVarType();
	public List<SysConfig> getVarByVarName(String name);
	public String getSysValue();
}
