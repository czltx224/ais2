package com.xbwl.entity;

// default package

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * FiPaymentcollectionset entity：代收货款结算设置.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_COLLECTIONSET")
public class FiCollectionset implements java.io.Serializable,
		AuditableEntity {
	private Long id;
	private Long deptId; //所属部门ID
	private Long customerId; //客商ID
	private Long billingCycle; //对账/结算周期
	private Long isdelete=1l; //1:正常,0:冻结
	private String remark; //备注
	private Date reconciliationDate;//最后对账日期
	private Date createTime;
	private String createName;
	private String orderfields;
	private Date updateTime;
	private String updateName;
	private String ts;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_COLLECTIONSET")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DEPT_ID", precision = 22, scale = 0)
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "BILLING_CYCLE", precision = 22, scale = 0)
	public Long getBillingCycle() {
		return this.billingCycle;
	}

	public void setBillingCycle(Long billingCycle) {
		this.billingCycle = billingCycle;
	}

	@Column(name = "ISDELETE", precision = 22, scale = 0)
	public Long getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(Long isdelete) {
		this.isdelete = isdelete;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JSON(format="yyyy-MM-dd")
	@Column(name = "RECONCILIATION_DATE", length = 7)
	public Date getReconciliationDate() {
		return this.reconciliationDate;
	}

	public void setReconciliationDate(Date reconciliationDate) {
		this.reconciliationDate = reconciliationDate;
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

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}