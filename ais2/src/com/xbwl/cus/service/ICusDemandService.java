package com.xbwl.cus.service;

import java.io.File;
import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusDemand;

/**
 *@author LiuHao
 *@time Oct 9, 2011 9:27:47 AM
 */
public interface ICusDemandService extends IBaseService<CusDemand,Long> {
	/**
	 * ����ͻ�������Ϣ
	 * @param cusDemand
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean saveCusDemand(CusDemand cusDemand,File file,String fileName)throws Exception;
	/**
	 * ɾ���ͻ�������Ϣ
	 * @param pks
	 * @throws Exception
	 */
	public void delCusDemand(List pks)throws Exception;
}
