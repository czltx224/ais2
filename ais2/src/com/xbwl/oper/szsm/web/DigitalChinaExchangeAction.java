package com.xbwl.oper.szsm.web;

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
import com.xbwl.entity.DigitalChinaExchange;
import com.xbwl.oper.szsm.service.IDataExchangeService;

/**
 * 神州数码对接控制层操作类
 * @author czl
 * @date 2012-06-27 
 */
@Controller
@Action("digitalChinaExchangeAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/szsm/opr_digitalChinaExchange.jsp", type = "dispatcher"),
		@Result(name = "count", location = "/WEB-INF/xbwl/stock/szsm/opr_digitalChinaExchangeCount.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class DigitalChinaExchangeAction extends SimpleActionSupport {

	@Resource(name="dataExchangeServiceImpl")
	private IDataExchangeService dataExchangeService;
	
	private DigitalChinaExchange digitalChinaExchange;
	
	@Override
	protected Object createNewInstance() {
		return new DigitalChinaExchange();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.dataExchangeService;
	}

	@Override
	public Object getModel() {
		return this.digitalChinaExchange;
	}

	@Override
	public void setModel(Object obj) {
		this.digitalChinaExchange=(DigitalChinaExchange)obj;
	}

	/**
	 * 神州数码对接成功结果查询
	 * @return
	 */
	public String findList(){
		String sql=null;
		//设置分页参数
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.dataExchangeService.findList(map);
			this.dataExchangeService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("神州数码对接成功结果查询查询失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * 跳转到神州数码对接统计页面
	 * @return
	 */
	public String count(){
		return "count";
	}
	
	/**
	 * 神州数码对接统计查询
	 * @return
	 */
	public String findCount(){
		String sql=null;
		//设置分页参数
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.dataExchangeService.findCount(map);
			this.dataExchangeService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("神州数码对接统计查询！", e);
		}
		return this.LIST;
	}
	
	/**
	 * 神州数码对接统计汇总
	 * @return
	 */
	public String findCountSum(){
		String sql=null;
		//设置分页参数
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.dataExchangeService.findCountSum(map);
			this.dataExchangeService.getPageBySqlMap(this.getPages(), sql, map);
			
			List<Map> list =getPages().getResultMap();
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("CHUANZHEN","传真(票)"+(totalMap.get("CHUANZHEN")==null?"0":totalMap.get("CHUANZHEN")));
				jsonObject.element("CHUKU","出库(票)"+(totalMap.get("CHUKU")==null?"0":totalMap.get("CHUKU")));
				jsonObject.element("PEICHEDUIJIE",("配车对接(票)"+totalMap.get("PEICHEDUIJIE")==null?"0":totalMap.get("PEICHEDUIJIE")));
				jsonObject.element("QIANSHOUDUIJIE","签收对接(票)"+(totalMap.get("QIANSHOUDUIJIE")==null?"0":totalMap.get("QIANSHOUDUIJIE")));
				jsonObject.element("DUIJIE","对接(票)"+(totalMap.get("DUIJIE")==null?"0":totalMap.get("DUIJIE")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch(Exception e){
			addError("神州数码对接统计查询！", e);
		}
		return this.LIST;
	}
}
