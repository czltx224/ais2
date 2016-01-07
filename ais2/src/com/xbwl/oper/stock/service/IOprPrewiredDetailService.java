package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprPrewired;
import com.xbwl.entity.OprPrewiredDetail;

/**
 * author shuw
 * time 2011-7-19 上午11:19:30
 * 实配明细服务层接口
 */

public interface IOprPrewiredDetailService extends  IBaseService<OprPrewiredDetail, Long>{

	/**
	 * 预配查询SQL拼接 除部门交接外
	 * @param filterMap 条件集合
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String getListSqlAll(Map filterMap) throws Exception;
	
	/**
	 *  预配求和
	 * @param filterMap 需要求和的id字符串
	 * @return
	 * @throws Exception
	 */
	public String getAjaxTotalSum(String filterMap) throws Exception;
	
	/**
	 * 货物预配保存	
	 *  * @param oprPrewired
	 * @param ids  配送单号集合
	 * @param s
	 * @param orderFields
	 * @return
	 * @throws Exception
	 */
	public Long saveOprPrewiredByids(OprPrewired oprPrewired,List<Long> ids,String s,String orderFields) throws Exception;

	/**
	 * 发车确认交接单查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findOutCarList(Map<String, String> map)throws Exception;
}
