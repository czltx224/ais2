package com.xbwl.cus.web;

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
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.ICusSearchService;
import com.xbwl.entity.CusSearch;

/**
 * author CaoZhili
 * time Oct 17, 2011 10:12:26 AM
 * 自定义查询操作层控制类
 */
@Controller
@Action("cusSearchAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_cusSearchAuthority.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusSearchAction extends SimpleActionSupport {

	@Resource(name="cusSearchServiceImpl")
	private ICusSearchService cusSearchService;
	
	private CusSearch cusSearch;
	
	private Long departId;
	
	@Override
	protected Object createNewInstance() {
		return new CusSearch();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.cusSearchService;
	}

	@Override
	public Object getModel() {
		return this.cusSearch;
	}

	@Override
	public void setModel(Object obj) {
		this.cusSearch=(CusSearch)obj;
	}
	
	/**查询自定义查询可用方法的最后十条
	 * @return
	 */
	public String findCusSearch(){
		
		setPageConfig();
		getPages().setLimit(10);//查询最后十条
		String sql=null;
		try{
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("departCode", map.get("departCode")+"%");
			sql = this.cusSearchService.findCusSearchService(map);
			this.cusSearchService.getPageBySqlMap(this.getPages(), sql, map);
		}catch (Exception e) {
			addError("自定义查询TOP10失败！", e);
		}
		return this.LIST;
	}
	
	/**自定义查询授权方法
	 * @return
	 */
	public String authorized(){
		
		String[]  idStrings = ServletActionContext.getRequest().getParameter("ids").split(",");
		
		try{
			this.cusSearchService.authorizedService(idStrings,this.departId);
		}catch (Exception e) {
			addError("自定义查询授权失败！", e);
		}
		
		return this.RELOAD;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	/**
	 * 手写自定义SQL查询方法
	 * @return
	 */
	public String  findSearchList(){
		
		String sql=null;
		//设置分页参数
        setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		try{
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			map.put("checkItems", this.getCheckItems());
			map.put("itemsValue", this.getItemsValue());
			map.put("createName", user.get("name")+"");
			map.put("departCode", user.get("departId")+"");
			sql = this.cusSearchService.findSearchListService(map);
			this.cusSearchService.getPageBySqlMap(this.getPages(), sql, map);
		}catch (Exception e) {
			addError("查询自定义查询失败！", e);
		}
		
		return this.LIST;
	}
}
