package com.xbwl.oper.edi.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.EDIOprHistory;

public interface IEDIOprHistoryService extends IBaseService<EDIOprHistory, Long> {

	/**
	 * ɨ��EDI������¼��ʱ����дAIS2.0ϵͳ
	 * @throws Exception
	 */
	public void scanTimingCtOprHistoryService()throws Exception;

}
