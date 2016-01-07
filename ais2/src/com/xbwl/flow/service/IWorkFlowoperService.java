package com.xbwl.flow.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowWorkflowoperators;
import com.xbwl.entity.FlowWorkflowstep;

/**
 * ���̲�������Ϣ �����ӿ�
 *@author LiuHao
 *@time Feb 24, 2012 5:23:14 PM
 */
public interface IWorkFlowoperService extends IBaseService<FlowWorkflowoperators,Long> {
	/**
	 * ɾ����ǰ�����ߵĲ�����Ϣ
	 * @author LiuHao
	 * @time Mar 2, 2012 4:30:07 PM 
	 * @param curUserId
	 * @param pipeId
	 * @throws Exception
	 */
	public void delCurOprMsg(Long curUserId,Long pipeId)throws Exception;
	/**
	 * ��ȡ��������Ϣ
	 * @author LiuHao
	 * @time May 25, 2012 10:25:08 AM 
	 * @param workflowId
	 * @param stepId
	 * @return
	 * @throws Exception
	 */
	public boolean getByStep(Long workflowId,List<FlowWorkflowstep> stepList)throws Exception;

}
