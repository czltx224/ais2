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
 * ʵ��ʵ����.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_PAID")
public class FiPaid implements java.io.Serializable, AuditableEntity {
	private Long id;
	private Long paidId=0L;//ʵ��ʵ��ID
	private Long fiPaymentId; // Ӧ�ո�����
	private String penyJenis; // ���㷽ʽ(�ֽ����С�POS����֧Ʊ��Ԥ��������Ӧ����Ӧ��)
	private Double settlementAmount=0.0; // ʵ�ո����
	private String accountNum; // �ո����˺�
	private String accountName; // �ո����˺�����
	private String bank; // �ո������
	private Long fiInvoiceId; // �վݺ�
	private Long cashId; // �ֽ�����id
	private String cashName; // �ֽ���������
	private Double verificationAmount=0.0; // �������
	private Long verificationStatus=0l; // ����״̬(0:δ����/1:�Ѻ���)
	private String verificationDept; // ��������
	private String verificationUser; // ������
	private Date verificationTime; // ����ʱ��
	private String createName;
	private Date createTime;
	private String createRemark;
	private Long departId; //����ҵ����id(�������)
	private String departName;//����ҵ����
	private String updateDept;
	private Date updateTime;
	private String updateName;
	private String ts;
	private Long fiCapitaaccountId;//�ո����˺�ID
	private Long fiCashiercollectionId;//�����տID
	private Long fiCheckId;//֧ƱID
	private Long fiAdvanceId;//Ԥ������ˮID
	private Long fiPaidWriteId; // �ո�����ID
	private Long status=1l;//״̬(0:����,1:����)
	private Long fiReceiptId;   //�վݵ���
	private Long fiFundstransferId;//�ʽ𽻽ӵ�ID(�����Ͻ�����)
	private Long fiFundstransferStatus=0L;//�Ͻ�״̬(0δ�Ͻ�,1���Ͻ�)
	private Long createId;//������ID
	private Long accountId;//���˵���(��������������)
	private Long accountDsId;//���˵���(��Ĳ��Ŵ�������)
	private Long accountStatus;//����״̬(0δ����,1�ѽ���)
	private Long incomeDepartId;//���벿��
	

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