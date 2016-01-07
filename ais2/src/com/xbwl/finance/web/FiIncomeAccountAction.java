package com.xbwl.finance.web;

import java.util.Date;
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
import com.xbwl.entity.FiIncomeAccount;
import com.xbwl.finance.Service.IFiIncomeAccountService;

@Controller
@Action("fiIncomeAccountAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_Income_Account.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiIncomeAccountAction extends SimpleActionSupport {

	@Resource(name="fiIncomeAccountServiceImpl")
	private IFiIncomeAccountService fiIncomeAccountService;
	
	private FiIncomeAccount fiIncomeAccount;
	
	private Date accountData;
	private Long departId;
	private Long batchNo;
	
	@Override
	protected Object createNewInstance() {
		return new FiIncomeAccount();
	}

	@Override
	public Map getContextMap() {
		// REVIEW Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiIncomeAccountService;
	}

	@Override
	public Object getModel() {
		return this.fiIncomeAccount;
	}

	@Override
	public void setModel(Object obj) {
		this.fiIncomeAccount=(FiIncomeAccount) obj;

	}
	
	//生成交账报表
	public String addAccountSingle() throws Exception{
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
				this.fiIncomeAccountService.addAccountSingle(departId,accountData);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("生成成功!");
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
	
	//作废交账报表
	public String revocation() throws Exception{
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
				this.fiIncomeAccountService.revocation(batchNo);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("作废成功!");
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
	
	//审核交账报表
	public String confirmAccountSingle() throws Exception{
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
				this.fiIncomeAccountService.confirmAccountSingle(batchNo);
					this.getValidateInfo().setSuccess(true);
					this.getValidateInfo().setMsg("交账确认成功!");
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

	public Date getAccountData() {
		return accountData;
	}

	public void setAccountData(Date accountData) {
		this.accountData = accountData;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
	
	
	

}
