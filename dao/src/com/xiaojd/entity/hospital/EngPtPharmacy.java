package com.xiaojd.entity.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import javax.persistence.Id;
import javax.persistence.Table;

import com.xiaojd.conn.ConManager;

/**
 * OrgUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eng_pt_pharmacy", catalog = "sz_hospital")
public class EngPtPharmacy implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 15)
	private String id;
    //药店名
	@Column(name = "pharmacy", nullable = false, length = 20)
	private String pharmacy;
	
	 //药店城区地址
	@Column(name = "urban_area", nullable = false, length = 30)
	private String urbanArea;
   public String getUrbanArea() {
		return urbanArea;
	}
	public void setUrbanArea(String urbanArea) {
		this.urbanArea = urbanArea;
	}
	//药店地址
	@Column(name = "address", nullable = false, length = 30)
	private String address;
	//药店领导
	@Column(name = "leader", nullable = false, length = 20)
	private String leader;
	//药店联系电话
	@Column(name = "leader_phone", nullable = false, length = 20)
	private String leaderPhone;
	//状态 删除 -1；停用0；正在使用1
	@Column(name = "status", nullable = false, length = 10)
	private String status;
	//药店编号
	@Column(name = "code", nullable = false, length = 10)
	private String code;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getLeaderPhone() {
		return leaderPhone;
	}
	public void setLeaderPhone(String leaderPhone) {
		this.leaderPhone = leaderPhone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}