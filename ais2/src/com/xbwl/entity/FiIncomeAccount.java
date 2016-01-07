package com.xbwl.entity;
// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * 收入收银交账报表
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_INCOME_ACCOUNT")
public class FiIncomeAccount implements java.io.Serializable,AuditableEntity {
	private Long id;
	private Long batchNo;//交账单号
	private String typeName; //类型(收入\收银)
	private Double cpFee;//预付收入
	private Double consigneeFee;//到付收入
	private Double incomeAmount;//收入总额
	private Double cashAmount;//现金
	private Double posAmount;//POS
	private Double checkAmount;//支票
	private Double intecollectionAmount;//内部代收
	private Double eliminationAmount;//到付冲销
	private Double collectionAmount;//代收货款
	private Double consigneeAmount;//到付款
	private Long accountStatus=0L;//交账状态(0：未交账,1：已交账)
	private Long status=1L;//状态(0：作废,1：正常)
	private Date accountData;//日期
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long departId;
	private String departName;//所属业务部门


	@Id  
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_INCOME_ACCOUNT",allocationSize=1)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "BATCH_NO", precision = 22, scale = 0)
	public Long getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "TYPE_NAME", length = 20)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "CP_FEE", precision = 10)
	public Double getCpFee() {
		return this.cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	@Column(name = "CONSIGNEE_FEE", precision = 10)
	public Double getConsigneeFee() {
		return this.consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	@Column(name = "INCOME_AMOUNT", precision = 10)
	public Double getIncomeAmount() {
		return this.incomeAmount;
	}

	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	@Column(name = "CASH_AMOUNT", precision = 10)
	public Double getCashAmount() {
		return this.cashAmount;
	}

	public void setCashAmount(Double cashAmount) {
		this.cashAmount = cashAmount;
	}

	@Column(name = "POS_AMOUNT", precision = 10)
	public Double getPosAmount() {
		return this.posAmount;
	}

	public void setPosAmount(Double posAmount) {
		this.posAmount = posAmount;
	}
	
	@Column(name = "CHECK_AMOUNT", precision = 10)
	public Double getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}

	@Column(name = "INTECOLLECTION_AMOUNT", precision = 10)
	public Double getIntecollectionAmount() {
		return this.intecollectionAmount;
	}

	public void setIntecollectionAmount(Double intecollectionAmount) {
		this.intecollectionAmount = intecollectionAmount;
	}

	@Column(name = "ELIMINATION_AMOUNT", precision = 10)
	public Double getEliminationAmount() {
		return this.eliminationAmount;
	}

	public void setEliminationAmount(Double eliminationAmount) {
		this.eliminationAmount = eliminationAmount;
	}

	@Column(name = "COLLECTION_AMOUNT", precision = 10)
	public Double getCollectionAmount() {
		return this.collectionAmount;
	}

	public void setCollectionAmount(Double collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	@Column(name = "CONSIGNEE_AMOUNT", precision = 10)
	public Double getConsigneeAmount() {
		return this.consigneeAmount;
	}

	public void setConsigneeAmount(Double consigneeAmount) {
		this.consigneeAmount = consigneeAmount;
	}

	@Column(name = "ACCOUNT_STATUS", precision = 22, scale = 0)
	public Long getAccountStatus() {
		return this.accountStatus;
	}

	public void setAccountStatus(Long accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "ACCOUNT_DATA", length = 7)
	public Date getAccountData() {
		return this.accountData;
	}

	public void setAccountData(Date accountData) {
		this.accountData = accountData;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return departName;
	}
	
	public void setDepartName(String departName) {
		this.departName = departName;
	}

}