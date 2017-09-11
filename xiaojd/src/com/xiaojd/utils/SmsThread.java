package com.xiaojd.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.xiaojd.conn.ConManager;
import com.xiaojd.entity.hospital.EngPtSms;
import com.xiaojd.entity.util.Config;
import com.xiaojd.entity.util.SMSSENDSTATUS;

public class SmsThread  implements Runnable{
    private  EngPtSms sms;
    public SmsThread (EngPtSms sms) {
    	this.sms =sms;
    }
	@Override
	public void run() {
		try {
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			String  backStatusCode = "-1";
			String backStatusName = SMSSENDSTATUS.getNameByStatus(backStatusCode);
			String backBalance = "-1";		// 剩余条数
			if("1".equals(Config.getValue("server", "smsSwitch"))){
				  backStatusCode = SmsPtUtils.sendSms(sms.getMessageContent(),sms.getMessageVar());
				  backStatusName = SMSSENDSTATUS.getNameByStatus(backStatusCode);
				  backBalance = SmsPtUtils.checkSmsNumber();		// 剩余条数
			}	
			sms.setSenderTime(ts);
			sms.setBackStatusCode(backStatusCode);
			sms.setBackStatusName(backStatusName);
			sms.setBackBalance(backBalance);
			Connection con = ConManager.getConn();
			if(sms.getId()>0) {//如果已存在，则删除
				sms.delete(con);
			}
			sms.save(con);
			con.close();//关闭连接
			} catch (Exception e) {
				System.out.println("短信异常" +sms.getMessageContent()+"/n"+sms.getMessageVar());
				e.printStackTrace();
			} 
		}
		

}
