package com.xbwl.oper.stock.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.stock.service.IOprOvermemoDetailService;
import com.xbwl.oper.stock.vo.OprMathingGoods;

/**
 * @author CaoZhili time Aug 15, 2011 10:04:24 AM
 * 
 * 有货无单单据匹配控制层操作类
 */
@Controller
@Action("haveGoodsNoReceiptsAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_haveGoodsNoReceipts.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class HaveGoodsNoReceiptsAction extends SimpleActionSupport {

	@Resource(name = "oprOvermemoDetailServiceImpl")
	private IOprOvermemoDetailService oprOvermemoDetailService;
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;

	private OprOvermemoDetail oprOvermemoDetail;

	@Element(value = OprMathingGoods.class)
	private List<OprMathingGoods> mathList;
	
	@Override
	protected Object createNewInstance() {
		return new OprOvermemoDetail();
	}

	public List<OprMathingGoods> getMathList() {
		return mathList;
	}


	public void setMathList(List<OprMathingGoods> mathList) {
		this.mathList = mathList;
	}


	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprOvermemoDetailService;
	}

	@Override
	public Object getModel() {

		return this.oprOvermemoDetail;
	}

	@Override
	public void setModel(Object obj) {

		this.oprOvermemoDetail = (OprOvermemoDetail) obj;
	}

	/**
	 * 按照主单号或者航班号查询传真表的数据，包括点到状态
	 * @return
	 */
	public String findHaveGoodsNoReceipt(){
		
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
	
		try{
			String sql = this.oprFaxInService.getSqlHaveGoodsNoReceipt(map);
			map.put("EQL_reachStatus", "0");
			map.put("EQL_faxStatus", "1");
			Page pg = this.oprFaxInService.getPageBySqlMap(this.getPages(), sql,map);
			super.getValidateInfo().setSuccess(true);
			Struts2Utils.renderJson(pg);
		}catch(Exception e){
			addError("按照主单号或者航班号查询传真表的数据失败！",e);
		}
		
		return this.LIST;
	}

	/**
	 * 有货无单匹配方法
	 * @return
	 */
	public String saveMatching(){
	
		try{
			this.oprOvermemoDetailService.saveMatchingService(this.mathList);
		}catch(Exception e){
			e.printStackTrace();
			addError("手动匹配失败！", e);
		}
		return this.RELOAD;
	}
	
	/**
	 * 有货无单查询方法
	 * @return
	 */
	public String findNoFaxList(){
		try{
			String sql=null;
			//设置分页参数
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			sql=this.oprOvermemoDetailService.findNoFaxListService(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("有货无单查询失败！", e);
		}
		return this.LIST;
	}
}
