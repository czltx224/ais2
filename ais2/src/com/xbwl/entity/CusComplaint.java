package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * CusComplaint entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_COMPLAINT")
public class CusComplaint implements java.io.Serializable,AuditableEntity {

	// Fields

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
	
	// Constructors

	/** default constructor */
	public CusComplaint() {
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_COMPLAINT ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "COMPLAINT_CUS", length = 50)
	public String getComplaintCus() {
		return this.complaintCus;
	}

	public void setComplaintCus(String complaintCus) {
		this.complaintCus = complaintCus;
	}

	@Column(name = "COMPLAINT_DNO", nullable = false, length = 20)
	public String getComplaintDno() {
		return this.complaintDno;
	}

	public void setComplaintDno(String complaintDno) {
		this.complaintDno = complaintDno;
	}
/*
	@Column(name = "COMPLAINT_TIME", nullable = false, length = 15)
	public String getComplaintTime() {
		return this.complaintTime;
	}

	public void setComplaintTime(String complaintTime) {
		this.complaintTime = complaintTime;
	}*/

	@Column(name = "COMPLAINT_TYPE", nullable = false, length = 20)
	public String getComplaintType() {
		return this.complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	@Column(name = "COMPLAINT_CONTEXT", nullable = false, length = 500)
	public String getComplaintContext() {
		return this.complaintContext;
	}

	public void setComplaintContext(String complaintContext) {
		this.complaintContext = complaintContext;
	}

	@Column(name = "COMPLAINT_LEVEL", nullable = false, precision = 22, scale = 0)
	public Long getComplaintLevel() {
		return this.complaintLevel;
	}

	public void setComplaintLevel(Long complaintLevel) {
		this.complaintLevel = complaintLevel;
	}

	@Column(name = "APPELLATE_NAME", nullable = false, length = 20)
	public String getAppellateName() {
		return this.appellateName;
	}

	public void setAppellateName(String appellateName) {
		this.appellateName = appellateName;
	}
	@Column(name = "IS_ACCEPT", precision = 22, scale = 0)
	public Long getIsAccept() {
		return this.isAccept;
	}

	public void setIsAccept(Long isAccept) {
		this.isAccept = isAccept;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "ACCEPT_TIME", length = 7)
	public Date getAcceptTime() {
		return this.acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	@Column(name = "ACCEPT_NAME", length = 20)
	public String getAcceptName() {
		return this.acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	@Column(name = "FILE_PATH", length = 500)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "DUTY_NAME", length = 50)
	public String getDutyName() {
		return this.dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	@Column(name = "DEAL_RESULT", length = 500)
	public String getDealResult() {
		return this.dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

	@Column(name = "DEAL_COST", precision = 22, scale = 0)
	public Double getDealCost() {
		return this.dealCost;
	}

	public void setDealCost(Double dealCost) {
		this.dealCost = dealCost;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "CUS_RECORD_ID", nullable = false, precision = 22, scale = 0)
	public Long getCusRecordId() {
		return this.cusRecordId;
	}

	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "DUTY_TIME", length = 7)
	public Date getDutyTime() {
		return this.dutyTime;
	}

	public void setDutyTime(Date dutyTime) {
		this.dutyTime = dutyTime;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "COMPLAINT_DATE", length = 7)
	public Date getComplaintDate() {
		return this.complaintDate;
	}

	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}

	@Column(name = "COMPLAINT_TEL", length = 50)
	public String getComplaintTel() {
		return this.complaintTel;
	}

	public void setComplaintTel(String complaintTel) {
		this.complaintTel = complaintTel;
	}

	@Column(name = "COMPLAINT_NAME", length = 50)
	public String getComplaintName() {
		return this.complaintName;
	}

	public void setComplaintName(String complaintName) {
		this.complaintName = complaintName;
	}

	@Column(name = "COMPLAINT_REQUIRE", length = 100)
	public String getComplaintRequire() {
		return this.complaintRequire;
	}

	public void setComplaintRequire(String complaintRequire) {
		this.complaintRequire = complaintRequire;
	}

	@Column(name = "COMPLAINT_PROMISE", length = 100)
	public String getComplaintPromise() {
		return this.complaintPromise;
	}

	public void setComplaintPromise(String complaintPromise) {
		this.complaintPromise = complaintPromise;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "REPLY_DATE", length = 7)
	public Date getReplyDate() {
		return this.replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	@Column(name = "REPLY_TIME", length = 10)
	public String getReplyTime() {
		return this.replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "ACTUAL_DUTY_TIME", length = 7)
	public Date getActualDutyTime() {
		return this.actualDutyTime;
	}

	public void setActualDutyTime(Date actualDutyTime) {
		this.actualDutyTime = actualDutyTime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "AGAIN_TIME", length = 7)
	public Date getAgainTime() {
		return this.againTime;
	}

	public void setAgainTime(Date againTime) {
		this.againTime = againTime;
	}

	@Column(name = "COMPLAINT_FEEDBACK", length = 500)
	public String getComplaintFeedback() {
		return this.complaintFeedback;
	}

	public void setComplaintFeedback(String complaintFeedback) {
		this.complaintFeedback = complaintFeedback;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the cusPleased
	 */
	@Column(name = "CUS_PLEASED")
	public Long getCusPleased() {
		return cusPleased;
	}

	/**
	 * @param cusPleased the cusPleased to set
	 */
	public void setCusPleased(Long cusPleased) {
		this.cusPleased = cusPleased;
	}

	/**
	 * @return the spendTime
	 */
	@Column(name = "SPEND_TIME")
	public Long getSpendTime() {
		return spendTime;
	}

	/**
	 * @param spendTime the spendTime to set
	 */
	public void setSpendTime(Long spendTime) {
		this.spendTime = spendTime;
	}

	/**
	 * @return the compliantManType
	 */
	@Column(name="COMPLIANT_MAN_TYPE")
	public String getCompliantManType() {
		return compliantManType;
	}

	/**
	 * @param compliantManType the compliantManType to set
	 */
	public void setCompliantManType(String compliantManType) {
		this.compliantManType = compliantManType;
	}

	@Column(name="FK_PS_COMPLAINT")
	public String getFkPsComplaint() {
		return fkPsComplaint;
	}

	public void setFkPsComplaint(String fkPsComplaint) {
		this.fkPsComplaint = fkPsComplaint;
	}
}