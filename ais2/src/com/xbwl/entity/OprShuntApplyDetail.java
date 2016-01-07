package com.xbwl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

/**
 * OprShuntApplyDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_SHUNT_APPLY_DETAIL")
public class OprShuntApplyDetail implements java.io.Serializable {

	// Fields

	private Long id;
	private Double disShuntWeight;//配车重量
	private String disCarNo;//配车车牌号
	private Double disCarTon;//配车吨位
	private Long disShuntPiece;//配车件数
	private String planCarTime;//计划发车时间
	private Long shuntApplyId;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long status;//状态
	private Long routeNumber;//车次号
	private String driverId;
	private String driverName;
	// Constructors

	/** default constructor */
	public OprShuntApplyDetail() {
	}

	/** minimal constructor */
	public OprShuntApplyDetail(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_SHUNT_APPLY_DETAIL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DIS_SHUNT_WEIGHT", precision = 7)
	public Double getDisShuntWeight() {
		return this.disShuntWeight;
	}

	public void setDisShuntWeight(Double disShuntWeight) {
		this.disShuntWeight = disShuntWeight;
	}

	@Column(name = "DIS_CAR_NO", length = 50)
	public String getDisCarNo() {
		return this.disCarNo;
	}

	public void setDisCarNo(String disCarNo) {
		this.disCarNo = disCarNo;
	}

	@Column(name = "DIS_CAR_TON", precision = 7)
	public Double getDisCarTon() {
		return this.disCarTon;
	}

	public void setDisCarTon(Double disCarTon) {
		this.disCarTon = disCarTon;
	}

	@Column(name = "DIS_SHUNT_PIECE", precision = 22, scale = 0)
	public Long getDisShuntPiece() {
		return this.disShuntPiece;
	}

	public void setDisShuntPiece(Long disShuntPiece) {
		this.disShuntPiece = disShuntPiece;
	}

	@Column(name = "PLAN_CAR_TIME", length = 20)
	public String getPlanCarTime() {
		return this.planCarTime;
	}

	public void setPlanCarTime(String planCarTime) {
		this.planCarTime = planCarTime;
	}

	@Column(name = "SHUNT_APPLY_ID", precision = 22, scale = 0)
	public Long getShuntApplyId() {
		return this.shuntApplyId;
	}

	public void setShuntApplyId(Long shuntApplyId) {
		this.shuntApplyId = shuntApplyId;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	@Column(name = "ROUTE_NUMBER")
	public Long getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(Long routeNumber) {
		this.routeNumber = routeNumber;
	}
	@Column(name = "DRIVER_ID")
	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	@Column(name = "DRIVER_NAME")
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
}