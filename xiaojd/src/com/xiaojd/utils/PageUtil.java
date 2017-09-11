package com.xiaojd.utils;

import java.util.HashMap;
import java.util.Map;

public class PageUtil {
	private int page;
	private int firstResult;
	private int maxResult;
	private String sortField;
	private String sortOrder;
	private String browseName;
	private Map<String, String> parameters;
	
	public PageUtil(Map<String, Object> params) {
		init(params);
	}
	
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public void init(Map<String, Object> params){
		String page = (String) params.get("page");
		String rows = (String) params.get("rows");
		this.browseName = (String) params.get("browseName");
		this.page = (page==null) ? 1 : Integer.parseInt(page);
		this.maxResult = (rows==null) ? 10 : Integer.parseInt(rows);
		this.firstResult = (this.page-1) * maxResult;
		
		Map<String, String> p = new HashMap<String, String>();
		for(String key : params.keySet()) {
			String value = params.get(key).toString().trim();
			if(key.startsWith("parameters")){
				if(value != null && value.length() > 0)
					p.put(key.substring(key.indexOf("[")+1, key.indexOf("]")), value);
			}else if(key.startsWith("rpt_parameters")){
				if(value != null && value.length() > 0){
					p.put(key.substring(key.indexOf("[")+1, key.indexOf("]")), value);
				}else{
					p.put(key.substring(key.indexOf("[")+1, key.indexOf("]")), "");
				}
				
				if(key.indexOf("coun") >= 0 && value.length() > 0){
					this.maxResult = Integer.valueOf(value) >= this.maxResult ? this.maxResult : Integer.valueOf(value);
				}
			}else if(key.startsWith("cf")){
				p.put(key.substring(2), value);
			}
		}
		this.parameters = p;
	}
	
	public String getSort(){
		return (this.sortField == null || this.sortField.isEmpty()) ? ""
				: ((this.sortOrder == null || this.sortOrder.isEmpty()) ? " order by " + this.sortField
						+ " asc" : " order by " + this.sortField + " "
						+ this.sortOrder);
	}
	
	public int getTotalPages(int size) {
		return (size%maxResult == 0)?(size/maxResult):(size/maxResult+1);
	}
	
	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public String getSortField() {
		return sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public int getPage() {
		return page;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}
    public void putParameter(String key,String value) { 
    	this.parameters.put(key,value);
    	
    }
	public String getBrowseName() {
		return browseName;
	}
	
}
