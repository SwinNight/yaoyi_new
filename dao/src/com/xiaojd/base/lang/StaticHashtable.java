package com.xiaojd.base.lang;

import java.util.Hashtable;


public class StaticHashtable extends StaticObject {
	
	public Hashtable<String,Object> getTable(){
		Hashtable<String, Object> ret = (Hashtable<String,Object>) getObject();
		if (ret == null) {
			ret = new Hashtable<String, Object>();
			setTable(ret);
		}
		return ret;
	}
	
	public void setTable(Hashtable<String,Object> s){
		setObject(s);
	}
	
	public Object get(String key){
		return getTable().get(key);
	}

	public void clear(){
		getTable().clear();
	}

	public void put(String key,Object value){
		getTable().put(key, value);
	}

	public Object remove(String key){
		return getTable().remove(key);
	}

}
