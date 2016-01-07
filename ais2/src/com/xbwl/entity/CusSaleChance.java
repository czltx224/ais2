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
 * CusSaleChance entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_SALE_CHANCE")
public class CusSaleChance implements java.io.Serializable {

	// Fields

	private Long id;
	private Long targetNum;//指标值
	private Date startTime;//指标开始时间
	private Date endTime;//指标结束时间
	private Long completeNum;//实际完成值
	private Long completeRate;//指标完成率
	private Double timeUser;//指标完成时间使用率
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long cusRecordId;
	private Long status;
	
	private Long departId;

	// Constructors

	/** default constructor */
	public CusSaleChance() {
	}

	/** minimal constructor */
	public CusSaleChance(Long id, Long targetNum, Date startTime, Date endTime) {
		this.id = id;
		this.targetNum = targetNum;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_SALE_CHANCE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TARGET_NUM", nullable = false, precision = 22, scale = 0)
	public Long getTargetNum() {
		return this.targetNum;
	}

	public void setTargetNum(Long targetNum) {
		this.targetNum = targetNum;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "START_TIME", nullable = false, length = 7)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "END_TIME", nullable = false, length = 7)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "COMPLETE_NUM", precision = 22, scale = 0)
	public Long getCompleteNum() {
		return this.completeNum;
	}

	public void setCompleteNum(Long completeNum) {
		this.completeNum = completeNum;
	}

	@Column(name = "COMPLETE_RATE", precision = 22, scale = 0)
	public Long getCompleteRate() {
		return this.completeRate;
	}

	public void setCompleteRate(Long completeRate) {
		this.completeRate = completeRate;
	}

	@Column(name = "TIME_USER", precision = 22, scale = 0)
	public Double getTimeUser() {
		return this.timeUser;
	}

	public void setTimeUser(Double timeUser) {
		this.timeUser = timeUser;
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

	@Column(name = "TS", length = 50)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "CUS_RECORD_ID", precision = 22, scale = 0)
	public Long getCusRecordId() {
		return this.cusRecordId;
	}

	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * @return the departId
	 */
	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}
	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
}