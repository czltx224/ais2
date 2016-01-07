package dto;

import java.util.Date;


/**
 * @project ais_edi
 * @author czl
 * @Time Mar 7, 2012 6:03:52 PM
 */
public class CusComplaintDto implements java.io.Serializable {

	private Long id;
	private String complaintCus;//Ͷ�߶�Ӧ�ͻ�
	private String complaintDno;//Ͷ�ߵ���
	//private String complaintTime;//Ͷ��ʱ��
	private String complaintType;//Ͷ������
	private String complaintContext;//Ͷ������
	private Long complaintLevel;//Ͷ�ߵȼ�
	private String appellateName;//���ʽӴ���
	//private Date appellateTime;//�Ӵ�ʱ��
	private Long isAccept;//�Ƿ����
	private Date acceptTime;//����ʱ��
	private String acceptName;//������
	private String filePath;//����
	private String dutyName;//����������
	private String dealResult;//������
	private Double dealCost;//����ɱ�
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long cusRecordId;//�ͻ����
	private Long status;
	private Date dutyTime;//Ҫ����ʱ��
	private Long departId;
	private Date complaintDate;//Ͷ������
	private String complaintTel;//Ͷ�ߵ绰
	private String complaintName;//Ͷ����
	private String complaintRequire;//�ͻ�Ҫ��
	private String complaintPromise;//�ͷ���ŵ��
	private Date replyDate;//Ҫ��ظ�����
	private String replyTime;//Ҫ��ظ�ʱ��
	private Date actualDutyTime;//ʵ�ʴ���ʱ��
	private Date againTime;//�ط�ʱ��
	private String complaintFeedback;//�ͻ�����
	private String remark;//��ע
	private Long cusPleased;//����̶�
	private Long spendTime;//����ʱ��
	
	private String compliantManType;//Ͷ��������
	private String fkPsComplaint;//��ӪͶ�߽����������

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComplaintCus() {
		return complaintCus;
	}

	public void setComplaintCus(String complaintCus) {
		this.complaintCus = complaintCus;
	}

	public String getComplaintDno() {
		return complaintDno;
	}

	public void setComplaintDno(String complaintDno) {
		this.complaintDno = complaintDno;
	}

	public String getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public String getComplaintContext() {
		return complaintContext;
	}

	public void setComplaintContext(String complaintContext) {
		this.complaintContext = complaintContext;
	}

	public Long getComplaintLevel() {
		return complaintLevel;
	}

	public void setComplaintLevel(Long complaintLevel) {
		this.complaintLevel = complaintLevel;
	}

	public String getAppellateName() {
		return appellateName;
	}

	public void setAppellateName(String appellateName) {
		this.appellateName = appellateName;
	}

	public Long getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(Long isAccept) {
		this.isAccept = isAccept;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getAcceptName() {
		return acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getDealResult() {
		return dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

	public Double getDealCost() {
		return dealCost;
	}

	public void setDealCost(Double dealCost) {
		this.dealCost = dealCost;
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

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

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

	public Long getCusRecordId() {
		return cusRecordId;
	}

	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getDutyTime() {
		return dutyTime;
	}

	public void setDutyTime(Date dutyTime) {
		this.dutyTime = dutyTime;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public Date getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}

	public String getComplaintTel() {
		return complaintTel;
	}

	public void setComplaintTel(String complaintTel) {
		this.complaintTel = complaintTel;
	}

	public String getComplaintName() {
		return complaintName;
	}

	public void setComplaintName(String complaintName) {
		this.complaintName = complaintName;
	}

	public String getComplaintRequire() {
		return complaintRequire;
	}

	public void setComplaintRequire(String complaintRequire) {
		this.complaintRequire = complaintRequire;
	}

	public String getComplaintPromise() {
		return complaintPromise;
	}

	public void setComplaintPromise(String complaintPromise) {
		this.complaintPromise = complaintPromise;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public Date getActualDutyTime() {
		return actualDutyTime;
	}

	public void setActualDutyTime(Date actualDutyTime) {
		this.actualDutyTime = actualDutyTime;
	}

	public Date getAgainTime() {
		return againTime;
	}

	public void setAgainTime(Date againTime) {
		this.againTime = againTime;
	}

	public String getComplaintFeedback() {
		return complaintFeedback;
	}

	public void setComplaintFeedback(String complaintFeedback) {
		this.complaintFeedback = complaintFeedback;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCusPleased() {
		return cusPleased;
	}

	public void setCusPleased(Long cusPleased) {
		this.cusPleased = cusPleased;
	}

	public Long getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(Long spendTime) {
		this.spendTime = spendTime;
	}

	public String getCompliantManType() {
		return compliantManType;
	}

	public void setCompliantManType(String compliantManType) {
		this.compliantManType = compliantManType;
	}

	public String getFkPsComplaint() {
		return fkPsComplaint;
	}

	public void setFkPsComplaint(String fkPsComplaint) {
		this.fkPsComplaint = fkPsComplaint;
	}
}
