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
 * CusGoodsRank entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="CUS_GOODS_RANK")
public class CusGoodsRank  implements java.io.Serializable,AuditableEntity {


    // Fields

     private Long id;
     private String rankName;
     private Long minVal;
     private Long maxVal;
     private String createName;
     private Date createTime;
     private String updateName;
     private Date updateTime;
     private String ts;


    // Constructors

    /** default constructor */
    public CusGoodsRank() {
    }

	/** minimal constructor */
    public CusGoodsRank(Long id) {
        this.id = id;
    }
       
    // Property accessors
    @Id 
    @SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_GOODS_RANK")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="RANK_NAME", length=50)

    public String getRankName() {
        return this.rankName;
    }
    
    public void setRankName(String rankName) {
        this.rankName = rankName;
    }
    
    @Column(name="MIN_VAL", precision=22, scale=0)

    public Long getMinVal() {
        return this.minVal;
    }
    
    public void setMinVal(Long minVal) {
        this.minVal = minVal;
    }
    
    @Column(name="MAX_VAl", precision=22, scale=0)

    public Long getMaxVal() {
        return this.maxVal;
    }
    
    public void setMaxVal(Long maxVal) {
        this.maxVal = maxVal;
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
   








}