package com.xbwl.flow.service;

import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowWorkflowlog;

/**
 * ������־��Ϣ �����ӿ�
 *@author LiuHao
 *@time Feb 24, 2012 5:23:14 PM
 */
public interface IWorkFlowlogService extends IBaseService<FlowWorkflowlog,Long> {
	/**
	 * ��ѯ�ύ���̽ڵ����־��Ϣ
	 * @author LiuHao
	 * @time Feb 25, 2012 3:49:27 PM 
	 * @param nodeId
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public List getOperatorListPost(Long nodeId,Long workflowId)throws Exception;
	/**
	 * ��õ�ǰ���̽ڵ����־��Ϣ
	 * @author LiuHao
	 * @time Feb 25, 2012 3:56:14 PM 
	 * @param nodeId
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public List getOperatorListCur(Long nodeId,Long workflowId)throws Exception;
	/**
	 * ��������ID���ڵ�ID����ѯ������־
	 * @author LiuHao
	 * @time Mar 8, 2012 9:38:17 AM 
	 * @return
	 * @throws Exception
	 */
	public List<FlowWorkflowlog> getFlowlog(Long workflowId,Long nodeId)throws Exception;
	/**
	 * ��ѯ������־��Ϣ
	 * @author LiuHao
	 * @time Apr 17, 2012 4:07:27 PM 
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public Page getFlowLogByWid(Page page,Long workflowId) throws Exception;
}
