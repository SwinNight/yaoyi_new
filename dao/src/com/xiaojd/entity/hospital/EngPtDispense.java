package com.xiaojd.entity.hospital;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xiaojd.conn.ConManager;

/**
 * EngCfItem entity. @author MyEclipse Persistence Tools 平台处方药
 */
@Entity
@Table(name = "eng_pt_dispense", catalog = "sz_hospital")
public class EngPtDispense implements java.io.Serializable {

	// Fields

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 50)
	private Long id;

	@Column(name = "pres_no", nullable = false, length = 10)
	private String presNo;// 与eng_cf中的id关联
	
	@Transient
	@Column(name = "status", length = 40)
	private String status;// 

	@Column(name = "hospital", length = 100)
	private String hospital;// 发药医院

	@Column(name = "department", length = 100)
	private String department;// 发药科室

	@Column(name = "doctor", length = 30)
	private String doctor;// 发药医生

	@Column(name = "comment", length = 30)
	private String comment;// 处方备注（返回发药社区或处方退回原因）

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPresNo() {
		return presNo;
	}

	public void setPresNo(String presNo) {
		this.presNo = presNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public EngPtDispense(){}
    public EngPtDispense(String presNo,String status) {
    	this.presNo = presNo;
    	this.status = status;
    	this.hospital ="";
    	this.department ="";
    	this.doctor ="";
    	this.comment ="";
    }
	public String toXml() {
		String xml ="<info>";
		xml +="<presNo>" +getPresNo()+"</presNo>";
		xml +="<status>" +getStatus()+"</status>";//只从eng_pt_cf中读取状态
		xml +="<hospital>" +getHospital()+"</hospital>";
		xml +="<department>" +getDepartment()+"</department>";
		xml +="<doctor>" +getDoctor()+"</doctor>";
		xml +="<comment>" +getComment()+"</comment>";
	    xml +="</info>";
		return  xml;
	}
	//eng_pt_cf 新建时同时添加
	public void save(Connection con) throws Exception {
		String sql = null;
		sql = "insert into eng_pt_dispense(pres_no) values (?)";
		PreparedStatement pst = con.prepareStatement(sql);
		int i = 1;
		pst.setString(i++,getPresNo());
		pst.execute();
		pst.close();
	}
	

}