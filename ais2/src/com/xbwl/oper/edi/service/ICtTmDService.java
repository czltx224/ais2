package com.xbwl.oper.edi.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CtTmD;

/**
 * @project ais2
 * @author czl
 * @Time Feb 13, 2012 3:28:16 PM
 */
public interface ICtTmDService extends IBaseService<CtTmD, String> {

	/**
	 * ɨ�����EDI��ʱд���д�뵽EDI
	 * @throws Excpetion
	 */
	public void scanningAISCtTmDService()throws Exception;
	
	/**
	 * ����EDI��ʱ������ݣ���״̬�޸�Ϊ0
	 * @param dno
	 * @throws Exception
	 */
	public void deleteStatusCtTmD(Long dno)throws Exception;
}
