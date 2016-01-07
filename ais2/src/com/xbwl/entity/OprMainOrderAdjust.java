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
 * OprMainOrderAdjust entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_MAIN_ORDER_ADJUST")
public class OprMainOrderAdjust implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;//序号
	private Long dno;//配送单号
	private String subNo;//分单号
	private String oldFlightMainNo;//旧主单号
	private String newFlightMainNo;//新主单号
	private Double oldWeight;//旧重量
	private Double newWeight;//新重量
	private Double oldConsigneeFee;//旧提送费
	private Double newConsigneeFee;//新提送费
	private Double adjustMoney;//调整金额差
	private Double consigneeRate;//提送费率
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private Long departId;
	private String ts;
	private String departName;

	// Constructors

	/** default constructor */
	public OprMainOrderAdjust() {
	}

	/** minimal constructor */
	public OprMainOrderAdjust(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprMainOrderAdjust(Long id, Long dno, String subNo,
			String oldFlightMainNo, String newFlightMainNo, Double oldWeight,
			Double newWeight, Double oldConsigneeFee, Double newConsigneeFee,
			Double adjustMoney, Double consigneeRate, String createName,
			Date createTime, String updateName, Date updateTime, Long departId,
			String ts,String departName) {
		this.id = id;
		this.dno = dno;
		this.subNo = subNo;
		this.oldFlightMainNo = oldFlightMainNo;
		this.newFlightMainNo = newFlightMainNo;
		this.oldWeight = oldWeight;
		this.newWeight = newWeight;
		this.oldConsigneeFee = oldConsigneeFee;
		this.newConsigneeFee = newConsigneeFee;
		this.adjustMoney = adjustMoney;
		this.consigneeRate = consigneeRate;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.departId = departId;
		this.ts = ts;
		this.departName=departName;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_MAIN_ORDER_ADJUST ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", precision = 10, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "SUB_NO", length = 50)
	public String getSubNo() {
		return this.subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	@Column(name = "OLD_FLIGHT_MAIN_NO", length = 50)
	public String getOldFlightMainNo() {
		return this.oldFlightMainNo;
	}

	public void setOldFlightMainNo(String oldFlightMainNo) {
		this.oldFlightMainNo = oldFlightMainNo;
	}

	@Column(name = "NEW_FLIGHT_MAIN_NO", length = 50)
	public String getNewFlightMainNo() {
		return this.newFlightMainNo;
	}

	public void setNewFlightMainNo(String newFlightMainNo) {
		this.newFlightMainNo = newFlightMainNo;
	}

	@Column(name = "OLD_WEIGHT", precision = 7)
	public Double getOldWeight() {
		return this.oldWeight;
	}

	public void setOldWeight(Double oldWeight) {
		this.oldWeight = oldWeight;
	}

	@Column(name = "NEW_WEIGHT", precision = 7)
	public Double getNewWeight() {
		return this.newWeight;
	}

	public void setNewWeight(Double newWeight) {
		this.newWeight = newWeight;
	}

	@Column(name = "OLD_CONSIGNEE_FEE", precision = 8)
	public Double getOldConsigneeFee() {
		return this.oldConsigneeFee;
	}

	public void setOldConsigneeFee(Double oldConsigneeFee) {
		this.oldConsigneeFee = oldConsigneeFee;
	}

	@Column(name = "NEW_CONSIGNEE_FEE", precision = 8)
	public Double getNewConsigneeFee() {
		return this.newConsigneeFee;
	}

	public void setNewConsigneeFee(Double newConsigneeFee) {
		this.newConsigneeFee = newConsigneeFee;
	}

	@Column(name = "ADJUST_MONEY", precision = 8)
	public Double getAdjustMoney() {
		return this.adjustMoney;
	}

	public void setAdjustMoney(Double adjustMoney) {
		this.adjustMoney = adjustMoney;
	}

	@Column(name = "CONSIGNEE_RATE", precision = 8)
	public Double getConsigneeRate() {
		return this.consigneeRate;
	}

	public void setConsigneeRate(Double consigneeRate) {
		this.consigneeRate = consigneeRate;
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

	@Column(name = "DEPART_ID", precision = 10, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	@Column(name = "DEPART_NAME", length = 20)
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
}