package com.xbwl.flow.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowWorkflowinfo;

/**
 * ������ת��Ϣ �����ӿ�
 *@author LiuHao
 *@time Feb 24, 2012 5:23:14 PM
 */
public interface IWorkFlowinfoService extends IBaseService<FlowWorkflowinfo,Long> {

	/**
	 * ���浱ǰ������Ϣ
	 * @author LiuHao
	 * @time Mar 8, 2012 11:53:11 AM 
	 * @param curStepid
	 * @param workflowId
	 * @param logType
	 * @throws Exception
	 */
	public void saveCurFlowinfo(Long curStepid,Long workflowId,Long logType)throws Exception;
}
