package com.xbwl.finance.Service;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiFundstransfer;

public interface IFiFundstransferService extends IBaseService<FiFundstransfer,Long> {

	public String getFundstransferNo () throws Exception;
	
	/**
	 * 根据资金交接单,对收、付账号余额进行处理。
	 * @param fiFundstransfer
	 * @throws Exception
	 */
	//public void accountHandover(FiFundstransfer fiFundstransfer) throws Exception;
	
	/**
	 * 付款确认
	 * @param id 交接单ID
	 * @param user
	 * @throws Exception
	 */
	public void paymentConfirmation(Long id,User user) throws Exception;
	
	/**
	 * 撤销付款确认
	 * @param id
	 * @param user
	 * @throws Exception
	 */
	public void paymentRevoke(Long id,User user) throws Exception;
	
	/**
	 * 收款确认
	 * @param id 交接单ID
	 * @param user
	 * @throws Exception
	 */
	public void receivablesConfirmation(Long id,User user) throws Exception;
	
	/**
	 * 作废资金交接单
	 * @param id 资金交接单ID
	 * @throws Exception
	 */
	public void revocation(Long id) throws Exception;
	
}
