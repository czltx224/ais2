package com.xbwl.oper.stock.service;

import java.util.Map;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprInformAppointmentDetail;

/**
 * @author CaoZhili time Jul 18, 2011 2:18:48 PM
 * 
 * 通知预约明细服务层接口
 */
public interface IOprInformAppointmentDetailService extends
		IBaseService<OprInformAppointmentDetail, Long> {

	/**
	 * 通过配送单号查询通知预约历史记录
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public String findDetailByDno(Map<String, String> map)throws ServiceException;
	

}
