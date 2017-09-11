package com.xiaojd.entity.hospital;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import javax.persistence.Id;
import javax.persistence.Table;

import com.xiaojd.conn.ConManager;

/**
 * OrgUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eng_pt_user", catalog = "sz_hospital")
public class EngPtUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 15)
	private long id;

	@Column(name = "name", nullable = false, length = 20)
	private String name;
	
	@Column(name = "pwd", nullable = false, length = 30)
	private String pwd;
	
	@Column(name = "code", nullable = false, length = 20)
	private String code;
	
	//关联的药房
	@Column(name = "pharmacy_id", nullable = false, length = 20)
	private Long pharmacyId;
	//角色
	@Column(name = "role", nullable = false, length = 10)
	private String role;
	
	//联系电话
	@Column(name = "phone", nullable = false, length = 20)
	private String phone;
	
	@Transient
	private String roleName;// 角色名
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRoleName() {
	
		if("1001".equals(role)) {
			roleName ="管理员";
		}else if("1002".equals(role)) {
			roleName ="店长";
		} else if ("1003".equals(role)) {
			roleName ="配送员";
		}
		return roleName;
	}
	public void setRoleName(String roleName) {
		if("管理员".equals(roleName)) {
			this.role ="1001";
		}else if("店长".equals(roleName)) {
			this.role ="1002";
		} else if ("配送员".equals(roleName)) {
			this.role ="1003";
		}
		this.roleName = roleName;
	}

	//删除 -1；停用0；正在使用1
	@Column(name = "status", nullable = false, length = 10)
	private String status;
	
   public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}

	//药店名
	@Transient
	private String pharmacy;

	/** default constructor */
	public EngPtUser() {
		this.pharmacyId = 1l;
	}
	/** full constructor */
	public EngPtUser(String name, String pwd, String code, Long pharmacyId, String role) {
		this.name = name;
		this.pwd = pwd;
		this.code = code;
		this.pharmacyId = pharmacyId;
		this.role= role;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
   
}