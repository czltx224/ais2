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
import com.xbwl.common.utils.XbwlInt;

/**
 * 客商欠款设置
 * 
 * @author oysz 2011-07-05
 */

@Entity
@Table(name = "FI_ARREARSET")
public class FiArrearset implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long customerId; // 客商
	@XbwlInt(autoDepart=false)
	private Long departId; // 所属部门
	private String contractType; // 合同类型
	private String contractStartTime; // 合同开始时间
	private String contractEndTime; // 合同结束时间
	private String contractNo; // 合同编号
	private String contractExtensionDay; // 合同到期延期(天)
	private String contractExtensionDate; // 合同延期日期
	private Long billingCycle; // 对账/结算周期
	private Double openingBalance; // 期初余额
	private Long credit; // 信用额度
	private Long limit; // 信用期限
	private Date extensionEndDate; // 延期信用截止日期
	private Long additionalAmount; // 追加额度
	private Date additionalAmountDate; // 追加额度截止日期
	private String ispaytoarrears; // 能否到付转欠款
	private String isautoreconciliation; // 能否自动对账
	private String reconciliationUser; // 对账员
	private Date createTime; // 创建时间
	private String createName; // 创建人
	private Date updateTime; // 修改时间
	private String updateName; // 修改人
	private String ts; // 时间戳
	private String orderfields; // 排序字段
	private String remark;//备注
	private Long isDelete=0L; //状态,默认0为可用，1为冻结
	private Long extensionLimit; //延期信用期限(天)

	// Constructors

	/** default constructor */
	public FiArrearset() {
	}

	/** minimal constructor */
	public FiArrearset(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_ARREARSET")
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

	@Column(name = "CONTRACT_TYPE", length = 10)
	public String getContractType() {
		return this.contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	@Column(name = "CONTRACT_START_TIME", length = 10)
	public String getContractStartTime() {
		return this.contractStartTime;
	}

	public void setContractStartTime(String contractStartTime) {
		this.contractStartTime = contractStartTime;
	}

	@Column(name = "CONTRACT_END_TIME", length = 10)
	public String getContractEndTime() {
		return this.contractEndTime;
	}

	public void setContractEndTime(String contractEndTime) {
		this.contractEndTime = contractEndTime;
	}

	@Column(name = "CONTRACT_NO", length = 30)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "CONTRACT_EXTENSION_DAY", length = 10)
	public String getContractExtensionDay() {
		return this.contractExtensionDay;
	}

	public void setContractExtensionDay(String contractExtensionDay) {
		this.contractExtensionDay = contractExtensionDay;
	}

	@Column(name = "CONTRACT_EXTENSION_DATE", length = 10)
	public String getContractExtensionDate() {
		return this.contractExtensionDate;
	}

	public void setContractExtensionDate(String contractExtensionDate) {
		this.contractExtensionDate = contractExtensionDate;
	}

	@Column(name = "BILLING_CYCLE", precision = 22, scale = 0)
	public Long getBillingCycle() {
		return this.billingCycle;
	}

	public void setBillingCycle(Long billingCycle) {
		this.billingCycle = billingCycle;
	}

	@Column(name = "OPENING_BALANCE", precision = 10)
	public Double getOpeningBalance() {
		return this.openingBalance;
	}

	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}

	@Column(name = "CREDIT", precision = 22, scale = 0)
	public Long getCredit() {
		return this.credit;
	}

	public void setCredit(Long credit) {
		this.credit = credit;
	}

	@Column(name = "LIMIT", precision = 22, scale = 0)
	public Long getLimit() {
		return this.limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "EXTENSION_END_DATE", length = 7)
	public Date getExtensionEndDate() {
		return this.extensionEndDate;
	}

	public void setExtensionEndDate(Date extensionEndDate) {
		this.extensionEndDate = extensionEndDate;
	}

	@Column(name = "ADDITIONAL_AMOUNT", precision = 22, scale = 0)
	public Long getAdditionalAmount() {
		return this.additionalAmount;
	}

	public void setAdditionalAmount(Long additionalAmount) {
		this.additionalAmount = additionalAmount;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "ADDITIONAL_AMOUNT_DATE", length = 7)
	public Date getAdditionalAmountDate() {
		return this.additionalAmountDate;
	}

	public void setAdditionalAmountDate(Date additionalAmountDate) {
		this.additionalAmountDate = additionalAmountDate;
	}

	@Column(name = "ISPAYTOARREARS", length = 10)
	public String getIspaytoarrears() {
		return this.ispaytoarrears;
	}

	public void setIspaytoarrears(String ispaytoarrears) {
		this.ispaytoarrears = ispaytoarrears;
	}

	@Column(name = "ISAUTORECONCILIATION", length = 10)
	public String getIsautoreconciliation() {
		return this.isautoreconciliation;
	}

	public void setIsautoreconciliation(String isautoreconciliation) {
		this.isautoreconciliation = isautoreconciliation;
	}

	@Column(name = "RECONCILIATION_USER", length = 20)
	public String getReconciliationUser() {
		return this.reconciliationUser;
	}

	public void setReconciliationUser(String reconciliationUser) {
		this.reconciliationUser = reconciliationUser;
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

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "ORDERFIELDS", length = 200)
	public String getOrderfields() {
		return this.orderfields;
	}

	public void setOrderfields(String orderfields) {
		this.orderfields = orderfields;
	}
	
	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "ISDELETE", precision = 22, scale = 0)
	public Long getIsDelete() {
		return isDelete;
	}
	
	
	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "EXTENSION_LIMIT", precision = 22, scale = 0)
	public Long getExtensionLimit() {
		return extensionLimit;
	}

	public void setExtensionLimit(Long extensionLimit) {
		this.extensionLimit = extensionLimit;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	
}