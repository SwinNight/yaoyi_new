package com.xiaojd.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xiaojd.entity.util.Config;

/**
 * 短信发送线程池
 * @author CZ
 *
 */
public class SmsFixedThreadPool {
    public static  ExecutorService SmsThreadPool;
    static {
    	 String numberStr =Config.getValue("server", "SmsThreadPool");
    	 int number =5;
    	 try {
    		 number = Integer.parseInt(numberStr);
    	 }catch (Exception e) {
    		 e.printStackTrace();
    		 number =5;
    	 } 
    	 SmsThreadPool = Executors.newFixedThreadPool(number);	
    }
    
    public  static void  addThreadPool(SmsThread thread) {
    	SmsThreadPool.execute(thread);
    }
    
    public static void endThreadPool() {
    	SmsThreadPool.shutdown();
    }
}
