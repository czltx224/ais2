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
 * OprReceiptComfirmLimitation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_RECEIPT_COMFIRM_LIMITATION")
public class OprReceiptComfirmLimitation implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String operationType;//������ʽ
	private String remark;//��ע
	private String countTime;//�Զ�ͳ��ʱ��
	private String countRange;//ͳ��ά�ȣ���/��/��/�գ�
	private String deptName;//��������
	private Long deptId;//����ID
	private String createName;//������
	private Date createTime;//����ʱ��
	private String updateName;//�޸���
	private Date updateTime;//�޸�ʱ��
	private String ts;//ʱ���
	private String comfirmStartTime;//ȷ�տ�ʼʱ��
	private String comfirmEndTime;//ȷ�ս���ʱ��

	private String comfirmStandardTime;
	private Long comfirmStandardDay;

	// Constructors

	/** default constructor */
	public OprReceiptComfirmLimitation() {
	}

	/** minimal constructor */
	public OprReceiptComfirmLimitation(Long id, String deptName, Long deptId) {
		this.id = id;
		this.deptName = deptName;
		this.deptId = deptId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_RECEIPT_LIMITATION")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OPERATION_TYPE", length = 20)
	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "COUNT_TIME", length = 5)
	public String getCountTime() {
		return this.countTime;
	}

	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}

	@Column(name = "COUNT_RANGE", length = 10)
	public String getCountRange() {
		return this.countRange;
	}

	public void setCountRange(String countRange) {
		this.countRange = countRange;
	}

	@Column(name = "DEPT_NAME", nullable = false, length = 50)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", nullable = false, precision = 22, scale = 0)
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

	@JSON(format = "yyyy-MM-dd HH:mm")
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

	@JSON(format = "yyyy-MM-dd HH:mm")
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

	@Column(name = "COMFIRM_START_TIME", length = 5)
	public String getComfirmStartTime() {
		return this.comfirmStartTime;
	}

	public void setComfirmStartTime(String comfirmStartTime) {
		this.comfirmStartTime = comfirmStartTime;
	}

	@Column(name = "COMFIRM_END_TIME", length = 5)
	public String getComfirmEndTime() {
		return this.comfirmEndTime;
	}

	public void setComfirmEndTime(String comfirmEndTime) {
		this.comfirmEndTime = comfirmEndTime;
	}

	@Column(name = "COMFIRM_STANDARD_TIME", length = 5)
	public String getComfirmStandardTime() {
		return this.comfirmStandardTime;
	}

	public void setComfirmStandardTime(String comfirmStandardTime) {
		this.comfirmStandardTime = comfirmStandardTime;
	}

	@Column(name = "COMFIRM_STANDARD_DAY")
	public Long getComfirmStandardDay() {
		return comfirmStandardDay;
	}

	public void setComfirmStandardDay(Long comfirmStandardDay) {
		this.comfirmStandardDay = comfirmStandardDay;
	}
	
}