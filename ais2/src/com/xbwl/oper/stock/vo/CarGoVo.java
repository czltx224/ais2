package com.xbwl.oper.stock.vo;

/**
 * author CaoZhili time Nov 23, 2011 2:15:22 PM
 * 
 * ����ȷ�ϱ�������VO
 */

public class CarGoVo implements java.io.Serializable {

	private Long checkAlone;//�Ƿ񵥶��ͻ�
	private String rentCarResult;// ������;
	private String useCarType;//�ó�����
	private String driverPhone;//˾���绰
	private Long carId;// ����ID
	private Long driverId;//˾��ID
	private Long totalDno;//��Ʊ��
	private Long bussDepartId;//ҵ����
	private String lockNum;//����
	private Long checkPrint;//�Ƿ���ǩ����ӡ
	private String carCode;// ���ƺ�
	private String driverName;//˾������
	
	public Long getCheckAlone() {
		return checkAlone;
	}
	public void setCheckAlone(Long checkAlone) {
		this.checkAlone = checkAlone;
	}
	public String getRentCarResult() {
		return rentCarResult;
	}
	public void setRentCarResult(String rentCarResult) {
		this.rentCarResult = rentCarResult;
	}
	public String getUseCarType() {
		return useCarType;
	}
	public void setUseCarType(String useCarType) {
		this.useCarType = useCarType;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public Long getCarId() {
		return carId;
	}
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	public Long getTotalDno() {
		return totalDno;
	}
	public void setTotalDno(Long totalDno) {
		this.totalDno = totalDno;
	}
	public Long getBussDepartId() {
		return bussDepartId;
	}
	public void setBussDepartId(Long bussDepartId) {
		this.bussDepartId = bussDepartId;
	}
	public String getLockNum() {
		return lockNum;
	}
	public void setLockNum(String lockNum) {
		this.lockNum = lockNum;
	}
	public Long getCheckPrint() {
		return checkPrint;
	}
	public void setCheckPrint(Long checkPrint) {
		this.checkPrint = checkPrint;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
}
