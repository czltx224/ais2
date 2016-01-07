package com.xbwl.oper.stock.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprSign;

/**
 * author shuw
 * time 2011-7-14 ����11:02:32
 */

public interface IOprSignService extends IBaseService<OprSign,Long>{
	
	/**
	 * �������ǩ��
	 * @param oprSign
	 * @param storeFee  ����
	 * @param consigneeRate ���ͷ���
	 * @param consigneeFee  �������ͷ�
	 * @param totalCpValueAddFee  �ܵ���ֵ��
	 * @throws Exception
	 */
	public void saveSignStatusByFaxIn(OprSign oprSign,
			Double storeFee,Double  consigneeRate,Double  consigneeFee,Double totalCpValueAddFee) throws Exception;
	/**
	 * ǩ������
	 * @param dnos ���͵�������
	 * @throws Exception
	 */
	public void delSign(String[] dnos) throws Exception;

	/**
	 * ��ʱ���񱣴浽ǩ�ձ���
	 * @param sign
	 * @throws Exception
	 */
	public void saveTask(OprSign sign)throws Exception;
}
