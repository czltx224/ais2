package com.xbwl.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * FiDeliverycostExcel entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_DELIVERYCOST_EXCEL", schema = "AISUSER")
public class FiDeliverycostExcel implements java.io.Serializable {

	// Fields

	private Long id; 
	private String excelCompany;  ///excel提货点
	private String excelNo;  //    excel单号
	private Double excelWeight;//excel重量
	private Double excelBanFee;//excel板费
	private Double excelAmount;//excel金额
	private String faxMainNo;//黄单号
	private Double faxWeight;//黄单重量
	private Double faxAmount;//黄单金额
	private Long batchNo;//板费
	private Long status;///对账状态
	private String wrongInfo;//  提示信息
	private String fidTs;
	// Constructors

	/** default constructor */
	public FiDeliverycostExcel() {
	}

	/** minimal constructor */
	public FiDeliverycostExcel(Long id) {
		this.id = id;
	}

	/** full constructor */
	public FiDeliverycostExcel(Long id, String excelCompany, String excelNo,
			Double excelWeight, Double excelBanFee, Double excelAmount,
			String faxMainNo, Double faxWeight, Double faxAmount,
			Long batchNo, Long status, String wrongInfo) {
		this.id = id;
		this.excelCompany = excelCompany;
		this.excelNo = excelNo;
		this.excelWeight = excelWeight;
		this.excelBanFee = excelBanFee;
		this.excelAmount = excelAmount;
		this.faxMainNo = faxMainNo;
		this.faxWeight = faxWeight;
		this.faxAmount = faxAmount;
		this.batchNo = batchNo;
		this.status = status;
		this.wrongInfo = wrongInfo;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_DELIVERYCOST_EXCEL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "EXCEL_COMPANY", length = 50)
	public String getExcelCompany() {
		return this.excelCompany;
	}

	public void setExcelCompany(String excelCompany) {
		this.excelCompany = excelCompany;
	}

	@Column(name = "EXCEL_NO", length = 50)
	public String getExcelNo() {
		return this.excelNo;
	}

	public void setExcelNo(String excelNo) {
		this.excelNo = excelNo;
	}

	@Column(name = "EXCEL_WEIGHT", precision = 10)
	public Double getExcelWeight() {
		return this.excelWeight;
	}

	public void setExcelWeight(Double excelWeight) {
		this.excelWeight = excelWeight;
	}

	@Column(name = "EXCEL_BAN_FEE", precision = 10)
	public Double getExcelBanFee() {
		return this.excelBanFee;
	}

	public void setExcelBanFee(Double excelBanFee) {
		this.excelBanFee = excelBanFee;
	}

	@Column(name = "EXCEL_AMOUNT", precision = 10)
	public Double getExcelAmount() {
		return this.excelAmount;
	}

	public void setExcelAmount(Double excelAmount) {
		this.excelAmount = excelAmount;
	}

	@Column(name = "FAX_MAIN_NO", length = 50)
	public String getFaxMainNo() {
		return this.faxMainNo;
	}

	public void setFaxMainNo(String faxMainNo) {
		this.faxMainNo = faxMainNo;
	}

	@Column(name = "FAX_WEIGHT", precision = 10)
	public Double getFaxWeight() {
		return this.faxWeight;
	}

	public void setFaxWeight(Double faxWeight) {
		this.faxWeight = faxWeight;
	}

	@Column(name = "FAX_AMOUNT", precision = 10)
	public Double getFaxAmount() {
		return this.faxAmount;
	}

	public void setFaxAmount(Double faxAmount) {
		this.faxAmount = faxAmount;
	}

	@Column(name = "BATCH_NO", precision = 22, scale = 0)
	public Long getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "WRONG_INFO", length = 500)
	public String getWrongInfo() {
		return this.wrongInfo;
	}

	public void setWrongInfo(String wrongInfo) {
		this.wrongInfo= wrongInfo;
	}

	@Column(name = "FID_TS", length = 50)
	public String getFidTs() {
		return fidTs;
	}

	public void setFidTs(String fidTs) {
		this.fidTs = fidTs;
	}

}