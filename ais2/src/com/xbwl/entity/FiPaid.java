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
 * 实收实付表.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_PAID")
public class FiPaid implements java.io.Serializable, AuditableEntity {
	private Long id;
	private Long paidId=0L;//实收实付ID
	private Long fiPaymentId; // 应收付单号
	private String penyJenis; // 结算方式(现金、银行、POS机、支票、预付冲销、应付冲应收)
	private Double settlementAmount=0.0; // 实收付金额
	private String accountNum; // 收付款账号
	private String accountName; // 收付款账号名称
	private String bank; // 收付款开户行
	private Long fiInvoiceId; // 收据号
	private Long cashId; // 现金流量id
	private String cashName; // 现金流量名称
	private Double verificationAmount=0.0; // 核销金额
	private Long verificationStatus=0l; // 核销状态(0:未核销/1:已核销)
	private String verificationDept; // 核销部门
	private String verificationUser; // 核销人
	private Date verificationTime; // 核销时间
	private String createName;
	private Date createTime;
	private String createRemark;
	private Long departId; //创建业务部门id(必输参数)
	private String departName;//创建业务部门
	private String updateDept;
	private Date updateTime;
	private String updateName;
	private String ts;
	private Long fiCapitaaccountId;//收付款账号ID
	private Long fiCashiercollectionId;//出纳收款单ID
	private Long fiCheckId;//支票ID
	private Long fiAdvanceId;//预付款流水ID
	private Long fiPaidWriteId; // 收付冲销ID
	private Long status=1l;//状态(0:作废,1:正常)
	private Long fiReceiptId;   //收据单号
	private Long fiFundstransferId;//资金交接单ID(收银上交核销)
	private Long fiFundstransferStatus=0L;//上交状态(0未上交,1已上交)
	private Long createId;//创建人ID
	private Long accountId;//交账单号(本部门收银数据)
	private Long accountDsId;//交账单号(别的部门代收数据)
	private Long accountStatus;//交账状态(0未交账,1已交账)
	private Long incomeDepartId;//收入部门
	

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_PAID")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	@Column(name = "PAID_ID", nullable = false, precision = 22, scale = 0)
	public Long getPaidId() {
		return paidId;
	}

	public void setPaidId(Long paidId) {
		this.paidId = paidId;
	}

	@Column(name = "FI_PAYMENT_ID", nullable = false, precision = 22, scale = 0)
	public Long getFiPaymentId() {
		return this.fiPaymentId;
	}

	public void setFiPaymentId(Long fiPaymentId) {
		this.fiPaymentId = fiPaymentId;
	}

	@Column(name = "PENY_JENIS", length = 20)
	public String getPenyJenis() {
		return this.penyJenis;
	}

	public void setPenyJenis(String penyJenis) {
		this.penyJenis = penyJenis;
	}

	@Column(name = "SETTLEMENT_AMOUNT", precision = 10)
	public Double getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	@Column(name = "ACCOUNT_NUM", length = 50)
	public String getAccountNum() {
		return this.accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	@Column(name = "ACCOUNT_NAME", length = 50)
	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "BANK", length = 50)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "FI_INVOICE_ID", precision = 22, scale = 0)
	public Long getFiInvoiceId() {
		return this.fiInvoiceId;
	}

	public void setFiInvoiceId(Long fiInvoiceId) {
		this.fiInvoiceId = fiInvoiceId;
	}

	@Column(name = "CASH_ID", precision = 22, scale = 0)
	public Long getCashId() {
		return this.cashId;
	}

	public void setCashId(Long cashId) {
		this.cashId = cashId;
	}

	@Column(name = "CASH_NAME", length = 50)
	public String getCashName() {
		return this.cashName;
	}

	public void setCashName(String cashName) {
		this.cashName = cashName;
	}

	@Column(name = "VERIFICATION_AMOUNT", precision = 10)
	public Double getVerificationAmount() {
		return this.verificationAmount;
	}

	public void setVerificationAmount(Double verificationAmount) {
		this.verificationAmount = verificationAmount;
	}

	@Column(name = "VERIFICATION_STATUS", precision = 22, scale = 0)
	public Long getVerificationStatus() {
		return this.verificationStatus;
	}

	public void setVerificationStatus(Long verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	@Column(name = "VERIFICATION_DEPT", length = 50)
	public String getVerificationDept() {
		return this.verificationDept;
	}

	public void setVerificationDept(String verificationDept) {
		this.verificationDept = verificationDept;
	}

	@Column(name = "VERIFICATION_USER", length = 20)
	public String getVerificationUser() {
		return this.verificationUser;
	}

	public void setVerificationUser(String verificationUser) {
		this.verificationUser = verificationUser;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "VERIFICATION_TIME", length = 7)
	public Date getVerificationTime() {
		return this.verificationTime;
	}

	public void setVerificationTime(Date verificationTime) {
		this.verificationTime = verificationTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return createName;
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

	@Column(name = "CREATE_REMARK", length = 500)
	public String getCreateRemark() {
		return this.createRemark;
	}

	public void setCreateRemark(String createRemark) {
		this.createRemark = createRemark;
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

	@Column(name = "UPDATE_DEPT", length = 50)
	public String getUpdateDept() {
		return this.updateDept;
	}

	public void setUpdateDept(String updateDept) {
		this.updateDept = updateDept;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "FICAPITAACCOUNTID", precision = 22, scale = 0)
	public Long getFiCapitaaccountId() {
		return fiCapitaaccountId;
	}

	public void setFiCapitaaccountId(Long fiCapitaaccountId) {
		this.fiCapitaaccountId = fiCapitaaccountId;
	}

	@Column(name = "FICASHIERCOLLECTIONID", precision = 22, scale = 0)
	public Long getFiCashiercollectionId() {
		return fiCashiercollectionId;
	}

	public void setFiCashiercollectionId(Long fiCashiercollectionId) {
		this.fiCashiercollectionId = fiCashiercollectionId;
	}

	@Column(name = "FICHECKID", precision = 22, scale = 0)
	public Long getFiCheckId() {
		return fiCheckId;
	}

	public void setFiCheckId(Long fiCheckId) {
		this.fiCheckId = fiCheckId;
	}

	@Column(name = "FIADVANCEID", precision = 22, scale = 0)
	public Long getFiAdvanceId() {
		return fiAdvanceId;
	}

	public void setFiAdvanceId(Long fiAdvanceId) {
		this.fiAdvanceId = fiAdvanceId;
	}

	@Column(name = "FIPAIDWRITEID", precision = 22, scale = 0)
	public Long getFiPaidWriteId() {
		return fiPaidWriteId;
	}

	public void setFiPaidWriteId(Long fiPaidWriteId) {
		this.fiPaidWriteId = fiPaidWriteId;
	}
	
	@Column(name = "FI_RECEIPT_ID", precision = 22, scale = 0)
	public Long getFiReceiptId() {
		return fiReceiptId;
	}

	public void setFiReceiptId(Long fiReceiptId) {
		this.fiReceiptId = fiReceiptId;
	}

	@Column(name = "FI_FUNDSTRANSFER_ID", precision = 22, scale = 0)
	public Long getFiFundstransferId() {
		return fiFundstransferId;
	}

	public void setFiFundstransferId(Long fiFundstransferId) {
		this.fiFundstransferId = fiFundstransferId;
	}

	@Column(name = "FI_FUNDSTRANSFER_STATUS", precision = 22, scale = 0)
	public Long getFiFundstransferStatus() {
		return fiFundstransferStatus;
	}

	public void setFiFundstransferStatus(Long fiFundstransferStatus) {
		this.fiFundstransferStatus = fiFundstransferStatus;
	}

	@Column(name = "CREATE_ID", precision = 22, scale = 0)
	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	
	@Column(name = "ACCOUNT_ID", precision = 22, scale = 0)
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@Column(name = "ACCOUNT_DS_ID", precision = 22, scale = 0)
	public Long getAccountDsId() {
		return accountDsId;
	}

	public void setAccountDsId(Long accountDsId) {
		this.accountDsId = accountDsId;
	}

	@Column(name = "ACCOUNT_STATUS", precision = 22, scale = 0)
	public Long getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Long accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Column(name = "INCOME_DEPART_ID", precision = 22, scale = 0)
	public Long getIncomeDepartId() {
		return incomeDepartId;
	}

	public void setIncomeDepartId(Long incomeDepartId) {
		this.incomeDepartId = incomeDepartId;
	}

}