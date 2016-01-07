package com.xbwl.oper.reports.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.reports.service.IOperationReportService;
import com.xbwl.oper.reports.vo.SendGoodsDetailCountVo;
/**
 * @author Administrator
 *送货货量报表控制层操作类
 */
@Controller
@Action("sendGoodsReport")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_sendGoodsReport.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class SendGoodsReportAction extends SimpleActionSupport {

	
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


	/**查询送货货量
	 * @return
	 */
	public String findSendGoods(){
		try {
			String sql="";
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("overmemoType","市内送货");
		
			sql=this.operationReportService.getSendGoodsService(map);
			this.operationReportService.getPageBySqlMap(this.getPages(), sql, map);
			
		} catch (Exception e) {
			addError("查询送货货量异常!", e);
		}
		return "list";
	}
	
	/**查询送货货量明细
	 * @return
	 */
	public String findSendGoodsDetail(){
		try {
			String sql="";
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("overmemoType", "市内送货");
		
			sql=this.operationReportService.getSendGoodsDetailService(map);
			this.operationReportService.getPageBySqlMap(this.getPages(), sql, map);
			
		} catch (Exception e) {
			addError("查询送货货量明细失败!", e);
		}
		return "list";
	}
	
	/**送货货量明细汇总
	 * @return
	 */
	public String findSendGoodsDetailCount(){
		
		try{
			setPageConfig();
			User user = WebRalasafe.getCurrentUser(ServletActionContext
					.getRequest());
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("overmemoType", "市内送货");
			String sql = this.operationReportService.findSendGoodsDetailCount(map);
			
			Page pg = this.operationReportService.getPageBySqlMap(getPages(), sql, map);
			
			List <Map>list =pg.getResultMap();
			for(Map  totalMap : list ){
				SendGoodsDetailCountVo vo = new SendGoodsDetailCountVo();
				vo.setAmpiece(totalMap.get("AMPIECE")==null?"0":totalMap.get("AMPIECE").toString());
				vo.setPmpiece((totalMap.get("PMPIECE")==null?"0":totalMap.get("PMPIECE")+""));
				vo.setAmcountpiece(totalMap.get("AMCOUNTPIECE")==null?"0":totalMap.get("AMCOUNTPIECE")+"");
				vo.setPmcountpiece(totalMap.get("PMCOUNTPIECE")==null?"0":totalMap.get("PMCOUNTPIECE")+"");
				vo.setAmweight(totalMap.get("AMWEIGHT")==null?"0":totalMap.get("AMWEIGHT")+"");
				vo.setPmweight(totalMap.get("PMWEIGHT")==null?"0":totalMap.get("PMWEIGHT")+"");
				vo.setQianshou(totalMap.get("QIANSHOU")==null?"0":totalMap.get("QIANSHOU")+"");
				vo.setAmqiandan(totalMap.get("AMQIANDAN")==null?"0":totalMap.get("AMQIANDAN")+"");
				vo.setPmqiandan(totalMap.get("PMQIANDAN")==null?"0":totalMap.get("PMQIANDAN")+"");
				vo.setAmshouru(totalMap.get("AMSHOURU")==null?"0":totalMap.get("AMSHOURU")+"");
				vo.setPmshouru(totalMap.get("PMSHOURU")==null?"0":totalMap.get("PMSHOURU")+"");
				vo.setReturnNum(totalMap.get("RETURNNUM")==null?"0":totalMap.get("RETURNNUM")+"");
				vo.setStopFee(totalMap.get("STOP_FEE")==null?"0":totalMap.get("STOP_FEE")+"");
				
				Struts2Utils.renderJson(vo);
			}
		}catch (Exception e) {
			addError(e.getLocalizedMessage(), e);
		}
		return this.LIST;
	}
}
