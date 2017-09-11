package com.xiaojd.conn;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

/*
 * 2014/6/3 
 * 数据库连接
 */
public class DbConManager {
	
	@Test
	public void test() throws Exception{
		getKingbaseConnection("","","");
	}
	
	static public Connection getKingbaseConnection(String url, String user,
			String password) throws Exception {
		try {
			Class.forName("com.kingbase.Driver").newInstance();
			Connection con;
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(true);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("得数据库连结失败" + e.getMessage());
		}
	}
	
	static public Connection getMySqlConnection(String url, String user,
			String password) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con;
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(true);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("得数据库连结失败" + e.getMessage());
		}
	}

	static public Connection getSqlServerConnection(String url, String user,
			String password) throws Exception {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
					.newInstance();
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("得数据库连结失败" + e.getMessage());
		}
	}

	static public Connection getSqlServer2000Connection(String url,
			String user, String password) throws Exception {
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver")
					.newInstance();
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("得数据库连结失败" + e.getMessage());
		}
	}
	
	
	static public Connection getOracleConnection(String url, String user,
			String password) throws Exception {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			Connection con;
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(true);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("得数据库连结失败" + e.getMessage());
		}
	}
}
