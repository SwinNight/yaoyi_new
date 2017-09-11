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
@Table(name = "eng_pt_sms", catalog = "sz_hospital")
public class EngPtSms implements java.io.Serializable {

	// Fields

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 30)
	private long id;

	@Column(name = "cf_id", nullable = false, length = 30)
	private String cfId;// 与eng_cf中的id对应

	@Column(name = "delivery_id", nullable = false, length = 30)
	private long deliveryId;// 与eng_pt_delivery中的id对应

	@Column(name = "sender_id", nullable = false, length = 30)
	private long senderId;// 与eng_pt_user中的id对应发送人

	@Column(name = "sender_time", length = 19)
	private Timestamp senderTime;// 发送信息时间

	@Column(name = "message_content", length = 500)
	private String messageContent;// 短信内容
	
	@Column(name = "message_var", length = 70)
	private String messageVar;// 短信参数

	@Column(name = "back_status_code", length = 2)
	private String backStatusCode;// 短信返回状态码(0/1等)

	@Column(name = "back_status_name", length = 30)
	private String backStatusName;// 状态码转换对应的描述

	@Column(name = "back_balance", length = 30)
	private String backBalance;// 短信剩余条数
    
	// Constructors

	public String getMessageVar() {
		return messageVar;
	}

	public void setMessageVar(String messageVar) {
		this.messageVar = messageVar;
	}

	/** default constructor */
	public EngPtSms() {
	}

	/** minimal constructor */
	public EngPtSms(String cfId) {
		this.cfId = cfId;
	}

	/** full constructor */
	public EngPtSms(String cfId, long deliveryId, long senderId, Timestamp senderTime, String messageContent,
			String messageVar,String backStatusCode, String backStatusName, String backContent) {
		this.cfId = cfId;
		this.deliveryId = deliveryId;
		this.senderId = senderId;
		this.senderTime = senderTime;
		this.messageContent = messageContent;
		this.backStatusCode = backStatusCode;
		this.backStatusName = backStatusName;
		this.backBalance = backContent;
		this.messageVar =messageVar;
	}

	// Property accessors
	

	public String getCfId() {
		return this.cfId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCfId(String cfId) {
		this.cfId = cfId;
	}


	public long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public Timestamp getSenderTime() {
		return senderTime;
	}

	public void setSenderTime(Timestamp senderTime) {
		this.senderTime = senderTime;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getBackStatusCode() {
		return backStatusCode;
	}

	public void setBackStatusCode(String backStatusCode) {
		this.backStatusCode = backStatusCode;
	}

	public String getBackStatusName() {
		return backStatusName;
	}

	public void setBackStatusName(String backStatusName) {
		this.backStatusName = backStatusName;
	}

	
	public String getBackBalance() {
		return backBalance;
	}

	public void setBackBalance(String backBalance) {
		this.backBalance = backBalance;
	}
    
	public void delete(Connection con) throws Exception {
		String sql = "delete from eng_pt_sms where id = ?";
		PreparedStatement pst = null;
		pst = con.prepareStatement(sql);
		pst.setLong(1, getId());
		pst.execute();
		pst.close();
	}
	
	public void save(Connection con) throws Exception {
		String sql = null;
		sql = "insert into eng_pt_sms"
				+ "(id,cf_id,delivery_id,sender_id,sender_time,message_content,message_var,back_status_code,back_status_name,back_balance) "
				+ "values (?,?,?,?,?,?,?,?,?,?)";
		setSenderTime(new Timestamp(System.currentTimeMillis()));

		PreparedStatement pst = con.prepareStatement(sql);

		int i = 1;
		pst.setLong(i++, getId());
		pst.setString(i++, getCfId());
		pst.setLong(i++, getDeliveryId());
		pst.setLong(i++, getSenderId());
		pst.setTimestamp(i++, getSenderTime());
		pst.setString(i++, getMessageContent());
		pst.setString(i++, getMessageVar());
		pst.setString(i++, getBackStatusCode());
		pst.setString(i++, getBackStatusName());
		pst.setString(i++, getBackBalance());
		pst.execute();
		pst.close();
	}

}