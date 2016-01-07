package com.xbwl.oper.fax.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author shuw time Aug 
 * 8, 2011 10:57:26 AM
 */

public class OprFaxInQueryVo {
	private Long dno;
	private String consignee; // 收货人
	private Date startDate; // 收货开始时间
	private Date endDate; // 收货结束时间
	private String flightMainNo; // 运单号
	private String consigneePhone; // 收货人电话
	private String subNo; // 分单号
	private String addr; // 收货人地址
	private String flightNo; // 航班号
	private String cpName; // 代理名称
	private String customerService; // 客服员姓名
	private String distribution; // 配送方式
	private String goWhere; // 去向
	private Long signStatus; // 是否录签收

	private Long rightDepartId; // 权限部门

	private Long delStatus; // 作废状态
	private String doGoods; // 提货方式
	private Long goodsStatus; // 货物状态
	private Long goodsStatusTwo; // 货物状态
	private Long isNoticeStatus;// 是否通知预约
	private Long isUrgentStatus; // 是否加急
	private Long doMoneyStatus; // 收银状态
	private Long startDepartId; // 开单部门Id
	private Date faxInStartDate; // 录单日期开始时间
	private Date faxInEndDate; // 录单日期结束时间
	private Date doGoodStartDate; // 送货开始时间
	private Date doGoodEndDate; // 送货结束时间
	private Date doMoneyStartDate; // 收银开始时间
	private Date doMoneyEndDate; // 收银结束时间
	private Long sonderzug; // 是否专车
	private Long signDanStatus; // 签收单状态
	private Long isErrorStatus; // 是否异常
	private Long isDoDanStatus;// 是否开单
	private Long distributionDepartId; // 配送部门Id
	private Long isDoToStatus; // 是否点到
	private Long noticeGoodStatus; // 等通知放货
	private Long overmemo; // 是否配车
	private String serviceDepartName; // 客服部门名称
	private Long endDepartId; // 终端部门Id

	private String queryCondition; // 查询条件
	private String queryConditionSelect; // 查询条件

	// 页面显示的值
	private String cpname; // 代理客商名称
	private String goodsstatus; // 货物状态
	private String flightno; // 航班号
	private Date flightdate; // 航班日期
	private String flightdateString; // 航班日期字符串
	private String flightTime; // 航班时间 时分 字符串
	private String curdepart; // 当前部门
	private Long piece; // 传真件数
	private String subno; // 子单号
	private String consigneetel; // 收货人电话号码
	private String goods; // 货物名称
	private Double cusweight; // 计费重量
	private Double consigneefee; // 收货人应付费
	private Double cusvalueaddfee; // CUS_VALUE_ADD_FEE 预付费
	private Double cpvalueaddfee; // 增值服务费总额
	private String gowhere; // 供应商
	private String dismode; // 配送方式
	private Date createtime; // 录单时间
	private String takemode; // 自提方式
	private String customerservice; // 客服员
	private String receipttype; // 回单类型
	private Long urgentservice; // 是否加急
	private String curstatus; // 回单当前状态
	private Long stockpiece; // 库存件数
	private Double stockweight;
	private Date stockcreatetime; // 点到时间
	private Long nodeorder; // 货物状态节点
	private String cusadd; // 客商地址
	private String customerName; // 客商名称
	private Date confirmtime; // 回单客户确收时间
	private String confirmname; // 回单确收确认人
	private String flightmainno;
	private Date sumitDate; // 货物确收时间
	private String signsource; // 签收来源
	private Long status; // 是否删除
	private Long notifystatus; // 是否短信通知
	private Long confirmstatus; // 回单确认状态
	private Long cashstatus; // 收银确认状态
	private String cashName; // 收银员
	private Date cashTime; // 收银时间
	private Long fisurestatus; // 财务确认状态
	private Long indepartid; // 当前部门ID
	private Date dogooddate; // 送货时间
	private Long reachstatus; // 回单入库状态
	private Long getstatus; // 回单出库状态
	private Long outstatus; // 回单寄出状态
	private Long scanstatus; // 回单扫描状态
	private Long printnum; // 打印次数
	private Long distributiondepartid; // 配送部门ID
	private String distributiondepartname; // 配送部门ID
	private Long oreachstatus; // 点到状态
	private Long waitnotice; // 是否等通知放货
	private String reachName; // 点到人
	private Long osuoutstatus; // 出库状态 0未出库 1已经出库 2分批出库
	private Date osuoutstatustime; // 出库时间

	private Long isException; // 库存异常
	private String cusdepartname; // 当前部门
	private String oicreatename; // 录单人
	private Double paymentcollection; // 应收应付货款
	private String remark;
	private Date discreatetime;
	private String signman;
	private String replacesign;

	private String realSignMan;
	private String oicartype; // 车型
	private Date airportendtime;
	private Date airportstarttime; // 机场发车时间
	private Long airportstartstatus; // 机场发车状态
	private String carNoNum; //
	private String doStoreName; // 到仓库确认人
	private Long doStoreStatus; // 到仓库状态
	private Date doStoreTime;

	private String trafficmode; // 运输方式
	private String consigneeInfoString; // 收货人信息
	private String roadtype; // 路型
	private Double sonderzugprice; // 专车费
	private String city; // 市
	private String town; // 区县

	private Long returnStatus; // 返货状态
	private String street; // 街道
	private String valuationtype; // 计费方式
	private Double bulk; // 体积
	private Double cqweight; // 代理重量
	private String whocash; // 付款方
	private String realgowhere; // 去向
	private Double trafeerate; // 中转费率
	private Double trafee; // 中转费
	private Double cprate; // 预费率
	private Double cpfee; // 预付费
	private Double consigneerate;

	private String indepart;
	private String enddepart;
	private Long cusId; // 代理ID
	private Long gowhereId; // 供应商ID

	private Double cpSonderzugprice; // 预付专车费

	private Long isOprException; // 动作异常

	private Long phoneNoticeSign; // 是否短信录签收

	private Long totalCp; // 代理个数
	private Double totalYuFee; // 总预付款 专车除外
	private Double totalDaoFee; // 总到付款 专车除外
	private Double totalFee; // 总收入
	private Double dnoIncomeFee; // 每一票的收入
	private Double cashMoney; // 收银合计

	private Long traPayStatus; // 成本支付状态
	private String traPayTime; // 成本支付时间
	private Long fiTraAudit; // 成本审核状态

	private String goodStatusColor; // 综合查询行背景颜色
	private String collectMsg; // 综合查询汇总信息
	private String request; // 个性化要求
	private Long isFlyLate;// 航班是否延误，0为未延误（即正常），1为延误

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getCollectMsg() {
		return collectMsg;
	}

	public void setCollectMsg(String collectMsg) {
		this.collectMsg = collectMsg;
	}

	public Long getTraPayStatus() {
		return traPayStatus;
	}

	public void setTraPayStatus(Long traPayStatus) {
		this.traPayStatus = traPayStatus;
	}

	public String getTraPayTime() {
		return traPayTime;
	}

	public void setTraPayTime(String traPayTime) {
		this.traPayTime = traPayTime;
	}

	public Long getFiTraAudit() {
		return fiTraAudit;
	}

	public void setFiTraAudit(Long fiTraAudit) {
		this.fiTraAudit = fiTraAudit;
	}

	public Double getTotalYuFee() {
		return totalYuFee;
	}

	public void setTotalYuFee(Double totalYuFee) {
		this.totalYuFee = totalYuFee;
	}

	public Double getTotalDaoFee() {
		return totalDaoFee;
	}

	public void setTotalDaoFee(Double totalDaoFee) {
		this.totalDaoFee = totalDaoFee;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Double getCpfee() {
		return cpfee;
	}

	public void setCpfee(Double cpfee) {
		this.cpfee = cpfee;
	}

	public Double getCprate() {
		return cprate;
	}

	public void setCprate(Double cprate) {
		this.cprate = cprate;
	}

	public String getWhocash() {
		return whocash;
	}

	public void setWhocash(String whocash) {
		this.whocash = whocash;
	}

	public Double getCqweight() {
		return cqweight;
	}

	public void setCqweight(Double cqweight) {
		this.cqweight = cqweight;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	public String getValuationtype() {
		return valuationtype;
	}

	public void setValuationtype(String valuationtype) {
		this.valuationtype = valuationtype;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getConsigneeInfoString() {
		return consigneeInfoString;
	}

	public void setConsigneeInfoString(String consigneeInfoString) {
		this.consigneeInfoString = consigneeInfoString;
	}

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getCustomerService() {
		return customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public String getGoWhere() {
		return goWhere;
	}

	public void setGoWhere(String goWhere) {
		this.goWhere = goWhere;
	}

	public Long getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(Long signStatus) {
		this.signStatus = signStatus;
	}

	public Long getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Long delStatus) {
		this.delStatus = delStatus;
	}

	public String getDoGoods() {
		return doGoods;
	}

	public void setDoGoods(String doGoods) {
		this.doGoods = doGoods;
	}

	public Long getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(Long goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public Long getGoodsStatusTwo() {
		return goodsStatusTwo;
	}

	public void setGoodsStatusTwo(Long goodsStatusTwo) {
		this.goodsStatusTwo = goodsStatusTwo;
	}

	public Long getIsNoticeStatus() {
		return isNoticeStatus;
	}

	public void setIsNoticeStatus(Long isNoticeStatus) {
		this.isNoticeStatus = isNoticeStatus;
	}

	public Long getIsUrgentStatus() {
		return isUrgentStatus;
	}

	public void setIsUrgentStatus(Long isUrgentStatus) {
		this.isUrgentStatus = isUrgentStatus;
	}

	public Long getDoMoneyStatus() {
		return doMoneyStatus;
	}

	public void setDoMoneyStatus(Long doMoneyStatus) {
		this.doMoneyStatus = doMoneyStatus;
	}

	public Long getStartDepartId() {
		return startDepartId;
	}

	public void setStartDepartId(Long startDepartId) {
		this.startDepartId = startDepartId;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getFaxInStartDate() {
		return faxInStartDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setFaxInStartDate(Date faxInStartDate) {
		this.faxInStartDate = faxInStartDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getFaxInEndDate() {
		return faxInEndDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setFaxInEndDate(Date faxInEndDate) {
		this.faxInEndDate = faxInEndDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDoGoodStartDate() {
		return doGoodStartDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setDoGoodStartDate(Date doGoodStartDate) {
		this.doGoodStartDate = doGoodStartDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDoGoodEndDate() {
		return doGoodEndDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setDoGoodEndDate(Date doGoodEndDate) {
		this.doGoodEndDate = doGoodEndDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDoMoneyStartDate() {
		return doMoneyStartDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setDoMoneyStartDate(Date doMoneyStartDate) {
		this.doMoneyStartDate = doMoneyStartDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDoMoneyEndDate() {
		return doMoneyEndDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setDoMoneyEndDate(Date doMoneyEndDate) {
		this.doMoneyEndDate = doMoneyEndDate;
	}

	public Long getSonderzug() {
		return sonderzug;
	}

	public void setSonderzug(Long sonderzug) {
		this.sonderzug = sonderzug;
	}

	public Long getSignDanStatus() {
		return signDanStatus;
	}

	public void setSignDanStatus(Long signDanStatus) {
		this.signDanStatus = signDanStatus;
	}

	public Long getIsErrorStatus() {
		return isErrorStatus;
	}

	public void setIsErrorStatus(Long isErrorStatus) {
		this.isErrorStatus = isErrorStatus;
	}

	public Long getIsDoDanStatus() {
		return isDoDanStatus;
	}

	public void setIsDoDanStatus(Long isDoDanStatus) {
		this.isDoDanStatus = isDoDanStatus;
	}

	public Long getDistributionDepartId() {
		return distributionDepartId;
	}

	public void setDistributionDepartId(Long distributionDepartId) {
		this.distributionDepartId = distributionDepartId;
	}

	public Long getIsDoToStatus() {
		return isDoToStatus;
	}

	public void setIsDoToStatus(Long isDoToStatus) {
		this.isDoToStatus = isDoToStatus;
	}

	public Long getNoticeGoodStatus() {
		return noticeGoodStatus;
	}

	public void setNoticeGoodStatus(Long noticeGoodStatus) {
		this.noticeGoodStatus = noticeGoodStatus;
	}

	public Long getOvermemo() {
		return overmemo;
	}

	public void setOvermemo(Long overmemo) {
		this.overmemo = overmemo;
	}

	public Long getEndDepartId() {
		return endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	public String getQueryConditionSelect() {
		return queryConditionSelect;
	}

	public void setQueryConditionSelect(String queryConditionSelect) {
		this.queryConditionSelect = queryConditionSelect;
	}

	public String getServiceDepartName() {
		return serviceDepartName;
	}

	public void setServiceDepartName(String serviceDepartName) {
		this.serviceDepartName = serviceDepartName;
	}

	public String getCpname() {
		return cpname;
	}

	public void setCpname(String cpname) {
		this.cpname = cpname;
	}

	public String getGoodsstatus() {
		return goodsstatus;
	}

	public void setGoodsstatus(String goodsstatus) {
		this.goodsstatus = goodsstatus;
	}

	public String getFlightno() {
		return flightno;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getFlightdate() {
		return flightdate;
	}

	public void setFlightdate(Date flightdate) {
		this.flightdate = flightdate;
	}

	public String getCurdepart() {
		return curdepart;
	}

	public void setCurdepart(String curdepart) {
		this.curdepart = curdepart;
	}

	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public String getSubno() {
		return subno;
	}

	public void setSubno(String subno) {
		this.subno = subno;
	}

	public String getConsigneetel() {
		return consigneetel;
	}

	public void setConsigneetel(String consigneetel) {
		this.consigneetel = consigneetel;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public Double getCusweight() {
		return cusweight;
	}

	public void setCusweight(Double cusweight) {
		this.cusweight = cusweight;
	}

	public Double getConsigneefee() {
		return consigneefee;
	}

	public void setConsigneefee(Double consigneefee) {
		this.consigneefee = consigneefee;
	}

	public Double getCusvalueaddfee() {
		return cusvalueaddfee;
	}

	public void setCusvalueaddfee(Double cusvalueaddfee) {
		this.cusvalueaddfee = cusvalueaddfee;
	}

	public Double getCpvalueaddfee() {
		return cpvalueaddfee;
	}

	public void setCpvalueaddfee(Double cpvalueaddfee) {
		this.cpvalueaddfee = cpvalueaddfee;
	}

	public String getGowhere() {
		return gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	public String getDismode() {
		return dismode;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setDismode(String dismode) {
		this.dismode = dismode;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getTakemode() {
		return takemode;
	}

	public void setTakemode(String takemode) {
		this.takemode = takemode;
	}

	public String getCustomerservice() {
		return customerservice;
	}

	public void setCustomerservice(String customerservice) {
		this.customerservice = customerservice;
	}

	public String getReceipttype() {
		return receipttype;
	}

	public void setReceipttype(String receipttype) {
		this.receipttype = receipttype;
	}

	public Long getUrgentservice() {
		return urgentservice;
	}

	public void setUrgentservice(Long urgentservice) {
		this.urgentservice = urgentservice;
	}

	public String getCurstatus() {
		return curstatus;
	}

	public void setCurstatus(String curstatus) {
		this.curstatus = curstatus;
	}

	public Long getStockpiece() {
		return stockpiece;
	}

	public void setStockpiece(Long stockpiece) {
		this.stockpiece = stockpiece;
	}

	public Double getStockweight() {
		return stockweight;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setStockweight(Double stockweight) {
		this.stockweight = stockweight;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getStockcreatetime() {
		return stockcreatetime;
	}

	public void setStockcreatetime(Date stockcreatetime) {
		this.stockcreatetime = stockcreatetime;
	}

	public Long getNodeorder() {
		return nodeorder;
	}

	public void setNodeorder(Long nodeorder) {
		this.nodeorder = nodeorder;
	}

	public String getCusadd() {
		return cusadd;
	}

	public void setCusadd(String cusadd) {
		this.cusadd = cusadd;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getConfirmtime() {
		return confirmtime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setConfirmtime(Date confirmtime) {
		this.confirmtime = confirmtime;
	}

	public String getFlightmainno() {
		return flightmainno;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setFlightmainno(String flightmainno) {
		this.flightmainno = flightmainno;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getSumitDate() {
		return sumitDate;
	}

	public void setSumitDate(Date sumitDate) {
		this.sumitDate = sumitDate;
	}

	public String getSignsource() {
		return signsource;
	}

	public void setSignsource(String signsource) {
		this.signsource = signsource;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getNotifystatus() {
		return notifystatus;
	}

	public void setNotifystatus(Long notifystatus) {
		this.notifystatus = notifystatus;
	}

	public Long getConfirmstatus() {
		return confirmstatus;
	}

	public void setConfirmstatus(Long confirmstatus) {
		this.confirmstatus = confirmstatus;
	}

	public Long getCashstatus() {
		return cashstatus;
	}

	public void setCashstatus(Long cashstatus) {
		this.cashstatus = cashstatus;
	}

	public Long getFisurestatus() {
		return fisurestatus;
	}

	public void setFisurestatus(Long fisurestatus) {
		this.fisurestatus = fisurestatus;
	}

	public Long getIndepartid() {
		return indepartid;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setIndepartid(Long indepartid) {
		this.indepartid = indepartid;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDogooddate() {
		return dogooddate;
	}

	public void setDogooddate(Date dogooddate) {
		this.dogooddate = dogooddate;
	}

	public Long getReachstatus() {
		return reachstatus;
	}

	public void setReachstatus(Long reachstatus) {
		this.reachstatus = reachstatus;
	}

	public Long getGetstatus() {
		return getstatus;
	}

	public void setGetstatus(Long getstatus) {
		this.getstatus = getstatus;
	}

	public Long getOutstatus() {
		return outstatus;
	}

	public void setOutstatus(Long outstatus) {
		this.outstatus = outstatus;
	}

	public Long getScanstatus() {
		return scanstatus;
	}

	public void setScanstatus(Long scanstatus) {
		this.scanstatus = scanstatus;
	}

	public Long getPrintnum() {
		return printnum;
	}

	public void setPrintnum(Long printnum) {
		this.printnum = printnum;
	}

	public Long getDistributiondepartid() {
		return distributiondepartid;
	}

	public void setDistributiondepartid(Long distributiondepartid) {
		this.distributiondepartid = distributiondepartid;
	}

	public Long getOreachstatus() {
		return oreachstatus;
	}

	public void setOreachstatus(Long oreachstatus) {
		this.oreachstatus = oreachstatus;
	}

	public Long getWaitnotice() {
		return waitnotice;
	}

	public void setWaitnotice(Long waitnotice) {
		this.waitnotice = waitnotice;
	}

	public Long getOsuoutstatus() {
		return osuoutstatus;
	}

	public void setOsuoutstatus(Long osuoutstatus) {
		this.osuoutstatus = osuoutstatus;
	}

	public String getCusdepartname() {
		return cusdepartname;
	}

	public void setCusdepartname(String cusdepartname) {
		this.cusdepartname = cusdepartname;
	}

	public String getOicreatename() {
		return oicreatename;
	}

	public void setOicreatename(String oicreatename) {
		this.oicreatename = oicreatename;
	}

	public Double getPaymentcollection() {
		return paymentcollection;
	}

	public void setPaymentcollection(Double paymentcollection) {
		this.paymentcollection = paymentcollection;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSignman() {
		return signman;
	}

	public void setSignman(String signman) {
		this.signman = signman;
	}

	public String getReplacesign() {
		return replacesign;
	}

	public void setReplacesign(String replacesign) {
		this.replacesign = replacesign;
	}

	public String getRealSignMan() {
		return realSignMan;
	}

	public void setRealSignMan(String realSignMan) {
		this.realSignMan = realSignMan;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDiscreatetime() {
		return discreatetime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setDiscreatetime(Date discreatetime) {
		this.discreatetime = discreatetime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAirportendtime() {
		return airportendtime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setAirportendtime(Date airportendtime) {
		this.airportendtime = airportendtime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAirportstarttime() {
		return airportstarttime;
	}

	public void setAirportstarttime(Date airportstarttime) {
		this.airportstarttime = airportstarttime;
	}

	public String getCarNoNum() {
		return carNoNum;
	}

	public void setCarNoNum(String carNoNum) {
		this.carNoNum = carNoNum;
	}

	public String getTrafficmode() {
		return trafficmode;
	}

	public void setTrafficmode(String trafficmode) {
		this.trafficmode = trafficmode;
	}

	public String getOicartype() {
		return oicartype;
	}

	public void setOicartype(String oicartype) {
		this.oicartype = oicartype;
	}

	public String getRoadtype() {
		return roadtype;
	}

	public void setRoadtype(String roadtype) {
		this.roadtype = roadtype;
	}

	public Double getSonderzugprice() {
		return sonderzugprice;
	}

	public void setSonderzugprice(Double sonderzugprice) {
		this.sonderzugprice = sonderzugprice;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Double getTrafeerate() {
		return trafeerate;
	}

	public void setTrafeerate(Double trafeerate) {
		this.trafeerate = trafeerate;
	}

	public Double getTrafee() {
		return trafee;
	}

	public void setTrafee(Double trafee) {
		this.trafee = trafee;
	}

	public Double getConsigneerate() {
		return consigneerate;
	}

	public void setConsigneerate(Double consigneerate) {
		this.consigneerate = consigneerate;
	}

	public Long getPhoneNoticeSign() {
		return phoneNoticeSign;
	}

	public void setPhoneNoticeSign(Long phoneNoticeSign) {
		this.phoneNoticeSign = phoneNoticeSign;
	}

	public Long getRightDepartId() {
		return rightDepartId;
	}

	public void setRightDepartId(Long rightDepartId) {
		this.rightDepartId = rightDepartId;
	}

	public String getIndepart() {
		return indepart;
	}

	public void setIndepart(String indepart) {
		this.indepart = indepart;
	}

	public String getEnddepart() {
		return enddepart;
	}

	public void setEnddepart(String enddepart) {
		this.enddepart = enddepart;
	}

	public String getRealgowhere() {
		return realgowhere;
	}

	public void setRealgowhere(String realgowhere) {
		this.realgowhere = realgowhere;
	}

	public String getCashName() {
		return cashName;
	}

	public void setCashName(String cashName) {
		this.cashName = cashName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}

	public Long getAirportstartstatus() {
		return airportstartstatus;
	}

	public void setAirportstartstatus(Long airportstartstatus) {
		this.airportstartstatus = airportstartstatus;
	}

	public String getDoStoreName() {
		return doStoreName;
	}

	public void setDoStoreName(String doStoreName) {
		this.doStoreName = doStoreName;
	}

	public Long getDoStoreStatus() {
		return doStoreStatus;
	}

	public void setDoStoreStatus(Long doStoreStatus) {
		this.doStoreStatus = doStoreStatus;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDoStoreTime() {
		return doStoreTime;
	}

	public void setDoStoreTime(Date doStoreTime) {
		this.doStoreTime = doStoreTime;
	}

	public String getConfirmname() {
		return confirmname;
	}

	public void setConfirmname(String confirmname) {
		this.confirmname = confirmname;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	public String getFlightdateString() {
		return flightdateString;
	}

	public void setFlightdateString(String flightdateString) {
		this.flightdateString = flightdateString;
	}

	public String getDistributiondepartname() {
		return distributiondepartname;
	}

	public void setDistributiondepartname(String distributiondepartname) {
		this.distributiondepartname = distributiondepartname;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getOsuoutstatustime() {
		return osuoutstatustime;
	}

	public void setOsuoutstatustime(Date osuoutstatustime) {
		this.osuoutstatustime = osuoutstatustime;
	}

	public String getReachName() {
		return reachName;
	}

	public void setReachName(String reachName) {
		this.reachName = reachName;
	}

	public Long getIsException() {
		return isException;
	}

	public void setIsException(Long isException) {
		this.isException = isException;
	}

	public Long getIsOprException() {
		return isOprException;
	}

	public void setIsOprException(Long isOprException) {
		this.isOprException = isOprException;
	}

	public Double getCpSonderzugprice() {
		return cpSonderzugprice;
	}

	public void setCpSonderzugprice(Double cpSonderzugprice) {
		this.cpSonderzugprice = cpSonderzugprice;
	}

	public Long getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(Long returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getGoodStatusColor() {
		return goodStatusColor;
	}

	public void setGoodStatusColor(String goodStatusColor) {
		this.goodStatusColor = goodStatusColor;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	public Long getGowhereId() {
		return gowhereId;
	}

	public void setGowhereId(Long gowhereId) {
		this.gowhereId = gowhereId;
	}

	public Double getDnoIncomeFee() {
		return dnoIncomeFee;
	}

	public void setDnoIncomeFee(Double dnoIncomeFee) {
		this.dnoIncomeFee = dnoIncomeFee;
	}

	public Long getTotalCp() {
		return totalCp;
	}

	public void setTotalCp(Long totalCp) {
		this.totalCp = totalCp;
	}

	public Double getCashMoney() {
		return cashMoney;
	}

	public void setCashMoney(Double cashMoney) {
		this.cashMoney = cashMoney;
	}

	public Long getIsFlyLate() {
		return isFlyLate;
	}

	public void setIsFlyLate(Long isFlyLate) {
		this.isFlyLate = isFlyLate;
	}
}
