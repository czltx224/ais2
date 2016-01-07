package com.xbwl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SysQianshou entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_QIANSHOU")
public class SysQianshou implements java.io.Serializable {

	// Fields

	private String sysqsid;
	private String tel;
	private String context;
	private Date createtime;

	// Constructors

	/** default constructor */
	public SysQianshou() {
	}

	/** minimal constructor */
	public SysQianshou(String sysqsid) {
		this.sysqsid = sysqsid;
	}

	/** full constructor */
	public SysQianshou(String sysqsid, String tel, String context,
			Date createtime) {
		this.sysqsid = sysqsid;
		this.tel = tel;
		this.context = context;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@Column(name = "SYSQSID", unique = true, nullable = false, length = 50)
	public String getSysqsid() {
		return this.sysqsid;
	}

	public void setSysqsid(String sysqsid) {
		this.sysqsid = sysqsid;
	}

	@Column(name = "TEL", length = 200)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "CONTEXT", length = 3000)
	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME", length = 7)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}