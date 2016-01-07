package com.xbwl.finance.Service;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasSpecialTrainRate;

/**
 *@author LiuHao
 *@time 2011-7-22 上午10:25:07
 */
public interface ISpecialTrainRateService extends IBaseService<BasSpecialTrainRate, Long> {
	/**
	 * 查询代理专车价格
	 * @param page
	 * @param cusId
	 * @param roadType
	 * @param town
	 * @param street
	 * @return
	 * @throws Exception
	 */
	public Page findSpecialTrainRate(Page page,Long cusId,String roadType,String town,String street,Long departId,String city)throws Exception;
}
