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
 * EngCf entity. @author MyEclipse Persistence Tools 处方头
 */
@Entity
@Table(name = "eng_pt_delivery", catalog = "sz_hospital")
public class EngPtDelivery implements java.io.Serializable {

	// Fields

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 30)
	private long id;		
	
	
	@Column(name = "cf_id", length = 50)
	private String cfId;
	
	@Column(name = "courier_id", length = 50)
	private long courierId;//快递员
	
	@Column(name = "cashier_id", length = 50)
	private long cashieId;//收银员	
	

	@Column(name = "pharmacy_id", length = 50)
	private long pharmacyId;//派送药店ID	
	
	@Column(name = "courier_charge_time", length = 19)
	private Timestamp courierChargeTime;//收费时间
	
	@Column(name = "pharmacy_time", length = 19)
	private Timestamp pharmacyTime;//分配到药店的时间
	
	@Column(name = "cashier_start_time", length = 19)
	private Timestamp cashierStartTime;//分配到派送员的时间
	
	@Column(name = "cashier_complete_time", length = 19)
	private Timestamp cashierCompleteTime;//派送员派送成功结束时间
	
	@Column(name = "address", length = 200)
	private String address;//收件人地址
	
	@Column(name = "phone_no", length = 60)
	private String phoneNo;//收件人联系电话
	
	@Column(name = "name", length = 50)
	private String name;//收件人
	
	@Column(name = "remark", length = 200)
	private String remark;//收件备注
	
	@Column(name = "delivery_comment", length = 200)
	private String deliveryComment;//收件备注
	
	@Column(name = "ways", length = 200)
	private String ways;//配送或者自取
	
	
	@Column(name = "take_drug_person", length = 20)
	private String takeDrugPerson;//自取药人
	


	public String getTakeDrugPerson() {
		return takeDrugPerson;
	}

	public void setTakeDrugPerson(String takeDrugPerson) {
		this.takeDrugPerson = takeDrugPerson;
	}

	public long getCourierId() {
		return courierId;
	}

	public void setCourierId(long courierId) {
		this.courierId = courierId;
	}

	public long getCashieId() {
		return cashieId;
	}

	public void setCashieId(long cashieId) {
		this.cashieId = cashieId;
	}

	
	public String getWays() {
		return ways;
	}

	public void setWays(String ways) {
		this.ways = ways;
	}

	@Transient
	private String presDateTime;//处方时间
	
	@Transient
	private String cfStatus;//处方状态
	
	@Transient
	private String courierName;//派送员名
	
	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getPresDateTime() {
		return presDateTime;
	}

	public void setPresDateTime(String presDateTime) {
		this.presDateTime = presDateTime;
	}

	public String getCfStatus() {
		return cfStatus;
	}

	public void setCfStatus(String cfStatus) {
		this.cfStatus = cfStatus;
	}


	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCfId() {
		return cfId;
	}

	public void setCfId(String cfId) {
		this.cfId = cfId;
	}

	public long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public Timestamp getCourierChargeTime() {
		return courierChargeTime;
	}

	public void setCourierChargeTime(Timestamp courierChargeTime) {
		this.courierChargeTime = courierChargeTime;
	}

	public Timestamp getPharmacyTime() {
		return pharmacyTime;
	}

	public void setPharmacyTime(Timestamp pharmacyTime) {
		this.pharmacyTime = pharmacyTime;
	}

	public Timestamp getCashierStartTime() {
		return cashierStartTime;
	}

	public void setCashierStartTime(Timestamp cashierStartTime) {
		this.cashierStartTime = cashierStartTime;
	}

	public Timestamp getCashierCompleteTime() {
		return cashierCompleteTime;
	}

	public void setCashierCompleteTime(Timestamp cashierCompleteTime) {
		this.cashierCompleteTime = cashierCompleteTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeliveryComment() {
		return deliveryComment;
	}

	public void setDeliveryComment(String deliveryComment) {
		this.deliveryComment = deliveryComment;
	}
	
	
	}