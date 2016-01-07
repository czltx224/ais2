package com.xbwl.oper.edi.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxOut;

/**
 *@author LiuHao
 *@time Apr 16, 2012 4:35:51 PM
 */
public interface IOprFaxOutService extends IBaseService<OprFaxOut,Long> {

	/**
	 * ��ʱ����ɨ��OPR_FAX_OUTд��EDI����
	 * @throws Exception
	 */
	public void faxChangeScanning() throws Exception;
	
}
