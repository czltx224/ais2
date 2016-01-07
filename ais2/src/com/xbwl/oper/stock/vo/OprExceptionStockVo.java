package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
public class OprExceptionStockVo implements java.io.Serializable {

	private Long id;
	private Long dno;// ���͵���
	private Long piece;// �쳣������
	private Double weight;// �쳣�������
	private String consignee;// �ջ���
	private String cpName;// ��������
	private String exceptionEnterName;// �쳣�����
	private Date exceptionEnterTime;// �쳣���ʱ��
	private String exceptionOutName;// �쳣������
	private Date exceptionOutTime;// �쳣����ʱ��
	private Long exceptionStatus;// ״̬0�����ϣ�1��������2����⣬3������
	private Long departId;// ҵ����ID
	private String departName;// ҵ����
	private String consigneeAddr;// �ջ��˵�ַ
	private String outStockNo;// ���ⵥ��
	private String gowhere;// ԭȥ��
	private String distributionMode;// ���ͷ�ʽ
	private String returnType;// ��������
	private String sourceType;// ��Դ���� ��Ϊ����������ת�쳣���ȵȣ���Դ�����ֵ�
	private Double configneeFee;// �����ܶ�
	private Double cpFee;// Ԥ���ܶ�
	private Double paymentCollection;// ���ջ���
	private String outStockObj;// �����������
	private String sourceNo;// ��Դ����
	private Double addConfigneeFee;// ׷�ӵ������ͷ�
	private Double addCpFee;// ׷��Ԥ�����ͷ�
	private Double outCost;// ����ɱ�
	private String createName;// ������
	private Date createTime;// ����ʱ��
	private String updateName;// �޸���
	private Date updateTime;// �޸�ʱ��
	private String ts;// ʱ���

	private String flightNo;// �����
	private Date flightDate;// ��������
	private String flightMainNo;// ����������
	private String subNo;// �ֵ���
	private String takeMode;// �����ʽ
	private Date faxCreateDate;// ����ʱ��
	private String goods;// Ʒ��
	private Long faxPiece;// ¼������
	private Double cusWeight;// �Ʒ�����
	private Double faxBulk;// ���
	private String goodsStatus;// ���ﵱǰ״̬

	private String outSender;//�ͻ�Ա
	private String outLoad;//����;��
	private String outStockObjName;//�����������
	private String outRemark;//���ⱸע
	
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

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getExceptionEnterName() {
		return exceptionEnterName;
	}

	public void setExceptionEnterName(String exceptionEnterName) {
		this.exceptionEnterName = exceptionEnterName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	public Date getExceptionEnterTime() {
		return exceptionEnterTime;
	}

	public void setExceptionEnterTime(Date exceptionEnterTime) {
		this.exceptionEnterTime = exceptionEnterTime;
	}

	public String getExceptionOutName() {
		return exceptionOutName;
	}

	public void setExceptionOutName(String exceptionOutName) {
		this.exceptionOutName = exceptionOutName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	public Date getExceptionOutTime() {
		return exceptionOutTime;
	}

	public void setExceptionOutTime(Date exceptionOutTime) {
		this.exceptionOutTime = exceptionOutTime;
	}

	public Long getExceptionStatus() {
		return exceptionStatus;
	}

	public void setExceptionStatus(Long exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	public String getOutStockNo() {
		return outStockNo;
	}

	public void setOutStockNo(String outStockNo) {
		this.outStockNo = outStockNo;
	}

	public String getGowhere() {
		return gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Double getConfigneeFee() {
		return configneeFee;
	}

	public void setConfigneeFee(Double configneeFee) {
		this.configneeFee = configneeFee;
	}

	public Double getCpFee() {
		return cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	public Double getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getOutStockObj() {
		return outStockObj;
	}

	public void setOutStockObj(String outStockObj) {
		this.outStockObj = outStockObj;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	public Double getAddConfigneeFee() {
		return addConfigneeFee;
	}

	public void setAddConfigneeFee(Double addConfigneeFee) {
		this.addConfigneeFee = addConfigneeFee;
	}

	public Double getAddCpFee() {
		return addCpFee;
	}

	public void setAddCpFee(Double addCpFee) {
		this.addCpFee = addCpFee;
	}

	public Double getOutCost() {
		return outCost;
	}

	public void setOutCost(Double outCost) {
		this.outCost = outCost;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@JSON(format="yyyy-MM-dd")  
	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	public Date getFaxCreateDate() {
		return faxCreateDate;
	}

	public void setFaxCreateDate(Date faxCreateDate) {
		this.faxCreateDate = faxCreateDate;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public Long getFaxPiece() {
		return faxPiece;
	}

	public void setFaxPiece(Long faxPiece) {
		this.faxPiece = faxPiece;
	}

	public Double getCusWeight() {
		return cusWeight;
	}

	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}

	public Double getFaxBulk() {
		return faxBulk;
	}

	public void setFaxBulk(Double faxBulk) {
		this.faxBulk = faxBulk;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getOutSender() {
		return outSender;
	}

	public void setOutSender(String outSender) {
		this.outSender = outSender;
	}

	public String getOutLoad() {
		return outLoad;
	}

	public void setOutLoad(String outLoad) {
		this.outLoad = outLoad;
	}

	public String getOutStockObjName() {
		return outStockObjName;
	}

	public void setOutStockObjName(String outStockObjName) {
		this.outStockObjName = outStockObjName;
	}

	public String getOutRemark() {
		return outRemark;
	}

	public void setOutRemark(String outRemark) {
		this.outRemark = outRemark;
	}

}
