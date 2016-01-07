package dto;

import java.util.Date;


/**
 * @project ais_edi
 * @author czl
 * @Time Mar 7, 2012 6:03:52 PM
 */
public class CusComplaintDto implements java.io.Serializable {

	private Long id;
	private String complaintCus;//投诉对应客户
	private String complaintDno;//投诉单号
	//private String complaintTime;//投诉时间
	private String complaintType;//投诉类型
	private String complaintContext;//投诉内容
	private Long complaintLevel;//投诉等级
	private String appellateName;//首问接待人
	//private Date appellateTime;//接待时间
	private Long isAccept;//是否采纳
	private Date acceptTime;//采纳时间
	private String acceptName;//采纳人
	private String filePath;//附件
	private String dutyName;//处理责任人
	private String dealResult;//处理结果
	private Double dealCost;//处理成本
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long cusRecordId;//客户编号
	private Long status;
	private Date dutyTime;//要求处理时间
	private Long departId;
	private Date complaintDate;//投诉日期
	private String complaintTel;//投诉电话
	private String complaintName;//投诉人
	private String complaintRequire;//客户要求
	private String complaintPromise;//客服承诺项
	private Date replyDate;//要求回复日期
	private String replyTime;//要求回复时间
	private Date actualDutyTime;//实际处理时间
	private Date againTime;//回访时间
	private String complaintFeedback;//客户反馈
	private String remark;//备注
	private Long cusPleased;//满意程度
	private Long spendTime;//花费时间
	
	private String compliantManType;//投诉人类型
	private String fkPsComplaint;//网营投诉建议表关联外键

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
