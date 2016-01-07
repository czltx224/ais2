package com.xbwl.finance.Service;

import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasStSpecialTrainRate;

/**
 *@author LiuHao
 *@time 2011-7-22 上午10:35:37
 */
public interface IStSpecialTrainRateService extends IBaseService<BasStSpecialTrainRate, Long> {
	/**
	 * 查询专车 代理协议价格
	 * @param page
	 * @param roadType
	 * @param town
	 * @param street
	 * @return
	 * @throws Exception
	 */
	public Page findStSpecialTrainRate(Page page,String roadType,String town,String street,Long departId,String city)throws Exception;
	/**
	 * 打折生成专车协议价
	 * @param stId
	 * @param cusId
	 * @param rebate
	 * @throws Exception
	 */
	public void discountSpeRate(List stId,Long cusId,String cpName,Double rebate)throws Exception;
}
