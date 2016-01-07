package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprPrewiredDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_PREWIRED_DETAIL", schema = "AISUSER")
public class OprPrewiredDetail implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;
	private OprPrewired oprPrewired;
	private Long DNo;
	private Long piece;
	private Double weight;
	private String consignee;
	private String addr;
	private String gowhere;
	private String flightNo;
	private String request;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	
	// Constructors

	/** default constructor */
	public OprPrewiredDetail() {
	}

	/** minimal constructor */
	public OprPrewiredDetail(Long id, OprPrewired oprPrewired, Long DNo,
			Long piece, Double weight, String consignee, String addr,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts) {
		this.id = id;
		this.oprPrewired = oprPrewired;
		this.DNo = DNo;
		this.piece = piece;
		this.weight = weight;
		this.consignee = consignee;
		this.addr = addr;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	/** full constructor */
	public OprPrewiredDetail(Long id, OprPrewired oprPrewired, Long DNo,
			Long piece, Double weight, String consignee, String addr,
			String gowhere, String flightNo, String request, String createName,
			Date createTime, String updateName, Date updateTime, String ts) {
		this.id = id;
		this.oprPrewired = oprPrewired;
		this.DNo = DNo;
		this.piece = piece;
		this.weight = weight;
		this.consignee = consignee;
		this.addr = addr;
		this.gowhere = gowhere;
		this.flightNo = flightNo;
		this.request = request;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id 
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_PREWIRED_DETAIL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPR_PREWIRED_ID", nullable = false)
	public OprPrewired getOprPrewired() {
		return this.oprPrewired;
	}

	public void setOprPrewired(OprPrewired oprPrewired) {
		this.oprPrewired = oprPrewired;
	}

	@Column(name = "D_NO", nullable = false, precision = 10, scale = 0)
	public Long getDNo() {
		return this.DNo;
	}

	public void setDNo(Long DNo) {
		this.DNo = DNo;
	}

	@Column(name = "PIECE", nullable = false, precision = 7, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "WEIGHT", nullable = false, precision = 7, scale = 1)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "CONSIGNEE", nullable = false, length = 100)
	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column(name = "ADDR", length = 500)
	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "GOWHERE", length = 200)
	public String getGowhere() {
		return this.gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	@Column(name = "FLIGHT_NO", length = 20)
	public String getFlightNo() {
		return this.flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@Column(name = "REQUEST", length = 200)
	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@Column(name = "CREATE_NAME",  length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "CREATE_TIME",  length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME",length = 20)
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

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
}