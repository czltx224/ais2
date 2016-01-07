package com.xbwl.oper.stock.service;

import java.util.Map;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprInformAppointmentDetail;

/**
 * @author CaoZhili time Jul 18, 2011 2:18:48 PM
 * 
 * ֪ͨԤԼ��ϸ�����ӿ�
 */
public interface IOprInformAppointmentDetailService extends
		IBaseService<OprInformAppointmentDetail, Long> {

	/**
	 * ͨ�����͵��Ų�ѯ֪ͨԤԼ��ʷ��¼
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public String findDetailByDno(Map<String, String> map)throws ServiceException;
	

}
