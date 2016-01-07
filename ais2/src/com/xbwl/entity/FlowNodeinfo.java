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
 * FlowNodeinfo entity.
 * ���̹��� �ڵ���Ϣ��
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_NODEINFO")
public class FlowNodeinfo implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String objName;//�ڵ�����
	private Long pipeId;//����ID
	private String nodeType;//�ڵ�����
	private Long isReject;//�Ƿ������˻�
	private Long rejectnodeId;//�˻ؽڵ�
	private String perPage;//�ڵ�Ԥ����ҳ��
	private String afterPage;//�ڵ����ҳ��
	private Long isRtx;//�Ƿ�rtx����(0������\1����)
	private String subBtnName;//�ύ��ť����
	private String saveBtnName;//���水ť����
	private Long isAutoflow;//�Ƿ��Զ���ת(0���Զ���ת\1�Զ���ת)
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	
	private Long drawxpos;//�ڵ�����X����
	private Long drawypos;//�ڵ�����Y����
	
	private Long status;//״̬ 0 ɾ��  1 ����
	// Constructors

	/** default constructor */
	public FlowNodeinfo() {
	}

	/** minimal constructor */
	public FlowNodeinfo(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_NODEINFO")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OBJ_NAME", length = 50)
	public String getObjName() {
		return this.objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	@Column(name = "PIPE_ID", precision = 22, scale = 0)
	public Long getPipeId() {
		return this.pipeId;
	}

	public void setPipeId(Long pipeId) {
		this.pipeId = pipeId;
	}

	@Column(name = "NODE_TYPE", length = 50)
	public String getNodeType() {
		return this.nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@Column(name = "IS_REJECT", precision = 22, scale = 0)
	public Long getIsReject() {
		return this.isReject;
	}

	public void setIsReject(Long isReject) {
		this.isReject = isReject;
	}

	@Column(name = "REJECTNODE_ID", precision = 22, scale = 0)
	public Long getRejectnodeId() {
		return this.rejectnodeId;
	}

	public void setRejectnodeId(Long rejectnodeId) {
		this.rejectnodeId = rejectnodeId;
	}

	@Column(name = "PER_PAGE")
	public String getPerPage() {
		return this.perPage;
	}

	public void setPerPage(String perPage) {
		this.perPage = perPage;
	}

	@Column(name = "AFTER_PAGE")
	public String getAfterPage() {
		return this.afterPage;
	}

	public void setAfterPage(String afterPage) {
		this.afterPage = afterPage;
	}

	@Column(name = "IS_RTX", precision = 22, scale = 0)
	public Long getIsRtx() {
		return this.isRtx;
	}

	public void setIsRtx(Long isRtx) {
		this.isRtx = isRtx;
	}

	@Column(name = "SUB_BTN_NAME", length = 50)
	public String getSubBtnName() {
		return this.subBtnName;
	}

	public void setSubBtnName(String subBtnName) {
		this.subBtnName = subBtnName;
	}

	@Column(name = "SAVE_BTN_NAME", length = 50)
	public String getSaveBtnName() {
		return this.saveBtnName;
	}

	public void setSaveBtnName(String saveBtnName) {
		this.saveBtnName = saveBtnName;
	}

	@Column(name = "IS_AUTOFLOW", precision = 22, scale = 0)
	public Long getIsAutoflow() {
		return this.isAutoflow;
	}

	public void setIsAutoflow(Long isAutoflow) {
		this.isAutoflow = isAutoflow;
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

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the status
	 */
	@Column(name = "STATUS", length = 15)
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
	 * @return the drawxpos
	 */
	@Column(name = "DRAWXPOS")
	public Long getDrawxpos() {
		return drawxpos;
	}

	/**
	 * @param drawxpos the drawxpos to set
	 */
	public void setDrawxpos(Long drawxpos) {
		this.drawxpos = drawxpos;
	}

	/**
	 * @return the drawypos
	 */
	@Column(name = "DRAWYPOS")
	public Long getDrawypos() {
		return drawypos;
	}

	/**
	 * @param drawypos the drawypos to set
	 */
	public void setDrawypos(Long drawypos) {
		this.drawypos = drawypos;
	}
	
}