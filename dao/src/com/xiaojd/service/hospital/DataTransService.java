package com.xiaojd.service.hospital;

import java.sql.Connection;

/*
 * 2014/6/3
 * 处方 医嘱 导入
 */
public interface DataTransService {
	public Connection getHisConnection();
	
	public Connection getZyHisConnection();
	
	public String getTotal();
	
	public void SetState(boolean state);
	
	public String testConnection();
	
	public Connection getLisHisConnection();

}
