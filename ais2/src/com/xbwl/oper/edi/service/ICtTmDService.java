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
	 * 扫描出库EDI临时写入表，写入到EDI
	 * @throws Excpetion
	 */
	public void scanningAISCtTmDService()throws Exception;
	
	/**
	 * 作废EDI临时表的数据，把状态修改为0
	 * @param dno
	 * @throws Exception
	 */
	public void deleteStatusCtTmD(Long dno)throws Exception;
}
