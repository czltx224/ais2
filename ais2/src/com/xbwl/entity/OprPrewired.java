package com.xbwl.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprPrewired entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_PREWIRED", schema = "AISUSER")
public class OprPrewired implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;
	private String autostowMode;
	private String toWhere;
	private Double weight;
	private Long piece;
	private Long votes;
	private Date createTime;
	private String createName;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long departId;
	private String departName;
	private Set<OprPrewiredDetail> oprPrewiredDetails = new HashSet<OprPrewiredDetail>(0);
	private Long status;
	private String orderField;
	private Long printNum;

	// Constructors
	@Column(name = "STATUS", nullable = false, precision = 10, scale = 0)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	/** default constructor */
	public OprPrewired() {
		this.printNum=0l;
	}

	/** minimal constructor */
	public OprPrewired(Long id, String autostowMode, String toWhere,
			Double weight, Long piece, Long votes, Date createTime,
			String createName, String updateName, Date updateTime, String ts,
			Long departId, String departName) {
		this.id = id;
		this.autostowMode = autostowMode;
		this.toWhere = toWhere;
		this.weight = weight;
		this.piece = piece;
		this.votes = votes;
		this.createTime = createTime;
		this.createName = createName;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.departId = departId;
		this.departName = departName;
	}

	/** full constructor */
	public OprPrewired(Long id, String autostowMode, String toWhere,
			Double weight, Long piece, Long votes, Date createTime,
			String createName, String updateName, Date updateTime, String ts,
			Long departId, String departName,
			Set<OprPrewiredDetail> oprPrewiredDetails) {
		this.id = id;
		this.autostowMode = autostowMode;
		this.toWhere = toWhere;
		this.weight = weight;
		this.piece = piece;
		this.votes = votes;
		this.createTime = createTime;
		this.createName = createName;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.departId = departId;
		this.departName = departName;
		this.oprPrewiredDetails = oprPrewiredDetails;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_PREWIRED")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "AUTOSTOW_MODE", nullable = false, length = 20)
	public String getAutostowMode() {
		return this.autostowMode;
	}

	public void setAutostowMode(String autostowMode) {
		this.autostowMode = autostowMode;
	}

	@Column(name = "TO_WHERE", nullable = false, length = 200)
	public String getToWhere() {
		return this.toWhere;
	}

	public void setToWhere(String toWhere) {
		this.toWhere = toWhere;
	}

	@Column(name = "WEIGHT", nullable = false, precision = 7, scale = 1)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "PIECE", nullable = false, precision = 8, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "VOTES", nullable = false, precision = 8, scale = 0)
	public Long getVotes() {
		return this.votes;
	}

	public void setVotes(Long votes) {
		this.votes = votes;
	}

	@Column(name = "CREATE_TIME",  length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME",  length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "UPDATE_NAME",  length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "UPDATE_TIME",  length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS",  length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "DEPART_ID", nullable = false, precision = 10, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", nullable = false, length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "oprPrewired")
	public Set<OprPrewiredDetail> getOprPrewiredDetails() {
		return this.oprPrewiredDetails;
	}

	public void setOprPrewiredDetails(Set<OprPrewiredDetail> oprPrewiredDetails) {
		this.oprPrewiredDetails = oprPrewiredDetails;
	}
	@Column(name = "PRINT_NUM")
	public Long getPrintNum() {
		return printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}

	@Column(name = "ORDER_FIELD", length = 200)
	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
}