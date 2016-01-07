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

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprNode;
import com.xbwl.oper.stock.service.IOprNodeService;

/**
 * author shuw
 * time Aug 9, 2011 8:45:57 AM
 */
@Controller
@Action("oprNodeAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) })
public class OprNodeAction extends SimpleActionSupport {
	
	@Resource(name = "oprNodeServiceImpl")
	private IOprNodeService oprNodeService;
	
	private OprNode oprNode;

	
	/**
	 * 综合查询获取部分节点
	 * @return
	 */
	public String getList() {
		String sql=null;
		//设置分页参数
        setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		try{
			sql  = oprNodeService.getListOfQuery(map);  //sonderzug
			oprNodeService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("查询失败！", e);
			
		}
		return LIST;
	}
	
	@Override
	protected Object createNewInstance() {
		return new OprNode();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprNodeService;
	}

	@Override
	public Object getModel() {
		return oprNode;
	}

	@Override
	public void setModel(Object obj) {
		oprNode = (OprNode)obj;
	}

}
