package com.xiaojd.entity.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import com.xiaojd.conn.ConManager;

/**
 * SysConfig entity. @author MyEclipse Persistence Tools
 * 系统配置
 */
@Entity
@Table(name = "eng_match_dict", catalog = "sz_hospital")
public class EngMatchDict implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 10)
	private Long id;

	@Column(name = "belong_org", nullable = false, length = 20)
	private String belongOrg;//分类

	@Column(name = "remark", nullable = false, length = 20)
	private String remark;//备注

	@Column(name = "patient", nullable = false, length = 50)
	private String patient;//医院分类

	@Column(name = "children", nullable = false, length = 500)
	private String children;//是否可用

	@Column(name = "enable", nullable = false, length = 10)
	private String enable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBelongOrg() {
		return belongOrg;
	}

	public void setBelongOrg(String belongOrg) {
		this.belongOrg = belongOrg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}