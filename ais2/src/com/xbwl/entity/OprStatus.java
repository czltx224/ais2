package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

/**
 * OprStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_STATUS")
public class OprStatus implements java.io.Serializable{

	// Fields

	private Long id;
	private Long dno;//���͵���
	private Date reachTime;//�㵽ʱ��
	private String reachName;//
	private Long reachStatus;//0,δ�㵽��1�Ѿ��㵽��2�����ֵ㵽 �㵽״̬
	private Long notifyStatus;//�㵽��
	private Date notifyTime;//0,δ֪ͨ��1��֪ͨ�ɹ���2��֪ͨʧ��
	private Long outStatus;//0,δ���⣬1��Ϊ�ѳ��⣬2���쳣���⣬3����Ԥ��
	private Date outTime;//����ʱ��
	private Long departOvermemoStatus;//0,δ��ʼ���ӣ�1���Ѿ�������ϣ�2���쳣����
	private Date departOvermemoStartTime;//���Ž��ӿ�ʼʱ��--δ�õ��ֶ�
	private Date departOvermemoEndTime;//���Ž��ӽ���ʱ��--δ�õ��ֶ�
	private Long cashStatus;//0,δ������1 �Ѿ�����
	private Date cashTime;//����ʱ��
	private Long payTra;//��ת�֧ⷢ��״̬��0��δ���1���Ѿ�����
	private Date payTraTime;//��ת�ⷢ����ʱ��
	private Long signStatus=0l;//ǩ��״̬ ,0,��δǩ�գ�1����ǩ�գ�2������ǩ��
	private Date signTime;//ǩ��ʱ��
	private Long feeAuditStatus = 0l;//��ת�������:0δ��ˣ�1�����
	private Long returnStatus=0l;//����״̬ 0,������1����Ʊ������2�����ַ�����3�����㷵��
	private Long fiSureStatus;//����ȷ��״̬��0ȷ�գ�1�Ѿ�ȷ��
	private Date feeAuditTime;//���ʱ��
	private Date returnTime;//����ʱ��
	private Date returnEnterTime;//�������ʱ��
	private Date returnOutTime;//��������ʱ��
	private Long airportOutcarStatus;//��������״̬ 1.�ѷ�����0δ����
	private Date airportOutcarTime;//��������ʱ��
	private String signName;//����ǩ����
	
	private String cashName;  // ������
	private String doStoreName; //���ֿ�ȷ����
	private Long doStoreStatus;   //����ȷ��״̬
	private Date doStoreTime;    // ����ȷ��ʱ��
	
	private Long isCreateFi ;  //�Ƿ��ѹ��� 0��δ���ˣ�1���ѹ��ˡ���Ҫ����쳣���⡣
	private Long isException;//�Ƿ����쳣 0��������1���쳣
	private Long isOprException; //�Ƿ������쳣 0��������1���쳣
	private Long isFlyLate;//�����Ƿ�����0Ϊδ���󣨼���������1Ϊ����
	private Long ediReachStatus;//EDI�㵽״̬ 0��δ�㵽��1���㵽
	private Date ediReachTime;//EDI�㵽ʱ�� 
	
	// Constructors
	@Column(name = "SIGN_NAME", length = 7)
	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	/** default constructor */
	public OprStatus() {
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_STATUS")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", nullable = false, precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "REACH_TIME", length = 7)
	public Date getReachTime() {
		return this.reachTime;
	}

	public void setReachTime(Date reachTime) {
		this.reachTime = reachTime;
	}

	@Column(name = "REACH_STATUS", nullable = false, precision = 22, scale = 0)
	public Long getReachStatus() {
		return this.reachStatus;
	}

	public void setReachStatus(Long reachStatus) {
		this.reachStatus = reachStatus;
	}

	@Column(name = "NOTIFY_STATUS", nullable = false, precision = 22, scale = 0)
	public Long getNotifyStatus() {
		return this.notifyStatus;
	}

	public void setNotifyStatus(Long notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "NOTIFY_TIME", length = 7)
	public Date getNotifyTime() {
		return this.notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	@Column(name = "OUT_STATUS", nullable = false, precision = 22, scale = 0)
	public Long getOutStatus() {
		return this.outStatus;
	}

	public void setOutStatus(Long outStatus) {
		this.outStatus = outStatus;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "OUT_TIME", length = 7)
	public Date getOutTime() {
		return this.outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	@Column(name = "DEPART_OVERMEMO_STATUS", nullable = false, precision = 22, scale = 0)
	public Long getDepartOvermemoStatus() {
		return this.departOvermemoStatus;
	}

	public void setDepartOvermemoStatus(Long departOvermemoStatus) {
		this.departOvermemoStatus = departOvermemoStatus;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DEPART_OVERMEMO_START_TIME", length = 7)
	public Date getDepartOvermemoStartTime() {
		return this.departOvermemoStartTime;
	}

	public void setDepartOvermemoStartTime(Date departOvermemoStartTime) {
		this.departOvermemoStartTime = departOvermemoStartTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DEPART_OVERMEMO_END_TIME", length = 7)
	public Date getDepartOvermemoEndTime() {
		return this.departOvermemoEndTime;
	}

	public void setDepartOvermemoEndTime(Date departOvermemoEndTime) {
		this.departOvermemoEndTime = departOvermemoEndTime;
	}

	@Column(name = "CASH_STATUS", nullable = false, precision = 22, scale = 0)
	public Long getCashStatus() {
		return this.cashStatus;
	}

	public void setCashStatus(Long cashStatus) {
		this.cashStatus = cashStatus;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CASH_TIME", length = 7)
	public Date getCashTime() {
		return this.cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}

	@Column(name = "PAY_TRA", nullable = false, precision = 22, scale = 0)
	public Long getPayTra() {
		return this.payTra;
	}

	public void setPayTra(Long payTra) {
		this.payTra = payTra;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "PAY_TRA_TIME", length = 7)
	public Date getPayTraTime() {
		return this.payTraTime;
	}

	public void setPayTraTime(Date payTraTime) {
		this.payTraTime = payTraTime;
	}

	@Column(name = "SIGN_STATUS", nullable = false, precision = 22, scale = 0)
	public Long getSignStatus() {
		return this.signStatus;
	}

	public void setSignStatus(Long signStatus) {
		this.signStatus = signStatus;
	}

	@Column(name = "SIGN_TIME", length = 7)
	public Date getSignTime() {
		return this.signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	@Column(name = "RETURN_STATUS")
	public Long getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(Long returnStatus) {
		this.returnStatus = returnStatus;
	}

	@Column(name = "FEE_AUDIT_STATUS", precision = 22, scale = 0)
	public Long getFeeAuditStatus() {
		return feeAuditStatus;
	}

	public void setFeeAuditStatus(Long feeAuditStatus) {
		this.feeAuditStatus = feeAuditStatus;
	}

	@Column(name = "FI_SURE_STATUS")
	public Long getFiSureStatus() {
		return fiSureStatus;
	}

	public void setFiSureStatus(Long fiSureStatus) {
		this.fiSureStatus = fiSureStatus;
	}

	/**
	 * @return the feeAuditTime
	 */
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "FEE_AUDIT_TIME")
	public Date getFeeAuditTime() {
		return feeAuditTime;
	}

	/**
	 * @param feeAuditTime the feeAuditTime to set
	 */
	public void setFeeAuditTime(Date feeAuditTime) {
		this.feeAuditTime = feeAuditTime;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "RETURN_TIME")
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "RETURN_ENTER_TIME")
	public Date getReturnEnterTime() {
		return returnEnterTime;
	}

	public void setReturnEnterTime(Date returnEnterTime) {
		this.returnEnterTime = returnEnterTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "RETURN_OUT_TIME")
	public Date getReturnOutTime() {
		return returnOutTime;
	}

	public void setReturnOutTime(Date returnOutTime) {
		this.returnOutTime = returnOutTime;
	}

	@Column(name = "AIRPORT_OUTCAR_STATUS")
	public Long getAirportOutcarStatus() {
		return airportOutcarStatus;
	}

	public void setAirportOutcarStatus(Long airportOutcarStatus) {
		this.airportOutcarStatus = airportOutcarStatus;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "AIRPORT_OUTCAR_TIME")
	public Date getAirportOutcarTime() {
		return airportOutcarTime;
	}

	public void setAirportOutcarTime(Date airportOutcarTime) {
		this.airportOutcarTime = airportOutcarTime;
	}

	@Column(name = "CASH_NAME", length = 50)
	public String getCashName() {
		return cashName;
	}

	public void setCashName(String cashName) {
		this.cashName = cashName;
	}

	@Column(name = "DO_STORE_NAME", length = 50)
	public String getDoStoreName() {
		return doStoreName;
	}

	public void setDoStoreName(String doStoreName) {
		this.doStoreName = doStoreName;
	}

	@Column(name = "DO_STORE_STATUS")
	public Long getDoStoreStatus() {
		return doStoreStatus;
	}

	public void setDoStoreStatus(Long doStoreStatus) {
		this.doStoreStatus = doStoreStatus;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DO_STORE_TIME")
	public Date getDoStoreTime() {
		return doStoreTime;
	}

	public void setDoStoreTime(Date doStoreTime) {
		this.doStoreTime = doStoreTime;
	}

	
	@Column(name = "REACH_NAME")
	public String getReachName() {
		return reachName;
	}

	public void setReachName(String reachName) {
		this.reachName = reachName;
	}

	@Column(name = "IS_EXCEPTION")
	public Long getIsException() {
		return isException;
	}

	public void setIsException(Long isException) {
		this.isException = isException;
	}

	@Column(name = "IS_OPR_EXCEPTION")
	public Long getIsOprException() {
		return isOprException;
	}

	public void setIsOprException(Long isOprException) {
		this.isOprException = isOprException;
	}

	@Column(name = "IS_CREATE_FI")
	public Long getIsCreateFi() {
		return isCreateFi;
	}

	public void setIsCreateFi(Long isCreateFi) {
		this.isCreateFi = isCreateFi;
	}

	@Column(name = "IS_FLY_LATE")
	public Long getIsFlyLate() {
		return isFlyLate;
	}

	public void setIsFlyLate(Long isFlyLate) {
		this.isFlyLate = isFlyLate;
	}

	@Column(name = "EDI_REACH_STATUS")
	public Long getEdiReachStatus() {
		return ediReachStatus;
	}

	public void setEdiReachStatus(Long ediReachStatus) {
		this.ediReachStatus = ediReachStatus;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "EDI_REACH_TIME")
	public Date getEdiReachTime() {
		return ediReachTime;
	}

	public void setEdiReachTime(Date ediReachTime) {
		this.ediReachTime = ediReachTime;
	}
}