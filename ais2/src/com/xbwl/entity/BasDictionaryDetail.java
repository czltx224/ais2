package com.xbwl.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * BasDictionaryDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="bas_dictionary_detail")

public class BasDictionaryDetail  implements java.io.Serializable {


    // Fields

     private Long id;
     private Long basDictionaryId;
     private String typeName;
     private Long typeCode;
     


    // Constructors

    /** default constructor */
    public BasDictionaryDetail() {
    }

	/** minimal constructor */
    public BasDictionaryDetail(Long id) {
        this.id = id;
    }
   
    public BasDictionaryDetail(Long id, Long basDictionaryId, String typeName,
			Long typeCode) {
		super();
		this.id = id;
		this.basDictionaryId = basDictionaryId;
		this.typeName = typeName;
		this.typeCode = typeCode;
	}

	// Property accessors
    @Id 
    @Column(name="id", unique=true, nullable=false, precision=22, scale=0)
    @SequenceGenerator(name = "generator", sequenceName="SEQ_BAS_DICTIONARY_DETAIL")
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="dictionary_id", length=20)
 	public Long getBasDictionaryId() {
		return basDictionaryId;
	}

	public void setBasDictionaryId(Long basDictionaryId) {
		this.basDictionaryId = basDictionaryId;
	}
    
    @Column(name="type_name", length=20,unique=true)
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    @Column(name="type_code", precision=22, scale=0)
    public Long getTypeCode() {
        return this.typeCode;
    }
    
    public void setTypeCode(Long typeCode) {
        this.typeCode = typeCode;
    }


   








}