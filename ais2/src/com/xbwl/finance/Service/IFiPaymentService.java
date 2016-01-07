package com.xbwl.finance.Service;

import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCheck;
import com.xbwl.entity.FiPayment;


/**
 * @author ����
*TODO
*����������
*@2011-7-16
 *
 */
public interface IFiPaymentService extends IBaseService<FiPayment, Long> {

	/**
	 * 
	* @Title: searchReceiving 
	* @Description: ����ѡ���ids,��Ӧ��Ӧ��������ҵ�id��Ӧ���������͵���
	* @param @param page
	* @param @param ids,��ʽ:1,2,3,
	* @param @return    �趨�ļ� 
	* @return Page<FiPayment>    �������� 
	* @throws
	 */
	public Map searchReceiving(Map map) throws Exception;
	
	/**
	 * 
	* @Title: searchReceiving 
	* @Description: ����ѡ���ids,��Ӧ��Ӧ��������ҵ�id��Ӧ�ĸ��
	* @param @param page
	* @param @param ids,��ʽ:1,2,3,
	* @param @return    �趨�ļ� 
	* @return Page<FiPayment>    �������� 
	* @throws
	 */
	public Page<FiPayment> searchPayment(Page page, Map map) throws Exception;
	
	/**
	 * 
	* @Title: saveReceiving 
	* @Description: TODO(�տ������Ӧ��Ӧ��-�տ�ȷ��) 
	 */
	public void saveReceiving(Map<String,Object> map,User user) throws Exception;
	
	/**
	 * 
	* @Title: saveReceiving 
	* @Description: TODO(���������Ӧ��Ӧ��-����ȷ��) 
	* @throws
	 */
	public void savePayment(Map<String, Object> map) throws Exception;
	
	/**
	* @Title: saveEntrust 
	* @Description: TODO(ί���ո���-����) 
	 */
	public void saveEntrust(Map<String,Object> map,FiPayment fiPayment) throws Exception;
	
	/**
	* @Title: saveEntrust 
	* @Description: TODO(����-����) 
	 */
	public void saveLosses(Map<String,Object> map) throws Exception;
	
	/**
	 * ֧Ʊ����ȷ��(����ʵ��ʵ�����տ�״̬)
	 * @param fiCheck
	 * @throws Exception
	 */
	public void checkAudit(FiCheck fiCheck) throws Exception;
	
	/**
	 * ��˵���
	 * @param id ����ID
	 * @param user �����
	 * @throws Exception
	 */
	public void audit(Long id,User user) throws Exception;
	
	/**
	 * �������
	 * @param id ����id
	 * @param user
	 * @throws Exception
	 */
	public void revocationAudit(Long id,User user) throws Exception;
	
	
	
}
