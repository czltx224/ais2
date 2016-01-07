package com.xbwl.oper.reports.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

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
 * 中转统计控制层操作类
 * @author czl
 * @date 2012-05-18
 */
@Controller
@Action("transitCountAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/transit/opr_transitCount.jsp", type = "dispatcher"),
		@Result(name = "toTransitDetail", location = "/WEB-INF/xbwl/reports/transit/opr_transiDetailFind.jsp", type = "dispatcher"),
		@Result(name = "toEdiAgingQuery", location = "/WEB-INF/xbwl/reports/transit/opr_ediAgingQuery.jsp", type = "dispatcher"),
		@Result(name = "toEdiAgingCount", location = "/WEB-INF/xbwl/reports/transit/opr_ediAgingCount.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) 
})
public class TransitCountAction extends SimpleActionSupport {

	private Long gowhereId;
	private String startDate;
	private String endDate;
	private Long curBussDepart;
	private String countCheckItems;
	
	@Resource(name = "operationReportServiceImpl")
	private IOperationReportService operationReportService;

	private OprLoadingbrigadeWeight oprLoadingbrigadeWeight;

	public String getCountCheckItems() {
		return countCheckItems;
	}

	public void setCountCheckItems(String countCheckItems) {
		this.countCheckItems = countCheckItems;
	}

	public Long getGowhereId() {
		return gowhereId;
	}

	public void setGowhereId(Long gowhereId) {
		this.gowhereId = gowhereId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getCurBussDepart() {
		return curBussDepart;
	}

	public void setCurBussDepart(Long curBussDepart) {
		this.curBussDepart = curBussDepart;
	}

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
		this.oprLoadingbrigadeWeight = (OprLoadingbrigadeWeight) obj;
	}

	/**
	 * 中转汇总统计控制层方法
	 * @return
	 */
	public String findTransitCount(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "中转");
			map.put("faxStatus", "1");
			map.put("fiStatus", "1");
			
			String sql = this.operationReportService.findTransitCountService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("中转汇总统计失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * 中转汇总统计合计控制层方法
	 * @return
	 */
	public String findTransitCountTotal(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "中转");
			map.put("faxStatus", "1");
			map.put("fiStatus", "1");
			
			String sql = this.operationReportService.findTransitCountTotalService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
			
			List<Map> list =getPages().getResultMap();
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("GOWHERE","<b>合计</b>：");
				jsonObject.element("TOTALTICKET","票"+(totalMap.get("TOTALTICKET")==null?"0":totalMap.get("TOTALTICKET")));
				jsonObject.element("TOTALPIECE","件"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")));
				jsonObject.element("TOTALWEIGHT","重(KG)"+(totalMap.get("TOTALWEIGHT")==null?"0":totalMap.get("TOTALWEIGHT")));
				jsonObject.element("DAOFU","到付(元)"+(totalMap.get("DAOFU")==null?"0":totalMap.get("DAOFU")));
				jsonObject.element("YUFU","预付(元)"+(totalMap.get("YUFU")==null?"0":totalMap.get("YUFU")));
				jsonObject.element("PAYMENT_COLLECTION","代(元)"+(totalMap.get("PAYMENT_COLLECTION")==null?"0":totalMap.get("PAYMENT_COLLECTION")));
				jsonObject.element("TRA_FEE","成本(元)"+(totalMap.get("TRA_FEE")==null?"0":totalMap.get("TRA_FEE")));
				jsonObject.element("NOTCONFIRMNUM","返单(票)"+(totalMap.get("NOTCONFIRMNUM")==null?"0":totalMap.get("NOTCONFIRMNUM")));
				jsonObject.element("NOTSETTLEAMOUNT","未收(元)"+(totalMap.get("NOTSETTLEAMOUNT")==null?"0":totalMap.get("NOTSETTLEAMOUNT")));
				jsonObject.element("NOTPAYAMOUNT","未付(元)"+(totalMap.get("NOTPAYAMOUNT")==null?"0":totalMap.get("NOTPAYAMOUNT")));
				jsonObject.element("PREKGCOST","(元/KG)"+(totalMap.get("PREKGCOST")==null?"0":totalMap.get("PREKGCOST")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch (Exception e) {
			addError("中转汇总合计统计失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * 中转明细查询控制层方法
	 * @return
	 */
	public String findTransitDetail(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("overmemoType", "中转");
			map.put("distributionMode", "中转");
			map.put("faxStatus", "1");
			map.put("fiStatus", "1");
			
			String sql = this.operationReportService.findTransitDetailFindService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("中转明细查询失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * 中转明细查询合计控制层方法
	 * @return
	 */
	public String findTransitDetailTotal(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("overmemoType", "中转");
			map.put("distributionMode", "中转");
			map.put("faxStatus", "1");
			map.put("fiStatus", "1");
			
			String sql = this.operationReportService.findTransitDetailTotalService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
			
			List<Map> list =getPages().getResultMap();
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("D_NO","<b>合计</b>：");
				jsonObject.element("CP_NAME","票"+(totalMap.get("TOTALTICKET")==null?"0":totalMap.get("TOTALTICKET")));
				jsonObject.element("PIECE","件"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")));
				jsonObject.element("CUS_WEIGHT","重(KG)"+(totalMap.get("TOTALWEIGHT")==null?"0":totalMap.get("TOTALWEIGHT")));
				jsonObject.element("DAOFU","到付(元)"+(totalMap.get("DAOFU")==null?"0":totalMap.get("DAOFU")));
				jsonObject.element("YUFU","预付(元)"+(totalMap.get("YUFU")==null?"0":totalMap.get("YUFU")));
				jsonObject.element("PAYMENT_COLLECTION","代(元)"+(totalMap.get("PAYMENT_COLLECTION")==null?"0":totalMap.get("PAYMENT_COLLECTION")));
				jsonObject.element("TRA_FEE","成本(元)"+(totalMap.get("TRA_FEE")==null?"0":totalMap.get("TRA_FEE")));
				jsonObject.element("NOTCONFIRMNUM","返单(票)"+(totalMap.get("NOTCONFIRMNUM")==null?"0":totalMap.get("NOTCONFIRMNUM")));
				jsonObject.element("NOTSETTLEAMOUNT","未收(元)"+(totalMap.get("NOTSETTLEAMOUNT")==null?"0":totalMap.get("NOTSETTLEAMOUNT")));
				jsonObject.element("NOTPAYAMOUNT","未付(元)"+(totalMap.get("NOTPAYAMOUNT")==null?"0":totalMap.get("NOTPAYAMOUNT")));
				jsonObject.element("PREKGCOST","(元/KG)"+(totalMap.get("PREKGCOST")==null?"0":totalMap.get("PREKGCOST")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch (Exception e) {
			addError("中转明细查询合计失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * 跳转到中转明细查询页面
	 * @return
	 */
	public String toTransitDetail(){
		
		return "toTransitDetail";
	}
	
	/**
	 * 跳转到EDI货物时效查询页面
	 * @return
	 */
	public String toEdiAgingQuery(){
		
		return "toEdiAgingQuery";
	}
	
	/**
	 * 跳转到EDI货物时效统计页面
	 * @return
	 */
	public String toEdiAgingCount(){
		
		return "toEdiAgingCount";
	}
	
	/**
	 * EDI货物时效查询方法
	 * @return
	 */
	public String findEdiAgingQuery(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "中转");
			map.put("faxStatus", "1");
			map.put("okFlag", "1");
			
			String sql = this.operationReportService.findEdiAgingQueryService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("EDI货物时效查询失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * EDI货物时效汇总统计查询方法
	 * @return
	 */
	public String findEdiAgingCount(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "中转");
			map.put("faxStatus", "1");
			
			String sql = this.operationReportService.findEdiAgingCountService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("EDI货物时效汇总统计查询失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * EDI货物时效查询合计方法
	 * @return
	 */
	public String findEdiAgingQueryTotal(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "中转");
			map.put("faxStatus", "1");
			map.put("okFlag", "1");
			
			String sql = this.operationReportService.findEdiAgingQueryTotalService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
			
			List<Map> list =getPages().getResultMap();
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("D_NO","<b>合计</b>：");
				jsonObject.element("CP_NAME",(totalMap.get("TOTALTICKET")==null?"0":totalMap.get("TOTALTICKET"))+"票");
				jsonObject.element("PIECE","件"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")));
				jsonObject.element("CUS_WEIGHT","重(KG)"+(totalMap.get("TOTALWEIGHT")==null?"0":totalMap.get("TOTALWEIGHT")));
				jsonObject.element("TRANSITSTANDARD","点到(达标)"+(totalMap.get("TRANSITSTANDARDNUM")==null?"0":totalMap.get("TRANSITSTANDARDNUM")));
				jsonObject.element("CAROUTSTANDARD","出库(达标)"+(totalMap.get("CAROUTSTANDARDNUM")==null?"0":totalMap.get("CAROUTSTANDARDNUM")));
				jsonObject.element("SIGNSTANDARD","签收(达标)"+(totalMap.get("SIGNSTANDARDNUM")==null?"0":totalMap.get("SIGNSTANDARDNUM")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch (Exception e) {
			addError("EDI货物时效查询合计失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * EDI货物时效汇总统计合计方法
	 * @return
	 */
	public String findEdiAgingCountTotal(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "中转");
			map.put("faxStatus", "1");
			
			String sql = this.operationReportService.findEdiAgingCountTotalService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
			
			List<Map> list =getPages().getResultMap();
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("IN_DEPART","<b>合计</b>：");
				jsonObject.element("TOTALTICKET",(totalMap.get("TOTALTICKET")==null?"0":totalMap.get("TOTALTICKET"))+"票");
				jsonObject.element("TOTALPIECE","件"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")));
				jsonObject.element("TOTALWEIGHT","重(KG)"+(totalMap.get("TOTALWEIGHT")==null?"0":totalMap.get("TOTALWEIGHT")));
				jsonObject.element("REACHNUM","点到(票)"+(totalMap.get("REACHNUM")==null?"0":totalMap.get("REACHNUM")));
				jsonObject.element("OUTNUM","出库(票)"+(totalMap.get("OUTNUM")==null?"0":totalMap.get("OUTNUM")));
				jsonObject.element("SIGNNUM","签收(票)"+(totalMap.get("SIGNNUM")==null?"0":totalMap.get("SIGNNUM")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch (Exception e) {
			addError("EDI货物时效汇总统计合计方法失败！", e);
		}
		return this.LIST;
	}
}
