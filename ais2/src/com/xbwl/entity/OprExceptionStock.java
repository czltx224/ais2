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
 * OprExceptionStock entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_EXCEPTION_STOCK")
public class OprExceptionStock implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long dno;//���͵���
	private Long piece;//������
	private Double weight;//�������
	private String consignee;//�ջ���
	private String cpName;//��������
	private String exceptionEnterName;//�쳣�����
	private Date exceptionEnterTime;//�쳣���ʱ��
	private String exceptionOutName;//�쳣������
	private Date exceptionOutTime;//�쳣����ʱ��
	private Long exceptionStatus;//״̬0�����ϣ�1��������2����⣬3������
	private Long departId;//ҵ����ID
	private String departName;//ҵ����
	private String consigneeAddr;//�ջ��˵�ַ
	private String outStockNo;//���ⵥ��
	private String gowhere;//ԭȥ��
	private String distributionMode;//���ͷ�ʽ
	private String returnType;//��������
	private String sourceType;//��Դ���� ��Ϊ����������ת�쳣���ȵȣ���Դ�����ֵ�
	private Double configneeFee;//�����ܶ�
	private Double cpFee;//Ԥ���ܶ�
	private Double paymentCollection;//���ջ���
	private String outStockObj;//�����������
	private String sourceNo;//��Դ����
	private Double addConfigneeFee;//׷�ӵ������ͷ�
	private Double addCpFee;//׷��Ԥ�����ͷ�
	private Double outCost;//����ɱ�
	private String createName;//������
	private Date createTime;//����ʱ��
	private String updateName;//�޸���
	private Date updateTime;//�޸�ʱ��
	private String ts;//ʱ���

	private String outSender;//�ͻ�Ա
	private String outLoad;//����;��
	private String outStockObjName;//�����������
	private String outRemark;//���ⱸע
	// Constructors

	public OprExceptionStock(Long id, Long dno, Long piece, Double weight,
			String consignee, String cpName, String exceptionEnterName,
			Date exceptionEnterTime, String exceptionOutName,
			Date exceptionOutTime, Long exceptionStatus, Long departId,
			String departName, String consigneeAddr, String outStockNo,
			String gowhere, String distributionMode, String returnType,
			String sourceType, Double configneeFee, Double cpFee,
			Double paymentCollection, String outStockObj, String sourceNo,
			Double addConfigneeFee, Double addCpFee, Double outCost,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts, String outSender, String outLoad,
			String outStockObjName, String outRemark) {
		super();
		this.id = id;
		this.dno = dno;
		this.piece = piece;
		this.weight = weight;
		this.consignee = consignee;
		this.cpName = cpName;
		this.exceptionEnterName = exceptionEnterName;
		this.exceptionEnterTime = exceptionEnterTime;
		this.exceptionOutName = exceptionOutName;
		this.exceptionOutTime = exceptionOutTime;
		this.exceptionStatus = exceptionStatus;
		this.departId = departId;
		this.departName = departName;
		this.consigneeAddr = consigneeAddr;
		this.outStockNo = outStockNo;
		this.gowhere = gowhere;
		this.distributionMode = distributionMode;
		this.returnType = returnType;
		this.sourceType = sourceType;
		this.configneeFee = configneeFee;
		this.cpFee = cpFee;
		this.paymentCollection = paymentCollection;
		this.outStockObj = outStockObj;
		this.sourceNo = sourceNo;
		this.addConfigneeFee = addConfigneeFee;
		this.addCpFee = addCpFee;
		this.outCost = outCost;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.outSender = outSender;
		this.outLoad = outLoad;
		this.outStockObjName = outStockObjName;
		this.outRemark = outRemark;
	}

	/** default constructor */
	public OprExceptionStock() {
	}

	/** minimal constructor */
	public OprExceptionStock(Long id) {
		this.id = id;
	}
	
	// Property accessors
	@Id   
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_EXCEPTION_STOCK",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "PIECE", precision = 7, scale = 0)
	public Long getPiece() {
		return this.piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	@Column(name = "WEIGHT", precision = 8)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "CONSIGNEE", length = 50)
	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column(name = "CP_NAME", length = 50)
	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	@Column(name = "EXCEPTION_ENTER_NAME", length = 50)
	public String getExceptionEnterName() {
		return this.exceptionEnterName;
	}

	public void setExceptionEnterName(String exceptionEnterName) {
		this.exceptionEnterName = exceptionEnterName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	@Column(name = "EXCEPTION_ENTER_TIME", length = 7)
	public Date getExceptionEnterTime() {
		return this.exceptionEnterTime;
	}

	public void setExceptionEnterTime(Date exceptionEnterTime) {
		this.exceptionEnterTime = exceptionEnterTime;
	}

	@Column(name = "EXCEPTION_OUT_NAME", length = 50)
	public String getExceptionOutName() {
		return this.exceptionOutName;
	}

	public void setExceptionOutName(String exceptionOutName) {
		this.exceptionOutName = exceptionOutName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	@Column(name = "EXCEPTION_OUT_TIME", length = 7)
	public Date getExceptionOutTime() {
		return this.exceptionOutTime;
	}

	public void setExceptionOutTime(Date exceptionOutTime) {
		this.exceptionOutTime = exceptionOutTime;
	}

	@Column(name = "EXCEPTION_STATUS", precision = 1, scale = 0)
	public Long getExceptionStatus() {
		return this.exceptionStatus;
	}

	public void setExceptionStatus(Long exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	@Column(name = "DEPART_ID", length = 10)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "CONSIGNEE_ADDR", length = 200)
	public String getConsigneeAddr() {
		return this.consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	@Column(name = "OUT_STOCK_NO")
	public String getOutStockNo() {
		return this.outStockNo;
	}

	public void setOutStockNo(String outStockNo) {
		this.outStockNo = outStockNo;
	}

	@Column(name = "GOWHERE", length = 50)
	public String getGowhere() {
		return this.gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	@Column(name = "DISTRIBUTION_MODE", length = 50)
	public String getDistributionMode() {
		return this.distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	@Column(name = "RETURN_TYPE", length = 50)
	public String getReturnType() {
		return this.returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Column(name = "SOURCE_TYPE", length = 50)
	public String getSourceType() {
		return this.sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "CONFIGNEE_FEE", precision = 8)
	public Double getConfigneeFee() {
		return this.configneeFee;
	}

	public void setConfigneeFee(Double configneeFee) {
		this.configneeFee = configneeFee;
	}

	@Column(name = "CP_FEE", precision = 8)
	public Double getCpFee() {
		return this.cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	@Column(name = "PAYMENT_COLLECTION", precision = 8)
	public Double getPaymentCollection() {
		return this.paymentCollection;
	}

	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	@Column(name = "OUT_STOCK_OBJ")
	public String getOutStockObj() {
		return this.outStockObj;
	}

	public void setOutStockObj(String outStockObj) {
		this.outStockObj = outStockObj;
	}

	@Column(name = "SOURCE_NO", length = 50)
	public String getSourceNo() {
		return this.sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	@Column(name = "ADD_CONFIGNEE_FEE", precision = 8)
	public Double getAddConfigneeFee() {
		return this.addConfigneeFee;
	}

	public void setAddConfigneeFee(Double addConfigneeFee) {
		this.addConfigneeFee = addConfigneeFee;
	}

	@Column(name = "ADD_CP_FEE", precision = 8)
	public Double getAddCpFee() {
		return this.addCpFee;
	}

	public void setAddCpFee(Double addCpFee) {
		this.addCpFee = addCpFee;
	}

	@Column(name = "OUT_COST", precision = 8)
	public Double getOutCost() {
		return this.outCost;
	}

	public void setOutCost(Double outCost) {
		this.outCost = outCost;
	}

	@Column(name = "CREATE_NAME", length = 50)
	public String getCreateName() {
		return this.createName;
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

	@Column(name = "UPDATE_NAME", length = 50)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
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

	@Column(name = "OUT_SENDER", length = 20)
	public String getOutSender() {
		return outSender;
	}

	public void setOutSender(String outSender) {
		this.outSender = outSender;
	}

	@Column(name = "OUT_LOAD", length = 20)
	public String getOutLoad() {
		return outLoad;
	}

	public void setOutLoad(String outLoad) {
		this.outLoad = outLoad;
	}

	@Column(name = "OUT_STOCK_OBJ_NAME", length = 50)
	public String getOutStockObjName() {
		return outStockObjName;
	}

	public void setOutStockObjName(String outStockObjName) {
		this.outStockObjName = outStockObjName;
	}

	@Column(name = "OUT_REMARK", length = 200)
	public String getOutRemark() {
		return outRemark;
	}

	public void setOutRemark(String outRemark) {
		this.outRemark = outRemark;
	}
	
}