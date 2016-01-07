package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author CaoZhili time Aug 8, 2011 10:08:10 AM
 */
public class OprMainOrderAdjustVo implements java.io.Serializable {

	private Long fdno;// 配送单号
	private Long cusId;// 代理公司客商编码
	private String cpName;// 代理公司客商名称
	private String subNo;// 分单号
	private String flightNo;// 航班号
	private String flightMainNo;// 航空主单号
	private String flightTime;// 航班时间
	private Date flightDate;// 航班日期
	private String addr;// 收货人地址
	private String consignee;// 收货人姓名
	private String gowhere;// 去向
	private Long piece;// 件数
	private Double consigneeRate;// 收货人应付费率
	private Double consigneeFee;// 收货人应付费
	private Double bulk;// 体积
	private String goods;// 品名
	private String goodsStatus;// 货物最新状态
	private Long inDepartId;
	private Double cusWeight;// 计费重量

	private Long id;// 序号
	private Long dno;// 配送单号
	private String oldFlightMainNo;// 旧主单号
	private String newFlightMainNo;// 新主单号
	private Double oldWeight;// 旧重量
	private Double newWeight;// 新重量
	private Double oldConsigneeFee;// 旧提送费
	private Double newConsigneeFee;// 新提送费
	private Double adjustMoney;// 调整金额差
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private Long departId;
	private String ts;
	private String departName;

	private String whoCash;// 费用付款方

	public Long getFdno() {
		return fdno;
	}

	public void setFdno(Long fdno) {
		this.fdno = fdno;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getGowhere() {
		return gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public Double getConsigneeRate() {
		return consigneeRate;
	}

	public void setConsigneeRate(Double consigneeRate) {
		this.consigneeRate = consigneeRate;
	}

	public Double getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public String getOldFlightMainNo() {
		return oldFlightMainNo;
	}

	public void setOldFlightMainNo(String oldFlightMainNo) {
		this.oldFlightMainNo = oldFlightMainNo;
	}

	public String getNewFlightMainNo() {
		return newFlightMainNo;
	}

	public void setNewFlightMainNo(String newFlightMainNo) {
		this.newFlightMainNo = newFlightMainNo;
	}

	public Double getOldWeight() {
		return oldWeight;
	}

	public void setOldWeight(Double oldWeight) {
		this.oldWeight = oldWeight;
	}

	public Double getNewWeight() {
		return newWeight;
	}

	public void setNewWeight(Double newWeight) {
		this.newWeight = newWeight;
	}

	public Double getOldConsigneeFee() {
		return oldConsigneeFee;
	}

	public void setOldConsigneeFee(Double oldConsigneeFee) {
		this.oldConsigneeFee = oldConsigneeFee;
	}

	public Double getNewConsigneeFee() {
		return newConsigneeFee;
	}

	public void setNewConsigneeFee(Double newConsigneeFee) {
		this.newConsigneeFee = newConsigneeFee;
	}

	public Double getAdjustMoney() {
		return adjustMoney;
	}

	public void setAdjustMoney(Double adjustMoney) {
		this.adjustMoney = adjustMoney;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Long getInDepartId() {
		return inDepartId;
	}

	public void setInDepartId(Long inDepartId) {
		this.inDepartId = inDepartId;
	}

	public Double getCusWeight() {
		return cusWeight;
	}

	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}

	public String getWhoCash() {
		return whoCash;
	}

	public void setWhoCash(String whoCash) {
		this.whoCash = whoCash;
	}

}
