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

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * Ӧ��Ӧ��--�쳣������
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_PAYMENTABNORMAL")
public class FiPaymentabnormal implements java.io.Serializable, AuditableEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long fiPaymentId;//Ӧ�ո���ID
	private Long type; //�쳣����
	private Double amount; //�쳣���
	private String remark;//��ע
	private Long status=1l;//����״̬(״̬(0:����,1:δ����,2:�Ѵ���)
	private String revocationRemark;//���ϱ�ע
	private String workflowNo;//���̺�
	private String verRemark;//������ע
	private Long departId; //����ҵ����id(�������)
	private String departName;//����ҵ����
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private Long updateDeptid;
	private String updateDept;
	private String ts;
	private Double verificationAmount=0.0; //�������
	private Date verificationTime; //����ʱ��
	private Long verificationStatus=1l; //����״̬(1:δ����,2:�Ѻ���)
	private String verificationuser;//������

	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_PAYMENTABNORMAL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FI_PAYMENT_ID", precision = 10, scale = 0)
	public Long getFiPaymentId() {
		return this.fiPaymentId;
	}

	public void setFiPaymentId(Long fiPaymentId) {
		this.fiPaymentId = fiPaymentId;
	}

	@Column(name = "TYPE", precision = 22, scale = 0)
	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	@Column(name = "AMOUNT", precision = 22, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "WORKFLOW_NO", length = 50)
	public String getWorkflowNo() {
		return this.workflowNo;
	}

	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}
	
	
	@Column(name = "VER_REMARK", length = 500)
	public String getVerRemark() {
		return verRemark;
	}

	public void setVerRemark(String verRemark) {
		this.verRemark = verRemark;
	}


	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return departName;
	}
	
	
	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_DEPTID", precision = 22, scale = 0)
	public Long getUpdateDeptid() {
		return this.updateDeptid;
	}

	public void setUpdateDeptid(Long updateDeptid) {
		this.updateDeptid = updateDeptid;
	}

	@Column(name = "UPDATE_DEPT", length = 50)
	public String getUpdateDept() {
		return this.updateDept;
	}

	public void setUpdateDept(String updateDept) {
		this.updateDept = updateDept;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}


	@Column(name = "VERIFICATION_AMOUNT", precision = 10)
	public Double getVerificationAmount() {
		return verificationAmount;
	}

	public void setVerificationAmount(Double verificationAmount) {
		this.verificationAmount = verificationAmount;
	}

	
	@Column(name = "VERIFICATION_TIME", length = 7)
	public Date getVerificationTime() {
		return verificationTime;
	}

	public void setVerificationTime(Date verificationTime) {
		this.verificationTime = verificationTime;
	}

	@Column(name = "VERIFICATION_STATUS", precision = 22, scale = 0)
	public Long getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(Long verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	@Column(name = "VERIFICATION_USER", length = 20)
	public String getVerificationuser() {
		return verificationuser;
	}

	public void setVerificationuser(String verificationuser) {
		this.verificationuser = verificationuser;
	}

}