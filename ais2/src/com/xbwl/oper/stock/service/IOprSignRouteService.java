package com.xbwl.oper.stock.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprSignRoute;

/**
 * author shuw
 * time Aug 2, 2011 1:51:12 PM
 */

public interface IOprSignRouteService extends IBaseService<OprSignRoute, Long>{

	/**
	 * @param departId
	 * @return  ��ó��κ�
	 * @throws Exception
	 */
	public String getCarTimesNum(Long departId) throws Exception;
	
	
	/**
	 * @param aa   �����ɱ�����������˱��浽�ɱ���
	 * @throws Exception
	 */
	public void fiAuditByName(List<OprSignRoute>aa) throws Exception;

	/**
	 * ���������� �������
	 * @param id
	 * @param ts
	 * @throws Exception
	 */
	public void qxFiAudit(Long id,String ts) throws Exception;
	
	/**
	 * ���ݳɱ�ID���¸���״̬
	 * @param id �ɱ�ID
	 * @param payStatus ����״̬��0δ���1��֧����
	 * @throws Exception
	 */
	public void payConfirmationById(Long id) throws Exception;
	
	/**
	 * ���ݳɱ�ID��������״̬
	 * @param id �ɱ�ID
	 * @param payStatus ����״̬��0δ���1��֧����
	 * @throws Exception
	 */
	public void payConfirmationRegisterById(Long id) throws Exception;
	
	/**
	 * �������
	 * @param aa   
	 * @throws Exception
	 */
	public void carAuditByName(List<OprSignRoute> aa) throws Exception;
	
	/**
	 * ���ݳ��κţ�ɾ�������ɱ�����
	 * @param routeNumber
	 * @throws Exception
	 */
	public void cancelCarByRouteNumber(Long routeNumber)throws Exception;
	
	/**
	 * ����ۺ�Ʊ��
	 * @param weight ����
	 * @throws Exception
	 */
	public double getVotesPiece(double weight,double startWeight,double levleWeight,double twoLevelWeight)throws Exception;
}
