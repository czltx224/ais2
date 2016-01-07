package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiPaymentabnormal;
import com.xbwl.entity.FiProblemreceivable;

public interface IFiPaymentabnormalService extends IBaseService<FiPaymentabnormal,Long> {

	/**
	 * (�쳣������Ǽǣ�����) 
	 * @param fiPaymentabnormal
	 * @return 
	 */
	public String saveException(FiPaymentabnormal fiPaymentabnormal)throws Exception;
	
	/**
	 * (�쳣������-�����Ǽ�) 
	 * @param fiPaymentabnormal
	 * @return 
	 */
	public void revocationException(FiPaymentabnormal fiPaymentabnormal)throws Exception ;
	
	/**
	 * �쳣���������
	 * @param fiPaymentabnormal
	 * @return
	 */
	public void verificationException(FiPaymentabnormal fiPaymentabnormal) throws Exception;
	
	/**
	 * �쳣����������
	 * @param fiPaymentabnormal
	 * @throws Exception
	 */
	public void verificationRegister(FiPaymentabnormal fiPaymentabnormal) throws Exception;
	
	/**
	 * �쳣���������
	 * @param fiPayment
	 * @throws Exception
	 */
	public void verfiPaymentabnormal(FiPayment fiPayment) throws Exception;
	
	/**
	 * �����쳣���������
	 * @param amount
	 * @param fiPaymentabnormalId
	 * @throws Exception
	 */
	public void verfiPaymentabnormalRegister(Double amount,Long fiPaymentabnormalId) throws Exception;

}
