package com.xiaojd.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaojd.base.tools.StringUtils;
import com.xiaojd.conn.ConManager;
import com.xiaojd.entity.hospital.EngPtCf;
import com.xiaojd.entity.hospital.EngPtDispense;
import com.xiaojd.entity.hospital.EngPtDrug;
import com.xiaojd.entity.hospital.EngPtMessage;
import com.xiaojd.entity.util.Config;
import com.xiaojd.entity.util.PTSTATUS;
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
 * 药易平台的DLL所有方法都集中在一起
 *
 */

@Controller
public class DLLController  {

	Logger loger = LoggerFactory.getLogger(DLLController.class);
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
	//处方状态

	
	

	//-------------------------------------------DLL接口调用---------------------------
	/**
	 * 医院HIS系统将上次到平台上有处方取消
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/engine/runCancelCf")
	public void engineRunCancelCf( HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String presNo = (String) request.getParameter("presNo");
		String dllEncoding = Config.getValue("engine", "DLLEncoding");
		presNo = java.net.URLDecoder.decode(presNo,dllEncoding);
		String xml ="";
		if(presNo ==null ||"".equals(presNo)) {
			xml =  "<root><isSuccess>0</isSuccess></root>";
		} else{
			response.setContentType("text/html; charset=gbk");
			EngPtCf  ptCf = engPtCfService.loadByIdToAll(presNo);
			if(ptCf == null) {
				xml =  "<root><isSuccess>0</isSuccess></root>";
			}else {
				String status =ptCf.getStatus();	  
				if(status !=null && !"".equals(status)&&("1".equals(status)||"0".equals(status))) {//（0取消 1：新开的药才可以取消
					ptCf.setStatus("0");
					engPtCfService.saveOrUpdate(ptCf);
					xml =  "<root><isSuccess>1</isSuccess></root>";
				} else {
					xml =  "<root><isSuccess>0</isSuccess></root>";
				}
			}
		}
		PrintWriter pw = response.getWriter();
		pw.print(xml);
		pw.flush();
		pw.close();
	}
	
	/**社区医院或药店通过平台调取处方
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/engine/runGetCf")
	public void engineRunＧetCf( HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String dllEncoding =(String) Config.getValue("engine", "DLLEncoding");
		String cardNo =  (String)request.getParameter("cardNo");
		String name =   (String)request.getParameter("name");
		String phoneNo =  (String)request.getParameter("phoneNo");
		String idNo =   (String)request.getParameter("idNo");

		if(idNo ==null) {
			idNo="";
		}  else {
			idNo=java.net.URLDecoder.decode(idNo,dllEncoding);
		}
		idNo = idNo.replaceAll(" ", "");
		if(cardNo == null) {
			cardNo ="";
		}  else {
			cardNo=java.net.URLDecoder.decode(cardNo,dllEncoding);
		}
		cardNo = cardNo.replaceAll(" ", "");
		if(phoneNo ==null) {
			phoneNo ="";
		}  else {
			phoneNo=java.net.URLDecoder.decode(phoneNo,dllEncoding);
		}
		phoneNo = phoneNo.replaceAll(" ", "");
		if(name ==null) {
			name ="";
		}else {
			name=java.net.URLDecoder.decode(name,dllEncoding);
		}
		name = name.replaceAll(" ", "");
		String xml ="<roots><isSuccess>0</isSuccess></roots>";
		if(!("".equals(cardNo)&&"".equals(name)&&"".equals(idNo)&&"".equals(phoneNo))) {
		    xml = "<roots>";
			List<EngPtCf> ptCfList = engPtCfService.loadByCardAndName(cardNo, name, idNo,phoneNo);
			if(ptCfList == null ||ptCfList.size()  ==0) {
				xml +="<isSuccess>0</isSuccess></roots>";
			} else {
				for(int i=0;i<ptCfList.size();i++) {
					   xml +="<root>";
					   xml +=ptCfList.get(i).toXml();
					  List<EngPtDrug> ptDrugList = engPtDrugService.loadByCfid(ptCfList.get(i).getId());
					  xml += " <prescriptions>";
					  for(int j=0;j<ptDrugList.size();j++) {
						  xml += ptDrugList.get(j).toXml("社区医院");
					  }
					  xml += " </prescriptions>";
					  
					 List<EngPtMessage> ptMessageList = engPtMessageService.loadByCfid(ptCfList.get(i).getId());
					 xml += " <infos>";
					 for(int n=0;n<ptMessageList.size();n++) {
						  xml += ptMessageList.get(n).toXml();
					 }
					 xml += " </infos>";
					 xml +="</root>";
				}
				xml +="<isSuccess>1</isSuccess></roots>";
			}
		}
			
		response.setContentType("text/html; charset=gbk");
		PrintWriter pw = response.getWriter();
		pw.print(xml);
		pw.flush();
		pw.close();
	}
	
	
	/**社区医院将从平台调取的处方处理后，把处理结果反馈给平台
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/engine/runBackCf")
	public void engineRunBackCf( HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String dllEncoding = Config.getValue("engine", "DLLEncoding");
		String presNo =  java.net.URLDecoder.decode((String) request.getParameter("presNo"),dllEncoding);
		String status =  java.net.URLDecoder.decode((String) request.getParameter("status"),dllEncoding);
		String hospital =  java.net.URLDecoder.decode((String) request.getParameter("hospital"),dllEncoding);
		String dept =  java.net.URLDecoder.decode((String) request.getParameter("dept"),dllEncoding);
		String doctor =  java.net.URLDecoder.decode((String) request.getParameter("doctor"),dllEncoding);
		String comment =  java.net.URLDecoder.decode((String) request.getParameter("comment"),dllEncoding);
		String xml =  "<root><isSuccess>1</isSuccess></root>";
		if(presNo ==null ||"".endsWith(presNo)||status ==null ||"".endsWith(status)||hospital ==null ||"".endsWith(hospital)||dept ==null ||"".endsWith(dept)||doctor ==null ||"".endsWith(doctor)) {
			xml ="<root><isSuccess>0</isSuccess></root>";
		} else {
			EngPtCf  ptCf = engPtCfService.loadByIdToHospital(presNo);
			if(	ptCf!=null &&"1".equals(ptCf.getStatus())) {
				EngPtDispense dispense = new EngPtDispense(ptCf.getPresNo(),ptCf.getStatus());
				ptCf.setStatus(status);//修改处方状态
				engPtCfService.saveOrUpdate(ptCf);
				dispense.setHospital(hospital);
				dispense.setComment(comment);
				dispense.setDepartment(dept);
				dispense.setDoctor(doctor);
			    engPtDispenseService.saveOrUpdate(dispense);
			} else {
				xml ="<root><isSuccess>0</isSuccess></root>";//不是新开的药，无法修改
			}
		}
		response.setContentType("text/html; charset=gbk");
		PrintWriter pw = response.getWriter();
		pw.print(xml);
		pw.flush();
		pw.close();
	}
	
	/**医院定时从平台读取处方的状态，然后刷新医院his系统的处方状态
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/engine/runRefreshCf")
	public void engineRunRefreshCf( HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String dllEncoding = Config.getValue("engine", "DLLEncoding");
		String presNos =  java.net.URLDecoder.decode((String) request.getParameter("presNos"),dllEncoding);
		String xml ="<root><infos>";
		if(presNos == null ||"".equals(presNos)) {
			xml ="<root><isSuccess>0</isSuccess></root>";
		} else {
			presNos =presNos.replaceAll(",", "','");
			presNos = "'" +presNos +"'";
			List<EngPtCf> ptCfs = engPtCfService.loadByPresNos(presNos);
			if(ptCfs == null ||ptCfs.size()==0) {
				xml ="<root><isSuccess>0</isSuccess></root>";
			} else {
	           for(int i=0;i<ptCfs.size();i++)  {
	        	   EngPtDispense  ptDispense = engPtDispenseService.loadByPresNo(ptCfs.get(i).getPresNo());
	        	   if(ptDispense == null) {
	        		   ptDispense = new EngPtDispense(ptCfs.get(i).getPresNo(),ptCfs.get(i).getStatus());
	        	   }
	        	   ptDispense.setStatus(ptCfs.get(i).getStatus());
			       xml += ptDispense.toXml();
	           }
				xml +="</infos><isSuccess>1</isSuccess></root>";
			}
		}
		response.setContentType("text/html; charset=gbk");
		PrintWriter pw = response.getWriter();
		pw.print(xml);
		pw.flush();
		pw.close();
	}
	
	
	@RequestMapping(value = "engine/runUpCf")
	public void engRunUpCf(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream is = null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			is = request.getInputStream();
			String engineEncoding = Config.getValue("engine", "hisEngineEncoding");
			InputStreamReader isr = new InputStreamReader(is, StringUtils.isEmpty(engineEncoding)?"GBK":engineEncoding);
			int ch;
			StringBuilder sbuf = new StringBuilder();
			while((ch = isr.read()) != -1){
				sbuf.append((char)ch);
			}
			String sb = sbuf.toString(); 
			String hisEngineLogFile = Config.getValue("engine", "hisEngineLog");
			if(StringUtils.isNotEmpty(hisEngineLogFile)){
				FileOutputStream fos = null;
				try{
					File logFile = new File(hisEngineLogFile);
					if(logFile != null){
						if(!logFile.getParentFile().exists()){
							logFile.getParentFile().mkdirs();
						}
						if(!logFile.exists()){
							logFile.createNewFile();
						}
						fos = new FileOutputStream(logFile);
						fos.write(sb.getBytes());
						fos.flush();
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("日志文件创建失败！");
				}finally{
					if(fos != null){
						try {
							fos.close();
						} catch (Exception e) {
							
						}
					}
				}//try-catch
			}
			

            String xml ="";
            EngPtCf cf =new EngPtCf();
            List<EngPtDrug> drugs = new ArrayList<EngPtDrug>();
			xml = checkPtCf(cf,drugs,sb.replaceAll("<root>", "").replaceAll("</root>",""));
			
			xml = xml.replaceAll("\\n", "");
			xml = xml.replaceAll("\\[[a-zA-Z0-9]+\\-.[a-zA-Z0-9]+\\]", "");
			String sex = cf.getSex();
			if(sex!= null &&("男".equals(sex)||"M".equals(sex))) {
				sex ="0";
			} else { sex ="1";}
			String pregnancy = cf.getPregnancy();
			String breastFeeding = cf.getBreastFeeding();
			String temp ="";
			if(pregnancy != null && "true".equals(pregnancy) && "TRUE".equals(pregnancy)) {
				temp ="1";
			}
			if(breastFeeding != null && "true".equals(breastFeeding) && "TRUE".equals(breastFeeding)) {
				temp ="0";
			}
			
	        String param = "<root>"
				        		+ "<Pre>"
				        		+ "<PreInfoPreNo=\""+cf.getId()+"\""
				        		+ "PreCode=\""+cf.getId()+"\""
				        		+ "PreType=\"1\""
				        		+ "InDate=\""+cf.getPresDateTime()+"\""
				        		+ "OutDate=\""+cf.getPresDateTime()+"\""
				        		+ "DoctCode=\""+cf.getDocId()+"\""
				        		+ "DeptCode=\""+cf.getDepartId()+"\""
				        		+ "PatientName=\""+cf.getName()+"\""
				        		+ "PatientType=\""+cf.getPayType()+"\""
				        		+ "Birthday=\"\""
				        		+ "Gender=\""+sex+"\""
				        		+ "LiverStatus=\"\""
				        		+ "KidneyStatus=\"\""
				        		+ "WomanStatus=\""+temp+"\""
				        		+ "AllegeInfo=\"\""
				        		+ "BlCode=\"\""
				        		+ "Branch=\"99\"/>"
				        		+ "</Pre>"
	        		+ "<ICD>"
	        		+ "<ICDInfoPreNo=\""+cf.getId()+"\""
	        		+ "ICDCode=\"\""
	        		+ "ICDName=\""+cf.getDiagnose()+"\"/>"
	        		+ "</ICD>";
	            for (int i=0;i<drugs.size();i++) {
                    EngPtDrug drug =drugs.get(i);
                    String dose = drug.getAdminDose();
                    String s=dose.replaceAll("[^0-9]","#");
                    String dcl ="";
                    String dclUnit ="";
                    if(s.indexOf("#")>-1) {
                    	dcl = dose.substring(0,s.lastIndexOf("#"));
                    	dclUnit = dose.substring(s.lastIndexOf("#"));
                    }
                    if(dcl.startsWith(".")) {
                    	dcl +="0"+dcl;
                    }
                    
	        	 	String tempStr = "<Drug>"
	        		+ "<DrugInfoPreNo=\""+cf.getId()+"\""
	        		+ "OrderCode=\"\""
	        		+ "OrderType=\"0\""
	        		+ "OrderDate=\""+cf.getPresDateTime()+"\""
	        		+ "OrderDoctor=\""+cf.getDocId()+"\""
	        		+ "IsCurrent=\"0\""
	        		+ "DrugCode=\""+drug.getDrug()+"\""
	        		+ "DrugName=\""+drug.getDrugName()+"\""
	        		+ "DrugSpec=\""+drug.getSpec()+"\""
	        		+ "UsingType=\""+drug.getAdminRoute()+"\""
	        		+ "Frequency=\""+drug.getAdminFrequency()+"\""
	        		+ "FreqTimes=\"\""
	        		+ "Dcl=\""+dcl+"\""
	        		+ "DclUnit=\""+dclUnit+"\""
	        		+ "Qnty=\""+drug.getQuantity()+"\""
	        		+ "QntyUnit=\""+drug.getDispenseUnit()+"\""
	        		+ "Price=\""+drug.getUnitPrice()+"\""
	        		+ "GroupNo=\"\""
	        		+ "BeginUseDate=\"\""
	        		+ "EndUseDate=\"\"/>"
	        		+ "</Drug>";
	        	 	 param +=tempStr;
	            }
	            param +="</root>";
	           String switchEngine = Config.getValue("engine", "switchEngine");
	           int n =0;
	           if(xml.indexOf("<isSuccess>1</isSuccess>")>-1) {
	        	   if(switchEngine!= null &&"1".equals(switchEngine)) {//启用·山东审方
		        	   //System.out.println("ok");
		        	    n = HisInfoPost.INSTANCE.VanAnaData("1","0",param);
		        	    xml = "<root><info>" +n+ "</info><isSuccess>1</isSuccess></root>";
		           } 
	   			cf.setResult(n+"");
				engPtCfService.saveOrUpdate(cf); 
	           } else {
	        	   xml =" <root><info>0</info><isSuccess>0</isSuccess></root>";
	           }
	          
/*	        0 该处方没问题；
	        1 该处方有严重问题；
	        2 该处方有一般问题；
	        3 该处方有其他问题。
	        4 该处方存在很严重问题，不能保存处方。*/
	        
	        //System.out.println(cf.getId()+":状态"+n);
			
			
			if(StringUtils.isNotEmpty(hisEngineLogFile)){
				FileOutputStream fos = null;
				try{
					hisEngineLogFile = hisEngineLogFile.replace("/", "\\");
					File logFile = new File(hisEngineLogFile.substring(0,hisEngineLogFile.lastIndexOf("\\"))+"\\output.log");
					if(logFile != null){
						if(!logFile.getParentFile().exists()){
							logFile.getParentFile().mkdirs();
						}
						if(!logFile.exists()){
							logFile.createNewFile();
						}
						fos = new FileOutputStream(logFile);
						fos.write(xml.getBytes());
						fos.flush();
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("日志文件创建失败！");
				}finally{
					if(fos != null){
						try {
							fos.close();
						} catch (Exception e) {
							
						}
					}
				}//try-catch
			}
			response.setContentType("text/html; charset=gbk");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			is.close();
		}
	}
	
	@RequestMapping(value = "engine/runUpEmr")
	public void engRunUpEmr(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream is = null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			is = request.getInputStream();
			String engineEncoding = Config.getValue("engine", "hisEngineEncoding");
			InputStreamReader isr = new InputStreamReader(is, StringUtils.isEmpty(engineEncoding)?"GBK":engineEncoding);
			int ch;
			StringBuilder sbuf = new StringBuilder();
			while((ch = isr.read()) != -1){
				sbuf.append((char)ch);
			}
			String sb = sbuf.toString(); 
			String hisEngineLogFile = Config.getValue("engine", "hisEngineLog");
			if(StringUtils.isNotEmpty(hisEngineLogFile)){
				FileOutputStream fos = null;
				try{
					File logFile = new File(hisEngineLogFile);
					if(logFile != null){
						if(!logFile.getParentFile().exists()){
							logFile.getParentFile().mkdirs();
						}
						if(!logFile.exists()){
							logFile.createNewFile();
						}
						fos = new FileOutputStream(logFile);
						fos.write(sb.getBytes());
						fos.flush();
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("日志文件创建失败！");
				}finally{
					if(fos != null){
						try {
							fos.close();
						} catch (Exception e) {
							
						}
					}
				}//try-catch
			}
			
			String xml = "";	
			xml = xml.replaceAll("\\n", "");
			xml = xml.replaceAll("\\[[a-zA-Z0-9]+\\-.[a-zA-Z0-9]+\\]", "");
			
			xml = "<root>" + xml + "</root>";
			
			if(StringUtils.isNotEmpty(hisEngineLogFile)){
				FileOutputStream fos = null;
				try{
					hisEngineLogFile = hisEngineLogFile.replace("/", "\\");
					File logFile = new File(hisEngineLogFile.substring(0,hisEngineLogFile.lastIndexOf("\\"))+"\\output.log");
					if(logFile != null){
						if(!logFile.getParentFile().exists()){
							logFile.getParentFile().mkdirs();
						}
						if(!logFile.exists()){
							logFile.createNewFile();
						}
						fos = new FileOutputStream(logFile);
						fos.write(xml.getBytes());
						fos.flush();
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("日志文件创建失败！");
				}finally{
					if(fos != null){
						try {
							fos.close();
						} catch (Exception e) {
							
						}
					}
				}//try-catch
			}
			response.setContentType("text/html; charset=gbk");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			is.close();
		}
	}
	
	
	@RequestMapping(value = "engine/runUpLis")
	public void engRunUpLis(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream is = null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			is = request.getInputStream();
			String engineEncoding = Config.getValue("engine", "hisEngineEncoding");
			InputStreamReader isr = new InputStreamReader(is, StringUtils.isEmpty(engineEncoding)?"GBK":engineEncoding);
			int ch;
			StringBuilder sbuf = new StringBuilder();
			while((ch = isr.read()) != -1){
				sbuf.append((char)ch);
			}
			String sb = sbuf.toString(); 
			String hisEngineLogFile = Config.getValue("engine", "hisEngineLog");
			if(StringUtils.isNotEmpty(hisEngineLogFile)){
				FileOutputStream fos = null;
				try{
					File logFile = new File(hisEngineLogFile);
					if(logFile != null){
						if(!logFile.getParentFile().exists()){
							logFile.getParentFile().mkdirs();
						}
						if(!logFile.exists()){
							logFile.createNewFile();
						}
						fos = new FileOutputStream(logFile);
						fos.write(sb.getBytes());
						fos.flush();
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("日志文件创建失败！");
				}finally{
					if(fos != null){
						try {
							fos.close();
						} catch (Exception e) {
							
						}
					}
				}//try-catch
			}
			
			String xml = "";
			xml = xml.replaceAll("\\n", "");
			xml = xml.replaceAll("\\[[a-zA-Z0-9]+\\-.[a-zA-Z0-9]+\\]", "");
			
			xml = "<root>" + xml + "</root>";
			
			if(StringUtils.isNotEmpty(hisEngineLogFile)){
				FileOutputStream fos = null;
				try{
					hisEngineLogFile = hisEngineLogFile.replace("/", "\\");
					File logFile = new File(hisEngineLogFile.substring(0,hisEngineLogFile.lastIndexOf("\\"))+"\\output.log");
					if(logFile != null){
						if(!logFile.getParentFile().exists()){
							logFile.getParentFile().mkdirs();
						}
						if(!logFile.exists()){
							logFile.createNewFile();
						}
						fos = new FileOutputStream(logFile);
						fos.write(xml.getBytes());
						fos.flush();
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("日志文件创建失败！");
				}finally{
					if(fos != null){
						try {
							fos.close();
						} catch (Exception e) {
							
						}
					}
				}//try-catch
			}
			response.setContentType("text/html; charset=gbk");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			is.close();
		}
	}


            static private String checkPtCf(EngPtCf cf,List<EngPtDrug> drugs,String s) {
            	Connection con = null;
    			String presNo = StringUtils.find(s, "<presNo>(###*?)</presNo>");
				try {
					con = ConManager.getConn();
					String sql4 = "select status from eng_pt_cf where pres_no = ? ";
					PreparedStatement pst4 = con.prepareStatement(sql4);
					pst4.setString(1, presNo);
					ResultSet rs4 = pst4.executeQuery();
					if (rs4.next()) {
						String status = rs4.getString("status");
					   if(PTSTATUS.CF_NEW.getStatus().equals(status)||PTSTATUS.CF_CANCEL.getStatus().equals(status)||PTSTATUS.CF_DISPENSE_RETURN.getStatus().equals(status) ) {	//只有0取消/1新建/3退方的处方才可以修改
							writePtDB( cf, drugs,s);
							return "<info>0</info><isSuccess>1</isSuccess>";
						} else {
							return s+ "<info>0</info><isSuccess>0</isSuccess>";
						}
					} else {
						   writePtDB( cf, drugs,s);//只有新建的处方才可以修改
						return "<info>0</info><isSuccess>1</isSuccess>";
					}	
					
				} catch (Exception a) {
					a.printStackTrace();
				} finally {
					ConManager.close(con);
				}
				return "<info>0</info><isSuccess>0</isSuccess>";
            }
			static private void writePtDB(EngPtCf cf,List<EngPtDrug> drugs, String s) {
				
			Connection con = null;
			String presNo = StringUtils.find(s, "<presNo>(###*?)</presNo>");
			String patientNo = StringUtils.find(s, "<patientNo>(###*?)</patientNo>");
			String presDatetime = StringUtils.find(s,"<presDatetime>(###*?)</presDatetime>");
			if ("".equals(presNo))
				return;
			if (presNo.startsWith("Z0") || presNo.startsWith("z0")) {
				presNo = "Z" + patientNo + presDatetime.substring(0, 10);
			}
			try {
				con = ConManager.getConn();
				if (StringUtils.find(s, "<prescriptions>(###*)</prescriptions>").trim().length() == 0) {
					return;
				}
				
				//覆盖以前处方记录
				String sql1 = "delete from eng_pt_drug where cfid =?";
				String sql2 = "delete from eng_pt_cf where pres_no = ?";
				String sql3 = "delete from eng_pt_message where cf_id =?";
				String sql4 = "select id from eng_pt_cf where pres_no = ?";
				//String sql5 = "delete from eng_pt_dispense where pres_no =?";

				PreparedStatement pst4 = con.prepareStatement(sql4);
				pst4.setString(1, presNo);
				ResultSet rs4 = pst4.executeQuery();
				if (rs4.next()) {
					String cfId = rs4.getString("id");
					PreparedStatement pst1 = con.prepareStatement(sql1);
					pst1.setString(1, cfId);
					pst1.execute();
					pst1.close();
					PreparedStatement pst2 = con.prepareStatement(sql2);
					pst2.setString(1, presNo);
					pst2.execute();
					pst2.close();
					PreparedStatement pst3 = con.prepareStatement(sql3);
					pst3.setString(1, cfId);
					pst3.execute();
					pst3.close();
					//PreparedStatement pst5 = con.prepareStatement(sql5);
					//pst5.setString(1, cfId);
					//pst5.execute();
					//pst5.close();
				}
				rs4.close();
				pst4.close();
				
				String t = StringUtils.find(s, "<patient>(###*?)</patient>");
				cf.setId(presNo);
				cf.setAddress(StringUtils.find(t, "<address>(###*)</address>"));
				cf.setAge(StringUtils.find(t, "<age>(###*)</age>"));
				cf.setAllergyList(StringUtils.find(t,"<allergyList>(###*)</allergyList>"));
				cf.setBedNo(StringUtils.find(t, "<bedNo>(###*)</bedNo>"));
				cf.setBirthWeight(StringUtils.find(t,"<birthWeight>(###*)</birthWeight>"));
				cf.setBreastFeeding(StringUtils.find(t,"<breastFeeding>(###*)</breastFeeding>"));
				cf.setCcr(StringUtils.find(t, "<ccr>(###*)</ccr>"));
				cf.setDepartId(StringUtils.find(t, "<departID>(###*)</departID>"));
				cf.setDepartment(StringUtils.find(t,"<department>(###*?)</department>"));
				cf.setDiagnose(StringUtils.find(t, "<diagnose>(###*)</diagnose>"));
				cf.setDialysis(StringUtils.find(t, "<dialysis>(###*)</dialysis>"));
				cf.setDocId(StringUtils.find(t, "<docID>(###*)</docID>"));
				cf.setDocName(StringUtils.find(t, "<docName>(###*)</docName>"));
				cf.setHeight(StringUtils.find(t, "<height>(###*)</height>"));
				cf.setIdCard(StringUtils.find(t, "<IDCard>(###*)</IDCard>"));
				cf.setName(StringUtils.find(t, "<name>(###*)</name>"));
				cf.setPatientNo(StringUtils.find(t, "<patientNo>(###*)</patientNo>"));
				cf.setPayType(StringUtils.find(t, "<payType>(###*)</payType>"));
				cf.setPhoneNo(StringUtils.find(t, "<phoneNo>(###*)</phoneNo>"));
				cf.setPregnancy(StringUtils.find(t, "<pregnancy>(###*)</pregnancy>"));
				String pdt = StringUtils.find(t,"<presDatetime>(###*)</presDatetime>");
				cf.setCreateDate(new Timestamp(System.currentTimeMillis()));
				cf.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				try {
					cf.setPresDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(StringUtils.double2Date(Double.parseDouble(pdt))));
				} catch (Exception e) {
					cf.setPresDateTime(pdt);
				}
				cf.setPresNo(presNo);
				cf.setPresType(StringUtils.find(t,"<presType>(###*)</presType>"));
				cf.setProxIdCard(StringUtils.find(t,"<proxIDCard>(###*)</proxIDCard>"));
				cf.setProxName(StringUtils.find(t, "<proxName>(###*)</proxName>"));
				cf.setSex(StringUtils.find(t, "<sex>(###*)</sex>"));
				cf.setTimeOfPreg(StringUtils.find(t,"<timeOfPreg>(###*)</timeOfPreg>"));
				cf.setTotalAmount(StringUtils.find(t,"<totalAmount>(###*)</totalAmount>"));
				cf.setWeight(StringUtils.find(t, "<weight>(###*)</weight>"));
				cf.setPresSource(StringUtils.find(t,"<presSource>(###*)</presSource>"));
				cf.setPharmChkId(StringUtils.find(t,"<pharmChkId>(###*)</pharmChkId>"));
				cf.setPharmChkName(StringUtils.find(t,"<pharmChkName>(###*)</pharmChkName>"));
				cf.setPharmChkTitle(StringUtils.find(t,"<pharmChkTitle>(###*)</pharmChkTitle>"));
				cf.setDocTitle(StringUtils.find(t, "<docTitle>(###*)</docTitle>"));
				cf.setCardNo(StringUtils.find(t, "<cardNo>(###*)</cardNo>"));
				cf.setAst(StringUtils.find(t, "<ast>(###*)</ast>"));
				cf.setScr(StringUtils.find(t, "<scr>(###*)</scr>"));
				cf.setAlt(StringUtils.find(t, "<alt>(###*)</alt>"));
				cf.setBsa(StringUtils.find(t, "<bsa>(###*)</bsa>"));
				cf.setDrugSensivity(StringUtils.find(t, "<drugSensivity>(###*)</drugSensivity>"));
				cf.setStatus("1");//新开
				cf.setHospitalOrPharmacy(StringUtils.find(t, "<hospitalOrPharmacy>(###*)</hospitalOrPharmacy>"));
				cf.setInsurePerson(StringUtils.find(t, "<insurePerson>(###*)</insurePerson>"));
				cf.save(con);

				t = StringUtils.find(s, "<prescriptions>(###*)</prescriptions>");
				Iterator<String> it = StringUtils.findAll(t,"<prescription>(###*?)</prescription>");
				int i = 0;
				while (it.hasNext()) {
					String p = it.next();			
					EngPtDrug item = new EngPtDrug();
					item.setId(cf.getId()+"_"+(i+1));
					item.setCfid(cf.getId());
					item.setDrug(StringUtils.find(p, "<drug>(###*)</drug>"));
					item.setDrugName(StringUtils.find(p,"<drugName>(###*)</drugName>"));
					item.setPrepForm(StringUtils.find(p,"<prepForm>(###*)</prepForm>"));
					item.setStartDate(StringUtils.find(p,"<startTime>(###*)</startTime>"));
					item.setType(StringUtils.find(p, "<type>(###*)</type>"));
					item.setAdminArea(StringUtils.find(p,"<adminArea>(###*)</adminArea>"));
					item.setAdminDose(StringUtils.find(p,"<adminDose>(###*)</adminDose>"));
					item.setAdminFrequency(StringUtils.find(p,"<adminFrequency>(###*)</adminFrequency>"));
					item.setAdminMethod(StringUtils.find(p,"<adminMethod>(###*)</adminMethod>"));
					item.setAdminRoute(StringUtils.find(p,"<adminRoute>(###*)</adminRoute>"));
					item.setContinueDay(StringUtils.find(p,"<continueDays>(###*)</continueDays>"));
					item.setFirstUse(StringUtils.find(p,"<firstUse>(###*)</firstUse>"));
					item.setSkinTest(StringUtils.find(p,"<skinTest>(###*)</skinTest>"));
					item.setGroupNo(StringUtils.find(p, "<groupNo>(###*)</groupNo>"));
					item.setAdminMethod(StringUtils.find(p, "<adminMethod>(###*)</adminMethod>"));
					item.setPack(StringUtils.find(p, "<package>(###*)</package>"));
					item.setPackUnit(StringUtils.find(p,"<packUnit>(###*)</packUnit>"));	
					item.setRegName(StringUtils.find(p, "<regName>(###*)</regName>"));
					item.setSpec(StringUtils.find(p,"<specification>(###*)</specification>"));
					item.setQty(StringUtils.find(p,"<qty>(###*)</qty>"));
					item.setQtyUnit(StringUtils.find(p,"<qtyUnit>(###*)</qtyUnit>"));
					item.setContent(StringUtils.find(p,"<content>(###*)</content>"));
					item.setContentUnit(StringUtils.find(p,"<contentUnit>(###*)</contentUnit>"));
					item.setQuantity(StringUtils.find(p,"<quantity>(###*)</quantity>"));
					item.setRealityQuantity(StringUtils.find(p,"<quantity>(###*)</quantity>"));
					item.setDispenseUnit(StringUtils.find(p,"<dispenseUnit>(###*)</dispenseUnit>"));
					item.setUnitPrice(StringUtils.find(p,"<unitPrice>(###*)</unitPrice>"));
					item.setAmount(StringUtils.find(p, "<amount>(###*)</amount>"));
					item.setRealityAmount(StringUtils.find(p, "<amount>(###*)</amount>"));
					item.save(con);
					drugs.add(item);
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ConManager.close(con);
			}
		}
			
}
