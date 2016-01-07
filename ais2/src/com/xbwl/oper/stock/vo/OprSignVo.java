package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 *@author LiuHao
 *@time Jul 30, 2011 9:50:43 AM
 */
public class OprSignVo implements java.io.Serializable{
	
	private Long id;
	private Long dno;//配送单号
	private String signMan;//签收人
	private String identityCard;//身份证
	private String replaceSign;//代签人
	private String reIdentityCard;//代签人身份证
	private String scanAdd;//身份证扫描地址
	private String cardType;//证件类型
	private String signSource;
	private String createName;
	private Date createTime;
	private Date updateTime;
	private String updateName;
	private String ts;
	private Long departId;//录签收部门
	private String remark;
	
	private Long cusId;
	private String cusName;//代理公司
	private String mainNo;//主单号
	private String subNo;//分单号
	private String flightNo;//航班号
	private String distributionMode;//配送方式
	private String takeMode;//提货方式
	private String consignee;//收货人名称
	private String consigneeTel;//收货人电话
	private String addr;//收货人地址
	private String signType;//签收类型
	private Date faxTime;//传真日期
	private Long signStatus;//签收状态
	
	private Long faxStatus;//传真状态 0作废、1正常
	
	private Long isSignException;//是否异常签收
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the dno
	 */
	public Long getDno() {
		return dno;
	}
	/**
	 * @param dno the dno to set
	 */
	public void setDno(Long dno) {
		this.dno = dno;
	}
	/**
	 * @return the signMan
	 */
	public String getSignMan() {
		return signMan;
	}
	/**
	 * @param signMan the signMan to set
	 */
	public void setSignMan(String signMan) {
		this.signMan = signMan;
	}
	/**
	 * @return the identityCard
	 */
	public String getIdentityCard() {
		return identityCard;
	}
	/**
	 * @param identityCard the identityCard to set
	 */
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	/**
	 * @return the replaceSign
	 */
	public String getReplaceSign() {
		return replaceSign;
	}
	/**
	 * @param replaceSign the replaceSign to set
	 */
	public void setReplaceSign(String replaceSign) {
		this.replaceSign = replaceSign;
	}
	/**
	 * @return the reIdentityCard
	 */
	public String getReIdentityCard() {
		return reIdentityCard;
	}
	/**
	 * @param reIdentityCard the reIdentityCard to set
	 */
	public void setReIdentityCard(String reIdentityCard) {
		this.reIdentityCard = reIdentityCard;
	}
	/**
	 * @return the scanAdd
	 */
	public String getScanAdd() {
		return scanAdd;
	}
	/**
	 * @param scanAdd the scanAdd to set
	 */
	public void setScanAdd(String scanAdd) {
		this.scanAdd = scanAdd;
	}
	/**
	 * @return the signSource
	 */
	public String getSignSource() {
		return signSource;
	}
	/**
	 * @param signSource the signSource to set
	 */
	public void setSignSource(String signSource) {
		this.signSource = signSource;
	}
	/**
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}
	/**
	 * @param createName the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	/**
	 * @return the createTime
	 */
	@JSON(format = "yyyy-MM-dd HH:mm")
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateTime
	 */
	@JSON(format = "yyyy-MM-dd HH:mm")
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the updateName
	 */
	public String getUpdateName() {
		return updateName;
	}
	/**
	 * @param updateName the updateName to set
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	/**
	 * @return the ts
	 */
	public String getTs() {
		return ts;
	}
	/**
	 * @param ts the ts to set
	 */
	public void setTs(String ts) {
		this.ts = ts;
	}
	/**
	 * @return the departId
	 */
	public Long getDepartId() {
		return departId;
	}
	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	/**
	 * @return the cusId
	 */
	public Long getCusId() {
		return cusId;
	}
	/**
	 * @param cusId the cusId to set
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	/**
	 * @return the mainNo
	 */
	public String getMainNo() {
		return mainNo;
	}
	/**
	 * @param mainNo the mainNo to set
	 */
	public void setMainNo(String mainNo) {
		this.mainNo = mainNo;
	}
	/**
	 * @return the subNo
	 */
	public String getSubNo() {
		return subNo;
	}
	/**
	 * @param subNo the subNo to set
	 */
	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}
	/**
	 * @return the flightNo
	 */
	public String getFlightNo() {
		return flightNo;
	}
	/**
	 * @param flightNo the flightNo to set
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	/**
	 * @return the distributionDepart
	 */
	public String getDistributionMode() {
		return distributionMode;
	}
	/**
	 * @param distributionDepart the distributionDepart to set
	 */
	public void setDistributionMode(String distributionDepart) {
		this.distributionMode = distributionDepart;
	}
	/**
	 * @return the takeMode
	 */
	public String getTakeMode() {
		return takeMode;
	}
	/**
	 * @param takeMode the takeMode to set
	 */
	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}
	/**
	 * @return the consignee
	 */
	public String getConsignee() {
		return consignee;
	}
	/**
	 * @param consignee the consignee to set
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	/**
	 * @return the consigneeTel
	 */
	public String getConsigneeTel() {
		return consigneeTel;
	}
	/**
	 * @param consigneeTel the consigneeTel to set
	 */
	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel;
	}
	/**
	 * @return the signType
	 */
	public String getSignType() {
		return signType;
	}
	/**
	 * @param signType the signType to set
	 */
	public void setSignType(String signType) {
		this.signType = signType;
	}
	/**
	 * @return the faxTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getFaxTime() {
		return faxTime;
	}
	/**
	 * @param faxTime the faxTime to set
	 */
	public void setFaxTime(Date faxTime) {
		this.faxTime = faxTime;
	}
	/**
	 * @return the cusName
	 */
	public String getCusName() {
		return cusName;
	}
	/**
	 * @param cusName the cusName to set
	 */
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}
	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	/**
	 * @return the signStatus
	 */
	public Long getSignStatus() {
		return signStatus;
	}
	/**
	 * @param signStatus the signStatus to set
	 */
	public void setSignStatus(Long signStatus) {
		this.signStatus = signStatus;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getIsSignException() {
		return isSignException;
	}
	public void setIsSignException(Long isSignException) {
		this.isSignException = isSignException;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Long getFaxStatus() {
		return faxStatus;
	}
	public void setFaxStatus(Long faxStatus) {
		this.faxStatus = faxStatus;
	}
	
}
