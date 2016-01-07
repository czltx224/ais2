package com.xbwl.finance.Service;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiProblemreceivable;

public interface IFiProblemreceivableService extends IBaseService<FiProblemreceivable,Long> {

	/**
	 * ���������˿�Ǽ�
	* @Title: revocationRegister 
	* @param     �����˿�ʵ��
	* @throws
	 */
	public void revocationRegister(FiProblemreceivable fiProblemreceivable) throws Exception;
	
	/**
	 * �����˿����(����Ӧ���)
	 * @param fiProblemreceivable
	 * @throws ServiceException
	 */
	public void audit(FiProblemreceivable fiProblemreceivable) throws Exception;
	
	/**
	 * �����˿�����
	 * @param fiProblemreceivable
	 * @throws Exception
	 */
	public void problemreceivableRegister(FiProblemreceivable fiProblemreceivable) throws Exception;
	
	/**
	 * �����˿����
	 * @param amount �������
	 * @param fiProblemreceivableId �����˿�ID
	 * @throws Exception
	 */
	public void verfiProblemreceivable(Double amount,Long fiProblemreceivableId) throws Exception;
	
	/**
	 * ���������˿����
	 * @param amount �������
	 * @param fiProblemreceivableId
	 * @throws Exception
	 */
	public void verfiProblemreceivableRegister(Double amount,Long fiProblemreceivableId) throws Exception;
	
}
