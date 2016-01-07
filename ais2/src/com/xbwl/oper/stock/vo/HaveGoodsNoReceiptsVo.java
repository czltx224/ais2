package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author CaoZhili time Aug 15, 2011 9:54:10 AM
 */
public class HaveGoodsNoReceiptsVo implements java.io.Serializable {

	private Long id;
	private Long oprOvermemoId;// ���ӵ�������
	private Long dno;// ���͵���
	private Long piece;// Ӧ������
	private Long realPiece;// Ӧ������
	private Long cusId;// �����������ID
	private String cpName;// ��������
	private Double weight;// ����
	private String subNo;// �ֵ���
	private String flightNo;// �����
	private String consignee;// �ջ���
	private String addr;// �ջ��˵�ַ
	private String distributionMode;// ���ͷ�ʽ
	private String takeMode;// �����ʽ
	private String flightMainNo;// ������
	private String goods;// Ʒ��
	private String storageArea;// �������
	private Long status;// �㵽״̬ 0,δ�㵽��1�Ѿ��㵽

	private Date endTime;// ����ʱ�䣬���㵽���ڣ�
	private String startDepartName;//�������(ʼ������)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOprOvermemoId() {
		return oprOvermemoId;
	}

	public void setOprOvermemoId(Long oprOvermemoId) {
		this.oprOvermemoId = oprOvermemoId;
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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
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

	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getStorageArea() {
		return storageArea;
	}

	public void setStorageArea(String storageArea) {
		this.storageArea = storageArea;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getStartDepartName() {
		return startDepartName;
	}

	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}

}
