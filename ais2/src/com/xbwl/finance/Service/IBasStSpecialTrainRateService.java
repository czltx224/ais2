package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasStSpecialTrainRate;

/**
 * @author CaoZhili time Aug 2, 2011 4:03:04 PM
 * 
 * 专车标准协议价服务层接口
 */
public interface IBasStSpecialTrainRateService extends
		IBaseService<BasStSpecialTrainRate, Long> {
	
	/**
	 * 修改专车标准协议价的状态
	 * 
	 * @param ids 专车标准协议价ID数组
	 * @param status 专车标准协议价要修改的状态
	 * @throws Exception 服务层异常
	 */
	public void updateStatusService(String[] ids, Long status)throws Exception;

	/**
	 * 判断是否可以保存专车标准协议价
	 * @param basStSpecialTrainRate
	 * @return
	 */
	public boolean allowSaveService(BasStSpecialTrainRate basStSpecialTrainRate);
	
}
