package com.xbwl.cus.service;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusProfit;

/**
 *@author LiuHao
 *@time Oct 20, 2011 2:03:50 PM
 */
public interface ICusProfitService extends IBaseService<CusProfit,Long> {
	/**
	 * Ó¯Àû·ÖÎö
	 * @param date
	 * @param cusId
	 * @return
	 * @throws Exception
	 */
	public Page findProfitMsg(Page page,String startCount,String endCount,String countRange,Long cusId)throws Exception;
}
