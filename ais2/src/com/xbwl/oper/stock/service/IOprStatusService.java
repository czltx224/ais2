package com.xbwl.oper.stock.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprStatus;

/**
 * @author CaoZhili time Jul 6, 2011 2:54:38 PM
 * ״̬���������ӿ�
 */
public interface IOprStatusService extends IBaseService<OprStatus, Long> {
	/**
	 * ���������Ų�ѯ����״̬��Ϣ
	 * @param dno
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public OprStatus findStatusByDno(Long dno) throws Exception;
	
	/**
	 * ��������ʱ��д����״̬������״̬
	 * @param dno 
	 * @throws Exception
	 */
	public void verificationCashStatusByFiPayment(Long dno) throws Exception;
	
	/**
	 * ����������ת�ⷢ��˾�½���˵�ʱ��д����״̬������״̬��
	 * @param dno 
	 * @throws Exception
	 */
	public void verificationCashStatusByFiReceivabledetail(Long dno) throws Exception;
	
	/**
	 * ����������
	 * @param dno
	 * @throws Exception
	 */
	public void revocationCashStatus(Long dno) throws Exception;
}

