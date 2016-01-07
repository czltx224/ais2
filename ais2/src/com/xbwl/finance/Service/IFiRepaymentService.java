package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiRepayment;

public interface IFiRepaymentService extends IBaseService<FiRepayment,Long> {
	
	/**
	 * ������ϸ����
	 * @param ids ����˱�ID
	 * @throws Exception
	 */
	public void confirmAccountSingle(String ids) throws Exception;
}
