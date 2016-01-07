package com.xbwl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

/**
 * CusDevelop entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_DEVELOP")
public class CusDevelop implements java.io.Serializable {

	// Fields

	private Long id;
	private String developName;//过程名称
	private String developType;//过程类型
	private String developStage;//过程阶段
	private String developContext;//开发经过
	private Long developCost;//开发成本
	private String filePath;//附件
	private String developedMan;//活动对象
	private String developMan;//开发人
	private Date developTime;//开发时间
	private Long isAudit;//是否审核
	private Date auditTime;//审核时间
	private String auditName;//审核人
	private String remark;//备注
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long cusRecordId;//联系人ID
	private Long status;
	
	private Long departId;
	private String developLinkmanTel;//活动联系人电话
	private Long assessResult;//评估结果 1-5，分别为：非常不满意、不满意、一般、满意、非常满意
	private String resultRemark;//结果说明
	// Constructors

	/** default constructor */
	public CusDevelop() {
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_DEVELOP")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DEVELOP_NAME", nullable = false, length = 50)
	public String getDevelopName() {
		return this.developName;
	}

	public void setDevelopName(String developName) {
		this.developName = developName;
	}

	@Column(name = "DEVELOP_TYPE", nullable = false, length = 20)
	public String getDevelopType() {
		return this.developType;
	}

	public void setDevelopType(String developType) {
		this.developType = developType;
	}

	@Column(name = "DEVELOP_STAGE", nullable = false, length = 20)
	public String getDevelopStage() {
		return this.developStage;
	}

	public void setDevelopStage(String developStage) {
		this.developStage = developStage;
	}

	@Column(name = "DEVELOP_CONTEXT", nullable = false, length = 2000)
	public String getDevelopContext() {
		return this.developContext;
	}

	public void setDevelopContext(String developContext) {
		this.developContext = developContext;
	}

	@Column(name = "DEVELOP_COST",precision = 22, scale = 0)
	public Long getDevelopCost() {
		return this.developCost;
	}

	public void setDevelopCost(Long developCost) {
		this.developCost = developCost;
	}

	@Column(name = "FILE_PATH", length = 200)
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "DEVELOPED_MAN", nullable = false, length = 50)
	public String getDevelopedMan() {
		return this.developedMan;
	}

	public void setDevelopedMan(String developedMan) {
		this.developedMan = developedMan;
	}

	@Column(name = "DEVELOP_MAN", nullable = false, length = 20)
	public String getDevelopMan() {
		return this.developMan;
	}

	public void setDevelopMan(String developMan) {
		this.developMan = developMan;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "DEVELOP_TIME", nullable = false, length = 7)
	public Date getDevelopTime() {
		return this.developTime;
	}

	public void setDevelopTime(Date developTime) {
		this.developTime = developTime;
	}

	@Column(name = "IS_AUDIT", nullable = false, precision = 22, scale = 0)
	public Long getIsAudit() {
		return this.isAudit;
	}

	public void setIsAudit(Long isAudit) {
		this.isAudit = isAudit;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "AUDIT_TIME", length = 7)
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "AUDIT_NAME", length = 20)
	public String getAuditName() {
		return this.auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "CUS_RECORD_ID", nullable = false, precision = 22, scale = 0)
	public Long getCusRecordId() {
		return this.cusRecordId;
	}

	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the departId
	 */
	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}

	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	/**
	 * @return the developLinkmanTel
	 */
	@Column(name = "DEVELOP_LINKMAN_TEL")
	public String getDevelopLinkmanTel() {
		return developLinkmanTel;
	}

	/**
	 * @param developLinkmanTel the developLinkmanTel to set
	 */
	public void setDevelopLinkmanTel(String developLinkmanTel) {
		this.developLinkmanTel = developLinkmanTel;
	}

	/**
	 * @return the assessResult
	 */
	@Column(name = "ASSESS_RESULT")
	public Long getAssessResult() {
		return assessResult;
	}

	/**
	 * @param assessResult the assessResult to set
	 */
	public void setAssessResult(Long assessResult) {
		this.assessResult = assessResult;
	}

	/**
	 * @return the resultRemark
	 */
	@Column(name = "RESULT_REMARK")
	public String getResultRemark() {
		return resultRemark;
	}

	/**
	 * @param resultRemark the resultRemark to set
	 */
	public void setResultRemark(String resultRemark) {
		this.resultRemark = resultRemark;
	}
	
}