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
 * FiReconciliationAccount entity.
 * 对账单打印账号基类
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_RECONCILIATION_ACCOUNT", schema = "AISUSER")
public class FiReconciliationAccount implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String nature;  //账号性质
	private String bank;  //银行名称 
	private String createBank; //开户银行
	private String accountNum;  //账号
	private String accountName;  //账号名称 
	private String phone;  // 联系方式
	private Date createTime;   
	private String createName;
	private Date updateTime;
	private String updateName;
	private Long isDelete;   //状态，0删除 1正常
	private String departName;  
	private Long departId;  // 业务部门
	private String remark;
	private String ts;
	
	private String bank2;	//	对私银行名称
	private String accountNum2;	//	对私账号
	private String accountName2;	//	对私开户名
	private String  createBank2	;//		对私开户银行

	// Constructors

	/** default constructor */
	public FiReconciliationAccount() {
	}

	/** minimal constructor */
	public FiReconciliationAccount(Long id) {
		this.id = id;
	}

	/** full constructor */
	public FiReconciliationAccount(Long id, String nature, String bank,
			String accountNum, String accountName, String phone,
			Date createTime, String createName, Date updateTime,
			String updateName, Long isdelete, String departname, Long departId,
			String remark, String ts) {
		this.id = id;
		this.nature = nature;
		this.bank = bank;
		this.accountNum = accountNum;
		this.accountName = accountName;
		this.phone = phone;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.isDelete = isdelete;
		this.departName = departname;
		this.departId = departId;
		this.remark = remark;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_RECONCILIATION_ACCOUNT",allocationSize=1)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NATURE", length = 10)
	public String getNature() {
		return this.nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	@Column(name = "BANK", length = 50)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "ACCOUNT_NUM", length = 50)
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

	@Column(name = "PHONE", length = 30)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	@Column(name = "IS_DELETE", precision = 22, scale = 0)
	public Long getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "CREATE_BANK", length = 50)
	public String getCreateBank() {
		return createBank;
	}

	public void setCreateBank(String createBank) {
		this.createBank = createBank;
	}

	@Column(name = "BANK2", length = 50)
	public String getBank2() {
		return bank2;
	}

	public void setBank2(String bank2) {
		this.bank2 = bank2;
	}

	@Column(name = "ACCOUNT_NUM2", length = 50)
	public String getAccountNum2() {
		return accountNum2;
	}

	public void setAccountNum2(String accountNum2) {
		this.accountNum2 = accountNum2;
	}

	@Column(name = "ACCOUNT_NAME2", length = 50)
	public String getAccountName2() {
		return accountName2;
	}

	public void setAccountName2(String accountName2) {
		this.accountName2 = accountName2;
	}

	@Column(name = "CREATE_BANK2", length = 50)
	public String getCreateBank2() {
		return createBank2;
	}

	public void setCreateBank2(String createBank2) {
		this.createBank2 = createBank2;
	}

}