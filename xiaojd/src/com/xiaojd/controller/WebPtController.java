package com.xiaojd.controller;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xiaojd.entity.hospital.EngPtDelivery;
import com.xiaojd.entity.hospital.EngPtDeliveryAddress;
import com.xiaojd.entity.hospital.EngPtDispense;
import com.xiaojd.entity.hospital.EngPtCf;
import com.xiaojd.entity.hospital.EngPtDrug;
import com.xiaojd.entity.hospital.EngPtPharmacy;
import com.xiaojd.entity.hospital.EngPtSms;
import com.xiaojd.entity.hospital.EngPtUser;
import com.xiaojd.entity.util.Config;
import com.xiaojd.entity.util.PTSTATUS;
import com.xiaojd.entity.util.PTUSER;
import com.xiaojd.entity.util.SMSSENDSTATUS;
import com.xiaojd.service.hospital.EngPtCfService;
import com.xiaojd.service.hospital.EngPtDeliveryAddressService;
import com.xiaojd.service.hospital.EngPtDeliveryService;
import com.xiaojd.service.hospital.EngPtDispenseService;
import com.xiaojd.service.hospital.EngPtDrugService;
import com.xiaojd.service.hospital.EngPtMessageService;
import com.xiaojd.service.hospital.EngPtPharmacyService;
import com.xiaojd.service.hospital.EngPtSmsService;
import com.xiaojd.service.hospital.EngPtUserService;
import com.xiaojd.utils.PageUtil;
import com.xiaojd.utils.ReportExcelUtil;
import com.xiaojd.utils.SmsFixedThreadPool;
import com.xiaojd.utils.SmsPtUtils;
import com.xiaojd.utils.SmsThread;

/**
 * @author CZ
 *
 */

@Controller
public class WebPtController  {

	Logger loger = LoggerFactory.getLogger(WebPtController.class);
	@Resource
	EngPtCfService engPtCfService;
	@Resource
	EngPtDrugService engPtDrugService;
	@Resource
	EngPtMessageService engPtMessageService;
	@Resource
	EngPtDispenseService engPtDispenseService;
	@Resource
	EngPtUserService engPtUserService;
	@Resource
	EngPtDeliveryAddressService engPtDeliveryAddressService;
	@Resource
	EngPtDeliveryService engPtDeliveryService;
	@Resource
	EngPtPharmacyService engPtPharmacyService;
	
	@Resource
	EngPtSmsService engPtSmsService;
	
	public static String Ways_Own ="自取";
	public static String Ways_Send ="配送";
	
	public static String Belong_Hos ="社区";
	public static String Belong_Pharmacy ="药店";
			

//-------------------------------------------业务逻辑-----------------------------------------
	
	/**
	 * 管理员出发查询  药店 社区医院
	 * @param 查询字段
	 * @param 查询的值
	 * @return
	 */
	@RequestMapping(value = "adminSearch")
	@ResponseBody
	public  Map<String, Object> adminSearch(@RequestParam Map<String, Object> params) {
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
	    Map<String, Object> maps = new HashMap<String,Object>();
	    if(!PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_ADMIN.getCode())) {//管理员,只能查看药店
		    maps.put("success", "false");
		    return maps;	
	    }
	   PageUtil p = new PageUtil(params);
	   String sql =Config.getValue("sql", "adminSerarch");//管理员查询语句  
	   Long size =0l;
       size = engPtCfService.countBySql(sql, p.getParameters());     
      List ptCfList = engPtCfService.executeSQLQuery(sql, p.getFirstResult(), p.getMaxResult(),
				p.getParameters());
      if(ptCfList.size()>0) {
	              for(int i=0;i<ptCfList.size();i++) {
	            		Map<String,String> cf = (Map) ptCfList.get(i);
	            		for(int j=0;j<cf.size();j++) {
	            			if(cf.get("status") !=null) {
	                			cf.put("status_name", PTSTATUS.getNameByStatus(cf.get("status")));
	            			}
	            		}
	              }
				maps.put("rowCount", size); // row counts
				maps.put("currentPage", p.getPage()); // current page
				maps.put("totalPage",  p.getTotalPages(Integer.valueOf(size+""))); // total pages
				maps.put("rows", ptCfList);
				maps.put("success", "true");
				return maps;	
		}	
		   maps.put("success", "false");
		   return maps;	
		}
		
	/**
	 * 药店查询处方
	 * @param 查询字段
	 * @param 查询的值
	 * @return
	 */
	@RequestMapping(value = "pharmacySearch")
	@ResponseBody
	public  Map<String, Object> pharmacySearch(@RequestParam Map<String, Object> params) {
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
	    Map<String, Object> maps = new HashMap<String,Object>();
	    if(!PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())) {//管理员,只能查看药店
		    maps.put("success", "false");
		    return maps;	
	    }
	   PageUtil p = new PageUtil(params);
	   String sql =Config.getValue("sql", "pharmacySearch");//管理员查询语句  
	   Long size =0l;
	   p.putParameter("pharmacy_id", userCurrent.getPharmacyId()+"");//只能查看该药店的处方
       size = engPtCfService.countBySql(sql, p.getParameters());     
       List ptCfList = engPtCfService.executeSQLQuery(sql, p.getFirstResult(), p.getMaxResult(),
				p.getParameters());
      if(ptCfList.size()>0) {
	              for(int i=0;i<ptCfList.size();i++) {
	            		Map<String,String> cf = (Map) ptCfList.get(i);
	            		for(int j=0;j<cf.size();j++) {
	            			if(cf.get("status") !=null) {
	                			cf.put("status_name", PTSTATUS.getNameByStatus(cf.get("status")));
	            			}
	            		}
	              }
				maps.put("rowCount", size); // row counts
				maps.put("currentPage", p.getPage()); // current page
				maps.put("totalPage",  p.getTotalPages(Integer.valueOf(size+""))); // total pages
				maps.put("rows", ptCfList);
				maps.put("success", "true");
				return maps;	
		}	
		   maps.put("success", "false");
		   return maps;	
		}
			/**
			 * 根据病人基本信息查询处方
			 * @param 查询字段
			 * @param 查询的值
			 * @return
			 */
			@RequestMapping(value = "loadCfByPatientInfoType1")
			@ResponseBody
			public  Map<String, Object> loadCfByPatientInfo1(@RequestParam Map<String, Object> params) {
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
				 if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_COURIER.getCode())==true) {
						return null;
				 }
			  Map<String, Object> maps = new HashMap<String,Object>();
			  PageUtil p = new PageUtil(params);
			  String temp ="";
			  String sql = " select * from eng_pt_cf cf where 1=1 ";
			  if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_ADMIN.getCode())) {//管理员,只能查看药店
				  sql = " select * from eng_pt_cf cf where 1=1 and hospital_or_pharmacy ='药店'";
			  } else if ( PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())){//药店
	               sql=  " select cf.* from eng_pt_cf cf,eng_pt_delivery de where cf.id=de.cf_id and de.pharmacy_id =?Epharmacy_id ";
	               p.putParameter("pharmacy_id", userCurrent.getPharmacyId()+"");
			  }
			  
			  if(p.getParameters().get("status") == null || "".equals(p.getParameters().get("status"))) {
				  if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_ADMIN.getCode())) {
					  temp=" and cf.status='" +PTSTATUS.CF_NEW.getStatus()+"' "; //默认查找新处方
				  } else if ( PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())){//药店
					  //默认查找新分配到药店的处方 
					  temp=" and cf.status in ('" +PTSTATUS.CF_TO_OWN.getStatus()+"','" +PTSTATUS.CF_TO_PHARMACY.getStatus()+"','" + PTSTATUS.CF_NOPAID_TO_PHARMACY .getStatus()+"')";
				  }
			  } else {
				  temp +=" and status = ?Estatus";
			  }
			   Long size =0l;
               size = engPtCfService.countBySql(sql +temp, p.getParameters());
              
              List ptCfList = engPtCfService.executeSQLQuery(sql+temp, p.getFirstResult(), p.getMaxResult(),
						p.getParameters());
              System.out.println("1");
              if(ptCfList.size()>0) {
			              for(int i=0;i<ptCfList.size();i++) {
			            		Map<String,String> cf = (Map) ptCfList.get(i);
			            		for(int j=0;j<cf.size();j++) {
			            			if(cf.get("status") !=null) {
			                			cf.put("status_name", PTSTATUS.getNameByStatus(cf.get("status")));
			            			}
			            		}
			              }
						maps.put("rowCount", size); // row counts
						maps.put("currentPage", p.getPage()); // current page
						maps.put("totalPage",  p.getTotalPages(Integer.valueOf(size+""))); // total pages
						maps.put("rows", ptCfList);
						maps.put("success", "true");
						return maps;	
				}	
				   maps.put("success", "false");
				   return maps;	
				}
					
			
			/**查询当前所有用户
			 * @param patientNo
			 * @param id
			 * @return
			 */
			@RequestMapping(value = "loadAllUser")
			public @ResponseBody List<EngPtUser> loadAllUser() {	
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
				 if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_ADMIN.getCode())==false) {
						return null;
				 }
					List<EngPtUser> listUser = engPtUserService.getUsers();
					if (listUser!=null && listUser.size()>0) {
						return listUser;
					}			
				   return null;
			}
			//备注
			
			/**修改用户，包括逻辑删除
			 * @param patientNo
			 * @param id
			 * @return
			 */
			@RequestMapping(value = "deleteOrUpdateUser")
			public @ResponseBody Map<String, String> deleteOrUpdateUser(Long id,String name,String code,String roleName,String pwd,String operation) {
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
		        Map<String, String> ret = new HashMap<String, String>();	
				 if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_ADMIN.getCode(),ret)==false) {
						return null;
				 }
				   if("DELETE".equals(operation) && id !=null && !"".equals(id)) {
					   EngPtUser user   = engPtUserService.findOrgRoleById(id);
					   if(user == null)  {
						   ret.put("success", "false");
						   ret.put("message","用户不存在");
					   } else {
						   user.setStatus("0");//状态置为无效 0
						   engPtUserService.saveOrUpdataUser(user);
						   ret.put("success", "true");
						   ret.put("message","删除成功");
					   }
				   }
				   if("CREATE".equals(operation)||"UPDATE".equals(operation)) {
					   
					   EngPtUser temp=engPtUserService.findByCode(code);
					   
					   EngPtUser user = new EngPtUser();
					   if("UPDATE".equals(operation)) {
						   user = engPtUserService.findOrgRoleById(id);
						   if(!user.getId().equals(temp.getId())&&temp.getCode().equals(user.getCode())) {
							   ret.put("success", "false");
							   ret.put("message","该账号已经存在");
							   return ret;
						   }
					   }else if("CREATE".equals(operation)) {
						   if(temp.getCode().equals(code)) {
							   ret.put("success", "false");
							   ret.put("message","该账号已经存在");
							   return ret;
						   }
						   user.setStatus("1");//状态置为使用
					   }
					   user.setName(name);
					   user.setCode(code);
					   user.setPwd(pwd);
	                   user.setRoleName(roleName);
	            	   engPtUserService.saveOrUpdataUser(user);
	            	   if("UPDATE".equals(operation)) {
	            		   ret.put("success", "true");
						   ret.put("message","修改成功");
	            	   } 
	            	   if("CREATE".equals(operation)) {
	            		   ret.put("success", "true");
						   ret.put("message","添加成功");
	            	   } 
				   }
				   return ret;
			}
			
			/**管理员根据病人处方号查找病人处方,和药品信息
			 * @param id
			 * @return
			 */
			@RequestMapping(value = "loadPtCfByIdToAdmin")
			public String loadPtCfByIdToAdmin(ModelMap resultMap,@RequestParam String id) {
				Map<String, Object> ret = null;
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
				 if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_ADMIN.getCode())==false) {
						return null;
				 }
				if(id != null && id.trim().length() > 0){			
					ret = new HashMap<String, Object>();
					EngPtCf cf = engPtCfService.loadByIdToAll(id);
					if(cf==null) {
						return null;
					}
					List<EngPtDrug> drugs = engPtDrugService.loadByCfid(id);
					resultMap.put("cf", cf);
					resultMap.put("drugs", drugs);
					if(Belong_Pharmacy.equals(cf.getHospitalOrPharmacy())) { //药店
						List<String> areaList = engPtPharmacyService.getPharmacysAreas();
					    resultMap.put("areaList", areaList); 	
					    if(!(PTSTATUS.CF_NEW_CANCEL.getStatus().equals(cf.getStatus())||PTSTATUS.CF_TO_PAID.getStatus().equals(cf.getStatus())||PTSTATUS. CF_NOPAID.getStatus().equals(cf.getStatus())||PTSTATUS.CF_NEW.getStatus().equals(cf.getStatus()))) {
						    EngPtDelivery ptDelivery =engPtDeliveryService.loadByCfId(id);
						    EngPtPharmacy  ptPharmacy= engPtPharmacyService.loadById(ptDelivery.getPharmacyId());
							if(ptDelivery.getCourierId() !=0) {
								   EngPtUser  courier= engPtUserService.findById(ptDelivery.getCourierId());
								   resultMap.put("courier", courier);
							} 
						    resultMap.put("ptDelivery", ptDelivery);
						    resultMap.put("ptPharmacy", ptPharmacy);
					    }
					    return "yaoyiPt/web/admin_pharmacy_refer";
					} else if(Belong_Hos.equals(cf.getHospitalOrPharmacy())) { //社区
						 EngPtDispense  dispense = engPtDispenseService.loadByPresNo(cf.getId());
						 resultMap.put("dispense", dispense);
						 return "yaoyiPt/web/admin_hospital_refer";
					}		
				}
				 return "500";
				
			}
			
			
			/**根据病人处方号查找病人处方,和药品信息
			 * @param id
			 * @return
			 */
			@RequestMapping(value = "loadPtCfByIdToPharmacy")
			public String loadPtCfByIdToPharmacy(ModelMap resultMap,@RequestParam String id) {
				Map<String, Object> ret = null;
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
				 if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())==false) {
						return null;
				 }
				if(id != null && id.trim().length() > 0){			
					ret = new HashMap<String, Object>();
					EngPtCf cf = engPtCfService.loadById(id);
					if(cf==null) {
						return null;
					}
					EngPtDelivery ptDelivery =engPtDeliveryService.loadByCfId(id);
                    if(ptDelivery ==null) {
                    	return null;
                    } else {
                    	if(ptDelivery.getPharmacyId() != userCurrent.getPharmacyId()) {
                    		return null;
                    	}
                    }
					List<EngPtDrug> drugs = engPtDrugService.loadByCfid(id);
					List<EngPtUser>  userList= engPtUserService.getUsersByPharmacy(ptDelivery.getPharmacyId());
					if(ptDelivery.getCourierId() !=0) {
					   EngPtUser  courier= engPtUserService.findById(ptDelivery.getCourierId());
					   resultMap.put("courier", courier);
					} 
					EngPtPharmacy  ptPharmacy= engPtPharmacyService.loadById(ptDelivery.getPharmacyId());
					resultMap.put("cf", cf);
					resultMap.put("drugs", drugs);
					resultMap.put("userList", userList);
					resultMap.put("ptDelivery", ptDelivery);
					resultMap.put("ptPharmacy", ptPharmacy);
					return "yaoyiPt/web/phramacy_refer";
				    }
				return null;
			}

            
			
	
			/**查询病人基本信息，包括地址，手机号
			 * @param patientNo
			 * @param id
			 * @return
			 */
			@RequestMapping(value = "loadPtDeliveryAddressByPatientNo")
			public @ResponseBody 
			List<EngPtDeliveryAddress> loadPtDeliveryAddressByPatientNo(@RequestParam String patientNo) {	
				List<EngPtDeliveryAddress> address = engPtDeliveryAddressService.loadByPatientNo(patientNo);
				if (address!=null && address.size()>0) {
					return address;
				}			
			   return null;
			}
			
			/**保存病人基本信息，包括地址，手机号
			 * @param patientNo
			 * @param id
			 * @return
			 */
			@RequestMapping(value = "savePtDeliveryAddress")
			public @ResponseBody  Map<String, String> savePtDeliveryAddress(String id,String  patientNo,String phoneNo,String address,String name ) {
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
		        Map<String, String> ret = new HashMap<String, String>();	
				 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_ADMIN.getCode(),ret)==false) {
						return ret;
				 }
				     EngPtDeliveryAddress  deliveryAddress = new EngPtDeliveryAddress();
					 Long idLong =0l;
				     if(id!=null && !"".equals(id)) {
				    	try {
				    		 idLong = Long.parseLong(id);
				    	 } catch(Exception e) {
				    		 idLong =-1l;
				    	 }
				     } 
				     deliveryAddress.setAddress(address);
				     deliveryAddress.setName(name);
				     deliveryAddress.setPatientNo(patientNo);
				     deliveryAddress.setPhoneNo(phoneNo);
				     if(idLong ==0) {
					     idLong =engPtDeliveryAddressService.saveAddress(deliveryAddress);
				     }
				     if(idLong >0){
				    	  deliveryAddress.setId(idLong);
				    	  engPtDeliveryAddressService.saveOrUpdate(deliveryAddress);
				     }     
				        ret.put("success", "true");
						ret.put("message","操作成功");
						ret.put("id", idLong +"");
				     return ret;	  
			}
			

			
			/**
			 * 删除病人地址信息
			 * @param id
			 * @param name
			 * @param address
			 * @param phoneNo
			 */
			@RequestMapping(value = "deleteDeliveryAddress")
			public @ResponseBody 
			Map<String, String> deleteDeliveryAddress(@RequestParam Long id) {
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
		        Map<String, String> ret = new HashMap<String, String>();	
				 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_ADMIN.getCode(),ret)==false) {
						return ret;
				 }
				engPtDeliveryAddressService.deleteById(id);	
		        ret.put("success", "true");
				ret.put("message","删除成功");
				return ret;
			}
		    
			/**	添加派送信息,分配到药房
			 * @param cfId  处方ID
			 * @param pharmacyId 药房ID
			 * @param address 收件人地址
			 * @param phoneNo 收件人联系电话
			 * @param name 收件人
			 * @param remark 备注
			 * @param way 是否自取
			 * @return
			 */
			@RequestMapping(value = "saveCfDeliverInfo")
			public @ResponseBody 
			Map<String,String> saveCfDeliverInfo(@RequestParam String cfId,@RequestParam long pharmacyId,
					@RequestParam String address,@RequestParam String phoneNo,
					@RequestParam String name,@RequestParam String  remark,@RequestParam String  way) {
				    Map<String,String> map = new HashMap<String,String>();
					EngPtCf ptCf= engPtCfService.loadById(cfId);
					 if(ptCf == null) {
							map.put("success", "false");
							map.put("message", "没有该处方");
							return map;
					 }
					String status = ptCf.getStatus();
				    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");	  
					 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_ADMIN.getCode(),map)==false) {
							return map;
					 }
					//先查询处方状态
					if(!(PTSTATUS.CF_TO_PAID.getStatus().equals(status) || PTSTATUS.CF_NOPAID.getStatus().equals(status))) {
						map.put("success", "false");//该处方订单异常
    					map.put("message","当前异状态：" +PTSTATUS.getNameByStatus(status));
    					return map;
					}
					if("".equals(phoneNo.trim())||"".equals(address.trim())||"".equals(name.trim())) {
						map.put("success", "false");//该处方订单已经存在
    					map.put("message","请填写收件人信息");
    					return map;
					}
					
					EngPtDelivery deli = engPtDeliveryService.loadByCfId(cfId);
					EngPtPharmacy pharmacy = engPtPharmacyService.loadById(pharmacyId);
					if(pharmacy == null) {
						map.put("success", "false");//该处方订单已经存在
    					map.put("message","药店选择异常！");
					}
					if(deli != null) {
						map.put("success", "false");//该处方订单已经存在
    					map.put("message","该处方订单已经存在");
    					return map;
					} else {
						deli = new  EngPtDelivery();
						deli.setCfId(cfId);
						deli.setPharmacyId(pharmacyId);
						deli.setPhoneNo(phoneNo.trim());
						deli.setAddress(address.trim());
						deli.setName(name.trim());
						deli.setRemark(remark.trim());
						deli.setPharmacyTime(new Timestamp(System.currentTimeMillis()));
						String content ="";//短信内容
						String params  ="";//短信参数
						String CF_NOPAID_TO_PHARMACY = "【医配通】尊敬的{$var},您在市立医院东区的就诊已转到{$var}药店，请到药店刷医保卡付款取药,处方号{$var}";// 内容
						String CF_TO_COURIER = "【医配通】尊敬的{$var},您在市立医院东区的就诊转到{$var}药店的{$var}处方正在派送中，如有问题请联系{$var}";// 内容
						String CF_TO_PHARMACY = "【医配通】尊敬的{$var},您在市立医院东区的就诊转到{$var}药店派送,安排配送中，处方号{$var}";// 内容
						String CF_TO_OWN = "【医配通】尊敬的{$var},您在市立医院东区的就诊转到{$var}药店取药,已付款，处方号{$var}";// 内容
						//先查询处方状态
						if(PTSTATUS.CF_TO_PAID.getStatus().equals(status)) {
							if(Ways_Send.equals(way.trim())) {
								ptCf.setStatus(PTSTATUS.CF_TO_PHARMACY.getStatus() );//分配到药店，药店安排配送
								map.put("message", PTSTATUS.CF_TO_PHARMACY.getName() );
								deli.setWays(Ways_Send);
								content = Config.getValue("server", "CF_TO_PHARMACY");
								String [] contentTemp = content.split("\\{\\$var\\}");
								content = contentTemp[0] +name.trim()+contentTemp[1] +pharmacy.getAddress()+pharmacy.getPharmacy()+contentTemp[2]+cfId;
								params =  phoneNo.trim();
							} else if (Ways_Own.equals(way.trim())) {
								ptCf.setStatus(PTSTATUS.CF_TO_OWN.getStatus() );//付费后到药店,自取
								map.put("message", PTSTATUS.CF_TO_OWN.getName());
								deli.setWays(Ways_Own);
								content = Config.getValue("server", "CF_TO_OWN");
								params =  phoneNo.trim() +"|"+name.trim()+"|" +pharmacy.getAddress()+pharmacy.getPharmacy()+"|"+cfId;
							} else {
								map.put("success", "false");
								map.put("message", "取药方式异常");
								return map;
							}
						} else if(PTSTATUS.CF_NOPAID.getStatus().equals(status)) { 	//先查询处方状态没有
							deli.setWays(Ways_Own);
							ptCf.setStatus(PTSTATUS.CF_NOPAID_TO_PHARMACY.getStatus() );//分配到药房（未付）
							map.put("message", PTSTATUS.CF_NOPAID_TO_PHARMACY.getName());
							content = Config.getValue("server", "CF_NOPAID_TO_PHARMACY");
							params =  phoneNo.trim() +"|" +name.trim()+"|"+pharmacy.getAddress()+pharmacy.getPharmacy()+"|"+cfId;
						} 

						long deliveryId = engPtDeliveryService.saveDelivery(deli);
						engPtCfService.saveOrUpdate(ptCf);	
						EngPtSms sms = new EngPtSms();
						sms.setCfId(cfId);
						sms.setDeliveryId(deli.getId());
						sms.setSenderId(user.getId());
						sms.setSenderTime(new Timestamp(System.currentTimeMillis()));
						sms.setMessageContent(content);
						sms.setMessageVar(params);
						SmsThread smsThread = new SmsThread(sms);
						SmsFixedThreadPool.addThreadPool(smsThread);//线程发送短信
						map.put("success", "true");
						map.put("delivery","" + deliveryId);
						return map;
					}
			}
			/**
			 * 修改处方状态
			 * @param cfId
			 * @param status
			 * @param handler
			 */
			@RequestMapping(value = "changePtCfToPay")
			public @ResponseBody 
			Map<String,String> changePtCfToPayHospital(@RequestParam String cfId,@RequestParam String status,@RequestParam String aduitDrugPerson, @RequestParam String druglist) {	
				    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");	    
				    if(PTSTATUS.CF_TO_PAID.getStatus().equals(status)) {//市院付费
				    	
				    }
					EngPtCf  ptCf = engPtCfService.loadById(cfId);
					String nowStatus = ptCf.getStatus();	
					Map<String,String> map = new HashMap<String,String>();
					 if(ptCf == null) {
							map.put("success", "false");
							map.put("message", "没有该处方");
							return map;
					 }
					//-------------------市院付款
					if(PTSTATUS.CF_TO_PAID.getStatus() .equals(status)){ //市院付款
						 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_ADMIN.getCode(),map)==false) {
								return map;
						 }
						 if(!PTSTATUS.CF_NEW.getStatus().equals(nowStatus)) {
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 
					}
					
					//-------------------市院付款
					if(PTSTATUS.CF_NOPAID_TO__PHARMACY_PAID.getStatus() .equals(status)){ //市院付款
						 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode(),map)==false) {
								return map;
						 }
							EngPtDelivery engDelivery=engPtDeliveryService.loadByCfId(cfId);	//配送单
							 if(engDelivery == null) {
									map.put("success", "false");
									map.put("message", "配送单不存在");
									return map;
							 }
						 if(engDelivery.getPharmacyId() != user.getPharmacyId()) {
							    map.put("success", "false");
								map.put("message", "配送单不存在");
								return map;
						 }
						 if(!PTSTATUS.CF_NOPAID_TO_PHARMACY.getStatus().equals(nowStatus)) {
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 
					}
	
					 if(aduitDrugPerson == null || "".equals(aduitDrugPerson.trim())) {
								map.put("success", "false");
								map.put("message", "请先填写审核人");
								return map;
						 }
						 List<EngPtDrug> drugs = engPtDrugService.loadByCfid(cfId);
						 if(drugs == null) {
							     map.put("success", "false");
								map.put("message", "药品为空");
								return map;
						 }
						 Map<String,EngPtDrug> drugsMapOld = new HashMap<String,EngPtDrug>();
						 for(int n =0;n<drugs.size();n++) {//遍历后与传进来的数据进行一一比对
							 EngPtDrug  temp =  drugs.get(n);
							 drugsMapOld.put(temp.getId(), temp);
						 }
						 String[] drugsTempOld = new String[drugs.size()];//id 集合
						 for(int i=0;i<drugsTempOld.length;i++) {
							 drugsTempOld[i]=drugs.get(i).getId();
						 }
						 if(druglist != null && !"".equals(druglist)) {
							 String[] drugsTemp = druglist.split("\\|");
							  if(drugsTemp.length ==0) {
									map.put("success", "false");
									map.put("message", "参数有误");
									return map;
							  }
							 String[] drugsTempNew = new String[drugsTemp.length]; //id 集合
							 Map<String,String> drugMapNew = new HashMap<String,String>();
							 for(int m=0;m<drugsTemp.length;m++) {
								 String[] temp = drugsTemp[m].split(":");
								 if(temp.length!=2) {
										map.put("success", "false");
										map.put("message", "参数有误");
										return map;
								 }
								 drugsTempNew[m] = temp[0];
								 drugMapNew.put(temp[0], temp[1]);
							 }
							 Arrays.sort(drugsTempOld);//数字、大写、小写，安装ACS码值进行排序)
							 Arrays.sort(drugsTempNew);//
							 if(drugsTempOld.length != drugsTempNew.length) {
									map.put("success", "false");
									map.put("message", "数据异常");
									return map;
							 }
							 for(int i=0;i<drugsTempOld.length;i++) {//查看一下id是否匹配
								 if(!drugsTempOld[i].equals(drugsTempNew[i])) {
									map.put("success", "false");
									map.put("message", "数据异常");
									return map;
								 }
							 }
						 
							 for (String drug : drugMapNew.keySet()) {
								    String newQuantityStr = drugMapNew.get(drug);//
								    EngPtDrug drugOld =  drugsMapOld.get(drug);
								    if(drugOld == null) {
								    	map.put("success", "false");
										map.put("message", "数据异常");
										return map;
								    }
								    try {
								    int newQuantity = Integer.parseInt(newQuantityStr);
								    int oldQuantity = Integer.parseInt(drugOld.getQuantity());
								    if(newQuantity <0 || newQuantity>oldQuantity) {
								    	map.put("success", "false");
										map.put("message", "数量异常 ");
										return map;
								    }//
								    Double oldamount = Double.parseDouble(drugOld.getAmount());
								    Double newAmount = oldamount/oldQuantity *newQuantity;
								    drugOld.setRealityAmount(String.format("%.2f", newAmount));
								    drugOld.setRealityQuantity(newQuantity+"");
								    engPtDrugService.saveOrUpdate(drugOld);
					
								    } catch (Exception e) {
								    	map.put("success", "false");
										map.put("message", "数据异常");
										return map;
								    }
								}  
						 } else {
								map.put("success", "false");
								map.put("message", "药品有误");
								return map;
						 }
						    ptCf.setStatus(status);
						    ptCf.setAduitDrugPerson(aduitDrugPerson);
							engPtCfService.saveOrUpdate(ptCf);
							map.put("success", "true");
							map.put("message", PTSTATUS.CF_TO_PAID.getName()+"成功！");
							return map;
			}			
			
			/**
			 * 修改处方状态
			 * @param cfId
			 * @param status
			 * @param handler
			 */
			@RequestMapping(value = "changePtCfStatus")
			public @ResponseBody 
			Map<String,String> changePtCfStatus(@RequestParam String cfId,@RequestParam String status,String aduitDrugPerson, String cancelRemark) {	
				    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");	    
					EngPtCf  ptCf = engPtCfService.loadById(cfId);
					String nowStatus = ptCf.getStatus();	
					Map<String,String> map = new HashMap<String,String>();
					 if(ptCf == null) {
							map.put("success", "false");
							map.put("message", "没有该处方");
							return map;
					 }
					//-------------------市院取消处方
					if(PTSTATUS.CF_NEW_CANCEL.getStatus() .equals(status)){ //
						 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_ADMIN.getCode(),map)==false) {
								return map;
						 }
						 if(!PTSTATUS.CF_NEW.getStatus().equals(nowStatus)) {
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 
						 if(aduitDrugPerson == null || "".equals(aduitDrugPerson.trim())) {
								map.put("success", "false");
								map.put("message", "请先填写审核人");
								return map;
						 }
						 
						 if(cancelRemark == null || "".equals(cancelRemark.trim())) {
								map.put("success", "false");
								map.put("message", "请填写取消备注");
								return map;
						 }
						    ptCf.setStatus(status);
						    ptCf.setAduitDrugPerson(aduitDrugPerson);
						    ptCf.setCancelRemark(cancelRemark);
							engPtCfService.saveOrUpdate(ptCf);
							map.put("success", "true");
							map.put("message", PTSTATUS.CF_NEW_CANCEL.getName()+"成功！");
							return map;
					}	
					//-----------------------------------市院未付--------------------------------------------------
					if(PTSTATUS.CF_NOPAID.getStatus().equals(status)){ 
						 if(!PTSTATUS.CF_NEW.getStatus().equals(nowStatus)) {//新开
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 	 
						 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_ADMIN.getCode(),map)==false) {//角色不对
								return map;
						 }
						 ptCf.setStatus(status);
						 engPtCfService.saveOrUpdate(ptCf);
						 map.put("success", "true");
						 map.put("message", PTSTATUS.CF_NOPAID.getName());	
						 return map;
					}

					 map.put("success", "false");
					 map.put("message", "异常");	
					 return map;				
			}			
			
			/**
			 * 修改处方状态
			 * @param cfId
			 * @param status
			 * @param handler
			 */
			@RequestMapping(value = "changePtCfStatusByAduit")
			public @ResponseBody 
			Map<String,String> changePtCfStatusByAduit(@RequestParam String cfId,@RequestParam String status, String aduitDrugPerson,String cancelRemark) {	
				    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");	    
					EngPtCf  ptCf = engPtCfService.loadById(cfId);
					String nowStatus = ptCf.getStatus();	
					Map<String,String> map = new HashMap<String,String>();	
					 if(ptCf == null) {
							map.put("success", "false");
							map.put("message", "没有该处方");
							return map;
					 }
					//------------------------
					if(PTSTATUS.CF_TO_OEW_ERROR.getStatus().equals(status)){ //自取异常
						 if(!PTSTATUS.CF_TO_OWN.getStatus().equals(nowStatus)) {//是否自取
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 	 
						 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode(),map)==false) {//角色不对
								return map;
						 }
						EngPtDelivery engDelivery=engPtDeliveryService.loadByCfId(cfId);	//配送单
						 if(engDelivery == null) {
								map.put("success", "false");
								map.put("message", "配送单不存在");
								return map;
						 }
						 if(PTUSER.CheckCFBelong(user.getPharmacyId(),engDelivery.getPharmacyId(),map)==false) {//该订单不属于该药店
								return map;
						 }
						 if(cancelRemark == null || "".equals(cancelRemark)) {
								map.put("success", "false");
								map.put("message", "请填写备注");
								return map;
						 }
						 ptCf.setCancelRemark(cancelRemark);
						 engPtDeliveryService.saveOrUpdateDelivery(engDelivery);
						 ptCf.setStatus(status);
						 engPtCfService.saveOrUpdate(ptCf);
						 map.put("success", "true");
						 map.put("message",PTSTATUS.CF_TO_OEW_ERROR.getName());	
						 return map;
					}
					
					//--------------------------------
					if(PTSTATUS.CF_TO_CANCEL.getStatus().equals(status)){ //配送取消
						 if(!PTSTATUS.CF_TO_PHARMACY.getStatus().equals(nowStatus)) {//是否配送到药店
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 	 
						 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode(),map)==false) {//角色不对
								return map;
						 }
						EngPtDelivery engDelivery=engPtDeliveryService.loadByCfId(cfId);	//配送单
						 if(engDelivery == null) {
								map.put("success", "false");
								map.put("message", "配送单不存在");
								return map;
						 }
						 if(PTUSER.CheckCFBelong(user.getPharmacyId(),engDelivery.getPharmacyId(),map)==false) {//该订单不属于该药店
								return map;
						 }
						 if(cancelRemark == null || "".equals(cancelRemark)) {
								map.put("success", "false");
								map.put("message", "请填写备注");
								return map;
						 }
						 ptCf.setCancelRemark(cancelRemark);
						 engPtDeliveryService.saveOrUpdateDelivery(engDelivery);
						 ptCf.setStatus(status);
						 engPtCfService.saveOrUpdate(ptCf);
						 map.put("success", "true");
						 map.put("message", PTSTATUS.CF_TO_CANCEL.getName());	
						 return map;
					}

					//-------------------------------------------------------药店付款------------------------------
					if(PTSTATUS.CF_NOPAID_TO__PHARMACY_PAID.getStatus().equals(status)){ //
						 if(!PTSTATUS.CF_NOPAID_TO_PHARMACY.getStatus().equals(nowStatus)) {//是否配送到药店
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 	 
						 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode(),map)==false) {//角色不对
								return map;
						 }
						EngPtDelivery engDelivery=engPtDeliveryService.loadByCfId(cfId);	//配送单
						 if(engDelivery == null) {
								map.put("success", "false");
								map.put("message", "配送单不存在");
								return map;
						 }
						 if(PTUSER.CheckCFBelong(user.getPharmacyId(),engDelivery.getPharmacyId(),map)==false) {//该订单不属于该药店
								return map;
						 }
						 ptCf.setAduitDrugPerson(aduitDrugPerson);
						 engPtDeliveryService.saveOrUpdateDelivery(engDelivery);
						 ptCf.setStatus(status);
						 engPtCfService.saveOrUpdate(ptCf);
						 map.put("success", "true");
						 map.put("message", PTSTATUS.CF_NOPAID_TO__PHARMACY_PAID.getName()+"成功！");	
						 return map;
					}

					//-------------------------------------------------------自取取消------------------------------
					if(PTSTATUS.CF_NOPAID_TO_ERROR.getStatus().equals(status)){ //
						 if(!PTSTATUS.CF_NOPAID_TO_PHARMACY.getStatus().equals(nowStatus)) {//是否配送到药店
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 	 
						 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode(),map)==false) {//角色不对
								return map;
						 }
						EngPtDelivery engDelivery=engPtDeliveryService.loadByCfId(cfId);	//配送单
						 if(engDelivery == null) {
								map.put("success", "false");
								map.put("message", "配送单不存在");
								return map;
						 }
						 if(PTUSER.CheckCFBelong(user.getPharmacyId(),engDelivery.getPharmacyId(),map)==false) {//该订单不属于该药店
								return map;
						 }
				         if(aduitDrugPerson == null || "".equals(aduitDrugPerson)) {
				        		map.put("success", "false");
								map.put("message", "请填写审核人");
								return map; 
				         }
						 if(cancelRemark == null || "".equals(cancelRemark)) {
								map.put("success", "false");
								map.put("message", "请填写备注");
								return map;
						 }
						 ptCf.setCancelRemark(cancelRemark);
						 ptCf.setAduitDrugPerson(aduitDrugPerson);
						 engPtDeliveryService.saveOrUpdateDelivery(engDelivery);
						 ptCf.setStatus(status);
						 engPtCfService.saveOrUpdate(ptCf);
						 map.put("success", "true");
						 map.put("message", PTSTATUS.CF_NOPAID_TO_ERROR.getName());	
						 return map;
					 }
					 map.put("success", "false");
					 map.put("message", "异常");	
					 return map;				
			}			
			
			
			/**
			 * 修改处方状态 自取成功，在药店付款
			 * @param cfId
			 * @param status
			 * @param handler
			 */
			@RequestMapping(value = "changePtCfStatusToOwnPayInPharmacy")
			public @ResponseBody 
			Map<String,String> changePtCfStatusToOwnPayInPharmacy(@RequestParam String cfId,@RequestParam String status,@RequestParam String takeDrugPerson) {	
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");	    
				EngPtCf  ptCf = engPtCfService.loadById(cfId);
				Map<String,String> map = new HashMap<String,String>();
				 if(ptCf == null) {
						map.put("success", "false");
						map.put("message", "没有该处方");
						return map;
				 }
				String nowStatus = ptCf.getStatus();	
				if(takeDrugPerson ==null || "".equals(takeDrugPerson)) {
					map.put("success", "false");
					map.put("message", "请填写取药人！");
					return map;
				}
				//-------------------------------------------------------自取(店付)------------------------------
				if(PTSTATUS.CF_NOPAID_TO_OWN.getStatus().equals(status)){ //
					 if(!PTSTATUS.CF_NOPAID_TO__PHARMACY_PAID.getStatus().equals(nowStatus)) {//是否配送到药店
							map.put("success", "false");
							map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
							return map;
					 } 	 
					 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode(),map)==false) {//角色不对
							return map;
					 }
					EngPtDelivery engDelivery=engPtDeliveryService.loadByCfId(cfId);	//配送单
					 if(engDelivery == null) {
							map.put("success", "false");
							map.put("message", "订单异常");
							return map;
					 }
					 if(PTUSER.CheckCFBelong(user.getPharmacyId(),engDelivery.getPharmacyId(),map)==false) {//该订单不属于该药店
							return map;
					 }
					 engDelivery.setTakeDrugPerson(takeDrugPerson);
					 engPtDeliveryService.saveOrUpdateDelivery(engDelivery);
					 ptCf.setStatus(status);
					 engPtCfService.saveOrUpdate(ptCf);
					 map.put("success", "true");
					 map.put("message", PTSTATUS.CF_NOPAID_TO_OWN.getName());	
					 return map;
				 }
				map.put("success", "false");
				map.put("message", "订单异常");
				return map;
			}
			
			
			/**
			 * 修改处方状态 市院付款后，到药店自取
			 * @param cfId
			 * @param status
			 * @param handler
			 */
			@RequestMapping(value = "changePtCfStatusToOwnPayInHospital")
			public @ResponseBody 
			Map<String,String> changePtCfStatusToOwnPayInHospital(@RequestParam String cfId,@RequestParam String status,@RequestParam String takeDrugPerson) {	
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");	    
				EngPtCf  ptCf = engPtCfService.loadById(cfId);
				String nowStatus = ptCf.getStatus();	
				Map<String,String> map = new HashMap<String,String>();
				 if(ptCf == null) {
						map.put("success", "false");
						map.put("message", "没有该处方");
						return map;
				 }
				if(takeDrugPerson ==null || "".equals(takeDrugPerson)) {
					map.put("success", "false");
					map.put("message", "请填写取药人！");
					return map;
				}
				//-------------------------------------------------------------------------------------
				if(PTSTATUS.CF_TO_OEW_SUCCESS.getStatus().equals(status)){ //自取成功
					 if(!PTSTATUS.CF_TO_OWN.getStatus().equals(nowStatus)) {//是否自取
							map.put("success", "false");
							map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
							return map;
					 } 	 
					 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode(),map)==false) {//角色不对
							return map;
					 }
					EngPtDelivery engDelivery=engPtDeliveryService.loadByCfId(cfId);	//配送单
					 if(engDelivery == null) {
							map.put("success", "false");
							map.put("message", "配送单不存在");
							return map;
					 }
					 if(PTUSER.CheckCFBelong(user.getPharmacyId(),engDelivery.getPharmacyId(),map)==false) {//该订单不属于该药店
							return map;
					 }
					 engDelivery.setTakeDrugPerson(takeDrugPerson);//取药人
					 engPtDeliveryService.saveOrUpdateDelivery(engDelivery);
					 ptCf.setStatus(status);
					 engPtCfService.saveOrUpdate(ptCf);
					 map.put("success", "true");
					 map.put("message",PTSTATUS.CF_TO_OEW_SUCCESS.getName());	
					 return map;
				}
				map.put("success", "false");
				map.put("message", "订单异常！");
				return null;
			}
			
			/**
			 * 订单派送给送货员
			 * @param cfId
			 * @param courierId
			 */
			@RequestMapping(value = "ptDelivertyToCourier")
			public @ResponseBody 
			Map<String, String> ptDelivertyToCourier(@RequestParam String cfId,@RequestParam long courierId) {		
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");	    
				    Map<String, String> ret = new HashMap<String, String>();
					EngPtCf  ptCf = engPtCfService.loadById(cfId);
					if(ptCf == null) {
						ret.put("success", "false");
						ret.put("message", "查询不到该处方！");
						 return ret;
					} else {	
						if(PTSTATUS.CF_TO_PHARMACY.getStatus().equals(ptCf.getStatus())) {//审核通过
							EngPtDelivery engDelivery = engPtDeliveryService.loadByCfId(cfId);
							if(engDelivery ==null) {
								ret.put("success", "false");
								ret.put("message", "订单不存在！");
								 return ret;
							}
							String content ="";//短信内容
							String params  ="";//短信参数
							String CF_TO_COURIER = "【医配通】尊敬的{$var},您在市立医院东区的就诊转到{$var}药店的{$var}处方正在派送中，如有问题请联系{$var}";// 内容
							 if(PTUSER.CheckCFBelong(user.getPharmacyId(),engDelivery.getPharmacyId(),ret)==false) {//该订单不属于该药店
									return ret;
							 }
							 EngPtUser	courier =engPtUserService.findById(courierId);
							 if(courier ==null || engDelivery.getPharmacyId() !=courier.getPharmacyId()) {
									ret.put("success", "false");
									ret.put("message", "派送员选择异常！");
									return ret;
							 }
							ptCf.setStatus(PTSTATUS.CF_TO_COURIER .getStatus());//分配到派送员
							engDelivery.setCourierId(courierId);//添加派送员
							engPtDeliveryService.saveOrUpdateDelivery(engDelivery);
							engPtCfService.saveOrUpdate(ptCf);
							EngPtPharmacy pharmacy = engPtPharmacyService.loadById(engDelivery.getPharmacyId());
							content = Config.getValue("server", "CF_TO_COURIER");
							params =  engDelivery.getPhoneNo() +"|" +engDelivery.getName()+"|"+pharmacy.getAddress()+pharmacy.getPharmacy()+"|"+cfId+"|"+courier.getPhone();
						
							EngPtSms sms = new EngPtSms();
							sms.setCfId(cfId);
							sms.setDeliveryId(engDelivery.getId());
							sms.setSenderId(user.getId());
							sms.setSenderTime(new Timestamp(System.currentTimeMillis()));
							sms.setMessageContent(content);
							sms.setMessageVar(params);
							SmsThread smsThread = new SmsThread(sms);
							SmsFixedThreadPool.addThreadPool(smsThread);//线程发送短信
							ret.put("success", "true");
							ret.put("message",PTSTATUS.CF_TO_COURIER .getName());
							return ret;
						} else {
							ret.put("success", "false");
							ret.put("message", "处方状态异常！");
						}
					}
					return ret;
			}
			
			 
	/**订单分配到药房
	 * @param resultMap
	 * @param area
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ptGetPharmacyByArea")
		public @ResponseBody List<EngPtPharmacy> ptGetPharmacyByArea(ModelMap resultMap,@RequestParam String area)  throws Exception {				
    		if(area != null && area.trim().length() > 0){
					List<EngPtPharmacy> pharmacyList = engPtPharmacyService.getPharmacysByArea(area); 
					return   pharmacyList;
    		} else {
    			   return  null;
    		}
		}
	
	@RequestMapping(value = "exportCf")
	public @ResponseBody String exportCf(HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute("CFSTUTE", "START");
		OutputStream os = null;
		String fileName ="";
		String cfId =request.getParameter("cfId");
		if(cfId == null || "".equals(cfId)) {
			return "";
		}
		try {	
			 response.reset();// 清除缓冲中的数据
	         EngPtCf cf =  engPtCfService.loadById(cfId);
	         List<EngPtDrug> drugs = engPtDrugService.loadByCfid(cfId);
	     if (cf != null) {
	         fileName= cf.getId()+"_"+cf.getName()+"处方.xls";	    
	 		String userAgent = request.getHeader("User-Agent");
			if(userAgent.indexOf("Firefox") != -1){
				fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
			}else{
				fileName = java.net.URLEncoder.encode(fileName,"UTF-8");//ie 6 下不能超过14个中文字
			}
			response.setHeader("Content-Disposition","attachment; filename="+fileName);// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型

		
				HSSFWorkbook demoWorkBook = new HSSFWorkbook();
				List<HSSFSheet> list = ReportExcelUtil.exportCF(cf, drugs, demoWorkBook);
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setColumnWidth(0,4*512);
						list.get(i).setColumnWidth(1,5*512);
						list.get(i).setColumnWidth(2, 5*512);
						list.get(i).setColumnWidth(3, 512*5);
						list.get(i).setColumnWidth(4, 512*5);
						list.get(i).setColumnWidth(5, 512*5);
						list.get(i).setColumnWidth(6, 512*3);
						list.get(i).setColumnWidth(7, 512*5);
						list.get(i).setGridsPrinted(true);
						HSSFFooter footer = list.get(i).getFooter();
						footer.setRight("Page " + HSSFFooter.page() + " of "
								+ HSSFFooter.numPages());
					}
					os = response.getOutputStream();
					demoWorkBook.write(os);
			}
		} catch (Exception e) {
			 String simplename = e.getClass().getSimpleName();   
			   if(!"ClientAbortException".equals(simplename)){   
			         e.printStackTrace();   
			    }   
		} finally {
			try {
				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.gc();
		}
		request.getSession().setAttribute("CFSTUTE", "END");
		return "";
	}
	
	/**打开处方下载页面 社区，药店处方
	 * @return
	 */
	@RequestMapping(value = "/downCf")
	public String downCf(ModelMap resultMap,@RequestParam String id){
		Map<String, Object> ret = null;
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
		 if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_COURIER.getCode())==true) {
				return null;
		 }
		if(id != null && id.trim().length() > 0){			
			ret = new HashMap<String, Object>();
			EngPtCf cf = engPtCfService.loadByIdToAll(id);//
			if(cf==null) {
				return null;
			}
			
			List<EngPtDrug> drugs = engPtDrugService.loadByCfid(id);
			resultMap.put("cf", cf);
			resultMap.put("drugs", drugs);
			return "yaoyiPt/web/downCf";
		    }
		return null;
	}
}
