package com.xbwl.finance.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.BasSpecialTrainRate;
import com.xbwl.finance.Service.IBasSpecialTrainRateService;

/**
 * @author CaoZhili
 * time Aug 2, 2011 4:09:34 PM
 * 
 * ר��Э��ۿ��Ʋ������
 */
@Controller
@Action("basSpecialTrainRateAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/bas_specialTrainRate.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class BasSpecialTrainRateAction extends SimpleActionSupport {

	private BasSpecialTrainRate basSpecialTrainRate;
	
	@Resource(name="basSpecialTrainRateServiceImpl")
	private IBasSpecialTrainRateService basSpecialTrainRateService;
	
	@Override
	protected Object createNewInstance() {

		return new BasSpecialTrainRate();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.basSpecialTrainRateService;
	}

	@Override
	public Object getModel() {

		return this.basSpecialTrainRate;
	}

	@Override
	public void setModel(Object obj) {
		
		this.basSpecialTrainRate=(BasSpecialTrainRate)obj;
	}
	
	/**
	 * αɾ������
	 * @return
	 */
	public String deleteStatus(){
		
		String strids=ServletActionContext.getRequest().getParameter("ids");
		try{
			this.basSpecialTrainRateService.updateStatusService(strids.split(","), new Long(0));
		}catch(Exception e){
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("ɾ��ʧ�ܣ�"+e.getLocalizedMessage());
		}
		return "reload";
	}
	
	/**
	 * ��˷���
	 * @return
	 */
	public String auditStatus(){
		
		String strids=ServletActionContext.getRequest().getParameter("ids");
		try{
			this.basSpecialTrainRateService.updateStatusService(strids.split(","), new Long(2));
		}catch(Exception e){
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("���ʧ�ܣ�"+e.getLocalizedMessage());
		}
		return "reload";
	}
}
