package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

/**
 * 出库写入EDI缓存表实体类
 * CtTmD entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CT_TM_D")
public class CtTmD implements java.io.Serializable {

	// Fields

	private Long ctDNo;
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
	private Long status;//是否删除 （1：正常，0：需要删除）
	// Constructors

	/** default constructor */
	public CtTmD() {
	}

	/** minimal constructor */
	public CtTmD(Long ctDNo, String DNo, Long ctId, String stopflag,
			String goodStatus, String okFlag, String signFlag,
			String exceptionFlag) {
		this.ctDNo = ctDNo;
		this.DNo = DNo;
		this.ctId = ctId;
		this.stopflag = stopflag;
		this.goodStatus = goodStatus;
		this.okFlag = okFlag;
		this.signFlag = signFlag;
		this.exceptionFlag = exceptionFlag;
	}

	/** full constructor */
	public CtTmD(Long ctDNo, String DNo, Long consignId, String ydNo,
			String sustbillNo, String trId, String receCorp, String receMan,
			String receAdd, String receMob, String receTel, Long ctId,
			String ctName, String goods, Long piece, Double weight,
			Double cubage, String takeMode, Double endpayAmt, Double traCost,
			Double traAmt, Double dnAmt, String signType, String request,
			String isvaluables, String remark, String stopflag,
			String goodStatus, String okFlag, Date okFlagCreatetime,
			String okFlagCreatename, Date driverClockInTime,
			Date dirverClockOutTime, Date distributeTime, Date createTime,
			String createName, String signFlag, String exceptionFlag,
			String customerServiceName, String shfNo, String flyTime,
			String dnside, String sp, String ediRemark,
			String returnVoucherFlag, Double traAmtChange,
			Double endpayAmtChange, Double dnAmtChange, Long printTimes) {
		this.ctDNo = ctDNo;
		this.DNo = DNo;
		this.consignId = consignId;
		this.ydNo = ydNo;
		this.sustbillNo = sustbillNo;
		this.trId = trId;
		this.receCorp = receCorp;
		this.receMan = receMan;
		this.receAdd = receAdd;
		this.receMob = receMob;
		this.receTel = receTel;
		this.ctId = ctId;
		this.ctName = ctName;
		this.goods = goods;
		this.piece = piece;
		this.weight = weight;
		this.cubage = cubage;
		this.takeMode = takeMode;
		this.endpayAmt = endpayAmt;
		this.traCost = traCost;
		this.traAmt = traAmt;
		this.dnAmt = dnAmt;
		this.signType = signType;
		this.request = request;
		this.isvaluables = isvaluables;
		this.remark = remark;
		this.stopflag = stopflag;
		this.goodStatus = goodStatus;
		this.okFlag = okFlag;
		this.okFlagCreatetime = okFlagCreatetime;
		this.okFlagCreatename = okFlagCreatename;
		this.driverClockInTime = driverClockInTime;
		this.dirverClockOutTime = dirverClockOutTime;
		this.distributeTime = distributeTime;
		this.createTime = createTime;
		this.createName = createName;
		this.signFlag = signFlag;
		this.exceptionFlag = exceptionFlag;
		this.customerServiceName = customerServiceName;
		this.shfNo = shfNo;
		this.flyTime = flyTime;
		this.dnside = dnside;
		this.sp = sp;
		this.ediRemark = ediRemark;
		this.returnVoucherFlag = returnVoucherFlag;
		this.traAmtChange = traAmtChange;
		this.endpayAmtChange = endpayAmtChange;
		this.dnAmtChange = dnAmtChange;
		this.printTimes = printTimes;
	}

	// Property accessors
	@Id
	@Column(name = "CT_D_NO", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_CT_TM_D_ID ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getCtDNo() {
		return this.ctDNo;
	}

	public void setCtDNo(Long ctDNo) {
		this.ctDNo = ctDNo;
	}

	@Column(name = "D_NO", nullable = false, length = 10)
	public String getDNo() {
		return this.DNo;
	}

	public void setDNo(String DNo) {
		this.DNo = DNo;
	}

	@Column(name = "CONSIGN_ID", precision = 20, scale = 0)
	public Long getConsignId() {
		return this.consignId;
	}

	public void setConsignId(Long consignId) {
		this.consignId = consignId;
	}

	@Column(name = "YD_NO", length = 50)
	public String getYdNo() {
		return this.ydNo;
	}

	public void setYdNo(String ydNo) {
		this.ydNo = ydNo;
	}

	@Column(name = "SUSTBILL_NO", length = 100)
	public String getSustbillNo() {
		return this.sustbillNo;
	}

	public void setSustbillNo(String sustbillNo) {
		this.sustbillNo = sustbillNo;
	}

	@Column(name = "TR_ID", length = 20)
	public String getTrId() {
		return this.trId;
	}

	public void setTrId(String trId) {
		this.trId = trId;
	}

	@Column(name = "RECE_CORP", length = 80)
	public String getReceCorp() {
		return this.receCorp;
	}

	public void setReceCorp(String receCorp) {
		this.receCorp = receCorp;
	}

	@Column(name = "RECE_MAN", length = 100)
	public String getReceMan() {
		return this.receMan;
	}

	public void setReceMan(String receMan) {
		this.receMan = receMan;
	}

	@Column(name = "RECE_ADD", length = 3000)
	public String getReceAdd() {
		return this.receAdd;
	}

	public void setReceAdd(String receAdd) {
		this.receAdd = receAdd;
	}

	@Column(name = "RECE_MOB", length = 100)
	public String getReceMob() {
		return this.receMob;
	}

	public void setReceMob(String receMob) {
		this.receMob = receMob;
	}

	@Column(name = "RECE_TEL", length = 200)
	public String getReceTel() {
		return this.receTel;
	}

	public void setReceTel(String receTel) {
		this.receTel = receTel;
	}

	@Column(name = "CT_ID", nullable = false, precision = 22, scale = 0)
	public Long getCtId() {
		return this.ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	@Column(name = "CT_NAME", length = 100)
	public String getCtName() {
		return this.ctName;
	}

	public void setCtName(String ctName) {
		this.ctName = ctName;
	}

	@Column(name = "GOODS", length = 50)
	public String getGoods() {
		return this.goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	@Column(name = "PIECE", precision = 22, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "WEIGHT", precision = 12)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "CUBAGE", precision = 12)
	public Double getCubage() {
		return this.cubage;
	}

	public void setCubage(Double cubage) {
		this.cubage = cubage;
	}

	@Column(name = "TAKE_MODE", length = 20)
	public String getTakeMode() {
		return this.takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	@Column(name = "ENDPAY_AMT", precision = 12)
	public Double getEndpayAmt() {
		return this.endpayAmt;
	}

	public void setEndpayAmt(Double endpayAmt) {
		this.endpayAmt = endpayAmt;
	}

	@Column(name = "TRA_COST", precision = 12)
	public Double getTraCost() {
		return this.traCost;
	}

	public void setTraCost(Double traCost) {
		this.traCost = traCost;
	}

	@Column(name = "TRA_AMT", precision = 12)
	public Double getTraAmt() {
		return this.traAmt;
	}

	public void setTraAmt(Double traAmt) {
		this.traAmt = traAmt;
	}

	@Column(name = "DN_AMT", precision = 12)
	public Double getDnAmt() {
		return this.dnAmt;
	}

	public void setDnAmt(Double dnAmt) {
		this.dnAmt = dnAmt;
	}

	@Column(name = "SIGN_TYPE", length = 40)
	public String getSignType() {
		return this.signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	@Column(name = "REQUEST", length = 1000)
	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@Column(name = "ISVALUABLES", length = 1)
	public String getIsvaluables() {
		return this.isvaluables;
	}

	public void setIsvaluables(String isvaluables) {
		this.isvaluables = isvaluables;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STOPFLAG", nullable = false, length = 1)
	public String getStopflag() {
		return this.stopflag;
	}

	public void setStopflag(String stopflag) {
		this.stopflag = stopflag;
	}

	@Column(name = "GOOD_STATUS", nullable = false, length = 10)
	public String getGoodStatus() {
		return this.goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

	@Column(name = "OK_FLAG", nullable = false, length = 1)
	public String getOkFlag() {
		return this.okFlag;
	}

	public void setOkFlag(String okFlag) {
		this.okFlag = okFlag;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "OK_FLAG_CREATETIME", length = 7)
	public Date getOkFlagCreatetime() {
		return this.okFlagCreatetime;
	}

	public void setOkFlagCreatetime(Date okFlagCreatetime) {
		this.okFlagCreatetime = okFlagCreatetime;
	}

	@Column(name = "OK_FLAG_CREATENAME", length = 10)
	public String getOkFlagCreatename() {
		return this.okFlagCreatename;
	}

	public void setOkFlagCreatename(String okFlagCreatename) {
		this.okFlagCreatename = okFlagCreatename;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DRIVER_CLOCK_IN_TIME", length = 7)
	public Date getDriverClockInTime() {
		return this.driverClockInTime;
	}

	public void setDriverClockInTime(Date driverClockInTime) {
		this.driverClockInTime = driverClockInTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DIRVER_CLOCK_OUT_TIME", length = 7)
	public Date getDirverClockOutTime() {
		return this.dirverClockOutTime;
	}

	public void setDirverClockOutTime(Date dirverClockOutTime) {
		this.dirverClockOutTime = dirverClockOutTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DISTRIBUTE_TIME", length = 7)
	public Date getDistributeTime() {
		return this.distributeTime;
	}

	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "SIGN_FLAG", nullable = false, length = 1)
	public String getSignFlag() {
		return this.signFlag;
	}

	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}

	@Column(name = "EXCEPTION_FLAG", nullable = false, length = 1)
	public String getExceptionFlag() {
		return this.exceptionFlag;
	}

	public void setExceptionFlag(String exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}

	@Column(name = "CUSTOMER_SERVICE_NAME", length = 20)
	public String getCustomerServiceName() {
		return this.customerServiceName;
	}

	public void setCustomerServiceName(String customerServiceName) {
		this.customerServiceName = customerServiceName;
	}

	@Column(name = "SHF_NO", length = 50)
	public String getShfNo() {
		return this.shfNo;
	}

	public void setShfNo(String shfNo) {
		this.shfNo = shfNo;
	}

	@Column(name = "FLY_TIME", length = 8)
	public String getFlyTime() {
		return this.flyTime;
	}

	public void setFlyTime(String flyTime) {
		this.flyTime = flyTime;
	}

	@Column(name = "DNSIDE", length = 10)
	public String getDnside() {
		return this.dnside;
	}

	public void setDnside(String dnside) {
		this.dnside = dnside;
	}

	@Column(name = "SP", length = 1)
	public String getSp() {
		return this.sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	@Column(name = "EDI_REMARK", length = 250)
	public String getEdiRemark() {
		return this.ediRemark;
	}

	public void setEdiRemark(String ediRemark) {
		this.ediRemark = ediRemark;
	}

	@Column(name = "RETURN_VOUCHER_FLAG", length = 1)
	public String getReturnVoucherFlag() {
		return this.returnVoucherFlag;
	}

	public void setReturnVoucherFlag(String returnVoucherFlag) {
		this.returnVoucherFlag = returnVoucherFlag;
	}

	@Column(name = "TRA_AMT_CHANGE", precision = 12)
	public Double getTraAmtChange() {
		return this.traAmtChange;
	}

	public void setTraAmtChange(Double traAmtChange) {
		this.traAmtChange = traAmtChange;
	}

	@Column(name = "ENDPAY_AMT_CHANGE", precision = 12)
	public Double getEndpayAmtChange() {
		return this.endpayAmtChange;
	}

	public void setEndpayAmtChange(Double endpayAmtChange) {
		this.endpayAmtChange = endpayAmtChange;
	}

	@Column(name = "DN_AMT_CHANGE", precision = 12)
	public Double getDnAmtChange() {
		return this.dnAmtChange;
	}

	public void setDnAmtChange(Double dnAmtChange) {
		this.dnAmtChange = dnAmtChange;
	}

	@Column(name = "PRINT_TIMES", precision = 22, scale = 0)
	public Long getPrintTimes() {
		return this.printTimes;
	}

	public void setPrintTimes(Long printTimes) {
		this.printTimes = printTimes;
	}

	@Column(name = "SIGN_REQUEST")
	public String getSignRequest() {
		return signRequest;
	}

	public void setSignRequest(String signRequest) {
		this.signRequest = signRequest;
	}

	@Column(name="DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="ISURGENT")
	public String getIsurgent() {
		return isurgent;
	}
	
	public void setIsurgent(String isurgent) {
		this.isurgent = isurgent;
	}

	@Column(name="IS_SP")
	public String getIsSp() {
		return isSp;
	}

	public void setIsSp(String isSp) {
		this.isSp = isSp;
	}

	@Column(name="STATUS")
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
}