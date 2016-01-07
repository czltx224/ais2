package com.xbwl.entity;
// default package

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
 * �ʽ���ˮ�˱�
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_CAPITAACCOUNT")
public class FiCapitaaccount implements java.io.Serializable,AuditableEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long fiCapitaaccountsetId;//�����˺�id
	private Double borrow; //��(֧��)
	private Double loan; //��(����)
	private Double balance; //���
	private String remark; //ժҪ
	private String sourceData; //������Դ
	private Long sourceNo; //��Դ����
	private Long departId; //����ҵ����id(�������)
	private String departName;//����ҵ����
	private String createName;
	private Date createTime;
	private String createUser;
	private Date updateTime;
	private String updateName;
	private String ts;
	private Long status=1L;//״̬(0:����,1:����)


	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_CAPITAACCOUNT")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FI_CAPITAACCOUNTSET_ID", precision = 22, scale = 0)
	public Long getFiCapitaaccountsetId() {
		return this.fiCapitaaccountsetId;
	}

	public void setFiCapitaaccountsetId(Long fiCapitaaccountsetId) {
		this.fiCapitaaccountsetId = fiCapitaaccountsetId;
	}

	@Column(name = "BORROW", precision = 10)
	public Double getBorrow() {
		return this.borrow;
	}

	public void setBorrow(Double borrow) {
		this.borrow = borrow;
	}

	@Column(name = "LOAN", precision = 10)
	public Double getLoan() {
		return this.loan;
	}

	public void setLoan(Double loan) {
		this.loan = loan;
	}

	@Column(name = "BALANCE", precision = 10)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_USER", length = 20)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	@Column(name = "SOURCE_DATA", length = 10)
	public String getSourceData() {
		return this.sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	@Column(name = "SOURCE_NO", precision = 22, scale = 0)
	public Long getSourceNo() {
		return this.sourceNo;
	}
	
	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}

	@Column(name = "STATUS")
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	

}