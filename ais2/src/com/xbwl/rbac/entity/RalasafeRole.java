package com.xbwl.rbac.entity;
// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * RalasafeRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RALASAFE_ROLE"
    ,schema="RALASAFE"
, uniqueConstraints = @UniqueConstraint(columnNames="NAME")
)

public class RalasafeRole  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String name;
     private String description;
     private Set<RalasafeRalasafeUserrole> ralasafeRalasafeUserroles = new HashSet<RalasafeRalasafeUserrole>(0);
     private Set<RalasafeRoleprivilege> ralasafeRoleprivileges = new HashSet<RalasafeRoleprivilege>(0);


    // Constructors

    /** default constructor */
    public RalasafeRole() {
    }

	/** minimal constructor */
    public RalasafeRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    /** full constructor */
    public RalasafeRole(Long id, String name, String description, Set<RalasafeRalasafeUserrole> ralasafeRalasafeUserroles, Set<RalasafeRoleprivilege> ralasafeRoleprivileges) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ralasafeRalasafeUserroles = ralasafeRalasafeUserroles;
        this.ralasafeRoleprivileges = ralasafeRoleprivileges;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="NAME", unique=true, nullable=false, length=100)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="DESCRIPTION", length=500)

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="ralasafeRole")

    public Set<RalasafeRalasafeUserrole> getRalasafeRalasafeUserroles() {
        return this.ralasafeRalasafeUserroles;
    }
    
    public void setRalasafeRalasafeUserroles(Set<RalasafeRalasafeUserrole> ralasafeRalasafeUserroles) {
        this.ralasafeRalasafeUserroles = ralasafeRalasafeUserroles;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="ralasafeRole")

    public Set<RalasafeRoleprivilege> getRalasafeRoleprivileges() {
        return this.ralasafeRoleprivileges;
    }
    
    public void setRalasafeRoleprivileges(Set<RalasafeRoleprivilege> ralasafeRoleprivileges) {
        this.ralasafeRoleprivileges = ralasafeRoleprivileges;
    }
   








}