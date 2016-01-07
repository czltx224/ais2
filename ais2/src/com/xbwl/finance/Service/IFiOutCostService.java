package com.xbwl.finance.Service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiOutcost;

/**
 *@author LiuHao
 *@time Aug 29, 2011 5:49:34 PM
 */
public interface IFiOutCostService extends IBaseService<FiOutcost,Long> {
	
	/**
	 * �ⷢ�ɱ����
	 * @param aa
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	public String outCostAduit(List<FiOutcost> aa,Long batchNo)throws Exception;
	
	/**
	 * �����ɱ����
	 * @param id
	 * @param ts
	 * @return
	 * @throws Exception
	 */
	public String qxFiAduit(Long id,String ts) throws Exception;
	
	/**
	 * ���ݳɱ�������κŸ��¸���״̬Ϊ��1��֧��
	 * @param batchNo ���κ�
	 * @throws Exception
	 */
	public void payConfirmationBybatchNo(Long batchNo) throws Exception;
	
	/**
	 * ���ݳɱ�������κų�������״̬Ϊ��0δ����
	 * @param batchNo ���κ�
	 * @throws Exception
	 */
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception;
	
	/**
	 * �����Ǽ���ˣ��ⷢ�ɱ��Ƿ����������
	 * @return
	 * @throws Exception
	 */
	public String  returnGoodsPrompt(Long dno,Long departId) throws Exception;
	
	/**
	 * ��������ɱ�����
	 * @param id
	 * @param ts
	 * @return
	 * @throws Exception
	 */
	public String  delOutcostData (Long id ,String ts)  throws Exception;
	
	/**
	 * �����ⷢ�ɱ� ��Ϣ��ʾ
	 * @param id  �ⷢ�ɱ�ID
	 * @param ts  ʱ���
	 * @return  ��ʾ�ַ���
	 * @throws Exception
	 */
	public String qxAmountCheck(Long id)throws Exception;
	
	/**
	 * ���ʱ��Ϣ��ʾ
	 * @param aa  ��ʾ�ļ���id
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> aduitAmountCheck(List<FiOutcost>aa ) throws Exception;
	
	/**
	 * ��ȡ��������
	 * @return �������κ�
	 * @throws Exception
	 */
	public Long getOutcostBatchNo() throws Exception;
	}
