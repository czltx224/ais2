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
import com.xbwl.entity.ConsigneeDealPrice;
import com.xbwl.sys.service.IConDealPriceService;

/**
 *@author LiuHao
 *@time Jun 29, 2011 9:59:43 AM
 */
@Controller
@Action("conDealPriceAction")
@Scope("prototype")
@Namespace("/sys")
@Results({
            @Result(name = "input", location = "/WEB-INF/xbwl/sys/conDealPrice.jsp", type = "dispatcher"),
            @Result(name = "tree", type = "json", params = {"root","stationList","excludeNullProperties","true"}),
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","includeProperties","*"})
         })
public class ConDealPriceAction extends SimpleActionSupport {
	@Resource(name="conDealPriceServiceImpl")
	private IConDealPriceService conDealPriceService;
	private ConsigneeDealPrice conDealPrice;
	private String[] tels;
	private String cusName;
	@Override
	protected Object createNewInstance() {
		return new ConsigneeDealPrice();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return conDealPriceService;
	}

	@Override
	public Object getModel() {
		return conDealPrice;
	}

	@Override
	public void setModel(Object obj) {
		conDealPrice=(ConsigneeDealPrice)obj;
	}
	
	/**
	 * @return the tels
	 */
	public String[] getTels() {
		return tels;
	}

	/**
	 * @param tels the tels to set
	 */
	public void setTels(String[] tels) {
		this.tels = tels;
	}
	
	

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String findRate(){
		try {
			setPageConfig();
			conDealPriceService.findConDealPrice(this.getPages(),cusName, tels);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"查询失败,失败原因:" + e.getLocalizedMessage());
			return "reload";
		}return "list";
	}
}
