package com.xbwl.oper.stock.web;

import java.util.Date;
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

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.OprExceptionStock;
import com.xbwl.oper.stock.service.IOprExceptionStockService;
import com.xbwl.oper.stock.vo.OprExceptionStockVo;

/**
 * author CaoZhili
 * time Nov 18, 2011 9:07:40 AM
 * 异常库存管理控制层操作类
 */
@Controller
@Action("oprExceptionStockAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_exceptionStock.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprExceptionStockAction extends SimpleActionSupport {

	@Resource(name="oprExceptionStockServiceImpl")
	private IOprExceptionStockService oprExceptionStockService;
	
	private OprExceptionStock oprExceptionStock;
	
	private OprExceptionStockVo vo;//保存界面数值的对象
	
	private String exceptionStockIds;//异常库存ID数组
	
	public String getExceptionStockIds() {
		return exceptionStockIds;
	}

	public void setExceptionStockIds(String exceptionStockIds) {
		this.exceptionStockIds = exceptionStockIds;
	}

	@Override
	protected Object createNewInstance() {
		return new OprExceptionStock();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprExceptionStockService;
	}

	@Override
	public Object getModel() {
		return this.oprExceptionStock;
	}

	@Override
	public void setModel(Object obj) {
		this.oprExceptionStock= (OprExceptionStock)obj;
	}

	public OprExceptionStockVo getVo() {
		return vo;
	}

	public void setVo(OprExceptionStockVo vo) {
		this.vo = vo;
	}

	/**异常出库
	 * @return
	 */
	public String exceptionOutStock(){
		String strId = ServletActionContext.getRequest().getParameter("id");
		try{
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			this.vo.setExceptionOutTime(new Date());
			this.vo.setId(strId==null?null:Long.valueOf(strId));
			this.vo.setExceptionOutName(user.get("name").toString());
			this.oprExceptionStockService.outStockService(vo);
		}catch (Exception e) {
			addError(e.getLocalizedMessage(), e);
		}
		return this.RELOAD;
	}
	
	/**异常库存转正常库存
	 * @return
	 */
	public String toNormalStock(){
		try{
			String[] ids = exceptionStockIds.split(",");
			String remark = ServletActionContext.getRequest().getParameter("remark");
			this.oprExceptionStockService.toNormalStockService(ids,remark);
			addMessage("成功转到正常库存！");
		}catch (Exception e) {
			addError(e.getLocalizedMessage(), e);
		}
		return this.RELOAD;
	}
}
