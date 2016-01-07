package com.xbwl.entity;

// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.xblink.annotation.XBlinkAlias;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * BasFlight entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_FLIGHT")
@XBlinkAlias("basFlight")
public class BasFlight implements java.io.Serializable,AuditableEntity {

	// Fields
	private Long id;
	private String flightNumber;//航班号
	private Long customerId; //客商ID
	private String svo; //航班三字代码
	private String startCity;//起飞城市
	private String endCity;//落地城市
	private String standardStarttime;//起飞时间
	private String standardEndtime;//落地时间
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;


	@Id
	@Column(name = "id", unique = true, nullable = false, length = 10)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_CUSTOMER")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FLIGHT_NUMBER", unique = true, nullable = false, length = 20)
	public String getFlightNumber() {
		return this.flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	@Column(name = "SVO", length = 5)
	public String getSvo() {
		return this.svo;
	}

	public void setSvo(String svo) {
		this.svo = svo;
	}

	@Column(name = "START_CITY", length = 10)
	public String getStartCity() {
		return this.startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	@Column(name = "END_CITY", nullable = false, length = 10)
	public String getEndCity() {
		return this.endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	@Column(name = "STANDARD_ENDTIME", length = 4)
	public String getStandardEndtime() {
		return this.standardEndtime;
	}

	public void setStandardEndtime(String standardEndtime) {
		this.standardEndtime = standardEndtime;
	}

	@Column(name = "STANDARD_STARTTIME", length = 4)
	public String getStandardStarttime() {
		return standardStarttime;
	}

	public void setStandardStarttime(String standardStarttime) {
		this.standardStarttime = standardStarttime;
	}

	@Column(name = "CREATE_NAME", length = 10)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 10)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
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

	@Column(name = "cus_id")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}