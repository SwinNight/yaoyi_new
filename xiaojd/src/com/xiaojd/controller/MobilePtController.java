package com.xiaojd.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.xiaojd.entity.hospital.EngPtDrug;
import com.xiaojd.entity.hospital.EngPtPharmacy;
import com.xiaojd.entity.hospital.EngPtSms;
import com.xiaojd.entity.hospital.EngPtSuccessImg;
import com.xiaojd.entity.hospital.EngPtUser;
import com.xiaojd.entity.util.Config;
import com.xiaojd.entity.util.PTSTATUS;
import com.xiaojd.entity.util.PTUSER;
import com.xiaojd.entity.util.SMSSENDSTATUS;
import com.xiaojd.entity.hospital.EngPtCf;
import com.xiaojd.service.hospital.EngPtCfService;
import com.xiaojd.service.hospital.EngPtDeliveryAddressService;
import com.xiaojd.service.hospital.EngPtDeliveryService;
import com.xiaojd.service.hospital.EngPtDispenseService;
import com.xiaojd.service.hospital.EngPtDrugService;
import com.xiaojd.service.hospital.EngPtMessageService;
import com.xiaojd.service.hospital.EngPtPharmacyService;
import com.xiaojd.service.hospital.EngPtSmsService;
import com.xiaojd.service.hospital.EngPtSuccessImgService;
import com.xiaojd.service.hospital.EngPtUserService;
import com.xiaojd.utils.SmsFixedThreadPool;
import com.xiaojd.utils.SmsPtUtils;
import com.xiaojd.utils.SmsThread;


/**
 * @author  
 * 药易平台的所有方法都集中在一起
 *
 */
/**
 * @author CZ
 *
 */

@Controller
public class MobilePtController  {

	Logger loger = LoggerFactory.getLogger(MobilePtController.class);
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
	@Resource
	EngPtSuccessImgService engPtSuccessImgService;

	//---------------------人员角色------------------
	public static String PT_ADMIN ="1001"; //平台管理员
	public static String PT_COURIER_P ="1002";//药店
	public static String PT_COURIER ="1003";//药店
	
//---------------------------------------------------------手机端业务逻辑-----------------------------------------------------------------------  
			// 查看 新配送   区分药店和派送员角色
			@RequestMapping(value = "mobileNew")
			public String mobileNew(ModelMap model){
				return "yaoyiPt/mobile/new";
			}
			// 查看 全部配送 区分药店和派送员角色
			@RequestMapping(value = "mobileNewDetail")
			@ResponseBody 
			public Map<String,Object> mobileNewDetail(@RequestParam int size,@RequestParam String oldList,@RequestParam int currentPage){
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
				Map<String,Object> ret = new HashMap<String,Object>();
				String newStatus = "'"+PTSTATUS.CF_TO_COURIER.getStatus()+"','"+PTSTATUS.CF_TO_PHARMACY.getStatus()+"'";
				String  sql =" select de.*,cf.status,cf.pres_date_time from eng_pt_delivery de  left join eng_pt_cf  cf on de.cf_id =cf.id  where 1=1 ";
			   if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())) {//药店店长
					sql +=" and de.pharmacy_id =" +userCurrent.getPharmacyId();
				} else if (PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_COURIER.getCode())) {
					sql +=" and de.courier_id =" +userCurrent.getId();
				} else  {
					return null;
				}
			    sql +=" and cf.status in(" +newStatus+")";
			    String temp ="";
				if(oldList != null && !"".equals(oldList)) { //已经显示的不再查找
						temp=" and de.id not in (" +oldList+")";
				}
				System.out.println(sql);
				Map<String, String> param = new HashMap<String,String>();
				if(currentPage<1) {currentPage =1;}
				if(size <1) {size =5;}
			    long count = engPtDeliveryService.countBySql(sql, param);
				List ptDeliveryList = engPtDeliveryService.executeSQLQuery(sql+temp, (currentPage-1)*size,size, param);
			    ret.put("ptDeliveryList", ptDeliveryList);
			    ret.put("count", count);
				return ret;
			}
			// 查看 全部配送 区分药店和派送员角色
			@RequestMapping(value = "mobileAll")
			public String mobileAll(ModelMap model){
				return "yaoyiPt/mobile/all";
			}
			
			// 查看 全部配送 区分药店和派送员角色
			@RequestMapping(value = "mobileAllDetail")
			@ResponseBody 
			public Map<String,Object> mobileAllDetail(@RequestParam String status,@RequestParam int size,@RequestParam String oldList,@RequestParam int currentPage){
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
				Map<String,Object> ret = new HashMap<String,Object>();
				String newStatus = "'"+PTSTATUS.CF_TO_COURIER.getStatus()+"','"+PTSTATUS.CF_TO_PHARMACY.getStatus()+"'";
				String successStatus = "'"+PTSTATUS.CF_TO_SUCCESS.getStatus()+"'";
				String faliedStatus  = "'"+PTSTATUS.CF_TO_ERROR.getStatus()+"','" +PTSTATUS.CF_TO_CANCEL.getStatus()+"'";
				String  sql =" select de.*,cf.status,cf.pres_date_time from eng_pt_delivery de  left join eng_pt_cf  cf on de.cf_id =cf.id  where 1=1 ";
			   if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())) {//药店店长
					sql +=" and de.pharmacy_id =" +userCurrent.getPharmacyId();
				} else if (PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_COURIER.getCode())) {
					sql +=" and de.courier_id =" +userCurrent.getId();
				} else  {
					return null;
				}
				if("new".equals(status)) {//新建
					sql +=" and cf.status in (" +newStatus+")";
				} else if("ok".equals(status)) {//成功
					sql +=" and cf.status  =" +successStatus;
				} else if("cancel".equals(status)) { //失败
					sql +=" and cf.status  in (" +faliedStatus+")";
				} else {
					sql +=" and cf.status  in (" +newStatus+","+successStatus+","+faliedStatus+")";
				}
				String temp ="";
				if(oldList != null && !"".equals(oldList)) { //已经显示的不再查找
						temp=" and de.id not in (" +oldList+")";
				}
				System.out.println(sql);
				Map<String, String> param = new HashMap<String,String>();
				if(currentPage<1) {currentPage =1;}
				if(size <1) {size =5;}
			    long count = engPtDeliveryService.countBySql(sql, param);
				List ptDeliveryList = engPtDeliveryService.executeSQLQuery(sql+temp, (currentPage-1)*size,size, param);
			    ret.put("ptDeliveryList", ptDeliveryList);
			    ret.put("count", count);
				return ret;
			}
			
			
			
			// 查看 个人中心 区分药店和派送员角色
			@RequestMapping(value = "mobileUser")
			public String mobileUser(ModelMap model){
				return "yaoyiPt/mobile/all";
			}
            
			@RequestMapping(value = "mobilePtDelivertyToDatail")
			public String mobilePtDelivertyToDatail(ModelMap model,@RequestParam long DelivertyId ) {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
				EngPtDelivery ptDelivery = engPtDeliveryService.loadById(DelivertyId);
				if(ptDelivery ==null) {
					return  "yaoyiPt/mobile/detail";
				}
				EngPtCf ptCf = engPtCfService.loadById(ptDelivery.getCfId());
				
				if(!(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())||PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_COURIER.getCode()))) {
						return  "yaoyiPt/mobile/detail";
				} 
				if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())) {
					if(userCurrent.getPharmacyId()!=ptDelivery.getPharmacyId()) {//处方派送到该药店
						return  "yaoyiPt/mobile/detail";
					}
				} else if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_COURIER.getCode())) {
					if(userCurrent.getId() !=ptDelivery.getCourierId()) {//处方派送到该派送员
						return  "yaoyiPt/mobile/detail";
					}
				}
				List<EngPtDrug> drugs = engPtDrugService.loadByCfid(ptCf.getId());
				
				if(PTSTATUS.CF_TO_COURIER.getStatus().equals(ptCf.getStatus())||PTSTATUS.CF_TO_SUCCESS.getStatus().equals(ptCf.getStatus())
						||PTSTATUS.CF_TO_ERROR.getStatus().equals(ptCf.getStatus())||PTSTATUS.CF_TO_CANCEL.getStatus().equals(ptCf.getStatus())) {
					EngPtUser userTemp  =engPtUserService.findById(ptDelivery.getCourierId());
					if(userTemp!=null) {
						ptDelivery.setCourierName(userTemp.getName());
					}
				   //审核通过的派送，选择派送员
				} else if(ptCf.getStatus().equals(PTSTATUS.CF_TO_PHARMACY.getStatus()) && userCurrent.getRole().equals(PTUSER.PT_PHARMACY_MANAGER.getCode())) { 
					List<EngPtUser> ptUserList =  engPtUserService.getUsersByPharmacy(ptDelivery.getPharmacyId());
					model.put("ptUserList", ptUserList);
				}  else {
					return  "yaoyiPt/mobile/detail";
				}
				model.put("drugs", drugs);
				model.put("ptCf", ptCf);
				model.put("ptDelivery", ptDelivery);
				return  "yaoyiPt/mobile/detail";
			}		
			
			@RequestMapping(value = "mobileDatailToEndFailed")
			public String mobileDatailToEndFailed(ModelMap model,@RequestParam long DelivertyId,@RequestParam String status) {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
				EngPtDelivery ptDelivery = engPtDeliveryService.loadById(DelivertyId);
			
				if(ptDelivery ==null) {
					return  "yaoyiPt/mobile/detailEndFailed";
				}
				EngPtCf ptCf = engPtCfService.loadById(ptDelivery.getCfId());
				if(!(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())||PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_COURIER.getCode()))) {
						return  "yaoyiPt/mobile/detailEndFailed";
				} 
				if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())) {
					if(userCurrent.getPharmacyId()!=ptDelivery.getPharmacyId()) {//处方派送到该药店
						return  "yaoyiPt/mobile/detailEndFailed";
					}
				} else if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_COURIER.getCode())) {
					if(userCurrent.getId() !=ptDelivery.getCourierId()) {//处方派送到该派送员
						return  "yaoyiPt/mobile/detailEndFailed";
					}
				}
				List<EngPtDrug> drugs = engPtDrugService.loadByCfid(ptCf.getId());
				
				if(ptDelivery.getCourierId()>0) {
					EngPtUser userTemp  =engPtUserService.findById(ptDelivery.getCourierId());
					if(userTemp!=null) {
						ptDelivery.setCourierName(userTemp.getName());
					}
				} 
				model.put("drugs", drugs);
				model.put("ptCf", ptCf);
				model.put("ptDelivery", ptDelivery);
				return  "yaoyiPt/mobile/detailEndFailed";
			}		
			
			@RequestMapping(value = "mobileDatailToEndSuccess")
			public String mobileDatailToEndSuccess(ModelMap model,@RequestParam long DelivertyId) {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
				EngPtDelivery ptDelivery = engPtDeliveryService.loadById(DelivertyId);
				if(ptDelivery ==null) {
					return  "yaoyiPt/mobile/detailEndSuccess";
				}
				if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_PHARMACY_MANAGER.getCode())) {
					if(userCurrent.getPharmacyId()!=ptDelivery.getPharmacyId()) {//处方派送到该药店
						return  "yaoyiPt/mobile/detailEndSuccess";
					}
				} else if(PTUSER.CheckRole(userCurrent.getRole(),PTUSER.PT_COURIER.getCode())) {
					if(userCurrent.getId() !=ptDelivery.getCourierId()) {//处方派送到该派送员
						return  "yaoyiPt/mobile/detailEndSuccess";
					}
				}
				EngPtCf ptCf = engPtCfService.loadById(ptDelivery.getCfId());
				List<EngPtDrug> drugs = engPtDrugService.loadByCfid(ptCf.getId());
				if(ptDelivery.getCourierId()>0) {
					EngPtUser userTemp  =engPtUserService.findById(ptDelivery.getCourierId());
					if(userTemp!=null) {
						ptDelivery.setCourierName(userTemp.getName());
					}
				} 
				String  imgNo = ptDelivery.getCfId() +"_"+ptDelivery.getId();
				EngPtSuccessImg successImg =  engPtSuccessImgService.findByImgNo(imgNo);
				if(successImg !=null) {
				      model.put("img", successImg);
				}
				model.put("drugs", drugs);
				model.put("ptCf", ptCf);
				model.put("ptDelivery", ptDelivery);
				return  "yaoyiPt/mobile/detailEndSuccess";
			}		

			/**
			 * 订单派送给送货员
			 * @param cfId
			 * @param courierId
			 */
			@RequestMapping(value = "mobilePtDelivertyToCourier")
			public @ResponseBody 
			Map<String, String> mobilePtDelivertyToCourier(@RequestParam String cfId,@RequestParam long courierId) {		
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");	    
				    Map<String, String> ret = new HashMap<String, String>();
					EngPtCf  ptCf = engPtCfService.loadById(cfId);
					if(ptCf == null) {
						ret.put("success", "false");
						ret.put("message", "查询不到该处方！");
						 return ret;
					} else {	
						if(PTSTATUS.CF_TO_PHARMACY.getStatus().equals(ptCf.getStatus())) {
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
							params =  engDelivery.getPhoneNo() +"|" +pharmacy.getAddress()+pharmacy.getPharmacy()+"|"+cfId+"|"+courier.getPhone();
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
			
			/**
			 * 修改处方状态
			 * @param cfId
			 * @param status
			 * @param handler
			 */
			@RequestMapping(value = "mobileChangePtCfStatus")
			public @ResponseBody 
			Map<String,String> mobileChangePtCfStatus(@RequestParam String cfId,@RequestParam String status,String cancelRemark,String takeDrugPerson) {	
				    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");	    
					EngPtCf  ptCf = engPtCfService.loadById(cfId);
					String nowStatus = ptCf.getStatus();	
					Map<String,String> map = new HashMap<String,String>();
					if(ptCf == null) {
						map.put("success", "false");
						map.put("message", "查询不到该处方！");
						 return map;
					}
					//----------------------------------------------------------配送取消
					if(PTSTATUS.CF_TO_CANCEL.getStatus().equals(status)){ //配送取消
						 if(!PTSTATUS.CF_TO_PHARMACY.getStatus().equals(nowStatus)) {//是否配送到药店
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 
						 if(cancelRemark == null || "".equals(cancelRemark.trim())) {
								map.put("success", "false");
								map.put("message", "请填写备注");
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
						 if(PTUSER.CheckCFBelong(user.getPharmacyId(),engDelivery.getPharmacyId(),map)==false) {//该订单不属于该配送员
								return map;
						 }
						 
						 ptCf.setStatus(status);
						 ptCf.setCancelRemark(cancelRemark);
						 engPtCfService.saveOrUpdate(ptCf);
						 map.put("success", "true");
						 map.put("message", PTSTATUS.CF_TO_CANCEL.getName());	
						 return map;
					}
					
					//----------------------------------------------------------配送失败
					if(PTSTATUS.CF_TO_ERROR.getStatus().equals(status)){ //配送失败
						 if(!PTSTATUS.CF_TO_COURIER.getStatus().equals(nowStatus)) {//是否配送到配送员
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 
						 if(cancelRemark == null || "".equals(cancelRemark.trim())) {
								map.put("success", "false");
								map.put("message", "请填写备注");
								return map;
						 }
						 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_COURIER.getCode(),map)==false) {//角色不对
								return map;
						 }
						EngPtDelivery engDelivery=engPtDeliveryService.loadByCfId(cfId);	//配送单
						 if(engDelivery == null) {
								map.put("success", "false");
								map.put("message", "配送单不存在");
								return map;
						 }
						 if(PTUSER.CheckCFBelong(user.getId(),engDelivery.getCourierId(),map)==false) {//该订单不属于该配送员
								return map;
						 }
						 
						 ptCf.setStatus(status);
						 ptCf.setCancelRemark(cancelRemark);
						 engPtCfService.saveOrUpdate(ptCf);
						 map.put("success", "true");
						 map.put("message", PTSTATUS.CF_TO_ERROR.getName());	
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
			@RequestMapping(value = "mobileChangePtCfStatusSuccess")
			public @ResponseBody 
			Map<String,String> mobileChangePtCfStatusSuccess(@RequestParam String cfId,@RequestParam String status,@RequestParam String takeDrugPerson ,@RequestParam String img) {	
				    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				    EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");	    
					EngPtCf  ptCf = engPtCfService.loadById(cfId);
					String nowStatus = ptCf.getStatus();	
					Map<String,String> map = new HashMap<String,String>();
					if(ptCf == null) {
						map.put("success", "false");
						map.put("message", "查询不到该处方！");
						 return map;
					}
					//------------------------------------------配送成功
					if(PTSTATUS.CF_TO_SUCCESS.getStatus().equals(status)){ //配送成功
						 if(!PTSTATUS.CF_TO_COURIER.getStatus().equals(nowStatus)) {//是否配送到配送员
								map.put("success", "false");
								map.put("message", "异常！当前状态："+PTSTATUS.getNameByStatus(nowStatus));
								return map;
						 } 
						 
						 if(PTUSER.CheckRole(user.getRole(),PTUSER.PT_COURIER.getCode(),map)==false) {//角色不对
								return map;
						 }
						EngPtDelivery engDelivery = engPtDeliveryService.loadByCfId(cfId);	//配送单
						 if(engDelivery == null) {
								map.put("success", "false");
								map.put("message", "配送单不存在engPtDeliveryService");
								return map;
						 }
						 if(PTUSER.CheckCFBelong(user.getId(),engDelivery.getCourierId(),map)==false) {//该订单不属于该配送员
								return map;
						 }
						 if(takeDrugPerson == null || "".equals(takeDrugPerson.trim())) {
								map.put("success", "false");
								map.put("message", "请填写取药人");
								return map;
						 }
						 if(img == null || img.length()<100) {
								map.put("success", "false");
								map.put("message", "图片上传有误");
								return map;
						 }
						 
						 //data:image/jpeg;base64,
						 String head = img.substring(0, img.indexOf(","));
						 if(head.length()==0) {
								map.put("success", "false");
								map.put("message", "图片上传有误");
								return map;
						 }
						 if(head.indexOf("/")<0||head.indexOf(";")<0||head.indexOf(";")<head.indexOf("/")) {
								map.put("success", "false");
								map.put("message", "图片上传有误");
								return map; 
						 }

						 String first = head.substring(0, head.indexOf("/"));//data:image/
						 String type = head.substring( head.indexOf("/")+1,head.indexOf(";"));
						 String last = head.substring(img.indexOf(";")+1);
						 if(first.length() ==0||last.length() ==0||!"data:image".equals(first) ||!"base64".equals(last)) {
								map.put("success", "false");
								map.put("message", "图片上传有误");
								return map;
						 }
						 String imgType = "bmp,gif,jpeg,png"; //控制输入格式
						 if(type.length()==0||imgType.indexOf(type)<0) {
								map.put("success", "false");
								map.put("message", "图片上传有误");
								return map;
						 }
						 if(img.length()>2097152) {
								map.put("success", "false");
								map.put("message", "图片2M以内");
								return map;
						 }
						 String imgNo = cfId+"_"+engDelivery.getId();
						 EngPtSuccessImg successImg =  engPtSuccessImgService.findByImgNo(imgNo);
						  if(successImg == null) {
							  successImg = new EngPtSuccessImg();
						  }
						  successImg.setImgNo(imgNo);
						  successImg.setImg(img);
						  engPtSuccessImgService.saveOrUpdate(successImg);
						 ptCf.setStatus(status);
						 engDelivery.setTakeDrugPerson(takeDrugPerson);
						 engPtDeliveryService.saveOrUpdateDelivery(engDelivery);
						 engPtCfService.saveOrUpdate(ptCf);
						 map.put("success", "true");
						 map.put("message", PTSTATUS.CF_TO_SUCCESS.getName());	
						 return map;
					}
			    	 map.put("success", "false");
					 map.put("message", "异常");	
					 return map;				
			}			
}
