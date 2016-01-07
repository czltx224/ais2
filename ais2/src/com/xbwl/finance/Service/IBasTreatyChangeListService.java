package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasTreatyChangeList;

/**
 * @author CaoZhili time Aug 10, 2011 2:30:10 PM
 * 
 * Э����޸ļ�¼������ӿ�
 */
public interface IBasTreatyChangeListService extends
		IBaseService<BasTreatyChangeList, Long> {
	
	/**
	 * �������Э��۸��ͨ�ñ��淽��
	 * 
	 * @param entity Ҫ�����¼��ʵ����
	 * @param chinaName Ҫ�����¼�ı�����
	 * @param isdelete �Ƿ�ɾ��
	 * @throws Exception
	 */
	public void saveRecord(Object entity, String chinaName,boolean isdelete) throws Exception;
	
	/**
	 * ��ȡ��ѯSQL
	 * @param map ��������
	 * @return
	 */
	public String getSqlService(Map<String,String> map) throws Exception;
}
