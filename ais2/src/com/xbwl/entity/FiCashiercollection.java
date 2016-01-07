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
 * �����տ��
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_CASHIERCOLLECTION")
public class FiCashiercollection implements java.io.Serializable ,AuditableEntity{
	private Long id;
	private String penyJenis; //���㷽ʽ
	private Date collectionTime; //����ʱ��
	private Long fiCapitaaccountsetId;//�����˺�
	private Double collectionAmount; //���˽��
	private Double verificationAmount=0.00; //�������
	private Date verificationTime; //����ʱ��
	private String verificationUser; //������
	private String verificationDept; //��������
	private Long verificationStatus=0L; //����״̬(0:δ����/1:�Ѻ���)
	private String verificationRemark;//������ע
	private Double entrustAmount=0.00; //ί��ȷ�Ͻ��
	private String entrustUser; //ί��ȷ����
	private Date entrustTime; //ί��ȷ��ʱ��
	private String entrustRemark; //ί��ȷ�ϱ�ע 
	private Long departId; //����ҵ����id(�������)
	private String departName;//����ҵ����
	private String createRemark;
	private Date createTime;
	private String createName;
	private String updateDept;
	private Date updateTime;
	private String updateName;
	private String ts;
	private Long status=1l;//״̬(0:����,1:����)
	private Long FiReceiptId;//�վݺ�

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_CASHIERCOLLECTION")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PENY_JENIS", length = 20)
	public String getPenyJenis() {
		return this.penyJenis;
	}

	public void setPenyJenis(String penyJenis) {
		this.penyJenis = penyJenis;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format="yyyy-MM-dd")
	@Column(name = "COLLECTION_TIME", length = 7)
	public Date getCollectionTime() {
		return this.collectionTime;
	}

	public void setCollectionTime(Date collectionTime) {
		this.collectionTime = collectionTime;
	}

	@Column(name = "FI_CAPITAACCOUNTSET_ID", precision = 22, scale = 0)
	public Long getFiCapitaaccountsetId() {
		return this.fiCapitaaccountsetId;
	}

	public void setFiCapitaaccountsetId(Long fiCapitaaccountsetId) {
		this.fiCapitaaccountsetId = fiCapitaaccountsetId;
	}

	@Column(name = "COLLECTION_AMOUNT", precision = 10)
	public Double getCollectionAmount() {
		return this.collectionAmount;
	}

	public void setCollectionAmount(Double collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	@Column(name = "VERIFICATION_AMOUNT", precision = 10)
	public Double getVerificationAmount() {
		return this.verificationAmount;
	}

	public void setVerificationAmount(Double verificationAmount) {
		this.verificationAmount = verificationAmount;
	}

	@Column(name = "VERIFICATION_TIME", length = 7)
	public Date getVerificationTime() {
		return this.verificationTime;
	}

	public void setVerificationTime(Date verificationTime) {
		this.verificationTime = verificationTime;
	}

	@Column(name = "VERIFICATION_USER", length = 20)
	public String getVerificationUser() {
		return this.verificationUser;
	}

	public void setVerificationUser(String verificationUser) {
		this.verificationUser = verificationUser;
	}

	@Column(name = "VERIFICATION_DEPT", length = 50)
	public String getVerificationDept() {
		return this.verificationDept;
	}

	public void setVerificationDept(String verificationDept) {
		this.verificationDept = verificationDept;
	}

	@Column(name = "VERIFICATION_STATUS", precision = 22, scale = 0)
	public Long getVerificationStatus() {
		return this.verificationStatus;
	}

	public void setVerificationStatus(Long verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	@Column(name = "ENTRUST_AMOUNT", precision = 10)
	public Double getEntrustAmount() {
		return this.entrustAmount;
	}

	public void setEntrustAmount(Double entrustAmount) {
		this.entrustAmount = entrustAmount;
	}

	@Column(name = "ENTRUST_USER", length = 20)
	public String getEntrustUser() {
		return this.entrustUser;
	}

	public void setEntrustUser(String entrustUser) {
		this.entrustUser = entrustUser;
	}

	@Column(name = "ENTRUST_TIME", length = 7)
	public Date getEntrustTime() {
		return this.entrustTime;
	}

	public void setEntrustTime(Date entrustTime) {
		this.entrustTime = entrustTime;
	}

	@Column(name = "ENTRUST_REMARK", length = 500)
	public String getEntrustRemark() {
		return this.entrustRemark;
	}

	public void setEntrustRemark(String entrustRemark) {
		this.entrustRemark = entrustRemark;
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

	@Column(name = "CREATE_REMARK", length = 500)
	public String getCreateRemark() {
		return this.createRemark;
	}

	public void setCreateRemark(String createRemark) {
		this.createRemark = createRemark;
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

	@Column(name = "UPDATE_DEPT", length = 50)
	public String getUpdateDept() {
		return this.updateDept;
	}

	public void setUpdateDept(String updateDept) {
		this.updateDept = updateDept;
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

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "FI_RECEIPT_ID", precision = 22, scale = 0)
	public Long getFiReceiptId() {
		return FiReceiptId;
	}

	public void setFiReceiptId(Long fiReceiptId) {
		FiReceiptId = fiReceiptId;
	}
	
	
	@Column(name = "VERIFICATION_REMARK", length = 500)
	public String getVerificationRemark() {
		return verificationRemark;
	}

	public void setVerificationRemark(String verificationRemark) {
		this.verificationRemark = verificationRemark;
	}

}