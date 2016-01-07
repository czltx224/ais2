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
 * 传真写入EDI缓存表实体类
 * CtEstimate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CT_ESTIMATE")
public class CtEstimate implements java.io.Serializable {

	// Fields

	private Long EId;//ID
	private String DNo;//配送单号
	private Long consignId;//收货人ID
	private String ydNo;//运单号
	private String sustbillNo;//分单号
	private String receCorp;//收货人单位
	private String receMan;//收货人
	private String receAdd;//收货人地址
	private String receMob;//收货人手机
	private String receTel;//收货人电话
	private Long ctId;//中转公司ID
	private String ctName;//中转公司
	private String goods;//货物种类
	private Long piece;//件数
	private Double weight;//重量
	private Double cubage;//体积
	private String takeMode;//提货方式
	private Double endpayAmt;//代收货款
	private Double traCost;//中转费率
	private Double traAmt;//中转费用
	private Double dnAmt;//到付提送费
	private String request;//个性化要求
	private String isvaluables;//是否贵重物品
	private String remark;//备注
	private String stopflag;
	private Date createTime;//创建时间
	private String createName;//创建人
	private String customerServiceName;//客服员
	private String shfNo;//航班号
	private String flyTime;//航班落地时间
	
	private Date faxInTime;//传真录入时间

	// Constructors

	/** default constructor */
	public CtEstimate() {
	}

	/** minimal constructor */
	public CtEstimate(Long EId, String DNo, Long ctId, String stopflag) {
		this.EId = EId;
		this.DNo = DNo;
		this.ctId = ctId;
		this.stopflag = stopflag;
	}

	/** full constructor */
	public CtEstimate(Long EId, String DNo, Long consignId, String ydNo,
			String sustbillNo, String receCorp, String receMan, String receAdd,
			String receMob, String receTel, Long ctId, String ctName,
			String goods, Long piece, Double weight, Double cubage,
			String takeMode, Double endpayAmt, Double traCost, Double traAmt,
			Double dnAmt, String request, String isvaluables, String remark,
			String stopflag, Date createTime, String createName,
			String customerServiceName, String shfNo, String flyTime) {
		this.EId = EId;
		this.DNo = DNo;
		this.consignId = consignId;
		this.ydNo = ydNo;
		this.sustbillNo = sustbillNo;
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
		this.request = request;
		this.isvaluables = isvaluables;
		this.remark = remark;
		this.stopflag = stopflag;
		this.createTime = createTime;
		this.createName = createName;
		this.customerServiceName = customerServiceName;
		this.shfNo = shfNo;
		this.flyTime = flyTime;
	}

	// Property accessors
	@Id
	@Column(name = "E_ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_CT_ESTIMATE_ID ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	//@GeneratedValue(generator = "paymentableGenerator")   
    //@GenericGenerator(name = "paymentableGenerator", strategy = "sequence",    
	  //           parameters = { @Parameter(name = "sequence", value = "SEQ_MUSIC_LABEL") })
	public Long getEId() {
		//return this.EId==null?null:Long.parseLong(this.EId);
		return this.EId;
	}

	public void setEId(Long EId) {
		this.EId = EId;
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

	@Column(name = "YD_NO", length = 200)
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "FAX_IN_TIME", length = 7)
	public Date getFaxInTime() {
		return faxInTime;
	}

	public void setFaxInTime(Date faxInTime) {
		this.faxInTime = faxInTime;
	}
}