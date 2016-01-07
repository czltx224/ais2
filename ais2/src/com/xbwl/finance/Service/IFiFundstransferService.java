package com.xbwl.finance.Service;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiFundstransfer;

public interface IFiFundstransferService extends IBaseService<FiFundstransfer,Long> {

	public String getFundstransferNo () throws Exception;
	
	/**
	 * �����ʽ𽻽ӵ�,���ա����˺������д���
	 * @param fiFundstransfer
	 * @throws Exception
	 */
	//public void accountHandover(FiFundstransfer fiFundstransfer) throws Exception;
	
	/**
	 * ����ȷ��
	 * @param id ���ӵ�ID
	 * @param user
	 * @throws Exception
	 */
	public void paymentConfirmation(Long id,User user) throws Exception;
	
	/**
	 * ��������ȷ��
	 * @param id
	 * @param user
	 * @throws Exception
	 */
	public void paymentRevoke(Long id,User user) throws Exception;
	
	/**
	 * �տ�ȷ��
	 * @param id ���ӵ�ID
	 * @param user
	 * @throws Exception
	 */
	public void receivablesConfirmation(Long id,User user) throws Exception;
	
	/**
	 * �����ʽ𽻽ӵ�
	 * @param id �ʽ𽻽ӵ�ID
	 * @throws Exception
	 */
	public void revocation(Long id) throws Exception;
	
}
