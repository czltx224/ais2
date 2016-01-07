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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprStocktakeDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_STOCKTAKE_DETAIL", schema = "AISUSER")
public class OprStocktakeDetail implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;
	private OprStocktake oprStocktake;
	private Long dNo;  //配送单号	
	private Long piece;    //配送单号件数
	private Long realPiece;    //盘点件数
	private Long departId;   //业务部门ID
	private String storageArea;  //库存区域
	private String createName;   
	private Date createTime;    //打印清仓单时间
	private String updateName;
	private Date updateTime;
	private String ts;
	private String flightMainNo;  //主单号
	private String subNo;             //子单号
	private String flightNo;  //航班号
	private String consignee; 
	private String addr;
	private Long status;    //状态  0：未盘点，1：已盘点
	private Double weight;
	
	// Constructors

	/** default constructor */
	public OprStocktakeDetail() {
	}

	@Column(name = "D_NO", nullable = false, precision = 22, scale = 0)
	public Long getDNo() {
		return dNo;
	}

	public void setDNo(Long no) {
		dNo = no;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "WEIGHT", precision = 22, scale = 0)
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/** minimal constructor */
	public OprStocktakeDetail(Long id, Long dno, Long piece, Long departId) {
		this.id = id;
		this.dNo = dno;
		this.piece = piece;
		this.departId = departId;
	}

 

	// Property accessors  
	@Id   
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_STOCKTAKE_DETAIL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCKTAKE_ID")
	public OprStocktake getOprStocktake() {
		return this.oprStocktake;
	}

	public void setOprStocktake(OprStocktake oprStocktake) {
		this.oprStocktake = oprStocktake;
	}

	@Column(name = "PIECE", nullable = false, precision = 22, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "REAL_PIECE", precision = 22, scale = 0)
	public Long getRealPiece() {
		return this.realPiece;
	}

	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}

	@Column(name = "DEPART_ID", nullable = false, precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "STORAGE_AREA", length = 20)
	public String getStorageArea() {
		return this.storageArea;
	}

	public void setStorageArea(String storageArea) {
		this.storageArea = storageArea;
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

	@Column(name = "FLIGHT_MAIN_NO", length = 20)
	public String getFlightMainNo() {
		return this.flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	@Column(name = "SUB_NO", length = 20)
	public String getSubNo() {
		return this.subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	@Column(name = "FLIGHT_NO", length = 20)
	public String getFlightNo() {
		return this.flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@Column(name = "CONSIGNEE", length = 100)
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

}