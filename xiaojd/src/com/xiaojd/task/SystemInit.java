package com.xiaojd.task;

import org.springframework.stereotype.Component;


@Component
public class SystemInit {
	
	private static boolean sys_flag = false;

	/**
	 * Spring 的定时任务
	 */
	public void run() {
		if(!sys_flag){
			//System.out.println("System Initialize");
		}
	}

}
