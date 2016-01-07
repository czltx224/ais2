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

/**
 * 导入POS
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_EXCEL_POS")
public class FiExcelPos implements java.io.Serializable {
	private Long id;
	private String posNo;//终端编号
	private Long transactionNumber;//交易笔数
	private Double amount=0.0;//交易金额
	private Double feeAmount=0.0;//手续费
	private Double settlemenAmount=0.0;//结算金额
	private String collectionDept;//部门
	private Date collectionTime;//日期
	private Long fiCapitaaccountsetId;//银行账号id
	private String accountNum;//入帐卡号
	private String accountName;//户名
	private String merchanCode;//银行商户编号
	private Date createTime;
	private String createName;
	private Long batchNo=0L;//批次号
	private Long matchStatus=0L;//匹配状态(0未匹配,1已匹配)
	private String ts;
	private String remark;

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_EXCEL_POS")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "POS_NO", length = 50)
	public String getPosNo() {
		return this.posNo;
	}

	public void setPosNo(String o) {
		this.posNo = o;
	}

	@Column(name = "TRANSACTION_NUMBER", precision = 22, scale = 0)
	public Long getTransactionNumber() {
		return this.transactionNumber;
	}

	public void setTransactionNumber(Long transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "FEE_AMOUNT", precision = 10)
	public Double getFeeAmount() {
		return this.feeAmount;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}

	@Column(name = "SETTLEMEN_AMOUNT", precision = 10)
	public Double getSettlemenAmount() {
		return this.settlemenAmount;
	}

	public void setSettlemenAmount(Double settlemenAmount) {
		this.settlemenAmount = settlemenAmount;
	}

	@Column(name = "COLLECTION_DEPT", length = 50)
	public String getCollectionDept() {
		return this.collectionDept;
	}

	public void setCollectionDept(String collectionDept) {
		this.collectionDept = collectionDept;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COLLECTION_TIME", length = 7)
	public Date getCollectionTime() {
		return this.collectionTime;
	}

	public void setCollectionTime(Date collectionTime) {
		this.collectionTime = collectionTime;
	}

	@Column(name = "ACCOUNT_NUM", length = 20)
	public String getAccountNum() {
		return this.accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	@Column(name = "ACCOUNT_NAME", length = 20)
	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "MERCHAN_CODE", length = 50)
	public String getMerchanCode() {
		return this.merchanCode;
	}

	public void setMerchanCode(String merchanCode) {
		this.merchanCode = merchanCode;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "BATCH_NO", precision = 22, scale = 0)
	public Long getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
	
	@Column(name = "MATCH_STATUS", precision = 1, scale = 0)
	public Long getMatchStatus() {
		return this.matchStatus;
	}

	public void setMatchStatus(Long matchStatus) {
		this.matchStatus = matchStatus;
	}
	
	
	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	@Column(name = "FI_CAPITAACCOUNTSET_ID", precision = 22, scale = 0)
	public Long getFiCapitaaccountsetId() {
		return this.fiCapitaaccountsetId;
	}

	public void setFiCapitaaccountsetId(Long fiCapitaaccountsetId) {
		this.fiCapitaaccountsetId = fiCapitaaccountsetId;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}