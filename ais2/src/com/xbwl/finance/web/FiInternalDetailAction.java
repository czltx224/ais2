package com.xbwl.finance.web;

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
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.finance.Service.IFiInternalDetailService;

/**
 * author CaoZhili time Oct 20, 2011 1:54:23 PM
 * 内部结算明细控制层操作类
 */
@Controller
@Action("fiInternalDetailAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_fiInternalDetail.jsp", type = "dispatcher"),
		@Result(name = "internalSettlementReport", location = "/WEB-INF/xbwl/fi/fi_fiInternalSettlementReport.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class FiInternalDetailAction extends SimpleActionSupport {

	@Resource(name = "fiInternalDetailServiceImpl")
	private IFiInternalDetailService fiInternalDetailService;

	private FiInternalDetail fiInternalDetail;
	
	private String departId1;

	@Override
	protected Object createNewInstance() {
		return new FiInternalDetail();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiInternalDetailService;
	}

	@Override
	public Object getModel() {
		return this.fiInternalDetail;
	}

	@Override
	public void setModel(Object obj) {
		this.fiInternalDetail = (FiInternalDetail) obj;
	}

	/**内部结算报表跳转ACTION
	 * @return 内部结算JSP
	 */
	public String internalSettlementReport(){
		
		return "internalSettlementReport";
	}
	
	/**内部结算报表查询ACTION
	 * @return
	 */
	public String reportList(){
		String sql=null;
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		
		try{
			sql  = this.fiInternalDetailService.reportListService(map);
			this.fiInternalDetailService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("内部结算报表查询失败！", e);
		}
		return LIST;
	}

	public String getDepartId1() {
		return departId1;
	}

	public void setDepartId1(String departId1) {
		this.departId1 = departId1;
	}
	
	
}
