package com.xbwl.finance.Service;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiProblemreceivable;

public interface IFiProblemreceivableService extends IBaseService<FiProblemreceivable,Long> {

	/**
	 * 撤销问题账款登记
	* @Title: revocationRegister 
	* @param     问题账款实体
	* @throws
	 */
	public void revocationRegister(FiProblemreceivable fiProblemreceivable) throws Exception;
	
	/**
	 * 问题账款审核(生成应付款单)
	 * @param fiProblemreceivable
	 * @throws ServiceException
	 */
	public void audit(FiProblemreceivable fiProblemreceivable) throws Exception;
	
	/**
	 * 问题账款撤销审核
	 * @param fiProblemreceivable
	 * @throws Exception
	 */
	public void problemreceivableRegister(FiProblemreceivable fiProblemreceivable) throws Exception;
	
	/**
	 * 问题账款核销
	 * @param amount 核销金额
	 * @param fiProblemreceivableId 问题账款ID
	 * @throws Exception
	 */
	public void verfiProblemreceivable(Double amount,Long fiProblemreceivableId) throws Exception;
	
	/**
	 * 撤销问题账款核销
	 * @param amount 撤销金额
	 * @param fiProblemreceivableId
	 * @throws Exception
	 */
	public void verfiProblemreceivableRegister(Double amount,Long fiProblemreceivableId) throws Exception;
	
}
