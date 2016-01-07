package com.xbwl.sys.web;

import java.util.Map;

import javax.annotation.Resource;

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
import com.xbwl.entity.BasRightDepart;
import com.xbwl.sys.service.IBasRightDepartService;

@Controller
@Action("basRightDepartAction")  //loadingbrigadeAction
@Scope("prototype")
@Namespace("/sys")
@Results( {
			@Result(name = "input", location = "/WEB-INF/xbwl/sys/sys_basRightDepart.jsp", type = "dispatcher"),
			@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
			@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" })
})
public class BasRightDepartAction extends SimpleActionSupport {

	@Resource(name = "basRightDepartServiceImpl")
	private IBasRightDepartService basRightDepartService;
	
	private BasRightDepart basRightDepart;

	@Override
	protected Object createNewInstance() {

		return new BasRightDepart();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.basRightDepartService;
	}

	@Override
	public Object getModel() {

		return this.basRightDepart;
	}

	@Override
	public void setModel(Object obj) {
		
		this.basRightDepart=(BasRightDepart)obj;
	}
	
	/**
	 * 获取权限部门
	 * @return
	 */
	public String  findDepartName(){
		
		String sql=null;
		//设置分页参数
        setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		
		try{
			sql=this.basRightDepartService.findDepartName(map);
			this.basRightDepartService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("查询失败！", e);
			
		}
		return this.LIST;
	}
}
