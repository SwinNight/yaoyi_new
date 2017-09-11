package com.xiaojd.sql;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaojd.conn.ConManager;

public class TestDAO {
	
	private static final Log log = LogFactory.getLog(TestDAO.class);
	private static String fieldSplitStr = "" + (char) 7;
	private static String recSplitStr = "" + (char) 8;
	
	public static void main(String [] args){
		try {
			Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-03-21 16:58:25");
			Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-03-21 17:13:08");
			long a = Long.valueOf(d1.getTime()-60*1000);
			String b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d2.getTime()-60*1000);
			System.out.println(a);
			System.out.println(b);
			String c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-03-21 17:13:08")).getTime()-60*1000);
			System.out.println(c);
			System.out.println("2016-03-21 17:13:05".compareTo(c));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	
	static public boolean runSql(String sql , String sqlCount) {
		Connection con = null;
		ZipOutputStream zout = null;
		int count = 0;
		int cycle = 0;
		try {
			File filePath = new File(System.getProperty("catalina.home") + "\\webapps" + "\\xiaojd\\" + "rule.zip");
			if(!filePath.exists()){
				filePath.createNewFile(); 
			}
			FileOutputStream fos=new FileOutputStream(filePath); 
			zout = new ZipOutputStream(fos);
			con = ConManager.getConn();
			PreparedStatement pstCount = con.prepareStatement(sqlCount);
			ResultSet rsCount = pstCount.executeQuery();
			while(rsCount.next()){
				count = rsCount.getInt(1);
			}
			if(count % 10000 == 0){
				cycle = count / 10000;
			}else{
				cycle = count / 10000 + 1;
			}
			for(int c = 0; c < cycle; c++){
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setInt(1, c*10000);
				pst.setInt(2, 10000);
				ResultSet rs = pst.executeQuery();
				writeZip(rs,c,zout);
				rs.close();
				pst.close();
			}
			zout.closeEntry();
			zout.close();
			fos.close();
		} catch (Exception e) {
			log.info("生成压缩文件出错：");
			e.printStackTrace();
		} finally {
			ConManager.close(con);
		}
		return true;
	}
	
	static public void writeZip(ResultSet rs , int t , ZipOutputStream zout){
		try{
			StringBuffer ret = new StringBuffer();
			int cc = rs.getMetaData().getColumnCount();
			boolean start = true;
			int c = 1;
			String rec = "";
			String s = "";
			while (rs.next()) {
				rec = "";
				for (int i=1;i<=cc;i++){
					if (rs.getMetaData().getColumnTypeName(i).equals("DATETIME")){
						if (rs.getTimestamp(i)==null){
							s = "0";
						} else {
							s = ""+rs.getTimestamp(i).getTime();
						}
					} else {
						s = rs.getString(i);					
					}
					
					rec += s + fieldSplitStr;
				}
				if(!start)
					ret.append(recSplitStr);
				ret.append(rec);
				start = false;
				
			    if(c % 500 == 0){
			    	zout.putNextEntry(new ZipEntry("zip"+(c+(t*10000))));
					zout.write(ret.toString().getBytes());
					ret = new StringBuffer();
					start = true;
			    }
			    c++;
			}
			if(c < 10001){
				zout.putNextEntry(new ZipEntry("zip"+((t*10000)+c-1)));
				zout.write(ret.toString().getBytes());
				ret = new StringBuffer();
				start = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
	}
}