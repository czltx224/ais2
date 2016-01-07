package com.xbwl.sys.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili time Jun 28, 2011 6:38:36 PM
 */
public class BasCarVo implements java.io.Serializable {

	private Long id;// ���1
	private String carCode;// ���ƺ�2
	private Long departId;// �Ǽ�����3
	private String departName;// �Ǽ���������3
	private String typeCode;// ����4
	private String cartrunkNo;// ���ܺ�5
	private String engineNo;// ������6
	private Date buyDate;// ����ʱ��7
	private Long loadWeight;// ��������8
	private Long maxloadWeight;// ʵ������9
	private Long loadCube;// ���۷���10
	private Long maxloadCube;// ʵ�ʷ���11
	private String remark;// ��ע12
	private String type;// ��Դ13
	private String property;// ����14
	private String comfirtBy;// ��15
	private Date comfirtDate;// ����ʱ��16
	private String createName;// ������17
	private Date createTime;// ����ʱ��18
	private String updateName;// �޸���19
	private Date updateTime;// �޸�ʱ��20
	private String ts;// ʱ���21
	private String gpsNo;// GPS����
	private String carBrand;//����Ʒ��
	
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
