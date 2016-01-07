package com.xbwl.finance.dto.impl;

import java.util.Date;

public class FiInterfaceProDto {
	private Long preCustomerId; //客商ID(更改前)
	private Long customerId;// 客商ID(更改后)
	private String customerName;// 客商名称
	private String distributionMode;// 配送方式(新邦/中转/外发)
	private Long dno;// 配送单号(必输参数)
	private Long settlementType;//收付类型(1:存款/收款、2:取款/付款)(必输参数)
	private String documentsType; //单据大类:收入\成本\对账\预存款\代收货款(必输参数)
	private String documentsSmalltype; //单据小类：配送单/对账单/配送单/预存款单(必输参数)
	private Long documentsNo;//单据号，单据小类对应的单号
	private String collectionUser; //收款责任人:自提：创建人，送货：送货员，外发：外发员(必输参数)
	private double amount;// 金额(必输参数)
	private String costType;// 费用类型：专车费/增值服务费/代理应付费/代收货款/到付提送费/到付增值费/其它收入/对账/中转费(必输参数)
	private String penyType; //结算类型(现结、月结)(必输参数)
	private Long departId; // 创建业务部门部门id(必输参数)
	private String departName;//创建业务部门名称
	private String sourceData;// 数据来源:中转成本(必输参数)
	private Long sourceNo;// 来源单号:中转成本ID(必输参数)
	
	private String outStockMode;//出库调用财务接口：出库方式(自提/中转/外发/送货/机场自提)
	private String contacts; //往来单位:没在客商档案中的客商，内部客户、车辆成本：收款司机。
	
	private String createRemark; //摘要
	
	//内部结算
	private Long startDepartId;//始发部门ID
	private String startDepartName; //始发部门
	private Long endDepartId; //终端部门ID
	private String endDepart;//终端部门
	
	//提货成本
	private String flightMainNo;//手动新增(主单号)
	private Double flightWeight;//黄单重量
	private Long flightPiece;//黄单件数
	private Double bulk;//体积
	private String flightNo;//航班号
	private String startcity;//始发站
	private String goodsType;//货物类型
	
	//更改申请
	//private Long incomeDepartId;//收入部门(录单部门ID)
	private Long disDepartId;//配送部门ID
	private double beforeAmount=0.0;//更改前金额
	private Long gocustomerId;// 客商ID(中转、外发客商ID)
	private String gocustomerName;// 客商名称(中转、外发客商)
	private Long stockStatus;//0：未出库，1：已出库
	
	
	//收入
	private String whoCash;//付款方
	private String customerService;//客服员
	private String admDepart;//客服员行政部门
	private Long admDepartId;//客服员行政部门Id
	private String incomeDepart;//收入部门
	private Long incomeDepartId;//收入部门
	
	
	//成本
	private String costType1;//成本大类
	private String costTypeDetail;  //成本小类
	

	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}

	public Long getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(Long settlementType) {
		this.settlementType = settlementType;
	}
	public String getDocumentsType() {
		return documentsType;
	}
	public void setDocumentsType(String documentsType) {
		this.documentsType = documentsType;
	}
	public String getDocumentsSmalltype() {
		return documentsSmalltype;
	}
	public void setDocumentsSmalltype(String documentsSmalltype) {
		this.documentsSmalltype = documentsSmalltype;
	}
	public String getCollectionUser() {
		return collectionUser;
	}
	public void setCollectionUser(String collectionUser) {
		this.collectionUser = collectionUser;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public String getSourceData() {
		return sourceData;
	}
	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}
	public Long getSourceNo() {
		return sourceNo;
	}
	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}
	public Long getDocumentsNo() {
		return documentsNo;
	}
	public void setDocumentsNo(Long documentsNo) {
		this.documentsNo = documentsNo;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDistributionMode() {
		return distributionMode;
	}
	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}
	public String getOutStockMode() {
		return outStockMode;
	}
	public void setOutStockMode(String outStockMode) {
		this.outStockMode = outStockMode;
	}
	public String getCreateRemark() {
		return createRemark;
	}
	public void setCreateRemark(String createRemark) {
		this.createRemark = createRemark;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public Long getStartDepartId() {
		return startDepartId;
	}
	public void setStartDepartId(Long startDepartId) {
		this.startDepartId = startDepartId;
	}
	public String getStartDepartName() {
		return startDepartName;
	}
	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}
	public Long getEndDepartId() {
		return endDepartId;
	}
	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}
	public String getEndDepart() {
		return endDepart;
	}
	public void setEndDepart(String endDepart) {
		this.endDepart = endDepart;
	}
	public String getFlightMainNo() {
		return flightMainNo;
	}
	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}
	public Double getFlightWeight() {
		return flightWeight;
	}
	public void setFlightWeight(Double flightWeight) {
		this.flightWeight = flightWeight;
	}
	public Long getFlightPiece() {
		return flightPiece;
	}
	public void setFlightPiece(Long flightPiece) {
		this.flightPiece = flightPiece;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getStartcity() {
		return startcity;
	}
	public void setStartcity(String startcity) {
		this.startcity = startcity;
	}
	public Long getGocustomerId() {
		return gocustomerId;
	}
	public void setGocustomerId(Long gocustomerId) {
		this.gocustomerId = gocustomerId;
	}
	public String getGocustomerName() {
		return gocustomerName;
	}
	public void setGocustomerName(String gocustomerName) {
		this.gocustomerName = gocustomerName;
	}
	public Long getStockStatus() {
		return stockStatus;
	}
	public void setStockStatus(Long stockStatus) {
		this.stockStatus = stockStatus;
	}
	public String getWhoCash() {
		return whoCash;
	}
	public void setWhoCash(String whoCash) {
		this.whoCash = whoCash;
	}
	public String getCustomerService() {
		return customerService;
	}
	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}
	public String getAdmDepart() {
		return admDepart;
	}
	public void setAdmDepart(String admDepart) {
		this.admDepart = admDepart;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public double getBeforeAmount() {
		return beforeAmount;
	}
	public void setBeforeAmount(double beforeAmount) {
		this.beforeAmount = beforeAmount;
	}
	public String getPenyType() {
		return penyType;
	}
	public void setPenyType(String penyType) {
		this.penyType = penyType;
	}
	public String getCostType1() {
		return costType1;
	}
	public void setCostType1(String costType1) {
		this.costType1 = costType1;
	}
	public String getCostTypeDetail() {
		return costTypeDetail;
	}
	public void setCostTypeDetail(String costTypeDetail) {
		this.costTypeDetail = costTypeDetail;
	}
	public Long getAdmDepartId() {
		return admDepartId;
	}
	public void setAdmDepartId(Long admDepartId) {
		this.admDepartId = admDepartId;
	}
	public String getIncomeDepart() {
		return incomeDepart;
	}
	public void setIncomeDepart(String incomeDepart) {
		this.incomeDepart = incomeDepart;
	}
	public Long getIncomeDepartId() {
		return incomeDepartId;
	}
	public void setIncomeDepartId(Long incomeDepartId) {
		this.incomeDepartId = incomeDepartId;
	}
	public Double getBulk() {
		return bulk;
	}
	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}
	public Long getDisDepartId() {
		return disDepartId;
	}
	public void setDisDepartId(Long disDepartId) {
		this.disDepartId = disDepartId;
	}
	public Long getPreCustomerId() {
		return preCustomerId;
	}
	public void setPreCustomerId(Long preCustomerId) {
		this.preCustomerId = preCustomerId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	
	
}
