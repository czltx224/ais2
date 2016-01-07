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
	 * 软删提货价格
	 * @param list
	 * @throws Exception
	 */
	public void deleteOfStatus(List<Long> list) throws Exception;
	
	/**
	 * 根据客商查询协议价
	 * @param customerId
	 * @param goodsType
	 * @return
	 * @throws Exception
	 */
	public List<FiDeliveryPrice> getPriceByCustomerId(Long customerId,String goodsType) throws Exception;
}
