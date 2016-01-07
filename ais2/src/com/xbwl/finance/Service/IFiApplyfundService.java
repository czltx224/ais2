package com.xbwl.finance.Service;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiApplyfund;

public interface IFiApplyfundService extends IBaseService<FiApplyfund,Long> {
	
	/**
	 * �����ʽ����뵥
	 * @param fiApplyfund
	 * @throws Exception
	 */
	public void saveApplyfund(FiApplyfund fiApplyfund) throws Exception;
	
	/**
	 * �����ʽ����뵥
	 * @param fiApplyfund
	 * @throws Exception
	 */
	public void invalidApplyfund(FiApplyfund fiApplyfund) throws Exception;
	
	/**
	 * ����ʽ����뵥
	 * @param fiApplyfund
	 * @throws Exception
	 */
	public void auditApplyfund(FiApplyfund fiApplyfund) throws Exception;
	
	/**
	 * �����ʽ���֧��
	 * @param fiApplyfund
	 * @throws Exception
	 */
	public void fundstransferSit(FiApplyfund fiApplyfund) throws Exception;
	
	/**
	 * �տ�ȷ��(������)
	 * @param id �ʽ����뵥ID
	 * @param user
	 * @throws Exception
	 */
	public void receivablesConfirmation(Long id,User user) throws Exception;
	

}
