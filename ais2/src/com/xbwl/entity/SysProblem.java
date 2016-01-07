package com.xbwl.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * SysProblem entity.
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_PROBLEM", schema = "AISUSER")
public class SysProblem implements java.io.Serializable,AuditableEntity {

	// Fields
	private Long id;
	private String problemContent;    //��������	
	private String doProblemName;     //  ������
	private Date doProblemTime;       //	����ʱ��
	private String doProblemResult;   //		������
	private Long problemStatus=1l;           //		����״̬ 1:������2:��ָ�ɣ�3:�Ѵ���4:������,0:ɾ��
	private String problemPhotoAddr;    //  ����ͼƬ��ַ
	private Date createTime;                                  
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	private Long problemScore;                   //����	
	private Date problemAssignedTime;   //ָ��ʱ��
	private Date doProblemMaxTime;        //  ����ʱ��
	private Long doProblemStatus;           // ����ʱ״̬ 0������ʱ��1����ʱ
	private String problemUrgentRating;  //��������̶�
	private String problemModeName;  //ģ������


	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_PROBLEM")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PROBLEM_CONTENT", length = 500)
	public String getProblemContent() {
		return this.problemContent;
	}

	public void setProblemContent(String problemContent) {
		this.problemContent = problemContent;
	}

	@Column(name = "DO_PROBLEM_NAME", length = 50)
	public String getDoProblemName() {
		return this.doProblemName;
	}

	public void setDoProblemName(String doProblemName) {
		this.doProblemName = doProblemName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "DO_PROBLEM_TIME", length = 7)
	public Date getDoProblemTime() {
		return this.doProblemTime;
	}

	public void setDoProblemTime(Date doProblemTime) {
		this.doProblemTime = doProblemTime;
	}

	@Column(name = "DO_PROBLEM_RESULT", length = 500)
	public String getDoProblemResult() {
		return this.doProblemResult;
	}

	public void setDoProblemResult(String doProblemResult) {
		this.doProblemResult = doProblemResult;
	}

	@Column(name = "PROBLEM_STATUS", nullable = false, precision = 1, scale = 0)
	public Long getProblemStatus() {
		return this.problemStatus;
	}

	public void setProblemStatus(Long problemStatus) {
		this.problemStatus = problemStatus;
	}

	@Column(name = "PROBLEM_PHOTO_ADDR", length = 500)
	public String getProblemPhotoAddr() {
		return this.problemPhotoAddr;
	}

	public void setProblemPhotoAddr(String problemPhotoAddr) {
		this.problemPhotoAddr = problemPhotoAddr;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 50)
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

	@Column(name = "UPDATE_NAME", length = 50)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "TS", length = 50)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "PROBLEM_SCORE", precision = 22, scale = 0)
	public Long getProblemScore() {
		return this.problemScore;
	}

	public void setProblemScore(Long problemScore) {
		this.problemScore = problemScore;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "PROBLEM_ASSIGNED_TIME", length = 7)
	public Date getProblemAssignedTime() {
		return this.problemAssignedTime;
	}

	public void setProblemAssignedTime(Date problemAssignedTime) {
		this.problemAssignedTime = problemAssignedTime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "DO_PROBLEM_MAX_TIME", length = 7)
	public Date getDoProblemMaxTime() {
		return this.doProblemMaxTime;
	}

	public void setDoProblemMaxTime(Date doProblemMaxTime) {
		this.doProblemMaxTime = doProblemMaxTime;
	}

	@Column(name = "DO_PROBLEM_STATUS", precision = 1, scale = 0)
	public Long getDoProblemStatus() {
		return this.doProblemStatus;
	}

	public void setDoProblemStatus(Long doProblemStatus) {
		this.doProblemStatus = doProblemStatus;
	}

	@Column(name = "PROBLEM_URGENT_RATING")
	public String getProblemUrgentRating() {  
		return problemUrgentRating;
	}

	public void setProblemUrgentRating(String problemUrgentRating) {
		this.problemUrgentRating = problemUrgentRating;
	}

	@Column(name = "PROBLEM_MODE_NAME")
	public String getProblemModeName() {
		return problemModeName;
	}

	public void setProblemModeName(String problemModeName) {
		this.problemModeName = problemModeName;
	}


}