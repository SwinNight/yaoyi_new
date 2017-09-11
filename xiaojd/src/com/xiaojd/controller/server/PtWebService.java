package com.xiaojd.controller.server;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author CZ
 *社区医院调用的
 */
@WebService
public interface PtWebService {
  /**社区医院或药店通过平台调取处方 
 * @param cardNo  医保卡号
 * @param name 患者姓名
 * @param idNo  身份证号
 * @param phoneNo 手机号
 * @return  是否成功  isSuccess 1,0
 */

public String  runGetCf(
		  @WebParam(name = "cardNo") String cardNo,@WebParam(name = "name") String name,
		  @WebParam(name = "idNo") String idNo,@WebParam(name = "phoneNo") String phoneNo);
  
  /**
   * 社区医院将从平台调取的处方处理后，把处理结果反馈给平台
 * @param presNo 处方号
 * @param status  处方状态，如，新开，已发药，退回
 * @param hospital  社区医院或药店名称
 * @param dept  科室
 * @param doctor 医生
 * @param comment  处方处理的结果，如，成功发药，处方退回原因
 * @return 是否成功  isSuccess 1,0
 */
public String  runBackCf(
		  @WebParam(name = "presNo") String presNo,@WebParam(name = "status") String status,
		  @WebParam(name = "hospital") String hospital,@WebParam(name = "dept") String dept,
		  @WebParam(name = "doctor") String doctor,@WebParam(name = "comment") String comment);


/**说明：社区医院将从平台调取电子病历
 * @param patientNo 患者号
 * @return 电子病历
 */
public String  runBackEmr(@WebParam(name = "patientNo") String patientNo);

/** 说明：社区医院将从平台调取检验报告
 * @param patientNo  患者号
 * @return 检验报告 
 */
public String  runBackLis(@WebParam(name = "patientNo") String patientNo);

}
