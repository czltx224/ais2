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

/**
 * BasCar entity. Caozhili
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_CAR")
public class BasCar implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;// 序号1
	private String carCode;// 车牌号2
	private Long departId;// 登记网点3
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
	
	private String carStatus;//车辆状态

	// Constructors

	public BasCar(Long id, String carCode, Long departId, String typeCode,
			String cartrunkNo, String engineNo, Date buyDate, Long loadWeight,
			Long maxloadWeight, Long loadCube, Long maxloadCube, String remark,
			String type, String property, String comfirtBy, Date comfirtDate,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts, String gpsNo,String carBrand) {
		super();
		this.id = id;
		this.carCode = carCode;
		this.departId = departId;
		this.typeCode = typeCode;
		this.cartrunkNo = cartrunkNo;
		this.engineNo = engineNo;
		this.buyDate = buyDate;
		this.loadWeight = loadWeight;
		this.maxloadWeight = maxloadWeight;
		this.loadCube = loadCube;
		this.maxloadCube = maxloadCube;
		this.remark = remark;
		this.type = type;
		this.property = property;
		this.comfirtBy = comfirtBy;
		this.comfirtDate = comfirtDate;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.gpsNo = gpsNo;
		this.carBrand=carBrand;
	}

	/** default constructor */
	public BasCar() {
	}

	/** minimal constructor */
	public BasCar(Long id) {
		this.id = id;
	}

	/** full constructor */

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_BAS_CAR ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CAR_CODE", length = 5)
	public String getCarCode() {
		return this.carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "TYPE_CODE", precision = 22, scale = 0)
	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "CARTRUNK_NO", length = 20)
	public String getCartrunkNo() {
		return this.cartrunkNo;
	}

	public void setCartrunkNo(String cartrunkNo) {
		this.cartrunkNo = cartrunkNo;
	}

	@Column(name = "ENGINE_NO", length = 20)
	public String getEngineNo() {
		return this.engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "BUY_DATE", length = 7)
	public Date getBuyDate() {
		return this.buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	@Column(name = "LOAD_WEIGHT", precision = 22, scale = 0)
	public Long getLoadWeight() {
		return this.loadWeight;
	}

	public void setLoadWeight(Long loadWeight) {
		this.loadWeight = loadWeight;
	}

	@Column(name = "MAXLOAD_WEIGHT", precision = 22, scale = 0)
	public Long getMaxloadWeight() {
		return this.maxloadWeight;
	}

	public void setMaxloadWeight(Long maxloadWeight) {
		this.maxloadWeight = maxloadWeight;
	}

	@Column(name = "LOAD_CUBE", precision = 22, scale = 0)
	public Long getLoadCube() {
		return this.loadCube;
	}

	public void setLoadCube(Long loadCube) {
		this.loadCube = loadCube;
	}

	@Column(name = "MAXLOAD_CUBE", precision = 22, scale = 0)
	public Long getMaxloadCube() {
		return this.maxloadCube;
	}

	public void setMaxloadCube(Long maxloadCube) {
		this.maxloadCube = maxloadCube;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "TYPE", precision = 22, scale = 0)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "PROPERTY", precision = 22, scale = 0)
	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Column(name = "COMFIRT_BY", length = 10)
	public String getComfirtBy() {
		return this.comfirtBy;
	}

	public void setComfirtBy(String comfirtBy) {
		this.comfirtBy = comfirtBy;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "COMFIRT_DATE", length = 7)
	public Date getComfirtDate() {
		return this.comfirtDate;
	}

	public void setComfirtDate(Date comfirtDate) {
		this.comfirtDate = comfirtDate;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "GPS_NO")
	public String getGpsNo() {
		return gpsNo;
	}

	public void setGpsNo(String gpsNo) {
		this.gpsNo = gpsNo;
	}

	@Column(name = "CAR_BRAND")
	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	@Column(name = "CAR_STATUS")
	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}
	
}