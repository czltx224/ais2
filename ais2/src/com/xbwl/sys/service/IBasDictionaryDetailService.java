package com.xbwl.sys.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasDictionaryDetail;

/**
 * @author Administrator
 *数据字典明细服务层接口
 */
public interface IBasDictionaryDetailService extends IBaseService<BasDictionaryDetail,Long>{
	/**
	 * 根据数据字典主表ID获得明细
	 * @author LiuHao
	 * @time Mar 2, 2012 10:01:41 AM 
	 * @param dicId
	 * @return
	 * @throws Exception
	 */
	public List<BasDictionaryDetail> getDicDetail(Long dicId)throws Exception;
}
