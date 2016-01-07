package com.xbwl.flow.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowWorkflowbase;
import com.xbwl.flow.vo.FlowSaveVo;

/**
 * ���̷����ӿ�
 *@author LiuHao
 *@time Feb 24, 2012 4:32:07 PM
 */
public interface IWorkFlowbaseService extends IBaseService<FlowWorkflowbase,Long> {
	/**
	 * ��ѯ�����Ƿ�鵵����ɾ��
	 * @author LiuHao
	 * @time Feb 25, 2012 3:40:15 PM 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean isFinishOrDelete(Long id)throws Exception;
	/**
	 * ��ȡ��һ���ڵ����е������˼���
	 * @author LiuHao
	 * @time Mar 1, 2012 4:09:00 PM 
	 * @param curNodeId
	 * @param pipeId
	 * @return
	 * @throws Exception
	 */
	public Map<Long ,Long> getNextUsers(Long curNodeId,Long pipeId,Long workflowId)throws Exception;
	/**
	 * �ύ����
	 * @author LiuHao
	 * @time Mar 25, 2012 10:47:33 AM 
	 * @param workId ����ID
	 * @param pipeId �ܵ�ID
	 * @param nodeId �ڵ�ID
	 * @param oprType ��������
	 * @param logType ��������
	 * @param auditRemark ������ע
	 * @param returnNodeid �˻ؽڵ�ID
	 * @param dno ���͵���
	 * @param applyRemark �ύ��ע
	 * @param formDetailStr ����ϸ�ַ���
	 * @throws Exception
	 */
	public void flowSubmit(FlowSaveVo saveVo)throws Exception;
	/**
	 * ����ת��
	 * @author LiuHao
	 * @time May 2, 2012 1:57:16 PM 
	 * @throws Exception
	 */
	public void flowSend(Long userId,Long workflowId,Long nodeId)throws Exception;
	
	public void flowControl(FlowSaveVo saveVo)throws Exception;

}
