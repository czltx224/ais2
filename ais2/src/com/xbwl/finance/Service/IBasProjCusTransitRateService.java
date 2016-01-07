package com.xbwl.finance.Service;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasProjCusTransitRate;

/**
 * author CaoZhili
 * time Nov 29, 2011 9:33:37 AM
 */

public interface IBasProjCusTransitRateService  extends IBaseService<BasProjCusTransitRate, Long>{
	/**
	 * 查询项目客户中转协议价
	 * @param page
	 * @param piece
	 * @param weight
	 * @param bulk
	 * @param cusId
	 * @param cpId
	 * @param areaType
	 * @param takeMode
	 * @param trafficMode
	 * @return
	 * @throws Exception
	 */
	public Page<BasProjCusTransitRate> findProTraRate(Page page,Long piece,Double weight,Double bulk,Long cusId,Long cpId,String areaType,String takeMode,String trafficMode,Long disDeaprtId,String town)throws Exception;

}
