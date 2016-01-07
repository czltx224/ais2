package com.xbwl.flow.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowRalarule;

/**
 * ���̹��� ���ݷ��ʲ�ӿ�
 *@author LiuHao
 *@time Feb 21, 2012 8:56:02 AM
 */
public interface IFlowRalaruleService extends IBaseService<FlowRalarule,Long> {
	/**
	 * ���ݽڵ�ID��ѯ�����û�
	 * @author LiuHao
	 * @time Mar 1, 2012 5:37:28 PM 
	 * @param nodeId
	 * @param pipeId
	 * @return Map<Long,Long> userId,ralaId
	 * @throws Exception
	 */
	public Map<Long,Long> getRalaByNodeId(Long nodeId,Long pipeId,Long workflowId)throws Exception;
}
