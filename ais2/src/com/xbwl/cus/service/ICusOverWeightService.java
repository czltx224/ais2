package com.xbwl.cus.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprOverweight;

/**
 *@author LiuHao
 *@time Oct 25, 2011 10:56:45 AM
 */
public interface ICusOverWeightService extends IBaseService<OprOverweight,Long> {
	/**
	 * …Û∫À÷˜µ•≥¨÷ÿ
	 * @param ooId
	 * @param aduitRemark
	 * @throws Exception
	 */
	public void aduitOverWeight(Long ooId,String aduitRemark)throws Exception;
}
