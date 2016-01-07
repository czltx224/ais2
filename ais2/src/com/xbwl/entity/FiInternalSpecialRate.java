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

/**
 * FiInternalSpecialRate entity.
 * 
 * @author MyEclipse Persistence Tools
 * 内部特殊客户协议价设置实体类
 */
@Entity
@Table(name = "FI_INTERNAL_SPECIAL_RATE")
public class FiInternalSpecialRate implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long customerId;//客商名称id
	private String customerName;//客商名称
	private String valuationType;//计价方式(公斤/体积/件数)
	private String customerType;//客户类型(发货代理\收货人)
	private Double rate;//费率
	private Double lowest;//最低一票
	private String remark;//备注
	private Long departId;//创建部门id
	private String departName;//创建部门
	private Date createTime;//创建时间
	private String createName;//创建人
	private Date updateTime;//修改时间
	private String updateName;//修改人
	private Long status=1L;//状态：0作废，1正常
	private String ts;//时间戳

	// Constructors

	/** default constructor */
	public FiInternalSpecialRate() {
	}

	/** minimal constructor */
	public FiInternalSpecialRate(Long id, Long status) {
		this.id = id;
		this.status = status;
	}

	/** full constructor */
	public FiInternalSpecialRate(Long id, Long customerId, String customerName,
			String valuationType, String customerType, Double rate,
			Double lowest, String remark, Long departId, String departName,
			Date createTime, String createName, Date updateTime,
			String updateName, Long status, String ts) {
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
		this.valuationType = valuationType;
		this.customerType = customerType;
		this.rate = rate;
		this.lowest = lowest;
		this.remark = remark;
		this.departId = departId;
		this.departName = departName;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.status = status;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_INTERNAL_SPECIAL_RATE ")
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

	@Column(name = "VALUATION_TYPE", length = 50)
	public String getValuationType() {
		return this.valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	@Column(name = "CUSTOMER_TYPE", length = 50)
	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	@Column(name = "RATE", precision = 10)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "LOWEST", precision = 10)
	public Double getLowest() {
		return this.lowest;
	}

	public void setLowest(Double lowest) {
		this.lowest = lowest;
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

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}