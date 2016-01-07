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
 * FiDeliveryPrice entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_DELIVERY_PRICE", schema = "AISUSER")
public class FiDeliveryPrice implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;
	private Long customerId;             //客商ID
	private String customerName;  //客商ID
	private Double rates;         //费率
	private Double lowest;      // 最低一票
	private Double board1;       //板费
	private Double board4;
	private Double board5;
	private Double board6;
	private Double board2;
	private Double board3;
	private String remark;                 //备注
	private String departName;
	private Long departId;                 //业务部门
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private Long isdelete;                  //删除状态 0:已删除
	private String ts;
	private Long isBoardStatus;          //是否需要板费
	private String goodsType;              //货物类型
	
	// Constructors

	/** default constructor */
	public FiDeliveryPrice() {
	}

	/** minimal constructor */
	public FiDeliveryPrice(Long id, Long isdelete) {
		this.id = id;
		this.isdelete = isdelete;
	}

	/** full constructor */
	public FiDeliveryPrice(Long id, Long customerId, String customerName,
			Double rates, Double lowest, Double board1, Double board4,
			Double board5, Double board6, Double board2, Double board3,
			String remark, String createDept, Long createDeptid,
			Date createTime, String createName, Date updateTime,
			String updateName, Long isdelete, String ts) {
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
		this.rates = rates;
		this.lowest = lowest;
		this.board1 = board1;
		this.board4 = board4;
		this.board5 = board5;
		this.board6 = board6;
		this.board2 = board2;
		this.board3 = board3;
		this.remark = remark;
		this.departId = createDeptid;
		this.departName = createDept;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.isdelete = isdelete;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_DELIVERY_PRICE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Column(name = "RATES", precision = 10)
	public Double getRates() {
		return this.rates;
	}

	public void setRates(Double rates) {
		this.rates = rates;
	}

	@Column(name = "LOWEST", precision = 10)
	public Double getLowest() {
		return this.lowest;
	}

	public void setLowest(Double lowest) {
		this.lowest = lowest;
	}

	@Column(name = "BOARD1", precision = 10)
	public Double getBoard1() {
		return this.board1;
	}

	public void setBoard1(Double board1) {
		this.board1 = board1;
	}

	@Column(name = "BOARD4", precision = 10)
	public Double getBoard4() {
		return this.board4;
	}

	public void setBoard4(Double board4) {
		this.board4 = board4;
	}

	@Column(name = "BOARD5", precision = 10)
	public Double getBoard5() {
		return this.board5;
	}

	public void setBoard5(Double board5) {
		this.board5 = board5;
	}

	@Column(name = "BOARD6", precision = 10)
	public Double getBoard6() {
		return this.board6;
	}

	public void setBoard6(Double board6) {
		this.board6 = board6;
	}

	@Column(name = "BOARD2", precision = 10)
	public Double getBoard2() {
		return this.board2;
	}

	public void setBoard2(Double board2) {
		this.board2 = board2;
	}

	@Column(name = "BOARD3", precision = 10)
	public Double getBoard3() {
		return this.board3;
	}

	public void setBoard3(Double board3) {
		this.board3 = board3;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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

	@Column(name = "ISDELETE", nullable = false, precision = 22, scale = 0)
	public Long getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(Long isdelete) {
		this.isdelete = isdelete;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "IS_BOARD_STATUS", nullable = false, precision = 22, scale = 0)
	public Long getIsBoardStatus() {
		return isBoardStatus;
	}

	public void setIsBoardStatus(Long isBoardStatus) {
		this.isBoardStatus = isBoardStatus;
	}

	@Column(name = "GOODS_TYPE", length = 100)
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

}