package com.xbwl.cus.service;

import java.io.File;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusCollection;

/**
 *@author LiuHao
 *@time Nov 3, 2011 4:07:50 PM
 */
public interface ICusCollectionService extends IBaseService<CusCollection,Long> {
	/**
	 * ±£´æ´ß¿î¼ÇÂ¼
	 * @param cusCollection
	 * @param file
	 * @param fileName
	 * @throws Exception
	 */
	public void saveCollection(CusCollection cusCollection,File file,String fileName)throws Exception;
}
