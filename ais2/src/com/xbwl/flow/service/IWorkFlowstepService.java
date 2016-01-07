package com.xbwl.flow.service;

import java.util.Date;
import java.util.List;

import org.jdom.Document;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowWorkflowstep;

/**
 * ���̲�����Ϣ �����ӿ�
 *@author LiuHao
 *@time Feb 24, 2012 5:23:14 PM
 */
public interface IWorkFlowstepService extends IBaseService<FlowWorkflowstep,Long> {
	/**
	 * ������̲����XML��ʽ
	 * @author LiuHao
	 * @time Feb 25, 2012 3:22:29 PM 
	 * @param pipeId
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public Document getStepXml(Long pipeId,Long workflowId)throws Exception;
	/**
	 * ���ݽڵ�ID��ѯ���̲�����Ϣ
	 * @author LiuHao
	 * @time Mar 2, 2012 9:27:41 AM 
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public List<FlowWorkflowstep> getStepByNodeId(Long nodeId,Long workflowId)throws Exception;
	/**
	 * �����һ��������Ϣ
	 * @author LiuHao
	 * @time Mar 2, 2012 2:49:00 PM 
	 * @param pipeId
	 * @param nodeId
	 * @param userId
	 * @throws Exception
	 */
	public List<FlowWorkflowstep> getLastStep(Long pipeId,Long nodeId,Long userId)throws Exception;
	/**
	 * ��ѯ������������
	 * @author LiuHao
	 * @time Mar 5, 2012 4:26:34 PM 
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 * operType 1 ���� 2��֪�ᡢ3 ��ˡ�4 ֪��
	 */
	public Page getstayAuditFlow(Page page,Long userId,Date startTime,Date endTime,String isAlert,Long operType)throws Exception;
	/**
	 * ��ѯΪ�ύ�Ĳ�����Ϣ
	 * @author LiuHao
	 * @time Mar 8, 2012 2:11:03 PM 
	 * @return
	 * @throws Exception
	 */
	public List<FlowWorkflowstep> getStepByNosubmit(Long workflowId)throws Exception;
	/**
	 * ��ѯ��ʼ�ڵ�Ĳ�����Ϣ
	 * @author LiuHao
	 * @time Apr 10, 2012 9:18:48 AM 
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public FlowWorkflowstep getStepByStartNode(Long workflowId)throws Exception;
	/**
	 * ����ʱЧͳ��
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
	 * ��ѯ�Ѿ�������������Ϣ
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
