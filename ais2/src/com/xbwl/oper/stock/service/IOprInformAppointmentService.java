package com.xbwl.oper.stock.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprInformAppointment;

/**
 * @author CaoZhili time Jul 18, 2011 2:18:16 PM
 * 
 * ֪ͨԤԼ��������ӿ�
 */

public interface IOprInformAppointmentService extends
		IBaseService<OprInformAppointment, Long> {

	/**
	 * ����֪ͨԤԼ����ͼ�¼�����޸�״̬���е�֪ͨ״̬
	 * @param map ֪ͨ�Ľ����Map����
	 * @throws Exception
	 */
	public void saveInformAppointmentService(Map map) throws Exception;

	/**
	 * ��ȡ��дSQL��ѯ���
	 * @param map Map ����
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String getResultMapQueryService(Map<String, String> map)throws Exception;

}
