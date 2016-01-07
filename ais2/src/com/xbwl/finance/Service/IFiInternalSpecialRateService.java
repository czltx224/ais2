package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.entity.FiInternalSpecialRate;

/**
 * author CaoZhili time Oct 20, 2011 10:39:36 AM
 * 内部特殊客户协议价设置服务层接口
 */

public interface IFiInternalSpecialRateService extends
		IBaseService<FiInternalSpecialRate, Long> {

	/**内部特殊协议价作废方法
	 * @param strids 要作废的ID数组
	 * @param status 作废状态
	 * @throws Exception
	 */
	public void invalidService(String[] strids, Long status)throws Exception;
	
	/**
	 * 特殊协议价计算
	 * @param customerId 客商ID
	 * @param flightWeight 重量
	 * @param flightPiece 件数
	 * @param bulk 体积
	 * @return
	 * @throws Exception
	 */
	public FiInternalDetail calculateCost(Long customerId,Double flightWeight,Long flightPiece,Double bulk)throws Exception;

}
