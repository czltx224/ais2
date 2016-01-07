package com.xbwl.flow.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowWorkflowinfo;

/**
 * 流程流转信息 服务层接口
 *@author LiuHao
 *@time Feb 24, 2012 5:23:14 PM
 */
public interface IWorkFlowinfoService extends IBaseService<FlowWorkflowinfo,Long> {

	/**
	 * 保存当前流程信息
	 * @author LiuHao
	 * @time Mar 8, 2012 11:53:11 AM 
	 * @param curStepid
	 * @param workflowId
	 * @param logType
	 * @throws Exception
	 */
	public void saveCurFlowinfo(Long curStepid,Long workflowId,Long logType)throws Exception;
}
