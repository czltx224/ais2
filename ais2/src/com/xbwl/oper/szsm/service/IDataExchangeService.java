package com.xbwl.oper.szsm.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.DigitalChinaExchange;

/**
 * 神州数码对接服务接口
 * @author czl
 * @date 2012-06-27
 */
public interface IDataExchangeService extends IBaseService<DigitalChinaExchange, Long>{
	
	/**
	 * 配送数据对接查询SQL获取
	 * @throws Exception
	 */
	public void doDispatchingExchange() throws Exception;

	/**
	 * 签收数据对接查询SQL获取
	 * @throws Exception
	 */
	public void doSingInExchange() throws Exception;

	/**
	 * 神州数码对接成功结果查询SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findList(Map<String, String> map)throws Exception;

	/**
	 * 神州数码对接统计查询SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findCount(Map<String, String> map)throws Exception;

	/**
	 * 神州数码对接统计汇总SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findCountSum(Map<String, String> map)throws Exception;
}