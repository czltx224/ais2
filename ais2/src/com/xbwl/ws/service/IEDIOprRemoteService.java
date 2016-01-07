package com.xbwl.ws.service;

import com.xbwl.ws.client.result.base.WSResult;

import dto.OprHistoryDto;
import dto.OprRemarkDto;
import dto.OprSignDto;

/**
 * EDIԶ�̵��ýӿ�
 * @author czl
 * @date 2012-05-22
 */
public interface IEDIOprRemoteService {

	/**
	 * ���浽ǩ�ձ���ⲿ�ӿ�
	 * @return
	 */
	public WSResult saveToOprSign(OprSignDto dto);
	
	
	/**
	 * ���±�ע�ⲿ�ӿ�
	 * @param dto
	 * @return
	 */
	public WSResult saveNewRemark(OprRemarkDto dto);
	
	/**
	 * ���浽OPR_HISTRORY������ʷ��¼��
	 * @param dto
	 * @return
	 */
	public WSResult saveToOprHistroy(OprHistoryDto dto);
}
