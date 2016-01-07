package com.xbwl.finance.web;

import java.util.HashMap;
import java.util.List;
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
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.finance.Service.IFiCapitaaccountsetService;

@Controller
@Action("fiCapitaaccountsetAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiCapitaaccountset.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiCapitaaccountsetAction extends SimpleActionSupport {

	@Resource(name = "fiCapitaaccountsetServiceImpl")
	private IFiCapitaaccountsetService fiCapitaaccountsetService;
	
	private FiCapitaaccountset fiCapitaaccountset;
	
	Long paymentType;
	Long departId;
	Long userId;
	Long accountType;
	String accountNum;
	String accountName;

	@Override
	protected Object createNewInstance() {
		return new FiCapitaaccountset();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiCapitaaccountsetService;
	}

	@Override
	public Object getModel() {
		return this.fiCapitaaccountset;
	}

	@Override
	public void setModel(Object obj) {
		this.fiCapitaaccountset = (FiCapitaaccountset) obj;

	}
	
	//保存资金账号
	public String saveFiCapitaaccountset() throws Exception{
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				this.fiCapitaaccountsetService.saveFiCapitaaccountset(fiCapitaaccountset);
    			this.getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
		}
		return RELOAD;
	}

	/**
	 * 查询账号信息,所有查询都包括收支账号
	 * @return
	 * @throws Exception
	 */
	public String findAccountList() throws Exception{
		try {
			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
			setPageConfig();
			Map map=new HashMap<String,Object>();
			map.put("paymentType", paymentType);
			map.put("departId", departId);
			map.put("userId", userId);
			map.put("accountNum", accountNum);
			map.put("accountName", accountName);
			map.put("accountType", accountType);
			this.fiCapitaaccountsetService.findAccountList(map, this.getPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
    /**
     * 保存出纳收款单前二次绑定
     * @throws Exception
     */
    public void prepareSaveFiCapitaaccountset() throws Exception {
    	prepareModel();
    }



	public Long getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getAccountName() {
		return accountName;
	}
	

	public Long getAccountType() {
		return accountType;
	}

	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	
    
}
