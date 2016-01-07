package com.xbwl.finance.Service;

import java.util.List;
import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.bean.ValidateInfo;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiReceivabledetail;

public interface IFiReceivabledetailService extends
		IBaseService<FiReceivabledetail, Long> {

	/**
	 * ���ݲ������ɶ��˵�
	 * @param map
	 * @param page
	 * @param validateInfo
	 * @return Boolean �������� ,trueΪ���ɳɹ���falseΪ����ʧ�ܡ�
	 * @throws Exception
	 */
	public Boolean saveFiReceivablestatement(Map map,Page page,ValidateInfo validateInfo) throws Exception;
	
	/**
	 * ������ϸ�޳�����
	 * @param map
	 * @throws Exception
	 */
	public void eliminate(Map map)  throws Exception;
	
	/**
	 * ���˵������������ϸ
	 * @param map
	 * @throws Exception
	 */
	public void receivabledetailAdd(Map map) throws Exception;
	
	/**
	 * 
	* @Title: saveProble 
	* @Description: TODO(���������˿1�����¹�Ӧ��������ϸ�����˿���Ϣ��2�������������˿��¼��) 
	* @param @param fiReceivabledetail   ��Ӧ��������ϸʵ�� 
	* @return void    �������� 
	* @throws
	 */
	public void saveProble(FiReceivabledetail fiReceivabledetail) throws Exception;

	/**
	 * 
	* @Title: updateFiReceivabledetailStatus 
	* @Description: TODO(���ݶ��˵��Ÿ���Ƿ����ϸ״̬) 
	* @param @param reconciliationNo ���˵���
	* @param @param reconciliationStatus  ���˵�״̬ 
	* @return void    �������� 
	* @throws
	 */
	public void updateStatusByreconciliationNo(Long reconciliationNo,Long reconciliationStatus);
	
	/**
	 * 
	* @Title: isProbleBytailNo 
	* @Description: TODO(���ݹ�Ӧ��������ϸ�����״̬���ж��Ƿ��ѵǼǹ������˿�) 
	* @param @param id ��Ӧ��������ϸ��ID
	* @param @return    �趨�ļ� 
	* @return boolean    true:δ�Ǽ�,false�ѵǼ�
	* @throws
	 */
	public boolean isProbleBytailNo(Long id);
	
	/**
	 * 
	* @Title: isStatusAudited 
	* @Description: TODO(����Ƿ����ϸ�����ж������˿��Ƿ�������) 
	* @param @param receivabledetailNo Ƿ����ϸ����
	* @param @return    ���Ϊ�������򷵻�True,�����������򷵻�False��
	* @return boolean  
	* @throws
	 */
	public boolean isStatusAudited(Long receivabledetailNo);
	
	/**
	 * ���ݶ��˵��Ÿ���Ƿ����ϸ���״̬Ϊ�Ѻ���
	 * @param reconciliationNo    ���˵���
	 */
	public void updateVerificationStatus(Long reconciliationNo);
	
	/**
	 * ���ݶ��˵���ѯ���д��ջ����б�
	 * @param reconciliationNo ���˵���
	 * @return
	 * @throws Exception
	 */
	public List<FiReceivabledetail> findCollectionByreconciliationNo(Long reconciliationNo) throws Exception;
	
	/**
	 * �������͵����б���Ӧ�����ջ���Ƿ����ϸ
	 * @param dnos ���͵��б�(��ʽ��1,2,3,)
	 * @return
	 * @throws Exception
	 */
	public List<FiReceivabledetail> findCollectionByDnos(String dnos) throws Exception;
	
	/**
	 * ���϶��˵�
	 * @param reconciliationNo
	 * @throws Exception
	 */
	public void invalid(Long reconciliationNo) throws Exception;
	
	/**
	 * ����Ӧ�����ջ�������״̬
	 * @param fiReceivabledetail Ӧ�����ջ���ʵ��
	 * @throws Exception
	 */
	public void verificationCollectionStatus(FiReceivabledetail fiReceivabledetail) throws Exception;
	
	/**
	 * ����Ӧ�����ջ�������״̬
	 * @param fiPayment Ӧ��Ӧ��ʵ��
	 * @throws Exception
	 */
	public void verificationReceistatment(FiPayment fiPayment) throws Exception;
	
	/**
	 * ����ʵ�յ�ʱ�����ΪӦ�մ��ջ������Ӧ�����ջ�������״̬
	 * @param fiPayment
	 * @throws Exception
	 */
	public void revocationFiPaid(FiPayment fiPayment) throws Exception;
	
	/**
	 * �������˵�ʱ�����ΪӦ�մ��ջ������Ӧ�����ջ�������״̬
	 * @param fiReceivabledetail
	 * @throws Exception
	 */
	public void revocationCollectionStatus(FiReceivabledetail fiReceivabledetail) throws Exception;

	/**
	 * �������ж�����ϸ���� ��Ҫ���ڵ����ʹ�ӡ���˵���ʱ��
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getAllReceivabledetailSql() throws Exception;
	
	
	/**
	 * �ֹ�����������ϸ����
	 * @param fiReceivabledetail
	 * @throws Exception
	 */
	public void saveReceivabledetail(FiReceivabledetail fiReceivabledetail) throws Exception;
	
	/**
	 * ���������ϸ
	 * @param map 
	 * @param user
	 * @throws Exception
	 */
	public void audit(Map map,User user) throws Exception;
	
	/**
	 * �������������ϸ
	 * @param map:������ϸID
	 * @param user 
	 * @throws Exception
	 */
	public void revocationAudit(Map map,User user) throws Exception;
	
	/**
	 * �����ֹ�����������ϸ
	 * @param map:������ϸID
	 * @param user
	 * @throws Exception
	 */
	public void invalid(Map map,User user)throws Exception;
}

