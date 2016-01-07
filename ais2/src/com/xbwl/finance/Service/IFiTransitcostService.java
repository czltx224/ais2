package com.xbwl.finance.Service;

import java.util.List;
import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiTransitcost;

/**
 * author shuw
 * time Oct 7, 2011 11:46:16 AM
 */

public interface IFiTransitcostService extends
						IBaseService<FiTransitcost, Long> {
	
 
	/**
	 * ��ת�ɱ���ˣ����浽��ת�ɱ���ͳɱ�����
	 * @param user
	 * @param dno
	 * @throws Exception
	 */
	public String saveFiTransitcostAndFicost(User user,String ts,Long  dno) throws Exception;

	/**
	 * ��ת�ɱ���ˣ����浽��ת�ɱ���ͳɱ�����
	 * @param user
	 * @param dno
	 * @throws Exception
	 */
	public String saveFiTransitcostAndFicost(User user,List<FiTransitcost> aa) throws Exception;
	
	/**
	 * ����������
	 * @param id
	 * @param ts
	 * @throws Exception
	 */
	public void qxFiAudit(Long id,String ts,String sourceData ) throws Exception;
	
	/**
	 * �������κ�
	 * @param id
	 * @param ts
	 * @throws Exception
	 */
	public Long getBatchNo( ) throws Exception;
	
	/**
	 * ���״̬��ѯSQL
	 * @param map ��ѯ����
	 * @param type  ��ѯ���͵��ж� 0�ǲ�ѯȫ����1����ˣ�-1��δ���
	 * @return
	 * @throws Exception
	 */
	public String getSelectSql(Map map,Long type ) throws Exception;
	
	/**
	 * �������κŸ��¸���״̬Ϊ:1��֧��
	 * @param batchNo ���κ�
	 * @throws Exception
	 */
	public void payConfirmationBybatchNo(Long batchNo) throws Exception;
	
	
	/**
	 * �������κų�������״̬Ϊ:0��֧��
	 * @param batchNo ���κ�
	 * @throws Exception
	 */
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception;
	
	
	/**���������Ϣ��ʾ
	 * @param id  ��ת�ɱ���ID
	 * @return info
	 * @throws Exception
	 */
	public String qxAmountCheck(Long id) throws Exception ;
}
