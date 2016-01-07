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
import com.xbwl.common.utils.Rate;

/**
 * BasTraShipmentRate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_TRA_SHIPMENT_RATE")
@Rate(mapping = "��תЭ���")
public class BasTraShipmentRate implements java.io.Serializable,
		AuditableEntity {

	// Fields

	private Long id;// ���

	@Rate(mapping = "����ID")
	private Long cusId;// ����ID

	@Rate(mapping = "���䷽ʽ")
	private String trafficMode;// ���䷽ʽ

	@Rate(mapping = "��ʼ����")
	private Date startDate;// ��ʼ����

	@Rate(mapping = "��������")
	private Date endDate;// ��������

	@Rate(mapping = "�����ʽ")
	private String takeMode;// �����ʽ

	@Rate(mapping = "���һƱ")
	private Double lowPrice;// ���һƱ

	@Rate(mapping = "500KG���µȼ���")
	private Double stage1Rate;// 500KG���µȼ���

	@Rate(mapping = "1000KG��500KG�ȼ���")
	private Double stage2Rate;// 1000KG��500KG�ȼ���

	@Rate(mapping = "1000KG���ϵȼ���")
	private Double stage3Rate;// 1000KG���ϵȼ���

	@Rate(mapping = "״̬")
	private Long status;// ״̬

	@Rate(mapping = "��������")
	private String cusName;// ��������

	@Rate(mapping = "�ۿ�")
	private Double discount;// �ۿ�
	private Long departId;// ���ű��
	private Date createTime;// ����ʱ��
	private String createName;// ������
	private Date updateTime;// �޸�ʱ��
	private String updateName;// �޸���
	private String ts;// ʱ���

	@Rate(mapping = "��������")
	private String areaType;// ��������

	@Rate(mapping = "�Ƽ۷�ʽ")
	private String valuationType;// �Ƽ۷�ʽ
	
	@Rate(mapping = "�Ƿ���Ŀ�ͻ�")
	private Long isNotProject;//�Ƿ���Ŀ�ͻ�0����1����
	@Rate(mapping = "��Ŀ�ͻ�����")
	private String projectCusName;//��Ŀ�ͻ�����
	@Rate(mapping = "��Ŀ�ͻ�ID")
	private Long projectCusId;//��Ŀ�ͻ�ID
	private String speTown;//������
	
	// Constructors

	/** default constructor */
	public BasTraShipmentRate() {
		this.isNotProject=0l;
		this.status=1l;
	}

	/** minimal constructor */
	public BasTraShipmentRate(Long id) {
		this.id = id;
	}

	/** full constructor */
	public BasTraShipmentRate(Long id, Long cusId, String trafficMode,
			Date startDate, Date endDate, String takeMode, Double lowPrice,
			Double stage1Rate, Double stage2Rate, Double stage3Rate,
			Long status, String cusName, Double discount, Long departId,
			Date createTime, String createName, Date updateTime,
			String updateName, String ts, String areaType) {
		this.id = id;
		this.cusId = cusId;
		this.trafficMode = trafficMode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.takeMode = takeMode;
		this.lowPrice = lowPrice;
		this.stage1Rate = stage1Rate;
		this.stage2Rate = stage2Rate;
		this.stage3Rate = stage3Rate;
		this.status = status;
		this.cusName = cusName;
		this.discount = discount;
		this.departId = departId;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.ts = ts;
		this.areaType = areaType;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_BAS_TRA_SHIPMENT_RATE ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUS_ID", precision = 10, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "TRAFFIC_MODE", length = 20)
	public String getTrafficMode() {
		return this.trafficMode;
	}

	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "TAKE_MODE", length = 20)
	public String getTakeMode() {
		return this.takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	@Column(name = "LOW_PRICE", precision = 8)
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

	@Column(name = "STATUS", precision = 1, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "CUS_NAME", length = 200)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "DISCOUNT")
	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Column(name = "DEPART_ID", precision = 10, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@JSON(format = "yyyy-MM-dd")
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

	@JSON(format = "yyyy-MM-dd")
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

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "AREA_TYPE", length = 20)
	public String getAreaType() {
		return this.areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	@Column(name = "VALUATION_TYPE", length = 20)
	public String getValuationType() {
		return valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	@Column(name = "IS_NOT_PROJECT")
	public Long getIsNotProject() {
		return isNotProject;
	}

	public void setIsNotProject(Long isNotProject) {
		this.isNotProject = isNotProject;
	}

	@Column(name = "PROJECT_CUS_NAME", length = 50)
	public String getProjectCusName() {
		return projectCusName;
	}

	public void setProjectCusName(String projectCusName) {
		this.projectCusName = projectCusName;
	}

	@Column(name = "PROJECT_CUS_ID")
	public Long getProjectCusId() {
		return projectCusId;
	}

	public void setProjectCusId(Long projectCusId) {
		this.projectCusId = projectCusId;
	}
	@Column(name = "SPE_TOWN")
	public String getSpeTown() {
		return speTown;
	}

	public void setSpeTown(String speTown) {
		this.speTown = speTown;
	}
	
	

}