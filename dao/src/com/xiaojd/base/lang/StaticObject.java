package com.xiaojd.base.lang;

import java.util.Hashtable;

public class StaticObject {
	private Hashtable<String, Object> v = new Hashtable<String, Object>();
	static public String getSchema(){
		return "sz_hospital";
	}
	
	public Object getObject(){
		return v.get(getSchema());
	}
	public void setObject(Object s){
		v.put(getSchema(), s);
	}
}
