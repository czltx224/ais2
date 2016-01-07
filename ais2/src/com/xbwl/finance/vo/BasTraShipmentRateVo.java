package com.xbwl.finance.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author CaoZhili time Aug 5, 2011 10:12:23 AM
 */
public class BasTraShipmentRateVo implements java.io.Serializable {

	private Long id;// ���
	private Long cusId;// ����ID
	private String trafficMode;// ���䷽ʽ
	private Date startDate;// ��ʼ����
	private Date endDate;// ��������
	private String takeMode;// �����ʽ
	private Double lowPrice;// ���һƱ
	private Double stage1Rate;// 500KG���µȼ���
	private Double stage2Rate;// 1000KG�ȼ���
	private Double stage3Rate;// 1000KG���ϵȼ���
	private Long status;// ״̬
	private String cusName;// ��������
	private Double discount;// �ۿ�
	private Long departId;// ���ű��
	private Date createTime;// ����ʱ��
	private String createName;// ������
	private Date updateTime;// �޸�ʱ��
	private String updateName;// �޸���
	private String ts;// ʱ���
	private String areaType;// ��������

	private String custprop;// ��������

	private String valuationType;// �Ƽ۷�ʽ
	private Long isNotProject;//�Ƿ���Ŀ�ͻ�0����1����
	private String projectCusName;//��Ŀ�ͻ�����
	private Long projectCusId;//��Ŀ�ͻ�ID
	private String speTown;//�������

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	public String getTrafficMode() {
		return trafficMode;
	}

	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getStage1Rate() {
		return stage1Rate;
	}

	public void setStage1Rate(Double stage1Rate) {
		this.stage1Rate = stage1Rate;
	}

	public Double getStage2Rate() {
		return stage2Rate;
	}

	public void setStage2Rate(Double stage2Rate) {
		this.stage2Rate = stage2Rate;
	}

	public Double getStage3Rate() {
		return stage3Rate;
	}

	public void setStage3Rate(Double stage3Rate) {
		this.stage3Rate = stage3Rate;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getCustprop() {
		return custprop;
	}

	public void setCustprop(String custprop) {
		this.custprop = custprop;
	}

	public String getValuationType() {
		return valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	public Long getIsNotProject() {
		return isNotProject;
	}

	public void setIsNotProject(Long isNotProject) {
		this.isNotProject = isNotProject;
	}

	public String getProjectCusName() {
		return projectCusName;
	}

	public void setProjectCusName(String projectCusName) {
		this.projectCusName = projectCusName;
	}

	public Long getProjectCusId() {
		return projectCusId;
	}

	public void setProjectCusId(Long projectCusId) {
		this.projectCusId = projectCusId;
	}

	public String getSpeTown() {
		return speTown;
	}

	public void setSpeTown(String speTown) {
		this.speTown = speTown;
	}
	
}
