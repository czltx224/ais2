package com.xbwl.flow.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.FlowFormfield;
import com.xbwl.flow.service.IFormfieldService;

/**
 * 流程管理- 表单字段控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("flowFormfieldAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "updateForm", location = "/WEB-INF/xbwl/flow/flow_formupdate.jsp", type = "dispatcher"),
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_forminfo.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class FlowFormfieldAction extends SimpleActionSupport {
	
	@Resource(name="formfieldServiceImpl")
	private IFormfieldService formfieldService;
	private FlowFormfield flowFormfield;
	private Long fieldId;
	private String tableName;//数据库表名
	

	@Override
	protected Object createNewInstance() {
		return new FlowFormfield();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return formfieldService;
	}

	@Override
	public Object getModel() {
		return flowFormfield;
	}

	@Override
	public void setModel(Object obj) {
		this.flowFormfield=(FlowFormfield)obj;
	}
	
	/**
	 * @return the fieldId
	 */
	public Long getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String save(){
		try {
			formfieldService.saveFormfield(flowFormfield,tableName);
		} catch (Exception e) {
			addError("保存失败", e);
		}
		return RELOAD;
	}
	
	public String delete(){
		FlowFormfield ff=formfieldService.getAndInitEntity(fieldId);
		if(ff == null){
			throw new ServiceException("数据异常，ID："+fieldId+"对应的对象为空了。");
		}
		ff.setStatus(0L);
		formfieldService.save(ff);
		return RELOAD;
	}
}
