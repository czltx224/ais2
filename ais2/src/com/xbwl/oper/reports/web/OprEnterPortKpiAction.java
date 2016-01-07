package com.xbwl.oper.reports.web;

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
import com.xbwl.entity.OprEnterPortKpi;
import com.xbwl.oper.reports.service.IOprEnterPortKpiService;

/**
 * author CaoZhili
 * time Nov 10, 2011 11:28:21 AM
 */
@Controller
@Action("oprEnterPortKpiAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_enterPortKpi.jsp", type = "dispatcher"),
		@Result(name = "kpiReport", location = "/WEB-INF/xbwl/reports/count/opr_kpiReport.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class OprEnterPortKpiAction extends SimpleActionSupport{

	@Resource(name="oprEnterPortKpiServiceImpl")
	private IOprEnterPortKpiService enterPortKpiService;
	
	private OprEnterPortKpi enterPortKpi;
	
	@Override
	protected Object createNewInstance() {
		return new OprEnterPortKpi();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.enterPortKpiService;
	}

	@Override
	public Object getModel() {
		return this.enterPortKpi;
	}

	@Override
	public void setModel(Object obj) {
		this.enterPortKpi=(OprEnterPortKpi)obj;
	}
	
	/**到KPI报表界面
	 * @return
	 */
	public String toKpiReport(){
		return "kpiReport";
	}
	
	public String findKpiReport(){
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		String countRange = map.get("countRange");
		if(null==countRange){
			map.put("countRange", "日");
		}
		try{
			String sql = this.enterPortKpiService.findKpiReportService(map);
		    Page pgPage = this.enterPortKpiService.getPageBySqlMap(this.getPages(), sql, map);
		    
		    //对PAGE中的值进行修改
		    
		}catch (Exception e) {
			addError("报表统计失败！", e);
		}
		return this.LIST;
	}
	
}
