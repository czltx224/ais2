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
import com.xbwl.oper.reports.service.IInuploadGoodsService;

/**
 * author CaoZhili time Jul 25, 2011 5:50:24 PM
 * 分拨货量和装卸货量查询控制层
 */
@Controller
@Action("inuploadGoods")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "separateDialReport", location = "/WEB-INF/xbwl/reports/opr_separateDialReport.jsp", type = "dispatcher"),
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_inuploadGoods.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class InuploadGoodsAction extends SimpleActionSupport {

	@Resource(name = "inuploadGoodsServiceImpl")
	private IInuploadGoodsService inuploadGoodsService;
	
	private OprLoadingbrigadeWeight oprLoadingbrigadeWeight;
	private String dateTime;
	private Long loadingbrigadeId;
	private Long loadingType;
	private String timestage;
	private Long cusId;

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

		return inuploadGoodsService;
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
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}
	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	/**
	 * @return the loadingbrigadeId
	 */
	public Long getLoadingbrigadeId() {
		return loadingbrigadeId;
	}
	/**
	 * @param loadingbrigadeId the loadingbrigadeId to set
	 */
	public void setLoadingbrigadeId(Long loadingbrigadeId) {
		this.loadingbrigadeId = loadingbrigadeId;
	}
	/**
	 * @return the loadingType
	 */
	public Long getLoadingType() {
		return loadingType;
	}
	/**
	 * @param loadingType the loadingType to set
	 */
	public void setLoadingType(Long loadingType) {
		this.loadingType = loadingType;
	}
	
	
	/**
	 * @return the timestage
	 */
	public String getTimestage() {
		return timestage;
	}
	/**
	 * @param timestage the timestage to set
	 */
	public void setTimestage(String timestage) {
		this.timestage = timestage;
	}
	
	/**
	 * @return the cusId
	 */
	public Long getCusId() {
		return cusId;
	}
	/**
	 * @param cusId the cusId to set
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	
	/**分拨货量查询
	 * @return
	 */
	public String getSeparateDialReport(){
		String sql="";
		setPageConfig();
		try {
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.inuploadGoodsService.getSeparateDialReportSQLService(map);
			this.inuploadGoodsService.getPageBySqlMap(this.getPages(), sql, map);
		} catch (Exception e) {
			addError(e.getLocalizedMessage(), e);
		}
		return this.LIST;
	}
	
	public String separateDialReport(){
		
		return "separateDialReport";
	}
	
	/**装卸货量统计报表查询
	 * @return
	 */
	public String findInuploadGoods(){
		setPageConfig();
		try {
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			String  sql = inuploadGoodsService.findInuploadGoods(map);
			
			this.inuploadGoodsService.getPageBySqlMap(this.getPages(), sql, map);
		} catch (Exception e) {
			getPages().setSuccess(false);
			addError("装卸货量汇总失败!", e);
		}
		return LIST;
	}
}
