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
import com.xbwl.entity.FlowNodeinfo;
import com.xbwl.flow.service.IFlowNodeinfoService;

/**
 * 流程管理- 节点信息控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("flowNodeinfoAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_nodeinfo.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class FlowNodeinfoAction extends SimpleActionSupport {
	
	@Resource(name="flowNodeinfoServiceImpl")
	private IFlowNodeinfoService flowNodeinfoService;
	private FlowNodeinfo flowNodeinfo;
	private Long nodeId;

	@Override
	protected Object createNewInstance() {
		return new FlowNodeinfo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return flowNodeinfoService;
	}

	@Override
	public Object getModel() {
		return flowNodeinfo;
	}

	@Override
	public void setModel(Object obj) {
		this.flowNodeinfo=(FlowNodeinfo)obj;
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
	public String save(){
		if(flowNodeinfo.getId() == null){
			flowNodeinfo.setDrawxpos(-1L);
			flowNodeinfo.setDrawypos(-1L);
		}
		flowNodeinfoService.save(flowNodeinfo);
		return RELOAD;
	}
	
	public String delete(){
		FlowNodeinfo fn=flowNodeinfoService.getAndInitEntity(nodeId);
		if(fn == null){
			throw new ServiceException("数据异常，ID："+nodeId+"对应的对象为空了。");
		}
		fn.setStatus(0L);
		flowNodeinfoService.save(fn);
		return RELOAD;
	}
}
