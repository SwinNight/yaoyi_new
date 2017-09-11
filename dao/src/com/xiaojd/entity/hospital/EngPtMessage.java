package com.xiaojd.entity.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EngCfMessage entity. @author MyEclipse Persistence Tools 
 */
@Entity
@Table(name = "eng_pt_message", catalog = "sz_hospital")
public class EngPtMessage implements java.io.Serializable {

	// Fields

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 10)
	private String id;

	@Column(name = "cf_id", nullable = false, length = 100)
	private String cfId;// 与eng_cf中的id对应
	
	@Column(name = "drug_code", nullable = false, length = 100)
	private String drugCode;// 

	@Column(name = "drug_name", length = 50)
	private String drugName;// 药品名称

	@Column(name = "message", length = 200)
	private String message;// 警示信息

	@Column(name = "source", length = 45)
	private String source;// 数据来源

	@Column(name = "severity", length = 1)
	private String severity;// 警示等级

	@Column(name = "advice", length = 500)
	private String advice;// 警示建议

	@Column(name = "type", length = 45)
	private String type;// 警示类型

	@Column(name = "create_date", length = 19)
	private Timestamp createDate;// 创建时间

	@Column(name = "update_date", length = 19)
	private Timestamp updateDate;// 更新时间

	@Column(name = "pres_date_time", length = 45)
	private String presDateTime;// 处方时间

	@Column(name = "department", length = 100)
	private String department;// 开方科室

	@Column(name = "doc_name", length = 45)
	private String docName;// 医生名字

	@Column(name = "message_id", length = 13)
	private String messageId;// 预eng_message中的id对应
	
	@Column(name = "detail_id", length = 30)
	private String detailId;// 触发警示的明细id

	// Constructors

	/** default constructor */
	public EngPtMessage() {
	}

	/** minimal constructor */
	public EngPtMessage(String cfId) {
		this.cfId = cfId;
	}

	/** full constructor */
	public EngPtMessage(String cfId, String drugName, String message,
			String source, String severity, String advice, String type,
			Timestamp createDate, Timestamp updateDate, String presDateTime,
			String department, String docName, String messageId, String detailId) {
		this.cfId = cfId;
		this.drugName = drugName;
		this.message = message;
		this.source = source;
		this.severity = severity;
		this.advice = advice;
		this.type = type;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.presDateTime = presDateTime;
		this.department = department;
		this.docName = docName;
		this.messageId = messageId;
		this.detailId = detailId;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCfId() {
		return this.cfId;
	}

	public void setCfId(String cfId) {
		this.cfId = cfId;
	}

	public String getDrugName() {
		return this.drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSeverity() {
		return this.severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getAdvice() {
		return this.advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getPresDateTime() {
		return this.presDateTime;
	}

	public void setPresDateTime(String presDateTime) {
		this.presDateTime = presDateTime;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDocName() {
		return this.docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drug_code) {
		this.drugCode = drug_code;
	}

	public void save(Connection con) throws Exception {
		String sql = null;
		sql = "insert into eng_pt_message(cf_id,drug_code,drug_name,message,source,severity,advice,type,create_date,update_date,pres_date_time,department,doc_name,message_id,detail_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		setCreateDate(new Timestamp(System.currentTimeMillis()));
		setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PreparedStatement pst = con.prepareStatement(sql);
		int i = 1;
		pst.setString(i++, getCfId());
		pst.setString(i++, getDrugCode());
		pst.setString(i++, getDrugName());
		pst.setString(i++, getMessage());
		pst.setString(i++, getSource());
		pst.setString(i++, getSeverity());
		pst.setString(i++, getAdvice());
		pst.setString(i++, getType());
		pst.setTimestamp(i++, getCreateDate());
		pst.setTimestamp(i++, getUpdateDate());
		pst.setString(i++, getPresDateTime());
		pst.setString(i++, getDepartment());
		pst.setString(i++, getDocName());
		pst.setString(i++, getMessageId());
		pst.setString(i++, getDetailId());
		pst.execute();
		pst.close();
	}
	
	public String toXml() {
		String xml = "<info>";
		xml +="<drugCode>"+getDrugCode()+"</drugCode>";
		xml +="<message>"+getMessage()+"</message>";
		xml +="<advice>"+getAdvice()+"</advice>";
		xml +="<source>"+getSource()+"</source>";
		xml +="<severity>"+getSeverity()+"</severity>";
		xml +="<messageId>"+getMessageId()+"</messageId>";
		xml +="<type>"+getType()+"</type>";
	    xml += "</info>";
	    return xml;
	}

}