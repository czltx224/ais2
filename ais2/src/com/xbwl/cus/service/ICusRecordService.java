package com.xbwl.cus.service;

import java.util.List;
import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusRecord;

/**
 *@author LiuHao
 *@time Oct 8, 2011 1:49:24 PM
 */
public interface ICusRecordService extends IBaseService<CusRecord,Long> {

	/**��ȡ�ͻ��б��ѯSQL
	 * @param map ��������
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String findCustomerListService(Map<String, String> map) throws Exception;

	/**
	 * ͣ�ÿͻ�����
	 * @param strIds ��Ҫ�޸�״̬�Ŀͻ�ID�ַ�������
	 * @throws Exception
	 */
	public void stopCustomerService(String[] strIds)throws Exception;

	/**��ȡ����ͻ���ѯSQL
	 * @param map ��������
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String findDistributionCustomerService(Map<String, String> map)throws Exception;

	/**�ѿͻ����䵽����
	 * @param idStrings �ͻ�ID����
	 * @param departId ����ID
	 * @throws Exception ������쳣
	 */
	public void referToDepartService(String[] idStrings, Long departId)throws Exception;

	/**��ѯ��ı�ע
	 * @param map ��������,�������tableName
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String findTableRemark(Map<String, String> map)throws Exception;

	/**�ͻ��б��Զ����ѯ
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String customSearchService(Map<String, String> map)throws Exception;
	/**
	 * �ջ���ת�ͻ�  ��д�ջ�����Ϣ��
	 * @param cusRecord
	 * @return
	 * @throws Exception
	 */
	/*
	public boolean saveCusAndCon(CusRecord cusRecord,User user)throws Exception;
	*/
	/**
	 * ��ѯ¼��ʱ����������Ϣ
	 * @return
	 * @throws Exception
	 */
	public List findFaxcus(Long departId,String cusName)throws Exception;
	/**
	 * �����ͻ�
	 * @param cusRecord
	 * @param user
	 * @throws Exception
	 */
	public void saveCusRecord(CusRecord cusRecord,User user,String conType)throws Exception;
	/**
	 * ��ѯԤ���ͻ�
	 * @author LiuHao
	 * @time May 10, 2012 4:33:48 PM 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page findWarnCus(Page page,Long cusRecordId,String cusService)throws Exception;

	/**
	 * ���ÿͻ�����
	 * @param strIds ��Ҫ���õĿͻ�����
	 * @throws Exception
	 */
	public void startCustomerService(String[] strIds)throws Exception;
}
