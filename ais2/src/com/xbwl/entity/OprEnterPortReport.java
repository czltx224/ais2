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
 * EnterPortReport entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_ENTER_PORT_REPORT")
public class OprEnterPortReport implements java.io.Serializable {

	// Fields

	private Long id;
	private Date countDate;//ͳ������
	private Date flightDate;//��������
	private String flightNo;//�����
	private String carCode;//���ƺ�
	private String flightLandingTime;//�������ʱ��
	private Date airportGocarTime;//��������ʱ��
	private Date enterStockTime;//�㵽ʱ��
	private Long takeUseTime;//�����ʱ(Сʱ)
	private Long enterPortLimitation;//����ʱЧ(Сʱ)
	private Long dno;//���͵���
	private Long takeIsStandard;//����Ƿ���
	private Long enterIsStandard;//�����Ƿ���
	private String takeDutyUnit;//������ε�λ
	private Long carRunTime;//��������Сʱ(����ȷ��ʱ��-����ʱ��)
	private Long takeCarStandardTime;//�������б�׼
	private Long carRunIsStandard;//���������Ƿ���
	private Long takeStandardTime;//���ʱЧ��׼
	private Long enterPortStandardTime;//����ʱЧ��׼
	private String cpName;//��������
	private String deptName;//��������
	private Long deptId;//���ű��

	// Constructors

	/** default constructor */
	public OprEnterPortReport() {
	}

	/** minimal constructor */
	public OprEnterPortReport(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprEnterPortReport(Long id, Date countDate, Date flightDate,String flightNo,
			String carCode, String flightLandingTime, Date airportGocarTime,
			Date enterStockTime, Long takeUseTime, Long enterPortLimitation,
			Long dno, Long takeIsStandard, Long enterIsStandard,
			String takeDutyUnit, Long carRunTime, Long takeCarStandardTime,
			Long carRunIsStandard, Long takeStandardTime,
			Long enterPortStandardTime, String cpName, String deptName,
			Long deptId, String createName, Date createTime,
			String updateName, Date updateTime, String ts) {
		this.id = id;
		this.countDate = countDate;
		this.flightDate=flightDate;
		this.flightNo = flightNo;
		this.carCode = carCode;
		this.flightLandingTime = flightLandingTime;
		this.airportGocarTime = airportGocarTime;
		this.enterStockTime = enterStockTime;
		this.takeUseTime = takeUseTime;
		this.enterPortLimitation = enterPortLimitation;
		this.dno = dno;
		this.takeIsStandard = takeIsStandard;
		this.enterIsStandard = enterIsStandard;
		this.takeDutyUnit = takeDutyUnit;
		this.carRunTime = carRunTime;
		this.takeCarStandardTime = takeCarStandardTime;
		this.carRunIsStandard = carRunIsStandard;
		this.takeStandardTime = takeStandardTime;
		this.enterPortStandardTime = enterPortStandardTime;
		this.cpName = cpName;
		this.deptName = deptName;
		this.deptId = deptId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_ENTER_PORT_REPORT ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "COUNT_DATE", length = 7)
	public Date getCountDate() {
		return this.countDate;
	}

	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}

	@Column(name = "FLIGHT_NO", length = 20)
	public String getFlightNo() {
		return this.flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@Column(name = "CAR_CODE", length = 20)
	public String getCarCode() {
		return this.carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	@Column(name = "FLIGHT_LANDING_TIME", length = 20)
	public String getFlightLandingTime() {
		return this.flightLandingTime;
	}

	public void setFlightLandingTime(String flightLandingTime) {
		this.flightLandingTime = flightLandingTime;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm")
	@Column(name = "AIRPORT_GOCAR_TIME")
	public Date getAirportGocarTime() {
		return this.airportGocarTime;
	}

	public void setAirportGocarTime(Date airportGocarTime) {
		this.airportGocarTime = airportGocarTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	@Column(name = "ENTER_STOCK_TIME")
	public Date getEnterStockTime() {
		return this.enterStockTime;
	}

	public void setEnterStockTime(Date enterStockTime) {
		this.enterStockTime = enterStockTime;
	}

	@Column(name = "TAKE_USE_TIME", precision = 22, scale = 0)
	public Long getTakeUseTime() {
		return this.takeUseTime;
	}

	public void setTakeUseTime(Long takeUseTime) {
		this.takeUseTime = takeUseTime;
	}

	@Column(name = "ENTER_PORT_LIMITATION", precision = 22, scale = 0)
	public Long getEnterPortLimitation() {
		return this.enterPortLimitation;
	}

	public void setEnterPortLimitation(Long enterPortLimitation) {
		this.enterPortLimitation = enterPortLimitation;
	}

	@Column(name = "DNO", precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "TAKE_IS_STANDARD", precision = 1, scale = 0)
	public Long getTakeIsStandard() {
		return this.takeIsStandard;
	}

	public void setTakeIsStandard(Long takeIsStandard) {
		this.takeIsStandard = takeIsStandard;
	}

	@Column(name = "ENTER_IS_STANDARD", precision = 1, scale = 0)
	public Long getEnterIsStandard() {
		return this.enterIsStandard;
	}

	public void setEnterIsStandard(Long enterIsStandard) {
		this.enterIsStandard = enterIsStandard;
	}

	@Column(name = "TAKE_DUTY_UNIT", length = 20)
	public String getTakeDutyUnit() {
		return this.takeDutyUnit;
	}

	public void setTakeDutyUnit(String takeDutyUnit) {
		this.takeDutyUnit = takeDutyUnit;
	}

	@Column(name = "CAR_RUN_TIME", precision = 22, scale = 0)
	public Long getCarRunTime() {
		return this.carRunTime;
	}

	public void setCarRunTime(Long carRunTime) {
		this.carRunTime = carRunTime;
	}

	@Column(name = "TAKE_CAR_STANDARD_TIME", precision = 22, scale = 0)
	public Long getTakeCarStandardTime() {
		return this.takeCarStandardTime;
	}

	public void setTakeCarStandardTime(Long takeCarStandardTime) {
		this.takeCarStandardTime = takeCarStandardTime;
	}

	@Column(name = "CAR_RUN_IS_STANDARD", precision = 1, scale = 0)
	public Long getCarRunIsStandard() {
		return this.carRunIsStandard;
	}

	public void setCarRunIsStandard(Long carRunIsStandard) {
		this.carRunIsStandard = carRunIsStandard;
	}

	@Column(name = "TAKE_STANDARD_TIME", precision = 22, scale = 0)
	public Long getTakeStandardTime() {
		return this.takeStandardTime;
	}

	public void setTakeStandardTime(Long takeStandardTime) {
		this.takeStandardTime = takeStandardTime;
	}

	@Column(name = "ENTER_PORT_STANDARD_TIME", precision = 22, scale = 0)
	public Long getEnterPortStandardTime() {
		return this.enterPortStandardTime;
	}

	public void setEnterPortStandardTime(Long enterPortStandardTime) {
		this.enterPortStandardTime = enterPortStandardTime;
	}

	@Column(name = "CP_NAME", length = 50)
	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	@Column(name = "DEPT_NAME", length = 50)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(name = "DEPT_ID")
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	@JSON(format = "yyyy-MM-dd")
	@Column(name = "FLIGHT_DATE")
	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
	
}