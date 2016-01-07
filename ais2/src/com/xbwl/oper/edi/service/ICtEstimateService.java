package com.xbwl.oper.edi.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CtEstimate;


/**
 * @project ais2
 * @author czl
 * @Time
 */
public interface ICtEstimateService extends IBaseService<CtEstimate, String> {

	/**
	 * 定时任务扫描传真写入EDI执行接口
	 * @throws Exception
	 */
	public void scanningCtEstimateService()throws Exception;

}
