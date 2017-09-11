package com.xiaojd.base.lang;


public class StaticString extends StaticObject {
	
	public String get(){
		return (String)getObject();
	}
	public void set(String s){
		setObject(s);
	}
}
