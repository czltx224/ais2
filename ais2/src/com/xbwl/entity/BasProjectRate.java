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
 * BasProjectRate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_PROJECT_RATE")
@Rate(mapping="项目客户协议价")
public class BasProjectRate implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	
	@Rate(mapping="计价单位")
	private String conditionUnit;
	
	@Rate(mapping="最小数值")
	private Double minValue;
	
	@Rate(mapping="最大数值")
	private Double maxValue;
	
	@Rate(mapping="计算方式")
	private String countWay;
	
	@Rate(mapping="目的站")
	private String endAdd;
	
	@Rate(mapping="费率")
	private Double rate;
	
	@Rate(mapping="最低一票")
	private Double lowFee;
	
	@Rate(mapping="发货代理ID")
	private Long cusId;
	
	@Rate(mapping="发货代理")
	private String cusName;
	
	private Double addFee;//追加费用
	
	private String departName;
	private Long departId;
	private Date createTime;
	private String createName;
	private String updateName;
	private Date updateTime;
	private String ts;

	// Constructors

	/** default constructor */
	public BasProjectRate() {
	}

	/** minimal constructor */
	public BasProjectRate(Long id, String countWay, Double rate, Long cusId,
			String cusName, String departName, Long departId, Date createTime,
			String createName, String updateName, Date updateTime, String ts) {
		this.id = id;
		this.countWay = countWay;
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
	}

	/** full constructor */
	public BasProjectRate(Long id, String conditionUnit, Double minValue,
			Double maxValue, String countWay, String endAdd, Double rate,
			Double lowFee, Long cusId, String cusName, String departName,
			Long departId, Date createTime, String createName,
			String updateName, Date updateTime, String ts) {
		this.id = id;
		this.conditionUnit = conditionUnit;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.countWay = countWay;
		this.endAdd = endAdd;
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
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_BAS_PROJECT_RATE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
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

	@Column(name = "MAX_VALUE", precision = 8)
	public Double getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	@Column(name = "COUNT_WAY", nullable = false, length = 50)
	public String getCountWay() {
		return this.countWay;
	}

	public void setCountWay(String countWay) {
		this.countWay = countWay;
	}

	@Column(name = "END_ADD", length = 50)
	public String getEndAdd() {
		return this.endAdd;
	}

	public void setEndAdd(String endAdd) {
		this.endAdd = endAdd;
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

	@JSON(format = "yyyy-MM-dd")
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

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UPDATE_NAME", nullable = false, length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd")
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
	@Column(name = "ADD_FEE")
	public Double getAddFee() {
		return addFee;
	}

	public void setAddFee(Double addFee) {
		this.addFee = addFee;
	}
	
}