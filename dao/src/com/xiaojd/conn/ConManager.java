package com.xiaojd.conn;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

public class ConManager {

	public static SessionFactory sessionFactory;
	
	private static ConnectionProvider cp;
	
	public static Connection getConn() throws SQLException {
		cp = ((SessionFactoryImplementor)sessionFactory).getConnectionProvider();
		return cp.getConnection();
	}
	
	public static void close(Connection conn) {
		try {
			if(cp != null) cp.closeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
