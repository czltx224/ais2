package com.xbwl.cus.service;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusGoodsRank;

/**
 *@author LiuHao
 *@time May 18, 2012 10:05:53 AM
 */
public interface ICusGoodsRankService extends IBaseService<CusGoodsRank,Long> {
	/**
	 * 查询所有等级
	 * @author LiuHao
	 * @time May 18, 2012 1:45:22 PM 
	 * @return
	 * @throws Exception
	 */
	public Page findAllRank(Page page)throws Exception;
}
