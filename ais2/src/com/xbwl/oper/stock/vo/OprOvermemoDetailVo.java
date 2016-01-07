package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili time Jul 4, 2011 9:28:29 AM
 */
public class OprOvermemoDetailVo implements java.io.Serializable {

	private Long idd;// ���ӵ���1
	private Long overmemoId;// ����������2
	private Long dno;// ���͵���3
	private String subNo;// �ֵ���4
	private Long realPiece;// ʵ������5
	private Long piece;// Ӧ������6
	private Long cusId;// �����������ID 7
	private String cpName;// �������� 7
	private Long weight;// ����8
	private String flightNo;// �����9
	private String consignee;// �ջ���10
	private String addr;// �ջ��˵�ַ11
	private String goods;// Ʒ��12
	private String stockAreaName; // �����������13
	private Date createTime;
	private Long carId;// ���1
	private String carCode;// ���ƺ�2
	private Long status;

	private Long loadingbrigadeId;// װж��13
	private String loadingbrigadeName;// װж������13
	private Long dispatchId;// �ֲ���ID
	private String dispatchGroup;// �ֲ���14
	private String gowhere;// ȥ��
	private Long loadingbrigadeWeightId;// װж�������ID
	private Long requestDoId;// ���Ի�Ҫ��ID
	private String flightMainNo;
	
	private Long endDepartId;
	
	private String goodsStatus;//���ﵱǰ״̬
	
	public Long getIdd() {
		return idd;
	}

	public void setIdd(Long idd) {
		this.idd = idd;
	}

	public Long getOvermemoId() {
		return overmemoId;
	}

	public void setOvermemoId(Long overmemoId) {
		this.overmemoId = overmemoId;
	}

	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	public Long getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}

	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
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

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getStockAreaName() {
		return stockAreaName;
	}

	public void setStockAreaName(String stockAreaName) {
		this.stockAreaName = stockAreaName;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getLoadingbrigadeId() {
		return loadingbrigadeId;
	}

	public void setLoadingbrigadeId(Long loadingbrigadeId) {
		this.loadingbrigadeId = loadingbrigadeId;
	}

	public String getDispatchGroup() {
		return dispatchGroup;
	}

	public void setDispatchGroup(String dispatchGroup) {
		this.dispatchGroup = dispatchGroup;
	}

	public String getGowhere() {
		return gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	public String getLoadingbrigadeName() {
		return loadingbrigadeName;
	}

	public void setLoadingbrigadeName(String loadingbrigadeName) {
		this.loadingbrigadeName = loadingbrigadeName;
	}

	public Long getLoadingbrigadeWeightId() {
		return loadingbrigadeWeightId;
	}

	public void setLoadingbrigadeWeightId(Long loadingbrigadeWeightId) {
		this.loadingbrigadeWeightId = loadingbrigadeWeightId;
	}

	public Long getRequestDoId() {
		return requestDoId;
	}

	public void setRequestDoId(Long requestDoId) {
		this.requestDoId = requestDoId;
	}

	public Long getEndDepartId() {
		return endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	public OprOvermemoDetailVo(Long idd) {
		super();
		this.idd = idd;
	}

	public OprOvermemoDetailVo() {
		super();
	}

	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public Long getDispatchId() {
		return dispatchId;
	}

	public void setDispatchId(Long dispatchId) {
		this.dispatchId = dispatchId;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
}
