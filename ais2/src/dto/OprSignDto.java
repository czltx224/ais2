package dto;

import java.util.Date;

/**
 * OprSign entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class OprSignDto implements java.io.Serializable {

	// Fields

	private Long id;
	private Long dno;//配送单号 
	private String signMan;//签收人
	private String identityCard;//身份证号码
	private String replaceSign;//代签人
	private String reIdentityCard;//代签身份证号码
	private String scanAdd;//扫描身份证地址
	private String createName;//创建人
	private Date createTime;//创建时间
	private Date updateTime;//修改人
	private String updateName;//修改时间
	private String ts;
	
	private Long departId;//签收部门
	private String signSource;//签收来源
	private String cardType;//证件类型
	private String remark;//异常备注
	
	private Long isSignException;//是否签收异常 0：正常，1：异常

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

	public String getSignMan() {
		return signMan;
	}

	public void setSignMan(String signMan) {
		this.signMan = signMan;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getReplaceSign() {
		return replaceSign;
	}

	public void setReplaceSign(String replaceSign) {
		this.replaceSign = replaceSign;
	}

	public String getReIdentityCard() {
		return reIdentityCard;
	}

	public void setReIdentityCard(String reIdentityCard) {
		this.reIdentityCard = reIdentityCard;
	}

	public String getScanAdd() {
		return scanAdd;
	}

	public void setScanAdd(String scanAdd) {
		this.scanAdd = scanAdd;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public String getSignSource() {
		return signSource;
	}

	public void setSignSource(String signSource) {
		this.signSource = signSource;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getIsSignException() {
		return isSignException;
	}

	public void setIsSignException(Long isSignException) {
		this.isSignException = isSignException;
	}
}