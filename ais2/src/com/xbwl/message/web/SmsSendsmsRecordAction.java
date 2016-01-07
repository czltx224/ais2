package com.xbwl.message.web;

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
import com.xbwl.entity.SmsSendsmsRecord;
import com.xbwl.message.service.ISmsSendsmsRecordService;

/**
 * 短信发送记录表控制层操作类
 * 
 * @author czl
 */
@Controller
@Action("smsSendsmsRecordAction")
@Scope("prototype")
@Namespace("/message")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/message/opr_smsSendsmsRecord.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		)}

)
public class SmsSendsmsRecordAction extends SimpleActionSupport {

	@Resource(name="smsSendsmsRecordServiceImpl")
	private ISmsSendsmsRecordService smsSendsmsRecordService;
	
	private SmsSendsmsRecord smsSendsmsRecord;
	
	@Override
	protected Object createNewInstance() {
		return new SmsSendsmsRecord();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.smsSendsmsRecordService;
	}

	@Override
	public Object getModel() {
		return this.smsSendsmsRecord;
	}

	@Override
	public void setModel(Object obj) {
		this.smsSendsmsRecord=(SmsSendsmsRecord)obj;
	}
	
}
