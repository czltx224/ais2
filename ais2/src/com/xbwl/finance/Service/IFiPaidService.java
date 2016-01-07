package com.xbwl.finance.Service;

import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiPaid;

public interface IFiPaidService extends IBaseService<FiPaid,Long> {
	
	/**
	* @Title: �����ա���� 
	 */
	public String revocation(Long fiPaidId) throws Exception;
	
	/**
	 * 
	* @Title: ����ʵ��ʵ������ 
	* @param @param map
	* @throws
	 */
	public void verificationById(Long id,User user) throws Exception;
	
	/**
	 * �������
	 * @param ids ���ID�б�
	 * @param user ������
	 * @throws Exception
	 */
	public void paymentVerification(String ids,User user) throws Exception;
	
	/**
	 * ��ѯ�ʽ��Ͻ��ܽ��
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map searchHandInAmount(Map map) throws Exception;
	
	/**
	 * �ʽ��Ͻ�������
	 * @param map
	 * @param user
	 * @throws Exception
	 */
	public void handInConfirmation(Map map,User user) throws Exception;
	
	/**
	 * �������������ձ���
	 * @param departId ��������
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @param seq ���㱨�����κ�
	 * @throws Exception
	 */
	public void addAccountSingle(Long departId,String startTime,String endTime,Long seq) throws Exception;
	
	/**
	 * ��ѯ��������������ϸ
	 * @param page
	 * @param map
	 * @return
	 */
	public Page findAccountSingle(Page page,Map map);
}
