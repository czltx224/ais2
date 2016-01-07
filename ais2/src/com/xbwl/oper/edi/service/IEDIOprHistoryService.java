package com.xbwl.oper.edi.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.EDIOprHistory;

public interface IEDIOprHistoryService extends IBaseService<EDIOprHistory, Long> {

	/**
	 * 扫描EDI操作记录临时表，回写AIS2.0系统
	 * @throws Exception
	 */
	public void scanTimingCtOprHistoryService()throws Exception;

}
