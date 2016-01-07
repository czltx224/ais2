package com.xbwl.cus.service;

import java.io.File;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusCurrival;

/**
 *@author LiuHao
 *@time Oct 28, 2011 9:51:12 AM
 */
public interface ICusCurrivalService extends IBaseService<CusCurrival,Long> {
	/**
	 * ���澺��������Ϣ
	 * @param cusCurrival
	 * @param file
	 * @param fileName
	 * @throws Exception
	 */
	public void saveCurrival(CusCurrival cusCurrival,File file,String fileName)throws Exception;
	/**
	 * ���Ͼ���������Ϣ
	 * @param id
	 * @throws Exception
	 */
	public void delCurrival(Long id)throws Exception;
}
