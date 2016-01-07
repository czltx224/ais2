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
import com.xbwl.entity.BasValueAddFee;
import com.xbwl.sys.service.IBasValueAddFeeService;

/**
 * author CaoZhili time Jun 28, 2011 4:42:14 PM
 */

@Controller
@Action("basValueAddFeeAction")
@Scope("prototype")
@Namespace("/basvalueaddfee")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/sys/sys_basValueAddFee.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class BasValueAddFeeAction extends SimpleActionSupport {

	@Resource(name = "basValueAddFeeServiceImpl")
	private IBasValueAddFeeService basValueAddFeeService;

	private BasValueAddFee basValueAddFee;

	@Override
	protected Object createNewInstance() {

		return new BasValueAddFee();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.basValueAddFeeService;
	}

	@Override
	public Object getModel() {

		return this.basValueAddFee;
	}

	@Override
	public void setModel(Object obj) {

		this.basValueAddFee = (BasValueAddFee) obj;

	}

}
