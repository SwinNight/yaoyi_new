package com.xiaojd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.xiaojd.entity.hospital.SysConfig;
import com.xiaojd.entity.util.Config;
import com.xiaojd.exception.BusinessException;
import com.xiaojd.service.hospital.SysConfigService;
import com.xiaojd.utils.PageUtil;

@Controller
public class ConfigController {

	Logger loger = LoggerFactory.getLogger(ConfigController.class);
	
	@Resource
	SysConfigService sysCfgService;

	@RequestMapping(value = "/getAllSysConfigs")
	@ResponseBody
	public Map<String, Object> getAllSysConfigs(@RequestParam Map<String, Object> params) throws Exception {
		loger.debug("Request URL: /getAllSysConfigs");
		loger.debug("Params: " + params);
		Map<String, Object> maps = null;
		List<SysConfig> allCfgs = null;
		try {
			PageUtil p = new PageUtil(params);
			allCfgs = sysCfgService.getAllSysConfigs(p.getParameters(), p.getSort(), p.getFirstResult(), p.getMaxResult());
			int size = sysCfgService.getTotalPages(p.getParameters(), p.getSort());
			int total = p.getTotalPages(size);
			if(size > 0){
				maps = new HashMap<String, Object>();
				maps.put("records", size); //row counts
				maps.put("page", p.getPage()); //current page
				maps.put("total", total); //total pages
				maps.put("rows", allCfgs);
			}
		} catch (Exception e) {
			loger.debug("Exception: " + e.getMessage());
			throw new BusinessException("获取系统设置出错");
		}
		return maps;
	}
	
	@RequestMapping(value = "/saveOrUpdateSysConfig")
	@ResponseBody
	public Map<String, Object> saveOrUpdateSysConfig(SysConfig cfg){
		loger.debug("Request URL: /saveOrUpdateSysConfig");
		loger.debug("Params: " + cfg);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String oper = request.getParameter("oper");
			
			if(oper != null && oper.equals("add")) { //Add
				loger.debug("Operator: Add!");
				Long newId = sysCfgService.addSysConfig(cfg);
				resultMap.put("newid", newId);
			} else if(oper != null && oper.equals("del")) { //Delete
				loger.debug("Operator: Delete!");
				sysCfgService.deleteSysConfig(cfg.getId());
			} else { //Update
				loger.debug("Operator: Update!");
				sysCfgService.saveOrUpdateSysConfig(cfg);
			}
			Config.reRegister();
			resultMap.put("status", "success");
			return resultMap;
		} catch (Exception e) {
			loger.debug("Exception: " + e.getMessage());
			resultMap.put("status", "error");
			resultMap.put("message", "操作系统设置出现错误！");
			return resultMap;
		}
	}
	
	@RequestMapping(value = "/getFlags")
	@ResponseBody
	public List<String> getFlags(){
		loger.debug("Request URL: /getFlags");
		List<String> flags = new ArrayList<String>();
		try {
			flags = sysCfgService.getFlags();
		} catch (Exception e) {
			loger.debug("Exception: " + e.getMessage());
		}
		return flags;
	}
	
	@RequestMapping(value  = "/getSysCfgValue")
	@ResponseBody
	public String getSysCfgValue(String flag, String name){
		return sysCfgService.getSysCfgValue(flag, name);
	}
	
	@RequestMapping(value  = "/getSysValue")
	@ResponseBody
	public String getSysValue(){
		return sysCfgService.getSysValue();
	}
	
}
