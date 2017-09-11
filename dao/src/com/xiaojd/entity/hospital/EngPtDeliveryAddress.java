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







//import com.dh.health.dataType.Prescription;
//import com.dh.health.lang.Weight;
import com.xiaojd.conn.ConManager;

/**
 * EngPatient entity. @author MyEclipse Persistence Tools 患者配送信息
 */
@Entity
@Table(name = "eng_pt_delivery_address", catalog = "sz_hospital")
public class EngPtDeliveryAddress implements java.io.Serializable {

	public EngPtDeliveryAddress() {
		// TODO Auto-generated constructor stub
	}
	// Fields
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 10)
	private Long id;	
	
	@Column(name = "patient_no", length = 50)
	private String patientNo;//病人号
	
	@Column(name = "phone_no", length = 50)
	private String phoneNo;//手机号码
	
	@Column(name = "address", length = 50)
	private String address;//地址
	
	@Column(name = "name", length = 50)
	private String name;//姓名
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPatientNo() {
		return patientNo;
	}

	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	}