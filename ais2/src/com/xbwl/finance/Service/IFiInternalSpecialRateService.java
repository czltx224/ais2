package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.entity.FiInternalSpecialRate;

/**
 * author CaoZhili time Oct 20, 2011 10:39:36 AM
 * �ڲ�����ͻ�Э������÷����ӿ�
 */

public interface IFiInternalSpecialRateService extends
		IBaseService<FiInternalSpecialRate, Long> {

	/**�ڲ�����Э������Ϸ���
	 * @param strids Ҫ���ϵ�ID����
	 * @param status ����״̬
	 * @throws Exception
	 */
	public void invalidService(String[] strids, Long status)throws Exception;
	
	/**
	 * ����Э��ۼ���
	 * @param customerId ����ID
	 * @param flightWeight ����
	 * @param flightPiece ����
	 * @param bulk ���
	 * @return
	 * @throws Exception
	 */
	public FiInternalDetail calculateCost(Long customerId,Double flightWeight,Long flightPiece,Double bulk)throws Exception;

}
