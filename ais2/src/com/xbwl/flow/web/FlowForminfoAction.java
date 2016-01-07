package com.xbwl.flow.web;

import java.util.List;
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
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FlowForminfo;
import com.xbwl.flow.service.IForminfoService;

/**
 * 流程管理- 表单信息控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("flowForminfoAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "formShow", location = "/WEB-INF/xbwl/flow/flow_formshow.jsp", type = "dispatcher"),
		@Result(name = "updateForm", location = "/WEB-INF/xbwl/flow/flow_formupdate.jsp", type = "dispatcher"),
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_forminfo.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class FlowForminfoAction extends SimpleActionSupport {
	
	@Resource(name="forminfoServiceImpl")
	private IForminfoService forminfoService;
	private FlowForminfo flowForminfo;
	
	private Long formId;//表单ID
	private Long objType;//表单表现形式
	private String tableName;//数据库表名
	
	private String tableType;//表单类型 main or detail
	private Long pipeId;//管道ID
	
	private String oprType;//操作类型
	private Long workflowId;//流程ID
	private Long nodeId;//节点ID
	
	private Long operateType;//审批类型

	@Override
	protected Object createNewInstance() {
		return new FlowForminfo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return forminfoService;
	}

	@Override
	public Object getModel() {
		return flowForminfo;
	}

	@Override
	public void setModel(Object obj) {
		this.flowForminfo=(FlowForminfo)obj;
	}
	
	/**
	 * @return the formId
	 */
	public Long getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	/**
	 * @return the objType
	 */
	public Long getObjType() {
		return objType;
	}

	/**
	 * @param objType the objType to set
	 */
	public void setObjType(Long objType) {
		this.objType = objType;
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
	
	/**
	 * @return the tableType
	 */
	public String getTableType() {
		return tableType;
	}

	/**
	 * @param tableType the tableType to set
	 */
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	
	/**
	 * @return the pipeId
	 */
	public Long getPipeId() {
		return pipeId;
	}

	/**
	 * @param pipeId the pipeId to set
	 */
	public void setPipeId(Long pipeId) {
		this.pipeId = pipeId;
	}
	
	/**
	 * @return the oprType
	 */
	public String getOprType() {
		return oprType;
	}

	/**
	 * @param oprType the oprType to set
	 */
	public void setOprType(String oprType) {
		this.oprType = oprType;
	}
	
	/**
	 * @return the workflowId
	 */
	public Long getWorkflowId() {
		return workflowId;
	}

	/**
	 * @param workflowId the workflowId to set
	 */
	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	/**
	 * @return the operateType
	 */
	public Long getOperateType() {
		return operateType;
	}

	/**
	 * @param operateType the operateType to set
	 */
	public void setOperateType(Long operateType) {
		this.operateType = operateType;
	}

	public String save(){
		try {
			forminfoService.saveFormInfo(flowForminfo);
		} catch (Exception e) {
			addError("保存失败", e);
		}
		return RELOAD;
	}
	
	public String gotoUpdateForm(){
		return "updateForm";
	}
	
	public String delete(){
		FlowForminfo  ff=forminfoService.getAndInitEntity(formId);
		if(ff == null){
			throw new ServiceException("数据异常，ID："+formId+"对应的对象为空了。");
		}
		ff.setStatus(0L);
		forminfoService.save(ff);
		return RELOAD;
	}
	/**
	 * 获取表单的表头信息
	 * @author LiuHao
	 * @time Feb 27, 2012 6:16:01 PM 
	 * @return
	 */
	public String getFormhead(){
		try {
			List list=forminfoService.getFormByPipeId(pipeId, tableType);
			Struts2Utils.renderJson(list);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return null;
	}
	
	public String gotoFormShow(){
		return "formShow";
	}
	/**
	 * 查询表单字段对应的值
	 * @author LiuHao
	 * @time Feb 28, 2012 1:40:11 PM 
	 * @return
	 */
	public String getFormvalue(){
		try {
			List list = forminfoService.getFormValueByPipeId(pipeId, tableType,workflowId);
			Struts2Utils.renderJson(list);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return null;
	}
}
