package com.xbwl.entity;

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


/**
 * @author shuw
 *	����ǩ��ʵ���� 
 */
@Entity
@Table(name = "OPR_SIGN_ROUTE", schema = "AISUSER")
public class OprSignRoute implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String carSignNo;  //����ǩ����
	private Long routeNumber;   // ���κ��Զ�����
	private String carNo;   //���ƺ�
	private String useCarType;        //�ó�����
	private Double maxloadWeight;          // ��������
	private String userCode;          //˾������
	private String driverName;   //˾������
	private String phone;            //˾���绰
	private Date useCarDate;         //�ó�����
	private String startAddr;             //��ʼ��ַ
	private String endAddr;           //������ַ
	private Date startTime;               //��ʼʱ��
	private Date endTime;					//����ʱ��
	private Double signTimeNum;           //ǩ��ʱ��
	private Double startKil;                  //��ʼ������
	private Double endKil;						//����������
	private Double kils;                          //������
	private String createDepartName   ;   //��������
	private Long createDeptId;                
	private Long printDepartId;
	private String printDepartName;       //��ӡ����
	private String printName;                     //��ӡ��
	private Double stopFee;                      //ͣ����
	private Double highSpeedFee;                      //���ٷ�
	private Double lowSpeedFee;                      //���ٷ�
	private Double tollChargeTotal;                 //·�ŷѺϼ�
	private Double signFee;                             //ǩ����
	private Double feeTotal;                           //���úϼ�
	private Double totalPoll;                              //�ۺ�Ʊ��
	private String carVerifyName;                  //���������
	private String fiVerifyName;                     //������
	private String rentCarResult;                    //������;
	private String remark;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long status;   //״̬ 0,���ϣ�1��������2������ˣ�3��������ˣ�4��������ˣ�5���Ѹ���
	private String enteringName;            //¼����
	private Date enteringTime;                //¼��ʱ��
	private Long printNum;                     //��ӡ����
	private Long isSeparateDelivery;         //�Ƿ񵥶��ͻ�
	private Long carId;
	
	private Long VerificationStatus;     //����״̬(0:δ����/1:�Ѻ���)
	private Date VerificationTime;    //����ʱ��
	private String verificationUser;   //������
	
	private Long departId;
	private String  departName;
	
	private String rentReasonCarResult;    //�⳵ԭ��
	private Long payStatus=0L;  //�Ƿ���֧����0��δ֧����1����֧����
	private Date payTime; //����ʱ��
	private String payUser;//������
	// Constructors


	/** default constructor */
	public OprSignRoute() {
	}

	/** minimal constructor */
	public OprSignRoute(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprSignRoute(Long id, String carSignNo, Long routeNumber,
			String carNo, String useCarType, Double maxloadWeight,
			String userCode, String driverName, String phone, Date useCarDate,
			String startAddr, String endAddr, Date startTime, Date endTime,
			Double signTimeNum, Double startKil, Double endKil, Double kils,
			String createDepartName, Long createDeptId, Long printDepartId,
			String printDepartName, String printName, Double stopFee,
			Double highSpeedFee, Double lowSpeedFee, Double tollChargeTotal,
			Double signFee, Double truckage, Double otherFee, Double feeTotal,
			Double totalPoll, String verifyName, String rentCarResult,
			String remark, String createName, Date createTime,
			String updateName, Date updateTime, String ts, Long status,
			String enteringName, Date enteringTime, Long printNum) {
		this.id = id;
		this.carSignNo = carSignNo;
		this.routeNumber = routeNumber;
		this.carNo = carNo;
		this.useCarType = useCarType;
		this.maxloadWeight = maxloadWeight;
		this.userCode = userCode;
		this.driverName = driverName;
		this.phone = phone;
		this.useCarDate = useCarDate;
		this.startAddr = startAddr;
		this.endAddr = endAddr;
		this.startTime = startTime;
		this.endTime = endTime;
		this.signTimeNum = signTimeNum;
		this.startKil = startKil;
		this.endKil = endKil;
		this.kils = kils;
		this.createDepartName = createDepartName;
		this.createDeptId = createDeptId;
		this.printDepartId = printDepartId;
		this.printDepartName = printDepartName;
		this.printName = printName;
		this.stopFee = stopFee;
		this.highSpeedFee = highSpeedFee;
		this.lowSpeedFee = lowSpeedFee;
		this.tollChargeTotal = tollChargeTotal;
		this.signFee = signFee;
		this.feeTotal = feeTotal;
		this.totalPoll = totalPoll;
		this.carVerifyName = verifyName;
		this.rentCarResult = rentCarResult;
		this.remark = remark;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.status = status;
		this.enteringName = enteringName;
		this.enteringTime = enteringTime;
		this.printNum = printNum;
	}

	// Property accessors
	@Id  
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_SIGN_ROUTE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CAR_SIGN_NO", length = 20)
	public String getCarSignNo() {
		return this.carSignNo;
	}

	public void setCarSignNo(String carSignNo) {
		this.carSignNo = carSignNo;
	}

	@Column(name = "ROUTE_NUMBER", precision = 10, scale = 0)  
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_SIGN_ROUTE")   // ���κ��Զ�����
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getRouteNumber() {
		return this.routeNumber;
	}

	public void setRouteNumber(Long routeNumber) {
		this.routeNumber = routeNumber;
	}

	@Column(name = "CAR_NO", length = 20)
	public String getCarNo() {
		return this.carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	@Column(name = "USE_CAR_TYPE", length = 20)
	public String getUseCarType() {
		return this.useCarType;
	}

	public void setUseCarType(String useCarType) {
		this.useCarType = useCarType;
	}

	@Column(name = "MAXLOAD_WEIGHT", precision = 10)
	public Double getMaxloadWeight() {
		return this.maxloadWeight;
	}

	public void setMaxloadWeight(Double maxloadWeight) {
		this.maxloadWeight = maxloadWeight;
	}

	@Column(name = "USER_CODE", length = 10)
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "DRIVER_NAME", length = 30)
	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "USE_CAR_DATE", length = 7)
	public Date getUseCarDate() {
		return this.useCarDate;
	}

	public void setUseCarDate(Date useCarDate) {
		this.useCarDate = useCarDate;
	}

	@Column(name = "START_ADDR", length = 50)
	public String getStartAddr() {
		return this.startAddr;
	}

	public void setStartAddr(String startAddr) {
		this.startAddr = startAddr;
	}

	@Column(name = "END_ADDR", length = 50)
	public String getEndAddr() {
		return this.endAddr;
	}

	public void setEndAddr(String endAddr) {
		this.endAddr = endAddr;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "START_TIME", length = 7)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "END_TIME", length = 7)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "SIGN_TIME_NUM", precision = 8)
	public Double getSignTimeNum() {
		return this.signTimeNum;
	}

	public void setSignTimeNum(Double signTimeNum) {
		this.signTimeNum = signTimeNum;
	}

	@Column(name = "START_KIL", precision = 8)
	public Double getStartKil() {
		return this.startKil;
	}

	public void setStartKil(Double startKil) {
		this.startKil = startKil;
	}

	@Column(name = "END_KIL", precision = 8)
	public Double getEndKil() {
		return this.endKil;
	}

	public void setEndKil(Double endKil) {
		this.endKil = endKil;
	}

	@Column(name = "KILS", precision = 8)
	public Double getKils() {
		return this.kils;
	}

	public void setKils(Double kils) {
		this.kils = kils;
	}

	@Column(name = "CREATE_DEPART_NAME", length = 50)
	public String getCreateDepartName() {
		return this.createDepartName;
	}

	public void setCreateDepartName(String createDepartName) {
		this.createDepartName = createDepartName;
	}

	@Column(name = "CREATE_DEPT_ID", precision = 10, scale = 0)
	public Long getCreateDeptId() {
		return this.createDeptId;
	}

	public void setCreateDeptId(Long createDeptId) {
		this.createDeptId = createDeptId;
	}

	@Column(name = "PRINT_DEPART_ID", precision = 10, scale = 0)
	public Long getPrintDepartId() {
		return this.printDepartId;
	}

	public void setPrintDepartId(Long printDepartId) {
		this.printDepartId = printDepartId;
	}

	@Column(name = "PRINT_DEPART_NAME", length = 50)
	public String getPrintDepartName() {
		return this.printDepartName;
	}

	public void setPrintDepartName(String printDepartName) {
		this.printDepartName = printDepartName;
	}

	@Column(name = "PRINT_NAME", length = 20)
	public String getPrintName() {
		return this.printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	@Column(name = "STOP_FEE", precision = 8)
	public Double getStopFee() {
		return this.stopFee;
	}

	public void setStopFee(Double stopFee) {
		this.stopFee = stopFee;
	}

	@Column(name = "HIGH_SPEED_FEE", precision = 8)
	public Double getHighSpeedFee() {
		return this.highSpeedFee;
	}

	public void setHighSpeedFee(Double highSpeedFee) {
		this.highSpeedFee = highSpeedFee;
	}

	@Column(name = "LOW_SPEED_FEE", precision = 8)
	public Double getLowSpeedFee() {
		return this.lowSpeedFee;
	}

	public void setLowSpeedFee(Double lowSpeedFee) {
		this.lowSpeedFee = lowSpeedFee;
	}

	@Column(name = "TOLL_CHARGE_TOTAL", precision = 8)
	public Double getTollChargeTotal() {
		return this.tollChargeTotal;
	}

	public void setTollChargeTotal(Double tollChargeTotal) {
		this.tollChargeTotal = tollChargeTotal;
	}

	@Column(name = "SIGN_FEE", precision = 8)
	public Double getSignFee() {
		return this.signFee;
	}

	public void setSignFee(Double signFee) {
		this.signFee = signFee;
	}

	@Column(name = "FEE_TOTAL", precision = 8)
	public Double getFeeTotal() {
		return this.feeTotal;
	}

	public void setFeeTotal(Double feeTotal) {
		this.feeTotal = feeTotal;
	}

	@Column(name = "TOTAL_POLL", precision = 8)
	public Double getTotalPoll() {
		return this.totalPoll;
	}

	public void setTotalPoll(Double totalPoll) {
		this.totalPoll = totalPoll;
	}

	@Column(name = "FI_VERIFY_NAME", length = 50)
	public String getFiVerifyName() {
		return this.fiVerifyName;
	}

	public void setFiVerifyName(String fiVerifyName) {
		this.fiVerifyName = fiVerifyName;
	}
	
	@Column(name = "CAR_VERIFY_NAME", length = 50)
	public String getCarVerifyName() {
		return this.carVerifyName;
	}

	public void setCarVerifyName(String carVerifyName) {
		this.carVerifyName = carVerifyName;
	}

	@Column(name = "RENT_REASON_CAR_RESULT", length = 500)
	public String getRentReasonCarResult() {
		return this.rentReasonCarResult;
	}

	public void setRentReasonCarResult(String rentReasonCarResult) {
		this.rentReasonCarResult = rentReasonCarResult;
	}

	@Column(name = "RENT_CAR_RESULT", length = 50)
	public String getRentCarResult() {
		return this.rentCarResult;
	}

	public void setRentCarResult(String rentCarResult) {
		this.rentCarResult = rentCarResult;
	}
	
	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATE_NAME", length = 20)
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

	@Column(name = "UPDATE_NAME", length = 20)
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

	@Column(name = "STATUS", precision = 1, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "ENTERING_NAME", length = 20)
	public String getEnteringName() {
		return this.enteringName;
	}

	public void setEnteringName(String enteringName) {
		this.enteringName = enteringName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "ENTERING_TIME", length = 7)
	public Date getEnteringTime() {
		return this.enteringTime;
	}

	public void setEnteringTime(Date enteringTime) {
		this.enteringTime = enteringTime;
	}

	@Column(name = "PRINT_NUM", precision = 2, scale = 0)
	public Long getPrintNum() {
		return this.printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}
	
	@Column(name = "IS_SEPARATE_DELIVERY", precision = 2, scale = 0)
	public Long getIsSeparateDelivery() {
		return isSeparateDelivery;
	}

	public void setIsSeparateDelivery(Long isSeparateDelivery) {
		this.isSeparateDelivery = isSeparateDelivery;
	}

	@Column(name = "CAR_ID", precision = 7, scale = 0)
	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	@Column(name = "VERIFICATION_STATUS", precision = 7, scale = 0)
	public Long getVerificationStatus() {
		return VerificationStatus;
	}

	public void setVerificationStatus(Long verificationStatus) {
		VerificationStatus = verificationStatus;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "VERIFICATION_TIME", length = 7)
	public Date getVerificationTime() {
		return VerificationTime;
	}

	public void setVerificationTime(Date verificationTime) {
		VerificationTime = verificationTime;
	}

	@Column(name = "VERIFICATION_USER", length = 20)
	public String getVerificationUser() {
		return verificationUser;
	}

	public void setVerificationUser(String verificationUser) {
		this.verificationUser = verificationUser;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId= departId;
	}
	
	@Column(name = "PAY_STATUS", precision = 22, scale = 0)
	public Long getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Long payStatus) {
		this.payStatus = payStatus;
	}
	
	@JSON(format="yyyy-MM-dd hh:mm:ss")
	@Column(name = "PAY_TIME")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime= payTime;
	}

	@Column(name = "PAY_USER", length = 20)
	public String getPayUser() {
		return payUser;
	}

	public void setPayUser(String payUser) {
		this.payUser = payUser;
	}
}