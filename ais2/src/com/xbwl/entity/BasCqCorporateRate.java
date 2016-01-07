package com.xbwl.entity;

// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.common.utils.Rate;

/**
 * BasCqCorporateRate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Rate(mapping="����Э���")
@Table(name = "BAS_CQ_CORPORATE_RATE")
public class BasCqCorporateRate implements java.io.Serializable,
		AuditableEntity {

	// Fields

	private Long id;
	
	@Rate(mapping="����˾ID")
	private Long cusId;// ����˾ID
	
	@Rate(mapping="��ʼ����")
	private Date startDate;// ��ʼ����
	
	@Rate(mapping="��������")
	private Date endDate;// ��������
	
	@Rate(mapping="���䷽ʽ")
	private String trafficMode;// ���䷽ʽ
	
	@Rate(mapping="���ͷ�ʽ")
	private String distributionMode;// ���ͷ�ʽ
	
	@Rate(mapping="�����ʽ")
	private String takeMode;// �����ʽ
	
	@Rate(mapping="�Ƽ۷�ʽ")
	private String valuationType;// �Ƽ۷�ʽ
	
	@Rate(mapping="���һƱ")
	private Double lowPrice;// ���һƱ
	
	@Rate(mapping="500KG���µȼ���")
	private Double stage1Rate;// 500KG���µȼ���
	
	@Rate(mapping="1000KG��500KG�ȼ���")
	private Double stage2Rate;// 1000KG��500KG�ȼ���
	
	@Rate(mapping="1000KG���ϵȼ���")
	private Double stage3Rate;// 1000KG���ϵȼ���
	
	@Rate(mapping="״̬")
	private Long status;// ״̬
	
	@Rate(mapping="��������")
	private String cusName;// ��������
	
	@Rate(mapping="�ۿ�")
	private Double discount;// �ۿ�
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	
	@Rate(mapping="��ַ����")
	private String addressType;// ��ַ����
	private Long departId;

	@Rate(mapping="��ʼ��ַ")
	private String startAddr;// ��ʼ��ַ
	
	@Rate(mapping="������ַ")
	private String endAddr;// ������ַ

	// Constructors

	/** default constructor */
	public BasCqCorporateRate() {
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_CQ_CORPORATE_RATE ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUS_ID", nullable = false, precision = 10, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "START_DATE", nullable = false, length = 7)
	@JSON(format = "yyyy-MM-dd")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "END_DATE")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "TRAFFIC_MODE", nullable = false, length = 20)
	public String getTrafficMode() {
		return this.trafficMode;
	}

	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}

	@Column(name = "DISTRIBUTION_MODE", nullable = false, length = 20)
	public String getDistributionMode() {
		return this.distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	@Column(name = "TAKE_MODE", nullable = false, length = 20)
	public String getTakeMode() {
		return this.takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	@Column(name = "LOW_PRICE", nullable = false, precision = 8)
	public Double getLowPrice() {
		return this.lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	@Column(name = "STAGE1_RATE", precision = 8)
	public Double getStage1Rate() {
		return this.stage1Rate;
	}

	public void setStage1Rate(Double stage1Rate) {
		this.stage1Rate = stage1Rate;
	}

	@Column(name = "STAGE2_RATE", precision = 8)
	public Double getStage2Rate() {
		return this.stage2Rate;
	}

	public void setStage2Rate(Double stage2Rate) {
		this.stage2Rate = stage2Rate;
	}

	@Column(name = "STAGE3_RATE", precision = 8)
	public Double getStage3Rate() {
		return this.stage3Rate;
	}

	public void setStage3Rate(Double stage3Rate) {
		this.stage3Rate = stage3Rate;
	}

	@Column(name = "STATUS", nullable = false, precision = 1, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "CUS_NAME", nullable = false, length = 200)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "DISCOUNT", nullable = false)
	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "CREATE_TIME", length = 7)
	@JSON(format = "yyyy-MM-dd")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "UPDATE_TIME", length = 7)
	@JSON(format = "yyyy-MM-dd")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "VALUATION_TYPE", length = 20)
	public String getValuationType() {
		return valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	@Column(name = "ADDRESS_TYPE", length = 20)
	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	@Column(name = "DEPART_ID")
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "START_ADDR")
	public String getStartAddr() {
		return startAddr;
	}

	public void setStartAddr(String startAddr) {
		this.startAddr = startAddr;
	}

	@Column(name = "END_ADDR")
	public String getEndAddr() {
		return endAddr;
	}

	public void setEndAddr(String endAddr) {
		this.endAddr = endAddr;
	}

}