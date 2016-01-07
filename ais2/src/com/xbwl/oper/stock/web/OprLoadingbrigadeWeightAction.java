package com.xbwl.oper.stock.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeWeightService;

/**
 * @author CaoZhili
 * @time May 16, 2012 14:48
 * 
 * װж�ֲ������ͳ�ƹ�����Ʋ������
 */
@Controller
@Action("oprLoadingbrigadeWeightAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_loadingbrigadeWeight.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" })
})
public class OprLoadingbrigadeWeightAction extends SimpleActionSupport {

	@Resource(name = "oprLoadingbrigadeWeightServiceImpl")
	private IOprLoadingbrigadeWeightService oprLoadingbrigadeWeightService;

	private OprLoadingbrigadeWeight oprLoadingbrigadeWeight;

	@Override
	protected Object createNewInstance() {
		return new OprLoadingbrigadeWeight();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprLoadingbrigadeWeightService;
	}

	@Override
	public Object getModel() {
		return this.oprLoadingbrigadeWeight;
	}

	@Override
	public void setModel(Object obj) {
		this.oprLoadingbrigadeWeight = (OprLoadingbrigadeWeight) obj;
	}
	
	/**
	 * ��дװж�ֲ��������ѯ����
	 * @return
	 */
	public String findSqlList(){
		
		setPageConfig();
        Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
        
		try{
			String sql = this.oprLoadingbrigadeWeightService.findSqlListService(map);
			
			this.oprLoadingbrigadeWeightService.getPageBySqlMap(this.getPages(), sql,map);
			super.getValidateInfo().setSuccess(true);
		}catch(Exception e){
			addError("��ѯװж�ֲ��������ʧ�ܣ�",e);
		}
		
		return this.LIST;
	}
}
