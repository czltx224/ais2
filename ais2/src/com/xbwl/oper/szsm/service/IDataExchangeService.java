package com.xbwl.oper.szsm.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.DigitalChinaExchange;

/**
 * ��������Խӷ���ӿ�
 * @author czl
 * @date 2012-06-27
 */
public interface IDataExchangeService extends IBaseService<DigitalChinaExchange, Long>{
	
	/**
	 * �������ݶԽӲ�ѯSQL��ȡ
	 * @throws Exception
	 */
	public void doDispatchingExchange() throws Exception;

	/**
	 * ǩ�����ݶԽӲ�ѯSQL��ȡ
	 * @throws Exception
	 */
	public void doSingInExchange() throws Exception;

	/**
	 * ��������Խӳɹ������ѯSQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findList(Map<String, String> map)throws Exception;

	/**
	 * ��������Խ�ͳ�Ʋ�ѯSQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findCount(Map<String, String> map)throws Exception;

	/**
	 * ��������Խ�ͳ�ƻ���SQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findCountSum(Map<String, String> map)throws Exception;
}