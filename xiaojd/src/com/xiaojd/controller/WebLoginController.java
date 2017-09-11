package com.xiaojd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.xiaojd.base.tools.StringUtils;
import com.xiaojd.conn.ConManager;
import com.xiaojd.entity.hospital.EngPtDelivery;
import com.xiaojd.entity.hospital.EngPtDeliveryAddress;
import com.xiaojd.entity.hospital.EngPtCf;
import com.xiaojd.entity.hospital.EngPtDispense;
import com.xiaojd.entity.hospital.EngPtDrug;
import com.xiaojd.entity.hospital.EngPtMessage;
import com.xiaojd.entity.hospital.EngPtPharmacy;
import com.xiaojd.entity.hospital.EngPtUser;
import com.xiaojd.entity.util.Config;
import com.xiaojd.entity.util.PTUSER;
import com.xiaojd.service.hospital.EngPtCfService;
import com.xiaojd.service.hospital.EngPtDeliveryAddressService;
import com.xiaojd.service.hospital.EngPtDeliveryService;
import com.xiaojd.service.hospital.EngPtDispenseService;
import com.xiaojd.service.hospital.EngPtDrugService;
import com.xiaojd.service.hospital.EngPtMessageService;
import com.xiaojd.service.hospital.EngPtPharmacyService;
import com.xiaojd.service.hospital.EngPtUserService;
import com.xiaojd.utils.HisInfoPost;


/**
 * @author  
 * 药易平台WEB端登录
 *
 */

@Controller
public class WebLoginController  {

	Logger loger = LoggerFactory.getLogger(WebLoginController.class);
	@Resource
	EngPtUserService engPtUserService;
	
	//---------------------人员角色------------------
	public static String PT_ADMIN ="1001"; //平台管理员
	public static String PT_COURIER ="1002";//平台配送员 
	
	@Resource
	SessionFactory sessionFactory;
	/**
	 * 将当前数据库连接赋给ConManager,ConManager原生SQl执行
	 */
	@PostConstruct
	private void init(){
		ConManager.sessionFactory = sessionFactory;
	}
	
	@RequestMapping(value = "/")
	public String defaultLogin(ModelMap model) {
			return "yaoyiPt/web/login";
	}
	//-------------------------------------------WEB页面跳转---------------------------

	/**
	 * WEB登录验证
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/loginPt")
	public String loginPt(ModelMap model, EngPtUser user){
		String validStr = "";//orgUserService.isValidUser(user);
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
            return "/yaoyiPt/web/redirect";
        } else {
        	model.put("error", validStr);
            return "/yaoyiPt/web/login";
        }
	}
	/**
	 * 登录成功后根据角色跳转不同的页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/homePt")
	public String home(ModelMap model){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
		String  roleId=user.getRole();		
		String validStr = "";
		if (PTUSER.PT_ADMIN.getCode().equals(roleId)) {
			Config.reRegister();//管理员登录刷新配置
			return "yaoyiPt/web/admin_index";//管理员查询页面
		} else if(PTUSER.PT_PHARMACY_MANAGER.getCode().equals(roleId)) {
			return "yaoyiPt/web/phramacy_index";//药店店长页面
		} else{
			    //角色不对，重新登录
				validStr = "该用户无权查看系统！";
				model.put("error", validStr);
				request.getSession().removeAttribute("XIAOJD-PTUSER");
				request.getSession().setAttribute("XIAOJD-PTURL", "loginPt");
				return "yaoyiPt/web/redirect";//登录跳转
		}	
	}
	/**
	 * session无效后，去除当前用户,退出系统,重新登录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginPtA")
	public String loginPtA(ModelMap model){
/*		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		request.getSession().removeAttribute("XIAOJD-PTUSER");*/
		return "yaoyiPt/web/login";
	}
	   
	/**
	 * 退出，注销用户
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logoutPt")
	 public String logoutPt(ModelMap model){
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			request.getSession().removeAttribute("XIAOJD-PTUSER");	//清除当前用户的Session
			return "yaoyiPt/web/login";
	}
	
	/**
	 * 用户修改密码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateOwnPassword")
	@ResponseBody
	 public Map<String, Object> updateOwnPassword(Long id,String pwd){
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
			Map<String, Object> ret = new HashMap<String, Object>();
			if(user.getId()!=id) {
				   ret.put("success", "false");
				   ret.put("message","不是当前用户！");
			} else {
				user.setPwd(pwd);
				engPtUserService.saveOrUpdataUser(user);
			    ret.put("success", "true");
				ret.put("message","密码修改成功！");
			}
			return ret;
	}
	
	
	
	
}
