package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprPrewired;
import com.xbwl.oper.stock.vo.OprPrewiredVo;

/**
 * author shuw
 * time 2011-7-19 上午11:12:37
 */
public interface IOprPrewiredService extends IBaseService<OprPrewired, Long>{

	/**
	 * 实配查询SQL拼接
	 * @param filterMap 查询条件Filter
	 * @return  查询SQL
	 * @throws Exception
	 */
	public String getAllDetail(Map filterMap) throws Exception;
	
	/**
	 * 实配查询（专车）SQL拼接
	 * @param filterMap
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String getAllDetailByCar(Map filterMap)throws Exception;
	
	/**
	 * 实配信息汇总SQL拼接
	 * @param ids
	 * @return  查询SQL
	 */
	public String getTotalDetail(String ids);
	
	
	/**
	 * 取消预配
	 * @param list  预配id集合
	 * @throws Exception
	 */
	public void deleteStatusByIds(List<Long>list ) throws Exception;
	
	/**
	 * 货物实配保存
	 * @param aa 提交的vo对象
	 * @param fileMap  提交的实配参数
	 * @param dnos   没有实配的其他预配Id
	 * @return 实配单号
	 * @throws Exception
	 */
	public Long saveOvemByIds(List<OprPrewiredVo>aa,Map fileMap,String dnos) throws Exception;
	
	/**
	 * 实配查询（部门交接）SQL拼接
	 * @param filterMap 查询条件
	 * @return  查询sql
	 * @throws Exception
	 */
	public String getAllDetailByDepartId(Map filterMap) throws Exception;
	
	/**
	 * 实配（单票添加）SQL拼接
	 * @param requestStage  参数
	 * @return
	 */
	public String getInfoByDno(String requestStage);
}
