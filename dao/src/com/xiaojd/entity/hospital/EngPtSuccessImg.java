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
 * EngPtSuccessImg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eng_pt_success_img", catalog = "sz_hospital")
public class EngPtSuccessImg implements java.io.Serializable {

	// Fields

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 30)
	private long id;

	@Column(name = "img_no", nullable = false, length = 40)
	private String imgNo;// 处方id_订单id

	@Column(name = "img", nullable = false, length = 30)
	private String img;// 与eng_pt_delivery中的id对应

	@Column(name = "create_date", length = 19)
	private Timestamp createDate;// 发送信息时间

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImgNo() {
		return imgNo;
	}

	public void setImgNo(String imgNo) {
		this.imgNo = imgNo;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}