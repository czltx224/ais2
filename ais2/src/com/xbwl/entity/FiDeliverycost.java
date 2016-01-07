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
 * FiDeliverycost entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_DELIVERYCOST", schema = "AISUSER")
public class FiDeliverycost implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String faxMainNo;  //系统主单号
	private Long matStatus;  // 匹配状态
	private String flightMainNo;//黄单号
	private Long customerId;// 提货点
	private String customerName;// 提货点名称
	private Long faxPiece;// 系统件数
	private Long flightPiece;//  黄单件数
	private Double faxWeight;//  系统重量
	private Double flightWeight;// 黄单重量
	private Double flightAmount;//黄单金额
	private Double boardAmount;//  板费
	private Double amount;// 总金额（板费+黄单金额）
	private Double price;//单价
	private Long isLowestStatus;//最低一票
	private String remark;//备注
	private Double diffWeight=0.0;// 重量差异
	private Double diffAmount=0.0;//金额差异
	private String startcity;//始发站
	private Long status=0l;//审核状态
	private String createName;//创建人
	private Date createTime;//创建时间
	private Long departId;//
	private String departName;//
	private String reviewDept;//审核部门
	private String reviewUser;//审核人
	private Date reviewDate;//审核时间
	private String reviewRemark;// 审核备注
	private String ts;// 
	private Date updateTime;
	private String updateName;
	private Long batchNo;//批次号
	private String companyCose;//  账单ID码
	private String goodsType;  //货物类型
	private Long payStatus=0l;//付款状态（0未付款，1已支付）
	private Date payTime; //付款时间
	private String payUser;//付款人
	private String faxId;
	// Constructors

	/** default constructor */
	public FiDeliverycost() {
	}

	/** minimal constructor */
	public FiDeliverycost(Long id) {
		this.id = id;
	}

	/** full constructor */
	public FiDeliverycost(Long id, String faxMainNo, Long matStatus,
			String flightMainNo, Long customerId, String customerName,
			Long faxPiece, Long flightPiece, Double faxWeight,
			Double flightWeight, Double flightAmount, Double boardAmount,
			Double amount, Double price, Long isLowestStatus, String remark,
			Double diffWeight, Double diffAmount, String startcity,
			Long status, String createName, Date createTime, Long createDeptid,
			String createDept, String reviewDept, String reviewUser,
			Date reviewDate, String reviewRemark, String ts) {
		this.id = id;
		this.faxMainNo = faxMainNo;
		this.matStatus = matStatus;
		this.flightMainNo = flightMainNo;
		this.customerId = customerId;
		this.customerName = customerName;
		this.faxPiece = faxPiece;
		this.flightPiece = flightPiece;
		this.faxWeight = faxWeight;
		this.flightWeight = flightWeight;
		this.flightAmount = flightAmount;
		this.boardAmount = boardAmount;
		this.amount = amount;
		this.price = price;
		this.isLowestStatus = isLowestStatus;
		this.remark = remark;
		this.diffWeight = diffWeight;
		this.diffAmount = diffAmount;
		this.startcity = startcity;
		this.status = status;
		this.createName = createName;
		this.createTime = createTime;
		this.reviewDept = reviewDept;
		this.reviewUser = reviewUser;
		this.reviewDate = reviewDate;
		this.reviewRemark = reviewRemark;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_DELIVERYCOST")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FAX_MAIN_NO", length = 200)
	public String getFaxMainNo() {
		return this.faxMainNo;
	}

	public void setFaxMainNo(String faxMainNo) {
		this.faxMainNo = faxMainNo;
	}

	@Column(name = "MAT_STATUS", precision = 22, scale = 0)
	public Long getMatStatus() {
		return this.matStatus;
	}

	public void setMatStatus(Long matStatus) {
		this.matStatus = matStatus;
	}

	@Column(name = "FLIGHT_MAIN_NO", length = 200)
	public String getFlightMainNo() {
		return this.flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
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

	@Column(name = "FAX_PIECE", precision = 22, scale = 0)
	public Long getFaxPiece() {
		return this.faxPiece;
	}

	public void setFaxPiece(Long faxPiece) {
		this.faxPiece = faxPiece;
	}

	@Column(name = "FLIGHT_PIECE", precision = 22, scale = 0)
	public Long getFlightPiece() {
		return this.flightPiece;
	}

	public void setFlightPiece(Long flightPiece) {
		this.flightPiece = flightPiece;
	}

	@Column(name = "FAX_WEIGHT", precision = 10)
	public Double getFaxWeight() {
		return this.faxWeight;
	}

	public void setFaxWeight(Double faxWeight) {
		this.faxWeight = faxWeight;
	}

	@Column(name = "FLIGHT_WEIGHT", precision = 10)
	public Double getFlightWeight() {
		return this.flightWeight;
	}

	public void setFlightWeight(Double flightWeight) {
		this.flightWeight = flightWeight;
	}

	@Column(name = "FLIGHT_AMOUNT", precision = 10)
	public Double getFlightAmount() {
		return this.flightAmount;
	}

	public void setFlightAmount(Double flightAmount) {
		this.flightAmount = flightAmount;
	}

	@Column(name = "BOARD_AMOUNT", precision = 10)
	public Double getBoardAmount() {
		return this.boardAmount;
	}

	public void setBoardAmount(Double boardAmount) {
		this.boardAmount = boardAmount;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "PRICE", precision = 10)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "IS_LOWEST_STATUS", precision = 22, scale = 0)
	public Long getIsLowestStatus() {
		return this.isLowestStatus;
	}

	public void setIsLowestStatus(Long isLowestStatus) {
		this.isLowestStatus = isLowestStatus;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DIFF_WEIGHT", precision = 10)
	public Double getDiffWeight() {
		return this.diffWeight;
	}

	public void setDiffWeight(Double diffWeight) {
		this.diffWeight = diffWeight;
	}

	@Column(name = "DIFF_AMOUNT", precision = 10)
	public Double getDiffAmount() {
		return this.diffAmount;
	}

	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}

	@Column(name = "STARTCITY", length = 50)
	public String getStartcity() {
		return this.startcity;
	}

	public void setStartcity(String startcity) {
		this.startcity = startcity;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "REVIEW_DEPT", length = 50)
	public String getReviewDept() {
		return this.reviewDept;
	}

	public void setReviewDept(String reviewDept) {
		this.reviewDept = reviewDept;
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

	@Column(name = "REVIEW_REMARK", length = 500)
	public String getReviewRemark() {
		return this.reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "BATCH_NO", precision = 10)
	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "COMPANY_CODE", length = 50)
	public String getCompanyCose() { 
		return companyCose;
	}

	public void setCompanyCose(String companyCose) {
		this.companyCose = companyCose;
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

	@Column(name = "PAY_STATUS", precision = 10)
	public Long getPayStatus() {  
		return payStatus;
	}

	public void setPayStatus(Long payStatus) {
		this.payStatus = payStatus;
	}

	@Column(name = "GOODS_TYPE", length = 100)
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	@Column(name = "FAX_ID")
	public String  getFaxId() {
		return faxId;
	}

	public void setFaxId(String  faxId) {
		this.faxId = faxId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "PAY_TIME")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime= payTime;
	}

	@Column(name = "PAY_USER", length = 20)
	public String getPayUser() {
		return payUser;
	}

	public void setPayUser(String payUser) {
		this.payUser = payUser;
	}
	
	
}