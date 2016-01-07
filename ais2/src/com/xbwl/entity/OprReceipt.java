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
 * OprReceipt entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_RECEIPT")
public class OprReceipt implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;// 序号
	private Long dno;// 配送单号
	private Long printNo;// 打印单据号
	private Long printNum;// 打印次数
	private String receiptType;// 回单类型
	private Long reachStatus;// 拆单状态
	private Long reachNum;// 拆单份数
	private String reachMan;// 拆担人
	private Date reachTime;// 拆单时间
	private Long getStatus;// 领单状态
	private String getMan;// 领单人
	private Date getTime;// 领单时间
	private Long confirmStatus;// 回单确认状态
	private Long confirmNum;// 确收份数
	private String confirmMan;// 确收人
	private Date confirmTime;// 确收时间
	private String confirmRemark;// 确收备注
	private Long outStatus;// 回单寄出状态
	private String outWay;// 回单寄出途径 来自数据字典，主要包括 ，传真，邮寄，快递等等
	private String outNo;// 回单寄出单号
	private String outMan;// 回单寄出人
	private Date outTime;// 回单寄出时间
	private String outCompany;// 回单寄出公司
	private Double outCost;// 回单寄出费用
	private Long scanStauts;// 回单寄出状态
	private String scanMan;// 回单扫描人
	private Date scanTime;// 回单扫描时间
	private String scanAddr;// 回单扫描地址
	private String createName;// 创建人
	private Date createTime;// 创建时间
	private String updateName;// 修改人
	private Date updateTime;// 修改时间
	private String ts;// 时间戳
	private String curStatus;// 回单当前状态

	private Long getNum;// 领单份数
	private String addMan;// 回单补签人
	private Date addTime;// 回单补签时间
	private Date lastPrintName;// 最后打印人
	private Date lastPrintTime;// 最后打印时间

	private Long outStockStatus;// 回单出库状态
	private String outStockMan;// 回单出库出库人
	private Date outStockTime;// 回单出库时间
	private Long outStockNum;//回单出库份数
	
	private Integer scanNum;  //回单扫描份数

	// Constructors

	/** default constructor */
	public OprReceipt() {
	}

	/** minimal constructor */
	public OprReceipt(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprReceipt(Long id, Long dno, Long printNo, Long printNum,
			String receiptType, Long reachStatus, Long reachNum,
			String reachMan, Date reachTime, Long getStatus, String getMan,
			Date getTime, Long confirmStatus, Long confirmNum,
			String confirmMan, Date confirmTime, String confirmRemark,
			Long outStatus, String outWay, String outNo, String outMan,
			Date outTime, String outCompany, Double outCost, Long scanStauts,
			String scanMan, Date scanTime, String scanAddr, String createName,
			Date createTime, String updateName, Date updateTime, String ts,
			String curStatus) {
		super();
		this.id = id;
		this.dno = dno;
		this.printNo = printNo;
		this.printNum = printNum;
		this.receiptType = receiptType;
		this.reachStatus = reachStatus;
		this.reachNum = reachNum;
		this.reachMan = reachMan;
		this.reachTime = reachTime;
		this.getStatus = getStatus;
		this.getMan = getMan;
		this.getTime = getTime;
		this.confirmStatus = confirmStatus;
		this.confirmNum = confirmNum;
		this.confirmMan = confirmMan;
		this.confirmTime = confirmTime;
		this.confirmRemark = confirmRemark;
		this.outStatus = outStatus;
		this.outWay = outWay;
		this.outNo = outNo;
		this.outMan = outMan;
		this.outTime = outTime;
		this.outCompany = outCompany;
		this.outCost = outCost;
		this.scanStauts = scanStauts;
		this.scanMan = scanMan;
		this.scanTime = scanTime;
		this.scanAddr = scanAddr;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.curStatus = curStatus;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_RECEIPT ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", precision = 10, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "PRINT_NO", precision = 10, scale = 0)
	public Long getPrintNo() {
		return this.printNo;
	}

	public void setPrintNo(Long printNo) {
		this.printNo = printNo;
	}

	@Column(name = "PRINT_NUM", precision = 2, scale = 0)
	public Long getPrintNum() {
		return this.printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}

	@Column(name = "RECEIPT_TYPE", length = 20)
	public String getReceiptType() {
		return this.receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	@Column(name = "REACH_STATUS", precision = 1, scale = 0)
	public Long getReachStatus() {
		return this.reachStatus;
	}

	public void setReachStatus(Long reachStatus) {
		this.reachStatus = reachStatus;
	}

	@Column(name = "REACH_NUM", precision = 2, scale = 0)
	public Long getReachNum() {
		return this.reachNum;
	}

	public void setReachNum(Long reachNum) {
		this.reachNum = reachNum;
	}

	@Column(name = "REACH_MAN", length = 20)
	public String getReachMan() {
		return this.reachMan;
	}

	public void setReachMan(String reachMan) {
		this.reachMan = reachMan;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "REACH_TIME", length = 7)
	public Date getReachTime() {
		return this.reachTime;
	}

	public void setReachTime(Date reachTime) {
		this.reachTime = reachTime;
	}

	@Column(name = "GET_STATUS", precision = 1, scale = 0)
	public Long getGetStatus() {
		return this.getStatus;
	}

	public void setGetStatus(Long getStatus) {
		this.getStatus = getStatus;
	}

	@Column(name = "GET_MAN", length = 20)
	public String getGetMan() {
		return this.getMan;
	}

	public void setGetMan(String getMan) {
		this.getMan = getMan;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "GET_TIME", length = 7)
	public Date getGetTime() {
		return this.getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	@Column(name = "CONFIRM_STATUS", precision = 1, scale = 0)
	public Long getConfirmStatus() {
		return this.confirmStatus;
	}

	public void setConfirmStatus(Long confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	@Column(name = "CONFIRM_NUM", precision = 2, scale = 0)
	public Long getConfirmNum() {
		return this.confirmNum;
	}

	public void setConfirmNum(Long confirmNum) {
		this.confirmNum = confirmNum;
	}

	@Column(name = "CONFIRM_MAN", length = 20)
	public String getConfirmMan() {
		return this.confirmMan;
	}

	public void setConfirmMan(String confirmMan) {
		this.confirmMan = confirmMan;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CONFIRM_TIME", length = 7)
	public Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	@Column(name = "CONFIRM_REMARK", length = 200)
	public String getConfirmRemark() {
		return this.confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}

	@Column(name = "OUT_STATUS", precision = 1, scale = 0)
	public Long getOutStatus() {
		return this.outStatus;
	}

	public void setOutStatus(Long outStatus) {
		this.outStatus = outStatus;
	}

	@Column(name = "OUT_WAY", length = 20)
	public String getOutWay() {
		return this.outWay;
	}

	public void setOutWay(String outWay) {
		this.outWay = outWay;
	}

	@Column(name = "OUT_NO", length = 50)
	public String getOutNo() {
		return this.outNo;
	}

	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}

	@Column(name = "OUT_MAN", length = 20)
	public String getOutMan() {
		return outMan;
	}

	public void setOutMan(String outMan) {
		this.outMan = outMan;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "OUT_TIME", length = 7)
	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	@Column(name = "OUT_COMPANY", length = 200)
	public String getOutCompany() {
		return this.outCompany;
	}

	public void setOutCompany(String outCompany) {
		this.outCompany = outCompany;
	}

	@Column(name = "OUT_COST", precision = 8)
	public Double getOutCost() {
		return this.outCost;
	}

	public void setOutCost(Double outCost) {
		this.outCost = outCost;
	}

	@Column(name = "SCAN_STAUTS", precision = 1, scale = 0)
	public Long getScanStauts() {
		return this.scanStauts;
	}

	public void setScanStauts(Long scanStauts) {
		this.scanStauts = scanStauts;
	}

	@Column(name = "SCAN_MAN", length = 20)
	public String getScanMan() {
		return this.scanMan;
	}

	public void setScanMan(String scanMan) {
		this.scanMan = scanMan;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "SCAN_TIME", length = 7)
	public Date getScanTime() {
		return this.scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	@Column(name = "SCAN_ADDR")
	public String getScanAddr() {
		return this.scanAddr;
	}

	public void setScanAddr(String scanAddr) {
		this.scanAddr = scanAddr;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
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

	@JSON(format = "yyyy-MM-dd")
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

	@Column(name = "CUR_STATUS", length = 20)
	public String getCurStatus() {
		return this.curStatus;
	}

	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	@Column(name = "GET_NUM")
	public Long getGetNum() {
		return getNum;
	}

	public void setGetNum(Long getNum) {
		this.getNum = getNum;
	}

	@Column(name = "ADD_MAN")
	public String getAddMan() {
		return addMan;
	}

	public void setAddMan(String addMan) {
		this.addMan = addMan;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "ADD_TIME")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name = "LAST_PRINT_NAME")
	public Date getLastPrintName() {
		return lastPrintName;
	}

	public void setLastPrintName(Date lastPrintName) {
		this.lastPrintName = lastPrintName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "LAST_PRINT_TIME")
	public Date getLastPrintTime() {
		return lastPrintTime;
	}

	public void setLastPrintTime(Date lastPrintTime) {
		this.lastPrintTime = lastPrintTime;
	}

	@Column(name = "OUT_STOCK_STATUS")
	public Long getOutStockStatus() {
		return outStockStatus;
	}

	public void setOutStockStatus(Long outStockStatus) {
		this.outStockStatus = outStockStatus;
	}

	@Column(name = "OUT_STOCK_MAN")
	public String getOutStockMan() {
		return outStockMan;
	}

	public void setOutStockMan(String outStockMan) {
		this.outStockMan = outStockMan;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "OUT_STOCK_TIME")
	public Date getOutStockTime() {
		return outStockTime;
	}

	public void setOutStockTime(Date outStockTime) {
		this.outStockTime = outStockTime;
	}

	@Column(name="OUT_STOCK_NUM")
	public Long getOutStockNum() {
		return outStockNum;
	}

	public void setOutStockNum(Long outStockNum) {
		this.outStockNum = outStockNum;
	}

	@Column(name="SCAN_NUM")
	public Integer getScanNum() {
		return scanNum;
	}

	public void setScanNum(Integer scanNum) {
		this.scanNum = scanNum;
	}

}