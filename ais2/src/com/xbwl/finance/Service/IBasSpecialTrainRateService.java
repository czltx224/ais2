package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasSpecialTrainRate;

/**
 * @author CaoZhili time Aug 2, 2011 4:02:05 PM
 * 
 * 专车协议价服务层接口
 */
public interface IBasSpecialTrainRateService extends
		IBaseService<BasSpecialTrainRate, Long> {

	
	/**
	 * 修改专车协议价的状态
	 * 
	 * @param ids 专车协议价ID数组
	 * @param status 专车协议价要修改的状态
	 * @throws Exception 服务层异常
	 */
	public void updateStatusService(String[] ids, Long status)throws Exception;
	

	/**
	 * 通过提货方式，地区类型，运输方式，部门计算出折扣价
	 * @param basSpecialTrainRate 专车协议价实体对象
	 * @return 折扣价
	 */
	public Double getDiscountService(BasSpecialTrainRate basSpecialTrainRate) throws Exception;


	/**
	 * 判断是否可以保存专车协议价
	 * @param basSpecialTrainRate
	 * @return
	 */
	public boolean allowSaveService(BasSpecialTrainRate basSpecialTrainRate);
}
