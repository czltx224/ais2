package com.xbwl.entity;

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

/**
 * FiCashiercollectionExcel entity.
 * 出纳收款单导入Excel中单表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_CASHIERCOLLECTION_EXCEL", schema = "AISUSER")
public class FiCashiercollectionExcel implements java.io.Serializable {

	// Fields

	private Long id;
	private Date doMoneyData;  //到账时间
	private String accountNum;  // 账号
	private String remark;   //备注
	private String companyName;  //客户名称
	private Double amount;           //金额
	private Long matchStatus=1L;       //匹配状态(0:不能匹配，1：能匹配)
	private Long batchNo;                    //批次号
	private Long fiCapitaaccountsetId;//银行账号id

	// Constructors

	/** default constructor */
	public FiCashiercollectionExcel() {
	}

	/** minimal constructor */
	public FiCashiercollectionExcel(Long id, String accountNum, Double amount) {
		this.id = id;
		this.accountNum = accountNum;
		this.amount = amount;
	}

	/** full constructor */
	public FiCashiercollectionExcel(Long id, Date doMoneyData,
			String accountNum, String remark, String companyName,
			Double amount, Long matchStatus, Long batchNo) {
		this.id = id;
		this.doMoneyData = doMoneyData;
		this.accountNum = accountNum;
		this.remark = remark;
		this.companyName = companyName;
		this.amount = amount;
		this.matchStatus = matchStatus;
		this.batchNo = batchNo;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 9, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_CASHIERCOLLECTION_EXCEL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format="yyyy-MM-dd")
	@Column(name = "DO_MONEY_DATA", length = 7)
	public Date getDoMoneyData() {
		return this.doMoneyData;
	}

	public void setDoMoneyData(Date doMoneyData) {
		this.doMoneyData = doMoneyData;
	}

	@Column(name = "ACCOUNT_NUM", nullable = false, length = 50)
	public String getAccountNum() {
		return this.accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "COMPANY_NAME", length = 100)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "AMOUNT", nullable = false, precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "MATCH_STATUS", precision = 1, scale = 0)
	public Long getMatchStatus() {
		return this.matchStatus;
	}

	public void setMatchStatus(Long matchStatus) {
		this.matchStatus = matchStatus;
	}

	@Column(name = "BATCH_NO", precision = 22, scale = 0)
	public Long getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
	
	@Column(name = "FI_CAPITAACCOUNTSET_ID", precision = 22, scale = 0)
	public Long getFiCapitaaccountsetId() {
		return this.fiCapitaaccountsetId;
	}

	public void setFiCapitaaccountsetId(Long fiCapitaaccountsetId) {
		this.fiCapitaaccountsetId = fiCapitaaccountsetId;
	}

}