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
import com.xbwl.entity.OprEnterPortReport;
import com.xbwl.oper.reports.service.IOprEnterPortReportService;

/**
 * author CaoZhili
 * time Nov 24, 2011 2:35:38 PM
 * 干线车报表控制层操作类
 */
@Controller
@Action("oprArteryCarReportAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/query/opr_arteryCarReport.jsp", type = "dispatcher"),
		@Result(name = "countReport", location = "/WEB-INF/xbwl/reports/count/opr_arteryCarCountReport.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class OprArteryCarReportAction extends SimpleActionSupport {

	@Resource(name="oprEnterPortReportServiceImpl")
	private IOprEnterPortReportService oprEnterPortReportService;
	
	private OprEnterPortReport oprEnterPortReport;
	
	@Override
	protected Object createNewInstance() {
		return new OprEnterPortReport();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprEnterPortReportService;
	}

	@Override
	public Object getModel() {
		return this.oprEnterPortReport;
	}

	@Override
	public void setModel(Object obj) {
		this.oprEnterPortReport=(OprEnterPortReport)obj;
	}

	/**干线车报表查询
	 * @return
	 */
	public String findList(){
		
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		String sql ="";
		try{
			sql = this.oprEnterPortReportService.findArteryCarListService(map);
			this.oprEnterPortReportService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("进港时效报表查询失败！", e);
		}
		return this.LIST;
	}
	
	/**干线车时效统计报表
	 * @return
	 */
	public String toCountReport(){
		
		return "countReport";
	}
	public String findCountReport(){
		
		setPageConfig();
		
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		String sql ="";
		try{
			sql = this.oprEnterPortReportService.findArteryCarCountService(map);
			this.oprEnterPortReportService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("进港时效报表查询失败！", e);
		}
		return this.LIST;
	}
}
