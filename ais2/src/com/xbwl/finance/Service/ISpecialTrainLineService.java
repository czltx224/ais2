package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasSpecialTrainLine;

/**
 *@author LiuHao
 *@time 2011-7-22 下午02:58:07
 */
public interface ISpecialTrainLineService extends IBaseService<BasSpecialTrainLine, Long> {

	/**
	 * 判断专车路线是否可以删除方法
	 * @param ids 要删除的ID数组
	 * @return 返回是否可以删除
	 * @throws Exception 服务层抛出异常
	 */
	public boolean getIsNotDeleteService(String[] ids) throws Exception;

	/**
	 * 货物专车线路查询SQL
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findListService(Map<String, String> map)throws Exception;

}
