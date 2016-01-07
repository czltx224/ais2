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
import com.xbwl.cus.service.ICusProfitService;
import com.xbwl.entity.CusProfit;

/**
 * 客户报价管理控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusPublicPriceAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_publicPrice.jsp", type = "dispatcher"),
		@Result(name = "main", location = "/WEB-INF/xbwl/cus/cus_quotePrice.jsp", type = "dispatcher"),
		@Result(name = "spePrice", location = "/WEB-INF/xbwl/cus/cus_spePrice.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusPublicPriceAction extends SimpleActionSupport {
	@Resource(name="cusProfitServiceImpl")
	private ICusProfitService cusProfitService;
	private CusProfit cusProfit;
	@Override
	protected Object createNewInstance() {
		return new CusProfit();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusProfitService;
	}
	@Override
	public Object getModel() {
		return cusProfit;
	}
	@Override
	public void setModel(Object obj) {
		cusProfit=(CusProfit)obj;
	}
	public String main(){
		return "main";
	}
	public String spePrice(){
		return "spePrice";
	}
}
