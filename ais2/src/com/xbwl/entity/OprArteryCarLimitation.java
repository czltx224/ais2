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
 * OprArteryCarLimitation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_ARTERY_CAR_LIMITATION")
public class OprArteryCarLimitation implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long startDepartId;//ʼ������ID
	private String startDepartName;//ʼ����������
	private Long endDepartId;//���ﲿ��ID
	private String endDepartName;//���ﲿ������
	private String outCarTime;//����ʱ��
	private String reachCarTime;//����ʱ��
	private Double runCarTime;//��������ʱ��
	private String countRange;//ͳ��ά�ȣ���/��/��/�գ�
	private String countTime;//�Զ�ͳ��ʱ��
	private String deptName;//��������
	private Long deptId;//����ID
	private String createName;//������
	private Date createTime;//����ʱ��
	private String updateName;//�޸���
	private Date updateTime;//�޸�ʱ��
	private String ts;//ʱ���

	// Constructors

	/** default constructor */
	public OprArteryCarLimitation() {
	}

	/** minimal constructor */
	public OprArteryCarLimitation(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprArteryCarLimitation(Long id, Long startDepartId,
			String startDepartName, Long endDepartId, String endDepartName,
			String outCarTime, String reachCarTime, Double runCarTime,
			String countRange, String countTime, String deptName, Long deptId,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts) {
		this.id = id;
		this.startDepartId = startDepartId;
		this.startDepartName = startDepartName;
		this.endDepartId = endDepartId;
		this.endDepartName = endDepartName;
		this.outCarTime = outCarTime;
		this.reachCarTime = reachCarTime;
		this.runCarTime = runCarTime;
		this.countRange = countRange;
		this.countTime = countTime;
		this.deptName = deptName;
		this.deptId = deptId;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_ARTERY_CAR_LIMITATION")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "START_DEPART_ID", precision = 22, scale = 0)
	public Long getStartDepartId() {
		return this.startDepartId;
	}

	public void setStartDepartId(Long startDepartId) {
		this.startDepartId = startDepartId;
	}

	@Column(name = "START_DEPART_NAME", length = 50)
	public String getStartDepartName() {
		return this.startDepartName;
	}

	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}

	@Column(name = "END_DEPART_ID", precision = 22, scale = 0)
	public Long getEndDepartId() {
		return this.endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	@Column(name = "END_DEPART_NAME", length = 50)
	public String getEndDepartName() {
		return this.endDepartName;
	}

	public void setEndDepartName(String endDepartName) {
		this.endDepartName = endDepartName;
	}

	@Column(name = "OUT_CAR_TIME", length = 10)
	public String getOutCarTime() {
		return this.outCarTime;
	}

	public void setOutCarTime(String outCarTime) {
		this.outCarTime = outCarTime;
	}

	@Column(name = "REACH_CAR_TIME", length = 10)
	public String getReachCarTime() {
		return this.reachCarTime;
	}

	public void setReachCarTime(String reachCarTime) {
		this.reachCarTime = reachCarTime;
	}

	@Column(name = "RUN_CAR_TIME")
	public Double getRunCarTime() {
		return this.runCarTime;
	}

	public void setRunCarTime(Double runCarTime) {
		this.runCarTime = runCarTime;
	}

	@Column(name = "COUNT_RANGE", length = 10)
	public String getCountRange() {
		return this.countRange;
	}

	public void setCountRange(String countRange) {
		this.countRange = countRange;
	}

	@Column(name = "COUNT_TIME", length = 10)
	public String getCountTime() {
		return this.countTime;
	}

	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}

	@Column(name = "DEPT_NAME", length = 50)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", precision = 22, scale = 0)
	public Long getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CREATE_NAME", length = 50)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd hh:mm")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 50)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd hh:mm")
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

}