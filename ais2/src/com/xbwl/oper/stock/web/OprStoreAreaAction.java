package com.xbwl.oper.stock.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprStoreArea;
import com.xbwl.oper.stock.service.IOprStoreAreaService;

/**
 * @author CaoZhili
 * 
 * 库存区域控制层操作类
 *
 */
@Controller
@Action("oprStoreAreaAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_storeArea.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) })
public class OprStoreAreaAction extends SimpleActionSupport {

	@Resource(name = "oprStoreAreaServiceImpl")
	private IOprStoreAreaService iOprStoreAreaService;

	private OprStoreArea oprStoreArea;

	@Override
	protected Object createNewInstance() {
		return new OprStoreArea();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.iOprStoreAreaService;
	}

	@Override
	public Object getModel() {
		return this.oprStoreArea;
	}

	@Override
	public void setModel(Object obj) {
		oprStoreArea = (OprStoreArea) obj;
	}
	@Value("${oprStoreArea.cusTporp}")
	private String   cusTporps; 
	
	public String findCusName(){
		String[] custprops=cusTporps.split(",");
		List<Map> cuslist;
		try {
			String cusName=ServletActionContext.getRequest().getParameter("filter_LIKES_cusName");
			cuslist = this.iOprStoreAreaService.findCusNameService(custprops,cusName);
			//System.out.println("cuslist:"+cuslist+"-- size:"+cuslist.size());
			Struts2Utils.renderJson(cuslist);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("查询失败,失败原因:"+e.getLocalizedMessage());
			
		}
		return null;
	}
	
	public String findPkAreacl(){
		
		List<Map> cuslist;
		try {
			String pkAreacl=ServletActionContext.getRequest().getParameter("filter_LIKES_pkAreacl");
			cuslist = this.iOprStoreAreaService.findPkAreaclService(pkAreacl);
			//System.out.println("cuslist:"+cuslist+"-- size:"+cuslist.size());
			Struts2Utils.renderJson(cuslist);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("查询失败,失败原因:"+e.getLocalizedMessage());
			
		}
		return null;
	}

}
