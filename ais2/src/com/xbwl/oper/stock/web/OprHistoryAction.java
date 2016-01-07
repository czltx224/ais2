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
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprHistory;
import com.xbwl.oper.stock.service.IOprHistoryService;

/**
 * author CaoZhili time Jul 21, 2011 10:01:53 AM
 * 
 * 运作历史记录控制层操作类
 */
@Controller
@Action("oprHistoryAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/rbac/sys_historyLog.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprHistoryAction extends SimpleActionSupport {

	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;

	private OprHistory oprHistory;

	@Override
	protected Object createNewInstance() {

		return new OprHistory();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprHistoryService;
	}

	@Override
	public Object getModel() {

		return this.oprHistory;
	}

	@Override
	public void setModel(Object obj) {

		this.oprHistory = (OprHistory) obj;
	}

	public String findHistoryByDno(){
		setPageConfig();
		getPages().setLimit(100);
		Long dno ;
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		try{
			String strdno=map.get("EQL_dno");
			if(null==strdno || "".equals(strdno.trim())){
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg("配送单号不允许为空！");
			}
			String sql = this.oprHistoryService.findHistoryByDno(Long.valueOf(strdno));
			this.oprHistoryService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("根据配送单号查询日志失败！", e);
		}
		return this.LIST;
	}
}
