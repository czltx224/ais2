package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprReturnGoods entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_RETURN_GOODS")
public class OprReturnGoods implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;//序号
	private Long dno;//配送单号
	private Long returnNum;//返货件数
	private String returnType;//返货类型
	private Long outNo;//返货单号
	private String dutyParty;//责任方
	private Double consigneeFee;//提送费
	private Double paymentCollection;//代收款
	private String returnComment;//返货备注
	private Double returnCost;//返货成本
	private String returnDepartName;//返货部门名称
	private Long returnDepart;//返货部门
	private String createName;//创建人
	private Date createTime;//创建时间
	private String updateName;//修改人
	private Date updateTime;//修改时间
	private String ts;//时间戳
	private String outType;//送货类型
	private Long status;//0,删除，1，正常，2，返货入库，3，返货出库
	private Long auditStatus;//审核状态 0、未审核，1、已审核
	// Constructors

	/** default constructor */
	public OprReturnGoods() {
		this.auditStatus=0l;//默认为未审核
	}

	/** minimal constructor */
	public OprReturnGoods(Long id, Long dno, Long returnNum, String returnType,
			String returnDepartName, Long returnDepart, String createName,
			Date createTime, String updateName, Date updateTime, String ts) {
		this.id = id;
		this.dno = dno;
		this.returnNum = returnNum;
		this.returnType = returnType;
		this.returnDepartName = returnDepartName;
		this.returnDepart = returnDepart;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	/** full constructor */
	public OprReturnGoods(Long id, Long dno, Long returnNum, String returnType,
			Long outNo, String dutyParty, Double consigneeFee,
			Double paymentCollection, String returnComment, Double returnCost,
			String returnDepartName, Long returnDepart, String createName,
			Date createTime, String updateName, Date updateTime, String ts,String outType) {
		this.id = id;
		this.dno = dno;
		this.returnNum = returnNum;
		this.returnType = returnType;
		this.outNo = outNo;
		this.dutyParty = dutyParty;
		this.consigneeFee = consigneeFee;
		this.paymentCollection = paymentCollection;
		this.returnComment = returnComment;
		this.returnCost = returnCost;
		this.returnDepartName = returnDepartName;
		this.returnDepart = returnDepart;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.outType=outType;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_RETURN_GOODS ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", nullable = false, precision = 10, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "RETURN_NUM", nullable = false, precision = 7, scale = 0)
	public Long getReturnNum() {
		return this.returnNum;
	}

	public void setReturnNum(Long returnNum) {
		this.returnNum = returnNum;
	}

	@Column(name = "RETURN_TYPE", nullable = false, precision = 7, scale = 0)
	public String getReturnType() {
		return this.returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Column(name = "OUT_NO", precision = 10, scale = 0)
	public Long getOutNo() {
		return this.outNo;
	}

	public void setOutNo(Long outNo) {
		this.outNo = outNo;
	}

	@Column(name = "DUTY_PARTY", length = 20)
	public String getDutyParty() {
		return this.dutyParty;
	}

	public void setDutyParty(String dutyParty) {
		this.dutyParty = dutyParty;
	}

	@Column(name = "CONSIGNEE_FEE", precision = 8)
	public Double getConsigneeFee() {
		return this.consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	@Column(name = "PAYMENT_COLLECTION", precision = 8)
	public Double getPaymentCollection() {
		return this.paymentCollection;
	}

	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	@Column(name = "RETURN_COMMENT", length = 500)
	public String getReturnComment() {
		return this.returnComment;
	}

	public void setReturnComment(String returnComment) {
		this.returnComment = returnComment;
	}

	@Column(name = "RETURN_COST", precision = 8)
	public Double getReturnCost() {
		return this.returnCost;
	}

	public void setReturnCost(Double returnCost) {
		this.returnCost = returnCost;
	}

	@Column(name = "RETURN_DEPART_NAME", nullable = false, length = 20)
	public String getReturnDepartName() {
		return this.returnDepartName;
	}

	public void setReturnDepartName(String returnDepartName) {
		this.returnDepartName = returnDepartName;
	}

	@Column(name = "RETURN_DEPART", nullable = false)
	public Long getReturnDepart() {
		return this.returnDepart;
	}

	public void setReturnDepart(Long returnDepart) {
		this.returnDepart = returnDepart;
	}

	@Column(name = "CREATE_NAME", nullable = false, length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CREATE_TIME", nullable = false, length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", nullable = false, length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", nullable = false, length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", nullable = false)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	@Column(name = "OUT_TYPE")
	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}
	@Column(name = "STATUS")
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "AUDIT_STATUS")
	public Long getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Long auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}