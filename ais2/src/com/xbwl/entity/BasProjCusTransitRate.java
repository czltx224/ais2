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
 * BasProjCusTransitRate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_PROJ_CUS_TRANSIT_RATE")
public class BasProjCusTransitRate implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String conditionUnit;//条件单位
	private Double minValue;//最小数值
	private Double maxValue;//最大数值
	private String valuationType;//计价方式
	private Double rate;//费率
	private Double lowFee;//最低一票
	private Long cusId;//客商ID
	private String cusName;//客商名称
	private String departName;//部门名称
	private Long departId;//部门ID
	private Date createTime;//创建时间
	private String createName;//创建人
	private String updateName;//修改人
	private Date updateTime;//修改时间
	private String ts;//时间戳
	private String cpName;//中转客商名称
	private String areaType;//地区类型
	private String takeMode;//提货方式
	private Long cpId;//中转客商ID
	private String trafficMode;//运输方式
	private String speTown;//特殊地区

	// Constructors

	/** default constructor */
	public BasProjCusTransitRate() {
	}

	/** minimal constructor */
	public BasProjCusTransitRate(Long id, String valuationType, Double rate,
			Long cusId, String cusName, String departName, Long departId,
			Date createTime, String createName, String updateName,
			Date updateTime, String ts, String cpName, String areaType) {
		this.id = id;
		this.valuationType = valuationType;
		this.rate = rate;
		this.cusId = cusId;
		this.cusName = cusName;
		this.departName = departName;
		this.departId = departId;
		this.createTime = createTime;
		this.createName = createName;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.cpName = cpName;
		this.areaType = areaType;
	}

	/** full constructor */
	public BasProjCusTransitRate(Long id, String conditionUnit,
			Double minValue, Double maxValue, String valuationType,
			Double rate, Double lowFee, Long cusId, String cusName,
			String departName, Long departId, Date createTime,
			String createName, String updateName, Date updateTime, String ts,
			String cpName, String areaType, String takeMode, Long cpId,
			String trafficMode) {
		this.id = id;
		this.conditionUnit = conditionUnit;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.valuationType = valuationType;
		this.rate = rate;
		this.lowFee = lowFee;
		this.cusId = cusId;
		this.cusName = cusName;
		this.departName = departName;
		this.departId = departId;
		this.createTime = createTime;
		this.createName = createName;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.cpName = cpName;
		this.areaType = areaType;
		this.takeMode = takeMode;
		this.cpId = cpId;
		this.trafficMode = trafficMode;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_BAS_PROJ_CUS_TRANSIT_RATE ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CONDITION_UNIT", length = 20)
	public String getConditionUnit() {
		return this.conditionUnit;
	}

	public void setConditionUnit(String conditionUnit) {
		this.conditionUnit = conditionUnit;
	}

	@Column(name = "MIN_VALUE", precision = 8)
	public Double getMinValue() {
		return this.minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	@Column(name = "MAX_VALUE", precision = 10)
	public Double getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	@Column(name = "VALUATION_TYPE", nullable = false, length = 50)
	public String getValuationType() {
		return this.valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	@Column(name = "RATE", nullable = false, precision = 8)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "LOW_FEE", precision = 8)
	public Double getLowFee() {
		return this.lowFee;
	}

	public void setLowFee(Double lowFee) {
		this.lowFee = lowFee;
	}

	@Column(name = "CUS_ID", nullable = false, precision = 10, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "CUS_NAME", nullable = false, length = 200)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "DEPART_NAME", nullable = false, length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "DEPART_ID", nullable = false, precision = 10, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", nullable = false, length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", nullable = false, length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "UPDATE_NAME", nullable = false, length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", nullable = false, length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", nullable = false, length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "CP_NAME", length = 50)
	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	@Column(name = "AREA_TYPE", nullable = false, length = 20)
	public String getAreaType() {
		return this.areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	@Column(name = "TAKE_MODE", length = 20)
	public String getTakeMode() {
		return this.takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	@Column(name = "CP_ID", precision = 10, scale = 0)
	public Long getCpId() {
		return this.cpId;
	}

	public void setCpId(Long cpId) {
		this.cpId = cpId;
	}

	@Column(name = "TRAFFIC_MODE", length = 20)
	public String getTrafficMode() {
		return this.trafficMode;
	}

	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}
	@Column(name = "SPE_TOWN")
	public String getSpeTown() {
		return speTown;
	}

	public void setSpeTown(String speTown) {
		this.speTown = speTown;
	}
	
}