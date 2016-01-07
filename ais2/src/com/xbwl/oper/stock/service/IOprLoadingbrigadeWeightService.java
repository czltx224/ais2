package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.stock.vo.LoadingbrigadeTypeEnum;

/**
 * @author CaoZhili time Jul 8, 2011 10:08:11 AM
 * 
 * 装卸组货量表服务层接口
 */
public interface IOprLoadingbrigadeWeightService extends
		IBaseService<OprLoadingbrigadeWeight, Long> {
	
	/**提货货量统计查询语句获取方法
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String findTakeGoods(Map<String, String> map) throws Exception ;

	/**
	 * 保存到装卸组货量表，创建人、创建时间、修改人、修改时间、时间戳可以不写
	 * @param list 需要保存到装卸组货量表中的数据集合
	 * @param enumType 货物的操作类型
	 */
	public void saveLoadingWeight(List<OprLoadingbrigadeWeight> list,LoadingbrigadeTypeEnum enumType)throws Exception;

	/**
	 * 手写装卸分拨组货量统计查询方法
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findSqlListService(Map<String, String> map)throws Exception;

}
