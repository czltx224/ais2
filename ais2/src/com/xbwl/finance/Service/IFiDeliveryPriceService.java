package com.xbwl.finance.Service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiDeliveryPrice;

/**
 * author shuw
 * time Oct 10, 2011 3:57:36 PM
 */

public interface IFiDeliveryPriceService extends  IBaseService<FiDeliveryPrice,Long>{

	/**
	 * ��ɾ����۸�
	 * @param list
	 * @throws Exception
	 */
	public void deleteOfStatus(List<Long> list) throws Exception;
	
	/**
	 * ���ݿ��̲�ѯЭ���
	 * @param customerId
	 * @param goodsType
	 * @return
	 * @throws Exception
	 */
	public List<FiDeliveryPrice> getPriceByCustomerId(Long customerId,String goodsType) throws Exception;
}
