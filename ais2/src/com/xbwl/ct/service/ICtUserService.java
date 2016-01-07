package com.xbwl.ct.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;

import dto.CtUserDto;

/**
 * EDI ��½�û�����
 * author shuw
 * time May 2, 2012 9:55:12 AM
 */
public interface ICtUserService extends IBaseService<CtUserDto, Long> {

	/**
	 * ����EDI�û�
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List findCtUser(Map<String, String> map)throws Exception;

	/**
	 * ɾ��EDI�û�
	 * @param split
	 * @throws Exception
	 */
	public void deleteCtUser(String[] ids)throws Exception;

}
