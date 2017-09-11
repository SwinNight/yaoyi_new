package com.xiaojd.entity.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.xiaojd.conn.ConManager;
import com.xiaojd.entity.util.Config;

/**
 * EngCfItem entity. @author MyEclipse Persistence Tools 平台处方药
 */
@Entity
@Table(name = "eng_pt_drug", catalog = "sz_hospital")
public class EngPtDrug implements java.io.Serializable {

	// Fields

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 50)
	private String id;

	@Column(name = "cfid", nullable = false, length = 10)
	private String cfid;// 与eng_cf中的id关联

	@Column(name = "drug", length = 40)
	private String drug;// 药品id

	@Column(name = "drug_name", length = 100)
	private String drugName;// 药品名称

	@Column(name = "reg_name", length = 100)
	private String regName;// 商品名

	@Column(name = "spec", length = 30)
	private String spec;// 规格

	@Column(name = "pack", length = 30)
	private String pack;// 包装

	@Column(name = "quantity", length = 20)
	private String quantity;// 数量

	@Column(name = "pack_unit", length = 30)
	private String packUnit;// 包装单位
	
	@Column(name = "qty", length = 30)
	private String qty;//最小剂量销售数量

	@Column(name = "qty_unit", length = 30)
	private String qtyUnit;// 最小剂量销售单位

	@Column(name = "dispense_unit", length = 30)
	private String dispenseUnit;//
	
	@Column(name = "content", length = 30)
	private String content;//

	@Column(name = "content_unit", length = 30)
	private String contentUnit;//
	
	@Column(name = "unit_price", length = 20)
	private String unitPrice;// 单价

	@Column(name = "amount", length = 20)
	private String amount;// 金额

	@Column(name = "group_no", length = 15)
	private String groupNo;// 组号

	@Column(name = "first_use", length = 10)
	private String firstUse;//

	@Column(name = "prep_form", length = 40)
	private String prepForm;// 剂型

	@Column(name = "admin_route", length = 50)
	private String adminRoute;// 给药途径

	@Column(name = "admin_area", length = 50)
	private String adminArea;// 给药区域

	@Column(name = "admin_frequency", length = 50)
	private String adminFrequency;// 给药频率

	@Column(name = "admin_dose", length = 50)
	private String adminDose;// 单次剂量

	@Column(name = "admin_method", length = 50)
	private String adminMethod;// 给药时机

	@Column(name = "create_date", length = 19)
	private Timestamp createDate;// 创建时间

	@Column(name = "update_date", length = 19)
	private Timestamp updateDate;// 更新时间

	@Column(name = "drug_name_bak", length = 145)
	private String drugNameBak;//

	@Column(name = "skin_test", length = 100)
	private String skinTest;// 皮试

	@Column(name = "type", length = 100)
	private String type;//

	@Column(name = "start_date", length = 100)
	private String startDate;// 开始时间
	
	@Column(name = "continue_day", length = 10)
	private String continueDay;//持续时间
	
	
	@Column(name = "reality_quantity", length = 100)
	private String realityQuantity;// 实际数量
	
	@Column(name = "reality_amount", length = 10)
	private String realityAmount;// 实际缴费金额
	
	public String getRealityQuantity() {
		return realityQuantity;
	}

	public void setRealityQuantity(String realityQuantity) {
		this.realityQuantity = realityQuantity;
	}

	public String getRealityAmount() {
		return realityAmount;
	}

	public void setRealityAmount(String realityAmount) {
		this.realityAmount = realityAmount;
	}

	@Transient
	private Double money;// 
    
	// Property accessors
	public Double getMoney() {
	    Double money ;
	    try
	    {
	    	money=  Double.parseDouble(realityAmount);
	    }catch(Exception e) {
	       return 0.0;
	    }
	      return money;
	    } 

	// Constructors

	/** default constructor */
	public EngPtDrug() {
	}

	/** minimal constructor */
	public EngPtDrug(String cfid) {
		this.cfid = cfid;
	}

	/** full constructor */
	public EngPtDrug(String cfid, String drug, String drugName, String regName,
			String spec, String pack, String quantity, String packUnit,
			String unitPrice, String amount, String groupNo, String firstUse,
			String prepForm, String adminRoute, String adminArea,
			String adminFrequency, String adminDose, String adminMethod,
			Timestamp createDate, Timestamp updateDate, String drugNameBak,
			String manufacturerName, String skinTest, String type,
			String startDate, String itemFlag) {
		this.cfid = cfid;
		this.drug = drug;
		this.drugName = drugName;
		this.regName = regName;
		this.spec = spec;
		this.pack = pack;
		this.quantity = quantity;
		this.packUnit = packUnit;
		this.unitPrice = unitPrice;
		this.amount = amount;
		this.groupNo = groupNo;
		this.firstUse = firstUse;
		this.prepForm = prepForm;
		this.adminRoute = adminRoute;
		this.adminArea = adminArea;
		this.adminFrequency = adminFrequency;
		this.adminDose = adminDose;
		this.adminMethod = adminMethod;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.drugNameBak = drugNameBak;
		this.skinTest = skinTest;
		this.type = type;
		this.startDate = startDate;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCfid() {
		return this.cfid;
	}

	public void setCfid(String cfid) {
		this.cfid = cfid;
	}

	public String getDrug() {
		return this.drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public String getDrugName() {
		return this.drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getRegName() {
		return this.regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getPack() {
		return this.pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPackUnit() {
		return this.packUnit;
	}

	public void setPackUnit(String packUnit) {
		this.packUnit = packUnit;
	}

	public String getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getFirstUse() {
		return this.firstUse;
	}

	public void setFirstUse(String firstUse) {
		this.firstUse = firstUse;
	}

	public String getPrepForm() {
		return this.prepForm;
	}

	public void setPrepForm(String prepForm) {
		this.prepForm = prepForm;
	}

	public String getAdminRoute() {
		return this.adminRoute;
	}

	public void setAdminRoute(String adminRoute) {
		this.adminRoute = adminRoute;
	}

	public String getAdminArea() {
		return this.adminArea;
	}

	public void setAdminArea(String adminArea) {
		this.adminArea = adminArea;
	}

	public String getAdminFrequency() {
		return this.adminFrequency;
	}

	public void setAdminFrequency(String adminFrequency) {

		this.adminFrequency = adminFrequency;
	}

	public String getAdminDose() {
		if(adminDose.startsWith(".")) {
			this.adminDose = "0"+adminDose;
		}
		return this.adminDose;
	}

	public void setAdminDose(String adminDose) {

		this.adminDose = adminDose;
	}

	public String getAdminMethod() {
		return this.adminMethod;
	}

	public void setAdminMethod(String adminMethod) {
		this.adminMethod = adminMethod;
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

	public String getDrugNameBak() {
		return this.drugNameBak;
	}

	public void setDrugNameBak(String drugNameBak) {
		this.drugNameBak = drugNameBak;
	}

	public String getSkinTest() {
		return this.skinTest;
	}

	public void setSkinTest(String skinTest) {
		this.skinTest = skinTest;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getContinueDay() {
		return continueDay;
	}

	public void setContinueDay(String continueDay) {
		this.continueDay = continueDay;
	}

	
	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getQtyUnit() {
		return qtyUnit;
	}

	public void setQtyUnit(String qtyUnit) {
		this.qtyUnit = qtyUnit;
	}

	public String getDispenseUnit() {
		return dispenseUnit;
	}

	public void setDispenseUnit(String dispenseUnit) {
		this.dispenseUnit = dispenseUnit;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentUnit() {
		return contentUnit;
	}

	public void setContentUnit(String contentUnit) {
		this.contentUnit = contentUnit;
	}
	
	public void save(Connection con) throws Exception {
		String sql = null;
		sql = "insert into eng_pt_drug"
				+ " (cfid,drug,drug_name,reg_name,spec,pack,quantity,pack_unit,unit_price,amount,"
				+ "group_no,first_use,prep_form,admin_route,admin_area,admin_frequency,admin_dose,admin_method,create_date,update_date,"
				+ "drug_name_bak,skin_test,type,start_date,continue_day,qty,qty_unit,content,content_unit,dispense_unit"
				+ ",reality_quantity,reality_amount, id) values (?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?)";
				
		setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PreparedStatement pst = con.prepareStatement(sql);
		int i = 1;
		pst.setString(i++, getCfid());
		pst.setString(i++, getDrug());
		pst.setString(i++, getDrugName());
		pst.setString(i++, getRegName());
		pst.setString(i++, getSpec());
		pst.setString(i++, getPack());
		pst.setString(i++, getQuantity());
		pst.setString(i++, getPackUnit());
		pst.setString(i++, getUnitPrice());
		pst.setString(i++, getAmount());
		pst.setString(i++, getGroupNo());
		pst.setString(i++, getFirstUse());
		pst.setString(i++, getPrepForm());
		pst.setString(i++, getAdminRoute());
		pst.setString(i++, getAdminArea());
		pst.setString(i++, getAdminFrequency());
		pst.setString(i++, getAdminDose());
		pst.setString(i++, getAdminMethod());
		pst.setTimestamp(i++, getCreateDate());
		pst.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
		pst.setString(i++, getDrugNameBak());
		pst.setString(i++, getSkinTest());
		pst.setString(i++, getType());
		pst.setString(i++, getStartDate());
		pst.setString(i++, getContinueDay());
		pst.setString(i++, getQty());
		pst.setString(i++, getQtyUnit());
		pst.setString(i++, getContent());
		pst.setString(i++, getContentUnit());
		pst.setString(i++, getDispenseUnit());
		pst.setString(i++, getRealityQuantity());
		pst.setString(i++, getRealityAmount());
		pst.setString(i++, getId());
		pst.execute();
		pst.close();
	}
    public String toXml() {
    	return toXml("原始数据");
    }
	public String toXml(String belogOrg) {
		String xml = "<prescription>";
		xml += "<drug>" +getDrug()+"</drug>";
		xml += "<drugName>" +getDrugName()+"</drugName>";
		xml += "<prepForm>" +getPrepForm()+"</prepForm>";
		xml += "<presNo>" +getId()+"</presNo>";
		
		xml += "<startTime>" +getStartDate()+"</startTime>";
		xml += "<type>" +getType()+"</type>";
		xml += "<adminArea>" +getAdminArea()+"</adminArea>";
		xml += "<adminDose>" +getAdminDose()+"</adminDose>";
		
		if("原始数据".equals(belogOrg)) {
			xml += "<adminFrequency>" +getAdminFrequency()+"</adminFrequency>";
			xml += "<adminRoute>" +getAdminRoute()+"</adminRoute>";
		} else {
			String flag ="给药频率_"+belogOrg;
			String flag1 ="给药途径_"+belogOrg;
			String frequency = Config.getValue(flag, "["+getAdminFrequency()+"]");
			String adminRoute =  Config.getValue(flag1,"["+getAdminRoute()+"]");
			xml += "<adminFrequency>" +frequency+"</adminFrequency>";
			xml += "<adminRoute>" +adminRoute+"</adminRoute>";	
		}
	
		xml += "<adminMethod>" +getAdminMethod()+"</adminMethod>";
		xml += "<continueDays>" +getContinueDay()+"</continueDays>";
		
		
		xml += "<firstUse>" +getFirstUse()+"</firstUse>";
		xml += "<skinTest>" +getSkinTest()+"</skinTest>";
		xml += "<groupNo>" +getGroupNo()+"</groupNo>";
		xml += "<adminMethod>" +getAdminMethod()+"</adminMethod>";
		
		
		xml += "<package>" +getPack()+"</package>";
		xml += "<packUnit>" +getPackUnit()+"</packUnit>";
		xml += "<regName>" +getRegName()+"</regName>";
		xml += "<specification>" +getSpec()+"</specification>";
		
		xml += "<qty>" +getQty()+"</qty>";
		xml += "<qtyUnit>" +getQtyUnit()+"</qtyUnit>";
		xml += "<content>" +getContent()+"</content>";
		xml += "<contentUnit>" +getContentUnit()+"</contentUnit>";
		
		xml += "<quantity>" +getQuantity()+"</quantity>";
		xml += "<dispenseUnit>" +getDispenseUnit()+"</dispenseUnit>";
		xml += "<unitPrice>" +getUnitPrice()+"</unitPrice>";
		xml += "<amount>" +getAmount()+"</amount>";
		xml += "<realityQuantity>" +getRealityQuantity()+"</realityQuantity>";
		xml += "<realityAmount>" +getRealityAmount()+"</realityAmount>";
	    xml += "</prescription>";
	    return xml;
	    		
	}

}