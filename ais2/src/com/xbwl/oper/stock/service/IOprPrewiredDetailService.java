package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprPrewired;
import com.xbwl.entity.OprPrewiredDetail;

/**
 * author shuw
 * time 2011-7-19 ����11:19:30
 * ʵ����ϸ�����ӿ�
 */

public interface IOprPrewiredDetailService extends  IBaseService<OprPrewiredDetail, Long>{

	/**
	 * Ԥ���ѯSQLƴ�� �����Ž�����
	 * @param filterMap ��������
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String getListSqlAll(Map filterMap) throws Exception;
	
	/**
	 *  Ԥ�����
	 * @param filterMap ��Ҫ��͵�id�ַ���
	 * @return
	 * @throws Exception
	 */
	public String getAjaxTotalSum(String filterMap) throws Exception;
	
	/**
	 * ����Ԥ�䱣��	
	 *  * @param oprPrewired
	 * @param ids  ���͵��ż���
	 * @param s
	 * @param orderFields
	 * @return
	 * @throws Exception
	 */
	public Long saveOprPrewiredByids(OprPrewired oprPrewired,List<Long> ids,String s,String orderFields) throws Exception;

	/**
	 * ����ȷ�Ͻ��ӵ���ѯ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findOutCarList(Map<String, String> map)throws Exception;
}
