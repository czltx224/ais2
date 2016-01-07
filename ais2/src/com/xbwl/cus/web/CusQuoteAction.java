package com.xbwl.cus.web;

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
import com.xbwl.cus.service.ICusComplaintService;
import com.xbwl.entity.CusComplaint;

/**
 * 客户报价管理控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusQuoteAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_quote.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusQuoteAction extends SimpleActionSupport {
	@Resource(name="cusComplaintServiceImpl")
	private ICusComplaintService cusComplaintService;
	private CusComplaint cusComplaint;
	@Override
	protected Object createNewInstance() {
		return new CusComplaint();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusComplaintService;
	}
	@Override
	public Object getModel() {
		return cusComplaint;
	}
	@Override
	public void setModel(Object obj) {
		this.cusComplaint=(CusComplaint)obj;
	}
}
