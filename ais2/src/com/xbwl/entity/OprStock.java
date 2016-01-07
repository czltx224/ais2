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
 * OprStock entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_STOCK")
public class OprStock implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;//序号
	private Long dno;//配送单号
	private Long piece;//库存件数
	private Long departId;//库存部门
	private String storageArea;//库存区域
	private String createName;//创建人
	private Date createTime;//创建时间
	private String updateName;//修改人
	private Date updateTime;//修改时间
	private String ts;//时间戳
	private String flightMainNo;//主单号
	private String subNo;//分单号
	private String flightNo;//航班号
	private String consignee;//收货人
	private String addr;//收货人地址
	private Double weight;
	
	//private Long status = 0l;//是否异常，0：正常，1：异常 默认为0
	
	// Constructors

	@Column(name = "D_NO", nullable = false, precision = 22, scale = 0)
	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "WEIGHT", precision = 22, scale = 0)
	public Double  getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/** default constructor */
	public OprStock() {
		this.piece=0l;
		this.weight=0d;
	}

	/** minimal constructor */
	public OprStock(Long id, Long dno, Long piece, Long departId) {
		this.id = id;
		this.dno = dno;
		this.piece = piece;
		this.departId = departId;
	}

	public OprStock( Long dno, String flightMainNo,
			String subNo, String consignee, String addr,
			Double weight,Long piece) {
		super();
		this.dno = dno;
		this.flightMainNo = flightMainNo;
		this.subNo = subNo;;
		this.consignee = consignee;
		this.addr = addr;
		this.weight = weight;
		this.piece=piece;
	}
	
	public OprStock(Long id, Long dno, Long piece, Long departId,
			String storageArea, String createName, Date createTime,
			String updateName, Date updateTime, String ts, String flightMainNo,
			String subNo, String flightNo, String consignee, String addr,
			Double weight) {
		super();
		this.id = id;
		this.dno = dno;
		this.piece = piece;
		this.departId = departId;
		this.storageArea = storageArea;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.flightMainNo = flightMainNo;
		this.subNo = subNo;
		this.flightNo = flightNo;
		this.consignee = consignee;
		this.addr = addr;
		this.weight = weight;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_STOCK")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PIECE",precision = 22, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
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