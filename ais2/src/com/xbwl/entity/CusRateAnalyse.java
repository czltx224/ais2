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


/**
 * CusRateAnalyse entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="CUS_RATE_ANALYSE"
)

public class CusRateAnalyse  implements java.io.Serializable {
    // Fields
     private Long id;
     private Long departId;
     private String departName;
     private String cusDepartName;
     private String cusDepartCode;
     private String endCity;
     private String productType;
     private Long cusId;
     private String cusName;
     private String trafficMode;
     private Date countTime;
     private Double rate;
     private Double rebate;


    // Constructors

    /** default constructor */
    public CusRateAnalyse() {
    }

	/** minimal constructor */
    public CusRateAnalyse(Long id) {
        this.id = id;
    }
   
    // Property accessors
    @Id 
    @SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_RATE_ANALYSE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="DEPART_ID", precision=22, scale=0)

    public Long getDepartId() {
        return this.departId;
    }
    
    public void setDepartId(Long departId) {
        this.departId = departId;
    }
    
    @Column(name="DEPART_NAME", length=200)

    public String getDepartName() {
        return this.departName;
    }
    
    public void setDepartName(String departName) {
        this.departName = departName;
    }
    
    @Column(name="CUS_DEPART_NAME", length=200)

    public String getCusDepartName() {
        return this.cusDepartName;
    }
    
    public void setCusDepartName(String cusDepartName) {
        this.cusDepartName = cusDepartName;
    }
    
    @Column(name="CUS_DEPART_CODE", length=100)

    public String getCusDepartCode() {
        return this.cusDepartCode;
    }
    
    public void setCusDepartCode(String cusDepartCode) {
        this.cusDepartCode = cusDepartCode;
    }
    
    @Column(name="END_CITY", length=100)

    public String getEndCity() {
        return this.endCity;
    }
    
    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }
    
    @Column(name="PRODUCT_TYPE", length=50)

    public String getProductType() {
        return this.productType;
    }
    
    public void setProductType(String productType) {
        this.productType = productType;
    }
    
    @Column(name="CUS_ID", precision=22, scale=0)

    public Long getCusId() {
        return this.cusId;
    }
    
    public void setCusId(Long cusId) {
        this.cusId = cusId;
    }
    
    @Column(name="CUS_NAME", length=200)

    public String getCusName() {
        return this.cusName;
    }
    
    public void setCusName(String cusName) {
        this.cusName = cusName;
    }
    
    @Column(name="TRAFFIC_MODE", length=50)

    public String getTrafficMode() {
        return this.trafficMode;
    }
    
    public void setTrafficMode(String trafficMode) {
        this.trafficMode = trafficMode;
    }
    @JSON(format="yyyy-MM-dd")
    @Column(name="COUNT_TIME", length=7)

    public Date getCountTime() {
        return this.countTime;
    }
    
    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }
    
    @Column(name="RATE", precision=7)

    public Double getRate() {
        return this.rate;
    }
    
    public void setRate(Double rate) {
        this.rate = rate;
    }
    
    @Column(name="REBATE", precision=7)
    public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}
}