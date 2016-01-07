package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiRepayment;

public interface IFiRepaymentService extends IBaseService<FiRepayment,Long> {
	
	/**
	 * 还款明细交账
	 * @param ids 还款交账表ID
	 * @throws Exception
	 */
	public void confirmAccountSingle(String ids) throws Exception;
}
