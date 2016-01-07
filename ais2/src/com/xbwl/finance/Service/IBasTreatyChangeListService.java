package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasTreatyChangeList;

/**
 * @author CaoZhili time Aug 10, 2011 2:30:10 PM
 * 
 * 协议价修改记录表服务层接口
 */
public interface IBasTreatyChangeListService extends
		IBaseService<BasTreatyChangeList, Long> {
	
	/**
	 * 保存各种协议价格的通用保存方法
	 * 
	 * @param entity 要保存记录的实体类
	 * @param chinaName 要保存记录的表名称
	 * @param isdelete 是否删除
	 * @throws Exception
	 */
	public void saveRecord(Object entity, String chinaName,boolean isdelete) throws Exception;
	
	/**
	 * 获取查询SQL
	 * @param map 条件集合
	 * @return
	 */
	public String getSqlService(Map<String,String> map) throws Exception;
}
