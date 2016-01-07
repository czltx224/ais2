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
 * CusRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_RECORD")
public class CusRecord implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String cusName;//�ͻ�����
	private String importanceLevel;//��Ҫ�̶ȷ���
	private String attentionClassify;//���ڹ�ע����
	private String type1;//�Զ�������
	private String area;//����
	private String bussAirport;//ҵ�����
	private String bussTel;//ҵ�������ϵ�绰
	private String isCq;//�Ƿ񷢻�����
	private String manCount;//��Ա��ģ
	private String developLevel;//�����׶�
	private Date lastBuss;//��󷢻�ʱ��
	private String addr;//��ַ
	private String mainBussiness;//��Ӫҵ��
	private String cusOrigin;//�ͻ���Դ����
	private String cusFrom;//�ͻ���Դ
	private String registerName;//����ע����
	private String aayuNum;//���̺�
	private Date registerDate;//ע��ʱ��
	private String phone;//�̶��绰
	private String businessEntity;//��ҵ����
	private Date lastCommunicate;//���ͨʱ��
	private String scopeBusiness;//��Ӫ��Χ
	private Double expectedCargo;//Ԥ�ƻ���
	private Double expectedTurnover;//Ԥ����Ӫҵ��
	private Long deliveryCycle;//��������
	private String information;//��ҵ��Ѷ
	private String competeCom;//������ҵ
	private String financialPositon;//����״��
	private Date startBuss;//��ʼ��������
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	private String departCode;//���ű���
	private Long cusId;//����ID ����̱����
	private Long status;//״̬ 0,ɾ����1������
	
	private String companyEmail;//��ҵ����
	private String webSite;//��ַ
	private String fax;//����
	private String profitType;//ӯ���Է���
	private String attentionRemark;//�ȵ�˵��
	private String province;//����ʡ
	private String city;//������
	private String companyRemark;//��˾���
	private String shortName;//���
	private Long departId;//ҵ����ID
	
	private Long principalId;//����ͷ�ԱID
	private String principal;//����ͷ�Ա
	
	private String settlement;//���㷽ʽ(�ֽ�\�½�\Ԥ��)
	private Long isProjectcustomer;//�Ƿ���Ŀ�ͻ�(0:������Ŀ�ͻ�\1:����Ŀ�ͻ�)
	
	private Long warnDeliveryCycle;//Ԥ����������
	private String remark;//��ע
	

	// Constructors

	/** default constructor */
	public CusRecord() {
	}
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_RECORD")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUS_NAME", nullable = false, length = 200)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "IMPORTANCE_LEVEL", nullable = false, length = 20)
	public String getImportanceLevel() {
		return this.importanceLevel;
	}

	public void setImportanceLevel(String importanceLevel) {
		this.importanceLevel = importanceLevel;
	}

	@Column(name = "ATTENTION_CLASSIFY", nullable = false, length = 20)
	public String getAttentionClassify() {
		return this.attentionClassify;
	}

	public void setAttentionClassify(String attentionClassify) {
		this.attentionClassify = attentionClassify;
	}

	@Column(name = "TYPE1", length = 50)
	public String getType1() {
		return this.type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	@Column(name = "AREA", length = 50)
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "BUSS_AIRPORT", length = 50)
	public String getBussAirport() {
		return this.bussAirport;
	}

	public void setBussAirport(String bussAirport) {
		this.bussAirport = bussAirport;
	}

	@Column(name = "BUSS_TEL", length = 200)
	public String getBussTel() {
		return this.bussTel;
	}

	public void setBussTel(String bussTel) {
		this.bussTel = bussTel;
	}

	@Column(name = "IS_CQ", length = 20)
	public String getIsCq() {
		return this.isCq;
	}

	public void setIsCq(String isCq) {
		this.isCq = isCq;
	}

	@Column(name = "MAN_COUNT")
	public String getManCount() {
		return this.manCount;
	}

	public void setManCount(String manCount) {
		this.manCount = manCount;
	}

	@Column(name = "DEVELOP_LEVEL", nullable = false, length = 20)
	public String getDevelopLevel() {
		return this.developLevel;
	}

	public void setDevelopLevel(String developLevel) {
		this.developLevel = developLevel;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "LAST_BUSS", length = 7)
	public Date getLastBuss() {
		return this.lastBuss;
	}

	public void setLastBuss(Date lastBuss) {
		this.lastBuss = lastBuss;
	}

	@Column(name = "MAIN_BUSSINESS", length = 200)
	public String getMainBussiness() {
		return this.mainBussiness;
	}

	public void setMainBussiness(String mainBussiness) {
		this.mainBussiness = mainBussiness;
	}

	@Column(name = "CUS_ORIGIN", length = 200)
	public String getCusOrigin() {
		return this.cusOrigin;
	}

	public void setCusOrigin(String cusOrigin) {
		this.cusOrigin = cusOrigin;
	}

	@Column(name = "CUS_FROM", length = 200)
	public String getCusFrom() {
		return this.cusFrom;
	}

	public void setCusFrom(String cusFrom) {
		this.cusFrom = cusFrom;
	}

	@Column(name = "REGISTER_NAME", length = 200)
	public String getRegisterName() {
		return this.registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	@Column(name = "AAYU_NUM", length = 200)
	public String getAayuNum() {
		return this.aayuNum;
	}

	public void setAayuNum(String aayuNum) {
		this.aayuNum = aayuNum;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "REGISTER_DATE", length = 7)
	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Column(name = "PHONE", length = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "BUSINESS_ENTITY", length = 200)
	public String getBusinessEntity() {
		return this.businessEntity;
	}

	public void setBusinessEntity(String businessEntity) {
		this.businessEntity = businessEntity;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "LAST_COMMUNICATE", length = 7)
	public Date getLastCommunicate() {
		return this.lastCommunicate;
	}

	public void setLastCommunicate(Date lastCommunicate) {
		this.lastCommunicate = lastCommunicate;
	}

	@Column(name = "SCOPE_BUSINESS", length = 500)
	public String getScopeBusiness() {
		return this.scopeBusiness;
	}

	public void setScopeBusiness(String scopeBusiness) {
		this.scopeBusiness = scopeBusiness;
	}

	@Column(name = "EXPECTED_CARGO", precision = 22, scale = 0)
	public Double getExpectedCargo() {
		return this.expectedCargo;
	}

	public void setExpectedCargo(Double expectedCargo) {
		this.expectedCargo = expectedCargo;
	}

	@Column(name = "EXPECTED_TURNOVER", precision = 22, scale = 0)
	public Double getExpectedTurnover() {
		return this.expectedTurnover;
	}

	public void setExpectedTurnover(Double expectedTurnover) {
		this.expectedTurnover = expectedTurnover;
	}

	@Column(name = "DELIVERY_CYCLE", precision = 22, scale = 0)
	public Long getDeliveryCycle() {
		return this.deliveryCycle;
	}

	public void setDeliveryCycle(Long deliveryCycle) {
		this.deliveryCycle = deliveryCycle;
	}

	@Column(name = "INFORMATION", length = 500)
	public String getInformation() {
		return this.information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Column(name = "COMPETE_COM", length = 500)
	public String getCompeteCom() {
		return this.competeCom;
	}

	public void setCompeteCom(String competeCom) {
		this.competeCom = competeCom;
	}

	@Column(name = "FINANCIAL_POSITON", length = 50)
	public String getFinancialPositon() {
		return this.financialPositon;
	}

	public void setFinancialPositon(String financialPositon) {
		this.financialPositon = financialPositon;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "START_BUSS", length = 7)
	public Date getStartBuss() {
		return this.startBuss;
	}

	public void setStartBuss(Date startBuss) {
		this.startBuss = startBuss;
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

	@Column(name = "UPDATE_NAME")
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "DEPART_CODE")
	public String getDepartCode() {
		return this.departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}

	@Column(name = "CUS_ID", precision = 22, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * @return the addr
	 */
	@Column(name = "ADDR")
	public String getAddr() {
		return addr;
	}
	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * @return the companyEmail
	 */
	@Column(name = "COMPANY_EMAIL")
	public String getCompanyEmail() {
		return companyEmail;
	}
	/**
	 * @param companyEmail the companyEmail to set
	 */
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	/**
	 * @return the webSite
	 */
	@Column(name = "WEBSITE")
	public String getWebSite() {
		return webSite;
	}
	/**
	 * @param webSite the webSite to set
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	/**
	 * @return the fax
	 */
	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return the profitType
	 */
	@Column(name = "PROFIT_TYPE")
	public String getProfitType() {
		return profitType;
	}
	/**
	 * @param profitType the profitType to set
	 */
	public void setProfitType(String profitType) {
		this.profitType = profitType;
	}
	/**
	 * @return the attentionRemark
	 */
	@Column(name = "ATTENTION_REMARK")
	public String getAttentionRemark() {
		return attentionRemark;
	}
	/**
	 * @param attentionRemark the attentionRemark to set
	 */
	public void setAttentionRemark(String attentionRemark) {
		this.attentionRemark = attentionRemark;
	}
	/**
	 * @return the province
	 */
	@Column(name = "PROVINCE")
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the city
	 */
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the companyRemark
	 */
	@Column(name = "COMPANY_REMARK")
	public String getCompanyRemark() {
		return companyRemark;
	}
	/**
	 * @param companyRemark the companyRemark to set
	 */
	public void setCompanyRemark(String companyRemark) {
		this.companyRemark = companyRemark;
	}
	/**
	 * @return the shortName
	 */
	@Column(name = "SHORT_NAME")
	public String getShortName() {
		return shortName;
	}
	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	/**
	 * @return the departId
	 */
	@Column(name = "DEPART_ID")
	public Long getDepartId() {
		return departId;
	}
	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	/**
	 * @return the principalId
	 */
	@Column(name = "PRINCIPAL_ID")
	public Long getPrincipalId() {
		return principalId;
	}
	/**
	 * @param principalId the principalId to set
	 */
	public void setPrincipalId(Long principalId) {
		this.principalId = principalId;
	}
	/**
	 * @return the principal
	 */
	@Column(name = "PRINCIPAL")
	public String getPrincipal() {
		return principal;
	}
	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	/**
	 * @return the settlement
	 */
	@Column(name = "SETTLEMENT")
	public String getSettlement() {
		return settlement;
	}
	/**
	 * @param settlement the settlement to set
	 */
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	/**
	 * @return the isProjectcustomer
	 */
	@Column(name = "IS_PROJECTCUSTOMER")
	public Long getIsProjectcustomer() {
		return isProjectcustomer;
	}
	/**
	 * @param isProjectcustomer the isProjectcustomer to set
	 */
	public void setIsProjectcustomer(Long isProjectcustomer) {
		this.isProjectcustomer = isProjectcustomer;
	}
	/**
	 * @return the warnDeliveryCycle
	 */
	@Column(name = "WARN_DELIVERY_CYCLE")
	public Long getWarnDeliveryCycle() {
		return warnDeliveryCycle;
	}
	/**
	 * @param warnDeliveryCycle the warnDeliveryCycle to set
	 */
	public void setWarnDeliveryCycle(Long warnDeliveryCycle) {
		this.warnDeliveryCycle = warnDeliveryCycle;
	}
	/**
	 * @return the remark
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}