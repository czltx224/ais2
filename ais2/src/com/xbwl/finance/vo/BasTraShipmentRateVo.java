package com.xbwl.finance.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author CaoZhili time Aug 5, 2011 10:12:23 AM
 */
public class BasTraShipmentRateVo implements java.io.Serializable {

	private Long id;// 序号
	private Long cusId;// 客商ID
	private String trafficMode;// 运输方式
	private Date startDate;// 开始日期
	private Date endDate;// 结束日期
	private String takeMode;// 提货方式
	private Double lowPrice;// 最后一票
	private Double stage1Rate;// 500KG以下等级价
	private Double stage2Rate;// 1000KG等级价
	private Double stage3Rate;// 1000KG以上等级价
	private Long status;// 状态
	private String cusName;// 客商名称
	private Double discount;// 折扣
	private Long departId;// 部门编号
	private Date createTime;// 创建时间
	private String createName;// 创建人
	private Date updateTime;// 修改时间
	private String updateName;// 修改人
	private String ts;// 时间戳
	private String areaType;// 地区类型

	private String custprop;// 客商类型

	private String valuationType;// 计价方式
	private Long isNotProject;//是否项目客户0：否，1：是
	private String projectCusName;//项目客户名称
	private Long projectCusId;//项目客户ID
	private String speTown;//特殊地区

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
