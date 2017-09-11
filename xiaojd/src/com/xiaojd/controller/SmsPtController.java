package com.xiaojd.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xiaojd.entity.hospital.EngPtSms;
import com.xiaojd.entity.hospital.EngPtUser;
import com.xiaojd.entity.util.Config;
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
import com.xiaojd.utils.SmsPtUtils;

/**
 * @author CZ
 *
 */

@Controller
public class SmsPtController {

	Logger loger = LoggerFactory.getLogger(SmsPtController.class);
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

	// -------------------------------------------业务逻辑-----------------------------------------
	/**
	 * 根据病人基本信息查询处方
	 * 
	 * @param 查询字段
	 * @param 查询的值
	 * @return
	 */
	@RequestMapping(value = "loadSms")
	@ResponseBody
	public Map<String, Object> loadSmsInfo(String type, String value) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		EngPtUser userCurrent = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
		if (PTUSER.CheckRole(userCurrent.getRole(), PTUSER.PT_COURIER.getCode()) == true) {
			return null;
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, String> paramValues = new HashMap<String, String>();
		String sql = "select * from eng_pt_sms sms left join (select ph.pharmacy, us.id uid, us.name from eng_pt_user us, eng_pt_pharmacy ph where ph.id = us.pharmacy_id ) b on sms.sender_id = b.uid left join (select id cid,name receiver, phone_no from eng_pt_cf) cf on sms.cf_id = cf.cid";
		// String sql = "select * from eng_pt_sms";
		long size = engPtSmsService.countBySql(sql, paramValues);
		List ptSmsList = engPtSmsService.executeSQLQuery(sql, 0, 20, paramValues);
		for (int i = 0; i < ptSmsList.size(); i++) {
			Map<String, Object> sms = (Map) ptSmsList.get(i);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sms.put("sender_time", formatter.format(sms.get("sender_time")));
		}
		if (ptSmsList != null && ptSmsList.size() > 0) {
			ret.put("row_count", size); // row counts
			ret.put("current_page", 1); // current page
			ret.put("total_page", (int) (size / 10)); // total pages
			ret.put("data", ptSmsList);
			ret.put("success", "true");
			return ret;
		}
		ret.put("success", "false");
		return ret;
	}
}
