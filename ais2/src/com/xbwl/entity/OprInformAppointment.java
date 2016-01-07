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

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprInformAppointment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_INFORM_APPOINTMENT")
public class OprInformAppointment implements java.io.Serializable,
		AuditableEntity {

	// Fields

	private Long id;
	private Long dno;
	private Long piece;
	private Long realpiece;
	private Double bulk;
	private Double weight;
	private String flightNo;
	private Long informNum;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Date lastInformTime;
	private String lastServiceName;
	private String lastInformCus;
	private Long lastInformResult;
	private Set<OprInformAppointmentDetail> oprInformAppointmentDetails = new HashSet<OprInformAppointmentDetail>(
			0);

	// Constructors

	/** default constructor */
	public OprInformAppointment() {
	}

	/** minimal constructor */
	public OprInformAppointment(Long id, Long dno) {
		this.id = id;
		this.dno = dno;
	}

	/** full constructor */
	public OprInformAppointment(Long id, Long dno, Long piece, Long realpiece,
			Double bulk, Double weight, String flightNo, Long informNum,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts, Date lastInformTime,
			String lastServiceName, String lastInformCus,
			Long lastInformResult,
			Set<OprInformAppointmentDetail> oprInformAppointmentDetails) {
		this.id = id;
		this.dno = dno;
		this.piece = piece;
		this.realpiece = realpiece;
		this.bulk = bulk;
		this.weight = weight;
		this.flightNo = flightNo;
		this.informNum = informNum;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.lastInformTime = lastInformTime;
		this.lastServiceName = lastServiceName;
		this.lastInformCus = lastInformCus;
		this.lastInformResult = lastInformResult;
		this.oprInformAppointmentDetails = oprInformAppointmentDetails;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_INFORM")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "PIECE", precision = 22, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "REALPIECE", precision = 22, scale = 0)
	public Long getRealpiece() {
		return this.realpiece;
	}

	public void setRealpiece(Long realpiece) {
		this.realpiece = realpiece;
	}

	@Column(name = "BULK", precision = 7)
	public Double getBulk() {
		return this.bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	@Column(name = "WEIGHT", precision = 7, scale = 1)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "FLIGHT_NO", length = 20)
	public String getFlightNo() {
		return this.flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@Column(name = "INFORM_NUM", precision = 22, scale = 0)
	public Long getInformNum() {
		return this.informNum;
	}

	public void setInformNum(Long informNum) {
		this.informNum = informNum;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "LAST_INFORM_TIME", length = 7)
	public Date getLastInformTime() {
		return this.lastInformTime;
	}

	public void setLastInformTime(Date lastInformTime) {
		this.lastInformTime = lastInformTime;
	}

	@Column(name = "LAST_SERVICE_NAME", length = 50)
	public String getLastServiceName() {
		return this.lastServiceName;
	}

	public void setLastServiceName(String lastServiceName) {
		this.lastServiceName = lastServiceName;
	}

	@Column(name = "LAST_INFORM_CUS", length = 50)
	public String getLastInformCus() {
		return this.lastInformCus;
	}

	public void setLastInformCus(String lastInformCus) {
		this.lastInformCus = lastInformCus;
	}

	@Column(name = "LAST_INFORM_RESULT", precision = 22, scale = 0)
	public Long getLastInformResult() {
		return this.lastInformResult;
	}

	public void setLastInformResult(Long lastInformResult) {
		this.lastInformResult = lastInformResult;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "oprInformAppointment")
	public Set<OprInformAppointmentDetail> getOprInformAppointmentDetails() {
		return this.oprInformAppointmentDetails;
	}

	public void setOprInformAppointmentDetails(
			Set<OprInformAppointmentDetail> oprInformAppointmentDetails) {
		this.oprInformAppointmentDetails = oprInformAppointmentDetails;
	}

}