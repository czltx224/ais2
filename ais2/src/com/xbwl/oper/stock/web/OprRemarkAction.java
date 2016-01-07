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
import com.xbwl.entity.OprRemark;
import com.xbwl.oper.stock.service.IOprRemarkService;

/**
 * author CaoZhili time Jul 19, 2011 4:15:46 PM
 * 
 * ��ע��¼���Ʋ������
 */

@Controller
@Action("oprRemarkAction")
@Scope("prototype")
@Namespace("/stock")
@Results({
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_remark.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) })
public class OprRemarkAction extends SimpleActionSupport {

	@Resource(name = "oprRemarkServiceImpl")
	private IOprRemarkService oprRemarkService;
	
	private OprRemark oprRemark;
	
	private String remark;

	public String  saveRemarks() {
		 try {
        		oprRemarkService.saveRemarks(getPksByIds(),remark);
        		getValidateInfo().setMsg("���ݱ���ɹ���");
        		getValidateInfo().setSuccess(true);
	        	
	        } catch (Exception e) {
	            addError("���ݱ���ʧ�ܣ�", e);
	        }
		return RELOAD;
	}
	
	@Override
	protected Object createNewInstance() {

		return new OprRemark();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprRemarkService;
	}

	@Override
	public Object getModel() {

		return this.oprRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public void setModel(Object obj) {

		this.oprRemark = (OprRemark) obj;

	}

	@Override
	public String save() throws Exception {
		try{
			this.oprRemarkService.save(this.oprRemark);
			getValidateInfo().setMsg("���ݱ���ɹ���");
			addMessage("���ݱ���ɹ���");
		}catch (Exception e) {
			addError("���ݱ���ʧ�ܣ�", e);
		}
		
		return this.RELOAD;
	}
	
	
}
