package com.xbwl.flow.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowWorkflowbase;
import com.xbwl.flow.vo.FlowSaveVo;

/**
 * 流程服务层接口
 *@author LiuHao
 *@time Feb 24, 2012 4:32:07 PM
 */
public interface IWorkFlowbaseService extends IBaseService<FlowWorkflowbase,Long> {
	/**
	 * 查询流程是否归档或者删除
	 * @author LiuHao
	 * @time Feb 25, 2012 3:40:15 PM 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean isFinishOrDelete(Long id)throws Exception;
	/**
	 * 获取下一个节点所有的审批人集合
	 * @author LiuHao
	 * @time Mar 1, 2012 4:09:00 PM 
	 * @param curNodeId
	 * @param pipeId
	 * @return
	 * @throws Exception
	 */
	public Map<Long ,Long> getNextUsers(Long curNodeId,Long pipeId,Long workflowId)throws Exception;
	/**
	 * 提交流程
	 * @author LiuHao
	 * @time Mar 25, 2012 10:47:33 AM 
	 * @param workId 流程ID
	 * @param pipeId 管道ID
	 * @param nodeId 节点ID
	 * @param oprType 操作类型
	 * @param logType 审批类型
	 * @param auditRemark 审批备注
	 * @param returnNodeid 退回节点ID
	 * @param dno 配送单号
	 * @param applyRemark 提交备注
	 * @param formDetailStr 表单明细字符串
	 * @throws Exception
	 */
	public void flowSubmit(FlowSaveVo saveVo)throws Exception;
	/**
	 * 流程转发
	 * @author LiuHao
	 * @time May 2, 2012 1:57:16 PM 
	 * @throws Exception
	 */
	public void flowSend(Long userId,Long workflowId,Long nodeId)throws Exception;
	
	public void flowControl(FlowSaveVo saveVo)throws Exception;

}
