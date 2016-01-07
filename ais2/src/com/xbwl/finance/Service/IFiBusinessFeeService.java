package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiBusinessFee;

/**
 * author CaoZhili
 * time Oct 17, 2011 3:38:13 PM
 * ҵ��ѹ�������ӿ� 
 */

public interface IFiBusinessFeeService extends IBaseService<FiBusinessFee, Long> {

	/**ҵ��ѹ���״̬�޸ķ���
	 * @param idStrings Ҫ�޸ĵ�ҵ���ID����
	 * @param status Ҫ�޸ĵ�״̬
	 * @param workflowNo ���̺�
	 * @param amount ҵ���
	 * @throws Exception ������쳣
	 */
	public void auditStatusService(String[] idStrings,Long status,String workflowNo,Double amount)throws Exception;

}
