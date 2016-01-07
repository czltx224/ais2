package com.xbwl.finance.Service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiBusinessFeePrice;

/**
 * author shuw
 * time Dec 26, 2011 10:11:31 AM
 * 业务费协议价管理
 */
public interface IFiBusinessFeePriceService extends IBaseService<FiBusinessFeePrice, Long> {

	/**
	 * 审核多条数据
	 * @param aa  集合
	 * @throws Exception
	 */
	public void audit(List<FiBusinessFeePrice> aa) throws Exception;
	
	/**
	 * 删除业务费协议价（状态删除）
	 * @param list （ID集合）
	 * @throws Exception
	 */
	public void deleteByStatus(List<Long> list)throws Exception;
}
