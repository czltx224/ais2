package com.xbwl.finance.Service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiBusinessFeePrice;

/**
 * author shuw
 * time Dec 26, 2011 10:11:31 AM
 * ҵ���Э��۹���
 */
public interface IFiBusinessFeePriceService extends IBaseService<FiBusinessFeePrice, Long> {

	/**
	 * ��˶�������
	 * @param aa  ����
	 * @throws Exception
	 */
	public void audit(List<FiBusinessFeePrice> aa) throws Exception;
	
	/**
	 * ɾ��ҵ���Э��ۣ�״̬ɾ����
	 * @param list ��ID���ϣ�
	 * @throws Exception
	 */
	public void deleteByStatus(List<Long> list)throws Exception;
}
