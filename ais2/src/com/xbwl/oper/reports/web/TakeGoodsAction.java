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
import com.xbwl.oper.stock.service.IOprLoadingbrigadeWeightService;

/**
 * author LiuHao time Jul 25, 2011 5:50:24 PM
 * 提货货量控制层操作类
 */
@Controller
@Action("takeGoodsAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_takeGoodsReport.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class TakeGoodsAction extends SimpleActionSupport {

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
	public String findTakeGoods(){
		String sql="";
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		try {
			sql = this.oprLoadingbrigadeWeightService.findTakeGoods(map);
			//System.out.println(sql);
			this.oprLoadingbrigadeWeightService.getPageBySqlMap(getPages(), sql, map);
			//Page pg =this.oprLoadingbrigadeWeightService.getPageBySqlMap(getPages(), sql, map);
			//System.out.println(pg.getResultMap());
		} catch (Exception e) {
			addError("提货货量汇总失败!", e);
		}
		return LIST;
	}
}
