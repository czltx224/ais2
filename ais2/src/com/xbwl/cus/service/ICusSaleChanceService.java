package com.xbwl.cus.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusSaleChance;

/**
 *@author LiuHao
 *@time Oct 22, 2011 9:31:33 AM
 */
public interface ICusSaleChanceService extends IBaseService<CusSaleChance,Long> {
	public void delSaleChance(Long scId)throws Exception;
}
