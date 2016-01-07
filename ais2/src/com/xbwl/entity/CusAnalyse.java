package com.xbwl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CusAnalyse entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_ANALYSE")
public class CusAnalyse implements java.io.Serializable {

	// Fields

	private Long id;
	private Long cusRecordId;//�ͷ�ID
	private String cusName;//�ͻ�����
	private Double cusIncome;//����
	private Long departId;//ҵ����
	private String departName;
	private Date countTime;//ͳ��ʱ��
	private String importanceLevel;//��Ҫ�̶�
	private String cusDepartCode;//�ͷ����ű���
	private String cusDepartName;//�ͷ���������

	// Constructors

	/** default constructor */
	public CusAnalyse() {
	}

	/** minimal constructor */
	public CusAnalyse(Long id) {
		this.id = id;
	}

	/** full constructor */
	public CusAnalyse(Long id, Long cusRecordId, String cusName,
			Double cusIncome, Long departId, String departName, Date countTime,
			String importanceLevel, String cusDepartCode, String cusDepartName) {
		this.id = id;
		this.cusRecordId = cusRecordId;
		this.cusName = cusName;
		this.cusIncome = cusIncome;
		this.departId = departId;
		this.departName = departName;
		this.countTime = countTime;
		this.importanceLevel = importanceLevel;
		this.cusDepartCode = cusDepartCode;
		this.cusDepartName = cusDepartName;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_CUS_ANALYSE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUS_RECORD_ID", precision = 22, scale = 0)
	public Long getCusRecordId() {
		return this.cusRecordId;
	}

	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}

	@Column(name = "CUS_NAME", length = 100)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "CUS_INCOME", precision = 10)
	public Double getCusIncome() {
		return this.cusIncome;
	}

	public void setCusIncome(Double cusIncome) {
		this.cusIncome = cusIncome;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 100)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COUNT_TIME", length = 7)
	public Date getCountTime() {
		return this.countTime;
	}

	public void setCountTime(Date countTime) {
		this.countTime = countTime;
	}

	@Column(name = "IMPORTANCE_LEVEL", length = 100)
	public String getImportanceLevel() {
		return this.importanceLevel;
	}

	public void setImportanceLevel(String importanceLevel) {
		this.importanceLevel = importanceLevel;
	}

	@Column(name = "CUS_DEPART_CODE", length = 50)
	public String getCusDepartCode() {
		return this.cusDepartCode;
	}

	public void setCusDepartCode(String cusDepartCode) {
		this.cusDepartCode = cusDepartCode;
	}

	@Column(name = "CUS_DEPART_NAME", length = 100)
	public String getCusDepartName() {
		return this.cusDepartName;
	}

	public void setCusDepartName(String cusDepartName) {
		this.cusDepartName = cusDepartName;
	}

}