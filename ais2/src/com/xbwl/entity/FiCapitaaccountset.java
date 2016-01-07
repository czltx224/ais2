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
import com.xbwl.common.utils.XbwlInt;

/**
 * �ʽ��˺����ñ�
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_CAPITAACCOUNTSET")
public class FiCapitaaccountset implements java.io.Serializable,AuditableEntity {
	private Long id;
	@XbwlInt(autoDepart=false)
	private Long departId;
	@XbwlInt(autoDepart=false)
	private String departName;//����ҵ����
	private Long paymentType; //��֧����(��:0\֧:1\��֧:2)
	private Long accountType; //�˺�����
	private String accountNum; //�˺�
	private String accountName; //�˺�����
	private String bank; //������
	private Double openingBalance=0.0; //���
	private Long responsibleId; //������id
	private String responsible; //������
	private String remark;//��ע
	private Long isDelete=1l; //״̬,Ĭ��1Ϊ���ã�0Ϊ����
	private String createName;
	private Date createTime;
	private Date updateTime;
	private String updateName;
	private String ts;
	
	private Long ownedBank;//��������
	private String nature;//�˺�����(�Թ�����˽)
	private String internetBank;//�Ƿ�ͨ����(δ��ͨ,�ѿ�ͨ)

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_CAPITAACCOUNTSET")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "PAYMENT_TYPE", precision = 22, scale = 0)
	public Long getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "ACCOUNT_TYPE", precision = 22, scale = 0)
	public Long getAccountType() {
		return this.accountType;
	}

	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}

	@Column(name = "ACCOUNT_NUM",length = 20)
	public String getAccountNum() {
		return this.accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	@Column(name = "ACCOUNT_NAME", length = 20)
	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "BANK", length = 50)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "OPENING_BALANCE", precision = 10)
	public Double getOpeningBalance() {
		return this.openingBalance;
	}

	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}

	@Column(name = "RESPONSIBLEID", precision = 22, scale = 0)
	public Long getResponsibleId() {
		return responsibleId;
	}

	public void setResponsibleId(Long responsibleId) {
		this.responsibleId = responsibleId;
	}

	@Column(name = "RESPONSIBLE", length = 50)
	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "ISDELETE", precision = 22, scale = 0)
	public Long getIsDelete() {
		return isDelete;
	}
	
	
	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
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

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "OWNEDBANK", precision = 22, scale = 0)
	public Long getOwnedBank() {
		return ownedBank;
	}

	public void setOwnedBank(Long ownedBank) {
		this.ownedBank = ownedBank;
	}

	@Column(name = "NATURE", length = 20)
	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	@Column(name = "INTERNETBANK", length = 20)
	public String getInternetBank() {
		return internetBank;
	}

	public void setInternetBank(String internetBank) {
		this.internetBank = internetBank;
	}
	
	
}