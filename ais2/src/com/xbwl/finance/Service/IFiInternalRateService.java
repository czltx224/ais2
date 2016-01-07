package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.entity.FiInternalRate;

/**
 * author CaoZhili
 * time Oct 19, 2011 5:21:34 PM
 * 内部结算协议价服务层接口
 */

public interface IFiInternalRateService  extends IBaseService<FiInternalRate, Long>{

	/**内部协议价作废方法
	 * @param strids 要作废的ID数组
	 * @param status 作废的状态
	 * @throws Exception
	 */
	public void invalidService(String[] strids, Long status)throws Exception;
	
	/**
	 * 计算内部成本金额
	 * @param startDepartId 始发部门
	 * @param endDepartId 到达部门
	 * @param dno 配送单号
	 * @param flightWeight 重量
	 * @param outStockMode 配送方式
	 * @return
	 * @throws Exception
	 */
	public FiInternalDetail calculateCost(Long startDepartId,Long endDepartId,Long dno,Double flightWeight,String outStockMode)throws Exception;

}
