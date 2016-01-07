package com.xbwl.cus.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusSearch;

/**
 * author CaoZhili
 * time Oct 17, 2011 10:08:43 AM
 */

public interface ICusSearchService extends IBaseService<CusSearch, Long> {

	/**��ѯ�Զ���Ĳ�ѯ���
	 * @param map ��������(Ԥ��)
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String findCusSearchService(Map<String, String> map) throws Exception;

	/**�Զ����ѯ��Ȩ����
	 * @param idStrings �Զ����ѯ�ַ�������
	 * @param departId ��Ȩ���ű��
	 * @throws Exception  ������쳣
	 */
	public void authorizedService(String[] idStrings, Long departId) throws  Exception;

	/**
	 * ��ѯ�Զ����ѯSQL��ȡ����
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findSearchListService(Map<String, String> map)throws Exception;

}
