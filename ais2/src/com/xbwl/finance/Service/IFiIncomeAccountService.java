package com.xbwl.finance.Service;

import java.util.Date;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiIncomeAccount;

public interface IFiIncomeAccountService extends IBaseService<FiIncomeAccount,Long> {
	
	/**
	 * 生成交账单
	 * @param departId 所属业务部门
	 * @param accountData 业务日期
	 * @throws Exception
	 */
	public void addAccountSingle(Long departId,Date accountData) throws Exception;
	
	/**
	 * 作废交账单号
	 * @param batchNo 交账单号
	 * @throws Exception
	 */
	public void revocation(Long batchNo) throws Exception;
	
	/**
	 * 审核交账单
	 * @param batchNo 交账单号
	 * @throws Exception
	 */
	public void confirmAccountSingle(Long batchNo) throws Exception;
}
