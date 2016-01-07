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
import com.xbwl.entity.OprException;
import com.xbwl.oper.exception.service.IOprExceptionService;

/**
 * 客服异常信息控制层
 * author LIUH
 * time Aug 22, 2011 4:35:40 PM  
 */
@Controller
@Action("cusExceptionAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_exception_mange.jsp", type = "dispatcher"), 
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class CusExceptionAction extends SimpleActionSupport{

	private OprException oprException;
	
	@Resource(name="oprExceptionServiceImpl")
	private IOprExceptionService oprExceptionService;
	
	@Override
	protected Object createNewInstance() {
		return new OprException();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprExceptionService;
	}

	@Override
	public Object getModel() {
		return  oprException;
	}

	@Override
	public void setModel(Object obj) {
		oprException=(OprException)obj;
	}

}
