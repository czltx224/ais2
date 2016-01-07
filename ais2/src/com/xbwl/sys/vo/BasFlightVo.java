package com.xbwl.sys.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

public class BasFlightVo {

	private Long id;
	private String flightNumber;//航班号
	private Long customerId; //客商ID
	private String svo; 
	private String startCity;
	private String endCity;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String cusName;
	private String standardStarttime;//起飞时间
	private String standardEndtime;//落地时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getSvo() {
		return svo;
	}
	public void setSvo(String svo) {
		this.svo = svo;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	
	
	/**
	 * @return the standardEndtime
	 */
	public String getStandardEndtime() {
		return standardEndtime;
	}
	/**
	 * @param standardEndtime the standardEndtime to set
	 */
	public void setStandardEndtime(String standardEndtime) {
		this.standardEndtime = standardEndtime;
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
	/**
	 * @return the standardStarttime
	 */
	public String getStandardStarttime() {
		return standardStarttime;
	}
	/**
	 * @param standardStarttime the standardStarttime to set
	 */
	public void setStandardStarttime(String standardStarttime) {
		this.standardStarttime = standardStarttime;
	}
	
}
