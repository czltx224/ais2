package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author CaoZhili time Aug 8, 2011 10:08:10 AM
 */
public class OprMainOrderAdjustVo implements java.io.Serializable {

	private Long fdno;// ���͵���
	private Long cusId;// ����˾���̱���
	private String cpName;// ����˾��������
	private String subNo;// �ֵ���
	private String flightNo;// �����
	private String flightMainNo;// ����������
	private String flightTime;// ����ʱ��
	private Date flightDate;// ��������
	private String addr;// �ջ��˵�ַ
	private String consignee;// �ջ�������
	private String gowhere;// ȥ��
	private Long piece;// ����
	private Double consigneeRate;// �ջ���Ӧ������
	private Double consigneeFee;// �ջ���Ӧ����
	private Double bulk;// ���
	private String goods;// Ʒ��
	private String goodsStatus;// ��������״̬
	private Long inDepartId;
	private Double cusWeight;// �Ʒ�����

	private Long id;// ���
	private Long dno;// ���͵���
	private String oldFlightMainNo;// ��������
	private String newFlightMainNo;// ��������
	private Double oldWeight;// ������
	private Double newWeight;// ������
	private Double oldConsigneeFee;// �����ͷ�
	private Double newConsigneeFee;// �����ͷ�
	private Double adjustMoney;// ��������
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private Long departId;
	private String ts;
	private String departName;

	private String whoCash;// ���ø��

	public Long getFdno() {
		return fdno;
	}

	public void setFdno(Long fdno) {
		this.fdno = fdno;
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

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

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

	public String getGowhere() {
		return gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public Double getConsigneeRate() {
		return consigneeRate;
	}

	public void setConsigneeRate(Double consigneeRate) {
		this.consigneeRate = consigneeRate;
	}

	public Double getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

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

	public String getOldFlightMainNo() {
		return oldFlightMainNo;
	}

	public void setOldFlightMainNo(String oldFlightMainNo) {
		this.oldFlightMainNo = oldFlightMainNo;
	}

	public String getNewFlightMainNo() {
		return newFlightMainNo;
	}

	public void setNewFlightMainNo(String newFlightMainNo) {
		this.newFlightMainNo = newFlightMainNo;
	}

	public Double getOldWeight() {
		return oldWeight;
	}

	public void setOldWeight(Double oldWeight) {
		this.oldWeight = oldWeight;
	}

	public Double getNewWeight() {
		return newWeight;
	}

	public void setNewWeight(Double newWeight) {
		this.newWeight = newWeight;
	}

	public Double getOldConsigneeFee() {
		return oldConsigneeFee;
	}

	public void setOldConsigneeFee(Double oldConsigneeFee) {
		this.oldConsigneeFee = oldConsigneeFee;
	}

	public Double getNewConsigneeFee() {
		return newConsigneeFee;
	}

	public void setNewConsigneeFee(Double newConsigneeFee) {
		this.newConsigneeFee = newConsigneeFee;
	}

	public Double getAdjustMoney() {
		return adjustMoney;
	}

	public void setAdjustMoney(Double adjustMoney) {
		this.adjustMoney = adjustMoney;
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

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Long getInDepartId() {
		return inDepartId;
	}

	public void setInDepartId(Long inDepartId) {
		this.inDepartId = inDepartId;
	}

	public Double getCusWeight() {
		return cusWeight;
	}

	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}

	public String getWhoCash() {
		return whoCash;
	}

	public void setWhoCash(String whoCash) {
		this.whoCash = whoCash;
	}

}
