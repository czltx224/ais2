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
 * OprShuntApply entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_SHUNT_APPLY")
public class OprShuntApply implements java.io.Serializable {

	// Fields

	private Long id;
	private String flightNo;//航班号
	private Double shuntWeight;//总重量
	private Long shuntPiece;//总件数
	private String takeAddr;//提货处
	private String flightEndTime;//航班落地时间
	/*
	private String uploadTime;//预计装车时间
	private String disCarNo;//已派车车牌号
	private Double disCarTon;//已配车吨位
	private Double disShuntWeight;//已配车总重量
	private Long disShuntPiece;//已配车件数
	private String planCarTime;//计划发车时间
	*/
	private String createName;
    private Date createTime;
    private String updateName;
    private Date updateTime;
    private String ts;
    private Long departId;

	// Constructors

	/** default constructor */
	public OprShuntApply() {
	}

	/** minimal constructor */
	public OprShuntApply(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_SHUNT_APPLY")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FLIGHT_NO", length = 200)
	public String getFlightNo() {
		return this.flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@Column(name = "SHUNT_WEIGHT", precision = 7)
	public Double getShuntWeight() {
		return this.shuntWeight;
	}

	public void setShuntWeight(Double shuntWeight) {
		this.shuntWeight = shuntWeight;
	}

	@Column(name = "SHUNT_PIECE", precision = 22, scale = 0)
	public Long getShuntPiece() {
		return this.shuntPiece;
	}

	public void setShuntPiece(Long shuntPiece) {
		this.shuntPiece = shuntPiece;
	}

	@Column(name = "TAKE_ADDR", length = 100)
	public String getTakeAddr() {
		return this.takeAddr;
	}

	public void setTakeAddr(String takeAddr) {
		this.takeAddr = takeAddr;
	}

	@Column(name = "FLIGHT_END_TIME", length = 200)
	public String getFlightEndTime() {
		return this.flightEndTime;
	}

	public void setFlightEndTime(String flightEndTime) {
		this.flightEndTime = flightEndTime;
	}
	/*
	@Column(name = "UPLOAD_TIME", length = 200)
	public String getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Column(name = "DIS_CAR_NO", length = 200)
	public String getDisCarNo() {
		return this.disCarNo;
	}

	public void setDisCarNo(String disCarNo) {
		this.disCarNo = disCarNo;
	}
	@Column(name = "DIS_CAR_TON", precision = 7)
	public Double getDisCarTon() {
		return this.disCarTon;
	}

	public void setDisCarTon(Double disCarTon) {
		this.disCarTon = disCarTon;
	}

	@Column(name = "DIS_SHUNT_WEIGHT", precision = 7)
	public Double getDisShuntWeight() {
		return this.disShuntWeight;
	}

	public void setDisShuntWeight(Double disShuntWeight) {
		this.disShuntWeight = disShuntWeight;
	}

	@Column(name = "DIS_SHUNT_PIECE", precision = 22, scale = 0)
	public Long getDisShuntPiece() {
		return this.disShuntPiece;
	}

	public void setDisShuntPiece(Long disShuntPiece) {
		this.disShuntPiece = disShuntPiece;
	}

	@Column(name = "PLAN_CAR_TIME", length = 15)
	public String getPlanCarTime() {
		return this.planCarTime;
	}

	public void setPlanCarTime(String planCarTime) {
		this.planCarTime = planCarTime;
	}
	*/
	@Column(name="CREATE_NAME", length=20)
    public String getCreateName() {
        return this.createName;
    }
    
    public void setCreateName(String createName) {
        this.createName = createName;
    }
    
    @JSON(format="yyyy-MM-dd")
    @Column(name="CREATE_TIME", length=7)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="UPDATE_NAME", length=20)
    public String getUpdateName() {
        return this.updateName;
    }
    
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
    
    @JSON(format="yyyy-MM-dd")
    @Column(name="UPDATE_TIME", length=7)
    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    @Column(name="TS", length=15)
    public String getTs() {
        return this.ts;
    }
    
    public void setTs(String ts) {
        this.ts = ts;
    }

	/**
	 * @return the departId
	 */
    @Column(name="DEPART_ID")
	public Long getDepartId() {
		return departId;
	}

	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
    
    
}