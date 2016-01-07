package com.xbwl.ws.client;

import com.xbwl.ws.client.result.base.WSResult;

import dto.SmsSendsmsDto;

/**
 * @author czl
 * @Time 2012-3-29
 * 
 */
public interface IWSSmsSendsmsService {

	/**
	 * 短信保存外部接口
	 * @param smsDto 短信Dto
	 * @return WSResult对象
	 * @throws Exception 异常
	 */
	public WSResult saveMsg(SmsSendsmsDto smsDto)throws Exception;
}
