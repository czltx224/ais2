package com.xbwl.report.print.web;

import java.util.Map;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.report.print.service.IBillPrintService;

/**
 * @author Administrator
 * @createTime 9:12:11 AM
 * @updateName Administrator
 * @updateTime 9:12:11 AM
 * 
 */
@Controller
@Action("billLadingPrintAction")
@Scope("prototype")
@Namespace("/print")
@Results( {
		@Result(name = "inputQuery", location = "/WEB-INF/xbwl/inquiry/comprehensive_inquiry.jsp", type = "dispatcher"),
		@Result(name = "input", location = "/WEB-INF/xbwl/fax/faxIn.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class BillPrintAction extends SimpleActionSupport {
	
	@Resource(name="billPrintServiceImpl")
	private IBillPrintService billPrintServiceImpl;
	
	private String modeName;
	
	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	@Override
	protected Object createNewInstance() {
		return null;
	}

	@Override
	public Map getContextMap() {
		return null;
	}
	
	public String print(){
		try{
		User user= WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		
		Map<String, String> filterParamMap = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "print_");
		
        Struts2Utils.renderXml( billPrintServiceImpl.getBillLadingList(modeName, filterParamMap,user));
		}catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
	
	

	@Override
	protected IBaseService getManager() {
		return null;
	}

	@Override
	public Object getModel() {
		return null;
	}

	@Override
	public void setModel(Object obj) {

	}

}
