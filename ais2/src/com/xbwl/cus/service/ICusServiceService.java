package com.xbwl.cus.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusService;

/**
 * author CaoZhili time Oct 10, 2011 4:35:01 PM
 */

public interface ICusServiceService extends IBaseService<CusService, Long> {

	/**
	 * ����ͻ����淽��
	 * 
	 * @param splitStrings ����Ŀͻ�ID ����
	 * @param userId ������û�ID
	 * @param flag �Ƿ��޸Ŀͷ�Ա
	 * @throws Exception
	 */
	public void saveCusService(String[] splitStrings, Long userId, Boolean flag)
			throws Exception;

	/**
	 * ����ͻ��Ƴ�����
	 * @param splitStrings
	 * @param bussDepart Ҫ�Ƴ��ͻ��Ĳ���
	 * @throws Exception
	 */
	public void moveCusService(String[] splitStrings,Long bussDepart)
			throws Exception;

	/**
	 * �ͷ�Աָ�ɿͻ���ѯ����
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findServiceDesignate(Map<String, String> map)throws Exception;

	/**
	 * ����ָ�ɿͷ�Ա
	 * @param cusRecordId
	 * @param userCode
	 * @throws Exception
	 */
	public void saveServiceDesignate(Long cusRecordId, String userCode)throws Exception;

	/**
	 * ɾ��ָ�ɿͷ�Ա
	 * @param cusRecordId
	 * @param userCode
	 * @throws Exception
	 */
	public void deleteServiceDesignate(Long cusRecordId, String userCode)throws Exception;

}
