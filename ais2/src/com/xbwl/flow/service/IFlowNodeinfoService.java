package com.xbwl.flow.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowNodeinfo;

/**
 * �ڵ���� �����ӿ�
 *@author LiuHao
 *@time Feb 17, 2012 4:09:59 PM
 */
public interface IFlowNodeinfoService extends IBaseService<FlowNodeinfo,Long> {
	/**
	 * ��������ID��ѯ�ڵ���Ϣ
	 * @author LiuHao
	 * @time Feb 22, 2012 4:07:18 PM 
	 * @param pipeId
	 * @return
	 * @throws Exception
	 */
	public List<FlowNodeinfo> getNodeByPipeid(Long pipeId)throws Exception;
	/**
	 * ��ѯ��ʼ���߽����ڵ�
	 * @author LiuHao
	 * @time Feb 22, 2012 5:46:34 PM 
	 * @param pipeId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public FlowNodeinfo getSEnodeinfoByPipeId(Long pipeId,String type) throws Exception;
}
