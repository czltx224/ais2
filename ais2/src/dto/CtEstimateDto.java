package dto;

import java.util.Date;

public class CtEstimateDto implements java.io.Serializable {

	private Long EId;// ID
	private String DNo;// 配送单号
	private Long consignId;// 收货人ID
	private String ydNo;// 运单号
	private String sustbillNo;// 分单号
	private String receCorp;// 收货人单位
	private String receMan;// 收货人
	private String receAdd;// 收货人地址
	private String receMob;// 收货人手机
	private String receTel;// 收货人电话
	private Long ctId;// 中转公司ID
	private String ctName;// 中转公司
	private String goods;// 货物种类
	private Long piece;// 件数
	private Double weight;// 重量
	private Double cubage;// 体积
	private String takeMode;// 提货方式
	private Double endpayAmt;// 代收货款
	private Double traCost;// 中转费率
	private Double traAmt;// 中转费用
	private Double dnAmt;// 到付提送费
	private String request;// 个性化要求
	private String isvaluables;// 是否贵重物品
	private String remark;// 备注
	private String stopflag;
	private Date createTime;// 创建时间
	private String createName;// 创建人
	private String customerServiceName;// 客服员
	private String shfNo;// 航班号
	private String flyTime;// 航班落地时间

	private Date faxInTime;// 传真录入时间

	public Long getEId() {
		return EId;
	}

	public void setEId(Long id) {
		EId = id;
	}

	public String getDNo() {
		return DNo;
	}

	public void setDNo(String no) {
		DNo = no;
	}

	public Long getConsignId() {
		return consignId;
	}

	public void setConsignId(Long consignId) {
		this.consignId = consignId;
	}

	public String getYdNo() {
		return ydNo;
	}

	public void setYdNo(String ydNo) {
		this.ydNo = ydNo;
	}

	public String getSustbillNo() {
		return sustbillNo;
	}

	public void setSustbillNo(String sustbillNo) {
		this.sustbillNo = sustbillNo;
	}

	public String getReceCorp() {
		return receCorp;
	}

	public void setReceCorp(String receCorp) {
		this.receCorp = receCorp;
	}

	public String getReceMan() {
		return receMan;
	}

	public void setReceMan(String receMan) {
		this.receMan = receMan;
	}

	public String getReceAdd() {
		return receAdd;
	}

	public void setReceAdd(String receAdd) {
		this.receAdd = receAdd;
	}

	public String getReceMob() {
		return receMob;
	}

	public void setReceMob(String receMob) {
		this.receMob = receMob;
	}

	public String getReceTel() {
		return receTel;
	}

	public void setReceTel(String receTel) {
		this.receTel = receTel;
	}

	public Long getCtId() {
		return ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	public String getCtName() {
		return ctName;
	}

	public void setCtName(String ctName) {
		this.ctName = ctName;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getCubage() {
		return cubage;
	}

	public void setCubage(Double cubage) {
		this.cubage = cubage;
	}

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	public Double getEndpayAmt() {
		return endpayAmt;
	}

	public void setEndpayAmt(Double endpayAmt) {
		this.endpayAmt = endpayAmt;
	}

	public Double getTraCost() {
		return traCost;
	}

	public void setTraCost(Double traCost) {
		this.traCost = traCost;
	}

	public Double getTraAmt() {
		return traAmt;
	}

	public void setTraAmt(Double traAmt) {
		this.traAmt = traAmt;
	}

	public Double getDnAmt() {
		return dnAmt;
	}

	public void setDnAmt(Double dnAmt) {
		this.dnAmt = dnAmt;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getIsvaluables() {
		return isvaluables;
	}

	public void setIsvaluables(String isvaluables) {
		this.isvaluables = isvaluables;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStopflag() {
		return stopflag;
	}

	public void setStopflag(String stopflag) {
		this.stopflag = stopflag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCustomerServiceName() {
		return customerServiceName;
	}

	public void setCustomerServiceName(String customerServiceName) {
		this.customerServiceName = customerServiceName;
	}

	public String getShfNo() {
		return shfNo;
	}

	public void setShfNo(String shfNo) {
		this.shfNo = shfNo;
	}

	public String getFlyTime() {
		return flyTime;
	}

	public void setFlyTime(String flyTime) {
		this.flyTime = flyTime;
	}

	public Date getFaxInTime() {
		return faxInTime;
	}

	public void setFaxInTime(Date faxInTime) {
		this.faxInTime = faxInTime;
	}

}
