package com.xbwl.finance.Service;

import java.util.Date;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiIncomeAccount;

public interface IFiIncomeAccountService extends IBaseService<FiIncomeAccount,Long> {
	
	/**
	 * ���ɽ��˵�
	 * @param departId ����ҵ����
	 * @param accountData ҵ������
	 * @throws Exception
	 */
	public void addAccountSingle(Long departId,Date accountData) throws Exception;
	
	/**
	 * ���Ͻ��˵���
	 * @param batchNo ���˵���
	 * @throws Exception
	 */
	public void revocation(Long batchNo) throws Exception;
	
	/**
	 * ��˽��˵�
	 * @param batchNo ���˵���
	 * @throws Exception
	 */
	public void confirmAccountSingle(Long batchNo) throws Exception;
}
