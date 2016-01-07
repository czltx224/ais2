package com.xbwl.oper.stock.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprSign;
import com.xbwl.oper.stock.service.IOprRequestDoService;
import com.xbwl.oper.stock.service.IOprSignService;
import com.xbwl.oper.stock.service.IOprStatusService;

/**
 * author shuw
 * time 2011-7-14 ����11:14:46
 */

@Controller
@Action("oprSignAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fax/opr_sign.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }

		) }

)
public class OprSignAction extends SimpleActionSupport {

	@Resource(name="oprSignServiceImpl")
	private IOprSignService oprSignService;
	
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Resource(name="oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;
	
	private OprSign oprSign;
	
	private Long  dno;  //���͵���
	private String  IDNumber;   //֤������
	private String IDNumberTwo;   //֤������2
	private String consignee;     //ǩ����
	private String consigneeTwo;	   //ǩ����
	private Long realPiece;      //����
	private String typeNameTwo;   //֤������
	private String typeName;         //֤������
	private String receiptType;        //ǩ������
	private String scanAdd;  //ɨ��ͼƬ��ַ
	
	private Double consigneeRate;   //�������ͷ���
	private Double inPaymentCollection;
	private Double consigneeFee;  // �������ͷ�
	private Double cusValueAddFee;   //����ֵ�����
	private Double storeFee;             //��汣�ܷ�
	
	/**
	 * ���ǩ��
	 * @return
	 */
	public String saveSignStatus(){
		try {
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId = Long.parseLong(user.get("bussDepart")+"");
			this.oprSign = new OprSign();
			this.oprSign.setSignMan(this.consignee);
			this.oprSign.setReplaceSign(this.consigneeTwo);
			this.oprSign.setIdentityCard(this.IDNumber);
			this.oprSign.setReIdentityCard(this.IDNumberTwo);
			this.oprSign.setDepartId(bussDepartId);
			this.oprSign.setDno(this.dno);
			this.oprSign.setSignSource("�������");
			this.oprSign.setCardType(this.receiptType);
			this.oprSign.setScanAdd(scanAdd);
			
			oprSignService.saveSignStatusByFaxIn( this.oprSign,storeFee,consigneeRate,consigneeFee,cusValueAddFee);
			getValidateInfo().setMsg("���ݱ���ɹ���");
			addMessage("���ݱ���ɹ���");
			
		} catch (Exception e) {
			addError("���ݱ���ʧ�ܣ�", e);
		}
			return RELOAD;
	}
	

	
	public Double getStoreFee() {
		return storeFee;
	}

	public void setStoreFee(Double storeFee) {
		this.storeFee = storeFee;
	}

	@Override
	protected Object createNewInstance() {
		return new OprSign();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprSignService;
	}

	@Override
	public Object getModel() {
		return  oprSign;
	}

	@Override
	public void setModel(Object obj) {
		oprSign=(OprSign)obj;
	}
	
	
	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String number) {
		IDNumber = number;
	}

	public String getIDNumberTwo() {
		return IDNumberTwo;
	}

	public void setIDNumberTwo(String numberTwo) {
		IDNumberTwo = numberTwo;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneeTwo() {
		return consigneeTwo;
	}

	public void setConsigneeTwo(String consigneeTwo) {
		this.consigneeTwo = consigneeTwo;
	}
	
	public IOprSignService getOprSignService() {
		return oprSignService;
	}

	public void setOprSignService(IOprSignService oprSignService) {
		this.oprSignService = oprSignService;
	}

	public IOprStatusService getOprStatusService() {
		return oprStatusService;
	}

	public void setOprStatusService(IOprStatusService oprStatusService) {
		this.oprStatusService = oprStatusService;
	}

	public OprSign getOprSign() {
		return oprSign;
	}

	public void setOprSign(OprSign oprSign) {
		this.oprSign = oprSign;
	}

	public Double getInPaymentCollection() {
		return inPaymentCollection;
	}

	public void setInPaymentCollection(Double inPaymentCollection) {
		this.inPaymentCollection = inPaymentCollection;
	}

	public Double getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}
 
	
	public Double getCusValueAddFee() {
		return cusValueAddFee;
	}



	public void setCusValueAddFee(Double cusValueAddFee) {
		this.cusValueAddFee = cusValueAddFee;
	}



	public String signReturn(){
		String dnostr = ServletActionContext.getRequest().getParameter("dnos");
		if(null==dnostr || "".equals(dnostr)){
			throw new ServiceException("���͵���Ϊ��!");
		}
		try {
			String[] dnos = dnostr.split(",");
			oprSignService.delSign(dnos);
		} catch (NumberFormatException e) {
			getValidateInfo().setSuccess(false);
			logger.error("����ʧ�ܣ�");
			getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
			addError("���ݱ���ʧ�ܣ�", e);
		} catch (Exception e) {
			getValidateInfo().setSuccess(false);
			logger.error("����ʧ�ܣ�");
			getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
			addError("���ݱ���ʧ�ܣ�", e);
		}
		return RELOAD;
	}

	public Long getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}

	public Double getConsigneeRate() {
		return consigneeRate;
	}
	
	public void setConsigneeRate(Double consigneeRate) {
		this.consigneeRate = consigneeRate;
	}

	public String getTypeNameTwo() {
		return typeNameTwo;
	}

	public void setTypeNameTwo(String typeNameTwo) {
		this.typeNameTwo = typeNameTwo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	@Override
	public String save() throws Exception {
		try{
			String isOpr = ServletActionContext.getRequest().getParameter("isOpr");
			String requestId = ServletActionContext.getRequest().getParameter("requestId");
			User user = WebRalasafe.getCurrentUser(ServletActionContext
					.getRequest());
			if(null!=requestId && requestId.length()>0){//����и��Ի�Ҫ�����޸�
				OprRequestDo requestDo = this.oprRequestDoService.get(Long.valueOf(requestId));
				requestDo.setIsOpr(Long.valueOf(isOpr));
				requestDo.setOprMan(user.get("name")+"");
				this.oprRequestDoService.save(requestDo);
			}
		}catch (Exception e) {
			addError(e.getLocalizedMessage(), e);
		}
		return super.save();
	}

	public String getScanAdd() {
		return scanAdd;
	}

	public void setScanAdd(String scanAdd) {
		this.scanAdd = scanAdd;
	}
}
