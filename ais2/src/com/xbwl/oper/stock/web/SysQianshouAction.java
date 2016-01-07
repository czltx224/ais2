package com.xbwl.oper.stock.web;

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
import com.xbwl.entity.SysQianshou;
import com.xbwl.oper.edi.service.ISysQianshouService;

/**
 * 短信签收记录表控制层操作类
 * @author czl
 * @date 2012-06-08
 *
 */
@Controller
@Action("sysQianshouAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/sys_qianshou_record.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class SysQianshouAction extends SimpleActionSupport {

	@Resource(name="sysQianshouServiceImpl")
	private ISysQianshouService sysQianshouService;
	
	private SysQianshou sysQianshou;
	
	@Override
	protected Object createNewInstance() {
		return new SysQianshou();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.sysQianshouService;
	}

	@Override
	public Object getModel() {
		return this.sysQianshou;
	}

	@Override
	public void setModel(Object obj) {
		this.sysQianshou=(SysQianshou)obj;
	}
	
	/**
	 * 短信签收历史记录查询
	 * @return
	 */
	public String findRecordList(){
		String sql=null;
		//设置分页参数
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.sysQianshouService.findRecordList(map);
			this.sysQianshouService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("短信签收历史记录查询失败！", e);
		}
		return this.LIST;
	}
}
