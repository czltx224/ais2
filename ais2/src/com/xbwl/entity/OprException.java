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
 * OprException entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_EXCEPTION")
public class OprException implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long dno;
	private String cusName;
	private String flightMainNo;
	private String flightNo;
	private String subNo;
	private String consignee;
	private String consigneeAdd;
	private Long piece;
	private Double weight;
	private String exceptionType1;
	private String exceptionType2;
	private String exceptionName;
	private Date exceptionTime;
	private String exceptionRepar;
	private Date exceptionReparTime;
	private Double exceptionReparCost;
	private String dutyDepartId;
	private String dutyDepartName;
	private Long exceptionPiece;
	private String exceptionAdd;
	private String exceptionDescribe;
	private String suggestion;
	private Long status;
	private String finalResult;
	private String dealName;
	private Date dealTime;
	private String dealReasult;
	private Long isCp;
	private Long isCus;
	private Long isRepar;
	private Long isWeb;
	private String exptionAdd;
	private String finalDuty;
	private Long finalPiece;
	private String qm;
	private Date qmTime;
	private String qmSuggestion;
	private Long submitQualified;
	private Long dealQualified;
	private Long reparQualified;
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	private Long departId;
	private String departName;
	private String createDepartId;
	private String createDepartName;
	private String exceptionNode;
	
	private Double exceptionMoney;
	
	// Constructors


	@Column(name = "EXCEPTION_MONEY", precision = 10)
	public Double getExceptionMoney() {
		return exceptionMoney;
	}

	public void setExceptionMoney(Double exceptionMoney) {
		this.exceptionMoney = exceptionMoney;
	}

	/** default constructor */
	public OprException() {
	}

	/** minimal constructor */
	public OprException(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprException(Long id, Long DNo, String cusName, String flightMainNo,
			String flightNo, String subNo, String consignee,
			String consigneeAdd, Long piece, Double weight,
			String exceptionType1, String exceptionType2, String exceptionName,
			Date exceptionTime, String exceptionRepar, Date exceptionReparTime,
			Double exceptionReparCost, String dutyDepartId,
			String dutyDepartName, Long exceptionPiece, String exceptionAdd,
			String exceptionDescribe, String suggestion, Long status,
			String finalResult, String dealName, Date dealTime,
			String dealReasult, Long isCp, Long isCus, Long isRepar,
			Long isWeb, String exptionAdd, String finalDuty, Long finalPiece,
			String qm, Date qmTime, String qmSuggestion, Long submitQualified,
			Long dealQualified, Long reparQualified, Date createTime,
			String createName, Date updateTime, String updateName, String ts,
			Long departId, String departName) {
		this.id = id;
		this.dno = DNo;
		this.cusName = cusName;
		this.flightMainNo = flightMainNo;
		this.flightNo = flightNo;
		this.subNo = subNo;
		this.consignee = consignee;
		this.consigneeAdd = consigneeAdd;
		this.piece = piece;
		this.weight = weight;
		this.exceptionType1 = exceptionType1;
		this.exceptionType2 = exceptionType2;
		this.exceptionName = exceptionName;
		this.exceptionTime = exceptionTime;
		this.exceptionRepar = exceptionRepar;
		this.exceptionReparTime = exceptionReparTime;
		this.exceptionReparCost = exceptionReparCost;
		this.dutyDepartId = dutyDepartId;
		this.dutyDepartName = dutyDepartName;
		this.exceptionPiece = exceptionPiece;
		this.exceptionAdd = exceptionAdd;
		this.exceptionDescribe = exceptionDescribe;
		this.suggestion = suggestion;
		this.status = status;
		this.finalResult = finalResult;
		this.dealName = dealName;
		this.dealTime = dealTime;
		this.dealReasult = dealReasult;
		this.isCp = isCp;
		this.isCus = isCus;
		this.isRepar = isRepar;
		this.isWeb = isWeb;
		this.exptionAdd = exptionAdd;
		this.finalDuty = finalDuty;
		this.finalPiece = finalPiece;
		this.qm = qm;
		this.qmTime = qmTime;
		this.qmSuggestion = qmSuggestion;
		this.submitQualified = submitQualified;
		this.dealQualified = dealQualified;
		this.reparQualified = reparQualified;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.ts = ts;
		this.departId = departId;
		this.departName = departName;
	}

	// Property accessors
	@Id   
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_EXCEPTION",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
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

	public void setDno(Long DNo) {
		this.dno = DNo;
	}

	@Column(name = "CUS_NAME", length = 200)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "FLIGHT_MAIN_NO", length = 200)
	public String getFlightMainNo() {
		return this.flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	@Column(name = "FLIGHT_NO", length = 50)
	public String getFlightNo() {
		return this.flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@Column(name = "SUB_NO", length = 200)
	public String getSubNo() {
		return this.subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	@Column(name = "CONSIGNEE", length = 500)
	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column(name = "CONSIGNEE_ADD", length = 500)
	public String getConsigneeAdd() {
		return this.consigneeAdd;
	}

	public void setConsigneeAdd(String consigneeAdd) {
		this.consigneeAdd = consigneeAdd;
	}

	@Column(name = "PIECE", precision = 8, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "WEIGHT", precision = 10)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "EXCEPTION_TYPE1", length = 50)
	public String getExceptionType1() {
		return this.exceptionType1;
	}

	public void setExceptionType1(String exceptionType1) {
		this.exceptionType1 = exceptionType1;
	}

	@Column(name = "EXCEPTION_TYPE2", length = 50)
	public String getExceptionType2() {
		return this.exceptionType2;
	}

	public void setExceptionType2(String exceptionType2) {
		this.exceptionType2 = exceptionType2;
	}

	@Column(name = "EXCEPTION_NAME", length = 50)
	public String getExceptionName() {
		return this.exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "EXCEPTION_TIME", length = 7)
	public Date getExceptionTime() {
		return this.exceptionTime;
	}

	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	@Column(name = "EXCEPTION_REPAR", length = 50)
	public String getExceptionRepar() {
		return this.exceptionRepar;
	}

	public void setExceptionRepar(String exceptionRepar) {
		this.exceptionRepar = exceptionRepar;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "EXCEPTION_REPAR_TIME", length = 7)
	public Date getExceptionReparTime() {
		return this.exceptionReparTime;
	}

	public void setExceptionReparTime(Date exceptionReparTime) {
		this.exceptionReparTime = exceptionReparTime;
	}

	@Column(name = "EXCEPTION_REPAR_COST", precision = 10)
	public Double getExceptionReparCost() {
		return this.exceptionReparCost;
	}

	public void setExceptionReparCost(Double exceptionReparCost) {
		this.exceptionReparCost = exceptionReparCost;
	}

	@Column(name = "DUTY_DEPART_ID", length = 50)
	public String getDutyDepartId() {
		return this.dutyDepartId;
	}

	public void setDutyDepartId(String dutyDepartId) {
		this.dutyDepartId = dutyDepartId;
	}

	@Column(name = "DUTY_DEPART_NAME", length = 50)
	public String getDutyDepartName() {
		return this.dutyDepartName;
	}

	public void setDutyDepartName(String dutyDepartName) {
		this.dutyDepartName = dutyDepartName;
	}

	@Column(name = "EXCEPTION_PIECE", precision = 8, scale = 0)
	public Long getExceptionPiece() {
		return this.exceptionPiece;
	}

	public void setExceptionPiece(Long exceptionPiece) {
		this.exceptionPiece = exceptionPiece;
	}

	@Column(name = "EXCEPTION_ADD", length = 500)
	public String getExceptionAdd() {
		return this.exceptionAdd;
	}

	public void setExceptionAdd(String exceptionAdd) {
		this.exceptionAdd = exceptionAdd;
	}

	@Column(name = "EXCEPTION_DESCRIBE", length = 2000)
	public String getExceptionDescribe() {
		return this.exceptionDescribe;
	}

	public void setExceptionDescribe(String exceptionDescribe) {
		this.exceptionDescribe = exceptionDescribe;
	}

	@Column(name = "SUGGESTION", length = 2000)
	public String getSuggestion() {
		return this.suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	@Column(name = "STATUS", precision = 1, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "FINAL_RESULT", length = 2000)
	public String getFinalResult() {
		return this.finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}

	@Column(name = "DEAL_NAME", length = 20)
	public String getDealName() {
		return this.dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "DEAL_TIME", length = 7)
	public Date getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	@Column(name = "DEAL_REASULT", length = 2000)
	public String getDealReasult() {
		return this.dealReasult;
	}

	public void setDealReasult(String dealReasult) {
		this.dealReasult = dealReasult;
	}

	@Column(name = "IS_CP", precision = 1, scale = 0)
	public Long getIsCp() {
		return this.isCp;
	}

	public void setIsCp(Long isCp) {
		this.isCp = isCp;
	}

	@Column(name = "IS_CUS", precision = 1, scale = 0)
	public Long getIsCus() {
		return this.isCus;
	}

	public void setIsCus(Long isCus) {
		this.isCus = isCus;
	}

	@Column(name = "IS_REPAR", precision = 1, scale = 0)
	public Long getIsRepar() {
		return this.isRepar;
	}

	public void setIsRepar(Long isRepar) {
		this.isRepar = isRepar;
	}

	@Column(name = "IS_WEB", precision = 1, scale = 0)
	public Long getIsWeb() {
		return this.isWeb;
	}

	public void setIsWeb(Long isWeb) {
		this.isWeb = isWeb;
	}

	@Column(name = "EXPTION_ADD", length = 2000)
	public String getExptionAdd() {
		return this.exptionAdd;
	}

	public void setExptionAdd(String exptionAdd) {
		this.exptionAdd = exptionAdd;
	}

	@Column(name = "FINAL_DUTY", length = 20)
	public String getFinalDuty() {
		return this.finalDuty;
	}

	public void setFinalDuty(String finalDuty) {
		this.finalDuty = finalDuty;
	}

	@Column(name = "FINAL_PIECE", precision = 8, scale = 0)
	public Long getFinalPiece() {
		return this.finalPiece;
	}

	public void setFinalPiece(Long finalPiece) {
		this.finalPiece = finalPiece;
	}

	@Column(name = "QM", length = 20)
	public String getQm() {
		return this.qm;
	}

	public void setQm(String qm) {
		this.qm = qm;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "QM_TIME", length = 7)
	public Date getQmTime() {
		return this.qmTime;
	}

	public void setQmTime(Date qmTime) {
		this.qmTime = qmTime;
	}

	@Column(name = "QM_SUGGESTION", length = 2000)
	public String getQmSuggestion() {
		return this.qmSuggestion;
	}

	public void setQmSuggestion(String qmSuggestion) {
		this.qmSuggestion = qmSuggestion;
	}

	@Column(name = "SUBMIT_QUALIFIED", precision = 1, scale = 0)
	public Long getSubmitQualified() {
		return this.submitQualified;
	}

	public void setSubmitQualified(Long submitQualified) {
		this.submitQualified = submitQualified;
	}

	@Column(name = "DEAL_QUALIFIED", precision = 1, scale = 0)
	public Long getDealQualified() {
		return this.dealQualified;
	}

	public void setDealQualified(Long dealQualified) {
		this.dealQualified = dealQualified;
	}

	@Column(name = "REPAR_QUALIFIED", precision = 1, scale = 0)
	public Long getReparQualified() {
		return this.reparQualified;
	}

	public void setReparQualified(Long reparQualified) {
		this.reparQualified = reparQualified;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "DEPART_ID", precision = 20, scale = 0)
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

	@Column(name = "CREATE_DEPART_ID",  length = 50)
	public String getCreateDepartId() {
		return createDepartId;
	}

	public void setCreateDepartId(String createDepartId) {
		this.createDepartId = createDepartId;
	}

	@Column(name = "CREATE_DEPART_NAME", length = 50)
	public String getCreateDepartName() {
		return createDepartName;
	}

	public void setCreateDepartName(String createDepartName) {
		this.createDepartName = createDepartName;
	}

	@Column(name = "EXCEPTION_NODE", length = 50)
	public String getExceptionNode() {
		return exceptionNode;
	}

	public void setExceptionNode(String exceptionNode) {
		this.exceptionNode = exceptionNode;
	}
	
}