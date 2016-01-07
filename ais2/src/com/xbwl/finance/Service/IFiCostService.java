package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCost;

/**
 * author shuw
 * 成本表 服务层接口
 * time Sep 21, 2011 3:51:03 PM
 */

public interface IFiCostService  extends IBaseService<FiCost,Long> {

	/**
	 * 根据数据来源，来源单号 获取成本的和
	 * @param sourceData 数据来源
	 * @param dno  来源单号
	 * @return  total
	 * @throws Exception
	 */
	public double sumCostBySourceDataId(String sourceData,Long  dno) throws Exception;
	
	/**
	 * 部门利润代理汇总统计分析报表查询
	 * @param map
	 * @throws Exception
	 */
	public String getAllCqList(Map<String,String>map) throws Exception;
}
