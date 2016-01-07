package com.xbwl.entity;

import static javax.persistence.GenerationType.SEQUENCE;

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


/**
 * BasDictionary entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="bas_dictionary")

public class BasDictionary  implements java.io.Serializable {


    // Fields

     private Long id;
     private String name;


    // Constructors

    /** default constructor */
    public BasDictionary() {
    }

	/** minimal constructor */
    public BasDictionary(Long id) {
        this.id = id;
    }
    
    /** full constructor */
    public BasDictionary(Long id, String name) {
        this.id = id;
        this.name = name;
    }

   
    // Property accessors
    @Id 
	@SequenceGenerator(name = "generator", sequenceName="SEQ_BAS_DICTIONARY_DETAIL")
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
    @Column(name="id", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="name", length=10)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}