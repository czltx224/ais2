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
 * OprFaxMain entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_FAX_MAIN")
public class OprFaxMain implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long totalPiece;
	private Double totalWeight;
	private Double realWeight;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long departId;
	private String departName;
	private String flightMainNo;
	private Long matchStatus=0l;   //Æ¥Åä×´Ì¬
	private Long costAuditStatus=0l;  //³É±¾ÉóºË×´Ì¬  0£ºÎ´ÉóºË 1£ºÒÑÉóºË
	
	// Constructors

	/** default constructor */
	public OprFaxMain() {
	}

	/** minimal constructor */
	public OprFaxMain(Long id, Long totalPiece, Double totalWeight) {
		this.id = id;
		this.totalPiece = totalPiece;
		this.totalWeight = totalWeight;
	}
	
	
	public OprFaxMain(Long totalPiece, Double totalWeight) {
		this.totalPiece = totalPiece;
		this.totalWeight = totalWeight;
	}

	/** full constructor */
	public OprFaxMain(Long id, Long totalPiece, Double totalWeight,
			Double realWeight, String createName, Date createTime,
			String updateName, Date updateTime, String ts, Long departId,
			String departName, String flightMainNo) {
		this.id = id;
		this.totalPiece = totalPiece;
		this.totalWeight = totalWeight;
		this.realWeight = realWeight;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.departId = departId;
		this.departName = departName;
		this.flightMainNo = flightMainNo;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_OPR_FAX_MAIN")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TOTAL_PIECE", nullable = false, precision = 22, scale = 0)
	public Long getTotalPiece() {
		return this.totalPiece;
	}

	public void setTotalPiece(Long totalPiece) {
		this.totalPiece = totalPiece;
	}

	@Column(name = "TOTAL_WEIGHT", nullable = false, precision = 22, scale = 0)
	public Double getTotalWeight() {
		return this.totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	@Column(name = "REAL_WEIGHT", precision = 22, scale = 0)
	public Double getRealWeight() {
		return this.realWeight;
	}

	public void setRealWeight(Double realWeight) {
		this.realWeight = realWeight;
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

	@Column(name = "DEPART_ID", precision = 10, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 200)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "FLIGHT_MAIN_NO", length = 200)
	public String getFlightMainNo() {
		return this.flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	@Column(name = "MATCH_STATUS", precision = 10, scale = 0)
	public Long getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(Long matchStatus) {
		this.matchStatus = matchStatus;
	}

	@Column(name = "COST_AUDIT_STATUS", precision = 10, scale = 0)
	public Long getCostAuditStatus() {
		return costAuditStatus;
	}

	public void setCostAuditStatus(Long costAuditStatus) {
		this.costAuditStatus = costAuditStatus;
	}

}