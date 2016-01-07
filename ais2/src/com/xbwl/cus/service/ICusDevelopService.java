package com.xbwl.cus.service;

import java.io.File;
import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusDevelop;

/**
 *@author LiuHao
 *@time Oct 13, 2011 3:37:50 PM
 */
public interface ICusDevelopService extends IBaseService<CusDevelop,Long> {
	/**
	 * 保存开发过程
	 * @param cusDevelop
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public void saveCusDevelop(CusDevelop cusDevelop,File file,String fileName)throws Exception;
	/**
	 * 
	 * 删除开发过程
	 * @param pks
	 * @throws Exception
	 */
	public void delCusDevelop(List pks)throws Exception;
}
