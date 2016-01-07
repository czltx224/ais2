package com.xbwl.sys.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprExceptionType;
import com.xbwl.sys.service.IOprExceptionTypeService;

/**
 * author shuw
 * time Aug 17, 2011 9:29:19 AM
 */
@Controller
@Action("exceptionTypeAction")
@Scope("prototype")
@Namespace("/sys")
@Results({
            @Result(name = "input", location = "/WEB-INF/xbwl/sys/opr_exception_type_list.jsp", type = "dispatcher"),
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","includeProperties","*"})
         })
public class OprExceptionTypeAction extends SimpleActionSupport {

	private OprExceptionType oprExceptionType;
	
	@Resource(name="oprExceptionTypeServiceImpl")
	private IOprExceptionTypeService oprExceptionTypeService;

	
	@Override
	protected Object createNewInstance() {
		return new OprExceptionType();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprExceptionTypeService;
	}

	@Override
	public Object getModel() {
		return oprExceptionType;
	}

	@Override
	public void setModel(Object obj) {
		oprExceptionType=(OprExceptionType)obj;
	}
	
	/**
	 * 异常类型节点数据调用方法
	 * @return
	 */
	public String  getExceTypeNode(){
		String sql=null;
        setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		try{
			sql=oprExceptionTypeService.getExceTypeNodeSql(map);
			oprExceptionTypeService.getPageBySqlMap(this.getPages(), sql, map);
		}catch(Exception e){
			addError("查询失败！", e);
		}
		return LIST;
	}
	
	/*
	public String getExceptionTree(){
		try{
			Struts2Utils.renderJson(oprExceptionTypeService.getExceptionTreeById(node));
		}catch(Exception e){
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("查询失败,失败原因:"+e.getLocalizedMessage());
			
			return "reload";
		}
		return null;
	}*/

	/**
	 * 根据上级类型ID查询类型信息
	 * @param parentId
	 * @return
	public String getExceptionTypeByParentId() {
		try {
			setPageConfig();
			oprExceptionTypeService.getExceptionByParentId(this.getPages(), parentId);
		} catch (Exception e) {
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("查询失败,失败原因:" + e.getLocalizedMessage());
			return "reload";
		}
		return "list";
	}
	 */
	
	/**
	 * 获得所有异常类型信息，去除关联
	 * @return
	public String findAll(){
		try {
			super.prepareList();
			oprExceptionTypeService.findAllException(this.getPages(), this.getFilters());
		} catch (Exception e) {
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("查询失败,失败原因:" + e.getLocalizedMessage());
			return "reload";
		}
		return "list";
	}
	 */


	
	public String excepList(){
		return "oprExceptionTypeList";
	}
}

