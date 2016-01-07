package com.xbwl.oper.stock.vo;

/**
 * author CaoZhili time Jul 6, 2011 6:07:35 PM
 */
public class OprFaxInSureVo implements java.io.Serializable {

	private Long dno;// ���͵���1
	private String cpName;// ����˾����2
	private Long cusId;
	private String flightNo;// �����3
	private String flightMainNo;// ����������4
	private String subNo;// �ֵ���5
	private String receiptType;// �Ƿ���ԭ��6
	private Long piece;// ����7
	private Double cqWeight;// ��������8
	private Long status;// ״̬9
	private String goods;// Ʒ��10
	private String request;// ִ��Ҫ��11
	private Long isOpr;// �Ƿ�ִ��12

	private Long loadingbrigadeId;// װж��ID13
	private Long dispatchId;// �ֲ���ID14
	private String orderfield;// �����ֶ�15
	private String reqRemark;// ����Ҫ��ע16
	private Long surePiece;// ʵ������17
	private Long splitNum;// �𵥷���18
	private Long arrivedPiece;// �ѵ�����19
	private Double bulk;// ���

	private String addr;// �ջ��˵�ַ
	private String consignee;// �ջ���
	private Long overmemoNo;// ����������

	private Long overmemoDetailId;
	private String distributionMode;
	private String takeMode;
	private Long realPiece;
	private String carCode;
	private Long requestDoId;

	private Long loadingbrigadeWeightId;

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

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Long getIsOpr() {
		return isOpr;
	}

	public void setIsOpr(Long isOpr) {
		this.isOpr = isOpr;
	}

	public Long getLoadingbrigadeId() {
		return loadingbrigadeId;
	}

	public void setLoadingbrigadeId(Long loadingbrigadeId) {
		this.loadingbrigadeId = loadingbrigadeId;
	}

	public Long getDispatchId() {
		return dispatchId;
	}

	public void setDispatchId(Long dispatchId) {
		this.dispatchId = dispatchId;
	}

	public String getOrderfield() {
		return orderfield;
	}

	public void setOrderfield(String orderfield) {
		this.orderfield = orderfield;
	}

	public String getReqRemark() {
		return reqRemark;
	}

	public void setReqRemark(String reqRemark) {
		this.reqRemark = reqRemark;
	}

	public Long getSurePiece() {
		return surePiece;
	}

	public void setSurePiece(Long surePiece) {
		this.surePiece = surePiece;
	}

	public Long getSplitNum() {
		return splitNum;
	}

	public void setSplitNum(Long splitNum) {
		this.splitNum = splitNum;
	}

	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public Double getCqWeight() {
		return cqWeight;
	}

	public void setCqWeight(Double cqWeight) {
		this.cqWeight = cqWeight;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	public Long getArrivedPiece() {
		return arrivedPiece;
	}

	public void setArrivedPiece(Long arrivedPiece) {
		this.arrivedPiece = arrivedPiece;
	}

	public Long getOvermemoNo() {
		return overmemoNo;
	}

	public void setOvermemoNo(Long overmemoNo) {
		this.overmemoNo = overmemoNo;
	}

	public Long getOvermemoDetailId() {
		return overmemoDetailId;
	}

	public void setOvermemoDetailId(Long overmemoDetailId) {
		this.overmemoDetailId = overmemoDetailId;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	public Long getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public Long getRequestDoId() {
		return requestDoId;
	}

	public void setRequestDoId(Long requestDoId) {
		this.requestDoId = requestDoId;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	public Long getLoadingbrigadeWeightId() {
		return loadingbrigadeWeightId;
	}

	public void setLoadingbrigadeWeightId(Long loadingbrigadeWeightId) {
		this.loadingbrigadeWeightId = loadingbrigadeWeightId;
	}

	public OprFaxInSureVo(Long dno, String cpName, String flightNo,
			String flightMainNo, String subNo, String receiptType, Long piece,
			Double cqWeight, Long status, String goods, String request,
			Long isOpr, Long loadingbrigadeId, Long dispatchId,
			String orderfield, String reqRemark, Long surePiece, Long splitNum,
			Long arrivedPiece, Double bulk, String addr, String consignee,
			Long overmemoNo, Long overmemoDetailId, String distributionMode,
			String takeMode, Long realPiece, Long requestDoId) {
		super();
		this.dno = dno;
		this.cpName = cpName;
		this.flightNo = flightNo;
		this.flightMainNo = flightMainNo;
		this.subNo = subNo;
		this.receiptType = receiptType;
		this.piece = piece;
		this.cqWeight = cqWeight;
		this.status = status;
		this.goods = goods;
		this.request = request;
		this.isOpr = isOpr;
		this.loadingbrigadeId = loadingbrigadeId;
		this.dispatchId = dispatchId;
		this.orderfield = orderfield;
		this.reqRemark = reqRemark;
		this.surePiece = surePiece;
		this.splitNum = splitNum;
		this.arrivedPiece = arrivedPiece;
		this.bulk = bulk;
		this.addr = addr;
		this.consignee = consignee;
		this.overmemoNo = overmemoNo;
		this.overmemoDetailId = overmemoDetailId;
		this.distributionMode = distributionMode;
		this.takeMode = takeMode;
		this.realPiece = realPiece;
		this.requestDoId = requestDoId;
	}

	public OprFaxInSureVo(Long dno, String cpName, String flightNo,
			String flightMainNo, String subNo, String receiptType, Long piece,
			Double cqWeight, Long status, String goods, String request,
			Long isOpr, String addr, String consignee, Double bulk,
			String distributionMode, String takeMode, Long requestDoId,
			String reqRemark, Long cusId) {
		super();
		this.dno = dno;
		this.cpName = cpName;
		this.flightNo = flightNo;
		this.flightMainNo = flightMainNo;
		this.subNo = subNo;
		this.receiptType = receiptType;
		this.piece = piece;
		this.cqWeight = cqWeight;
		this.status = status;
		this.goods = goods;
		this.request = request;
		this.isOpr = isOpr;
		this.addr = addr;
		this.consignee = consignee;
		this.bulk = bulk;
		this.distributionMode = distributionMode;
		this.takeMode = takeMode;
		this.requestDoId = requestDoId;
		this.reqRemark = reqRemark;
		this.cusId = cusId;
	}

	public OprFaxInSureVo(Long dno) {
		super();
		this.dno = dno;
	}

	public OprFaxInSureVo() {
		super();
	}

}
