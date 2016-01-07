package com.xbwl.flow.service;

import java.util.Date;
import java.util.List;

import org.jdom.Document;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowWorkflowstep;

/**
 * 流程步骤信息 服务层接口
 *@author LiuHao
 *@time Feb 24, 2012 5:23:14 PM
 */
public interface IWorkFlowstepService extends IBaseService<FlowWorkflowstep,Long> {
	/**
	 * 获得流程步骤的XML格式
	 * @author LiuHao
	 * @time Feb 25, 2012 3:22:29 PM 
	 * @param pipeId
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public Document getStepXml(Long pipeId,Long workflowId)throws Exception;
	/**
	 * 根据节点ID查询流程步骤信息
	 * @author LiuHao
	 * @time Mar 2, 2012 9:27:41 AM 
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public List<FlowWorkflowstep> getStepByNodeId(Long nodeId,Long workflowId)throws Exception;
	/**
	 * 获得上一个步骤信息
	 * @author LiuHao
	 * @time Mar 2, 2012 2:49:00 PM 
	 * @param pipeId
	 * @param nodeId
	 * @param userId
	 * @throws Exception
	 */
	public List<FlowWorkflowstep> getLastStep(Long pipeId,Long nodeId,Long userId)throws Exception;
	/**
	 * 查询待审批的流程
	 * @author LiuHao
	 * @time Mar 5, 2012 4:26:34 PM 
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 * operType 1 审批 2、知会、3 审核、4 知会
	 */
	public Page getstayAuditFlow(Page page,Long userId,Date startTime,Date endTime,String isAlert,Long operType)throws Exception;
	/**
	 * 查询为提交的步骤信息
	 * @author LiuHao
	 * @time Mar 8, 2012 2:11:03 PM 
	 * @return
	 * @throws Exception
	 */
	public List<FlowWorkflowstep> getStepByNosubmit(Long workflowId)throws Exception;
	/**
	 * 查询开始节点的步骤信息
	 * @author LiuHao
	 * @time Apr 10, 2012 9:18:48 AM 
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public FlowWorkflowstep getStepByStartNode(Long workflowId)throws Exception;
	/**
	 * 审批时效统计
	 * @author LiuHao
	 * @time Apr 21, 2012 1:44:07 PM 
	 * @param startDate
	 * @param endDate
	 * @param sort
	 * @param pageSize
	 * @return
	 */
	public List getStepTime(Date startDate,Date endDate,int pageSize,Long pipeId,String countType)throws Exception;
	/**
	 * 查询已经审批的流程信息
	 * @author LiuHao
	 * @time May 2, 2012 3:45:51 PM 
	 * @param page
	 * @param workId
	 * @param workName
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public Page getYetAuditFlow(Page page,Long workId,String workName,Date startTime,Date endTime)throws Exception;
}
