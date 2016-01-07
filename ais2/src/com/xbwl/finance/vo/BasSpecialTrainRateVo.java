package com.xbwl.finance.vo;

import java.util.Date;

/**
 * @author CaoZhili time Aug 3, 2011 9:50:45 AM
 */
public class BasSpecialTrainRateVo implements java.io.Serializable {

	private Long id;
	private String specialTrainLineName;
	private Long specialTrainLineId;
	private Double van;
	private Double goldCupCar;
	private Double twoTonCar;
	private Double threeTonCar;
	private Double fiveTonCar;
	private Double chillCar;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String cusName;
	private Long cusId;
	private String roadType;// Â·ÐÍ
	private Long status;
	private Long departId;
	private Double discount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecialTrainLineName() {
		return specialTrainLineName;
	}

	public void setSpecialTrainLineName(String specialTrainLineName) {
		this.specialTrainLineName = specialTrainLineName;
	}

	public Double getVan() {
		return van;
	}

	public void setVan(Double van) {
		this.van = van;
	}

	public Double getGoldCupCar() {
		return goldCupCar;
	}

	public void setGoldCupCar(Double goldCupCar) {
		this.goldCupCar = goldCupCar;
	}

	public Double getTwoTonCar() {
		return twoTonCar;
	}

	public void setTwoTonCar(Double twoTonCar) {
		this.twoTonCar = twoTonCar;
	}

	public Double getThreeTonCar() {
		return threeTonCar;
	}

	public void setThreeTonCar(Double threeTonCar) {
		this.threeTonCar = threeTonCar;
	}

	public Double getFiveTonCar() {
		return fiveTonCar;
	}

	public void setFiveTonCar(Double fiveTonCar) {
		this.fiveTonCar = fiveTonCar;
	}

	public Double getChillCar() {
		return chillCar;
	}

	public void setChillCar(Double chillCar) {
		this.chillCar = chillCar;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getSpecialTrainLineId() {
		return specialTrainLineId;
	}

	public void setSpecialTrainLineId(Long specialTrainLineId) {
		this.specialTrainLineId = specialTrainLineId;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

}
