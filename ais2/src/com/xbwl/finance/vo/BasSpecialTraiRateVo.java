package com.xbwl.finance.vo;
// default package

import java.util.Date;

/**
 * BasSpecialTraiRate entity.@author MyEclipse Persistence Tools
 */
public class BasSpecialTraiRateVo implements java.io.Serializable {

	// Fields

	private Long id;
	private Long specialTrainLineId;
	private Double van;
	private Double goldCupCar;
	private Double twoTonCar;
	private Double threeTonCar;
	private Double chillCar;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String cusName;
	private Long cusId;
	private String lineName;
	private Double fiveTonCar;
	private String roadType;
	private String addrType;
	private Long departId;
	private String departName;
	private Long status;

	// Constructors

	/** default constructor */
	public BasSpecialTraiRateVo() {
	}

	/** minimal constructor */
	public BasSpecialTraiRateVo(Long id, Long specialTrainLineId,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts, String cusName, Long cusId) {
		this.id = id;
		this.specialTrainLineId = specialTrainLineId;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.cusName = cusName;
		this.cusId = cusId;
	}

	/** full constructor */
	public BasSpecialTraiRateVo(Long id, Long specialTrainLineId, Double van,
			Double goldCupCar, Double twoTonCar, Double threeTonCar,
			Double chillCar, String createName, Date createTime,
			String updateName, Date updateTime, String ts, String cusName,
			Long cusId) {
		this.id = id;
		this.specialTrainLineId = specialTrainLineId;
		this.van = van;
		this.goldCupCar = goldCupCar;
		this.twoTonCar = twoTonCar;
		this.threeTonCar = threeTonCar;
		this.chillCar = chillCar;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.cusName = cusName;
		this.cusId = cusId;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSpecialTrainLineId() {
		return this.specialTrainLineId;
	}

	public void setSpecialTrainLineId(Long specialTrainLineId) {
		this.specialTrainLineId = specialTrainLineId;
	}

	public Double getVan() {
		return this.van;
	}

	public void setVan(Double van) {
		this.van = van;
	}

	public Double getGoldCupCar() {
		return this.goldCupCar;
	}

	public void setGoldCupCar(Double goldCupCar) {
		this.goldCupCar = goldCupCar;
	}

	public Double getTwoTonCar() {
		return this.twoTonCar;
	}

	public void setTwoTonCar(Double twoTonCar) {
		this.twoTonCar = twoTonCar;
	}

	public Double getThreeTonCar() {
		return this.threeTonCar;
	}

	public void setThreeTonCar(Double threeTonCar) {
		this.threeTonCar = threeTonCar;
	}

	public Double getChillCar() {
		return this.chillCar;
	}

	public void setChillCar(Double chillCar) {
		this.chillCar = chillCar;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Double getFiveTonCar() {
		return fiveTonCar;
	}

	public void setFiveTonCar(Double fiveTonCar) {
		this.fiveTonCar = fiveTonCar;
	}

	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	/**
	 * @return the addrType
	 */
	public String getAddrType() {
		return addrType;
	}

	/**
	 * @param addrType the addrType to set
	 */
	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	/**
	 * @return the departName
	 */
	public String getDepartName() {
		return departName;
	}

	/**
	 * @param departName the departName to set
	 */
	public void setDepartName(String departName) {
		this.departName = departName;
	}

	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the departId
	 */
	public Long getDepartId() {
		return departId;
	}

	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
}