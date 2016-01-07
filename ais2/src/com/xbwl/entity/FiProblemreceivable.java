package com.xbwl.entity;

// default package

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
 * 问题账款记录表 FiProblemreceivable entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_PROBLEMRECEIVABLE")
public class FiProblemreceivable implements java.io.Serializable,
		AuditableEntity {

	// Fields

	private Long id;
	private String sourceData; // 数据来源
	private Long sourceNo; // 来源单号
	private Long customerId;// 客商ID
	private String customerName;// 客商名称
	private Long dno;// 配送单号
	private String problemType; // 问题账款类型
	private Double problemAmount=0.0; // 问题账款金额
	private String problemRemark; // 问题账款备注
	private Long status=1L; // 状态(1:已登记/2:已审核/0:已取消)
	private Long workflowNo; // 流程号
	private String verRemark;//审核备注
	private String updateName;
	private String createName;
	private Date createTime;
	private String ts;
	private String orderfields;
	private Date updateTime;
	private Long departId; //创建业务部门id(必输参数)
	private String departName;//创建业务部门
	private String updateDept;
	private Double verificationAmount=0.0; //核销金额
	private Date verificationTime; //核销时间
	private Long verificationStatus=1L; //核销状态(1:未核销,2:部门核销,3：已核销)
	private String verificationuser;//核销人


	/** default constructor */
	public FiProblemreceivable() {
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_PROBLEMRECEIVABLE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", precision = 22, scale = 0)
	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}
	
	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", length = 200)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Column(name = "SOURCE_DATA", length = 10)
	public String getSourceData() {
		return this.sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	@Column(name = "SOURCE_NO", precision = 10, scale = 0)
	public Long getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}

	@Column(name = "PROBLEM_TYPE", length = 10)
	public String getProblemType() {
		return this.problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	@Column(name = "PROBLEM_AMOUNT", precision = 10)
	public Double getProblemAmount() {
		return this.problemAmount;
	}

	public void setProblemAmount(Double problemAmount) {
		this.problemAmount = problemAmount;
	}

	@Column(name = "PROBLEM_REMARK", length = 500)
	public String getProblemRemark() {
		return this.problemRemark;
	}

	public void setProblemRemark(String problemRemark) {
		this.problemRemark = problemRemark;
	}

	@Column(name = "STATUS", precision = 1, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "WORKFLOW_NO", precision = 22, scale = 0)
	public Long getWorkflowNo() {
		return this.workflowNo;
	}

	public void setWorkflowNo(Long workflowNo) {
		this.workflowNo = workflowNo;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
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

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "ORDERFIELDS", length = 200)
	public String getOrderfields() {
		return this.orderfields;
	}

	public void setOrderfields(String orderfields) {
		this.orderfields = orderfields;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	@Column(name = "UPDATE_DEPT", length = 50)
	public String getUpdateDept() {
		return this.updateDept;
	}

	public void setUpdateDept(String updateDept) {
		this.updateDept = updateDept;
	}
	
	@Column(name = "VER_REMARK", length = 500)
	public String getVerRemark() {
		return verRemark;
	}

	public void setVerRemark(String verRemark) {
		this.verRemark = verRemark;
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