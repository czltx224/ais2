package com.xbwl.message.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.SmsSendsmsRecord;

import dto.SmsSendsmsDto;

/**
 * ���ŷ��ͼ�¼������ӿ�
 * @author czl
 *
 */
public interface ISmsSendsmsRecordService extends
		IBaseService<SmsSendsmsRecord, Long> {
	
	/**
	 * ͨ��SmsSendsmsDto���󱣴浽���ż�¼��
	 * @param dto
	 * @throws Exception
	 */
	public void saveBySmsSendsmsDto(SmsSendsmsDto dto)throws Exception;
	
	/**
	 * ��ȡ���ŷ���UID
	 * @param smsDto
	 * @throws Exception
	 */
	public String getSmsUid(String ipAddr)throws Exception;

	/**
	 * ���Ͷ��Ų��ұ����¼
	 * @param dto ���ŷ���dto
	 * @param consigneeTels �ö��Ÿ����ĵ绰�ַ���
	 * @throws Exception
	 */
	public void saveSendMsg(SmsSendsmsDto dto,String consigneeTels)throws Exception;
	
}
