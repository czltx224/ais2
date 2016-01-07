package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiAdvancebp;

public interface IFiAdvancebpService extends IBaseService<FiAdvancebp,Long> {
	
	/**
	 * ���ȷ��
	 * @param fiAdvancebp
	 * @throws Exception
	 */
	public void reviewConfirmation(FiAdvancebp fiAdvancebp) throws Exception;
	
	/**
	 * �������
	 * @param fiAdvancebp
	 * @throws Exception
	 */
	public void reviewRegister(FiAdvancebp fiAdvancebp) throws Exception;
	
	/**
	 * ��������
	 * @param fiAdvancebp
	 * @throws Exception
	 */
	public void addRegister(FiAdvancebp fiAdvancebp) throws Exception;
	
	/**
	 * ����ʱ��д����״̬
	 * @param amountȫ�����ո����
	 * @param fiAdvancebpId
	 * @throws Exception
	 */
	public void verfiFiAdvancebp(Double amount,Long fiAdvancebpId) throws Exception;
	
	/**
	 * ��������ʱ��д����״̬
	 * @param fiAdvancebpId
	 * @throws Exception
	 */
	public void verfiFiAdvancebpRegister(Long fiAdvancebpId) throws Exception;

}
