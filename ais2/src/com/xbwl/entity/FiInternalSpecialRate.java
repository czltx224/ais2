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
 * �ڲ�����ͻ�Э�������ʵ����
 */
@Entity
@Table(name = "FI_INTERNAL_SPECIAL_RATE")
public class FiInternalSpecialRate implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long customerId;//��������id
	private String customerName;//��������
	private String valuationType;//�Ƽ۷�ʽ(����/���/����)
	private String customerType;//�ͻ�����(��������\�ջ���)
	private Double rate;//����
	private Double lowest;//���һƱ
	private String remark;//��ע
	private Long departId;//��������id
	private String departName;//��������
	private Date createTime;//����ʱ��
	private String createName;//������
	private Date updateTime;//�޸�ʱ��
	private String updateName;//�޸���
	private Long status=1L;//״̬��0���ϣ�1����
	private String ts;//ʱ���

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