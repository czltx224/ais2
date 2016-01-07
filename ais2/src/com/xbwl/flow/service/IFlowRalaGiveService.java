package com.xbwl.flow.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowRalaGive;

/**
 *@author LiuHao
 *@time Apr 19, 2012 4:15:17 PM
 */
public interface IFlowRalaGiveService extends IBaseService<FlowRalaGive,Long> {
	/**
	 * ���ݹܵ�ID��ѯ������Ȩ��Ϣ
	 * @author LiuHao
	 * @time Apr 20, 2012 10:01:41 AM 
	 * @param pipeId
	 * @return
	 * @throws Exception
	 */
	public FlowRalaGive getRalaByPipeId(Long pipeId,Long userId)throws Exception;
}
