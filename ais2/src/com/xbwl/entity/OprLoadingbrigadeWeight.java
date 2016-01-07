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
 * OprLoadingbrigadeWeight entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_LOADINGBRIGADE_WEIGHT")
public class OprLoadingbrigadeWeight implements java.io.Serializable,
		AuditableEntity {

	// Fields

	private Long id;
	private Long overmemoNo;
	private Long dno;
	private String goods;
	private Double weight;
	private Double bulk;
	private Long piece;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long loadingbrigadeId;//装卸组ID
	private Long dispatchId;//分拨组 ID

	private Long loadingbrigadeType;// 0.卸货，1.装货，2.提货，3.送货，4.接货
	private Long departId;// 部门编号
	private Long overmemoDetailId;//交接单细表ID

	// Constructors

	/** default constructor */
	public OprLoadingbrigadeWeight() {
	}

	/** minimal constructor */
	public OprLoadingbrigadeWeight(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprLoadingbrigadeWeight(Long id, Long overmemoNo, Long dno,
			String goods, Double weight, Double bulk, Long piece,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts, Long loadingbrigadeId, Long dispatchId) {
		this.id = id;
		this.overmemoNo = overmemoNo;
		this.dno = dno;
		this.goods = goods;
		this.weight = weight;
		this.bulk = bulk;
		this.piece = piece;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.loadingbrigadeId = loadingbrigadeId;
		this.dispatchId = dispatchId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_LOADINGBRIGADE_WEIGHT ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OVERMEMO_NO", precision = 22, scale = 0)
	public Long getOvermemoNo() {
		return this.overmemoNo;
	}

	public void setOvermemoNo(Long overmemoNo) {
		this.overmemoNo = overmemoNo;
	}

	@Column(name = "D_NO", precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "GOODS", length = 50)
	public String getGoods() {
		return this.goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	@Column(name = "WEIGHT", precision = 22, scale = 0)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "BULK", precision = 22, scale = 0)
	public Double getBulk() {
		return this.bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	@Column(name = "PIECE", precision = 22, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
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

	@JSON(format = "yyyy-MM-dd")
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

	@Column(name = "LOADINGBRIGADE_ID", precision = 22, scale = 0)
	public Long getLoadingbrigadeId() {
		return this.loadingbrigadeId;
	}

	public void setLoadingbrigadeId(Long loadingbrigadeId) {
		this.loadingbrigadeId = loadingbrigadeId;
	}

	@Column(name = "DISPATCH_ID")
	public Long getDispatchId() {
		return dispatchId;
	}

	public void setDispatchId(Long dispatchId) {
		this.dispatchId = dispatchId;
	}

	@Column(name = "LOADINGBRIGADE_TYPE")
	public Long getLoadingbrigadeType() {
		return loadingbrigadeType;
	}

	public void setLoadingbrigadeType(Long loadingbrigadeType) {
		this.loadingbrigadeType = loadingbrigadeType;
	}

	@Column(name = "DEPART_ID")
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "OVERMEMO_DETAIL_ID")
	public Long getOvermemoDetailId() {
		return overmemoDetailId;
	}

	public void setOvermemoDetailId(Long overmemoDetailId) {
		this.overmemoDetailId = overmemoDetailId;
	}
}