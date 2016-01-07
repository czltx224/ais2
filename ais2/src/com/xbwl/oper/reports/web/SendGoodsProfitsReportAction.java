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
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.reports.service.IOperationReportService;

/**
 * author CaoZhili
 * time Sep 30, 2011 2:10:54 PM
 * �ͻ�ӯ��������Ʋ������
 */
@Controller
@Action("sendGoodsProfitsReport")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_sendGoodsProfitsReport.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class SendGoodsProfitsReportAction extends SimpleActionSupport {

	@Resource(name = "operationReportServiceImpl")
	private IOperationReportService operationReportService;
	
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
		return this.operationReportService;
	}

	@Override
	public Object getModel() {
		return this.oprLoadingbrigadeWeight;
	}

	@Override
	public void setModel(Object obj) {
		this.oprLoadingbrigadeWeight=(OprLoadingbrigadeWeight)obj;
	}
	
	/**�ͻ�ӯ������ͳ�Ʒ���
	 * @return
	 */
	public String findSendGoodsProfits(){
		
		String sql = "";
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		map.put("overmemoType", "�����ͻ�");
		// map.put("f.takeMode", "�����ͻ�");
		map.put("feeName", "���˷�");
		try {
			sql = this.operationReportService.findSendGoodsProfitsService(map);
			//System.out.println(sql);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
			//Page pg =this.oprLoadingbrigadeWeightService.getPageBySqlMap(getPages(), sql, map);
			//System.out.println(pg.getResultMap());
		} catch (Exception e) {
			addError("�ͻ�ӯ������ͳ��ʧ��!", e);
		}
		return this.LIST;
		
	}

}
