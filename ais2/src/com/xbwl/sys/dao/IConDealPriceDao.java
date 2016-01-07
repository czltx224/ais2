package com.xbwl.sys.dao;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.entity.ConsigneeDealPrice;

/**
 * �ջ��˴���Э��۸�
 *@author LiuHao
 *@time Jun 27, 2011 6:42:06 PM
 */
public interface IConDealPriceDao extends IBaseDAO<ConsigneeDealPrice,Long> {
	/**
	 * ��ѯ�ջ���Э��۸�
	 * @param page
	 * @param tels
	 * @return
	 * @throws Exception
	 */
	public Page<ConsigneeDealPrice> findConDealPrice(Page page,String cusId,String[] tels)throws Exception;
}
