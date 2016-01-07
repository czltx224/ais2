package com.xbwl.flow.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowRalarule;

/**
 * 流程管理 数据访问层接口
 *@author LiuHao
 *@time Feb 21, 2012 8:56:02 AM
 */
public interface IFlowRalaruleService extends IBaseService<FlowRalarule,Long> {
	/**
	 * 根据节点ID查询审批用户
	 * @author LiuHao
	 * @time Mar 1, 2012 5:37:28 PM 
	 * @param nodeId
	 * @param pipeId
	 * @return Map<Long,Long> userId,ralaId
	 * @throws Exception
	 */
	public Map<Long,Long> getRalaByNodeId(Long nodeId,Long pipeId,Long workflowId)throws Exception;
}
