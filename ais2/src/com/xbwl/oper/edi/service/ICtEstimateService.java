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
	 * ��ʱ����ɨ�贫��д��EDIִ�нӿ�
	 * @throws Exception
	 */
	public void scanningCtEstimateService()throws Exception;

}
