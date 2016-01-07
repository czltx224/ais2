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

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.FlowExport;
import com.xbwl.flow.service.IFlowExportService;

/**
 * 流程管理- 出口管理控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("flowExportAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_export.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class FlowExportAction extends SimpleActionSupport {
	
	@Resource(name="flowExportServiceImpl")
	private IFlowExportService flowExportService;
	private FlowExport flowExport;
	private Long exportId;//出口ID
	@Element(value = FlowExport.class)
	private List<FlowExport> exportList;//出口List

	@Override
	protected Object createNewInstance() {
		return new FlowExport();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return flowExportService;
	}

	@Override
	public Object getModel() {
		return flowExport;
	}

	@Override
	public void setModel(Object obj) {
		this.flowExport=(FlowExport)obj;
	}
	

	/**
	 * @return the exportId
	 */
	public Long getExportId() {
		return exportId;
	}

	/**
	 * @param exportId the exportId to set
	 */
	public void setExportId(Long exportId) {
		this.exportId = exportId;
	}
	
	/**
	 * @return the exportList
	 */
	public List<FlowExport> getExportList() {
		return exportList;
	}

	/**
	 * @param exportList the exportList to set
	 */
	public void setExportList(List<FlowExport> exportList) {
		this.exportList = exportList;
	}

	public String delete(){
		FlowExport fe=flowExportService.getAndInitEntity(exportId);
		if(fe == null){
			throw new ServiceException("数据异常，ID："+exportId+"对应的对象为空了。");
		}
		fe.setStatus(0L);
		flowExportService.save(fe);
		return RELOAD;
	}
	
	public String save(){
		try {
			flowExportService.saveExports(exportList);
			this.getValidateInfo().setMsg("保存成功！");
			this.getValidateInfo().setSuccess(true);
		} catch (Exception e) {
			this.getValidateInfo().setMsg("保存失败！");
			this.getValidateInfo().setSuccess(false);
			addError("保存失败", e);
		}
		return RELOAD;
	}
	
}
