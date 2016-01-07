package com.xbwl.finance.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiPaymentabnormal;
import com.xbwl.finance.Service.IFiPaymentService;
import com.xbwl.finance.Service.IFiPaymentabnormalService;

@Controller
@Action("fiPaymentabnormalAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiPaymentabnormal.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiPaymentabnormalAction extends SimpleActionSupport {

	@Resource(name = "fiPaymentabnormalServiceImpl")
	private IFiPaymentabnormalService fiPaymentabnormalService;
	private FiPaymentabnormal fiPaymentabnormal;

	@Resource(name = "fiPaymentServiceImpl")
	private IFiPaymentService fiPaymentService;

	private Long fiPaymentId;// Ӧ�ո��ID
	private Long typeid;// �쳣����id
	private Double amount; // �쳣���
	private String remark; // �쳣��ע

	@Override
	protected Object createNewInstance() {
		return new FiPaymentabnormal();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiPaymentabnormalService;
	}

	@Override
	public Object getModel() {
		return this.fiPaymentabnormal;
	}

	@Override
	public void setModel(Object obj) {
		this.fiPaymentabnormal = (FiPaymentabnormal) obj;

	}

	/**
	 * �����쳣������
	 * 
	 * @throws Exception
	 */
	public String saveException() throws Exception {
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
				FiPayment fp = this.fiPaymentService.getAndInitEntity(this
						.getFiPaymentId());
				if (fp.getPaymentStatus().equals(2L)) {
					this.getValidateInfo().setSuccess(false);
					this.getValidateInfo().setMsg(
							fp.getPaymentType() + "[" + fp.getId()
									+ "]���տ�����ٵǼ��쳣������!");
					addMessage("���ݱ���ʧ�ܣ�");
				} else if (fp.getPaymentStatus().equals(5L)) {
					this.getValidateInfo().setSuccess(false);
					this.getValidateInfo().setMsg(
							fp.getPaymentType() + "[" + fp.getId()
									+ "]�Ѹ�������ٵǼ��쳣������!");
					addMessage("���ݱ���ʧ�ܣ�");
				} else if (fp.getPaymentStatus().equals(7L)) {
					this.getValidateInfo().setSuccess(false);
					this.getValidateInfo().setMsg(
							fp.getPaymentType() + "[" + fp.getId()
									+ "]��תǷ������ٵǼ��쳣������!");
					addMessage("���ݱ���ʧ�ܣ�");
				} else if (fp.getPaymentStatus().equals(8L)) {
					this.getValidateInfo().setSuccess(false);
					this.getValidateInfo().setMsg(
							fp.getPaymentType() + "[" + fp.getId()
									+ "]�ѵǼǹ��쳣����������ظ��Ǽ�!");
					addMessage("���ݱ���ʧ�ܣ�");
				} else {
					FiPaymentabnormal fiPaymentabnormal = new FiPaymentabnormal();
					fiPaymentabnormal.setFiPaymentId(this.fiPaymentId);
					fiPaymentabnormal.setAmount(this.getAmount());
					fiPaymentabnormal.setRemark(this.remark);
					fiPaymentabnormal.setType(this.typeid);
					this.fiPaymentabnormalService
							.saveException(fiPaymentabnormal);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("���ݱ���ɹ���");
					addMessage("���ݱ���ɹ���");
				}

			} else {
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg(
						WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}

		} catch (Exception e) {
			this.getValidateInfo().setSuccess(false);
			this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
			addError("���ݱ���ʧ�ܣ�", e);
		}
		return RELOAD;
	}

	/**
	 * �����쳣������ǰ���ΰ�
	 * 
	 * @throws Exception
	 */
	public void prepareSaveException() throws Exception {
		prepareModel();
	}

	// �����쳣�Ǽ�
	public String revocationException() throws Exception {
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
					this.fiPaymentabnormalService.revocationException(fiPaymentabnormal);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("�����ɹ�!");
			} else {
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg(
						WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}
		} catch (Exception e) {
			this.getValidateInfo().setSuccess(false);
			this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
			addError("���ݱ���ʧ�ܣ�", e);
		}
		return RELOAD;
	}

	/**
	 * �����쳣�Ǽ�ǰ���ΰ�
	 * 
	 * @throws Exception
	 */
	public void prepareRevocationException() throws Exception {
		prepareModel();
	}

	// �쳣���
	public String verificationException() throws Exception {
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
				this.fiPaymentabnormalService.verificationException(fiPaymentabnormal);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("��˳ɹ�!");
			} else {
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg(
						WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}

		} catch (Exception e) {
			this.getValidateInfo().setSuccess(false);
			this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
			addError("���ݱ���ʧ�ܣ�", e);
		}
		return RELOAD;
	}
	
	

	// �쳣�������
	public String verificationRegister() throws Exception {
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
				this.fiPaymentabnormalService.verificationRegister(fiPaymentabnormal);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("��˳ɹ�!");
			} else {
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg(
						WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}

		} catch (Exception e) {
			this.getValidateInfo().setSuccess(false);
			this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
			addError("���ݱ���ʧ�ܣ�", e);
		}
		return RELOAD;
	}
	
	/**
	 * �쳣�������ǰ���ΰ�
	 * 
	 * @throws Exception
	 */
	public void prepareVerificationRegister() throws Exception {
		prepareModel();
	}
	
	/**
	 * �����쳣�Ǽ�ǰ���ΰ�
	 * 
	 * @throws Exception
	 */
	public void prepareVerificationException() throws Exception {
		prepareModel();
	}

	public Long getFiPaymentId() {
		return fiPaymentId;
	}

	public void setFiPaymentId(Long fiPaymentId) {
		this.fiPaymentId = fiPaymentId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getTypeid() {
		return typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

}
