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
	 * 保存竞争对手信息
	 * @param cusCurrival
	 * @param file
	 * @param fileName
	 * @throws Exception
	 */
	public void saveCurrival(CusCurrival cusCurrival,File file,String fileName)throws Exception;
	/**
	 * 作废竞争对手信息
	 * @param id
	 * @throws Exception
	 */
	public void delCurrival(Long id)throws Exception;
}
