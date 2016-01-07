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
 * 预付款结算清单.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_ADVANCE")
public class FiAdvance implements java.io.Serializable,AuditableEntity {
	private Long id;
	private Long customerId;//客商ID(必输参数)
	private String customerName;//客商名称(必输参数)
	private Long settlementType;//收付类型(1:存款、2:取款)(必输参数)
	private Double settlementAmount; //结算金额(必输参数)
	private Double settlementBalance;//余额
	private String sourceData;//数据来源(必输参数)
	private Long sourceNo;//来源单号(必输参数)
	private String remark;//备注
	private String updateName;
	private Date updateTime;
	private String createName;
	private Date createTime;
	private Long departId;
	private String departName;
	private Long status=1l;//状态(0:作废,1:正常)
	private Long fiAdvanceId;//预付款设置Id
	private String ts;
	private Double verificationAmount; //核销金额
	private Date verificationTime; //核销时间
	private Long verificationStatus=0L; //核销状态(0:未核销,1:已核销)

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_ADVANCE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", length = 50)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "SETTLEMENT_TYPE", precision = 22, scale = 0)
	public Long getSettlementType() {
		return this.settlementType;
	}

	public void setSettlementType(Long settlementType) {
		this.settlementType = settlementType;
	}

	@Column(name = "SETTLEMENT_AMOUNT", precision = 10)
	public Double getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	@Column(name = "SETTLEMENT_BALANCE", precision = 10)
	public Double getSettlementBalance() {
		return this.settlementBalance;
	}

	public void setSettlementBalance(Double settlementBalance) {
		this.settlementBalance = settlementBalance;
	}

	@Column(name = "SOURCE_DATA", length = 10)
	public String getSourceData() {
		return this.sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	@Column(name = "SOURCE_NO", precision = 22, scale = 0)
	public Long getSourceNo() {
		return this.sourceNo;
	}

	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd hh:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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


	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "FIADVANCEID", precision = 22, scale = 0)
	public Long getFiAdvanceId() {
		return fiAdvanceId;
	}

	public void setFiAdvanceId(Long fiAdvanceId) {
		this.fiAdvanceId = fiAdvanceId;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	@Column(name = "VERIFICATION_AMOUNT", precision = 10)
	public Double getVerificationAmount() {
		return verificationAmount;
	}

	public void setVerificationAmount(Double verificationAmount) {
		this.verificationAmount = verificationAmount;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "VERIFICATION_TIME", length = 7)
	public Date getVerificationTime() {
		return verificationTime;
	}

	public void setVerificationTime(Date verificationTime) {
		this.verificationTime = verificationTime;
	}

	@Column(name = "VERIFICATION_STATUS", precision = 22, scale = 0)
	public Long getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(Long verificationStatus) {
		this.verificationStatus = verificationStatus;
	}	
}