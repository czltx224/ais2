package com.xbwl.finance.web;

import java.util.Date;
import java.util.HashMap;
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

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.FiInternalDivide;
import com.xbwl.finance.Service.IFiInternalDivideService;

/**
 * author CaoZhili time Oct 21, 2011 4:33:39 PM
 * 内部结算划分控制层操作类
 */
@Controller
@Action("fiInternalDivideAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_fiInternalDivide.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class FiInternalDivideAction extends SimpleActionSupport {

	@Resource(name = "fiInternalDivideServiceImpl")
	private IFiInternalDivideService fiInternalDivideService;

	private FiInternalDivide fiInternalDivide;
	
	private Long id;//ID,主键
	private String auditRemark;//审核备注
	private Double amount;//金额
	private String departId1;

	@Override
	protected Object createNewInstance() {
		return new FiInternalDivide();
	}

	@Override
	public Map getContextMap() {
		Map map=new HashMap();
		map.put("departId1",departId1);
		return map;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiInternalDivideService;
	}

	@Override
	public Object getModel() {
		return this.fiInternalDivide;
	}

	@Override
	public void setModel(Object obj) {
		this.fiInternalDivide = (FiInternalDivide) obj;
	}

	/**内部结算划分审核方法
	 * @return
	 */
	public String audit(){
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		
		//this.fiInternalDivide= new FiInternalDivide();
		this.fiInternalDivide.setAmount(this.amount);
		//this.fiInternalDivide.setId(this.id);
		this.fiInternalDivide.setAuditStatus(2l);
		this.fiInternalDivide.setAuditRemark(this.auditRemark);
		this.fiInternalDivide.setAuditTime(new Date());
		this.fiInternalDivide.setAuditName(user.get("name").toString());
		
		try{
			this.fiInternalDivideService.auditService(this.fiInternalDivide);
			getValidateInfo().setMsg("内部结算划分审核成功！");
		}catch (Exception e) {
			addError("内部结算划分审核失败！", e);
		}
		
		return this.RELOAD;
	}
	
	public void prepareAudit() throws Exception {
	      super.prepareModel();
		}
	
	/**内部结算划分撤销审核方法
	 * @return
	 */
	public String cancelAudit(){
		
		String strIds[] = ServletActionContext.getRequest().getParameter("ids").split(",");
		try{
			this.fiInternalDivideService.cancelAuditService(strIds);
			getValidateInfo().setMsg("内部结算划分撤销审核成功！");
		}catch (Exception e) {
			addError("内部结算划分撤销审核失败！", e);
		}
		
		return this.RELOAD;
	}
	
	/**内部结算划分作废方法
	 * @return
	 */
	public String invalid(){
		String strIds[] = ServletActionContext.getRequest().getParameter("ids").split(",");
		try{
			this.fiInternalDivideService.invalidService(strIds);
			getValidateInfo().setMsg("内部结算划分作废成功！");
		}catch (Exception e) {
			addError("内部结算划分作废失败！", e);
		}
		return this.RELOAD;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDepartId1() {
		return departId1;
	}

	public void setDepartId1(String departId1) {
		this.departId1 = departId1;
	}
	
	
}
