package com.xiaojd.controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.xiaojd.entity.hospital.EngPtUser;
import com.xiaojd.entity.util.PTUSER;
import com.xiaojd.service.hospital.EngPtUserService;

/**
 * 手机端登录访问
 * @author CZ
 *
 */
@Controller
public class MobileLoginController  {
	Logger loger = LoggerFactory.getLogger(MobileLoginController.class);
	@Resource
	EngPtUserService engPtUserService;
	//---------------------人员角色------------------
	public static String PT_ADMIN ="1001"; //平台管理员
	public static String PT_COURIER_P="1002";//药店 
	public static String PT_COURIER ="1003";//平台配送员
	//----------------------------------------手机端登录访问----------------------------
			/**
			 * 手机端登录
			 * @param model
			 * @param user
			 * @return
			 */
			@RequestMapping(value = "mobileLoginPt")
			public String mobileLoginPt(ModelMap model, EngPtUser user){
				
				String validStr = "";
				if(user.getCode() == null || user.getCode().isEmpty())
					validStr = "用户名不能为空！";
				else if(user.getPwd() == null || user.getPwd().isEmpty())
					validStr = "密码不能为空！";
				else {
					List dbUsers = engPtUserService.getUserByCode(user.getCode());
					if(dbUsers != null && dbUsers.size() > 0) {
						Object[] dbUser = (Object[])dbUsers.get(0);
						if(user.getPwd().equals(dbUser[2].toString())) {
							user.setId(Long.parseLong(dbUser[0].toString()));
							user.setName(dbUser[1].toString());
							user.setPwd(dbUser[2].toString());
							user.setCode(dbUser[3].toString());
							user.setPharmacyId(Long.parseLong(dbUser[4].toString()));
							user.setRole(dbUser[5].toString());
							user.setStatus(dbUser[6].toString());
							user.setPharmacy(dbUser[7].toString());
							validStr = "success";
						} else
							validStr = "密码错误！";
					} else {
						validStr = "该用户不存在！";
					}
				}
				
				if ("success".equals(validStr)) {
					HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
					request.getSession().setAttribute("XIAOJD-PTUSER", user);
					request.getSession().setMaxInactiveInterval(30*60);//session 30分钟有效
		            return "/yaoyiPt/mobile/redirect";
		        } else {
		        	model.put("error", validStr);
		            return "/yaoyiPt/mobile/login";
		        }
			}
			
			/**
			 * 登录成功，跳转
			 * @param model
			 * @return
			 */
			@RequestMapping(value = "/mobileHomePt")
			public String mobileHome(ModelMap model){
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
				String  roleId=user.getRole();		
				String validStr = "";
	            if (PTUSER.PT_PHARMACY_MANAGER.getCode().equals(roleId)||PTUSER.PT_COURIER.getCode().equals(roleId)){	
	            	//查看当前所有的订单		
					   return "yaoyiPt/mobile/redirect";
				}else{		
						validStr = "该用户无权查看系统！";
						model.put("error", validStr);
						request.getSession().removeAttribute("XIAOJD-PTUSER");
						request.getSession().setAttribute("XIAOJD-PTURL", "mobile/loginPt");
						return "yaoyiPt/mobile/redirect";
				}	
			}
			

			/**
			 * session失效，重新登录
			 * @param model
			 * @return
			 */
			@RequestMapping(value = "mobileLoginPtA")
			public String mobileloginPtA(ModelMap model){
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				request.getSession().removeAttribute("XIAOJD-PTUSER");
				return "yaoyiPt/mobile/login";
			}
			
			/**
			 * 退出，注销用户
			 * @param model
			 * @return
			 */
			@RequestMapping(value = "mobileLogoutPt")
			 public String logoutPt(ModelMap model){
					HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
					request.getSession().removeAttribute("XIAOJD-PTUSER");	//清除当前用户的Session
					return "yaoyiPt/mobile/login";
			}
}
