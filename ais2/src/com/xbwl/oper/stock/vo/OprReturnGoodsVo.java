package com.xbwl.oper.stock.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author CaoZhili time Jul 30, 2011 10:54:30 AM
 */
public class OprReturnGoodsVo implements Serializable {

	private Long id;// ���
	private Long dno;// ���͵���
	private Long returnNum;// ��������
	private String returnType;// ��������
	private Long outNo;// ��������
	private String dutyParty;// ���η�
	private Double consigneeFee;// ���ͷ�
	private Double paymentCollection;// ���տ�
	private String returnComment;// ������ע
	private Double returnCost;// �����ɱ�
	private String returnDepartName;// ������������
	private Long returnDepart;// ��������
	private String createName;// ������
	private Date createTime;// ����ʱ��
	private String updateName;// �޸���
	private Date updateTime;// �޸�ʱ��
	private String ts;// ʱ���
	private String outType;// �ͻ�����
	private Long status;//0,ɾ����1��������2��������⣬3����������
	private Long auditStatus;//���״̬ 0��δ��ˣ�1�������

	private String cpName;// ����˾��������
	private String flightNo;// �����
	private String consignee;// �ջ�������
	private String consigneeTel;// �ջ��˵绰
	private String addr;// �ջ��˵�ַ
	private Double cqWeight;// �Ʒ�����
	private String realGoWhere;// ȥ��
	private Double cusValueAddFee;// �ͻ���ֵ�����
	private String distributionMode;// ���ͷ�ʽ
	private String takeMode;// ���ͷ�ʽ
	private Double bulk;// ���
	private Long faxPiece;//¼����
	private Double disWeight;//�ۺ�����

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

	public Long getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(Long returnNum) {
		this.returnNum = returnNum;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Long getOutNo() {
		return outNo;
	}

	public void setOutNo(Long outNo) {
		this.outNo = outNo;
	}

	public String getDutyParty() {
		return dutyParty;
	}

	public void setDutyParty(String dutyParty) {
		this.dutyParty = dutyParty;
	}

	public Double getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public Double getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getReturnComment() {
		return returnComment;
	}

	public void setReturnComment(String returnComment) {
		this.returnComment = returnComment;
	}

	public Double getReturnCost() {
		return returnCost;
	}

	public void setReturnCost(Double returnCost) {
		this.returnCost = returnCost;
	}

	public String getReturnDepartName() {
		return returnDepartName;
	}

	public void setReturnDepartName(String returnDepartName) {
		this.returnDepartName = returnDepartName;
	}

	public Long getReturnDepart() {
		return returnDepart;
	}

	public void setReturnDepart(Long returnDepart) {
		this.returnDepart = returnDepart;
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

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
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

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneeTel() {
		return consigneeTel;
	}

	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Double getCqWeight() {
		return cqWeight;
	}

	public void setCqWeight(Double cqWeight) {
		this.cqWeight = cqWeight;
	}

	public String getRealGoWhere() {
		return realGoWhere;
	}

	public void setRealGoWhere(String realGoWhere) {
		this.realGoWhere = realGoWhere;
	}

	public Double getCusValueAddFee() {
		return cusValueAddFee;
	}

	public void setCusValueAddFee(Double cusValueAddFee) {
		this.cusValueAddFee = cusValueAddFee;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	public Long getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Long auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Long getFaxPiece() {
		return faxPiece;
	}

	public void setFaxPiece(Long faxPiece) {
		this.faxPiece = faxPiece;
	}

	public Double getDisWeight() {
		return disWeight;
	}

	public void setDisWeight(Double disWeight) {
		this.disWeight = disWeight;
	}
	
}
