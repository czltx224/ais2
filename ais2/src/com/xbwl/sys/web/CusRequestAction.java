package com.xbwl.sys.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.BasCusRequest;
import com.xbwl.sys.service.IBasCusRequestService;

/**
 *@author LiuHao
 *@time Jun 28, 2011 8:52:57 AM
 */
@Controller
@Action("cusRequestAction")
@Scope("prototype")
@Namespace("/sys")
@Results({
            @Result(name = "input", location = "/WEB-INF/xbwl/sys/cus_request.jsp", type = "dispatcher"),
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "tree", type = "json", params = {"root","areaList","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","includeProperties","*"})
         })
public class CusRequestAction extends SimpleActionSupport {
	@Resource(name="basCusRequestServiceImpl")
	private IBasCusRequestService cusRequestService;
	private BasCusRequest cusRequest;
	private String cpName;
	private String cusTel;
	@Override
	protected Object createNewInstance() {
		return new BasCusRequest();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return cusRequestService;
	}

	@Override
	public Object getModel() {
		return cusRequest;
	}

	@Override
	public void setModel(Object obj) {
		cusRequest=(BasCusRequest)obj;
	}

	/**
	 * @return the cpName
	 */
	public String getCpName() {
		return cpName;
	}

	/**
	 * @param cpName the cpName to set
	 */
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	/**
	 * @return the cusTel
	 */
	public String getCusTel() {
		return cusTel;
	}

	/**
	 * @param cusTel the cusTel to set
	 */
	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}
	public String save(){
		try {
			cusRequestService.save(cusRequest);
			this.getValidateInfo().setMsg("保存成功");
			this.getValidateInfo().setSuccess(true);
		} catch (Exception e) {
			addError("保存出错", e);
		}
		return RELOAD;
	}
	public String findRequest(){
		try {
			setPageConfig();
			cusRequestService.findRequest(this.getPages(), cpName, cusTel);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return "list";
	}
}
