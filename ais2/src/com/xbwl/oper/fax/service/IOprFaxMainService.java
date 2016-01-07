package com.xbwl.oper.fax.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxMain;

/**
 * @author CaoZhili
 * time Aug 29, 2011 10:29:48 AM
 */
public interface IOprFaxMainService extends IBaseService<OprFaxMain, Long>{

	
	/**
	 * ͨ��������ȥ��ѯ�ܼ�����������
	 * @param flightMainNo ������
	 * @return �������������
	 */
	public OprFaxMain getTotalByFlightMainNo(String flightMainNo)throws Exception;
	
	
	/**
	 * ͨ��������ȥ��ѯ������ʵ��
	 * @param flightMainNo ������
	 * @return �������������
	 */
	public OprFaxMain getOprFaxMainByFlightMainNo(String flightMainNo)throws Exception;
	
	
	/**
	 * ͨ�������Ŵ���ʱ���ѯ������Ϣ
	 * @param map  ����ʱ���������
	 * @return  SQL�ַ���
	 * @throws Exception
	 */
	public String findFiDeliverycost(Map map) throws Exception;
	
	/**
	 * ͨ�������Ŵ���ʱ���ѯ������Ϣ
	 * @param map  ����ʱ���������
	 * @return  SQL�ַ���
	 * @throws Exception
	 */
	public OprFaxMain findFiDeliveryByMatchStatus(String flightMainNo) throws Exception;
}
