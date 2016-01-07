package com.xbwl.entity;

// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.common.utils.XbwlInt;

/**
 * Ӧ��Ӧ����.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_PAYMENT")
public class FiPayment implements java.io.Serializable,AuditableEntity {
	private Long id;
	private Long paymentType; //�ո�����(1:�տ/2:���)(�������)
	private Long paymentStatus=0L; //�ո�״̬��0���ϡ�1δ�տ2���տ3�����տ4δ���5�Ѹ��6���ָ��7����תǷ�8�쳣��9����
	private String costType; //��������:���ջ���/�������ͷ�/������ֵ��/Ԥ�����ͷ�/Ԥ����ֵ��/��������/����(�������)
	private String documentsType; //���ݴ���:����\�ɱ�\����\Ԥ���\���ջ���(�������)
	private String documentsSmalltype; //����С�ࣺ���͵�/���˵�/���͵�/Ԥ��(�������)
	private Long documentsNo; //����С���Ӧ�ĵ���(�������)
	private String penyType; //��������(�ֽᡢ�½�)(�������)
	private String penyJenis; //���㷽ʽ(�ֽ����С�POS����֧Ʊ��Ԥ��������Ӧ����Ӧ��)
	private Double amount=0.0; //Ӧ�ո����(�������)
	private Double settlementAmount=0.0; //ʵ�ո����
	private Double abnormalAmount=0.0;//�쳣��������
	private Double eliminationAmount=0.0; //�������
	private String workflowNo;//���̺�
	private String contacts; //������λ:û�ڿ��̵����еĿ��̣��ڲ��ͻ���
	private Long customerId; //����id(�������)
	private String customerName; //���̱�����(�������)
	private String sourceData; //������Դ(�������)
	private Long sourceNo; //��Դ����(�������)
	private String collectionUser; //�տ�������:���᣺�����ˣ��ͻ����ͻ�Ա���ⷢ���ⷢԱ(�������)
	private Long paymentMark; //�ո�����:����Ա:0/����:1
	private String entrustDept; //ί�в���
	private Date entrustTime; //ί��ʱ��
	private String entrustUser; //ί����
	private String entrustRemark; //ί�б�ע
	private String createName; 
	private Date createTime; 
	private String createRemark; 
	
	@XbwlInt(autoDepart=false)
	private Long departId; //����ҵ����id(�������)
	
	@XbwlInt(autoDepart=false)
	private String departName;//����ҵ����
	private Long entrustDeptid;  //ί�в���ID
	private String updateDept; 
	private Date updateTime; 
	private String updateName; 
	private String ts; 
	private Long status=1l;//״̬(0:����,1:����)
	private Long incomeDepartId;//���벿��
	private Long reviewStatus=1L;//���״̬(0δ���,1�����)
	private String reviewUser;//�����
	private Date reviewDate;//���ʱ��

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_PAYMENT")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PAYMENT_TYPE", precision = 22, scale = 0)
	public Long getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "PAYMENT_STATUS", precision = 22, scale = 0)
	public Long getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Column(name = "COST_TYPE", length = 20)
	public String getCostType() {
		return this.costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	@Column(name = "DOCUMENTS_TYPE", length = 20)
	public String getDocumentsType() {
		return this.documentsType;
	}

	public void setDocumentsType(String documentsType) {
		this.documentsType = documentsType;
	}

	@Column(name = "DOCUMENTS_SMALLTYPE", length = 20)
	public String getDocumentsSmalltype() {
		return this.documentsSmalltype;
	}

	public void setDocumentsSmalltype(String documentsSmalltype) {
		this.documentsSmalltype = documentsSmalltype;
	}

	@Column(name = "DOCUMENTS_NO", precision = 22, scale = 0)
	public Long getDocumentsNo() {
		return this.documentsNo;
	}

	public void setDocumentsNo(Long documentsNo) {
		this.documentsNo = documentsNo;
	}

	@Column(name = "PENY_TYPE", length = 20)
	public String getPenyType() {
		return this.penyType;
	}

	public void setPenyType(String penyType) {
		this.penyType = penyType;
	}

	@Column(name = "PENY_JENIS", length = 20)
	public String getPenyJenis() {
		return this.penyJenis;
	}

	public void setPenyJenis(String penyJenis) {
		this.penyJenis = penyJenis;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "SETTLEMENT_AMOUNT", precision = 10, scale = 0)
	public Double getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	
	@Column(name = "ELIMINATION_AMOUNT", precision = 10, scale = 0)
	public Double getEliminationAmount() {
		return eliminationAmount;
	}

	public void setEliminationAmount(Double eliminationAmount) {
		this.eliminationAmount = eliminationAmount;
	}

	@Column(name = "WORKFLOW_NO", length = 20)
	public String getWorkflowNo() {
		return workflowNo;
	}

	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	@Column(name = "CONTACTS", length = 50)
	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", length = 50)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "SOURCE_DATA", length = 20)
	public String getSourceData() {
		return this.sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	@Column(name = "SOURCE_NO", precision = 22, scale = 0)
	public Long getSourceNo() {
		return this.sourceNo;
	}

	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}

	@Column(name = "COLLECTION_USER", length = 20)
	public String getCollectionUser() {
		return this.collectionUser;
	}

	public void setCollectionUser(String collectionUser) {
		this.collectionUser = collectionUser;
	}

	@Column(name = "PAYMENT_MARK", precision = 22, scale = 0)
	public Long getPaymentMark() {
		return this.paymentMark;
	}

	public void setPaymentMark(Long paymentMark) {
		this.paymentMark = paymentMark;
	}

	@Column(name = "ENTRUST_DEPT", length = 50)
	public String getEntrustDept() {
		return this.entrustDept;
	}

	public void setEntrustDept(String entrustDept) {
		this.entrustDept = entrustDept;
	}

	@Column(name = "ENTRUST_TIME", length = 7)
	public Date getEntrustTime() {
		return this.entrustTime;
	}

	public void setEntrustTime(Date entrustTime) {
		this.entrustTime = entrustTime;
	}

	@Column(name = "ENTRUST_USER", length = 20)
	public String getEntrustUser() {
		return this.entrustUser;
	}

	public void setEntrustUser(String entrustUser) {
		this.entrustUser = entrustUser;
	}

	@Column(name = "ENTRUST_REMARK", length = 500)
	public String getEntrustRemark() {
		return this.entrustRemark;
	}

	public void setEntrustRemark(String entrustRemark) {
		this.entrustRemark = entrustRemark;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_REMARK", length = 500)
	public String getCreateRemark() {
		return this.createRemark;
	}

	public void setCreateRemark(String createRemark) {
		this.createRemark = createRemark;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return departName;
	}
	
	
	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "ENTRUST_DEPTID", precision = 22, scale = 0)
	public Long getEntrustDeptid() {
		return this.entrustDeptid;
	}

	public void setEntrustDeptid(Long entrustDeptid) {
		this.entrustDeptid = entrustDeptid;
	}

	@Column(name = "UPDATE_DEPT", length = 50)
	public String getUpdateDept() {
		return this.updateDept;
	}

	public void setUpdateDept(String updateDept) {
		this.updateDept = updateDept;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "ABNORMAL_AMOUNT", precision = 10, scale = 0)
	public Double getAbnormalAmount() {
		return abnormalAmount;
	}

	public void setAbnormalAmount(Double abnormalAmount) {
		this.abnormalAmount = abnormalAmount;
	}

	@Column(name = "INCOME_DEPART_ID", precision = 22, scale = 0)
	public Long getIncomeDepartId() {
		return incomeDepartId;
	}

	public void setIncomeDepartId(Long incomeDepartId) {
		this.incomeDepartId = incomeDepartId;
	}

	@Column(name = "REVIEW_STATUS", precision = 22, scale = 0)
	public Long getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Long reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@Column(name = "REVIEW_USER", length = 20)
	public String getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "REVIEW_DATE", length = 7)
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	
	

}