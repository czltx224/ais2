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
 * CusCurrival entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_CURRIVAL")
public class CusCurrival implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String curName;//竞争对手名称
	private Long cusId;//客商编号
	private String cusName;//客商名称
	private String choiceReason;//选择对手原因
	private String curGoodness;//竞争对手优势
	private String curBadness;//竞争对手劣势
	private Date teamworkTime;//合作时间
	private Double teamworkWeight;//合作货量
	private String keyLinkman;//关键联系人
	private String curProjiect;//提供方案
	private Long departId;//部门编号
	private String createName;//创建人
	private Date createTime;
	private Date updateTime;
	private String updateName;
	private String ts;
	
	private Long cusRecordId;
	private Long status;

	// Constructors

	/** default constructor */
	public CusCurrival() {
	}

	/** minimal constructor */
	public CusCurrival(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_CURRIVAL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUR_NAME", length = 100)
	public String getCurName() {
		return this.curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}

	@Column(name = "CUS_NAME", length = 100)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "CHOICE_REASON", length = 500)
	public String getChoiceReason() {
		return this.choiceReason;
	}

	public void setChoiceReason(String choiceReason) {
		this.choiceReason = choiceReason;
	}

	@Column(name = "CUR_GOODNESS", length = 200)
	public String getCurGoodness() {
		return this.curGoodness;
	}

	public void setCurGoodness(String curGoodness) {
		this.curGoodness = curGoodness;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "TEAMWORK_TIME", length = 7)
	public Date getTeamworkTime() {
		return this.teamworkTime;
	}

	public void setTeamworkTime(Date teamworkTime) {
		this.teamworkTime = teamworkTime;
	}

	@Column(name = "TEAMWORK_WEIGHT", precision = 7, scale = 1)
	public Double getTeamworkWeight() {
		return this.teamworkWeight;
	}

	public void setTeamworkWeight(Double teamworkWeight) {
		this.teamworkWeight = teamworkWeight;
	}

	@Column(name = "KEY_LINKMAN", length = 100)
	public String getKeyLinkman() {
		return this.keyLinkman;
	}

	public void setKeyLinkman(String keyLinkman) {
		this.keyLinkman = keyLinkman;
	}

	@Column(name = "CUR_PROJIECT", length = 100)
	public String getCurProjiect() {
		return this.curProjiect;
	}

	public void setCurProjiect(String curProjiect) {
		this.curProjiect = curProjiect;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	/**
	 * @return the cusId
	 */
	@Column(name = "CUS_ID")
	public Long getCusId() {
		return cusId;
	}

	/**
	 * @param cusId the cusId to set
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	/**
	 * @return the ts
	 */
	@Column(name = "TS")
	public String getTs() {
		return ts;
	}

	/**
	 * @param ts the ts to set
	 */
	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the cusRecordId
	 */
	@Column(name = "CUS_RECORD_ID")
	public Long getCusRecordId() {
		return cusRecordId;
	}

	/**
	 * @param cusRecordId the cusRecordId to set
	 */
	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}

	/**
	 * @return the status
	 */
	@Column(name = "STATUS")
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the curBadness
	 */
	@Column(name = "CUR_BADNESS")
	public String getCurBadness() {
		return curBadness;
	}

	/**
	 * @param curBadness the curBadness to set
	 */
	public void setCurBadness(String curBadness) {
		this.curBadness = curBadness;
	}
	
}