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

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprOvermemoDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_OVERMEMO_DETAIL")
public class OprOvermemoDetail implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;
	private OprOvermemo oprOvermemo;
	private Long dno;// 配送单号
	private Long realPiece;// 实到件数
	private Long piece;// 应到件数
	private Long cusId;// 发货代理客商ID
	private String cpName;// 发货代理
	private Double weight;// 重量
	private Long isException;// 是否异常
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String subNo;// 分单号
	private String flightNo;// 航班号
	private String consignee;// 收货人
	private String addr;// 收货人地址
	private Long status;// 状态 0，为未点到，1为已经点到，2 部分点到
	private String distributionMode;// 配送方式
	private String takeMode;// 提货方式
	private String flightMainNo;
	private String goods;
	private String storageArea;//库存区域
	private Long reNum;  //回单份数
	private String ts;
	private Long departId;//部门ID

	// Constructors

	/** default constructor */
	public OprOvermemoDetail() {
	}
	
	public OprOvermemoDetail(Long id) {
		this.id=id;
	}

	/** minimal constructor */
	public OprOvermemoDetail(Long id, Long dno, Long piece, Long cusId,
			String cpName, Double weight, Long isException, String takeMode) {
		this.id = id;
		this.dno = dno;
		this.piece = piece;
		this.cusId = cusId;
		this.cpName = cpName;
		this.weight = weight;
		this.isException = isException;
		this.takeMode = takeMode;
	}

	/** full constructor */
	public OprOvermemoDetail(Long id, OprOvermemo oprOvermemo, Long dno,
			Long realPiece, Long piece, Long cusId, String cpName, Double weight,
			Long isException, String createName, Date createTime,
			String updateName, Date updateTime, String subNo, String flightNo,
			String consignee, String addr, Long status,
			String distributionMode, String takeMode, String goods) {
		this.id = id;
		this.oprOvermemo = oprOvermemo;
		this.dno = dno;
		this.realPiece = realPiece;
		this.piece = piece;
		this.cusId = cusId;
		this.cpName = cpName;
		this.weight = weight;
		this.isException = isException;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.subNo = subNo;
		this.flightNo = flightNo;
		this.consignee = consignee;
		this.addr = addr;
		this.status = status;
		this.distributionMode = distributionMode;
		this.takeMode = takeMode;
		this.goods = goods;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_OVERMEMO_DETAIL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OVERMEMO")
	public OprOvermemo getOprOvermemo() {
		return this.oprOvermemo;
	}

	public void setOprOvermemo(OprOvermemo oprOvermemo) {
		this.oprOvermemo = oprOvermemo;
	}

	@Column(name = "D_NO", precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "REAL_PIECE", precision = 22, scale = 0)
	public Long getRealPiece() {
		return this.realPiece;
	}

	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}

	@Column(name = "PIECE",precision = 22, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "CUS_ID", precision = 22, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "CP_NAME", length = 30)
	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	@Column(name = "WEIGHT", precision = 22, scale = 0)
	public Double getWeight() {

		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "IS_EXCEPTION", precision = 22, scale = 0)
	public Long getIsException() {
		return this.isException;
	}

	public void setIsException(Long isException) {
		this.isException = isException;
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

	@Column(name = "SUB_NO", length = 30)
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

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "DISTRIBUTION_MODE", length = 10)
	public String getDistributionMode() {
		return this.distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	@Column(name = "TAKE_MODE",  length = 10)
	public String getTakeMode() {
		return this.takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	@Column(name = "GOODS", length = 30)
	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}
	@Column(name = "FLIGHT_MAIN_NO")
	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public String getStorageArea() {
		return storageArea;
	}

	public void setStorageArea(String storageArea) {
		this.storageArea = storageArea;
	}
	
	@Column(name = "TS")
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "RENUM",precision = 22, scale = 0)
	public Long getReNum() {
		return reNum;
	}

	public void setReNum(Long reNum) {
		this.reNum = reNum;
	}
	
	@Column(name = "DEPART_ID",precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Override
	public boolean equals(Object obj) {
		OprOvermemoDetail ood=(OprOvermemoDetail)obj;
		if(this.dno.equals(ood.getDno()) && this.flightMainNo.equals(ood.getFlightMainNo())){
			return true;
		}else{
			return false;
		}
	}	
}