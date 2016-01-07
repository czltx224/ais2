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
import com.xbwl.entity.FiRepayment;
import com.xbwl.finance.Service.IFiReceiptService;
import com.xbwl.finance.Service.IFiRepaymentService;

@Controller
@Action("fiRepaymentAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiRepayment.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })	
public class FiRepaymentAction extends SimpleActionSupport {

	@Resource(name="fiRepaymentServiceImpl")
	private IFiRepaymentService fiRepaymentService;
	private FiRepayment fiRepayment;
	
	private String ids;//还款明细表IDS
	
	@Override
	protected Object createNewInstance() {
		return new FiRepayment();
	}

	@Override
	public Map getContextMap() {
		// REVIEW Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiRepaymentService;
	}

	@Override
	public Object getModel() {
		return this.fiRepayment;
	}

	@Override
	public void setModel(Object obj) {
		this.fiRepayment=(FiRepayment) obj;

	}

	//交账
	public String confirmAccountSingle() throws Exception{
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), this
					.getPrivilege(), getModel(), getContextMap())) {
				this.fiRepaymentService.confirmAccountSingle(ids);
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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
}
