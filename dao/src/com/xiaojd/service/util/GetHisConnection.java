package com.xiaojd.service.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class GetHisConnection {

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

	// 得到His数据库连接
	public static Connection getHisConnection(String dataType, String url,
			String user, String pwd) throws Exception {
		Connection con = null;
		if (dataType.equalsIgnoreCase("sqlServer")) {
			try {
				con = getSqlServerConnection(url, user, pwd);
			} catch (Exception e) {
				throw e;
			}
		} else if (dataType.equalsIgnoreCase("oracle")) {
			try {
				con = getOracleConnection(url, user, pwd);
			} catch (Exception e) {
				throw e;
			}
		} else if (dataType.equalsIgnoreCase("mysql")) {
			try {
				con = getMySqlConnection(url, user, pwd);
			} catch (Exception e) {
				throw e;
			}
		} else if (dataType.equalsIgnoreCase("sqlServer2000")) {
			try {
				con = getSqlServer2000Connection(url, user, pwd);
			} catch (Exception e) {
				throw e;
			}
		} else {
			System.err.println("仅支持His使用mysql,sqlServer,oracle数据库");
			throw new Exception("得HIS数据库连结失败,仅支持His使用mysql,sqlServer,oracle数据库");
		}
		return con;
	}
	
	public static Connection getZyHisConnection(String dataType, String zyUrl, String mzUrl, String user, String pwd) throws Exception {
		
		String url = zyUrl;

		if (url.length()==0) {
			url = mzUrl;
		}
		
		if (dataType.equalsIgnoreCase("sqlServer")) {
			return getSqlServerConnection(url, user, pwd);
		} else if (dataType.equalsIgnoreCase("oracle")) {
			return getOracleConnection(
					url, user, pwd);
		} else if (dataType.equalsIgnoreCase("mysql")) {
			return getMySqlConnection(url,
					user, pwd);
		} else if (dataType.equalsIgnoreCase(
				"sqlServer2000")) {
			return getSqlServer2000Connection(url, user, pwd);
		}
		System.err.println("仅支持His使用mysql,sqlServer,oracle数据库");
		throw new Error("得HIS数据库连结失败,仅支持His使用mysql,sqlServer,oracle数据库");
	}
}
