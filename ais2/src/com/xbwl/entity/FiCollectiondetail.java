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
 * FiPaymentcollectiondetail entity:代收货款对账明细.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_COLLECTIONDETAIL")
public class FiCollectiondetail implements java.io.Serializable, AuditableEntity {
	private Long id;
	private Long customerId;//客商ID
	private String customerName;//客商名称
	private String flightmainno;//航空主单号
	private Long dno;//配送单号
	private String sourceData; //数据来源
	private Long sourceNo;//来源单号
	private Double bulk;//体积
	private Long piece;// 件数
	private Double cusWeight;// 计费重量
	private Double amount;//金额
	private String reconciliationUser;//对账员
	private Long reconciliationNo;//对账单号
	private Long reconciliationStatus;//对账状态
	private Double verificationAmount; //核销金额
	private Long verificationStatus;//核销状态
	private Long createDeptid;//所属部门
	private String createDept;
	private Date createTime;
	private String orderfields;
	private Date updateTime;
	private String updateName;
	private String createName;
	private String ts;
	private Long batch; //批次号
	
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_COLLECTIONDETAIL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUSTOMER_ID", precision = 10, scale = 0)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", length = 200)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "FLIGHTMAINNO", length = 50)
	public String getFlightmainno() {
		return this.flightmainno;
	}

	public void setFlightmainno(String flightmainno) {
		this.flightmainno = flightmainno;
	}

	@Column(name = "D_NO", precision = 10, scale = 0)
	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "SOURCE_DATA", length = 10)
	public String getSourceData() {
		return this.sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	@Column(name = "SOURCE_NO", precision = 10, scale = 0)
	public Long getSourceNo() {
		return this.sourceNo;
	}

	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}

	@Column(name = "BULK", precision = 7)
	public Double getBulk() {
		return this.bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	@Column(name = "PIECE", precision = 22, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "CUS_WEIGHT", precision = 7, scale = 1)
	public Double getCusWeight() {
		return this.cusWeight;
	}

	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "RECONCILIATION_USER", length = 20)
	public String getReconciliationUser() {
		return this.reconciliationUser;
	}

	public void setReconciliationUser(String reconciliationUser) {
		this.reconciliationUser = reconciliationUser;
	}

	@Column(name = "RECONCILIATION_NO", precision = 10, scale = 0)
	public Long getReconciliationNo() {
		return this.reconciliationNo;
	}

	public void setReconciliationNo(Long reconciliationNo) {
		this.reconciliationNo = reconciliationNo;
	}

	@Column(name = "RECONCILIATION_STATUS", precision = 10, scale = 0)
	public Long getReconciliationStatus() {
		return this.reconciliationStatus;
	}

	public void setReconciliationStatus(Long reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
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

	@Column(name = "CREATE_DEPTID", precision = 22, scale = 0)
	public Long getCreateDeptid() {
		return this.createDeptid;
	}

	public void setCreateDeptid(Long createDeptid) {
		this.createDeptid = createDeptid;
	}

	@Column(name = "CREATE_DEPT", length = 50)
	public String getCreateDept() {
		return this.createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "ORDERFIELDS", length = 200)
	public String getOrderfields() {
		return this.orderfields;
	}

	public void setOrderfields(String orderfields) {
		this.orderfields = orderfields;
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

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "BATCH", precision = 22, scale = 0)
	public Long getBatch() {
		return batch;
	}

	public void setBatch(Long batch) {
		this.batch = batch;
	}

}