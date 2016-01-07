package com.xbwl.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprOvermemo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_OVERMEMO")
public class OprOvermemo implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long startDepartId;// 始发部门
	private Long endDepartId;// 到达部门
	private Date startTime;// 发车时间
	private Date endTime;// 到车时间
	private Date unloadStartTime;// 卸车开始时间
	private Date unloadEndTime;// 卸车结束时间
	private String overmemoType;// 交接单类型
	private Long carId;// 车辆Id
	private Long status;// 车辆状态
	private String remark;// 备注
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	private String orderfields;// 排序字段
	private Long totalTicket;// 总票数
	private Long totalPiece;// 件数
	private Double totalWeight;// 重量
	private Set<OprOvermemoDetail> oprOvermemoDetails = new HashSet<OprOvermemoDetail>(0);
	private String lockNo;// 锁号
	private String endDepartName;
	private String startDepartName;
	private Long routeNumber; // 车次号自动增长
	private Long printNum;// 打印次数
	private String carCode;//车牌号
	
	private String useCarType;//用车类型
	private String rentCarResult;//车辆用途
	
	private String telPhone; //联系方式

	// Constructors

	/** default constructor */
	public OprOvermemo() {
		this.printNum=0l;
	}

	public OprOvermemo(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public OprOvermemo(Long id, Long startDepartId, Long endDepartId,
			Date startTime, String overmemoType, Long carId, Long status) {
		this.id = id;
		this.startDepartId = startDepartId;
		this.endDepartId = endDepartId;
		this.startTime = startTime;
		this.overmemoType = overmemoType;
		this.carId = carId;
		this.status = status;
	}

	/** full constructor */
	public OprOvermemo(Long id, Long startDepartId, Long endDepartId,
			Date startTime, Date endTime, Date unloadStartTime,
			Date unloadEndTime, String overmemoType, Long carId, Long status,
			String remark, Date createTime, String createName, Date updateTime,
			String updateName, String ts, String orderfields,
			Set<OprOvermemoDetail> oprOvermemoDetails) {
		this.id = id;
		this.startDepartId = startDepartId;
		this.endDepartId = endDepartId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.unloadStartTime = unloadStartTime;
		this.unloadEndTime = unloadEndTime;
		this.overmemoType = overmemoType;
		this.carId = carId;
		this.status = status;
		this.remark = remark;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.ts = ts;
		this.orderfields = orderfields;
		this.oprOvermemoDetails = oprOvermemoDetails;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_OVERMEMO")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "START_DEPART_ID", nullable = false, precision = 22, scale = 0)
	public Long getStartDepartId() {
		return this.startDepartId;
	}

	public void setStartDepartId(Long startDepartId) {
		this.startDepartId = startDepartId;
	}

	@Column(name = "END_DEPART_ID", nullable = true, precision = 22, scale = 0)
	public Long getEndDepartId() {
		return this.endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	@JSON(format = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "START_TIME", length = 7)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "END_TIME", length = 7)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UNLOAD_START_TIME", length = 7)
	public Date getUnloadStartTime() {
		return this.unloadStartTime;
	}

	public void setUnloadStartTime(Date unloadStartTime) {
		this.unloadStartTime = unloadStartTime;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UNLOAD_END_TIME", length = 7)
	public Date getUnloadEndTime() {
		return this.unloadEndTime;
	}

	public void setUnloadEndTime(Date unloadEndTime) {
		this.unloadEndTime = unloadEndTime;
	}

	@Column(name = "OVERMEMO_TYPE", nullable = false, length = 20)
	public String getOvermemoType() {
		return this.overmemoType;
	}

	public void setOvermemoType(String overmemoType) {
		this.overmemoType = overmemoType;
	}

	@Column(name = "CAR_ID", precision = 22, scale = 0)
	public Long getCarId() {
		return this.carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
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

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "ORDERFIELDS", length = 200)
	public String getOrderfields() {
		return this.orderfields;
	}

	public void setOrderfields(String orderfields) {
		this.orderfields = orderfields;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "oprOvermemo")
	public Set<OprOvermemoDetail> getOprOvermemoDetails() {
		return this.oprOvermemoDetails;
	}

	public void setOprOvermemoDetails(Set<OprOvermemoDetail> oprOvermemoDetails) {
		this.oprOvermemoDetails = oprOvermemoDetails;
	}

	@Column(name = "LOCK_NO", length = 20)
	public String getLockNo() {
		return lockNo;
	}

	public void setLockNo(String lockNo) {
		this.lockNo = lockNo;
	}

	@Column(name = "TOTAL_TICKET")
	public Long getTotalTicket() {
		return totalTicket;
	}

	public void setTotalTicket(Long totalTicket) {
		this.totalTicket = totalTicket;
	}

	@Column(name = "TOTAL_PIECE")
	public Long getTotalPiece() {
		return totalPiece;
	}

	public void setTotalPiece(Long totalPiece) {
		this.totalPiece = totalPiece;
	}

	@Column(name = "TOTAL_WEIGHT")
	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	@Column(name = "END_DEPART_NAME", length = 50)
	public String getEndDepartName() {
		return endDepartName;
	}

	public void setEndDepartName(String endDepartName) {
		this.endDepartName = endDepartName;
	}

	public Long getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(Long routeNumber) {
		this.routeNumber = routeNumber;
	}

	/**
	 * @return the startDepartName
	 */
	@Column(name = "START_DEPART_NAME", length = 100)
	public String getStartDepartName() {
		return startDepartName;
	}

	/**
	 * @param startDepartName
	 *            the startDepartName to set
	 */
	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}

	@Column(name = "PRINT_NUM")
	public Long getPrintNum() {
		return printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}
	@Column(name = "CAR_CODE")
	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	@Column(name = "USE_CAR_TYPE")
	public String getUseCarType() {
		return useCarType;
	}

	public void setUseCarType(String useCarType) {
		this.useCarType = useCarType;
	}

	@Column(name = "RENT_CAR_RESULT")
	public String getRentCarResult() {
		return rentCarResult;
	}

	public void setRentCarResult(String rentCarResult) {
		this.rentCarResult = rentCarResult;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "交接单号:"+id+",车牌号:"+carCode+",车次号:"+routeNumber+"发车时间:"+startTime+",始发部门:"
		+startDepartName+",到达部门:"+endDepartName+",重量:"+totalWeight+",件数:"+totalPiece+",票数:"+totalTicket;
	}

	@Column(name = "TEL_PHONE")
	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	
}