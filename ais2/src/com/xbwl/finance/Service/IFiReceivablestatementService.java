package com.xbwl.finance.Service;

import org.ralasafe.user.User;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiPaid;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiReceivablestatement;

public interface IFiReceivablestatementService extends
		IBaseService<FiReceivablestatement, Long> {

	/**
	 * 
	* @Title:���˵��Ƿ�Ϊδ���
	* @Description:  ���˵��Ƿ�Ϊδ���
	* @param @param reconciliationNos    ���˵��б�
	* @return boolean  true:δ��ˣ�false������˻������ϻ�������
	 */
	public boolean isConfirmReview(String reconciliationNos);

	
	/**
	 * 
	* @Title: ���˵����
	* @Description: ���˵����(1���Զ����¶��˵�����״̬��2����������Ϣд��Ӧ��Ӧ����) 
	* @param @param reconciliationNos    ���˵��б� 
	* @param @param user    ��ǰ�����û�
	* @return void    �������� 
	 */
	public void confirmReview(String reconciliationNos,User user)throws Exception;
	
	
	/**
	 * 
	* @Title: ���˵�״̬�Ƿ�Ϊ����� 
	* @Description: ���˵�״̬�Ƿ�Ϊ����� 
	* @param @param reconciliationNos    ���˵��б� 
	* @param @return    true:�����,falseΪδ���
	 */
	public boolean isRevocationReview(String reconciliationNos);
	
	
	/**
	* @Title: �������˵���� 
	* @Description: TODO(1���Զ����¶��˵�����״̬��2���Զ�����Ӧ��Ӧ����) 
	* @param @param reconciliationNos  ���˵��б� 
	* @param @param user    ��ǰ�����û�
	 */
	public void revocationReview(String reconciliationNos,User user) throws Exception;
	
	
	/**
	 * 
	* @Title: ���˵����� 
	* @Description: ���˵�����
	* @param @param reconciliationNos    ���˵��б� 
	 */
	public void invalid(String reconciliationNos) throws Exception;
	/**
	 * ���ܿ��̶�����Ϣ
	 * @author LiuHao
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page findCusReceistatment(Page page)throws Exception;
	
	/**
	 * �������˵�
	 * @param amount �������
	 *  @param costType ������������
	 * @param reconciliationNo ���˵���
	 * @throws Exception
	 */
	public void verificationReceistatment(Double amount,Long reconciliationNo) throws Exception;
	
	/**
	 * ����ʵ��ʵ��ʱ���������˵���������ϸ��Ӧ�����ջ�������״̬
	 * @param fiPayment
	 * @param fiPaid
	 * @throws Exception
	 */
	public  void revocationFiPaid(FiPayment fiPayment,FiPaid fiPaid)  throws Exception;
	
	/**
	 * ����Excel �����Excel��������Ҫģ��
	 * @param id
	 * @throws Exception
	 */
	public void exporterExcel(Long id) throws Exception;
}

