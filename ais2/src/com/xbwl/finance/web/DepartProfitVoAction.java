package com.xbwl.finance.web;

import java.util.HashMap;
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
import com.xbwl.finance.Service.IFiCostService;
import com.xbwl.finance.vo.DepartProfitVo;
import com.xbwl.oper.stock.vo.OprPrewiredDetailVo;

/**
 * author shuw
 * time Oct 17, 2011 4:45:13 PM
 */
@Controller
@Action("departProfitVoAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cost/depart_profit_vo_main.jsp", type = "dispatcher"),
		@Result(name = "dnototal", location = "/WEB-INF/xbwl/cost/depart_profit_vo_dno.jsp", type = "dispatcher"),
		@Result(name = "cptotal", location = "/WEB-INF/xbwl/cost/depart_profit_vo_cp.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		///cus/cusPublicPriceAction!main.action
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class DepartProfitVoAction extends SimpleActionSupport {
	
	private DepartProfitVo departProfitVo;
	
	@Resource(name = "fiCostServiceImpl")
	private IFiCostService fiCostService;

	/**
	 * 代理汇总统 查询功能
	 * @return
	 */
	public String queryCqList(){
		try{
			setPageConfig();  	//设置分页参数
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			String sql=this.fiCostService.getAllCqList(map);
			System.out.println(sql);
			fiCostService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("查询失败！", e);
			
		}
		return this.LIST;
	}
	
	public String queryCqTotal(){
		try {
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			String sql=this.fiCostService.getAllCqList(map);
			StringBuffer sb=new StringBuffer(" select  sum(piece) piece,");
			sb.append("sum(countdno) countdno, ");
			sb.append("sum(cusWeight) cusWeight,");
			
			sb.append("sum(signDanCostFee) signDanCostFee,");
			sb.append("sum(doGoodCostFee) doGoodCostFee,");
			sb.append("sum(transitCostFee) transitCostFee,");
			sb.append("sum(outSideCostFee) outSideCostFee,");
			sb.append("sum(therCostFee) therCostFee,");
			sb.append("sum(totalCostFee) totalCostFee,");
			
			sb.append(" sum(therAddFee) therAddFee ,");
			sb.append(" sum(cpValueAddFee)+sum(cpFee)+sum(yfSonderzugPrice)  yfFee ,");
			sb.append(" sum(consigneeFee)+sum(cusValueAddFee)+ sum(sonderzugPrice)  dyFee,");
			
			sb.append("sum(totalIncomeFee) totalIncomeFee,");
			sb.append("sum(grossProfitFee) grossProfitFee, "); 
			sb.append(" sum(paymentcollection) paymentcollection,");
			sb.append("round(avg(maorate), 2) maorate ");
			sb.append("from (");
			sb.append(sql);
			sb.append(" )");
			fiCostService.getPageBySqlMap(this.getPages(), sb.toString(), map);
			Struts2Utils.renderJson(getPages());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	@Override
	protected Object createNewInstance() {
		return  new DepartProfitVo();
	}

	public String dnototal(){
		return "dnototal";
	}
	
	public String cptotal(){
		return "cptotal";
	}
	
	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiCostService;
	}

	@Override
	public Object getModel() {
		return departProfitVo;
	}

	@Override
	public void setModel(Object obj) {
		departProfitVo=(DepartProfitVo)obj;
	}
}
