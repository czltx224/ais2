package dto;

import java.util.Date;

public class CtTmdDto implements java.io.Serializable{

	private String ctDNo;
	private String DNo;
	private Long consignId;
	private String ydNo;
	private String sustbillNo;
	private String trId;
	private String receCorp;
	private String receMan;
	private String receAdd;
	private String receMob;
	private String receTel;
	private Long ctId;
	private String ctName;
	private String goods;
	private Long piece;
	private Double weight;
	private Double cubage;
	private String takeMode;
	private Double endpayAmt;
	private Double traCost;
	private Double traAmt;
	private Double dnAmt;
	private String signType;
	private String request;
	private String isvaluables;
	private String remark;
	private String stopflag;
	private String goodStatus;
	private String okFlag;
	private Date okFlagCreatetime;
	private String okFlagCreatename;
	private Date driverClockInTime;
	private Date dirverClockOutTime;
	private Date distributeTime;
	private Date createTime;
	private String createName;
	private String signFlag;
	private String exceptionFlag;
	private String customerServiceName;
	private String shfNo;
	private String flyTime;
	private String dnside;
	private String sp;
	private String ediRemark;
	private String returnVoucherFlag;
	private Double traAmtChange;
	private Double endpayAmtChange;
	private Double dnAmtChange;
	private Long printTimes;
	private String signRequest;
	private String deptName;
	private String isurgent;//是否加急
	private String isSp;//是否专车
	private Long status;//状态 1正常，0删除
	
	public String getCtDNo() {
		return ctDNo;
	}
	public void setCtDNo(String ctDNo) {
		this.ctDNo = ctDNo;
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
	public String getTrId() {
		return trId;
	}
	public void setTrId(String trId) {
		this.trId = trId;
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
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
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
	public String getGoodStatus() {
		return goodStatus;
	}
	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}
	public String getOkFlag() {
		return okFlag;
	}
	public void setOkFlag(String okFlag) {
		this.okFlag = okFlag;
	}
	public Date getOkFlagCreatetime() {
		return okFlagCreatetime;
	}
	public void setOkFlagCreatetime(Date okFlagCreatetime) {
		this.okFlagCreatetime = okFlagCreatetime;
	}
	public String getOkFlagCreatename() {
		return okFlagCreatename;
	}
	public void setOkFlagCreatename(String okFlagCreatename) {
		this.okFlagCreatename = okFlagCreatename;
	}
	public Date getDriverClockInTime() {
		return driverClockInTime;
	}
	public void setDriverClockInTime(Date driverClockInTime) {
		this.driverClockInTime = driverClockInTime;
	}
	public Date getDirverClockOutTime() {
		return dirverClockOutTime;
	}
	public void setDirverClockOutTime(Date dirverClockOutTime) {
		this.dirverClockOutTime = dirverClockOutTime;
	}
	public Date getDistributeTime() {
		return distributeTime;
	}
	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
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
	public String getSignFlag() {
		return signFlag;
	}
	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}
	public String getExceptionFlag() {
		return exceptionFlag;
	}
	public void setExceptionFlag(String exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
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
	public String getDnside() {
		return dnside;
	}
	public void setDnside(String dnside) {
		this.dnside = dnside;
	}
	public String getSp() {
		return sp;
	}
	public void setSp(String sp) {
		this.sp = sp;
	}
	public String getEdiRemark() {
		return ediRemark;
	}
	public void setEdiRemark(String ediRemark) {
		this.ediRemark = ediRemark;
	}
	public String getReturnVoucherFlag() {
		return returnVoucherFlag;
	}
	public void setReturnVoucherFlag(String returnVoucherFlag) {
		this.returnVoucherFlag = returnVoucherFlag;
	}
	public Double getTraAmtChange() {
		return traAmtChange;
	}
	public void setTraAmtChange(Double traAmtChange) {
		this.traAmtChange = traAmtChange;
	}
	public Double getEndpayAmtChange() {
		return endpayAmtChange;
	}
	public void setEndpayAmtChange(Double endpayAmtChange) {
		this.endpayAmtChange = endpayAmtChange;
	}
	public Double getDnAmtChange() {
		return dnAmtChange;
	}
	public void setDnAmtChange(Double dnAmtChange) {
		this.dnAmtChange = dnAmtChange;
	}
	public Long getPrintTimes() {
		return printTimes;
	}
	public void setPrintTimes(Long printTimes) {
		this.printTimes = printTimes;
	}
	public String getSignRequest() {
		return signRequest;
	}
	public void setSignRequest(String signRequest) {
		this.signRequest = signRequest;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getIsurgent() {
		return isurgent;
	}
	public void setIsurgent(String isurgent) {
		this.isurgent = isurgent;
	}
	public String getIsSp() {
		return isSp;
	}
	public void setIsSp(String isSp) {
		this.isSp = isSp;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
}
