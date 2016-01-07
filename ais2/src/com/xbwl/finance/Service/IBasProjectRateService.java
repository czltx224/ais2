package com.xbwl.finance.Service;

import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasProjectRate;
 
/**
 * @author CaoZhili time Aug 10, 2011 10:43:40 AM
 * 
 * 项目客户协议价服务层接口
 */
public interface IBasProjectRateService extends
		IBaseService<BasProjectRate, Long> {
	
	public void saveProjectRate(BasProjectRate entity,String chinaName)throws Exception;
	/**
	 * 获得项目客户协议价
	 * @param piece
	 * @param weight
	 * @param bulk
	 * @throws Exception
	 */
	public Page<BasProjectRate> findProRate(Page page,Long piece,Double weight,Double bulk,Long cusId,String city,String town)throws Exception;
}
