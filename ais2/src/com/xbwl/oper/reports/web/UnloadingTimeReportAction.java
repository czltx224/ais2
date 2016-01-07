package com.xbwl.oper.reports.web;

import java.util.Date;
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

/**卸货时效报表控制层操作类
 * author CaoZhili
 * time Sep 30, 2011 2:25:05 PM
 */
@Controller
@Action("unloadingTimeReport")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_unloadingTimeReport.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class UnloadingTimeReportAction extends SimpleActionSupport {
	
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

	public String findUnloadingTimeList(){
		
		Date dt=new Date();
		String sql="";
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
	
		try {
			sql=this.operationReportService.getUnloadingTimeListService(map);
			this.operationReportService.getPageBySqlMap(this.getPages(), sql, map);
			
		} catch (Exception e) {
			addError("卸货时效查询失败!", e);
		}
		return this.LIST;
	}

public String findUnloadingTimeDetailList(){
		String sql="";
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		
		try {
			sql=this.operationReportService.getUnloadingTimeDetailListService(map);
			this.operationReportService.getPageBySqlMap(this.getPages(), sql, map);
			
		} catch (Exception e) {
			addError("卸货时效明细查询失败!", e);
		}
		return this.LIST;
	}
}
