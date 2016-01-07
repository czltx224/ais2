package com.xbwl.oper.stock.web;

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
import com.xbwl.entity.OprStatus;
import com.xbwl.oper.stock.service.IOprStatusService;

/**
 * author CaoZhili time Jul 6, 2011 2:58:19 PM
 * 
 * ×´Ì¬¸¨Öú±í¿ØÖÆ²ã²Ù×÷Àà
 */
@Controller
@Action("oprStatusAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_overmemoDetail.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprStatusAction extends SimpleActionSupport {

	@Resource(name = "oprStatusServiceImpl")
	private IOprStatusService oprStatusService;

	private OprStatus oprStatus;

	@Override
	protected Object createNewInstance() {

		return new OprStatus();
	}

	@Override
	public Map getContextMap() {

		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprStatusService;
	}

	@Override
	public Object getModel() {

		return this.oprStatus;
	}

	@Override
	public void setModel(Object obj) {

		this.oprStatus = (OprStatus) obj;

	}

}
