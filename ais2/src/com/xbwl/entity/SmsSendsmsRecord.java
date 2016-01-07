package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

/**
 * 短信发送记录表实体类
 * SmsSendsmsRecord entity.
 * 
 * @author MyEclipse Persistence Tools czl
 */
@Entity
@Table(name = "SMS_SENDSMS_RECORD", schema = "AISUSER")
public class SmsSendsmsRecord implements java.io.Serializable {

	// Fields

	private Long id;
	private String tel;
	private String context;
	private String sendDepart;
	private String sendName;
	private Long sysNo;
	private String smstype;
	private String receiver;
	private String billno;
	private String ipAddr;
	private Date createTime;
	private String createName;
	private String systemName;

	// Constructors

	/** default constructor */
	public SmsSendsmsRecord() {
	}

	/** minimal constructor */
	public SmsSendsmsRecord(Long id) {
		this.id = id;
	}

	/** full constructor */
	public SmsSendsmsRecord(Long id, String tel, String context,
			String sendDepart, String sendName, Long sysNo, String smstype,
			String receiver, String billno, String ipAddr, Date createTime,
			String createName, String systemName) {
		this.id = id;
		this.tel = tel;
		this.context = context;
		this.sendDepart = sendDepart;
		this.sendName = sendName;
		this.sysNo = sysNo;
		this.smstype = smstype;
		this.receiver = receiver;
		this.billno = billno;
		this.ipAddr = ipAddr;
		this.createTime = createTime;
		this.createName = createName;
		this.systemName = systemName;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_SMS_SENDSMS_RECORD")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TEL", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "CONTEXT", length = 1000)
	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Column(name = "SEND_DEPART", length = 100)
	public String getSendDepart() {
		return this.sendDepart;
	}

	public void setSendDepart(String sendDepart) {
		this.sendDepart = sendDepart;
	}

	@Column(name = "SEND_NAME", length = 20)
	public String getSendName() {
		return this.sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	@Column(name = "SYS_NO", precision = 3, scale = 0)
	public Long getSysNo() {
		return this.sysNo;
	}

	public void setSysNo(Long sysNo) {
		this.sysNo = sysNo;
	}

	@Column(name = "SMSTYPE", length = 20)
	public String getSmstype() {
		return this.smstype;
	}

	public void setSmstype(String smstype) {
		this.smstype = smstype;
	}

	@Column(name = "RECEIVER", length = 100)
	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "BILLNO", length = 10)
	public String getBillno() {
		return this.billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	@Column(name = "IP_ADDR", length = 20)
	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 50)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "SYSTEM_NAME", length = 20)
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

}