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
	 * ���ű����ⲿ�ӿ�
	 * @param smsDto ����Dto
	 * @return WSResult����
	 * @throws Exception �쳣
	 */
	public WSResult saveMsg(SmsSendsmsDto smsDto)throws Exception;
}
