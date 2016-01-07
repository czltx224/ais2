package com.xbwl.ws.client;

import java.util.List;
import java.util.Map;

import com.xbwl.ws.client.result.base.WSResult;

import dto.CtEstimateDto;
import dto.CtTmdDto;
import dto.CtUserDto;
import dto.OprSignDto;

/**
 * EDI��AIS2.0�ⲿ�ӿ�
 * @author czl
 * @date 2012-05-24
 */
public interface IWSEdiToAisRemote {

	/**
	 * ���浽EDI����
	 * @param dto
	 * @return
	 */
	public WSResult saveToCtTmd(CtTmdDto dto);
	
	/**
	 * ���浽Ԥ�ƻ�����
	 * @return
	 */
	public WSResult saveToCtEstimate(CtEstimateDto dto);
	
	/**
	 * ����CT_USER�û�����ӿ�
	 * @param dto
	 * @return
	 */
	public WSResult saveToCtUser(CtUserDto dto);
	
	/**
	 * ����EDI�û�����ӿ�
	 * @param map
	 * @return
	 */
	public List findCtUser(Map<String,String> map);

	/**
	 * ɾ��EDI�û�Զ�̽ӿ�
	 * @param ids
	 * @return
	 */
	public WSResult deleteCtuser(String[] ids);
	
	/**
	 * ����ǩ��ɨ���дEDIǩ�ձ�
	 * @param dto
	 * @return
	 */
	public WSResult messageRemoteSign(OprSignDto dto);
	
}
