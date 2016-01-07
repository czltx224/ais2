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
 * FiReceipt entity.
 * 收据实体类
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_RECEIPT")
public class FiReceipt implements java.io.Serializable,AuditableEntity {
	private Long id;
	private String receiptNo;   //收据单号
	private Date receiptData;   //收据时间
	private String remark;   // 摘要
	private Double amount;   //金额
	private Long departId;         //业务部门
	private String departName;
	private Long fiPaidId;     //实配单号
	private String createName;                   
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private Long delStatus=1l;       //作废状态 0：作废，1：正常
	private String delName;      //作废人
	private Date delTime;           //作废时间
	private Long printNum=0l;           //打印次数
	private String printName;         //打印人
	private Date printDate;              //打印时间
	private String ts;
	private String sourceData; //数据来源(必输参数)
	private Long sourceNo; //来源单号(必输参数)

	// Constructors

	/** default constructor */
	public FiReceipt() {
	}

	/** minimal constructor */
	public FiReceipt(Long id) {
		this.id = id;
	}

	/** full constructor */
	public FiReceipt(Long id, String receiptNo, Date receiptData,
			String remark, Double amount, Long departId, String departName,
			Long fiPaidId, String createName, Date createTime,
			String updateName, Date updateTime, Long delStatus, String delName,
			Date delTime, Long printNum, String printName, Date printDate,
			String ts) {
		this.id = id;
		this.receiptNo = receiptNo;
		this.receiptData = receiptData;
		this.remark = remark;
		this.amount = amount;
		this.departId = departId;
		this.departName = departName;
		this.fiPaidId = fiPaidId;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.delStatus = delStatus;
		this.delName = delName;
		this.delTime = delTime;
		this.printNum = printNum;
		this.printName = printName;
		this.printDate = printDate;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_RECEIPT")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "RECEIPT_NO", length = 20)
	public String getReceiptNo() {
		return this.receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "RECEIPT_DATA", length = 7)
	public Date getReceiptData() {
		return this.receiptData;
	}

	public void setReceiptData(Date receiptData) {
		this.receiptData = receiptData;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	@Column(name = "FI_PAID_ID", precision = 22, scale = 0)
	public Long getFiPaidId() {
		return this.fiPaidId;
	}

	public void setFiPaidId(Long fiPaidId) {
		this.fiPaidId = fiPaidId;
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

	@Column(name = "DEL_STATUS", precision = 1, scale = 0)
	public Long getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(Long delStatus) {
		this.delStatus = delStatus;
	}

	@Column(name = "DEL_NAME", length = 20)
	public String getDelName() {
		return this.delName;
	}

	public void setDelName(String delName) {
		this.delName = delName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "DEL_TIME", length = 7)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@Column(name = "PRINT_NUM")
	public Long getPrintNum() {
		return this.printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}

	@Column(name = "PRINT_NAME", length = 20)
	public String getPrintName() {
		return this.printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "PRINT_DATE", length = 7)
	public Date getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	

	@Column(name = "SOURCE_DATA", length = 20)
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

}