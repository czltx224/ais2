package com.xbwl.oper.reports.web;

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
import com.xbwl.entity.OprUnloadingStandard;
import com.xbwl.oper.reports.service.IOprUnloadingStandardService;

/**卸货时效标准控制层操作类
 * @author Caozhili
 *
 */
@Controller
@Action("oprUnloadingStandardAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_unloadingStandard.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class OprUnloadingStandardAction extends SimpleActionSupport{

	@Resource(name="oprUnloadingStandardServiceImpl")
	private IOprUnloadingStandardService oprUnloadingStandardService;
	
	private OprUnloadingStandard oprUnloadingStandard;
	
	@Override
	protected Object createNewInstance() {
		return new OprUnloadingStandard();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprUnloadingStandardService;
	}

	@Override
	public Object getModel() {
		return this.oprUnloadingStandard;
	}

	@Override
	public void setModel(Object obj) {
		this.oprUnloadingStandard=(OprUnloadingStandard)obj;
	}

}
