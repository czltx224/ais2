package com.xbwl.oper.edi.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxOut;

/**
 *@author LiuHao
 *@time Apr 16, 2012 4:35:51 PM
 */
public interface IOprFaxOutService extends IBaseService<OprFaxOut,Long> {

	/**
	 * 定时任务扫描OPR_FAX_OUT写入EDI主表
	 * @throws Exception
	 */
	public void faxChangeScanning() throws Exception;
	
}
