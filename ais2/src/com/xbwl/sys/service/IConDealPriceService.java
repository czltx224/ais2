package com.xbwl.sys.service;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.ConsigneeDealPrice;

/**
 *@author LiuHao
 *@time Jun 27, 2011 6:48:26 PM
 */
public interface IConDealPriceService extends IBaseService<ConsigneeDealPrice,Long> {
	/**
	 * ����ջ���Э��۸�
	 * @param page
	 * @param tels
	 * @return
	 * @throws Exception
	 */
	public Page<ConsigneeDealPrice> findConDealPrice(Page page,String cusName,String[] tels)throws Exception;
}
