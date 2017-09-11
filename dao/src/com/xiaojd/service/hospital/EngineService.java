package com.xiaojd.service.hospital;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface EngineService {

	public Connection getConnection() throws Exception;
	
	public void closeConn(Connection conn) throws Exception;
	
	public Session getSession();
	
	public void closeSession(Session session);
	
	public Object getFirst(String queryString, Object... paramValues);
	
	public SessionFactory getSessionFactory();
	
}