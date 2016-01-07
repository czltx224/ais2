package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiPaymentabnormal;
import com.xbwl.entity.FiProblemreceivable;

public interface IFiPaymentabnormalService extends IBaseService<FiPaymentabnormal,Long> {

	/**
	 * (异常到付款登记，保存) 
	 * @param fiPaymentabnormal
	 * @return 
	 */
	public String saveException(FiPaymentabnormal fiPaymentabnormal)throws Exception;
	
	/**
	 * (异常到付款-撤销登记) 
	 * @param fiPaymentabnormal
	 * @return 
	 */
	public void revocationException(FiPaymentabnormal fiPaymentabnormal)throws Exception ;
	
	/**
	 * 异常到付款审核
	 * @param fiPaymentabnormal
	 * @return
	 */
	public void verificationException(FiPaymentabnormal fiPaymentabnormal) throws Exception;
	
	/**
	 * 异常到付款撤销审核
	 * @param fiPaymentabnormal
	 * @throws Exception
	 */
	public void verificationRegister(FiPaymentabnormal fiPaymentabnormal) throws Exception;
	
	/**
	 * 异常到付款核销
	 * @param fiPayment
	 * @throws Exception
	 */
	public void verfiPaymentabnormal(FiPayment fiPayment) throws Exception;
	
	/**
	 * 撤销异常到付款核销
	 * @param amount
	 * @param fiPaymentabnormalId
	 * @throws Exception
	 */
	public void verfiPaymentabnormalRegister(Double amount,Long fiPaymentabnormalId) throws Exception;

}
