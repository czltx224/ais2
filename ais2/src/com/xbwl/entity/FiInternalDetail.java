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
import com.xbwl.common.utils.BaseObject;

/**
 * FiInternalDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 * 内部明细结算实体类 
 */
@Entity
@Table(name = "FI_INTERNAL_DETAIL")
public class FiInternalDetail extends BaseObject implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long startDepartId;//始发部门id
	private String startDepartName;//始发部门
	private Long endDepartId;//到达部门id
	private String endDepartName;//到达部门
	private Long settlementType;//结算类型(1:收入、2:支出)
	private String distributionMode;//配送方式
	private Double amount;//金额
	private String sourceData;//数据来源
	private Long sourceNo;//来源单号
	private Long belongsDepartId;//所属部门id
	private String belongsDepartName;//所属部门
	private Long dno;//配送单号
	private String remark;//备注
	private Long departId;//创建部门id
	private String departName;//创建部门
	private Date createTime;//创建时间
	private String createName;//创建人
	private Date updateTime;//修改时间
	private String updateName;//修改人
	private Long status=1L;//状态：0作废，1正常
	private Long customerId; //客商ID
	private Long agreementType; //协议价类型(标准协议价\特殊协议价\手动划分)
	private Long agreementId;//协议价ID
	private String ts;//时间戳


	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_INTERNAL_DETAIL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "START_DEPART_ID", precision = 22, scale = 0)
	public Long getStartDepartId() {
		return this.startDepartId;
	}

	public void setStartDepartId(Long startDepartId) {
		this.startDepartId = startDepartId;
	}

	@Column(name = "START_DEPART_NAME", length = 50)
	public String getStartDepartName() {
		return this.startDepartName;
	}

	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}

	@Column(name = "END_DEPART_ID", precision = 22, scale = 0)
	public Long getEndDepartId() {
		return this.endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	@Column(name = "END_DEPART_NAME", length = 50)
	public String getEndDepartName() {
		return this.endDepartName;
	}

	public void setEndDepartName(String endDepartName) {
		this.endDepartName = endDepartName;
	}

	@Column(name = "SETTLEMENT_TYPE", precision = 22, scale = 0)
	public Long getSettlementType() {
		return this.settlementType;
	}

	public void setSettlementType(Long settlementType) {
		this.settlementType = settlementType;
	}

	@Column(name = "DISTRIBUTION_MODE", length = 50)
	public String getDistributionMode() {
		return this.distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "SOURCE_DATA", length = 50)
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

	@Column(name = "BELONGS_DEPART_ID", precision = 22, scale = 0)
	public Long getBelongsDepartId() {
		return this.belongsDepartId;
	}

	public void setBelongsDepartId(Long belongsDepartId) {
		this.belongsDepartId = belongsDepartId;
	}

	@Column(name = "BELONGS_DEPART_NAME", length = 50)
	public String getBelongsDepartName() {
		return this.belongsDepartName;
	}

	public void setBelongsDepartName(String belongsDepartName) {
		this.belongsDepartName = belongsDepartName;
	}

	@Column(name = "D_NO", precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
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

	@Column(name = "STATUS", nullable = false, precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	
	@Column(name = "AGREEMENT_TYPE", precision = 22, scale = 0)
	public Long getAgreementType() {
		return agreementType;
	}

	public void setAgreementType(Long agreementType) {
		this.agreementType = agreementType;
	}

	@Column(name = "AGREEMENT_ID", precision = 22, scale = 0)
	public Long getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(Long agreementId) {
		this.agreementId = agreementId;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}