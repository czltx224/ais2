package com.xbwl.oper.stock.vo;

/**
 * author CaoZhili time Nov 23, 2011 2:15:22 PM
 * 
 * 发车确认保存数据VO
 */

public class CarGoVo implements java.io.Serializable {

	private Long checkAlone;//是否单独送货
	private String rentCarResult;// 车辆用途
	private String useCarType;//用车类型
	private String driverPhone;//司机电话
	private Long carId;// 车辆ID
	private Long driverId;//司机ID
	private Long totalDno;//总票数
	private Long bussDepartId;//业务部门
	private String lockNum;//锁号
	private Long checkPrint;//是否车辆签单打印
	private String carCode;// 车牌号
	private String driverName;//司机名称
	
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
