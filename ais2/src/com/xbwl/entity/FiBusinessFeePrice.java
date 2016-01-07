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
 * FiBusinessFeePrice entity.
 * 业务费协议价管理实体类
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_BUSINESS_FEE_PRICE", schema = "AISUSER")
public class FiBusinessFeePrice implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long customerId;  
	private String customerName;           // 发货代理
	private String settlement;         // 费率类型（重量/营业额/固定金额）
	private Double rate;               //费率
	private String departName;       //业务部门
	private Long departId;        //业务部门
	private Date createTime;             //创建时间
	private String createName;           //创建人
	private Date updateTime;             //修改时间
	private String updateName;          //修改人
	private String remark;        //备注
	private Long isDelete=1l;     //作废状态(0：已作废；1：正常)
	private String ts;          //时间戳
	private Long accountNum;   //收款账号
	private String incomeCustomerName;  //收款客商
	private String bank;             //开户行
	private String phone;   //联系方式
	private String reviewUser;    //审核人
	private Date reviewDate;      //审核时间
	private Long status=0l;   //状态(0：新增；1：已审核)
	private Long incomeCustomerId;   //收款客商ID

	// Constructors

	/** default constructor */
	public FiBusinessFeePrice() {
	}

	/** minimal constructor */
	public FiBusinessFeePrice(Long id, Long customerId, String customerName,
			String settlement, Double rate, Long isDelete) {
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
		this.settlement = settlement;
		this.rate = rate;
		this.isDelete = isDelete;
	}

	/** full constructor */
	public FiBusinessFeePrice(Long id, Long customerId, String customerName,
			String settlement, Double rate, String departName, Long departId,
			Date createTime, String createName, Date updateTime,
			String updateName, String remark, Long isDelete, String ts,
			Long accountNum, String incomeCustomerName, String bank,
			String phone, String reviewUser, Date reviewDate, Long status,
			Long incomeCustomerId) {
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
		this.settlement = settlement;
		this.rate = rate;
		this.departName = departName;
		this.departId = departId;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.remark = remark;
		this.isDelete = isDelete;
		this.ts = ts;
		this.accountNum = accountNum;
		this.incomeCustomerName = incomeCustomerName;
		this.bank = bank;
		this.phone = phone;
		this.reviewUser = reviewUser;
		this.reviewDate = reviewDate;
		this.status = status;
		this.incomeCustomerId = incomeCustomerId;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_BUSINESS_FEE_PRICE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", length = 50)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "SETTLEMENT", length = 50)
	public String getSettlement() {
		return this.settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	@Column(name = "RATE", precision = 10)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "DEPART_ID", precision = 20, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
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

	@Column(name = "REMARK", length = 50)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "IS_DELETE", precision = 22, scale = 0)
	public Long getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "ACCOUNT_NUM", precision = 22, scale = 0)
	public Long getAccountNum() {
		return this.accountNum;
	}

	public void setAccountNum(Long accountNum) {
		this.accountNum = accountNum;
	}

	@Column(name = "INCOME_CUSTOMER_NAME", length = 20)
	public String getIncomeCustomerName() {
		return this.incomeCustomerName;
	}

	public void setIncomeCustomerName(String incomeCustomerName) {
		this.incomeCustomerName = incomeCustomerName;
	}

	@Column(name = "BANK", length = 50)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "REVIEW_USER", length = 20)
	public String getReviewUser() {
		return this.reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "REVIEW_DATE", length = 7)
	public Date getReviewDate() {
		return this.reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	@Column(name = "STATUS", precision = 1, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "INCOME_CUSTOMER_ID", precision = 22, scale = 0)
	public Long getIncomeCustomerId() {
		return this.incomeCustomerId;
	}

	public void setIncomeCustomerId(Long incomeCustomerId) {
		this.incomeCustomerId = incomeCustomerId;
	}

}