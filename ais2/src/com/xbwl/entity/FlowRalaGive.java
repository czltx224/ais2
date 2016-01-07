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
 * FlowRalaGive entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="FLOW_RALA_GIVE")
public class FlowRalaGive  implements java.io.Serializable,AuditableEntity {

    // Fields

     private Long id;
     private Long pipeId;//管道ID
     private Date startDate;//开始日期
     private Date endDate;//结束日期
     private String startTime;//开始时间
     private String endTime;//结束时间
     private Long giveId;//赋予人ID
     private Long userId;//授权人ID
     private String createName;//创建人
     private Date createTime;//创建时间
     private String updateName;
     private Date updateTime;
     private String ts;
     private String remark;
     
     private Long departId;
     private String departName;


    // Constructors

    /** default constructor */
    public FlowRalaGive() {
    }

	/** minimal constructor */
    public FlowRalaGive(Long id) {
        this.id = id;
    }
    // Property accessors
    @Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_RALA_GIVE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="PIPE_ID", precision=22, scale=0)

    public Long getPipeId() {
        return this.pipeId;
    }
    
    public void setPipeId(Long pipeId) {
        this.pipeId = pipeId;
    }
    
    @JSON(format = "yyyy-MM-dd")
    @Column(name="START_DATE")
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    @JSON(format = "yyyy-MM-dd")
    @Column(name="END_DATE")
    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @Column(name="START_TIME", length=10)

    public String getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    @Column(name="END_TIME", length=10)
    public String getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    @Column(name="USER_ID", precision=22, scale=0)
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Column(name="CREATE_NAME", length=50)
    public String getCreateName() {
        return this.createName;
    }
    
    public void setCreateName(String createName) {
        this.createName = createName;
    }
    
    @JSON(format = "yyyy-MM-dd")
    @Column(name="CREATE_TIME")
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="UPDATE_NAME", length=50)
    public String getUpdateName() {
        return this.updateName;
    }
    
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
    
    @JSON(format = "yyyy-MM-dd")
    @Column(name="UPDATE_TIME")
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
    
    @Column(name="GIVE_ID")
	public Long getGiveId() {
		return giveId;
	}

	public void setGiveId(Long giveId) {
		this.giveId = giveId;
	}
	
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="DEPART_ID")
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	@Column(name="DEPART_NAME")
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
    








}