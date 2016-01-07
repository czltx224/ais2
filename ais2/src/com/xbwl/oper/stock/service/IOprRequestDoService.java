package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprRequestDo;

/**
 * @author CaoZhili time Jul 12, 2011 10:22:54 AM
 * 个性化要求服务层接口
 */
public interface IOprRequestDoService extends IBaseService<OprRequestDo, Long> {

	/**获取个性化要求执行情况查询语句
	 * @param filterParamMap
	 * @return
	 * @throws Exception
	 */
	public String getSqlRalaListService(Map<String, String> filterParamMap)throws Exception;
	/**
	 * 查询个性化要求
	 * @author LiuHao
	 * @time Apr 16, 2012 3:39:29 PM 
	 * @param dno
	 * @return
	 * @throws Exception
	 */
	public List<OprRequestDo> getRequestByDno(Long dno)throws Exception;

}
