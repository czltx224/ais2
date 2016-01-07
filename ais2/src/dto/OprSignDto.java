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
	private Long dno;//���͵��� 
	private String signMan;//ǩ����
	private String identityCard;//���֤����
	private String replaceSign;//��ǩ��
	private String reIdentityCard;//��ǩ���֤����
	private String scanAdd;//ɨ�����֤��ַ
	private String createName;//������
	private Date createTime;//����ʱ��
	private Date updateTime;//�޸���
	private String updateName;//�޸�ʱ��
	private String ts;
	
	private Long departId;//ǩ�ղ���
	private String signSource;//ǩ����Դ
	private String cardType;//֤������
	private String remark;//�쳣��ע
	
	private Long isSignException;//�Ƿ�ǩ���쳣 0��������1���쳣

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