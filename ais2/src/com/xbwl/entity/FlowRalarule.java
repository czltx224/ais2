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
 * FlowRalarule entity.
 * ����Ȩ�޷�������ʵ��
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_RALARULE")
public class FlowRalarule implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long nodeId;//�ڵ�ID
	private Long wfoptType;//��������(����\֪��\�����������)
	private Long shareType;//��������(��Ա����\��λ����\�ض������µĸ�λ��ϵ\��ɫ����)
	private Long userobjType;//��Ա����(ָ����Ա�����̲������ֶΡ���������Դ�ֶ�)
	private Long stationobjType;//��λ����(ָ����λ�����̲������ֶΡ���������Դ�ֶΡ�����λ�ֶ�)
	private Long usershareType;//�ض������µĸ�λ��ϵ(ֱ���ϼ���λ\�����ϼ���λ)
	private Long roleobjType;//��ɫ����(ָ����ɫ)
	private Long userIds;//ָ����Աid
	private Long formfieldId;//���ֶ�id
	private Long roleobjId;//��ɫid 
	private Long wfoperatornodeId;//���̽ڵ�id 
	private Long stationId;//��λID
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	
	private Long status;

	// Constructors

	/** default constructor */
	public FlowRalarule() {
	}

	/** minimal constructor */
	public FlowRalarule(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_RALARULE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NODE_ID", precision = 10, scale = 0)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "WFOPT_TYPE", precision = 10, scale = 0)
	public Long getWfoptType() {
		return this.wfoptType;
	}

	public void setWfoptType(Long wfoptType) {
		this.wfoptType = wfoptType;
	}

	@Column(name = "SHARE_TYPE", precision = 10, scale = 0)
	public Long getShareType() {
		return this.shareType;
	}

	public void setShareType(Long shareType) {
		this.shareType = shareType;
	}

	@Column(name = "USEROBJ_TYPE", precision = 10, scale = 0)
	public Long getUserobjType() {
		return this.userobjType;
	}

	public void setUserobjType(Long userobjType) {
		this.userobjType = userobjType;
	}

	@Column(name = "STATIONOBJ_TYPE", precision = 10, scale = 0)
	public Long getStationobjType() {
		return this.stationobjType;
	}

	public void setStationobjType(Long stationobjType) {
		this.stationobjType = stationobjType;
	}

	@Column(name = "USERSHARE_TYPE", precision = 10, scale = 0)
	public Long getUsershareType() {
		return this.usershareType;
	}

	public void setUsershareType(Long usershareType) {
		this.usershareType = usershareType;
	}

	@Column(name = "ROLEOBJ_TYPE", precision = 10, scale = 0)
	public Long getRoleobjType() {
		return this.roleobjType;
	}

	public void setRoleobjType(Long roleobjType) {
		this.roleobjType = roleobjType;
	}

	@Column(name = "USER_IDS", precision = 10, scale = 0)
	public Long getUserIds() {
		return this.userIds;
	}

	public void setUserIds(Long userIds) {
		this.userIds = userIds;
	}

	@Column(name = "FORMFIELD_ID", precision = 10, scale = 0)
	public Long getFormfieldId() {
		return this.formfieldId;
	}

	public void setFormfieldId(Long formfieldId) {
		this.formfieldId = formfieldId;
	}

	@Column(name = "ROLEOBJ_ID", precision = 10, scale = 0)
	public Long getRoleobjId() {
		return this.roleobjId;
	}

	public void setRoleobjId(Long roleobjId) {
		this.roleobjId = roleobjId;
	}

	@Column(name = "WFOPERATORNODE_ID", precision = 10, scale = 0)
	public Long getWfoperatornodeId() {
		return this.wfoperatornodeId;
	}

	public void setWfoperatornodeId(Long wfoperatornodeId) {
		this.wfoperatornodeId = wfoperatornodeId;
	}

	@Column(name = "STATION_ID", precision = 10, scale = 0)
	public Long getStationId() {
		return this.stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
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
	
}