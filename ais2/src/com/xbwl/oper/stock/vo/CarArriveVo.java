package com.xbwl.oper.stock.vo;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;

/**
 * OprOvermemo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CarArriveVo implements java.io.Serializable {

	// Fields

	private Long id;
	private Long startDepartId;//始发部门
	private String startDepartName;
	private Long endDepartId;//到达部门
	private String endDepartName;
	private Date startTime;//发车时间
	private Date endTime;//到车时间
	private Date unloadStartTime;//卸车开始时间
	private Date unloadEndTime;//卸车结束时间
	private String overmemoType;//交接单类型
	private Long carId;//车辆Id
	private String carCode;
	private String carType;
	private String carWhere;
	private String glatLng;
	private Long status;//车辆状态
	private String remark;//备注
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	private String orderfields;//排序字段
	
	private Long totalTicket;//总票数
	private Long totalPiece;//件数
	private Long totalWeight;//重量
	private String lockNo;
	private Long routeNumber;   // 车次号自动增长

	// Constructors

	/** default constructor */
	public CarArriveVo() {
	}
	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStartDepartId() {
		return this.startDepartId;
	}

	public void setStartDepartId(Long startDepartId) {
		this.startDepartId = startDepartId;
	}

	public Long getEndDepartId() {
		return this.endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUnloadStartTime() {
		return this.unloadStartTime;
	}

	public void setUnloadStartTime(Date unloadStartTime) {
		this.unloadStartTime = unloadStartTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUnloadEndTime() {
		return this.unloadEndTime;
	}

	public void setUnloadEndTime(Date unloadEndTime) {
		this.unloadEndTime = unloadEndTime;
	}

	public String getOvermemoType() {
		return this.overmemoType;
	}

	public void setOvermemoType(String overmemoType) {
		this.overmemoType = overmemoType;
	}

	public Long getCarId() {
		return this.carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	

	/**
	 * @return the ts
	 */
	public String getTs() {
		return ts;
	}
	/**
	 * @param ts the ts to set
	 */
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getOrderfields() {
		return this.orderfields;
	}

	public void setOrderfields(String orderfields) {
		this.orderfields = orderfields;
	}
	public String getStartDepartName() {
		return startDepartName;
	}
	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}
	public String getEndDepartName() {
		return endDepartName;
	}
	public void setEndDepartName(String endDepartName) {
		this.endDepartName = endDepartName;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarWhere() {
		return carWhere;
	}
	public void setCarWhere(String carWhere) {
		this.carWhere = carWhere;
	}
	public String getGlatLng() {
		return glatLng;
	}
	public void setGlatLng(String glatLng) {
		this.glatLng = glatLng;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public Long getTotalTicket() {
		return totalTicket;
	}
	public void setTotalTicket(Long totalTicket) {
		this.totalTicket = totalTicket;
	}
	public Long getTotalPiece() {
		return totalPiece;
	}
	public void setTotalPiece(Long totalPiece) {
		this.totalPiece = totalPiece;
	}
	public Long getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Long totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getLockNo() {
		return lockNo;
	}
	public void setLockNo(String lockNo) {
		this.lockNo = lockNo;
	}
	/**
	 * @return the routeNumber
	 */
	public Long getRouteNumber() {
		return routeNumber;
	}
	/**
	 * @param routeNumber the routeNumber to set
	 */
	public void setRouteNumber(Long routeNumber) {
		this.routeNumber = routeNumber;
	}
	
}