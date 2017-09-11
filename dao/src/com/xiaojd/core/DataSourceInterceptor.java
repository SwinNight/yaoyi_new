package com.xiaojd.core;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class DataSourceInterceptor {
	public void setDataSourceHospital(JoinPoint jp) {
		// System.out.println("before:" + DatabaseContextHolder.getCustomerType());
		DatabaseContextHolder.setCustomerType("dataSourceHospital");
		// System.out.println("after:" + DatabaseContextHolder.getCustomerType());
	}

/*	public void setDataSourceMed1(JoinPoint jp) {
		// System.out.println("before:" + DatabaseContextHolder.getCustomerType());
		DatabaseContextHolder.setCustomerType("dataSourceMed1");
		// System.out.println("after:" + DatabaseContextHolder.getCustomerType());
	}*/
}
