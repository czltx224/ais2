package com.xbwl.sys.vo;



/**
 * BasDictionaryDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BasDictionaryVo  implements java.io.Serializable {
    // Fields
     private Long id;
     private Long basDictionaryId;
     private String typeName;
     private Long typeCode;
     private String basDictionaryName;
     


    // Constructors

    /** default constructor */
    public BasDictionaryVo() {
    }

	/** minimal constructor */
    public BasDictionaryVo(Long id) {
        this.id = id;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBasDictionaryId() {
		return basDictionaryId;
	}

	public void setBasDictionaryId(Long basDictionaryId) {
		this.basDictionaryId = basDictionaryId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Long typeCode) {
		this.typeCode = typeCode;
	}

	public String getBasDictionaryName() {
		return basDictionaryName;
	}

	public void setBasDictionaryName(String basDictionaryName) {
		this.basDictionaryName = basDictionaryName;
	}

}