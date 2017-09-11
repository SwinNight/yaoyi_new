/*
 * Created on 2003-7-15
 */
package com.xiaojd.entity.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.xiaojd.base.lang.StaticHashtable;
import com.xiaojd.conn.ConManager;

public class Config {

	public static String SQLSERVER = "sqlserver";
	public static String MYSQL = "mysql";

	public static String getDbType() {

		return MYSQL;
	}
   
	public static String separatorStr = (char) 7 + "";

	public static String lastXML = "";

	private static StaticHashtable table = new StaticHashtable();

	private static char specChar = (char) 7;

	static public Vector<String> getKeys(String flag) {
		Vector<String> ret = new Vector<String>();
		Enumeration<String> enumTmp = table.getTable().keys();
		while (enumTmp.hasMoreElements()) {
			String key = "" + enumTmp.nextElement();
			if (key.indexOf(flag + specChar) == 0) {
				ret.add(key.substring(flag.length() + 1));
			}
		}
		return ret;
	}

	static public Vector<String> getFlags() {
		Vector<String> ret = new Vector<String>();
		Enumeration<String> enumTmp = table.getTable().keys();
		while (enumTmp.hasMoreElements()) {
			String key = "" + enumTmp.nextElement();
			String flag = key.substring(0, key.indexOf(specChar));
			if (!ret.contains(flag)) {
				ret.add(flag);
			}
		}
		return ret;
	}

	/**
	 * 获取系统配置参数
	 * @param flag  参数类型
	 * @param name  参数名称
	 * @return      参数值
	 */
	public static String getValue(String flag, String name) {
		if (table.getTable().size() == 0) {
			register();
		}
		String ret = (String) table.get(flag + specChar + name);
		if (ret == null)
			ret = "";

		return ret;
	}
	

	public static Hashtable getTable() {
		return table.getTable();
	}
    
	public synchronized static void register() {
		Connection con = null;
		try {
			con = ConManager.getConn();
			String sql = "select * from sys_config where enable  != 'off'";
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				table.put(
						rs.getString("flag") + specChar + rs.getString("name"),
						rs.getString("value"));
			}
			
			 String sql1 = "select * from eng_match_dict where enable  != 'off'";
			 pst = con.prepareStatement(sql1);
			 rs = pst.executeQuery();
			while (rs.next()) {
				String children = rs.getString("children");
				String  value = rs.getString("patient");
				String   belongOrg =  rs.getString("belong_org");
				String   classT = rs.getString("class");
				
				if(children != null && !"".equals(children)) {
					String [] childrenList = children.split(",");
					if(childrenList !=null && childrenList.length >0)  {
						for(String t: childrenList) {
							if(t.indexOf("[")>-1) {
								String key =  classT +"_"+belongOrg+specChar +t;
								table.put(key, value);
							}
				
						}
			
					}
				}
			}
			
			rs.close();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConManager.close(con);
		}
	}

	public synchronized static void reRegister() {
		table.clear();
		register();
	}
}
