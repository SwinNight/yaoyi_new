package com.xiaojd.service.hospital.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaojd.entity.hospital.SysConfig;
import com.xiaojd.entity.util.Config;
import com.xiaojd.service.hospital.SysConfigService;

@Repository
public class SysConfigServiceImpl extends HospitalBaseDAOImpl<SysConfig>
		implements SysConfigService {

	public List<SysConfig> getAllSysConfigs(Map<String, String> paremeters,
			String sort, int firstRusult, int maxResult) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from SysConfig where 1=1 ");
		if (!paremeters.isEmpty()) {
			for (String key : paremeters.keySet()) {
				sql.append("and " + key + " like '%" + paremeters.get(key)
						+ "%' ");
			}
		}
		if (sort == null || sort.trim().equals("") || sort.isEmpty()) {
			sql.append(" order by id ");
		} else {
			sql.append(sort);
		}
		return executeQuery(sql.toString(), firstRusult, maxResult);
	}

	public String getSysCfgValue(String flag, String name) {
		if (Config.getTable().size() == 0) {
			Config.register();
		}
		return Config.getValue(flag, name);
	}

	public Long addSysConfig(SysConfig entity) {
		return save(entity);
	}

	public void saveOrUpdateSysConfig(SysConfig entity) {
		String name = entity.getName();
		if (name == null || name.length() == 0) {
			return;
		} else {
			name = name.trim();
			if(name.equals("type")&&entity.getFlag().equals("his")){
				updata_lisUrlOrZyUrlOrUrl(name,entity.getValue());
			} else if(name.equals("zyType")&&entity.getFlag().equals("his")){
				updata_lisUrlOrZyUrlOrUrl(name,entity.getValue());
			} else if(name.equals("lisType")&&entity.getFlag().equals("his")){
				updata_lisUrlOrZyUrlOrUrl(name,entity.getValue());
			}

			saveOrUpdate(entity);
		}

	}

	public void updata_lisUrlOrZyUrlOrUrl(String name,String DB_name){
		List<SysConfig> list = findzyUrlOrUrl(name);
		String DBConfig = getDBConfig(DB_name);
		SysConfig sysConfig = null ;
		if(list != null){
			for(int i = 0 ; i < list.size() ; i++){
				sysConfig = list.get(i);
				sysConfig.setValue(DBConfig);
				saveOrUpdate(sysConfig);
			}
		}
	}
	public String getDBConfig(String DB_name){
		String DBConfig = "";
		if(DB_name.toLowerCase().indexOf("sqlserver") > -1){
			if(DB_name.toLowerCase().indexOf("2000") > -1){
				DBConfig = "jdbc:microsoft:sqlserver://***.***.***.***:1433;DatabaseName=****";
			}else{
				DBConfig = "jdbc:sqlserver://***.***.***.***:1433;DatabaseName=****";
			}
		}else if(DB_name.toLowerCase().indexOf("mysql") > -1){
			DBConfig = "jdbc:mysql://***.***.***.***:3306/dbname";
		}else if(DB_name.toLowerCase().indexOf("oracle") > -1){
			DBConfig = "jdbc:oracle:thin:@***.***.***.***:1521:orcl";
		}
		return DBConfig;
	}
	
	public List<SysConfig> findzyUrlOrUrl(String name){
		StringBuffer sql = new StringBuffer();
		sql.append(" from SysConfig where 1=1 and  flag='his' and ");
		if(name.equals("type")){
			sql.append(" (name = 'zyUrl' or name='Url' or name = 'lisUrl') ");
		} else if(name.equals("zyType")){
			sql.append(" (name = 'zyUrl'  or name = 'lisUrl') ");
		}else if(name.equals("lisType")){
			sql.append("  name = 'lisUrl' ");
		}else{
			return null;
		}
		return executeQuery(sql.toString());
	}
	
	
	public void deleteSysConfig(Long id) {
		delete(id);
	}

	public int getTotalPages(Map<String, String> paremeters, String sort) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from SysConfig where 1=1 ");
		if (!paremeters.isEmpty()) {
			for (String key : paremeters.keySet()) {
				sql.append("and " + key + " like '%" + paremeters.get(key)
						+ "%' ");
			}
		}
		sql.append(sort);
		return getTotalCount(sql.toString());
	}

	@SuppressWarnings("unchecked")
	public List<String> getFlags() {
		return getSession()
				.createQuery(
						"SELECT DISTINCT flag FROM SysConfig where flag = 'server' or flag = 'his' or flag = 'engine' order by flag")
				.list();
	}

	public SysConfig getSysCfg(String flag, String name) {
		List<SysConfig> cfgs = executeQuery(" from SysConfig where enable='on' and flag = '"
				+ flag + "' and name = '" + name + "'");
		return cfgs == null ? null : cfgs.get(0);
	}
	
	public List<SysConfig> getVarType(){
		return executeQuery(" from SysConfig where flag = 'var' and enable = 'on' and name != 'varROOT'");
	}
	
	public List<SysConfig> getVarByVarName(String name){
		return executeQuery(" from SysConfig where flag = 'bean' and enable = 'on' and name like '" + name.trim() + "%'");
	}
	
	public String getSysValue(){
		List<SysConfig> cfgs = executeQuery("from SysConfig where flag = 'server' and enable = 'on' and name = 'unitFlag'");
		return cfgs.size()>0 ? cfgs.get(0).getValue() : null;
	}
}
