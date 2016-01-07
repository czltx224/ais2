package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EdiScanTiming entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "EDI_SCAN_TIMING")
public class EdiScanTiming implements java.io.Serializable {

	// Fields

	private Long scanId;
	private String scanName;
	private Date scanLastTime;
	private Date scanLastSuccessTime;

	// Constructors

	/** default constructor */
	public EdiScanTiming() {
	}

	/** minimal constructor */
	public EdiScanTiming(Long scanId) {
		this.scanId = scanId;
	}

	/** full constructor */
	public EdiScanTiming(Long scanId, String scanName, Date scanLastTime,
			Date scanLastSuccessTime) {
		this.scanId = scanId;
		this.scanName = scanName;
		this.scanLastTime = scanLastTime;
		this.scanLastSuccessTime = scanLastSuccessTime;
	}

	// Property accessors
	@Id
	@Column(name = "SCAN_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getScanId() {
		return this.scanId;
	}

	public void setScanId(Long scanId) {
		this.scanId = scanId;
	}

	@Column(name = "SCAN_NAME", length = 50)
	public String getScanName() {
		return this.scanName;
	}

	public void setScanName(String scanName) {
		this.scanName = scanName;
	}

	@Column(name = "SCAN_LAST_TIME")
	public Date getScanLastTime() {
		return this.scanLastTime;
	}

	public void setScanLastTime(Date scanLastTime) {
		this.scanLastTime = scanLastTime;
	}

	@Column(name = "SCAN_LAST_SUCCESS_TIME")
	public Date getScanLastSuccessTime() {
		return this.scanLastSuccessTime;
	}

	public void setScanLastSuccessTime(Date scanLastSuccessTime) {
		this.scanLastSuccessTime = scanLastSuccessTime;
	}

}