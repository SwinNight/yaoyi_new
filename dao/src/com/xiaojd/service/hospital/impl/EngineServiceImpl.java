package com.xiaojd.service.hospital.impl;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.xiaojd.service.hospital.EngineService;

@Repository
public class EngineServiceImpl extends HospitalBaseDAOImpl<Object> implements EngineService {

	public Connection getConnection() throws Exception {
		return SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
	}
	
	public void closeConn(Connection conn) throws Exception {
		conn.close();
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public void closeSession(Session session){
		session.close();
	}
	
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
