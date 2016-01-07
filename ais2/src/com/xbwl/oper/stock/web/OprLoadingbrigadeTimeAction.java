package com.xbwl.oper.stock.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprLoadingbrigadTime;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeTimeService;

/**�Ű����Ʋ������
 * author shuw czl
 * time Sep 20, 2011 8:01:51 PM
 */
@Controller
@Action("oprLoadingbrigadeTimeAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/sys/opr_loadingbrigadTime.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprLoadingbrigadeTimeAction extends SimpleActionSupport{

	@Resource(name = "oprLoadingbrigadeTimeServiceImpl")
	private IOprLoadingbrigadeTimeService oprLoadingbrigadeTimeService;

	private OprLoadingbrigadTime oprLoadingbrigadeTime;
	private String startDateString;
	private String endDateString;

	public String save() throws Exception {
		   try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	        		DateFormat  simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        		Date startDate=simpleDateFormat.parse("1970-01-01 ".concat(startDateString).concat(":00"));
	        		Date endDate=simpleDateFormat.parse("1970-01-01 ".concat(endDateString).concat(":59"));
	        		oprLoadingbrigadeTime.setStartDate(startDate);
	        		oprLoadingbrigadeTime.setEndDate(endDate);
	        		oprLoadingbrigadeTimeService.save(oprLoadingbrigadeTime);
	        		getValidateInfo().setMsg("���ݱ���ɹ���");
	        		addMessage("���ݱ���ɹ���");
	        	}else{
	        		getValidateInfo().setSuccess(false);
	        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	getValidateInfo().setSuccess(false);
	        	getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
	            addError("���ݱ���ʧ�ܣ�", e);
	        }
		return RELOAD;
	}

	@Override
	protected Object createNewInstance() {
		return new OprLoadingbrigadTime() ;
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprLoadingbrigadeTimeService;
	}

	@Override
	public Object getModel() {
		return oprLoadingbrigadeTime;
	}

	@Override
	public void setModel(Object obj) {
		oprLoadingbrigadeTime=(OprLoadingbrigadTime)obj;
	}

	
	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}
}
