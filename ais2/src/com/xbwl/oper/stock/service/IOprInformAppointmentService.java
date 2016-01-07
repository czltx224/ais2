package com.xbwl.oper.stock.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprInformAppointment;

/**
 * @author CaoZhili time Jul 18, 2011 2:18:16 PM
 * 
 * 通知预约主表服务层接口
 */

public interface IOprInformAppointmentService extends
		IBaseService<OprInformAppointment, Long> {

	/**
	 * 保存通知预约结果和记录，并修改状态表中的通知状态
	 * @param map 通知的结果用Map保存
	 * @throws Exception
	 */
	public void saveInformAppointmentService(Map map) throws Exception;

	/**
	 * 获取手写SQL查询语句
	 * @param map Map 集合
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String getResultMapQueryService(Map<String, String> map)throws Exception;

}
