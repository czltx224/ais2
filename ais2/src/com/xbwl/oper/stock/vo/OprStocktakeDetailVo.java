package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;


public class OprStocktakeDetailVo {

		private Long id;
		private Long stocktakeId;
		private Long dno;
		private Long piece;
		private Long realPiece;
		private Long departId;
		private String storageArea;
		private String createName;
		private Date createTime;
		private String updateName;
		private Date updateTime;
		private String ts;
		private String flightMainNo;
		private String subNo;
		private String flightNo;
		private String consignee;
		private String addr;
		private String  departName;
		private Long status;
		private double weight;
		private String goWhere;
		private String distributionMode;
		private String takeMode;
		private String cusName;
		// Constructors

		public OprStocktakeDetailVo(Long id, Long stocktakeId, Long dno,
				Long piece, Long realPiece, Long departId, String storageArea,
				String createName, Date createTime, String updateName,
				Date updateTime, String ts, String flightMainNo, String subNo,
				String flightNo, String consignee, String addr,
				String departName, Long status, double weight, String goWhere,
				String distributionMode, String takeMode, String cusName) {
			super();
			this.id = id;
			this.stocktakeId = stocktakeId;
			this.dno = dno;
			this.piece = piece;
			this.realPiece = realPiece;
			this.departId = departId;
			this.storageArea = storageArea;
			this.createName = createName;
			this.createTime = createTime;
			this.updateName = updateName;
			this.updateTime = updateTime;
			this.ts = ts;
			this.flightMainNo = flightMainNo;
			this.subNo = subNo;
			this.flightNo = flightNo;
			this.consignee = consignee;
			this.addr = addr;
			this.departName = departName;
			this.status = status;
			this.weight = weight;
			this.goWhere = goWhere;
			this.distributionMode = distributionMode;
			this.takeMode = takeMode;
			this.cusName = cusName;
		}

		/** default constructor */
		public OprStocktakeDetailVo() {
		}

		/** minimal constructor */
		public OprStocktakeDetailVo(Long id, Long dno, Long piece, Long departId) {
			this.id = id;
			this.dno = dno;
			this.piece = piece;
			this.departId = departId;
		}

		/** full constructor */
		 

		public Long getId() {
			return this.id;
		}

		public void setId(Long id) {
			this.id = id;
		}


		public Long getDno() {
			return this.dno;
		}

		public void setDno(Long dno) {
			this.dno = dno;
		}

		public Long getPiece() {
			return this.piece;
		}

		public void setPiece(Long piece) {
			this.piece = piece;
		}

		public Long getRealPiece() {
			return this.realPiece;
		}

		public void setRealPiece(Long realPiece) {
			this.realPiece = realPiece;
		}

		public Long getDepartId() {
			return this.departId;
		}

		public void setDepartId(Long departId) {
			this.departId = departId;
		}

		public String getStorageArea() {
			return this.storageArea;
		}

		public void setStorageArea(String storageArea) {
			this.storageArea = storageArea;
		}

		public String getCreateName() {
			return this.createName;
		}

		public void setCreateName(String createName) {
			this.createName = createName;
		}

		@JSON(format="yyyy-MM-dd")
		public Date getCreateTime() {
			return this.createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public String getUpdateName() {
			return this.updateName;
		}

		public void setUpdateName(String updateName) {
			this.updateName = updateName;
		}

		@JSON(format="yyyy-MM-dd")
		public Date getUpdateTime() {
			return this.updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public String getTs() {
			return this.ts;
		}

		public void setTs(String ts) {
			this.ts = ts;
		}

		public String getFlightMainNo() {
			return this.flightMainNo;
		}

		public void setFlightMainNo(String flightMainNo) {
			this.flightMainNo = flightMainNo;
		}

		public String getSubNo() {
			return this.subNo;
		}

		public void setSubNo(String subNo) {
			this.subNo = subNo;
		}

		public String getFlightNo() {
			return this.flightNo;
		}

		public void setFlightNo(String flightNo) {
			this.flightNo = flightNo;
		}

		public String getConsignee() {
			return this.consignee;
		}

		public void setConsignee(String consignee) {
			this.consignee = consignee;
		}

		public String getAddr() {
			return this.addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public String getDepartName() {
			return departName;
		}

		public void setDepartName(String departName) {
			this.departName = departName;
		}

		public Long getStocktakeId() {
			return stocktakeId;
		}

		public void setStocktakeId(Long stocktakeId) {
			this.stocktakeId = stocktakeId;
		}

		public Long getStatus() {
			return status;
		}

		public void setStatus(Long status) {
			this.status = status;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public String getGoWhereString() {
			return goWhere;
		}

		public void setGoWhereString(String goWhereString) {
			this.goWhere = goWhereString;
		}

		public String getDistributionMode() {
			return distributionMode;
		}

		public void setDistributionMode(String distributionMode) {
			this.distributionMode = distributionMode;
		}

		public String getTakeMode() {
			return takeMode;
		}

		public void setTakeMode(String takeMode) {
			this.takeMode = takeMode;
		}

		public String getCusName() {
			return cusName;
		}

		public void setCusName(String cusName) {
			this.cusName = cusName;
		}

	}
	
	
	

