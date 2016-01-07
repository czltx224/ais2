package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiAdvance;

/**
 * @author Administrator
 *预付款账号结算清单服务层接口
 */
public interface IFiAdvanceService extends IBaseService<FiAdvance,Long> {
	
	/**
	 * 清单保存
	 * @param fiAdvance
	 * @throws Exception
	 */
	public void saveFiAdvance(FiAdvance fiAdvance) throws Exception;
	
	/**
	 * 清单删除 
	 * @param id
	 * @param fiAdvancesetId
	 * @throws Exception
	 */
	public void deleteStatus(Long id,Long fiAdvancesetId) throws Exception;
}
