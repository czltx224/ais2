package com.xbwl.message.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.SmsSendsmsRecord;

import dto.SmsSendsmsDto;

/**
 * 短信发送记录表服务层接口
 * @author czl
 *
 */
public interface ISmsSendsmsRecordService extends
		IBaseService<SmsSendsmsRecord, Long> {
	
	/**
	 * 通过SmsSendsmsDto对象保存到短信记录表
	 * @param dto
	 * @throws Exception
	 */
	public void saveBySmsSendsmsDto(SmsSendsmsDto dto)throws Exception;
	
	/**
	 * 获取短信发送UID
	 * @param smsDto
	 * @throws Exception
	 */
	public String getSmsUid(String ipAddr)throws Exception;

	/**
	 * 发送短信并且保存记录
	 * @param dto 短信发送dto
	 * @param consigneeTels 用逗号隔开的电话字符串
	 * @throws Exception
	 */
	public void saveSendMsg(SmsSendsmsDto dto,String consigneeTels)throws Exception;
	
}
