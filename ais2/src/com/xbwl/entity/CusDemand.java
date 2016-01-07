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
 * CusDemand entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_DEMAND")
public class CusDemand implements java.io.Serializable ,AuditableEntity{

	// Fields

	private Long id;
	private String demandType;
	private String demandContext;
	private String demandMan;
	private Long isAccept;
	private String filePath;
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
	public CusDemand() {
	}
	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_DEMAND")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DEMAND_TYPE", nullable = false, length = 20)
	public String getDemandType() {
		return this.demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	@Column(name = "DEMAND_CONTEXT", nullable = false, length = 2000)
	public String getDemandContext() {
		return this.demandContext;
	}

	public void setDemandContext(String demandContext) {
		this.demandContext = demandContext;
	}

	@Column(name = "DEMAND_MAN", nullable = false, length = 50)
	public String getDemandMan() {
		return this.demandMan;
	}

	public void setDemandMan(String demandMan) {
		this.demandMan = demandMan;
	}

	@Column(name = "IS_ACCEPT", nullable = false, precision = 22, scale = 0)
	public Long getIsAccept() {
		return this.isAccept;
	}

	public void setIsAccept(Long isAccept) {
		this.isAccept = isAccept;
	}

	@Column(name = "FILE_PATH", length = 50)
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "CUS_RECORD_ID", nullable = false, precision = 22, scale = 0)
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