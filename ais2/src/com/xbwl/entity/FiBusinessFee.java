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
 * FiBusinessFee entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_BUSINESS_FEE")
public class FiBusinessFee implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	private String workflowNo;// ���̺�
	private Long belongDepartId;// ��������Id
	private Long customerId;// ����Id
	private Double amount;// Ӧ�����
	private Double volume;// ����
	private String remark;// ժҪ
	private String collectionAccount;// �տ��ʺ�
	private String collectionBank;// �տ��˿�����
	private Long businessType;// ҵ�������(1:��˽,2:�Թ�)
	private Long status;// ���״̬(0:����,1:δ���,2:�����)
	private Double rate;// ����
	private Long departId;// ����ҵ����id
	private String departName;// ����ҵ����
	private String createName;// ������
	private Date createTime;// ����ʱ��
	private Date updateTime;// �޸�ʱ��
	private String updateName;// �޸���
	private String ts;// ʱ���
	private Long collectionCustomerId;// �տ����ID

	private String businessMonth;// ҵ���·�(2011-01)
	private String  settlement;  //���ü��㷽ʽ
	private Long fiBusinessFeePriceId;
	private Double turnover; //Ӫҵ��
	// Constructors

	/** full constructor */
	public FiBusinessFee(Long id, String workflowNo, Long belongDepartId,
			Long customerId, Double amount, Double volume, String remark,
			String collectionAccount, String collectionBank, Long businessType,
			Long status, Double rate, Long departId, String departName,
			String createName, Date createTime, Date updateTime,
			String updateName, String ts) {
		this.id = id;
		this.workflowNo = workflowNo;
		this.belongDepartId = belongDepartId;
		this.customerId = customerId;
		this.amount = amount;
		this.volume = volume;
		this.remark = remark;
		this.collectionAccount = collectionAccount;
		this.collectionBank = collectionBank;
		this.businessType = businessType;
		this.status = status;
		this.rate = rate;
		this.departId = departId;
		this.departName = departName;
		this.createName = createName;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_BUSINESS_FEE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "WORKFLOW_NO", length = 20)
	public String getWorkflowNo() {
		return this.workflowNo;
	}

	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	@Column(name = "BELONG_DEPART_ID", precision = 22, scale = 0)
	public Long getBelongDepartId() {
		return this.belongDepartId;
	}

	public void setBelongDepartId(Long belongDepartId) {
		this.belongDepartId = belongDepartId;
	}

	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "VOLUME", precision = 10)
	public Double getVolume() {
		return this.volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "COLLECTION_ACCOUNT", length = 50)
	public String getCollectionAccount() {
		return this.collectionAccount;
	}

	public void setCollectionAccount(String collectionAccount) {
		this.collectionAccount = collectionAccount;
	}

	@Column(name = "COLLECTION_BANK", length = 50)
	public String getCollectionBank() {
		return this.collectionBank;
	}

	public void setCollectionBank(String collectionBank) {
		this.collectionBank = collectionBank;
	}

	@Column(name = "BUSINESS_TYPE", precision = 22, scale = 0)
	public Long getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(Long businessType) {
		this.businessType = businessType;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "RATE", precision = 10)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
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

	@Column(name = "COLLECTION_CUSTOMER_ID")
	public Long getCollectionCustomerId() {
		return collectionCustomerId;
	}

	public void setCollectionCustomerId(Long collectionCustomerId) {
		this.collectionCustomerId = collectionCustomerId;
	}

	@Column(name = "BUSINESS_MONTH")
	public String getBusinessMonth() {
		return businessMonth;
	}

	public void setBusinessMonth(String businessMonth) {
		this.businessMonth = businessMonth;
	}

	@Column(name = "FI_BUSINESS_FEE_PRICE_ID")
	public Long getFiBusinessFeePriceId() {
		return fiBusinessFeePriceId;
	}

	public void setFiBusinessFeePriceId(Long fiBusinessFeePriceId) {
		this.fiBusinessFeePriceId = fiBusinessFeePriceId;
	}

	@Column(name = "SETTLEMENT")
	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	@Column(name = "TURNOVER")
	public Double getTurnover() {
		return turnover;
	}

	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}

	/** default constructor */
	public FiBusinessFee() {
		this.status=1l;
	}

	/** minimal constructor */
	public FiBusinessFee(Long id) {
		this.id = id;
	}

}