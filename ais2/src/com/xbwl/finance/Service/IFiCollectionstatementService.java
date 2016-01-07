package com.xbwl.finance.Service;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCollectionstatement;

public interface IFiCollectionstatementService extends IBaseService<FiCollectionstatement,Long> {
	/**
	 * 
	* @Title: ���˵��Ƿ�Ϊδ���
	* @Description: TODO
	* @param @param reconciliationNos
	* @param @return    true:δ��ˣ�false������˻������ϻ�������
	* @return boolean    �������� 
	* @throws
	 */
	public boolean isConfirmReview(String reconciliationNos);

	/**
	 * 
	* @Title: ���˵����
	* @Description: TODO(1���Զ����¶��˵�����״̬��2����������Ϣд��Ӧ��Ӧ����) 
	* @param @param reconciliationNos    ���˵��б� 
	* @param @param user    ��ǰ�����û�
	* @return void    �������� 
	* @throws
	 */
	public void confirmReview(String reconciliationNos,User user);
	
	/**
	 * 
	* @Title: ���˵�״̬�Ƿ�Ϊ����� 
	* @Description: TODO
	* @param @param reconciliationNos
	* @param @return    true:�����,falseΪδ���
	* @return boolean    �������� 
	* @throws
	 */
	public boolean isRevocationReview(String reconciliationNos);
	
	/**
	 * �������˵����
	* @Title: RevocationReview 
	* @Description: TODO(1���Զ����¶��˵�����״̬��2���Զ�����Ӧ��Ӧ����) 
	* @param @param reconciliationNos  ���˵��б� 
	* @param @param user    ��ǰ�����û�
	* @return void    �������� 
	* @throws
	 */
	public void revocationReview(String reconciliationNos,User user);
}
