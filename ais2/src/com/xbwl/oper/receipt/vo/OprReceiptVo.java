package com.xbwl.oper.receipt.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili time Jul 25, 2011 6:10:23 PM
 * 
 * �ص�VO
 */
public class OprReceiptVo implements Serializable {

	private Long id;// ���
	private Long dno;// ���͵���
	private Long printNo;// ��ӡ���ݺ�
	private Long printNum;// ��ӡ����
	private String receiptType;// �ص�����
	private Long reachStatus;// ��״̬
	private Long reachNum;// �𵥷���
	private String reachMan;// ���� ���㵽��
	private Date reachTime;// ��ʱ��
	private Long getStatus;// �쵥״̬
	private String getMan;// �쵥�� ��������
	private Date getTime;// �쵥ʱ��
	private Long confirmStatus;// �ص�ȷ��״̬ (0:δȷ�ϣ�1��������2���쳣)
	private Long confirmNum;// ȷ�շ���
	private String confirmMan;// ȷ����
	private Date confirmTime;// ȷ��ʱ��
	private String confirmRemark;// ȷ�ձ�ע(�쳣)
	private Long outStatus;// �ص��ĳ�״̬
	private String outWay;// �ص��ĳ�;�� ���������ֵ䣬��Ҫ���� �����棬�ʼģ���ݵȵ�
	private String outNo;// �ص��ĳ�����
	private String outMan;// �ص��ĳ���
	private Date outTime;// �ص��ĳ�ʱ��
	private String outCompany;// �ص��ĳ���˾
	private Double outCost;// �ص��ĳ�����
	private Long scanStauts;// �ص�ɨ��״̬
	private String scanMan;// �ص�ɨ����
	private Date scanTime;// �ص�ɨ��ʱ��
	private String scanAddr;// �ص�ɨ���ַ
	private String createName;// ������
	private Date createTime;// ����ʱ��
	private String updateName;// �޸���
	private Date updateTime;// �޸�ʱ��
	private String ts;// ʱ���
	private String curStatus;// �ص���ǰ״̬

	private String distributionMode;// ���ͷ�ʽ
	private String gowhere;// ȥ��

	private String cpName;// ����˾��������
	private Long prewiredId;// Ԥ�䵥��
	private Long overmemoId;// ʵ�䵥��
	private String request;// ����Ҫ��
	private Long urgentStatus;
	
	private String goodsStatus;

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

	public Long getPrintNo() {
		return printNo;
	}

	public void setPrintNo(Long printNo) {
		this.printNo = printNo;
	}

	public Long getPrintNum() {
		return printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public Long getReachStatus() {
		return reachStatus;
	}

	public void setReachStatus(Long reachStatus) {
		this.reachStatus = reachStatus;
	}

	public Long getReachNum() {
		return reachNum;
	}

	public void setReachNum(Long reachNum) {
		this.reachNum = reachNum;
	}

	public String getReachMan() {
		return reachMan;
	}

	public void setReachMan(String reachMan) {
		this.reachMan = reachMan;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getReachTime() {
		return reachTime;
	}

	public void setReachTime(Date reachTime) {
		this.reachTime = reachTime;
	}

	public Long getGetStatus() {
		return getStatus;
	}

	public void setGetStatus(Long getStatus) {
		this.getStatus = getStatus;
	}

	public String getGetMan() {
		return getMan;
	}

	public void setGetMan(String getMan) {
		this.getMan = getMan;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public Long getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(Long confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Long getConfirmNum() {
		return confirmNum;
	}

	public void setConfirmNum(Long confirmNum) {
		this.confirmNum = confirmNum;
	}

	public String getConfirmMan() {
		return confirmMan;
	}

	public void setConfirmMan(String confirmMan) {
		this.confirmMan = confirmMan;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getConfirmRemark() {
		return confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}

	public Long getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(Long outStatus) {
		this.outStatus = outStatus;
	}

	public String getOutWay() {
		return outWay;
	}

	public void setOutWay(String outWay) {
		this.outWay = outWay;
	}

	public String getOutNo() {
		return outNo;
	}

	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}

	public String getOutCompany() {
		return outCompany;
	}

	public void setOutCompany(String outCompany) {
		this.outCompany = outCompany;
	}

	public Double getOutCost() {
		return outCost;
	}

	public void setOutCost(Double outCost) {
		this.outCost = outCost;
	}

	public Long getScanStauts() {
		return scanStauts;
	}

	public void setScanStauts(Long scanStauts) {
		this.scanStauts = scanStauts;
	}

	public String getScanMan() {
		return scanMan;
	}

	public void setScanMan(String scanMan) {
		this.scanMan = scanMan;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public String getScanAddr() {
		return scanAddr;
	}

	public void setScanAddr(String scanAddr) {
		this.scanAddr = scanAddr;
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

	public String getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getGowhere() {
		return gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	public String getOutMan() {
		return outMan;
	}

	public void setOutMan(String outMan) {
		this.outMan = outMan;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public Long getPrewiredId() {
		return prewiredId;
	}

	public void setPrewiredId(Long prewiredId) {
		this.prewiredId = prewiredId;
	}

	public Long getOvermemoId() {
		return overmemoId;
	}

	public void setOvermemoId(Long overmemoId) {
		this.overmemoId = overmemoId;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public Long getUrgentStatus() {
		return urgentStatus;
	}

	public void setUrgentStatus(Long urgentStatus) {
		this.urgentStatus = urgentStatus;
	}
	
}
