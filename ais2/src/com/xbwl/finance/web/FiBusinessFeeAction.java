package com.xbwl.finance.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiBusinessFee;
import com.xbwl.finance.Service.IFiBusinessFeeService;
import com.xbwl.oper.fax.service.IOprFaxInService;

/**
 * author CaoZhili
 * time Oct 17, 2011 3:41:29 PM
 */
@Controller
@Action("fiBusinessFeeAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_businessFee.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo","excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }) })
public class FiBusinessFeeAction extends SimpleActionSupport{

	@Resource(name="fiBusinessFeeServiceImpl")
	private IFiBusinessFeeService fiBusinessFeeService;
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	private FiBusinessFee fiBusinessFee;
	
	private Long customerId;
	private String businessMonth;
	
	@Override
	protected Object createNewInstance() {
		return new FiBusinessFee();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiBusinessFeeService;
	}

	@Override
	public Object getModel() {
		return this.fiBusinessFee;
	}

	@Override
	public void setModel(Object obj) {
		this.fiBusinessFee=(FiBusinessFee)obj;
	}

	@Override
	public String save() throws Exception {
		return super.save();
	}

	/**从传真表统计货量
	 * @return
	 */
	public String countFaxCusGoods(){
		try{
			Map<String, Double> map  = oprFaxInService.countCusGoods(customerId,businessMonth);
			Struts2Utils.renderJson(map);
		}catch (Exception e) {
			addError("统计货量失败！", e);
		}
		return  null;
	}

	/**业务费管理审核方法
	 * @return 审核结果信息
	 */
	public String audit(){
		//原本计划可以审核多个的，现在改为只审核一个
		String idStrings[] = ServletActionContext.getRequest().getParameter("ids").split(",");
		String workflowNo = ServletActionContext.getRequest().getParameter("workflowNo");
		Double amount = Double.valueOf(ServletActionContext.getRequest().getParameter("amount"));
		Long status = 2l;//审核状态
		try{
			this.fiBusinessFeeService.auditStatusService(idStrings,status,workflowNo,amount);
		}catch (Exception e) {
			addError("业务费管理审核失败！", e);
		}
		return this.RELOAD;
	}
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getBusinessMonth() {
		return businessMonth;
	}

	public void setBusinessMonth(String businessMonth) {
		this.businessMonth = businessMonth;
	}
}
