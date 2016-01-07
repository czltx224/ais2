package com.xbwl.flow.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FlowWorkflowstep;
import com.xbwl.flow.service.IWorkFlowstepService;

/**
 * 流程管理- 流程流转信息控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("workFlowstepAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_stayaudit.jsp", type = "dispatcher"),
		@Result(name = "stepTime", location = "/WEB-INF/xbwl/flow/flow_step_time.jsp", type = "dispatcher"),
		@Result(name = "alreadyAudit", location = "/WEB-INF/xbwl/flow/flow_alreadyaudit.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class WorkFlowstepAction extends SimpleActionSupport {
	
	@Resource(name="workFlowstepServiceImpl")
	private IWorkFlowstepService workFlowstepService;
	private FlowWorkflowstep flowWorkflowstep;
	private Date startTime;
	private Date endTime;
	
	private Date startDate;
	private Date endDate;
	private int recordSize;
	private String sort;
	private Long pipeId;
	private String countType;
	
	private Long workflowId;
	private String workflowName;
	private String isAlert;
	private Long operType;

	@Override
	protected Object createNewInstance() {
		return new FlowWorkflowstep();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return workFlowstepService;
	}

	@Override
	public Object getModel() {
		return flowWorkflowstep;
	}

	@Override
	public void setModel(Object obj) {
		this.flowWorkflowstep=(FlowWorkflowstep)obj;
	}
	
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public Long getPipeId() {
		return pipeId;
	}

	public void setPipeId(Long pipeId) {
		this.pipeId = pipeId;
	}
	
	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}
	
	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	
	

	public String getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(String isAlert) {
		this.isAlert = isAlert;
	}
	
	public Long getOperType() {
		return operType;
	}

	public void setOperType(Long operType) {
		this.operType = operType;
	}

	/**
	 * 查询待审批的流程
	 * @author LiuHao
	 * @time Mar 6, 2012 3:07:44 PM 
	 * @return
	 */
	public String getstayauditFlow(){
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		try {
			this.setPageConfig();
			workFlowstepService.getstayAuditFlow(this.getPages(), Long.valueOf(user.get("id")+""), startTime, endTime,isAlert,operType);
		} catch (NumberFormatException e) {
			addError("查询出错", e);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
	
	public String gotoAlreadyaudit(){
		return "alreadyAudit";
	}
	
	public String getStepTime(){
		try {
			List timeList = workFlowstepService.getStepTime(startDate, endDate, recordSize,pipeId,countType);
			Struts2Utils.renderJson(timeList);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return null;
	}
	
	public String stepTime(){
		return "stepTime";
	}
	public String yetAudit(){
		this.setPageConfig();
		try {
			workFlowstepService.getYetAuditFlow(this.getPages(), workflowId, workflowName, startTime, endTime);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}
