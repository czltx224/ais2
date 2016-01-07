package com.xbwl.cus.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusSearch;

/**
 * author CaoZhili
 * time Oct 17, 2011 10:08:43 AM
 */

public interface ICusSearchService extends IBaseService<CusSearch, Long> {

	/**查询自定义的查询语句
	 * @param map 集合条件(预留)
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String findCusSearchService(Map<String, String> map) throws Exception;

	/**自定义查询授权方法
	 * @param idStrings 自定义查询字符串数组
	 * @param departId 授权部门编号
	 * @throws Exception  服务层异常
	 */
	public void authorizedService(String[] idStrings, Long departId) throws  Exception;

	/**
	 * 查询自定义查询SQL获取方法
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findSearchListService(Map<String, String> map)throws Exception;

}
