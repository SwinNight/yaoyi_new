package com.xiaojd.entity.util;

/**
 * 药店处方状态
 * @author CZ
 *
 */
public enum SMSSENDSTATUS {
	SMS_NO_SEND("-1","发送短信不发送"),
	SMS_SUCCESS("0","发送短信成功"),
	SMS_PARAM_NULL("1","提交参数不能为空"),
	SMS_USER_MISS("2","用户名或密码错误"),
	SMS_ACESS("3","账号未启用"),
	SMS_ACCOUNT_INVALID("4","计费账号无效"),
	SMS_TIME_INVALID("5","定时时间无效"),
	SMS_PHONENUM_INVALID("6","号码中含有无效号码"),
	SMS_BALANCE_LESS("7","短信余额不足"),
	SMS_FORMAT_ERROR("8","压缩格式不正确"),
	SMS_PARAM_COUNT("9","可用参数组个数不正确,请检查参数"),
	SMS_CONTENT_MORE("10","消息内容或者参数值过长,一次提交的内容长度不能超过500000字符"),
	SMS_OTHER_ERROR("11","其他错误");
	private  String   status;
	private  String   name;
	
	private  SMSSENDSTATUS(String status,String name) {
		this.name =name;
		this.status =status;
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
	
	   // 普通方法  
    public static String getNameByStatus(String status) {  
        for (SMSSENDSTATUS c : SMSSENDSTATUS.values()) {  
            if (c.getStatus().equals(status)) {  
                return c.name;  
            }  
        }  
        return "状态异常";  
    }  

}
