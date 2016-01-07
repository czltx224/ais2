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
import com.xbwl.entity.FlowFormlink;
import com.xbwl.flow.service.IFormlinkService;

/**
 * 流程管理- 表单关联控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("flowFormlinkAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_formlink.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class FlowFormlinkAction extends SimpleActionSupport {
	
	@Resource(name="formlinkServiceImpl")
	private IFormlinkService formlinkService;
	private FlowFormlink flowFormlink;
	private Long linkId;//表单关联ID

	@Override
	protected Object createNewInstance() {
		return new FlowFormlink();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return formlinkService;
	}

	@Override
	public Object getModel() {
		return flowFormlink;
	}

	@Override
	public void setModel(Object obj) {
		this.flowFormlink=(FlowFormlink)obj;
	}

	/**
	 * @return the linkId
	 */
	public Long getLinkId() {
		return linkId;
	}

	/**
	 * @param linkId the linkId to set
	 */
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
	
	public String delete(){
		FlowFormlink ff=formlinkService.getAndInitEntity(linkId);
		if(ff == null){
			throw new ServiceException("数据异常，ID："+linkId+"对应的对象为空了。");
		}
		ff.setStatus(0L);
		formlinkService.save(ff);
		return RELOAD;
	}
}
