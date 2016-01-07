package com.xbwl.sys.web;

import java.util.List;
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
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.BasFlight;
import com.xbwl.entity.ConsigneeInfo;
import com.xbwl.sys.service.IBasAreaService;
import com.xbwl.sys.service.IBasFlightService;
import com.xbwl.sys.service.IConInfoService;

@Controller
@Action("conInfoAction")
@Scope("prototype")
@Namespace("/sys")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class ConInfoAction extends SimpleActionSupport {

	@Resource(name = "conInfoServiceImpl")
	private IConInfoService conInfoService;
	private ConsigneeInfo consigneeInfo;
	private String tel;
	

	@Override
	protected Object createNewInstance() {
		return new ConsigneeInfo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return conInfoService;
	}

	@Override
	public Object getModel() {
		return consigneeInfo;
	}

	@Override
	public void setModel(Object obj) {
		consigneeInfo = (ConsigneeInfo) obj;
		
	}
	
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	public List<ConsigneeInfo> findConInfo(){
		List<ConsigneeInfo> list;
		try {
			list = conInfoService.findConsigneeInfoByTel(tel);
			Struts2Utils.renderJson(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
