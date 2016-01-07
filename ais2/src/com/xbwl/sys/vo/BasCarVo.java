package com.xbwl.sys.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili time Jun 28, 2011 6:38:36 PM
 */
public class BasCarVo implements java.io.Serializable {

	private Long id;// 序号1
	private String carCode;// 车牌号2
	private Long departId;// 登记网点3
	private String departName;// 登记网点名称3
	private String typeCode;// 车型4
	private String cartrunkNo;// 车架号5
	private String engineNo;// 发动机6
	private Date buyDate;// 购买时间7
	private Long loadWeight;// 理论载重8
	private Long maxloadWeight;// 实际载重9
	private Long loadCube;// 理论方数10
	private Long maxloadCube;// 实际方数11
	private String remark;// 备注12
	private String type;// 车源13
	private String property;// 属性14
	private String comfirtBy;// 人15
	private Date comfirtDate;// 打入时间16
	private String createName;// 创建人17
	private Date createTime;// 创建时间18
	private String updateName;// 修改人19
	private Date updateTime;// 修改时间20
	private String ts;// 时间戳21
	private String gpsNo;// GPS编码
	private String carBrand;//车辆品牌
	
	private String carStatus;
	
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getCartrunkNo() {
		return cartrunkNo;
	}

	public void setCartrunkNo(String cartrunkNo) {
		this.cartrunkNo = cartrunkNo;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public Long getLoadWeight() {
		return loadWeight;
	}

	public void setLoadWeight(Long loadWeight) {
		this.loadWeight = loadWeight;
	}

	public Long getMaxloadWeight() {
		return maxloadWeight;
	}

	public void setMaxloadWeight(Long maxloadWeight) {
		this.maxloadWeight = maxloadWeight;
	}

	public Long getLoadCube() {
		return loadCube;
	}

	public void setLoadCube(Long loadCube) {
		this.loadCube = loadCube;
	}

	public Long getMaxloadCube() {
		return maxloadCube;
	}

	public void setMaxloadCube(Long maxloadCube) {
		this.maxloadCube = maxloadCube;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getComfirtBy() {
		return comfirtBy;
	}

	public void setComfirtBy(String comfirtBy) {
		this.comfirtBy = comfirtBy;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getComfirtDate() {
		return comfirtDate;
	}

	public void setComfirtDate(Date comfirtDate) {
		this.comfirtDate = comfirtDate;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getGpsNo() {
		return gpsNo;
	}

	public void setGpsNo(String gpsNo) {
		this.gpsNo = gpsNo;
	}

	public BasCarVo() {
		super();
	}

	public BasCarVo(Long id) {
		super();
		this.id = id;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
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

	@JSON(format = "yyyy-MM-dd")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}
	
}
