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
import com.xbwl.common.utils.Rate;

/**
 * BasSpecialTraiRate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_SPECIAL_TRAI_RATE")
@Rate(mapping="专车协议价")
public class BasSpecialTrainRate implements java.io.Serializable,
		AuditableEntity {

	// Fields

	private Long id;
	
	@Rate(mapping="线路ID")
	private Long specialTrainLineId;
	
	@Rate(mapping="面包车")
	private Double van;
	
	@Rate(mapping="金杯车")
	private Double goldCupCar;
	
	@Rate(mapping="2吨车")
	private Double twoTonCar;
	
	@Rate(mapping="3吨车")
	private Double threeTonCar;
	
	@Rate(mapping="5吨车")
	private Double fiveTonCar;
	
	@Rate(mapping="冷藏车")
	private Double chillCar;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	
	@Rate(mapping="客商名称")
	private String cusName;
	
	@Rate(mapping="客商编号")
	private Long cusId;
	
	@Rate(mapping="路型")
	private String roadType;// 路型
	
	@Rate(mapping="状态")
	private Long status;

	private Long departId;
	
	@Rate(mapping="折扣")
	private Double discount;// 折扣
	
	// Constructors

	public BasSpecialTrainRate(Long id, Long specialTrainLineId, Double van,
			Double goldCupCar, Double twoTonCar, Double threeTonCar,
			Double fiveTonCar, Double chillCar, String createName,
			Date createTime, String updateName, Date updateTime, String ts,
			String cusName, Long cusId, String roadType, Long status,
			Long departId, Double discount) {
		super();
		this.id = id;
		this.specialTrainLineId = specialTrainLineId;
		this.van = van;
		this.goldCupCar = goldCupCar;
		this.twoTonCar = twoTonCar;
		this.threeTonCar = threeTonCar;
		this.fiveTonCar = fiveTonCar;
		this.chillCar = chillCar;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.cusName = cusName;
		this.cusId = cusId;
		this.roadType = roadType;
		this.status = status;
		this.departId = departId;
		this.discount = discount;
	}

	/** default constructor */
	public BasSpecialTrainRate() {
	}

	/** minimal constructor */
	public BasSpecialTrainRate(Long id, Long specialTrainLineId,
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

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_SPECIAL_TRAI_RATE ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SPECIAL_TRAIN_LINE_ID", nullable = false, precision = 10, scale = 0)
	public Long getSpecialTrainLineId() {
		return this.specialTrainLineId;
	}

	public void setSpecialTrainLineId(Long specialTrainLineId) {
		this.specialTrainLineId = specialTrainLineId;
	}

	@Column(name = "VAN", precision = 8)
	public Double getVan() {
		return this.van;
	}

	public void setVan(Double van) {
		this.van = van;
	}

	@Column(name = "GOLD_CUP_CAR", precision = 8)
	public Double getGoldCupCar() {
		return this.goldCupCar;
	}

	public void setGoldCupCar(Double goldCupCar) {
		this.goldCupCar = goldCupCar;
	}

	@Column(name = "TWO_TON_CAR", precision = 8)
	public Double getTwoTonCar() {
		return this.twoTonCar;
	}

	public void setTwoTonCar(Double twoTonCar) {
		this.twoTonCar = twoTonCar;
	}

	@Column(name = "THREE_TON_CAR", precision = 8)
	public Double getThreeTonCar() {
		return this.threeTonCar;
	}

	public void setThreeTonCar(Double threeTonCar) {
		this.threeTonCar = threeTonCar;
	}

	@Column(name = "CHILL_CAR", precision = 8)
	public Double getChillCar() {
		return this.chillCar;
	}

	public void setChillCar(Double chillCar) {
		this.chillCar = chillCar;
	}

	@Column(name = "CREATE_NAME", nullable = false, length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CREATE_TIME", nullable = false, length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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

	@Column(name = "CUS_NAME", nullable = false, length = 200)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "CUS_ID", nullable = false, precision = 10, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "ROAD_TYPE", length = 20)
	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	@Column(name = "FIVE_TON_CAR", precision = 8)
	public Double getFiveTonCar() {
		return fiveTonCar;
	}

	public void setFiveTonCar(Double fiveTonCar) {
		this.fiveTonCar = fiveTonCar;
	}

	@Column(name = "STATUS")
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "DEPART_ID")
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DISCOUNT")
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}