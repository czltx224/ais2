package com.xbwl.flow.web;

import java.io.PrintWriter;
import java.util.Iterator;
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
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FlowExport;
import com.xbwl.entity.FlowRalarule;
import com.xbwl.flow.service.IFlowRalaruleService;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.rbac.entity.SysUser;

/**
 * 流程管理- 权限规则控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("flowRalaruleAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_export.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class FlowRalaruleAction extends SimpleActionSupport {
	
	@Resource(name="flowRalaruleServiceImpl")
	private IFlowRalaruleService flowRalaruleService;
	@Resource(name="userServiceImpl")
	private IUserService userService;
	private FlowRalarule flowRalarule;
	private Long ralaId;//出口ID
	private Long nodeId;//节点ID
	private Long pipeId;//管道ID
	private Long workflowId;
	@Element(value = FlowExport.class)
	private List<FlowExport> exportList;//出口List

	@Override
	protected Object createNewInstance() {
		return new FlowRalarule();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return flowRalaruleService;
	}

	@Override
	public Object getModel() {
		return flowRalarule;
	}

	@Override
	public void setModel(Object obj) {
		this.flowRalarule=(FlowRalarule)obj;
	}
	

	
	
	/**
	 * @return the ralaId
	 */
	public Long getRalaId() {
		return ralaId;
	}

	/**
	 * @param ralaId the ralaId to set
	 */
	public void setRalaId(Long ralaId) {
		this.ralaId = ralaId;
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

	public String delete(){
		FlowRalarule fr=flowRalaruleService.getAndInitEntity(ralaId);
		if(fr == null){
			throw new ServiceException("数据异常，ID："+ralaId+"对应的对象为空了。");
		}
		fr.setStatus(0L);
		flowRalaruleService.save(fr);
		return RELOAD;
	}
	
	public String getRules(){
		StringBuffer str=new StringBuffer("<div>");
		try {
			Map<Long,Long> userMap = flowRalaruleService.getRalaByNodeId(nodeId, pipeId,workflowId);
			Iterator<Long> iter = userMap.keySet().iterator();
			while(iter.hasNext()){
				Long key = iter.next();
				SysUser su = userService.getAndInitEntity(key);
				if(su == null){
					throw new ServiceException("数据异常，用户ID："+key+"对应的用户不存在!");
				}
				str.append("用户:");
				str.append(su.getUserName());
				str.append("<br>");
			}
			str.append("</div>");
			Struts2Utils.getResponse().setCharacterEncoding("UTF-8");
			Struts2Utils.getResponse().setContentType("text/xml");
			Struts2Utils.getResponse().setHeader("Cache-control", "no-cache");
		    PrintWriter writer = Struts2Utils.getResponse().getWriter();
		    writer.write(str.toString());
		} catch (Exception e) {
			addError("查询规则出错！", e);
		}
		return null;
	}
}
