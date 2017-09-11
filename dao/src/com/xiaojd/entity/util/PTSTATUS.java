package com.xiaojd.entity.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 药店处方状态
 * @author CZ
 *
 */
public enum PTSTATUS {
	
	CF_CANCEL("0","取消","医院"),
	CF_NEW("1","新开","医院"),
	//-------------------社区发药--------------
	CF_DISPENSE_SUCCESS("2","已发药","社区"),
	CF_DISPENSE_RETURN("3","退方","社区"),
	
	CF_NEW_CANCEL("7","市院取消","医院"),
	//------------------市院付款--------------
	CF_TO_PAID("10","市院付款","医院"),//选择付款
	
	CF_TO_PHARMACY("11","药房(院付)","药店"), //先选择药店,配送
	/*CF_TO_COURIER_ADUIT("12","派送审核"),*/
	CF_TO_COURIER("13","到派送员","药店"),
	CF_TO_OWN("14","自取(院付)","药店"),//先选择药店,自取
	CF_TO_SUCCESS("15","配送完成","药店"),
	CF_TO_ERROR("16","配送失败","药店"),
	CF_TO_CANCEL("17","配送取消","药店"),
	CF_TO_OEW_SUCCESS("18","自取成功(院付)","药店"),
	CF_TO_OEW_ERROR("19","自取失败(院付)","药店"),
	//-----------------------市院未付款------------
	CF_NOPAID("21","市院未付","医院"),
	CF_NOPAID_TO_PHARMACY("22","药店(未付)","药店"),
	CF_NOPAID_TO__PHARMACY_PAID("23","药店付款","药店"),
	CF_NOPAID_TO_OWN("24","自取(店付)","药店"),
	CF_NOPAID_TO_ERROR("25","自取取消(未付)","药店");
	private  String   status;
	private  String   name;
	private  String   type;
	
	private  PTSTATUS(String status,String name,String type) {
		this.name =name;
		this.status =status;
		this.type= type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	   public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
	
	// 普通方法  
    public static String getNameByStatus(String status) {  
        for (PTSTATUS c : PTSTATUS.values()) {  
            if (c.getStatus().equals(status)) {  
                return c.name;  
            }  
        }  
        return "状态异常";  
    }  
   /**
 * 状态Map
 */
   private static  	Map<String, String> map =new HashMap<String,String>();
   static {
        for (PTSTATUS c : PTSTATUS.values()) {  
           map.put(c.getStatus(), c.getName());
        }  
   }
    public static Map<String,String> getAllStatus() {
        return map;
    }
    
    /**
     * 状态Map
     */
       private static  	Map<String, String> pharmacyMap =new HashMap<String,String>();
       static {
            for (PTSTATUS c : PTSTATUS.values()) {  
            	if("药店".equals(c.getType())) {
                	pharmacyMap.put(c.getStatus(), c.getName());
            	}

            }  
       }
        public static Map<String,String> getPharmacyStatus() {
            return pharmacyMap;
        }

}
