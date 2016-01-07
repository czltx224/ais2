package com.xbwl.cus.web;

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
import com.xbwl.cus.service.ICusRateAnalyseService;
import com.xbwl.cus.vo.ReportSerarchVo;

/**
 * 价格分析
 * author LiuHao
 *  time Jul 6, 2011 2:40:02 PM
 */
@Controller
@Action("rateAnalyseAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/cus/report_rateanalyse.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class RateAnalyseAction extends SimpleActionSupport {

	@Resource(name = "cusRateAnalyseServiceImpl")
	private ICusRateAnalyseService cusRateAnalyseService;
	private ReportSerarchVo reportSerarchVo;


	@Override
	protected Object createNewInstance() {
		return new ReportSerarchVo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.cusRateAnalyseService;
	}

	@Override
	public Object getModel() {
		return this.reportSerarchVo;
	}
	@Override
	public void setModel(Object obj) {
		this.reportSerarchVo = (ReportSerarchVo) obj;
	}
	public void prepareRateAnalyse(){
		try {
			this.prepareSave();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 价格 折扣分析
	 * @return
	 */
	public String rateAnalyse(){
		this.setPageConfig();
		try {
			cusRateAnalyseService.rateAnalyse(this.getPages(),reportSerarchVo);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}
