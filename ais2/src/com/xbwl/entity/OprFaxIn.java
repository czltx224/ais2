package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprFaxIn entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_FAX_IN")
public class OprFaxIn implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long dno;//���͵���
	private Long cusId;//����˾���̱���
	private String cpName;//����˾��������
	private String flightNo;//�����
	private Date flightDate;//��������
	private String flightTime;//����ʱ��
	private String trafficMode;//���䷽ʽ
	private String flightMainNo;//����������
	private String subNo;//�ֵ���
	private String distributionMode;//���ͷ�ʽ
	private String takeMode;//�����ʽ
	private String receiptType;//�ص�����
	private String consignee;//�ջ�������
	private String consigneeTel;//�ջ��˵绰���ֻ� 
	private String city;//�ջ���������
	private String town;//�ջ���������������
	private String addr;//�ջ��˵�ַ
	private Long piece;//����
	private Double cqWeight;//��������
	private Double cusWeight;//�Ʒ�����
	private Double bulk;//���
	private Long inDepartId;
	private String inDepart;//¼������
	private Long curDepartId;
	private String curDepart;//��ǰ����
	private Long endDepartId;
	private String endDepart;//�ն˲���
	private String gowhere;//��Ӧ��
	private Long distributionDepartId;
	private String distributionDepart;//���Ͳ���
	private Long greenChannel;//��ɫͨ��
	private Long urgentService;//�Ӽ�
	private Long wait;//��֪ͨ�Ż�
	private Long sonderzug;//�Ƿ�ר��
	private String carType;//ר������
	private String roadType;//ר��·��
	private String remark;//��ע
	private Long status;//״̬
	private String barCode;//���ݺ�
	private Double paymentCollection;//���ջ���
	private Double traFee;//��ת��
	private Double traFeeRate;//��ת����
	private Double cpRate;//����Ӧ������
	private Double cpFee;//����Ӧ����
	private Double consigneeRate;//�ջ���Ӧ������
	private Double consigneeFee;//�ջ���Ӧ����
	private String whoCash;//���ø��
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long faxMainId;//������������
	private String customerService;//�ͷ�Ա
	private Double cusValueAddFee;//�ͻ���ֵ�����
	private Double cpValueAddFee;//��ֵ������ܶ�
	private String goodsStatus;//��������״̬
	private Double declaredValue;//�����ֵ����
	private String goods;
	private String valuationType;//�Ƽ۷�ʽ
	private Double sonderzugPrice;//ר����
	private String areaType;//�ջ��˵�ַ����
	private String street;//�ջ������ڽֵ�����
	private String areaRank;
	private Double normTraRate;//��׼��ת����
	private Double normCpRate;//��׼����Ӧ������
	private Double normConsigneeRate;//��׼�ջ���Ӧ������
	private Double normSonderzugPrice;//��׼ר���۸�
	
	private String cusDepartName;
	private String cusDepartCode;//�ͷ�Ա���ڲ��ű���
	private String realGoWhere;//ʵ��ȥ��
	private Long goWhereId;//��Ӧ��ID
	
	private Double cpSonderzugPrice;//Ԥ��ר����
	
	//private Double totalSonderzug;//ר���ܺ�
	

	// Constructors

	/** default constructor */
	public OprFaxIn() {
	}

	/** minimal constructor */
	public OprFaxIn(Long dno, String cusName, String distributionMode,
			String takeMode, String consignee, Long piece,
			String customerService, Double declaredValue) {
		this.dno = dno;
		this.cpName = cusName;
		this.distributionMode = distributionMode;
		this.takeMode = takeMode;
		this.consignee = consignee;
		this.piece = piece;
		this.customerService = customerService;
		this.declaredValue = declaredValue;
	}

	// Property accessors
	@Id
	@Column(name = "D_NO", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_FAX_IN ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
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

	@Column(name = "CP_NAME", nullable = false, length = 50)
	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cusName) {
		this.cpName = cusName;
	}

	@Column(name = "FLIGHT_NO", length = 20)
	public String getFlightNo() {
		return this.flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "FLIGHT_DATE", length = 7)
	public Date getFlightDate() {
		return this.flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	@Column(name = "FLIGHT_TIME", length = 10)
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

	@Column(name = "FLIGHT_MAIN_NO", length = 50)
	public String getFlightMainNo() {
		return this.flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	@Column(name = "SUB_NO", length = 50)
	public String getSubNo() {
		return this.subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	@Column(name = "DISTRIBUTION_MODE", nullable = false, length = 10)
	public String getDistributionMode() {
		return this.distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	@Column(name = "TAKE_MODE", nullable = false, length = 10)
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

	@Column(name = "CONSIGNEE", nullable = false, length = 100)
	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column(name = "CONSIGNEE_TEL", length = 30)
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

	@Column(name = "PIECE", nullable = false, precision = 22, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "CQ_WEIGHT", precision = 22, scale = 0)
	public Double getCqWeight() {
		return this.cqWeight;
	}

	public void setCqWeight(Double cqWeight) {
		this.cqWeight = cqWeight;
	}

	@Column(name = "CUS_WEIGHT", precision = 22, scale = 0)
	public Double getCusWeight() {
		return this.cusWeight;
	}

	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}

	@Column(name = "BULK", precision = 22, scale = 0)
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

	@Column(name = "PAYMENT_COLLECTION", precision = 22, scale = 0)
	public Double getPaymentCollection() {
		return this.paymentCollection;
	}

	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	@Column(name = "TRA_FEE", precision = 22, scale = 0)
	public Double getTraFee() {
		return this.traFee;
	}

	public void setTraFee(Double traFee) {
		this.traFee = traFee;
	}

	@Column(name = "TRA_FEE_RATE", precision = 22, scale = 0)
	public Double getTraFeeRate() {
		return this.traFeeRate;
	}

	public void setTraFeeRate(Double traFeeRate) {
		this.traFeeRate = traFeeRate;
	}

	@Column(name = "CP_RATE", precision = 22, scale = 0)
	public Double getCpRate() {
		return this.cpRate;
	}

	public void setCpRate(Double cpRate) {
		this.cpRate = cpRate;
	}

	@Column(name = "CP_FEE", precision = 22, scale = 0)
	public Double getCpFee() {
		return this.cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	@Column(name = "CONSIGNEE_RATE", precision = 22, scale = 0)
	public Double getConsigneeRate() {
		return this.consigneeRate;
	}

	public void setConsigneeRate(Double consigneeRate) {
		this.consigneeRate = consigneeRate;
	}

	@Column(name = "CONSIGNEE_FEE", precision = 22, scale = 0)
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

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
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

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS")
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

	@Column(name = "CUSTOMER_SERVICE", nullable = false, length = 20)
	public String getCustomerService() {
		return this.customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}

	@Column(name = "CUS_VALUE_ADD_FEE", precision = 22, scale = 0)
	public Double getCusValueAddFee() {
		return this.cusValueAddFee;
	}

	public void setCusValueAddFee(Double cusValueAddFee) {
		this.cusValueAddFee = cusValueAddFee;
	}

	@Column(name = "CP_VALUE_ADD_FEE", precision = 22, scale = 0)
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

	@Column(name = "DECLARED_VALUE", nullable = false, precision = 22, scale = 0)
	public Double getDeclaredValue() {
		return this.declaredValue;
	}

	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	@Column(name = "GOODS", length = 30)
	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}
	@Column(name = "VALUATION_TYPE", length = 10)
	public String getValuationType() {
		return valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}
	@Column(name = "SONDERZUG_PRICE")
	public Double getSonderzugPrice() {
		return sonderzugPrice;
	}

	public void setSonderzugPrice(Double sonderzugPrice) {
		this.sonderzugPrice = sonderzugPrice;
	}
	@Column(name = "AREA_TYPE", length = 20)
	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	@Column(name = "STREET", length = 20)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	@Column(name = "ROAD_TYPE", length = 20)
	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	/**
	 * @return the areaRank
	 */
	@Column(name = "AREA_RANK", length = 20)
	public String getAreaRank() {
		return areaRank;
	}

	/**
	 * @param areaRank the areaRank to set
	 */
	public void setAreaRank(String areaRank) {
		this.areaRank = areaRank;
	}

	/**
	 * @return the inDepartId
	 */
	@Column(name = "IN_DEPART_ID")
	public Long getInDepartId() {
		return inDepartId;
	}

	/**
	 * @param inDepartId the inDepartId to set
	 */
	public void setInDepartId(Long inDepartId) {
		this.inDepartId = inDepartId;
	}

	/**
	 * @return the curDepartId
	 */
	@Column(name = "CUR_DEPART_ID")
	public Long getCurDepartId() {
		return curDepartId;
	}

	/**
	 * @param curDepartId the curDepartId to set
	 */
	public void setCurDepartId(Long curDepartId) {
		this.curDepartId = curDepartId;
	}

	/**
	 * @return the endDepartId
	 */
	@Column(name = "END_DEPART_ID")
	public Long getEndDepartId() {
		return endDepartId;
	}

	/**
	 * @param endDepartId the endDepartId to set
	 */
	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	/**
	 * @return the distributionDepartId
	 */
	@Column(name = "DISTRIBUTION_DEPART_ID")
	public Long getDistributionDepartId() {
		return distributionDepartId;
	}

	/**
	 * @param distributionDepartId the distributionDepartId to set
	 */
	public void setDistributionDepartId(Long distributionDepartId) {
		this.distributionDepartId = distributionDepartId;
	}
	
	@Column(name = "CUS_DEPART_NAME", length = 50)
	public String getCusDepartName() {
		return cusDepartName;
	}

	public void setCusDepartName(String cusDepartName) {
		this.cusDepartName = cusDepartName;
	}

	/**
	 * @return the normTraRate
	 */
	@Column(name = "NORM_TRA_RATE")
	public Double getNormTraRate() {
		return normTraRate;
	}

	/**
	 * @param normTraRate the normTraRate to set
	 */
	public void setNormTraRate(Double normTraRate) {
		this.normTraRate = normTraRate;
	}

	/**
	 * @return the normCpRate
	 */
	@Column(name = "NORM_CP_RATE")
	public Double getNormCpRate() {
		return normCpRate;
	}

	/**
	 * @param normCpRate the normCpRate to set
	 */
	public void setNormCpRate(Double normCpRate) {
		this.normCpRate = normCpRate;
	}

	/**
	 * @return the normConsigneeRate
	 */
	@Column(name = "NORM_CONSIGNEE_RATE")
	public Double getNormConsigneeRate() {
		return normConsigneeRate;
	}

	/**
	 * @param normConsigneeRate the normConsigneeRate to set
	 */
	public void setNormConsigneeRate(Double normConsigneeRate) {
		this.normConsigneeRate = normConsigneeRate;
	}

	/**
	 * @return the normSonderzugPrice
	 */
	@Column(name = "NORM_SONDERZUG_PRICE")
	public Double getNormSonderzugPrice() {
		return normSonderzugPrice;
	}

	/**
	 * @param normSonderzugPrice the normSonderzugPrice to set
	 */
	public void setNormSonderzugPrice(Double normSonderzugPrice) {
		this.normSonderzugPrice = normSonderzugPrice;
	}

	/**
	 * @return the cusDepartId
	 */
	@Column(name = "CUS_DEPART_CODE")
	public String getCusDepartCode() {
		return cusDepartCode;
	}

	/**
	 * @param cusDepartId the cusDepartId to set
	 */
	public void setCusDepartCode(String cusDepartCode) {
		this.cusDepartCode = cusDepartCode;
	}

	/**
	 * @return the realGoWhere
	 */
	@Column(name = "REAL_GO_WHERE")
	public String getRealGoWhere() {
		return realGoWhere;
	}

	/**
	 * @param realGoWhere the realGoWhere to set
	 */
	public void setRealGoWhere(String realGoWhere) {
		this.realGoWhere = realGoWhere;
	}

	/**
	 * @return the goWhereId
	 */
	@Column(name = "GOWHERE_ID")
	public Long getGoWhereId() {
		return goWhereId;
	}

	/**
	 * @param goWhereId the goWhereId to set
	 */
	public void setGoWhereId(Long goWhereId) {
		this.goWhereId = goWhereId;
	}

	public String toString() {
		return "���͵���:"+dno+",���䷽ʽ:"+trafficMode+",������:"+flightMainNo+",�����:"+flightNo+",����˾:"+cpName+",�ջ���������"
		+consignee+",�ջ��˵绰:"+consigneeTel+",Ԥ�����ͷ�:"+cpFee+",�������ͷ�:"+consigneeFee+",��ת��:"+traFee
		+",Ԥ��ר����:"+cpSonderzugPrice+",����ר����:"+sonderzugPrice+"Ԥ����ֵ��:"+cpValueAddFee+"������ֵ��:"+cusValueAddFee
		+"���ջ���:"+paymentCollection+",���ͷ�ʽ:"+distributionMode+",��ַ����:"+areaType+",�����ʽ:"+takeMode;
	}
	
	public String showGoodMsg(){
		return "����:"+cusWeight+",���:"+bulk+",����:"+piece+",�ջ��˵�ַ:"+addr;
	}

	/**
	 * @return the cpSonderzugPrice
	 */
	
	@Column(name = "CP_SONDERZUG_PRICE")
	public Double getCpSonderzugPrice() {
		return cpSonderzugPrice;
	}

	/**
	 * @param cpSonderzugPrice the cpSonderzugPrice to set
	 */
	public void setCpSonderzugPrice(Double cpSonderzugPrice) {
		this.cpSonderzugPrice = cpSonderzugPrice;
	}
	
}