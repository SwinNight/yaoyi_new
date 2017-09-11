package com.xiaojd.entity.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xiaojd.conn.ConManager;
import com.xiaojd.entity.util.PTSTATUS;

/**
 * EngCf entity. @author MyEclipse Persistence Tools 平台处方
 */
@Entity
@Table(name = "eng_pt_cf", catalog = "sz_hospital")
public class EngPtCf implements java.io.Serializable {

	// Fields

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 30)
	private String id;

	@Column(name = "depart_id", length = 45)
	private String departId;// 科室id

	@Column(name = "department", length = 100)
	private String department;// 科室

	@Column(name = "bed_no", length = 20)
	private String bedNo;// 床位号

	@Column(name = "age", length = 10)
	private String age;// 病人年龄

	@Column(name = "pres_type", length = 45)
	private String presType;// 处方类型

	@Column(name = "pres_date_time", length = 45)
	private String presDateTime;// 处方时间

	@Column(name = "sex", length = 10)
	private String sex;// 性别

	@Column(name = "pay_type", length = 145)
	private String payType;// 付费类型

	@Column(name = "patient_no", length = 45)
	private String patientNo;// 病人号

	@Column(name = "pres_no", length = 45)
	private String presNo;// 处方号

	@Column(name = "name", length = 20)
	private String name;// 病人姓名

	@Column(name = "diagnose", length = 100)
	private String diagnose;// 诊断

	@Column(name = "address", length = 200)
	private String address;// 病人地址

	@Column(name = "id_card", length = 40)
	private String idCard;// 身份证号
	
	@Column(name = "card_no", length = 40)
	private String cardNo;// 医保卡号
	
	@Column(name = "insure_person", length = 40)
	private String insurePerson;// 社保人员编号
	

	@Column(name = "phone_no", length = 60)
	private String phoneNo;// 电话号码

	@Column(name = "height", length = 20)
	private String height;// 身高

	@Column(name = "weight", length = 20)
	private String weight;// 体重

	@Column(name = "birth_weight", length = 20)
	private String birthWeight;// 出生时体重


	@Column(name = "allergy_list", length = 100)
	private String allergyList;// 过敏药物列表

	@Column(name = "pregnancy", length = 10)
	private String pregnancy;// 是否怀孕

	@Column(name = "time_of_preg", length = 10)
	private String timeOfPreg;// 孕期

	@Column(name = "breast_feeding", length = 10)
	private String breastFeeding;// 是否哺乳

	@Column(name = "dialysis", length = 10)
	private String dialysis;//

	@Column(name = "prox_name", length = 20)
	private String proxName;// 代办人

	@Column(name = "prox_id_card", length = 40)
	private String proxIdCard;// 代办人身份证号码

	@Column(name = "doc_id", length = 20)
	private String docId;// 医生工号

	@Column(name = "doc_name", length = 20)
	private String docName;// 医生姓名
	
	@Column(name = "doc_title", length = 100)
	private String docTitle;// 医生职称

	@Column(name = "total_amount", length = 20)
	private String totalAmount;// 处方金额

	@Column(name = "pres_source", length = 100)
	private String presSource;// 处方来源 急诊、门诊

	@Column(name = "pharm_chk_id", length = 100)
	private String pharmChkId;

	@Column(name = "pharm_chk_name", length = 100)
	private String pharmChkName;// 核对药师
	
	@Column(name = "pharm_chk_title", length = 100)
	private String pharmChkTitle;// 核对药师职称
	
	@Column(name = "scr", length = 100)
	private String scr;
	
	@Column(name = "ccr", length = 10)
	private String ccr;
	
	@Column(name = "ast", length = 10)
	private String ast;//天门冬氨酸转氨酶
	
	@Column(name = "alt", length = 10)
	private String alt;//丙氨酸转氨酶
	
	@Column(name = "bsa", length = 10)
	private String bsa;//

	@Column(name = "drugSensivity", length = 10)
	private String drugSensivity;//菌检
	


	@Column(name = "flag1", length = 100)
	private String flag1;// 抗菌药物标记

	@Column(name = "flag2", length = 100)
	private String flag2;// 注射给药标记

	@Column(name = "flag3", length = 100)
	private String flag3;//
	

	@Column(name = "create_date", length = 19)
	private Timestamp createDate;

	@Column(name = "update_date", length = 19)
	private Timestamp updateDate;
	
	@Column(name = "status", length = 10)
	private String status;//  
	
	@Column(name = "hospital_or_pharmacy", length = 20)
	private String hospitalOrPharmacy;// 处方到药店，还是社区
	@Column(name = "result", length = 4)
	private String result;// 处方审核结果
	
	@Transient
	@Column(name = "statusName", length = 10)
	private String statusName;
	
	
	@Column(name = "aduit_drug_person", length = 20)
	private String aduitDrugPerson;//审核人
	
	public String getAduitDrugPerson() {
		return aduitDrugPerson;
	}

	public void setAduitDrugPerson(String aduitDrugPerson) {
		this.aduitDrugPerson = aduitDrugPerson;
	}
	
	@Column(name = "cancel_remark", length = 20)
	private String cancelRemark;//医院管理端取消处方，备注
	
	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	@Transient
	private String doctorBmp;
	
	public String getDoctorBmp() {
		this.doctorBmp = this.docId+"_"+this.docName.replaceAll("//*", "");
		return doctorBmp;
	}
	@Transient
	private String presDate;
	
	public String getPresDate() {
	   String temp =this.presDateTime.replaceAll("-",".");
	   if(temp.length()>10) {
		   temp = temp.substring(0, 10);
	   }
		return temp;
	}



	// Constructors
	public String getStatusName() {
		return PTSTATUS.getNameByStatus(this.status);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/** default constructor */
	public EngPtCf() {
	}

	/** full constructor */
	public EngPtCf(String departId, String department, String bedNo, String age,
			String presType, String presDateTime, String sex, String payType,
			String patientNo, String presNo, String name, String diagnose,
			String address, String idCard, String card_no, String phoneNo, String height,
			Timestamp createDate, Timestamp updateDate, 
		    String weight, String birthWeight, 
			String allergyList, String pregnancy, String timeOfPreg,
			String breastFeeding, String dialysis, String proxName,
			String proxIdCard, String docId, String docName,
			String totalAmount, String presSource, 
			String pharmPreId, String pharmPreName, String pharmChkId,
			String pharmChkName,  String docTitle, String scr, String ccr, String ast,String alt,String bsa,String drugSensivity,
			String flag1, String flag2, String flag3,String status,String hospitalOrPharmacy,String result,String insurePerson) {
		this.departId = departId;
		this.department = department;
		this.bedNo = bedNo;
		this.age = age;
		this.presType = presType;
		this.presDateTime = presDateTime;
		this.sex = sex;
		this.payType = payType;
		this.patientNo = patientNo;
		this.presNo = presNo;
		this.name = name;
		this.diagnose = diagnose;
		this.address = address;
		this.idCard = idCard;
		this.cardNo = card_no;
		this.phoneNo = phoneNo;
		this.height = height;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.weight = weight;
		this.birthWeight = birthWeight;
		this.allergyList = allergyList;
		this.pregnancy = pregnancy;
		this.timeOfPreg = timeOfPreg;
		this.breastFeeding = breastFeeding;
		this.dialysis = dialysis;
		this.proxName = proxName;
		this.proxIdCard = proxIdCard;
		this.docId = docId;
		this.docName = docName;
		this.docTitle = docTitle;
		this.presSource = presSource;
		this.pharmChkId = pharmChkId;
		this.pharmChkName = pharmChkName;
		this.totalAmount = totalAmount;
		this.scr = scr;
		this.ccr = ccr;
		this.alt = alt;
		this.ast = ast;
		this.bsa = bsa;
		this.flag1 = flag1;
		this.flag2 = flag2;
		this.flag3 = flag3;
		this.drugSensivity =drugSensivity;
		this.status = status;
		this.hospitalOrPharmacy =hospitalOrPharmacy;
		this.result =result;
		this.insurePerson =insurePerson;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBedNo() {
		return this.bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPresType() {
		return this.presType;
	}

	public void setPresType(String presType) {
		this.presType = presType;
	}

	public String getPresDateTime() {
		return this.presDateTime;
	}

	public void setPresDateTime(String presDateTime) {
		this.presDateTime = presDateTime;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPatientNo() {
		return this.patientNo;
	}

	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}

	public String getPresNo() {
		return this.presNo;
	}

	public void setPresNo(String presNo) {
		this.presNo = presNo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiagnose() {
		return this.diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBirthWeight() {
		return this.birthWeight;
	}

	public void setBirthWeight(String birthWeight) {
		this.birthWeight = birthWeight;
	}

	public String getCcr() {
		return this.ccr;
	}

	public void setCcr(String ccr) {
		this.ccr = ccr;
	}

	public String getAllergyList() {
		return this.allergyList;
	}

	public void setAllergyList(String allergyList) {
		this.allergyList = allergyList;
	}

	public String getPregnancy() {
		return this.pregnancy;
	}

	public void setPregnancy(String pregnancy) {
		this.pregnancy = pregnancy;
	}

	public String getTimeOfPreg() {
		return this.timeOfPreg;
	}

	public void setTimeOfPreg(String timeOfPreg) {
		this.timeOfPreg = timeOfPreg;
	}

	public String getBreastFeeding() {
		return this.breastFeeding;
	}

	public void setBreastFeeding(String breastFeeding) {
		this.breastFeeding = breastFeeding;
	}

	public String getDialysis() {
		return this.dialysis;
	}

	public void setDialysis(String dialysis) {
		this.dialysis = dialysis;
	}

	public String getProxName() {
		return this.proxName;
	}

	public void setProxName(String proxName) {
		this.proxName = proxName;
	}

	public String getProxIdCard() {
		return this.proxIdCard;
	}

	public void setProxIdCard(String proxIdCard) {
		this.proxIdCard = proxIdCard;
	}

	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return this.docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPresSource() {
		return this.presSource;
	}

	public void setPresSource(String presSource) {
		this.presSource = presSource;
	}


	public String getPharmChkId() {
		return this.pharmChkId;
	}

	public void setPharmChkId(String pharmChkId) {
		this.pharmChkId = pharmChkId;
	}

	public String getPharmChkName() {
		return this.pharmChkName;
	}

	public void setPharmChkName(String pharmChkName) {
		this.pharmChkName = pharmChkName;
	}

	
	public String getDocTitle() {
		return this.docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getScr() {
		return this.scr;
	}

	public void setScr(String scr) {
		this.scr = scr;
	}
   
	public String getDrugSensivity() {
		return drugSensivity;
	}

	public void setDrugSensivity(String drugSensivity) {
		this.drugSensivity = drugSensivity;
	}


	public String getFlag1() {
		return this.flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return this.flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	public String getFlag3() {
		return this.flag3;
	}

	public void setFlag3(String flag3) {
		this.flag3 = flag3;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPharmChkTitle() {
		return pharmChkTitle;
	}

	public void setPharmChkTitle(String pharmChkTitle) {
		this.pharmChkTitle = pharmChkTitle;
	}

	public String getAst() {
		return ast;
	}

	public void setAst(String ast) {
		this.ast = ast;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getBsa() {
		return bsa;
	}

	public void setBsa(String bsa) {
		this.bsa = bsa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	
	public String getHospitalOrPharmacy() {
		return hospitalOrPharmacy;
	}

	public void setHospitalOrPharmacy(String hospitalOrPharmacy) {
		this.hospitalOrPharmacy = hospitalOrPharmacy;
	}



	
	public String getInsurePerson() {
		return insurePerson;
	}

	public void setInsurePerson(String insurePerson) {
		this.insurePerson = insurePerson;
	}


	
	public void save(Connection con) throws Exception {
		String sql = null;
		sql = "insert into eng_pt_cf"
				+ "(depart_id,department,bed_no,age,pres_type,pres_date_time,sex,pay_type,patient_no,pres_no,"
				+ "name,diagnose,address,id_card,phone_no,height,create_date,update_date,weight,birth_weight,"
				+ "ccr,allergy_list,pregnancy,time_of_preg,breast_feeding,dialysis,prox_name,prox_id_card,doc_id,doc_name,"
				+ "total_amount,pres_source,pharm_chk_id,pharm_chk_name,doc_title,scr,flag1,flag2,flag3,card_no,"
				+ "ast,alt,bsa ,status,drugSensivity,hospital_or_pharmacy,insure_person,id) values (?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				 +"?,?,?,?,?,?,?,?,?,?,"
				 + "?,?,?,?,?,?,?,?)";

		setUpdateDate(new Timestamp(System.currentTimeMillis()));

		PreparedStatement pst = con.prepareStatement(sql);

		int i = 1;
		pst.setString(i++, getDepartId());
		pst.setString(i++, getDepartment());
		pst.setString(i++, getBedNo());
		pst.setString(i++, getAge());
		pst.setString(i++, getPresType());
		pst.setString(i++, getPresDateTime());
		pst.setString(i++, getSex());
		pst.setString(i++, getPayType());
		pst.setString(i++, getPatientNo());
		pst.setString(i++, getPresNo());
		pst.setString(i++, getName());
		pst.setString(i++, getDiagnose());
		pst.setString(i++, getAddress());
		pst.setString(i++, getIdCard());
		pst.setString(i++, getPhoneNo());
		pst.setString(i++, getHeight());
		pst.setTimestamp(i++, getCreateDate());
		pst.setTimestamp(i++, getUpdateDate());
		pst.setString(i++, getWeight());
		pst.setString(i++, getBirthWeight());
		pst.setString(i++, getCcr());
		pst.setString(i++, getAllergyList());
		pst.setString(i++, getPregnancy());
		pst.setString(i++, getTimeOfPreg());
		pst.setString(i++, getBreastFeeding());
		pst.setString(i++, getDialysis());
		pst.setString(i++, getProxName());
		pst.setString(i++, getProxIdCard());
		pst.setString(i++, getDocId());
		pst.setString(i++, getDocName());
		pst.setString(i++, getTotalAmount());
		pst.setString(i++, getPresSource());
		pst.setString(i++, getPharmChkId());
		pst.setString(i++, getPharmChkName());
		pst.setString(i++, getDocTitle());
		pst.setString(i++, getScr());
		pst.setString(i++, getFlag1());
		pst.setString(i++, getFlag2());
		pst.setString(i++, getFlag3());
		pst.setString(i++, getCardNo());
		pst.setString(i++, getAst());
		pst.setString(i++, getAlt());
		pst.setString(i++, getBsa());
		pst.setString(i++, getStatus());
		pst.setString(i++, getDrugSensivity());
		pst.setString(i++, getHospitalOrPharmacy());
		pst.setString(i++, getInsurePerson());
		pst.setString(i++, getId());
		pst.execute();
		pst.close();
	}
	public String toXml() {
		String xml ="<patient>";
		xml += "<presNo>" +getId()+"</presNo>";
		xml += "<cardNo>" +getCardNo()+"</cardNo>";
		xml += "<insurePerson>" + getInsurePerson()+"</insurePerson>";
		xml += "<hospitalOrPharmacy>"+getHospitalOrPharmacy()+"</hospitalOrPharmacy>";
		xml += "<IDCard>" +getIdCard()+"</IDCard>";
		xml += "<patientNo>" +getPatientNo()+"</patientNo>";
		xml += "<name>" +getName()+"</name>";
		xml += "<address>" +getAddress()+"</address>";
		xml += "<age>" +getAge()+"</age>";
		xml += "<allergyList>" +getAllergyList()+"</allergyList>";
		xml += "<bedNo>" +getBedNo()+"</bedNo>";
		xml += "<birthWeight>" +getBirthWeight()+"</birthWeight>";
		xml += "<breastFeeding>" +getBreastFeeding()+"</breastFeeding>";
		xml += "<departID>" +getDepartId()+"</departID>";
		xml += "<department>" +getDepartment()+"</department>";
		xml += "<presSource>" +getPresSource()+"</presSource>";
		xml += "<diagnose>" +getDiagnose()+"</diagnose>";
		xml += "<dialysis>" +getDialysis()+"</dialysis>";
		xml += "<docID>" +getDocId()+"</docID>";	
		xml += "<docName>" +getDocName()+"</docName>";
		xml += "<docTitle>" +getDocTitle()+"</docTitle>";
		xml += "<height>" +getHeight()+"</height>";
		xml += "<payType>" +getPayType()+"</payType>";
		xml += "<phoneNo>" +getPhoneNo()+"</phoneNo>";
		xml += "<pregnancy>" +getPregnancy()+"</pregnancy>";
		xml += "<presDatetime>" +getPresDateTime()+"</presDatetime>";
		xml += "<presType>" +getPresType()+"</presType>";
		xml += "<proxIDCard>" +getProxIdCard()+"</proxIDCard>";
		xml += "<proxName>" +getProxName()+"</proxName>";
		xml += "<sex>" +getSex()+"</sex>";	
		xml += "<timeOfPreg>" +getTimeOfPreg()+"</timeOfPreg>";
		xml += "<weight>" +getWeight()+"</weight>";
		xml += "<pharmChkId>" +getPharmChkId()+"</pharmChkId>";
		xml += "<pharmChkName>" +getPharmChkName()+"</pharmChkName>";
		xml += "<pharmChkTitle>" +getPharmChkTitle()+"</pharmChkTitle>";
		xml += "<ast>" +getAst()+"</ast>";
		xml += "<scr>" +getScr()+"</scr>";
		xml += "<alt>" +getAlt()+"</alt>";
		xml += "<ccr>" +getCcr()+"</ccr>";
		xml += "<bsa>" +getBsa()+"</bsa>";
		xml += "<drugSensivity>" +getDrugSensivity()+"</drugSensivity>";
		xml += "<status>" +getStatus()+"</status>";
		xml += "<totalAmount>" +getTotalAmount()+"</totalAmount>";
		xml +="<result>" +getResult()+"</result>";
		xml += "</patient>";
		return xml;
	}
	
}