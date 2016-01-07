package com.xbwl.entity;

import static javax.persistence.GenerationType.SEQUENCE;

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
 * RequestTypeDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="REQUEST_TYPE_DETAIL"
)

public class RequestTypeDetail  implements java.io.Serializable ,AuditableEntity{


    // Fields    

     private Long id;
     private String requestItem;//需求类型
     private String requestStage;//需求阶段
     private Long requestTypeMainId;
     private String createName;
     private Date createTime;
     private String updateName;
     private Date updateTime;
     private String ts;


    // Constructors

    /** default constructor */
    public RequestTypeDetail() {
    }

	/** minimal constructor */
    public RequestTypeDetail(Long id) {
        this.id = id;
    } 
    // Property accessors
    @Id 
    @SequenceGenerator(name = "generator", sequenceName="SEQ_REQUEST_TYPE_DETAIL")
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="REQUEST_STAGE", length=20)

    public String getRequestStage() {
        return this.requestStage;
    }
    
    public void setRequestStage(String requestStage) {
        this.requestStage = requestStage;
    }
    
    @Column(name="REQUEST_TYPE_MAIN_ID", precision=22, scale=0)

    public Long getRequestTypeMainId() {
        return this.requestTypeMainId;
    }
    
    public void setRequestTypeMainId(Long requestTypeMainId) {
        this.requestTypeMainId = requestTypeMainId;
    }
    
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
    @Column(name="REQUEST_ITEM", length=100)
	public String getRequestItem() {
		return requestItem;
	}

	public void setRequestItem(String requestItem) {
		this.requestItem = requestItem;
	}
   








}