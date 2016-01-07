package com.xbwl.rbac.entity;
// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * RalasafeRalasafeUserrole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RALASAFE_RALASAFE_USERROLE"
    ,schema="RALASAFE"
)

public class RalasafeRalasafeUserrole  implements java.io.Serializable {


    // Fields    

     private RalasafeRalasafeUserroleId id;
     private RalasafeRole ralasafeRole;


    // Constructors

    /** default constructor */
    public RalasafeRalasafeUserrole() {
    }

    
    /** full constructor */
    public RalasafeRalasafeUserrole(RalasafeRalasafeUserroleId id, RalasafeRole ralasafeRole) {
        this.id = id;
        this.ralasafeRole = ralasafeRole;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="userid", column=@Column(name="USERID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="roleid", column=@Column(name="ROLEID", nullable=false, precision=22, scale=0) ) } )

    public RalasafeRalasafeUserroleId getId() {
        return this.id;
    }
    
    public void setId(RalasafeRalasafeUserroleId id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ROLEID", nullable=false, insertable=false, updatable=false)

    public RalasafeRole getRalasafeRole() {
        return this.ralasafeRole;
    }
    
    public void setRalasafeRole(RalasafeRole ralasafeRole) {
        this.ralasafeRole = ralasafeRole;
    }
   








}