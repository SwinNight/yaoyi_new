package com.xiaojd.base.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DjObject implements DjInterface{

	public Logger getLog(){
		// return Logger.getLogger(this.getClass());
		return LoggerFactory.getLogger(this.getClass());
	}

	
}
