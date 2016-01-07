package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

/**
 * BizOperateLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BIZ_OPERATE_LOG", schema = "PSONLINE")
public class BizOperateLog implements java.io.Serializable {

	// Fields

	private String pkOl;//主键
	private String DNo;
	private String remark;
	private String onlineRemark;
	private Date oprTime;
	private Date createTime;
	private String deleteFlag;
	private String free1;
	private String free2;
	private Long free3;

	// Constructors

	/** default constructor */
	public BizOperateLog() {
	}

	/** minimal constructor */
	public BizOperateLog(String pkOl) {
		this.pkOl = pkOl;
	}

	/** full constructor */
	public BizOperateLog(String pkOl, String DNo, String remark,
			String onlineRemark, Date oprTime, Date createTime,
			String deleteFlag, String free1, String free2, Long free3) {
		this.pkOl = pkOl;
		this.DNo = DNo;
		this.remark = remark;
		this.onlineRemark = onlineRemark;
		this.oprTime = oprTime;
		this.createTime = createTime;
		this.deleteFlag = deleteFlag;
		this.free1 = free1;
		this.free2 = free2;
		this.free3 = free3;
	}

	// Property accessors
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
	@Column(name = "PK_OL", unique = true, nullable = false, length = 32)
	public String getPkOl() {
		return this.pkOl;
	}

	public void setPkOl(String pkOl) {
		this.pkOl = pkOl;
	}

	@Column(name = "D_NO", length = 10)
	public String getDNo() {
		return this.DNo;
	}

	public void setDNo(String DNo) {
		this.DNo = DNo;
	}

	@Column(name = "REMARK", length = 260)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "ONLINE_REMARK", length = 260)
	public String getOnlineRemark() {
		return this.onlineRemark;
	}

	public void setOnlineRemark(String onlineRemark) {
		this.onlineRemark = onlineRemark;
	}

	@JSON(format = "yyyy-MM-dd HH:mm ss")
	@Column(name = "OPR_TIME")
	public Date getOprTime() {
		return this.oprTime;
	}

	public void setOprTime(Date oprTime) {
		this.oprTime = oprTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm ss")
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "FREE1", length = 20)
	public String getFree1() {
		return this.free1;
	}

	public void setFree1(String free1) {
		this.free1 = free1;
	}

	@Column(name = "FREE2", length = 20)
	public String getFree2() {
		return this.free2;
	}

	public void setFree2(String free2) {
		this.free2 = free2;
	}

	@Column(name = "FREE3", scale = 0)
	public Long getFree3() {
		return this.free3;
	}

	public void setFree3(Long free3) {
		this.free3 = free3;
	}

}