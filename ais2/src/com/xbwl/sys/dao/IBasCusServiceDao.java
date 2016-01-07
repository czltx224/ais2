package com.xbwl.sys.dao;

import java.util.List;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.entity.BasCusService;

/**
 *@author LiuHao
 *@time Jul 29, 2011 4:10:32 PM
 */
public interface IBasCusServiceDao extends IBaseDAO<BasCusService,Long> {
	/**
	 * 查询客商对应客服员
	 * @param cusId
	 * @param bussDepart
	 * @return
	 * @throws Exception
	 */
	public List<BasCusService> findCusServices(Long cusId,Long bussDepart) throws Exception;
}
