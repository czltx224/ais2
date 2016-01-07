package com.xbwl.rbac.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * RalasafeRalasafeUserroleId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class RalasafeRalasafeUserroleId  implements java.io.Serializable {


    // Fields    

     private Long userid;
     private Long roleid;


    // Constructors

    /** default constructor */
    public RalasafeRalasafeUserroleId() {
    }

    
    /** full constructor */
    public RalasafeRalasafeUserroleId(Long userid, Long roleid) {
        this.userid = userid;
        this.roleid = roleid;
    }

   
    // Property accessors

    @Column(name="USERID", nullable=false, precision=22, scale=0)

    public Long getUserid() {
        return this.userid;
    }
    
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Column(name="ROLEID", nullable=false, precision=22, scale=0)

    public Long getRoleid() {
        return this.roleid;
    }
    
    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RalasafeRalasafeUserroleId) ) return false;
		 RalasafeRalasafeUserroleId castOther = ( RalasafeRalasafeUserroleId ) other; 
         
		 return ( (this.getUserid()==castOther.getUserid()) || ( this.getUserid()!=null && castOther.getUserid()!=null && this.getUserid().equals(castOther.getUserid()) ) )
 && ( (this.getRoleid()==castOther.getRoleid()) || ( this.getRoleid()!=null && castOther.getRoleid()!=null && this.getRoleid().equals(castOther.getRoleid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserid() == null ? 0 : this.getUserid().hashCode() );
         result = 37 * result + ( getRoleid() == null ? 0 : this.getRoleid().hashCode() );
         return result;
   }   





}