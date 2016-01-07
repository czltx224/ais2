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
import javax.persistence.UniqueConstraint;

import org.apache.struts2.json.annotations.JSON;

/**
 * OprFaxOut entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_FAX_OUT", uniqueConstraints = @UniqueConstraint(columnNames = "D_NO"))
public class OprFaxOut implements java.io.Serializable {

	// Fields

	private Long id;
	private Long dno;//配送单号
	private Long cusId;//代理公司客商编码
	private String cpName;//代理公司客商名称
	private String flightNo;//航班号
	private Date flightDate;//航班日期
	private String flightTime;//航班时间
	private String trafficMode;//运输方式
	private String flightMainNo;//航空主单号
	private String subNo;//分单号
	private String distributionMode;//配送方式
	private String takeMode;//提货方式
	private String receiptType;//回单类型
	private String consignee;//收货人姓名
	private String consigneeTel;//收货人电话和手机 
	private String city;//收货人所在市
	private String town;//收货人所在区或者县
	private String addr;//收货人地址
	private Long piece;//件数
	private Double cqWeight;//代理重量
	private Double cusWeight;//计费重量
	private Double bulk;//体积
	private Long inDepartId;
	private String inDepart;//录单部门
	private Long curDepartId;
	private String curDepart;//当前部门
	private Long endDepartId;
	private String endDepart;//终端部门
	private String gowhere;//供应商
	private Long distributionDepartId;
	private String distributionDepart;//配送部门
	private Long greenChannel;//绿色通道
	private Long urgentService;//加急
	private Long wait;//等通知放货
	private Long sonderzug;//是否专车
	private String carType;//专车类型
	private String roadType;//专车路型
	private String remark;//备注
	private Long status;//状态
	private String barCode;//单据号
	private Double paymentCollection;//代收货款
	private Double traFee;//中转费
	private Double traFeeRate;//中转费率
	private Double cpRate;//代理应付费率
	private Double cpFee;//代理应付费
	private Double consigneeRate;//收货人应付费率
	private Double consigneeFee;//收货人应付费
	private String whoCash;//费用付款方
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long faxMainId;//代理传真主单号
	private String customerService;//客服员
	private Double cusValueAddFee;//客户增值服务费
	private Double cpValueAddFee;//增值服务费总额
	private String goodsStatus;//货物最新状态
	private Double declaredValue;//货物价值声明
	private String goods;
	private String valuationType;//计价方式
	private Double sonderzugPrice;//专车费
	private String areaType;//收货人地址类型
	private String street;//收货人所在街道或镇
	private String areaRank;
	private Double normTraRate;//标准中转费率
	private Double normCpRate;//标准代理应付费率
	private Double normConsigneeRate;//标准收货人应付费率
	private Double normSonderzugPrice;//标准专车价格
	
	private String cusDepartName;
	private String cusDepartCode;//客服员所在部门编码
	private String realGoWhere;//实际去向
	private Long goWhereId;//供应商ID
	
	private Double cpSonderzugPrice;//预付专车费
	private String request;
	private Long consignId;
	private String changeName;
	private Date changeTime;

	private String ediUserId;//EDI对应客商ID(ct_user.user_id)
	private Date outTime;//发车出库时间
	// Constructors

	/** default constructor */
	public OprFaxOut() {
	}

	/** minimal constructor */
	public OprFaxOut(Long id) {
		this.id = id;
	}

	// Property accessors seq_opr_fax_out
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_FAX_OUT ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", unique = true, precision = 20, scale = 0)
	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "CUS_ID", precision = 22, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "CP_NAME", length = 100)
	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	@Column(name = "FLIGHT_NO", length = 200)
	public String getFlightNo() {
		return this.flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FLIGHT_DATE", length = 7)
	public Date getFlightDate() {
		return this.flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	@Column(name = "FLIGHT_TIME", length = 50)
	public String getFlightTime() {
		return this.flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	@Column(name = "TRAFFIC_MODE", length = 20)
	public String getTrafficMode() {
		return this.trafficMode;
	}

	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}

	@Column(name = "FLIGHT_MAIN_NO", length = 200)
	public String getFlightMainNo() {
		return this.flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	@Column(name = "SUB_NO", length = 200)
	public String getSubNo() {
		return this.subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	@Column(name = "DISTRIBUTION_MODE", length = 10)
	public String getDistributionMode() {
		return this.distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	@Column(name = "TAKE_MODE", length = 10)
	public String getTakeMode() {
		return this.takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	@Column(name = "RECEIPT_TYPE", length = 20)
	public String getReceiptType() {
		return this.receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	@Column(name = "CONSIGNEE", length = 200)
	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column(name = "CONSIGNEE_TEL", length = 200)
	public String getConsigneeTel() {
		return this.consigneeTel;
	}

	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel;
	}

	@Column(name = "CITY", length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "TOWN", length = 20)
	public String getTown() {
		return this.town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	@Column(name = "ADDR", length = 500)
	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "PIECE", precision = 7, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "CQ_WEIGHT", precision = 7, scale = 1)
	public Double getCqWeight() {
		return this.cqWeight;
	}

	public void setCqWeight(Double cqWeight) {
		this.cqWeight = cqWeight;
	}

	@Column(name = "CUS_WEIGHT", precision = 7, scale = 1)
	public Double getCusWeight() {
		return this.cusWeight;
	}

	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}

	@Column(name = "BULK", precision = 7)
	public Double getBulk() {
		return this.bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	@Column(name = "IN_DEPART", length = 20)
	public String getInDepart() {
		return this.inDepart;
	}

	public void setInDepart(String inDepart) {
		this.inDepart = inDepart;
	}

	@Column(name = "CUR_DEPART", length = 20)
	public String getCurDepart() {
		return this.curDepart;
	}

	public void setCurDepart(String curDepart) {
		this.curDepart = curDepart;
	}

	@Column(name = "END_DEPART", length = 20)
	public String getEndDepart() {
		return this.endDepart;
	}

	public void setEndDepart(String endDepart) {
		this.endDepart = endDepart;
	}

	@Column(name = "GOWHERE", length = 200)
	public String getGowhere() {
		return this.gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	@Column(name = "DISTRIBUTION_DEPART", length = 20)
	public String getDistributionDepart() {
		return this.distributionDepart;
	}

	public void setDistributionDepart(String distributionDepart) {
		this.distributionDepart = distributionDepart;
	}

	@Column(name = "GREEN_CHANNEL", precision = 22, scale = 0)
	public Long getGreenChannel() {
		return this.greenChannel;
	}

	public void setGreenChannel(Long greenChannel) {
		this.greenChannel = greenChannel;
	}

	@Column(name = "URGENT_SERVICE", precision = 22, scale = 0)
	public Long getUrgentService() {
		return this.urgentService;
	}

	public void setUrgentService(Long urgentService) {
		this.urgentService = urgentService;
	}

	@Column(name = "WAIT", precision = 22, scale = 0)
	public Long getWait() {
		return this.wait;
	}

	public void setWait(Long wait) {
		this.wait = wait;
	}

	@Column(name = "SONDERZUG", precision = 22, scale = 0)
	public Long getSonderzug() {
		return this.sonderzug;
	}

	public void setSonderzug(Long sonderzug) {
		this.sonderzug = sonderzug;
	}

	@Column(name = "CAR_TYPE", length = 20)
	public String getCarType() {
		return this.carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "BAR_CODE", length = 20)
	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Column(name = "PAYMENT_COLLECTION", precision = 10)
	public Double getPaymentCollection() {
		return this.paymentCollection;
	}

	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	@Column(name = "TRA_FEE", precision = 10)
	public Double getTraFee() {
		return this.traFee;
	}

	public void setTraFee(Double traFee) {
		this.traFee = traFee;
	}

	@Column(name = "TRA_FEE_RATE", precision = 10)
	public Double getTraFeeRate() {
		return this.traFeeRate;
	}

	public void setTraFeeRate(Double traFeeRate) {
		this.traFeeRate = traFeeRate;
	}

	@Column(name = "CP_RATE", precision = 10)
	public Double getCpRate() {
		return this.cpRate;
	}

	public void setCpRate(Double cpRate) {
		this.cpRate = cpRate;
	}

	@Column(name = "CP_FEE", precision = 10)
	public Double getCpFee() {
		return this.cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	@Column(name = "CONSIGNEE_RATE", precision = 10)
	public Double getConsigneeRate() {
		return this.consigneeRate;
	}

	public void setConsigneeRate(Double consigneeRate) {
		this.consigneeRate = consigneeRate;
	}

	@Column(name = "CONSIGNEE_FEE", precision = 10)
	public Double getConsigneeFee() {
		return this.consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	@Column(name = "WHO_CASH", length = 20)
	public String getWhoCash() {
		return this.whoCash;
	}

	public void setWhoCash(String whoCash) {
		this.whoCash = whoCash;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME")
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "FAX_MAIN_ID", precision = 22, scale = 0)
	public Long getFaxMainId() {
		return this.faxMainId;
	}

	public void setFaxMainId(Long faxMainId) {
		this.faxMainId = faxMainId;
	}

	@Column(name = "CUSTOMER_SERVICE", length = 20)
	public String getCustomerService() {
		return this.customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}

	@Column(name = "CUS_VALUE_ADD_FEE", precision = 10)
	public Double getCusValueAddFee() {
		return this.cusValueAddFee;
	}

	public void setCusValueAddFee(Double cusValueAddFee) {
		this.cusValueAddFee = cusValueAddFee;
	}

	@Column(name = "CP_VALUE_ADD_FEE", precision = 10)
	public Double getCpValueAddFee() {
		return this.cpValueAddFee;
	}

	public void setCpValueAddFee(Double cpValueAddFee) {
		this.cpValueAddFee = cpValueAddFee;
	}

	@Column(name = "GOODS_STATUS", length = 20)
	public String getGoodsStatus() {
		return this.goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	@Column(name = "DECLARED_VALUE", precision = 22, scale = 0)
	/**
	 * @return the declaredValue
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}

	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	@Column(name = "GOODS", length = 200)
	public String getGoods() {
		return this.goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	@Column(name = "VALUATION_TYPE", length = 10)
	public String getValuationType() {
		return this.valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	@Column(name = "SONDERZUG_PRICE", precision = 10)
	public Double getSonderzugPrice() {
		return this.sonderzugPrice;
	}

	public void setSonderzugPrice(Double sonderzugPrice) {
		this.sonderzugPrice = sonderzugPrice;
	}

	@Column(name = "AREA_TYPE", length = 20)
	public String getAreaType() {
		return this.areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	@Column(name = "AREA_RANK", length = 10)
	public String getAreaRank() {
		return this.areaRank;
	}

	public void setAreaRank(String areaRank) {
		this.areaRank = areaRank;
	}

	@Column(name = "STREET", length = 100)
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "ROAD_TYPE", length = 20)
	public String getRoadType() {
		return this.roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	@Column(name = "IN_DEPART_ID", precision = 22, scale = 0)
	public Long getInDepartId() {
		return this.inDepartId;
	}

	public void setInDepartId(Long inDepartId) {
		this.inDepartId = inDepartId;
	}

	@Column(name = "CUR_DEPART_ID", precision = 22, scale = 0)
	public Long getCurDepartId() {
		return this.curDepartId;
	}

	public void setCurDepartId(Long curDepartId) {
		this.curDepartId = curDepartId;
	}

	@Column(name = "END_DEPART_ID", precision = 22, scale = 0)
	public Long getEndDepartId() {
		return this.endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	@Column(name = "DISTRIBUTION_DEPART_ID", precision = 22, scale = 0)
	public Long getDistributionDepartId() {
		return this.distributionDepartId;
	}

	public void setDistributionDepartId(Long distributionDepartId) {
		this.distributionDepartId = distributionDepartId;
	}

	@Column(name = "CUS_DEPART_NAME", length = 50)
	public String getCusDepartName() {
		return this.cusDepartName;
	}

	public void setCusDepartName(String cusDepartName) {
		this.cusDepartName = cusDepartName;
	}

	@Column(name = "NORM_SONDERZUG_PRICE", precision = 10)
	public Double getNormSonderzugPrice() {
		return this.normSonderzugPrice;
	}

	public void setNormSonderzugPrice(Double normSonderzugPrice) {
		this.normSonderzugPrice = normSonderzugPrice;
	}

	@Column(name = "NORM_CP_RATE", precision = 10)
	public Double getNormCpRate() {
		return this.normCpRate;
	}

	public void setNormCpRate(Double normCpRate) {
		this.normCpRate = normCpRate;
	}

	@Column(name = "NORM_CONSIGNEE_RATE", precision = 10)
	public Double getNormConsigneeRate() {
		return this.normConsigneeRate;
	}

	public void setNormConsigneeRate(Double normConsigneeRate) {
		this.normConsigneeRate = normConsigneeRate;
	}

	@Column(name = "NORM_TRA_RATE", precision = 22, scale = 0)
	/**
	 * @return the normTraRate
	 */
	public Double getNormTraRate() {
		return normTraRate;
	}

	/**
	 * @param normTraRate the normTraRate to set
	 */
	public void setNormTraRate(Double normTraRate) {
		this.normTraRate = normTraRate;
	}

	@Column(name = "CUS_DEPART_CODE", length = 20)
	public String getCusDepartCode() {
		return this.cusDepartCode;
	}

	public void setCusDepartCode(String cusDepartCode) {
		this.cusDepartCode = cusDepartCode;
	}

	@Column(name = "REAL_GO_WHERE", length = 100)
	public String getRealGoWhere() {
		return this.realGoWhere;
	}

	public void setRealGoWhere(String realGoWhere) {
		this.realGoWhere = realGoWhere;
	}

	@Column(name = "GOWHERE_ID", precision = 22, scale = 0)
	/**
	 * @return the goWhereId
	 */
	public Long getGoWhereId() {
		return goWhereId;
	}

	/**
	 * @param goWhereId the goWhereId to set
	 */
	public void setGoWhereId(Long goWhereId) {
		this.goWhereId = goWhereId;
	}

	@Column(name = "CP_SONDERZUG_PRICE", precision = 10)
	public Double getCpSonderzugPrice() {
		return this.cpSonderzugPrice;
	}

	public void setCpSonderzugPrice(Double cpSonderzugPrice) {
		this.cpSonderzugPrice = cpSonderzugPrice;
	}

	@Column(name = "REQUEST", length = 500)
	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@Column(name = "CONSIGN_ID", precision = 22, scale = 0)
	public Long getConsignId() {
		return this.consignId;
	}

	public void setConsignId(Long consignId) {
		this.consignId = consignId;
	}

	@Column(name = "CHANGE_NAME", length = 50)
	public String getChangeName() {
		return this.changeName;
	}

	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CHANGE_TIME")
	public Date getChangeTime() {
		return this.changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	
	@Column(name = "EDI_USER_ID", length = 30)
	public String getEdiUserId() {
		return ediUserId;
	}

	public void setEdiUserId(String ediUserId) {
		this.ediUserId = ediUserId;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "OUT_TIME")
	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
}