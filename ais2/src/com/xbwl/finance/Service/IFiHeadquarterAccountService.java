package com.xbwl.finance.Service;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCashiercollection;
import com.xbwl.entity.FiHeadquarterAccount;

public interface IFiHeadquarterAccountService extends IBaseService<FiHeadquarterAccount,Long> {
	
	/**
	 * �����ܲ��վ�
	 * @param fiHeadquarterAccount
	 * @throws Exception
	 */
	public void verification(FiHeadquarterAccount fiHeadquarterAccount,User user) throws Exception;
	
	/**
	 * ����
	 * @param id
	 * @throws Exception
	 */
	public void revocation(Long id) throws Exception;

}
