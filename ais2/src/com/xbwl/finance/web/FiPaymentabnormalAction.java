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

	private Long fiPaymentId;// 应收付款单ID
	private Long typeid;// 异常类型id
	private Double amount; // 异常金额
	private String remark; // 异常备注

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
	 * 保存异常到付款
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
									+ "]已收款，不能再登记异常到付款!");
					addMessage("数据保存失败！");
				} else if (fp.getPaymentStatus().equals(5L)) {
					this.getValidateInfo().setSuccess(false);
					this.getValidateInfo().setMsg(
							fp.getPaymentType() + "[" + fp.getId()
									+ "]已付款，不能再登记异常到付款!");
					addMessage("数据保存失败！");
				} else if (fp.getPaymentStatus().equals(7L)) {
					this.getValidateInfo().setSuccess(false);
					this.getValidateInfo().setMsg(
							fp.getPaymentType() + "[" + fp.getId()
									+ "]已转欠款，不能再登记异常到付款!");
					addMessage("数据保存失败！");
				} else if (fp.getPaymentStatus().equals(8L)) {
					this.getValidateInfo().setSuccess(false);
					this.getValidateInfo().setMsg(
							fp.getPaymentType() + "[" + fp.getId()
									+ "]已登记过异常到付款，不能重复登记!");
					addMessage("数据保存失败！");
				} else {
					FiPaymentabnormal fiPaymentabnormal = new FiPaymentabnormal();
					fiPaymentabnormal.setFiPaymentId(this.fiPaymentId);
					fiPaymentabnormal.setAmount(this.getAmount());
					fiPaymentabnormal.setRemark(this.remark);
					fiPaymentabnormal.setType(this.typeid);
					this.fiPaymentabnormalService
							.saveException(fiPaymentabnormal);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("数据保存成功！");
					addMessage("数据保存成功！");
				}

			} else {
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg(
						WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}

		} catch (Exception e) {
			this.getValidateInfo().setSuccess(false);
			this.getValidateInfo().setMsg("数据保存失败！");
			addError("数据保存失败！", e);
		}
		return RELOAD;
	}

	/**
	 * 保存异常到付款前二次绑定
	 * 
	 * @throws Exception
	 */
	public void prepareSaveException() throws Exception {
		prepareModel();
	}

	// 撤销异常登记
	public String revocationException() throws Exception {
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
					this.fiPaymentabnormalService.revocationException(fiPaymentabnormal);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("撤销成功!");
			} else {
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg(
						WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}
		} catch (Exception e) {
			this.getValidateInfo().setSuccess(false);
			this.getValidateInfo().setMsg("数据保存失败！");
			addError("数据保存失败！", e);
		}
		return RELOAD;
	}

	/**
	 * 撤销异常登记前二次绑定
	 * 
	 * @throws Exception
	 */
	public void prepareRevocationException() throws Exception {
		prepareModel();
	}

	// 异常审核
	public String verificationException() throws Exception {
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
				this.fiPaymentabnormalService.verificationException(fiPaymentabnormal);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("审核成功!");
			} else {
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg(
						WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}

		} catch (Exception e) {
			this.getValidateInfo().setSuccess(false);
			this.getValidateInfo().setMsg("数据保存失败！");
			addError("数据保存失败！", e);
		}
		return RELOAD;
	}
	
	

	// 异常撤销审核
	public String verificationRegister() throws Exception {
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
				this.fiPaymentabnormalService.verificationRegister(fiPaymentabnormal);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("审核成功!");
			} else {
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg(
						WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}

		} catch (Exception e) {
			this.getValidateInfo().setSuccess(false);
			this.getValidateInfo().setMsg("数据保存失败！");
			addError("数据保存失败！", e);
		}
		return RELOAD;
	}
	
	/**
	 * 异常撤销审核前二次绑定
	 * 
	 * @throws Exception
	 */
	public void prepareVerificationRegister() throws Exception {
		prepareModel();
	}
	
	/**
	 * 撤销异常登记前二次绑定
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
