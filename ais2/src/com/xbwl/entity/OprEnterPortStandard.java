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
 * EnterPortStandard entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_ENTER_PORT_STANDARD")
public class OprEnterPortStandard implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	private Double takeCarStandardTime;// 提货车辆标准用时
	private Double takeStandardTime;// 提货标准用时
	private Double enterStandardTime;// 进港标准用时
	private String countTime;// 自动统计时间
	private String countRange;// 统计维度（年/月/周/日）
	private String deptName;// 部门名称
	private Long deptId;// 部门编号
	private String createName;// 创建人
	private Date createTime;// 创建时间
	private String updateName;// 修改人
	private Date updateTime;// 修改时间
	private String ts;// 时间戳
	private String lastCountName;// 最后统计人
	private Date lastCountTime;// 最后统计时间

	// Constructors

	/** default constructor */
	public OprEnterPortStandard() {
	}

	/** minimal constructor */
	public OprEnterPortStandard(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprEnterPortStandard(Long id, Double takeCarStandardTime,
			Double takeStandardTime, Double enterStandardTime, String countTime,
			String countRange, String deptName, Long deptId, String createName,
			Date createTime, String updateName, Date updateTime, String ts) {
		this.id = id;
		this.takeCarStandardTime = takeCarStandardTime;
		this.takeStandardTime = takeStandardTime;
		this.enterStandardTime = enterStandardTime;
		this.countTime = countTime;
		this.countRange = countRange;
		this.deptName = deptName;
		this.deptId = deptId;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_ENTER_PORT_STANDARD ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TAKE_CAR_STANDARD_TIME", precision = 22, scale = 0)
	public Double getTakeCarStandardTime() {
		return this.takeCarStandardTime;
	}

	public void setTakeCarStandardTime(Double takeCarStandardTime) {
		this.takeCarStandardTime = takeCarStandardTime;
	}

	@Column(name = "TAKE_STANDARD_TIME", precision = 22, scale = 0)
	public Double getTakeStandardTime() {
		return this.takeStandardTime;
	}

	public void setTakeStandardTime(Double takeStandardTime) {
		this.takeStandardTime = takeStandardTime;
	}

	@Column(name = "ENTER_STANDARD_TIME", precision = 22, scale = 0)
	public Double getEnterStandardTime() {
		return this.enterStandardTime;
	}

	public void setEnterStandardTime(Double enterStandardTime) {
		this.enterStandardTime = enterStandardTime;
	}

	@Column(name = "COUNT_TIME", length = 5)
	public String getCountTime() {
		return countTime;
	}

	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}
	@Column(name = "COUNT_RANGE", length = 10)
	public String getCountRange() {
		return countRange;
	}

	public void setCountRange(String countRange) {
		this.countRange = countRange;
	}

	@Column(name = "DEPT_NAME", length = 50)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID" ,unique = true)
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CREATE_NAME", length = 50)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
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

	@JSON(format = "yyyy-MM-dd HH:mm")
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

	@Column(name = "LAST_COUNT_NAME", length = 50)
	public String getLastCountName() {
		return lastCountName;
	}

	public void setLastCountName(String lastCountName) {
		this.lastCountName = lastCountName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	@Column(name = "LAST_COUNT_TIME")
	public Date getLastCountTime() {
		return lastCountTime;
	}

	public void setLastCountTime(Date lastCountTime) {
		this.lastCountTime = lastCountTime;
	}

}