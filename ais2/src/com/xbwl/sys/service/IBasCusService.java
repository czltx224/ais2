package com.xbwl.sys.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasCusService;

/**
 *@author LiuHao
 *@time Jul 29, 2011 4:12:49 PM
 */
public interface IBasCusService extends IBaseService<BasCusService,Long> {
	/**
	 * ���ݿ���ID��ѯ�ͷ�Ա��Ϣ
	 * @author LiuHao
	 * @time Feb 29, 2012 3:51:10 PM 
	 * @param cusId
	 * @param bussId
	 * @return
	 * @throws Exception
	 */
	public BasCusService getCusServiceByCusId(Long cusId,Long bussId)throws Exception;
}
