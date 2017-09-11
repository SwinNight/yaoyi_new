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
@Table(name = "sys_config", catalog = "sz_hospital")
public class SysConfig implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 10)
	private Long id;

	@Column(name = "flag", nullable = false, length = 20)
	private String flag;//分类

	@Column(name = "name", nullable = false, length = 100)
	private String name;//名称

	@Column(name = "value", nullable = false, length = 1200)
	private String value;//配置值

	@Column(name = "enable", nullable = false, length = 1)
	private String enable;//是否可用

	@Column(name = "create_date", nullable = false, length = 19)
	private Timestamp createDate;

	@Column(name = "update_date", nullable = false, length = 19)
	private Timestamp updateDate;
	
	@Column(name = "depict",  length = 50)
	private String depict; //备注

	/** default constructor */
	public SysConfig() {
	}

	/** full constructor */
	public SysConfig(String flag, String name, String value, String enable,
			Timestamp createDate, Timestamp updateDate, String depict) {
		this.flag = flag;
		this.name = name;
		this.value = value;
		this.enable = enable;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.depict = depict;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
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
	
	public String getDepict() {
		return depict;
	}

	public void setDepict(String depict) {
		this.depict = depict;
	}

	public void save(Connection con) throws SQLException {
		String sql = null;
		if (getId().equals("0")||getId().equals("null")){		
			sql = "insert into sys_config(flag,name,value,enable,create_date,update_date) values (?,?,?,?,?,?)";
			setCreateDate(new Timestamp(System.currentTimeMillis()));
			setUpdateDate(new Timestamp(System.currentTimeMillis()));		}
		else{
			sql = "update sys_config set " + " flag=?,name=?,value=?,enable=?,create_date=?,update_date=? where id=?";
			setUpdateDate(new Timestamp(System.currentTimeMillis()));
		}
		PreparedStatement pst = con.prepareStatement(sql);
		int i=1;
		pst.setString 	(i++, getFlag());
		pst.setString 	(i++, getName());
		pst.setString 	(i++, getValue());
		pst.setString 	(i++, getEnable());
		pst.setTimestamp(i++, getCreateDate());
		pst.setTimestamp(i++, getUpdateDate());
		if(getId() != null)
		pst.setLong(i, getId());
		pst.execute();
		pst.close();
	}
	

}