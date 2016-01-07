package com.xbwl.oper.stock.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprStock;
import com.xbwl.oper.stock.service.IOprStockService;

/**
 * @author CaoZhili
 * time Aug 16, 2011 4:54:48 PM
 * 
 * 异常库存管理控制层操作类
 */
@Controller
@Action("oprStockAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_stock.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprStockAction extends SimpleActionSupport {

	@Resource(name="oprStockServiceImpl")
	private IOprStockService oprStockService; 
	
	private OprStock oprStock;
	
	@Override
	protected Object createNewInstance() {

		return new OprStock();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		
		return this.oprStockService;
	}

	@Override
	public Object getModel() {

		return this.oprStock;
	}

	@Override
	public void setModel(Object obj) {

		this.oprStock=(OprStock)obj;
	}
	
	/**
	 * 库存查询地址
	 * @return
	 */
	public String findStock(){
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		try{
			String sql=this.oprStockService.getStockSql(map);
			
			Page pg = this.oprStockService.getPageBySqlMap(this.getPages(), sql,map);
			super.getValidateInfo().setSuccess(true);
			Struts2Utils.renderJson(pg);
		}catch(Exception e){
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("查询失败,失败原因:"+e.getLocalizedMessage());
		}
		return this.LIST;
		
	}
	
	
	/**正常库存转异常库存
	 * @return
	 */
	public String toExceptionStock(){
		try{
			String ids[] = ServletActionContext.getRequest().getParameter("stockIds").split(",");
			String remark = ServletActionContext.getRequest().getParameter("remark");
			this.oprStockService.toExceptionStockService(ids,remark);
			addMessage("成功转到异常库存！");
		}catch (Exception e) {
			addError(e.getLocalizedMessage(), e);
		}

		return this.RELOAD;
	}
}
