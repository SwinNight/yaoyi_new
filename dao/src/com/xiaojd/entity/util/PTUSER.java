package com.xiaojd.entity.util;

import java.util.Map;

/**
 * 平台人员
 * @author CZ
 *
 */
public enum PTUSER {
    PT_ADMIN("1001","管理员"),
    PT_PHARMACY_MANAGER("1002","药店店长"),
    PT_COURIER("1003","药店配送员");

	private  String   code;
	private  String   name;
	
	PTUSER(String code,String name) {
		this.code =code;
		this.name= name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 普通方法  
    public static String getNameByCode(String code) {  
        for (PTUSER c : PTUSER.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return "角色异常";  
    }  
   
    /**
     * 判断下一步操作当前角色是否合适
     * @param nowRole
     * @param compareRole
     * @return
     */
    public static boolean CheckRole(String nowRole,String compareRole,Map<String,String> map) {
    	if(nowRole !=null &&!"".equals(nowRole)&&compareRole!=null&&!"".equals(compareRole)) {
    		if(nowRole.equals(compareRole)) {
    	      return true;
    		}
    	}
		map.put("success", "false");
		map.put("message", "角色异常");
		return false;
    }
    
    /**
     * 判断下一步操作当前角色是否合适
     * @param nowRole
     * @param compareRole
     * @return
     */
    public static boolean CheckRole(String nowRole,String compareRole) {
    	if(nowRole !=null &&!"".equals(nowRole)&&compareRole!=null&&!"".equals(compareRole)) {
    		if(nowRole.equals(compareRole)) {
    	      return true;
    		}
    	}
		return false;
    }
    
    
    /**
     * 判断订单是否是否属于药店，或者属于配送人员
     * @param nowRole
     * @param compareRole
     * @return
     */
    public static boolean CheckCFBelong(long id,long compareId,Map<String,String> map) {
    	 if(id == compareId) {
    	      return true;
    	}
		map.put("success", "false");
		map.put("message", "订单读取异常");
		return false;
    }
    
}
