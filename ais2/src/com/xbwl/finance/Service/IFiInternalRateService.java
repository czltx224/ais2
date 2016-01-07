package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.entity.FiInternalRate;

/**
 * author CaoZhili
 * time Oct 19, 2011 5:21:34 PM
 * �ڲ�����Э��۷����ӿ�
 */

public interface IFiInternalRateService  extends IBaseService<FiInternalRate, Long>{

	/**�ڲ�Э������Ϸ���
	 * @param strids Ҫ���ϵ�ID����
	 * @param status ���ϵ�״̬
	 * @throws Exception
	 */
	public void invalidService(String[] strids, Long status)throws Exception;
	
	/**
	 * �����ڲ��ɱ����
	 * @param startDepartId ʼ������
	 * @param endDepartId ���ﲿ��
	 * @param dno ���͵���
	 * @param flightWeight ����
	 * @param outStockMode ���ͷ�ʽ
	 * @return
	 * @throws Exception
	 */
	public FiInternalDetail calculateCost(Long startDepartId,Long endDepartId,Long dno,Double flightWeight,String outStockMode)throws Exception;

}
