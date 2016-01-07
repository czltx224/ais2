package com.xbwl.entity;
// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SysVersionsMsg entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_VERSIONS_MSG", schema = "AISUSER")
public class SysVersionsMsg implements java.io.Serializable {

	// Fields

	private Long revision;
	private Date revisionDate;
	private String message;
	private String author;
	private String logentry;
	private String firstAudit;
	private Date firstAuditTime;
	private String firstAuditRemark;
	private String secondAudit;
	private Date secondAuditTime;
	private String secondAuditRemark;

	// Constructors

	/** default constructor */
	public SysVersionsMsg() {
	}

	/** minimal constructor */
	public SysVersionsMsg(Long revision) {
		this.revision = revision;
	}

	/** full constructor */
	public SysVersionsMsg(Long revision, Date revisionDate, String message,
			String author, String logentry, String firstAudit,
			Date firstAuditTime, String firstAuditRemark, String secondAudit,
			Date secondAuditTime, String secondAuditRemark) {
		this.revision = revision;
		this.revisionDate = revisionDate;
		this.message = message;
		this.author = author;
		this.logentry = logentry;
		this.firstAudit = firstAudit;
		this.firstAuditTime = firstAuditTime;
		this.firstAuditRemark = firstAuditRemark;
		this.secondAudit = secondAudit;
		this.secondAuditTime = secondAuditTime;
		this.secondAuditRemark = secondAuditRemark;
	}

	// Property accessors
	@Id
	@Column(name = "REVISION", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getRevision() {
		return this.revision;
	}

	public void setRevision(Long revision) {
		this.revision = revision;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REVISION_DATE", length = 7)
	public Date getRevisionDate() {
		return this.revisionDate;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	@Column(name = "MESSAGE", length = 20)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "AUTHOR", length = 20)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "LOGENTRY", length = 2000)
	public String getLogentry() {
		return this.logentry;
	}

	public void setLogentry(String logentry) {
		this.logentry = logentry;
	}

	@Column(name = "FIRST_AUDIT", length = 20)
	public String getFirstAudit() {
		return this.firstAudit;
	}

	public void setFirstAudit(String firstAudit) {
		this.firstAudit = firstAudit;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FIRST_AUDIT_TIME", length = 7)
	public Date getFirstAuditTime() {
		return this.firstAuditTime;
	}

	public void setFirstAuditTime(Date firstAuditTime) {
		this.firstAuditTime = firstAuditTime;
	}

	@Column(name = "FIRST_AUDIT_REMARK", length = 2000)
	public String getFirstAuditRemark() {
		return this.firstAuditRemark;
	}

	public void setFirstAuditRemark(String firstAuditRemark) {
		this.firstAuditRemark = firstAuditRemark;
	}

	@Column(name = "SECOND_AUDIT", length = 20)
	public String getSecondAudit() {
		return this.secondAudit;
	}

	public void setSecondAudit(String secondAudit) {
		this.secondAudit = secondAudit;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SECOND_AUDIT_TIME", length = 7)
	public Date getSecondAuditTime() {
		return this.secondAuditTime;
	}

	public void setSecondAuditTime(Date secondAuditTime) {
		this.secondAuditTime = secondAuditTime;
	}

	@Column(name = "SECOND_AUDIT_REMARK", length = 2000)
	public String getSecondAuditRemark() {
		return this.secondAuditRemark;
	}

	public void setSecondAuditRemark(String secondAuditRemark) {
		this.secondAuditRemark = secondAuditRemark;
	}

}