package com.xbwl.flow.service;

import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowWorkflowlog;

/**
 * 流程日志信息 服务层接口
 *@author LiuHao
 *@time Feb 24, 2012 5:23:14 PM
 */
public interface IWorkFlowlogService extends IBaseService<FlowWorkflowlog,Long> {
	/**
	 * 查询提交流程节点的日志信息
	 * @author LiuHao
	 * @time Feb 25, 2012 3:49:27 PM 
	 * @param nodeId
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public List getOperatorListPost(Long nodeId,Long workflowId)throws Exception;
	/**
	 * 获得当前流程节点的日志信息
	 * @author LiuHao
	 * @time Feb 25, 2012 3:56:14 PM 
	 * @param nodeId
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public List getOperatorListCur(Long nodeId,Long workflowId)throws Exception;
	/**
	 * 根据流程ID，节点ID，查询流程日志
	 * @author LiuHao
	 * @time Mar 8, 2012 9:38:17 AM 
	 * @return
	 * @throws Exception
	 */
	public List<FlowWorkflowlog> getFlowlog(Long workflowId,Long nodeId)throws Exception;
	/**
	 * 查询流程日志信息
	 * @author LiuHao
	 * @time Apr 17, 2012 4:07:27 PM 
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public Page getFlowLogByWid(Page page,Long workflowId) throws Exception;
}
